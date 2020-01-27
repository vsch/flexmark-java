package com.vladsch.flexmark.parser.internal;

import com.vladsch.flexmark.parser.block.BlockContinue;

public class BlockContinueImpl extends BlockContinue {

    final private int newIndex;
    final private int newColumn;
    final private boolean finalize;

    public BlockContinueImpl(int newIndex, int newColumn, boolean finalize) {
        this.newIndex = newIndex;
        this.newColumn = newColumn;
        this.finalize = finalize;
    }

    public int getNewIndex() {
        return newIndex;
    }

    public int getNewColumn() {
        return newColumn;
    }

    public boolean isFinalize() {
        return finalize;
    }
}
