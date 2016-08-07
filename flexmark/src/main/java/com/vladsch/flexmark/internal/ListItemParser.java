package com.vladsch.flexmark.internal;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.ast.util.Parsing;
import com.vladsch.flexmark.parser.block.AbstractBlockParser;
import com.vladsch.flexmark.parser.block.BlockContinue;
import com.vladsch.flexmark.parser.block.BlockParser;
import com.vladsch.flexmark.parser.block.ParserState;
import com.vladsch.flexmark.util.sequence.BasedSequence;

public class ListItemParser extends AbstractBlockParser {

    private final ListItem block;

    /**
     * Minimum number of columns that the content has to be indented (relative to the containing block) to be part of
     * this list item.
     */
    final private ListOptions options;
    final private boolean itemInterruptsItemParagraph;
    private int markerColumn;
    private int contentColumn;
    private int contentIndent;
    private boolean hadBlankLine = false;
    final private boolean mismatchedItemToSubItem;
    final private Parsing myParsing;

    public ListItemParser(ListOptions options, Parsing parsing, int markerColumn, int contentColumn, int contentIndent, BasedSequence marker, boolean isNumberedList) {
        this.options = options;

        this.markerColumn = markerColumn;
        this.contentColumn = contentColumn;

        this.contentIndent = contentIndent;
        mismatchedItemToSubItem = this.options.itemTypeMatch && this.options.itemMismatchToSubItem;
        myParsing = parsing;

        if (isNumberedList) {
            block = new OrderedListItem();
            itemInterruptsItemParagraph = this.options.orderedItemInterruptsParagraph || this.options.orderedItemInterruptsItemParagraph;
        } else {
            block = new BulletListItem();
            itemInterruptsItemParagraph = this.options.bulletItemInterruptsParagraph || this.options.bulletItemInterruptsItemParagraph;
        }

        block.setOpeningMarker(marker);
    }

    @Override
    public boolean isContainer() {
        return true;
    }

    @Override
    public boolean canContain(Block block) {
        return true;
    }

    @Override
    public boolean isPropagatingLastBlankLine(BlockParser lastMatchedBlockParser) {
        return !(block.getFirstChild() == null && this != lastMatchedBlockParser);
    }

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public void closeBlock(ParserState state) {
        block.setCharsFromContent();
    }

    @Override
    public BlockContinue tryContinue(ParserState state) {
        if (state.isBlank()) {
            if (block.getFirstChild() == null) {
                // Blank line after empty list item
                return BlockContinue.none();
            } else {
                hadBlankLine = true;
                return BlockContinue.atIndex(state.getNextNonSpaceIndex());
            }
        }

        int nonBlankColumn = state.getColumn() + state.getIndent();
        if (nonBlankColumn < contentColumn && options.useListContentIndent && nonBlankColumn > markerColumn) {
            // if we are the in the first list, we take it as our sublist, otherwise we let the list handle it
            boolean previousListsItem = false;

            if ((block.getParent() instanceof ListBlock)) {
                if (!options.overIndentsToFirstItem) {
                    ListBlockParser parentListParser = (ListBlockParser) state.getActiveBlockParser(block.getParent());
                    if (parentListParser != null && nonBlankColumn >= parentListParser.markerColumn && nonBlankColumn < parentListParser.itemContentColumn + options.listContentIndentOffset) {
                        previousListsItem = true;
                    }
                } else {
                    if (block.getParent().getParent() instanceof ListItem) {
                        ListBlockParser parentListParser = (ListBlockParser) state.getActiveBlockParser(block.getParent());
                        if (parentListParser != null && nonBlankColumn >= parentListParser.markerColumn && nonBlankColumn < parentListParser.itemContentColumn + options.listContentIndentOffset) {
                            previousListsItem = true;
                        }
                        //ListBlockParser parentListParser = (ListBlockParser) state.getActiveBlockParser(block.getParent().getParent().getParent());
                        //if (parentListParser != null && nonBlankColumn >= parentListParser.markerColumn && nonBlankColumn < parentListParser.itemContentColumn + options.listContentIndentOffset) {
                        //}
                    }
                }
            }

            if (!previousListsItem) return BlockContinue.atColumn(state.getColumn());
        }

        if (state.getIndent() >= contentIndent) {
            return BlockContinue.atColumn(state.getColumn() + contentIndent);
        } else if (!hadBlankLine && !itemInterruptsItemParagraph) {
            return BlockContinue.atIndex(state.getIndent());
        } else {
            // here have to see if the item is really a mismatch and we sub-list mismatches
            if (mismatchedItemToSubItem) {
                Boolean isOrderedListItem = ListBlockParser.isOrderedListMarker(state.getLine(), myParsing, state.getNextNonSpaceIndex());
                if (isOrderedListItem != null) {
                    if (isOrderedListItem != this.block instanceof OrderedListItem) {
                        // we keep it as our sub-item
                        return BlockContinue.atIndex(state.getIndent());
                    }
                }
            }

            return BlockContinue.none();
        }
    }
}
