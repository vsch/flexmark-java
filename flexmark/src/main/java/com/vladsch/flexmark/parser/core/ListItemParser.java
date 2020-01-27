package com.vladsch.flexmark.parser.core;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.ast.util.Parsing;
import com.vladsch.flexmark.parser.ListOptions;
import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.parser.block.AbstractBlockParser;
import com.vladsch.flexmark.parser.block.BlockContinue;
import com.vladsch.flexmark.parser.block.BlockParser;
import com.vladsch.flexmark.parser.block.ParserState;
import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.misc.Utils;

import static com.vladsch.flexmark.parser.Parser.PARSER_EMULATION_PROFILE;
import static com.vladsch.flexmark.parser.ParserEmulationProfile.*;

public class ListItemParser extends AbstractBlockParser {
    final private ListItem myBlock;
    final private ListOptions myOptions;
    final private ListBlockParser.ListData myListData;
    final private Parsing myParsing;
    private boolean myHadBlankLine = false;
    private boolean myIsEmpty = false;

    ListItemParser(ListOptions options, Parsing parsing, ListBlockParser.ListData listData) {
        myOptions = options;
        myListData = listData;
        myParsing = parsing;
        myBlock = myListData.isNumberedList ? new OrderedListItem() : new BulletListItem();
        myBlock.setOpeningMarker(myListData.listMarker);
        myBlock.setMarkerSuffix(myListData.markerSuffix);
    }

    @SuppressWarnings({ "WeakerAccess", "unused" })
    int getContentColumn() {
        return myListData.markerColumn + myListData.listMarker.length() + (myOptions.isItemContentAfterSuffix() ? myListData.contentOffset : myListData.markerSuffixOffset);
    }

    @SuppressWarnings("WeakerAccess")
    int getContentIndent() {
        return myListData.markerIndent + myListData.listMarker.length() + (myOptions.isItemContentAfterSuffix() ? myListData.contentOffset : myListData.markerSuffixOffset);
    }

    int getMarkerContentIndent() {
        return myListData.markerIndent + myListData.listMarker.length() + 1;
    }

    @Override
    public boolean isContainer() {
        return true;
    }

    @Override
    public boolean canContain(ParserState state, BlockParser blockParser, Block block) {
        // Issue 66, fenced code can only be contained in GitHub Doc mode if it is indented more than list item
        if (block instanceof FencedCodeBlock) {
            // see if it indented more than our marker
            if (PARSER_EMULATION_PROFILE.get(state.getProperties()) == GITHUB_DOC) {
                // Issue #66, if we are in a list item and our indent == list indent then we interrupt the list
                FencedCodeBlockParser parser = (FencedCodeBlockParser) blockParser;
                return myListData.markerIndent < parser.getFenceMarkerIndent();
            }
        }
        return true;
    }

    @Override
    public boolean isPropagatingLastBlankLine(BlockParser lastMatchedBlockParser) {
        return !(myBlock.getFirstChild() == null && this != lastMatchedBlockParser);
    }

    @Override
    public Block getBlock() {
        return myBlock;
    }

    @Override
    public void closeBlock(ParserState state) {
        myBlock.setCharsFromContent();
    }

    private BlockContinue continueAtColumn(int newColumn) {
        // reset our empty flag, we have content now so we stay open
        if (myHadBlankLine) {
            myBlock.setContainsBlankLine(true);
        }
        myIsEmpty = false;
        return BlockContinue.atColumn(newColumn);
    }

