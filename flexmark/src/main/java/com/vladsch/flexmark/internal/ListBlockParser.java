package com.vladsch.flexmark.internal;

import com.vladsch.flexmark.parser.ListOptions;
import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.ast.util.Parsing;
import com.vladsch.flexmark.parser.ParserEmulationFamily;
import com.vladsch.flexmark.parser.block.*;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;

import static com.vladsch.flexmark.parser.ParserEmulationFamily.*;

public class ListBlockParser extends AbstractBlockParser {
    private final ListBlock myBlock;
    private final ListOptions myOptions;
    private final ListData myListData;
    private ListItemParser myLastChild = null;
    private BasedSequence myItemHandledLine = null;
    private boolean myItemHandledNewListLine;
    private boolean myItemHandledNewItemLine;
    private boolean myItemHandledSkipActiveLine;

    public ListBlockParser(ListOptions options, ListData listData, ListItemParser listItemParser) {
        myOptions = options;
        myListData = listData;
        myBlock = listData.listBlock;
        myBlock.setTight(true);
        myLastChild = listItemParser;
        myItemHandledNewListLine = false;
        myItemHandledNewItemLine = false;
        myItemHandledSkipActiveLine = false;
    }

    BasedSequence getItemHandledLine() {
        return myItemHandledLine;
    }

    void setItemHandledLine(BasedSequence itemHandledLine) {
        myItemHandledLine = itemHandledLine;
        myItemHandledNewListLine = false;
        myItemHandledNewItemLine = false;
        myItemHandledSkipActiveLine = false;
    }

    void setItemHandledNewListLine(BasedSequence itemHandledLine) {
        myItemHandledLine = itemHandledLine;
        myItemHandledNewListLine = true;
        myItemHandledNewItemLine = false;
        myItemHandledSkipActiveLine = false;
    }

    void setItemHandledNewItemLine(BasedSequence itemHandledLine) {
        myItemHandledLine = itemHandledLine;
        myItemHandledNewListLine = false;
        myItemHandledNewItemLine = true;
        myItemHandledSkipActiveLine = false;
    }

    void setItemHandledLineSkipActive(BasedSequence itemHandledLine) {
        myItemHandledLine = itemHandledLine;
        myItemHandledNewListLine = false;
        myItemHandledNewItemLine = false;
        myItemHandledSkipActiveLine = true;
    }

    public ListOptions getOptions() {
        return myOptions;
    }

    public ListData getListData() {
        return myListData;
    }

    int getContentIndent() {
        return myListData.markerIndent + myListData.listMarker.length() + myListData.contentOffset;
    }

    int getLastContentIndent() {
        return myLastChild.getContentIndent();
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
    public ListBlock getBlock() {
        return myBlock;
    }

    @SuppressWarnings("SameParameterValue")
    private void setTight(boolean tight) {
        myBlock.setTight(tight);
    }

    @Override
    public void closeBlock(ParserState state) {
        finalizeListTight(state);
        myBlock.setCharsFromContent();
    }

    @Override
    public boolean breakOutOnDoubleBlankLine() {
        return myOptions.endOnDoubleBlank;
    }

    private void finalizeListTight(ParserState parserState) {
        Node item = getBlock().getFirstChild();
        boolean isTight = true;
        boolean prevItemLoose = false;
        boolean haveNestedList = false;

        while (item != null) {
            // check for non-final list item ending with blank line:
            boolean thisItemLoose = false;
            if (myOptions.looseWhenBlankFollowsItemParagraph) {
                if (item instanceof ListItem) {
                    if (myOptions.looseWhenBlankFollowsItemParagraph && ((ListItem) item).isHadBlankAfterItemParagraph()) {
                        if (item.getNext() == null && (item.getFirstChild() == null || item.getFirstChild().getNext() == null)) {
                            // not for last block
                        } else {
                            ((ListItem) item).setLoose(true);
                            isTight = false;
                            thisItemLoose = true;
                        }
                    }
                }
            } else {
                if (parserState.endsWithBlankLine(item) && item.getNext() != null) {
                    isTight = false;
                    thisItemLoose = true;
                }
            }

            if (item instanceof ListItem) {
                if (thisItemLoose || (myOptions.looseOnPrevLooseItem && prevItemLoose)) {
                    // set the item's tight setting
                    ((ListItem) item).setTight(false);
                }

                prevItemLoose = thisItemLoose;
            }

            // recurse into children of list item, to see if there are
            // spaces between any of them:
            Node subItem = item.getFirstChild();
            while (subItem != null) {
                if (myOptions.looseWhenBlankFollowsItemParagraph) {
                    if (subItem instanceof ListItem) {
                        if (!((ListItem) subItem).isTight()) {
                            isTight = false;
                            if (haveNestedList || !myOptions.autoLooseOneLevelLists) break;
                        }
                    }
                } else {
                    if (parserState.endsWithBlankLine(subItem) && (item.getNext() != null || subItem.getNext() != null)) {
                        isTight = false;
                        if (haveNestedList || !myOptions.autoLooseOneLevelLists) break;
                    }
                }
                if (subItem instanceof ListBlock) haveNestedList = true;
                subItem = subItem.getNext();
            }
            item = item.getNext();
        }

        if (myOptions.autoLoose && myOptions.autoLooseOneLevelLists) {
            if (!haveNestedList && getBlock().getAncestorOfType(ListBlock.class) == null && !isTight) {
                setTight(false);
            }
        } else {
            if (myOptions.autoLoose && !isTight) {
                setTight(false);
            }
        }
    }

    /**
     * Parse a list marker and return data on the marker or null.
     */
    static ListData parseListMarker(int newItemCodeIndent, ParserState state) {
        Parsing parsing = state.getParsing();
        BasedSequence line = state.getLine();
        int markerIndex = state.getNextNonSpaceIndex();
        int markerColumn = state.getColumn() + state.getIndent();
        int markerIndent = state.getIndent();

        BasedSequence rest = line.subSequence(markerIndex, line.length());
        Matcher matcher = parsing.LIST_ITEM_MARKER.matcher(rest);
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
        int contentOffset = 0;

        // See at which column the content starts if there is content
        boolean hasContent = false;
        for (int i = indexAfterMarker; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '\t') {
                contentOffset += Parsing.columnsToNextTabStop(columnAfterMarker + contentOffset);
            } else if (c == ' ') {
                contentOffset++;
            } else {
                hasContent = true;
                break;
            }
        }

        if (!hasContent || contentOffset > newItemCodeIndent) {
            // If this line is blank or has a code block, default to 1 space after marker
            contentOffset = 1;
        }

        return new ListData(listBlock, !hasContent, markerIndex, markerColumn, markerIndent, contentOffset, rest.subSequence(matcher.start(), matcher.end()), isNumberedList);
    }

