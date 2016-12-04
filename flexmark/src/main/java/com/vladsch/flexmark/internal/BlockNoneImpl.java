package com.vladsch.flexmark.internal;

import com.vladsch.flexmark.parser.block.BlockContinue;

public class BlockNoneImpl extends BlockContinue {

    private final int newIndex;
    private final int newColumn;

    public BlockNoneImpl(int newIndex, int newColumn) {
        this.newIndex = newIndex;
        this.newColumn = newColumn;
    }

    public int getNewIndex() {
        return newIndex;
    }

    public int getNewColumn() {
        return newColumn;
    }
}
