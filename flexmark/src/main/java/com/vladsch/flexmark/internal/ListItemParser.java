package com.vladsch.flexmark.internal;

import com.vladsch.flexmark.internal.util.BasedSequence;
import com.vladsch.flexmark.node.Block;
import com.vladsch.flexmark.node.BulletListItem;
import com.vladsch.flexmark.node.ListItem;
import com.vladsch.flexmark.node.OrderedListItem;
import com.vladsch.flexmark.parser.block.AbstractBlockParser;
import com.vladsch.flexmark.parser.block.BlockContinue;
import com.vladsch.flexmark.parser.block.ParserState;

public class ListItemParser extends AbstractBlockParser {

    private final ListItem block;

    /**
     * Minimum number of columns that the content has to be indented (relative to the containing block) to be part of
     * this list item.
     */
    private int contentIndent;

    public ListItemParser(int contentIndent, BasedSequence marker, boolean isNumberedList) {
        this.contentIndent = contentIndent;
        if (isNumberedList) {
            block = new OrderedListItem();
        } else {
            block = new BulletListItem();
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
                return BlockContinue.atIndex(state.getNextNonSpaceIndex());
            }
        }

        if (state.getIndent() >= contentIndent) {
            return BlockContinue.atColumn(state.getColumn() + contentIndent);
        } else {
            return BlockContinue.none();
        }
    }
}