    private static ListBlock createListBlock(Matcher matcher) {
        String bullet = matcher.group(1);
        if (bullet != null) {
            BulletList bulletList = new BulletList();
            bulletList.setOpeningMarker(bullet.charAt(0));
            return bulletList;
        } else {
            String digit = matcher.group(2);
            String delimiter = matcher.group(3);
            OrderedList orderedList = new OrderedList();
            orderedList.setStartNumber(Integer.parseInt(digit));
            orderedList.setDelimiter(delimiter.charAt(0));
            return orderedList;
        }
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
        private final ListOptions myOptions;

        BlockFactory(DataHolder options) {
            super(options);
            myOptions = new ListOptions(options);
        }

        @Override
        public BlockStart tryStart(ParserState state, MatchedBlockParser matchedBlockParser) {
            BlockParser matched = matchedBlockParser.getBlockParser();
            ParserEmulationFamily emulationFamily = myOptions.parserEmulationFamily;
            int newItemCodeIndent = emulationFamily == COMMONMARK ? myOptions.codeIndent : -1;

            if (matched instanceof ListBlockParser) {
                // the list item should have handled this line, if it is part of new list item, or a new sub list
                ListBlockParser listBlockParser = (ListBlockParser) matched;

                if (state.getLine() == listBlockParser.myItemHandledLine) {
                    if (listBlockParser.myItemHandledNewListLine) {
                        // it is a new list already determined by the item
                        ListData listData = parseListMarker(newItemCodeIndent, state);
                        ListItemParser listItemParser = new ListItemParser(myOptions, state.getParsing(), listData);

                        assert listData != null;

                        int newColumn = listData.markerColumn + listData.listMarker.length() + listData.contentOffset;
                        listBlockParser = new ListBlockParser(myOptions, listData, listItemParser);
                        return BlockStart.of(listBlockParser, listItemParser).atColumn(newColumn);
                    } else if (listBlockParser.myItemHandledNewItemLine) {
                        // it is a new item for this list already determined by the previous item
                        ListData listData = parseListMarker(newItemCodeIndent, state);
                        ListItemParser listItemParser = new ListItemParser(myOptions, state.getParsing(), listData);

                        assert listData != null;

                        int newColumn = listData.markerColumn + listData.listMarker.length() + listData.contentOffset;
                        listBlockParser.myLastChild = listItemParser;

                        return BlockStart.of(listItemParser).atColumn(newColumn);
                    }

                    // then it is not for us to handle, since we only handle new list creation here outside of an existing list
                    listBlockParser.myItemHandledLine = null;
                    return BlockStart.none();
                }

                // if there is a pre-existing list then it's last list item should have handled the line
                return BlockStart.none();
            } else {
                // see if the list item is still active and set line handled, need this to handle lazy continuations when they look like list items
                ListBlock block = (ListBlock) matched.getBlock().getAncestorOfType(ListBlock.class);
                if (block != null) {
                    ListBlockParser listBlockParser = (ListBlockParser) state.getActiveBlockParser(block);
                    if (listBlockParser.myItemHandledLine == state.getLine() && listBlockParser.myItemHandledSkipActiveLine) {
                        listBlockParser.myItemHandledLine = null;
                        return BlockStart.none();
                    }
                }
            }

            // at this point if the line should have been handled by the the item
            // what we can have here is list items for the same list or list items that start a new list because of mismatched type
            // nothing else should get here because the list item should have handled it
            // so everything should have indent >= current list indent, the rest should not be here

            if (emulationFamily == COMMONMARK) {
                // - CommonMark: version 0.27 of the spec, all common mark parsers
                //     - Definitions/Defaults:
                //         - `ITEM_INDENT` = 4 <!-- not used -->
                //         - `CODE_INDENT` = 4
                //         - `current indent` = `line indent`
                //     - Start List Conditions:
                //         - `item indent` < `CODE_INDENT`: new list with new item
                //         - `item content indent` >= `CODE_INDENT`: empty item, indented code
                //     - Continuation Conditions:
                //         - `current indent` >= `list last content indent` + `CODE_INDENT`: indented code
                //         - `current indent` >= `list last content indent`: sub-item
                //         - `current indent` >= `list indent`: list item

                int currentIndent = state.getIndent();

                if (currentIndent >= myOptions.codeIndent) {
                    return BlockStart.none();
                }
            } else if (emulationFamily == FIXED_INDENT) {
                // - FixedIndent: Pandoc, MultiMarkdown, Pegdown
                //     - Definitions/Defaults:
                //         - `ITEM_INDENT` = 4
                //         - `CODE_INDENT` = 8
                //         - `current indent` = line indent
                //     - Start List Conditions:
                //         - `current indent` < `ITEM_INDENT`: new list with new item
                //     - Continuation Conditions:
                //          - `current indent` >= `CODE_INDENT`: indented code
                //          - `current indent` >= `ITEM_INDENT`: sub-item
                //          - otherwise: list item

                int currentIndent = state.getIndent();

                if (currentIndent >= myOptions.codeIndent) {
                    return BlockStart.none();
                }
            } else if (emulationFamily == KRAMDOWN) {
                // - Kramdown:
                //     - Definitions/Defaults:
                //         - `ITEM_INDENT` = 4
                //         - `CODE_INDENT` = 8
                //         - `current indent` = `line indent`
                //     - Start List Conditions:
                //         - `current indent` < `ITEM_INDENT`: new list with new item
                //     - Continuation Conditions:
                //         - `current indent` >= `list content indent` + `CODE_INDENT`: indented code
                //         - `current indent` >= `list content indent` + `ITEM_INDENT`:
                //             - if had blank line in item && have previous list item parent: then let it have it
                //             - otherwise: lazy continuation of last list item
                //         - `current indent` >= `item content indent`: sub-item
                //         - `current indent` >= `list content indent`: list item

                int currentIndent = state.getIndent();
                int listContentIndent = 0;

                if (currentIndent >= listContentIndent + myOptions.itemIndent) {
                    return BlockStart.none();
                }
            } else if (emulationFamily == MARKDOWN) {
                // - Markdown:
                //     - Definitions/Defaults:
                //         - `ITEM_INDENT` = 4
                //         - `CODE_INDENT` = 8
                //         - `current indent` = `line indent`
                //     - Start List Conditions:
                //         - `current indent` < `ITEM_INDENT`: new list with new item
                //     - Continuation Conditions:
                //         - `current indent` >= `list indent` + `CODE_INDENT`:
                //             - if had blank line in item && have previous list item parent: then let it have it
                //             - otherwise: lazy continuation of last list item
                //         - `current indent` > `list indent`: sub-item
                //         - `current indent` == `list indent`: list item

                int currentIndent = state.getIndent();
                if (currentIndent >= myOptions.itemIndent) {
                    return BlockStart.none();
                }
            }

            ListData listData = parseListMarker(newItemCodeIndent, state);

            if (listData != null) {
                int newColumn = listData.markerColumn + listData.listMarker.length() + listData.contentOffset;

                boolean inParagraph = matched.isParagraphParser();
                boolean inParagraphListItem = inParagraph && matched.getBlock().getParent() instanceof ListItem && matched.getBlock() == matched.getBlock().getParent().getFirstChild();

                if (inParagraph && !myOptions.canInterrupt(listData.listBlock, listData.isEmpty, inParagraphListItem)) {
                    return BlockStart.none();
                }

                ListItemParser listItemParser = new ListItemParser(myOptions, state.getParsing(), listData);

                // prepend new list block
                ListBlockParser listBlockParser = new ListBlockParser(myOptions, listData, listItemParser);
                return BlockStart.of(listBlockParser, listItemParser).atColumn(newColumn);
            }

            return BlockStart.none();
        }
    }

    static class ListData {
        final ListBlock listBlock;
        final boolean isEmpty;
        final int markerIndex;
        final int markerColumn;
        final int markerIndent;
        final int contentOffset;
        final BasedSequence listMarker;
        final boolean isNumberedList;

        ListData(ListBlock listBlock, boolean isEmpty, int markerIndex, int markerColumn, int markerIndent, int contentOffset, BasedSequence listMarker, boolean isNumberedList) {
            this.listBlock = listBlock;
            this.isEmpty = isEmpty;
            this.markerIndex = markerIndex;
            this.markerColumn = markerColumn;
            this.markerIndent = markerIndent;
            this.contentOffset = contentOffset;
            this.listMarker = listMarker;
            this.isNumberedList = isNumberedList;
        }
    }
}
