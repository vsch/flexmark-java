package com.vladsch.flexmark.internal;

import com.vladsch.flexmark.internal.util.BasedSequence;
import com.vladsch.flexmark.internal.util.DataHolder;
import com.vladsch.flexmark.internal.util.Parsing;
import com.vladsch.flexmark.node.*;
import com.vladsch.flexmark.parser.block.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListBlockParser extends AbstractBlockParser {

    private static Pattern MARKER = Pattern.compile(
            "^([*+-])(?= |\t|$)" +
                    "|^(\\d{1,9})([.)])(?= |\t|$)");

    private final ListBlock block;
    private final ListOptions options;
    private final int itemIndent;
    private final boolean isNumberedList;

    public ListBlockParser(ListOptions options, ListData listData) {
        this.options = options;
        this.block = listData.listBlock;
        this.itemIndent = listData.indent;
        this.isNumberedList = listData.isNumberedList;
    }

    public ListOptions getOptions() {
        return options;
    }

    @Override
    public boolean isContainer() {
        return true;
    }

    @Override
    public boolean canContain(Block block) {
        return block instanceof ListItem;
    }

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public BlockContinue tryContinue(ParserState state) {
        // List blocks themselves don't have any markers, only list items. So try to stay in the list.
        // If there is a block start other than list item, canContain makes sure that this list is closed.
        return BlockContinue.atIndex(state.getIndex());
    }

    private void setTight(boolean tight) {
        block.setTight(tight);
    }

    @Override
    public void closeBlock(ParserState parserState) {
        finalizeListTight(parserState);
        block.setCharsFromContent();
    }

    @Override
    public boolean breakOutOnDoubleBlankLine() {
        return options.endOnDoubleBlank;
    }

    private void finalizeListTight(ParserState parserState) {
        Node item = getBlock().getFirstChild();
        while (item != null) {
            // check for non-final list item ending with blank line:
            if (parserState.endsWithBlankLine(item) && item.getNext() != null) {
                if (item instanceof ListItem) {
                    // set the item's tight setting
                    ((ListItem) item).setTight(false);
                }

                if (options.autoLoose) {
                    setTight(false);
                }
                break;
            }

            // recurse into children of list item, to see if there are
            // spaces between any of them:
            if (options.autoLoose) {
                Node subItem = item.getFirstChild();
                while (subItem != null) {
                    if (parserState.endsWithBlankLine(subItem) && (item.getNext() != null || subItem.getNext() != null)) {
                        setTight(false);
                        break;
                    }
                    subItem = subItem.getNext();
                }
            }
            item = item.getNext();
        }
    }

    /**
     * Parse a list marker and return data on the marker or null.
     */
    private static ListData parseListMarker(BasedSequence line, int indent, final int markerIndex, final int markerColumn) {
        BasedSequence rest = line.subSequence(markerIndex, line.length());
        Matcher matcher = MARKER.matcher(rest);
        if (!matcher.find()) {
            return null;
        }

        ListBlock listBlock = createListBlock(matcher);

        int markerLength = matcher.end() - matcher.start();
        boolean isNumberedList = !"+-*".contains(matcher.group());
        int indexAfterMarker = markerIndex + markerLength;
        // marker doesn't include tabs, so counting them as columns directly is ok
        int columnAfterMarker = markerColumn + markerLength;
        // the column within the line where the content starts
        int contentColumn = columnAfterMarker;

        // See at which column the content starts if there is content
        boolean hasContent = false;
        for (int i = indexAfterMarker; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '\t') {
                contentColumn += Parsing.columnsToNextTabStop(contentColumn);
            } else if (c == ' ') {
                contentColumn++;
            } else {
                hasContent = true;
                break;
            }
        }

        if (!hasContent || (contentColumn - columnAfterMarker) > Parsing.CODE_BLOCK_INDENT) {
            // If this line is blank or has a code block, default to 1 space after marker
            contentColumn = columnAfterMarker + 1;
        }
        return new ListData(listBlock, indent, contentColumn, rest.subSequence(matcher.start(), matcher.end()), isNumberedList);
    }

    private static ListBlock createListBlock(Matcher matcher) {
        String bullet = matcher.group(1);
        if (bullet != null) {
            BulletList bulletList = new BulletList();
            bulletList.setOpeningMarker(bullet.charAt(0));
            return bulletList;
        } else {
            String digit = matcher.group(2);
            String delim = matcher.group(3);
            OrderedList orderedList = new OrderedList();
            orderedList.setStartNumber(Integer.parseInt(digit));
            orderedList.setDelimiter(delim.charAt(0));
            return orderedList;
        }
    }

    /**
     * Returns true if the two list items are of the same type,
     * with the same delimiter and bullet character. This is used
     * in agglomerating list items into lists.
     */
    private static boolean listsMatch(ListBlock a, ListBlock b) {
        if (a instanceof BulletList && b instanceof BulletList) {
            return equals(((BulletList) a).getOpeningMarker(), ((BulletList) b).getOpeningMarker());
        } else if (a instanceof OrderedList && b instanceof OrderedList) {
            return equals(((OrderedList) a).getDelimiter(), ((OrderedList) b).getDelimiter());
        }
        return false;
    }

    private static boolean equals(Object a, Object b) {
        return (a == null) ? (b == null) : a.equals(b);
    }

    public static class Factory implements CustomBlockParserFactory {
        @Override
        public BlockParserFactory create(DataHolder options) {
            return new BlockFactory(options);
        }
    }

    private static class BlockFactory extends AbstractBlockParserFactory {
        private final ListOptions options;

        private BlockFactory(DataHolder options) {
            super(options);
            this.options = new ListOptions(options);
        }

        @Override
        public BlockStart tryStart(ParserState state, MatchedBlockParser matchedBlockParser) {
            BlockParser matched = matchedBlockParser.getMatchedBlockParser();

            if (state.getIndent() >= Parsing.CODE_BLOCK_INDENT && !(matched instanceof ListBlockParser)) {
                return BlockStart.none();
            }

            if (!options.relaxedStart && matched.isParagraphParser()) {
                return BlockStart.none();
            }

            ListBlockParser listBlockParser = matched instanceof ListBlockParser ? (ListBlockParser) matched : null;

            if (listBlockParser != null && options.fixedIndent > 0 && state.getIndent() >= listBlockParser.itemIndent + options.fixedIndent) {
                return BlockStart.none();
            }

            int markerIndex = state.getNextNonSpaceIndex();

            ListData listData = parseListMarker(state.getLine(), state.getIndent(), markerIndex, state.getColumn() + state.getIndent());
            if (listData == null) {
                return BlockStart.none();
            }

            int newContentColumn = options.fixedIndent > 0 ? state.getColumn() + options.fixedIndent : listData.contentColumn;
            ListItemParser listItemParser = new ListItemParser(newContentColumn - state.getColumn(), listData.listMarker, listData.isNumberedList);

            int newColumn = listData.contentColumn;
            // prepend the list block if needed
            if (listBlockParser == null || (options.bulletMatch && !listsMatch((ListBlock) matched.getBlock(), listData.listBlock))) {
                listBlockParser = new ListBlockParser(options, listData);
                listBlockParser.setTight(true);

                return BlockStart.of(listBlockParser, listItemParser).atColumn(newColumn);
            } else {
                return BlockStart.of(listItemParser).atColumn(newColumn);
            }
        }
    }

    private static class ListData {
        final ListBlock listBlock;
        final int contentColumn;
        final int indent;
        final BasedSequence listMarker;
        final boolean isNumberedList;

        ListData(ListBlock listBlock, int indent, int contentColumn, BasedSequence listMarker, boolean isNumberedList) {
            this.listBlock = listBlock;
            this.indent = indent;
            this.contentColumn = contentColumn;
            this.listMarker = listMarker;
            this.isNumberedList = isNumberedList;
        }
    }
}
