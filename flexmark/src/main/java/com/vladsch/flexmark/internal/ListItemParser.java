package com.vladsch.flexmark.internal;

import com.vladsch.flexmark.internal.util.Parsing;
import com.vladsch.flexmark.internal.util.sequence.BasedSequence;
import com.vladsch.flexmark.node.Block;
import com.vladsch.flexmark.node.BulletListItem;
import com.vladsch.flexmark.node.ListItem;
import com.vladsch.flexmark.node.OrderedListItem;
import com.vladsch.flexmark.parser.block.AbstractBlockParser;
import com.vladsch.flexmark.parser.block.BlockContinue;
import com.vladsch.flexmark.parser.block.BlockParser;
import com.vladsch.flexmark.parser.block.ParserState;

public class ListItemParser extends AbstractBlockParser {

    private final ListItem block;

    /**
     * Minimum number of columns that the content has to be indented (relative to the containing block) to be part of
     * this list item.
     */
    final private ListOptions options;
    final private boolean itemInterruptsItemParagraph;
    private int contentIndent;
    private boolean hadBlankLine = false;
    final private boolean mismatchedItemToSubItem;
    final private Parsing myParsing;

    public ListItemParser(ListOptions options, Parsing parsing, int contentIndent, BasedSequence marker, boolean isNumberedList) {
        this.options = options;

        this.contentIndent = contentIndent;
        if (isNumberedList) {
            block = new OrderedListItem();
            itemInterruptsItemParagraph = this.options.orderedItemInterruptsParagraph || this.options.orderedItemInterruptsItemParagraph;
        } else {
            block = new BulletListItem();
            itemInterruptsItemParagraph = this.options.bulletItemInterruptsParagraph || this.options.bulletItemInterruptsItemParagraph;
        }

        mismatchedItemToSubItem = this.options.itemTypeMatch && this.options.itemMismatchToSubitem;
        myParsing = parsing;

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
    public void closeBlock(ParserState parserState) {
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