    @Override
    public BlockContinue tryContinue(ParserState state) {
        if (state.isBlank()) {
            // now when we have a Blank line after empty list item, now we need to handle it because the list is not closed and we handle next list item conditions
            Node firstChild = myBlock.getFirstChild();
            myIsEmpty = firstChild == null;
            if (myIsEmpty || firstChild.getNext() == null) {
                myBlock.setHadBlankAfterItemParagraph(true);
            }
            myHadBlankLine = true;
            return BlockContinue.atIndex(state.getNextNonSpaceIndex());
        }

        assert myBlock.getParent() instanceof ListBlock;

        ListBlockParser listBlockParser = (ListBlockParser) state.getActiveBlockParser(myBlock.getParent());

        ParserEmulationProfile emulationProfile = myOptions.getParserEmulationProfile();
        ParserEmulationProfile emulationFamily = emulationProfile.family;
        final int contentIndent = getContentIndent();
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
            int newColumn = state.getColumn() + contentIndent;

            if (currentIndent >= contentIndent + myOptions.getCodeIndent()) {
                // our indented code child
                listBlockParser.setItemHandledLine(state.getLine());
                return continueAtColumn(newColumn);
            } else {
                ListBlockParser.ListData listData = ListBlockParser.parseListMarker(myOptions, myOptions.getCodeIndent(), state);

                if (currentIndent >= contentIndent) {
                    if (listData != null) {
                        BlockParser matched = state.getActiveBlockParser();
                        boolean inParagraph = matched.isParagraphParser();
                        boolean inParagraphListItem = inParagraph && matched.getBlock().getParent() instanceof ListItem && matched.getBlock() == matched.getBlock().getParent().getFirstChild();

                        if (inParagraphListItem
                                && (!myOptions.canInterrupt(listData.listBlock, listData.isEmpty, true)
                                || !myOptions.canStartSubList(listData.listBlock, listData.isEmpty))) {
                            // just a lazy continuation of us
                            listBlockParser.setItemHandledLineSkipActive(state.getLine());
                            return continueAtColumn(newColumn);
                        } else {
                            // our sub list item
                            listBlockParser.setItemHandledNewListLine(state.getLine());
                            return continueAtColumn(newColumn);
                        }
                    } else {
                        if (myIsEmpty) {
                            // our child item, other than a list item, if we are empty then no such thing
                            listBlockParser.setItemHandledLine(state.getLine());
                            return BlockContinue.none();
                        } else {
                            listBlockParser.setItemHandledLine(state.getLine());
                            return continueAtColumn(newColumn);
                        }
                    }
                } else if (listData != null) {
                    if (!myHadBlankLine && !myOptions.canInterrupt(listData.listBlock, listData.isEmpty, true)) {
                        // our text or lazy continuation
                        listBlockParser.setItemHandledLine(state.getLine());
                        return continueAtColumn(state.getColumn() + currentIndent);
                    } else {
                        // here have to see if the item is really a mismatch and we sub-list mismatches
                        boolean overrideSubList = myOptions.isItemTypeMismatchToNewList() && myOptions.isItemTypeMismatchToSubList() && myHadBlankLine;
                        if (!overrideSubList && myOptions.startSubList(listBlockParser.getBlock(), listData.listBlock)) {
                            // we keep it as our sub-item
                            listBlockParser.setItemHandledNewListLine(state.getLine());
                            return continueAtColumn(state.getColumn() + currentIndent);
                        } else {
                            if (myOptions.startNewList(listBlockParser.getBlock(), listData.listBlock)) {
                                // a new list
                                listBlockParser.setItemHandledNewListLine(state.getLine());
                                return BlockContinue.none();
                            } else {
                                // the next line in the list
                                listBlockParser.setItemHandledNewItemLine(state.getLine());
                                return BlockContinue.none();
                            }
                        }
                    }
                }
            }
        } else {
            final int itemIndent = myOptions.getItemIndent();

            if (emulationFamily == FIXED_INDENT) {
                // - FixedIndent: Pandoc, MultiMarkdown, Pegdown
                //     - Definitions/Defaults:
                //         - `ITEM_INDENT` = 4
                //         - `CODE_INDENT` = 8
                //         - `current indent` = line indent
                //     - Start List Conditions:
                //         - `current indent` < `ITEM_INDENT`: new list with new item
                //     - Continuation Conditions:
                //          - `current indent` >= `CODE_INDENT`: item content
                //          - `current indent` >= `ITEM_INDENT`: sub-item or content
                //          - otherwise: list item or not ours

                int currentIndent = state.getIndent();

                // advance by item indent
                int newColumn = state.getColumn() + itemIndent;

                if (currentIndent >= myOptions.getCodeIndent()) {
                    // our indented code child but if it starts with an item prefix and parsing this list's paragraph item then this is a lazy continuation
                    if (myBlock.getFirstChild() != null && myBlock.getFirstChild() == myBlock.getLastChild()) {
                        BlockParser matched = state.getActiveBlockParser();
                        if (matched.isParagraphParser() && matched.getBlock() == myBlock.getFirstChild()) {
                            // just a lazy continuation of us
                            listBlockParser.setItemHandledLineSkipActive(state.getLine());
                            return continueAtColumn(newColumn);
                        }
                    }

                    listBlockParser.setItemHandledLine(state.getLine());
                    return continueAtColumn(newColumn);
                } else {
                    ListBlockParser.ListData listData = ListBlockParser.parseListMarker(myOptions, -1, state);

                    if (currentIndent >= itemIndent) {
                        if (listData != null) {
                            BlockParser matched = state.getActiveBlockParser();
                            boolean inParagraph = matched.isParagraphParser();
                            boolean inParagraphListItem = inParagraph && matched.getBlock().getParent() instanceof ListItem && matched.getBlock() == matched.getBlock().getParent().getFirstChild();

                            if (inParagraphListItem && (!myOptions.canInterrupt(listData.listBlock, listData.isEmpty, true)
                                    || !myOptions.canStartSubList(listData.listBlock, listData.isEmpty))) {
                                // just a lazy continuation of us
                                listBlockParser.setItemHandledLineSkipActive(state.getLine());
                                return continueAtColumn(state.getColumn() + currentIndent);
                            } else {
                                // our sub list item
                                listBlockParser.setItemHandledNewListLine(state.getLine());
                                return continueAtColumn(newColumn);
                            }
                        } else {
                            // our child item, other than a list item, if we are empty then no such thing
                            if (myIsEmpty) {
                                listBlockParser.setItemHandledLine(state.getLine());
                                return BlockContinue.none();
                            } else {
                                listBlockParser.setItemHandledLine(state.getLine());
                                return continueAtColumn(newColumn);
                            }
                        }
                    } else if (listData != null) {
                        if (!myHadBlankLine && !myOptions.canInterrupt(listData.listBlock, listData.isEmpty, true)) {
                            // our text or lazy continuation
                            listBlockParser.setItemHandledLine(state.getLine());
                            return continueAtColumn(state.getColumn() + currentIndent);
                        } else {
                            // here have to see if the item is really a mismatch and we sub-list mismatches
                            boolean overrideSubList = myOptions.isItemTypeMismatchToNewList() && myOptions.isItemTypeMismatchToSubList() && myHadBlankLine;
                            if (!overrideSubList && myOptions.startSubList(listBlockParser.getBlock(), listData.listBlock)) {
                                // we keep it as our sub-item
                                listBlockParser.setItemHandledNewListLine(state.getLine());
                                return continueAtColumn(state.getColumn() + currentIndent);
                            } else {
                                if (myOptions.startNewList(listBlockParser.getBlock(), listData.listBlock)) {
                                    // a new list
                                    listBlockParser.setItemHandledNewListLine(state.getLine());
                                    return BlockContinue.none();
                                } else {
                                    // the next line in the list
                                    listBlockParser.setItemHandledNewItemLine(state.getLine());
                                    return BlockContinue.none();
                                }
                            }
                        }
                    }
                }
            } else {
                final int markerIndent = listBlockParser.getListData().markerIndent;
                if (emulationFamily == KRAMDOWN) {
                    // - Kramdown:
                    //     - Definitions/Defaults:
                    //         - `ITEM_INDENT` = 4
                    //         - `CODE_INDENT` = 8
                    //         - `current indent` = `line indent`
                    //     - Start List Conditions:
                    //         - `current indent` < `ITEM_INDENT`: new list with new item
                    //     - Continuation Conditions:
                    //         - `current indent` >=  `item content indent`: sub-item or content
                    //         - `current indent` >= `list indent` + `ITEM_INDENT`
                    //              - hadBlankLine: end current item, keep loose status, item content
                    //              - !hadBlankLine: lazy continuation
                    //         - `current indent` >= `list indent` + `CODE_INDENT`: item content
                    //         - `current indent` >= `list indent`: list item or not ours

                    int currentIndent = state.getIndent();
                    int listIndent = markerIndent;
                    int newColumn = state.getColumn() + contentIndent;

                    ListBlockParser.ListData listData = ListBlockParser.parseListMarker(myOptions, -1, state);

                    if (currentIndent >= contentIndent) {
                        // our sub item
                        if (listData != null) {
                            BlockParser matched = state.getActiveBlockParser();
                            boolean inParagraph = matched.isParagraphParser();
                            boolean inParagraphListItem = inParagraph && matched.getBlock().getParent() instanceof ListItem && matched.getBlock() == matched.getBlock().getParent().getFirstChild();

                            if (inParagraphListItem
                                    && (!myOptions.canInterrupt(listData.listBlock, listData.isEmpty, true)
                                    || !myOptions.canStartSubList(listData.listBlock, listData.isEmpty))) {
                                // just a lazy continuation of us
                                listBlockParser.setItemHandledLineSkipActive(state.getLine());
                                return continueAtColumn(newColumn);
                            } else {
                                // our sub list item
                                listBlockParser.setItemHandledNewListLine(state.getLine());
                                return continueAtColumn(newColumn);
                            }
                        } else {
                            // our child item, other than a list item, if we are empty then no such thing
                            if (myIsEmpty) {
                                listBlockParser.setItemHandledLine(state.getLine());
                                return BlockContinue.none();
                            } else {
                                listBlockParser.setItemHandledLine(state.getLine());
                                return continueAtColumn(newColumn);
                            }
                        }
                    } else {
                        if (currentIndent >= listIndent + itemIndent) {
                            if (myHadBlankLine) {
                                // indented code, interrupts item but keeps loose status
                                if (myBlock.isHadBlankAfterItemParagraph()) myBlock.setLoose(true);
                                listBlockParser.setItemHandledLineSkipActive(state.getLine());
                                return BlockContinue.none();
                            } else {
                                // our text or lazy continuation
                                listBlockParser.setItemHandledLineSkipActive(state.getLine());
                                return continueAtColumn(state.getColumn() + currentIndent);
                            }
                        } else if (listData != null) {
                            if (currentIndent >= listIndent) {
                                // here have to see if the item is really a mismatch and we sub-list mismatches
                                boolean overrideSubList = myOptions.isItemTypeMismatchToNewList() && myOptions.isItemTypeMismatchToSubList() && myHadBlankLine;
                                if (!overrideSubList && myOptions.startSubList(listBlockParser.getBlock(), listData.listBlock)) {
                                    // we keep it as our sub-item
                                    listBlockParser.setItemHandledNewListLine(state.getLine());
                                    return continueAtColumn(state.getColumn() + currentIndent);
                                } else {
                                    if (myOptions.startNewList(listBlockParser.getBlock(), listData.listBlock)) {
                                        // a new list
                                        listBlockParser.setItemHandledNewListLine(state.getLine());
                                        return BlockContinue.none();
                                    } else {
                                        // the next line in the list
                                        listBlockParser.setItemHandledNewItemLine(state.getLine());
                                        return BlockContinue.none();
                                    }
                                }
                            }
                        }
                    }
                } else if (emulationProfile == GITHUB_DOC) {
                    // - Markdown:
                    //     - Definitions/Defaults:
                    //         - `ITEM_INDENT` = 4
                    //         - `CODE_INDENT` = 8
                    //         - `current indent` = `line indent`
                    //     - Start List Conditions:
                    //         - `current indent` < `ITEM_INDENT`: new list with new item
                    //     - Continuation Conditions:
                    //         - `current indent` >= `CODE_INDENT`: item content
                    //         - `current indent` > `ITEM_INDENT`: sub-item or content
                    //         - `current indent` > `list indent`: sub-item or content
                    //         - otherwise: list item or not ours

                    int currentIndent = state.getIndent();
                    int currentIndex = state.getIndex() + currentIndent;
                    int listIndent = markerIndent;
                    final int contentIndentRemoval = Utils.maxLimit(currentIndent, contentIndent, listIndent + 4);

                    if (currentIndent >= myOptions.getCodeIndent()) {
                        // this could be indented code or our lazy continuation
                        listBlockParser.setItemHandledLine(state.getLine());
                        return continueAtColumn(state.getColumn() + Utils.maxLimit(contentIndent, itemIndent));
                    } else {
                        ListBlockParser.ListData listData = ListBlockParser.parseListMarker(myOptions, -1, state);

                        if (currentIndent > itemIndent) {
                            if (listData != null) {
                                // our sub item
                                BlockParser matched = state.getActiveBlockParser();
                                boolean inParagraph = matched.isParagraphParser();
                                boolean inParagraphListItem = inParagraph && matched.getBlock().getParent() instanceof ListItem && matched.getBlock() == matched.getBlock().getParent().getFirstChild();

                                if (inParagraphListItem
                                        && (!myOptions.canInterrupt(listData.listBlock, listData.isEmpty, true)
                                        || !myOptions.canStartSubList(listData.listBlock, listData.isEmpty))) {
                                    // just a lazy continuation of us
                                    listBlockParser.setItemHandledLineSkipActive(state.getLine());
                                    return continueAtColumn(state.getColumn() + currentIndent);
                                } else {
                                    // our sub list item
                                    listBlockParser.setItemHandledNewListLine(state.getLine());
                                    return continueAtColumn(state.getColumn() + contentIndentRemoval);
                                }
                            } else {
                                // our content
                                listBlockParser.setItemHandledLine(state.getLine());
                                return continueAtColumn(state.getColumn() + itemIndent);
                            }
                        } else {
                            if (currentIndent > listIndent) {
                                if (listData != null) {
                                    // our sublist
                                    BlockParser matched = state.getActiveBlockParser();
                                    boolean inParagraph = matched.isParagraphParser();
                                    boolean inParagraphListItem = inParagraph && matched.getBlock().getParent() instanceof ListItem && matched.getBlock() == matched.getBlock().getParent().getFirstChild();

                                    if (inParagraphListItem
                                            && (!myOptions.canInterrupt(listData.listBlock, listData.isEmpty, true)
                                            || !myOptions.canStartSubList(listData.listBlock, listData.isEmpty))) {
                                        // just a lazy continuation of us
                                        listBlockParser.setItemHandledLineSkipActive(state.getLine());
                                        return continueAtColumn(state.getColumn() + currentIndent);
                                    } else {
                                        // our sub list item
                                        listBlockParser.setItemHandledNewListLine(state.getLine());
                                        return continueAtColumn(state.getColumn() + contentIndentRemoval);
                                    }
                                } else {
                                    // our content
                                    listBlockParser.setItemHandledLine(state.getLine());
                                    return continueAtColumn(state.getColumn() + contentIndentRemoval);
                                }
                            } else {
                                if (listData != null) {
                                    //here have to see if the item is really a mismatch and we sub-list mismatches
                                    //the next line in the list
                                    boolean overrideSubList = myOptions.isItemTypeMismatchToNewList() && myOptions.isItemTypeMismatchToSubList() && myHadBlankLine;
                                    if (!overrideSubList && myOptions.startSubList(listBlockParser.getBlock(), listData.listBlock)) {
                                        // we keep it as our sub-item
                                        listBlockParser.setItemHandledNewListLine(state.getLine());
                                        return continueAtColumn(state.getColumn() + contentIndentRemoval);
                                    } else {
                                        if (myOptions.startNewList(listBlockParser.getBlock(), listData.listBlock)) {
                                            // a new list
                                            listBlockParser.setItemHandledNewListLine(state.getLine());
                                            return BlockContinue.none();
                                        } else {
                                            BlockParser matched = state.getActiveBlockParser();
                                            boolean inParagraph = matched.isParagraphParser();
                                            boolean inParagraphListItem = inParagraph && matched.getBlock().getParent() instanceof ListItem && matched.getBlock() == matched.getBlock().getParent().getFirstChild();

                                            if (inParagraphListItem
                                                    && (!myOptions.canInterrupt(listData.listBlock, listData.isEmpty, true)
                                                    || !myOptions.canStartSubList(listData.listBlock, listData.isEmpty))) {
                                                // just a lazy continuation of us
                                                listBlockParser.setItemHandledLineSkipActive(state.getLine());
                                                return continueAtColumn(state.getColumn() + currentIndent);
                                            } else {
                                                // the next line in the list
                                                listBlockParser.setItemHandledNewItemLine(state.getLine());
                                                return BlockContinue.none();
                                            }
                                        }
                                    }
                                } else if (!myHadBlankLine || state.getActiveBlockParser() instanceof FencedCodeBlockParser) {
                                    // our lazy continuation or a new element
                                    // Issue #66, if fenced code follows then need to interrupt the list
                                    listBlockParser.setItemHandledLine(state.getLine());
                                    return continueAtColumn(state.getColumn() + currentIndent);
                                }
                            }
                        }
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
                    //         - `current indent` >= `CODE_INDENT`: item content
                    //         - `current indent` > `ITEM_INDENT`: sub-item or content
                    //         - `current indent` > `list indent`: sub-item or content
                    //         - otherwise: list item or not ours

                    int currentIndent = state.getIndent();

                    if (currentIndent >= myOptions.getCodeIndent()) {
                        // this could be indented code or our lazy continuation
                        listBlockParser.setItemHandledLine(state.getLine());
                        return continueAtColumn(state.getColumn() + itemIndent);
                    } else {
                        ListBlockParser.ListData listData = ListBlockParser.parseListMarker(myOptions, -1, state);

                        if (currentIndent > itemIndent) {
                            if (listData != null) {
                                // our sub item
                                BlockParser matched = state.getActiveBlockParser();
                                boolean inParagraph = matched.isParagraphParser();
                                boolean inParagraphListItem = inParagraph && matched.getBlock().getParent() instanceof ListItem && matched.getBlock() == matched.getBlock().getParent().getFirstChild();

                                if (inParagraphListItem
                                        && (!myOptions.canInterrupt(listData.listBlock, listData.isEmpty, true)
                                        || !myOptions.canStartSubList(listData.listBlock, listData.isEmpty))) {
                                    // just a lazy continuation of us
                                    listBlockParser.setItemHandledLineSkipActive(state.getLine());
                                    return continueAtColumn(state.getColumn() + currentIndent);
                                } else {
                                    // our sub list item
                                    listBlockParser.setItemHandledNewListLine(state.getLine());
                                    return continueAtColumn(state.getColumn() + itemIndent);
                                }
                            } else {
                                // our content
                                listBlockParser.setItemHandledLine(state.getLine());
                                return continueAtColumn(state.getColumn() + itemIndent);
                            }
                        } else {
                            int listIndent = markerIndent;
                            if (currentIndent > listIndent) {
                                if (listData != null) {
                                    // our sublist
                                    BlockParser matched = state.getActiveBlockParser();
                                    boolean inParagraph = matched.isParagraphParser();
                                    boolean inParagraphListItem = inParagraph && matched.getBlock().getParent() instanceof ListItem && matched.getBlock() == matched.getBlock().getParent().getFirstChild();

                                    if (inParagraphListItem
                                            && (!myOptions.canInterrupt(listData.listBlock, listData.isEmpty, true)
                                            || !myOptions.canStartSubList(listData.listBlock, listData.isEmpty))) {
                                        // just a lazy continuation of us
                                        listBlockParser.setItemHandledLineSkipActive(state.getLine());
                                        return continueAtColumn(state.getColumn() + currentIndent);
                                    } else {
                                        // our sub list item
                                        listBlockParser.setItemHandledNewListLine(state.getLine());
                                        return continueAtColumn(state.getColumn() + currentIndent);
                                    }
                                } else {
                                    // our content
                                    listBlockParser.setItemHandledLine(state.getLine());
                                    return continueAtColumn(state.getColumn() + currentIndent);
                                }
                            } else if (listData != null) {
                                //here have to see if the item is really a mismatch and we sub-list mismatches
                                //the next line in the list
                                boolean overrideSubList = myOptions.isItemTypeMismatchToNewList() && myOptions.isItemTypeMismatchToSubList() && myHadBlankLine;
                                if (!overrideSubList && myOptions.startSubList(listBlockParser.getBlock(), listData.listBlock)) {
                                    // we keep it as our sub-item
                                    listBlockParser.setItemHandledNewListLine(state.getLine());
                                    return continueAtColumn(state.getColumn() + currentIndent);
                                } else {
                                    if (myOptions.startNewList(listBlockParser.getBlock(), listData.listBlock)) {
                                        // a new list
                                        listBlockParser.setItemHandledNewListLine(state.getLine());
                                        return BlockContinue.none();
                                    } else {
                                        BlockParser matched = state.getActiveBlockParser();
                                        boolean inParagraph = matched.isParagraphParser();
                                        boolean inParagraphListItem = inParagraph && matched.getBlock().getParent() instanceof ListItem && matched.getBlock() == matched.getBlock().getParent().getFirstChild();

                                        if (inParagraphListItem
                                                && (!myOptions.canInterrupt(listData.listBlock, listData.isEmpty, true)
                                                || !myOptions.canStartSubList(listData.listBlock, listData.isEmpty))) {
                                            // just a lazy continuation of us
                                            listBlockParser.setItemHandledLineSkipActive(state.getLine());
                                            return continueAtColumn(state.getColumn() + currentIndent);
                                        } else {
                                            // the next line in the list
                                            listBlockParser.setItemHandledNewItemLine(state.getLine());
                                            return BlockContinue.none();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return BlockContinue.none();
    }
}
