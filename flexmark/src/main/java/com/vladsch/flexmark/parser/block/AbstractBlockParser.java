package com.vladsch.flexmark.parser.block;

import com.vladsch.flexmark.internal.util.BasedSequence;
import com.vladsch.flexmark.internal.util.MutableDataHolder;
import com.vladsch.flexmark.internal.util.MutableDataSet;
import com.vladsch.flexmark.node.Block;
import com.vladsch.flexmark.parser.InlineParser;

public abstract class AbstractBlockParser implements BlockParser {
    private MutableDataSet mutableData = null;
    
    @Override
    public boolean isContainer() {
        return false;
    }

    @Override
    public boolean canContain(Block block) {
        return false;
    }

    @Override
    public void addLine(BasedSequence line, int eolLength) {
    }

    @Override
    public void parseInlines(InlineParser inlineParser) {
    }

    @Override
    final public void finalizeClosedBlock() {
        mutableData = null;
    }

    @Override
    public MutableDataHolder getDataHolder() {
        if (mutableData == null) {
            mutableData = new MutableDataSet();
        }
        return mutableData;
    }
}
