package com.vladsch.flexmark.internal;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.ast.util.Parsing;
import com.vladsch.flexmark.parser.block.*;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;

public class ListBlockParser extends AbstractBlockParser {

    private final ListBlock block;
    private final ListOptions options;
    final int itemIndent;
    final int markerColumn;
    final int itemContentColumn;
    private final boolean isNumberedList;

    public ListBlockParser(ListOptions options, ListData listData) {
        this.options = options;
        this.block = listData.listBlock;
        this.markerColumn = listData.markerColumn;
        this.itemIndent = listData.indent;
        this.itemContentColumn = listData.contentColumn;
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

    void setTight(boolean tight) {
        block.setTight(tight);
    }

    @Override
    public void closeBlock(ParserState state) {
        finalizeListTight(state);
        block.setCharsFromContent();
    }

    @Override
    public boolean breakOutOnDoubleBlankLine() {
        return options.endOnDoubleBlank;
    }

    private void finalizeListTight(ParserState parserState) {
        Node item = getBlock().getFirstChild();
        boolean isTight = true;
        boolean prevItemLoose = false;

        while (item != null) {
            // check for non-final list item ending with blank line:
            boolean thisItemLoose = false;
            if (parserState.endsWithBlankLine(item) && item.getNext() != null) {
                isTight = false;
                thisItemLoose = true;
            }

            if (item instanceof ListItem) {
                if (thisItemLoose || (options.looseOnPrevLooseItem && prevItemLoose)) {
                    // set the item's tight setting
                    ((ListItem) item).setTight(false);
                }

                prevItemLoose = thisItemLoose;
            }

            // recurse into children of list item, to see if there are
            // spaces between any of them:
            Node subItem = item.getFirstChild();
            while (subItem != null) {
                if (parserState.endsWithBlankLine(subItem) && (item.getNext() != null || subItem.getNext() != null)) {
                    isTight = false;
                    break;
                }
                subItem = subItem.getNext();
            }
            item = item.getNext();
        }

        if (options.autoLoose && !isTight) setTight(false);
    }

    static Boolean isOrderedListMarker(BasedSequence line, Parsing parsing, int markerIndex) {
        BasedSequence rest = line.subSequence(markerIndex, line.length());
        Matcher matcher = parsing.LIST_ITEM_MARKER.matcher(rest);
        if (!matcher.find()) {
            return null;
        }
        return !"+-*".contains(matcher.group());
    }

    /**
     * Parse a list marker and return data on the marker or null.
     */
    static ListData parseListMarker(ListOptions options, Parsing parsing, BasedSequence line, int indent, final int markerIndex, final int markerColumn, final boolean inParagraph, final boolean inParagraphListItem) {
        BasedSequence rest = line.subSequence(markerIndex, line.length());
        Matcher matcher = parsing.LIST_ITEM_MARKER.matcher(rest);
        if (!matcher.find()) {
            return null;
        }

        ListBlock listBlock = createListBlock(matcher);

        if (inParagraph) {
            // If the list item is ordered, the start number must be 1 to interrupt a paragraph.
            if (listBlock instanceof OrderedList) {
                if (inParagraphListItem) {
                    if (!(options.orderedItemInterruptsItemParagraph && (options.orderedNonOneItemInterruptsParentItemParagraph || !options.orderedStart || ((OrderedList) listBlock).getStartNumber() == 1))) {
                        return null;
                    }
                } else {
                    if (!(options.orderedItemInterruptsParagraph && (options.orderedNonOneItemInterruptsParagraph || !options.orderedStart || ((OrderedList) listBlock).getStartNumber() == 1))) {
                        return null;
                    }
                }
            } else {
                if (!(options.bulletItemInterruptsParagraph || options.bulletItemInterruptsItemParagraph && inParagraphListItem)) {
                    return null;
                }
            }
        }

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

        if (inParagraph) {
            // Empty list item can not interrupt a paragraph.
            if (!hasContent) {
                return null;
            }
        }

        if (!hasContent || (contentColumn - columnAfterMarker) > parsing.CODE_BLOCK_INDENT) {
            // If this line is blank or has a code block, default to 1 space after marker
            contentColumn = columnAfterMarker + 1;
        }
        return new ListData(listBlock, indent, markerColumn, contentColumn, rest.subSequence(matcher.start(), matcher.end()), isNumberedList);
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
    enum ListMatchType {
        PREFIX_MATCHED(true, true),
        PREFIX_NOT_MATCHED(false, true),
        TYPE_MISMATCHED_NEWLIST(false, false),
        TYPE_MISMATCHED_IGNORE(false, false),
        TYPE_MISMATCHED_SUBITEM(false, false);

        final private boolean isPrefixMatched;
        final private boolean isTypeMatched;

        ListMatchType(boolean isPrefixMatched, boolean isTypeMatched) {
            this.isPrefixMatched = isPrefixMatched;
            this.isTypeMatched = isTypeMatched;
        }
    }

    static ListMatchType listsMatch(ListOptions options, ListBlock a, ListBlock b) {
        if (a instanceof BulletList && b instanceof BulletList) {
            return !options.bulletMatch || equals(((BulletList) a).getOpeningMarker(), ((BulletList) b).getOpeningMarker()) ? ListMatchType.PREFIX_MATCHED : ListMatchType.PREFIX_NOT_MATCHED;
        } else if (a instanceof OrderedList && b instanceof OrderedList) {
            return !options.bulletMatch || equals(((OrderedList) a).getDelimiter(), ((OrderedList) b).getDelimiter()) ? ListMatchType.PREFIX_MATCHED : ListMatchType.PREFIX_NOT_MATCHED;
        }
        return options.itemTypeMatch ? (options.itemMismatchToSubItem ? ListMatchType.TYPE_MISMATCHED_SUBITEM : ListMatchType.TYPE_MISMATCHED_NEWLIST) : ListMatchType.TYPE_MISMATCHED_IGNORE;
    }

    private static boolean equals(Object a, Object b) {
        return (a == null) ? (b == null) : a.equals(b);
    }

    @Override
    public BlockContinue tryContinue(ParserState state) {
        // List blocks themselves don't have any markers, only list items. So try to stay in the list.
        // If there is a block start other than list item, canContain makes sure that this list is closed.
        return BlockContinue.atIndex(state.getIndex());
    }

    public static class Factory implements CustomBlockParserFactory {
        @Override
        public Set<Class<? extends CustomBlockParserFactory>> getAfterDependents() {
            return new HashSet<>(Arrays.asList(
                    BlockQuoteParser.Factory.class,
                    HeadingParser.Factory.class,
                    FencedCodeBlockParser.Factory.class,
                    HtmlBlockParser.Factory.class,
                    ThematicBreakParser.Factory.class
                    //ListBlockParser.Factory.class,
                    //IndentedCodeBlockParser.Factory.class
            ));
        }

        @Override
        public Set<Class<? extends CustomBlockParserFactory>> getBeforeDependents() {
            return new HashSet<>(Arrays.asList(
                    //BlockQuoteParser.Factory.class,
                    //HeadingParser.Factory.class,
                    //FencedCodeBlockParser.Factory.class,
                    //HtmlBlockParser.Factory.class,
                    //ThematicBreakParser.Factory.class,
                    //ListBlockParser.Factory.class,
                    IndentedCodeBlockParser.Factory.class
            ));
        }

        @Override
        public boolean affectsGlobalScope() {
            return false;
        }

        @Override
        public BlockParserFactory create(DataHolder options) {
            return new BlockFactory(options);
        }
    }

    private static class BlockFactory extends AbstractBlockParserFactory {
        private final ListOptions options;

        BlockFactory(DataHolder options) {
            super(options);
            this.options = new ListOptions(options);
        }

        @Override
        public BlockStart tryStart(ParserState state, MatchedBlockParser matchedBlockParser) {
            BlockParser matched = matchedBlockParser.getMatchedBlockParser();

            if ((!options.useListContentIndent || !options.listContentIndentOverridesCodeOffset) && state.getIndent() >= state.getParsing().CODE_BLOCK_INDENT && !(matched instanceof ListBlockParser)) {
                return BlockStart.none();
            }

            ListBlockParser listBlockParser = matched instanceof ListBlockParser ? (ListBlockParser) matched : null;

            if (listBlockParser != null && options.fixedIndent > 0 && state.getIndent() >= listBlockParser.itemIndent + options.fixedIndent) {
                return BlockStart.none();
            }

            int markerIndex = state.getNextNonSpaceIndex();
            int markerColumn = state.getColumn() + state.getIndent();
            boolean inParagraph = matched.isParagraphParser();
            boolean inParagraphListItem = inParagraph && matched.getBlock().getParent() instanceof ListItem && matched.getBlock() == matched.getBlock().getParent().getFirstChild();

            ListData listData = parseListMarker(options, state.getParsing(), state.getLine(), state.getIndent(), markerIndex, markerColumn, inParagraph, inParagraphListItem);
            if (listData == null) {
                return BlockStart.none();
            }

            Integer moreThanToSubItem = null;
            if (options.useListContentIndent) {
                // see if we are in a list item
                if (state.getIndent() >= options.listContentIndentOffset + state.getParsing().CODE_BLOCK_INDENT && !(matched instanceof ListBlockParser)) {
                    return BlockStart.none();
                }

                //moreThanToSubItem = listData.markerColumn;
                //
                //List<BlockParser> parsers = state.getActiveBlockParsers();
                //ListBlockParser parentListBlockParser = null;
                //
                //int iMax = parsers.size();
                //for (int i = iMax; i-- > 1; ) {
                //    if (parsers.get(i) instanceof ListBlockParser) {
                //        parentListBlockParser = (ListBlockParser) parsers.get(i);
                //        moreThanToSubItem = parentListBlockParser.markerColumn;
                //        break;
                //    }
                //    
                //    if (!(parsers.get(i) instanceof ParagraphParser || parsers.get(i) instanceof ListItemParser)) {
                //        break;
                //    }
                //}
            }

            int newContentColumn = options.fixedIndent > 0 ? state.getColumn() + options.fixedIndent : listData.contentColumn;
            ListItemParser listItemParser = new ListItemParser(options, state.getParsing(), listData.markerColumn, newContentColumn, newContentColumn - state.getColumn(), listData.listMarker, listData.isNumberedList);

            int newColumn = listData.contentColumn;
            // prepend the list block if needed
            if (listBlockParser == null) {
                listBlockParser = new ListBlockParser(options, listData);
                listBlockParser.setTight(true);

                return BlockStart.of(listBlockParser, listItemParser).atColumn(newColumn);
            } else {
                ListMatchType matchType = listsMatch(options, (ListBlock) matched.getBlock(), listData.listBlock);
                if (matchType == ListMatchType.PREFIX_NOT_MATCHED || matchType == ListMatchType.TYPE_MISMATCHED_NEWLIST) {
                    listBlockParser = new ListBlockParser(options, listData);
                    listBlockParser.setTight(true);

                    return BlockStart.of(listBlockParser, listItemParser).atColumn(newColumn);
                } else if (matchType == ListMatchType.TYPE_MISMATCHED_SUBITEM) {
                    // need to start a new sub-item list
                    listBlockParser = new ListBlockParser(options, listData);
                    listBlockParser.setTight(true);

                    return BlockStart.of(listBlockParser, listItemParser).atColumn(newColumn);
                } else {
                    if (options.useListContentIndent && options.overIndentsToFirstItem) {
                        // if this is the first list with a single item and we are > list.markerColumn and options.firstListFirstItemTakesOverIndents we start a sub list
                        if (listData.markerColumn > listBlockParser.markerColumn && !(listBlockParser.getBlock().getParent() instanceof ListItem) && listBlockParser.getBlock().getFirstChild() == listBlockParser.getBlock().getLastChild())
                        {
                            listBlockParser = new ListBlockParser(options, listData);
                            listBlockParser.setTight(true);

                            return BlockStart.of(listBlockParser, listItemParser).atColumn(newColumn);
                        }
                    }
                    return BlockStart.of(listItemParser).atColumn(newColumn);
                }
            }
        }
    }

    private static class ListData {
        final ListBlock listBlock;
        final int contentColumn;
        final int markerColumn;
        final int indent;
        final BasedSequence listMarker;
        final boolean isNumberedList;

        ListData(ListBlock listBlock, int indent, int markerColumn, int contentColumn, BasedSequence listMarker, boolean isNumberedList) {
            this.listBlock = listBlock;
            this.indent = indent;
            this.markerColumn = markerColumn;
            this.contentColumn = contentColumn;
            this.listMarker = listMarker;
            this.isNumberedList = isNumberedList;
        }
    }
}
