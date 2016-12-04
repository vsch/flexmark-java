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
    private final int markerColumn;
    private final int markerIndent;
    private final int contentOffset;
    private final int contentIndent;
    private final int contentColumn;
    private boolean hadBlankLine = false;
    final private boolean mismatchedItemToSubItem;
    final private Parsing myParsing;

    public ListItemParser(ListOptions options, Parsing parsing, int markerColumn, int markerIndent, int contentOffset, BasedSequence marker, boolean isNumberedList) {
        this.options = options;

        this.markerColumn = markerColumn;
        this.markerIndent = markerIndent;
        this.contentOffset = contentOffset;
        this.contentIndent = markerIndent + contentOffset;
        this.contentColumn = markerColumn + contentOffset;

        mismatchedItemToSubItem = this.options.itemMismatchToSubItem;
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

        assert block.getParent() instanceof ListBlock;
        ListBlockParser parentListParser = (ListBlockParser) state.getActiveBlockParser(block.getParent());
        boolean previousListsItem = false;

        int indent = state.getIndent();
        if (options.fixedIndent <= 0
                && indent < contentIndent
                && (options.firstItemIndentBasedLimit || options.itemIndentOverMarkerToList)
                && indent >= parentListParser.itemMarkerIndent) {
            // this line is indented more than this item, but not enough to be content of this item, see if it is that

            if (!options.itemIndentOverMarkerToList) {
                if (indent >= parentListParser.itemMarkerIndent && indent < parentListParser.itemMarkerIndent + options.firstItemIndentBasedLimitOffset) {
                    previousListsItem = true;
                }
            } else {
                if (indent >= parentListParser.itemMarkerIndent) {
                    if (!options.firstItemIndentBasedLimit || indent < parentListParser.itemMarkerIndent + options.firstItemIndentBasedLimitOffset) {
                        previousListsItem = true;
                    } else {
                        // it is lazy continuation of this item, let the ListBlockParser pass it through, eat up the indent.
                        // it is a lazy continuation after all
                        parentListParser.setPassThroughLine(state.getLine());
                        return BlockContinue.none(); //state.getColumn() + indent);
                    }
                }
            }

            if (!previousListsItem) return BlockContinue.none();
        }

        if (indent >= contentIndent) {
            return BlockContinue.atColumn(state.getColumn() + contentIndent);
        } else if (!hadBlankLine && !itemInterruptsItemParagraph) {
            return BlockContinue.atColumn(state.getColumn() + indent);
        } else if (indent >= markerIndent) {
            // here have to see if the item is really a mismatch and we sub-list mismatches
            if (mismatchedItemToSubItem) {
                Boolean isOrderedListItem = ListBlockParser.isOrderedListMarker(state.getLine(), myParsing, state.getNextNonSpaceIndex());
                if (isOrderedListItem != null) {
                    if (isOrderedListItem != (this.block instanceof OrderedListItem)) {
                        // we keep it as our sub-item
                        return BlockContinue.atColumn(state.getColumn() + indent);
                    }
                }
            }
        }

        if (previousListsItem) {
            parentListParser.setLastItemIndent(markerIndent);
            return BlockContinue.none();
        }

        return BlockContinue.none();
    }
}
