package com.vladsch.flexmark.parser.block;

import com.vladsch.flexmark.internal.BlockContent;
import com.vladsch.flexmark.internal.util.collection.MutableDataHolder;
import com.vladsch.flexmark.internal.util.collection.MutableDataSet;
import com.vladsch.flexmark.internal.util.sequence.BasedSequence;
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
    public boolean isParagraphParser() {
        return false;
    }

    @Override
    public BlockContent getBlockContent() {
        return null;
    }

    @Override
    public void addLine(ParserState state, BasedSequence line) {
    }

    @Override
    public void parseInlines(InlineParser inlineParser) {
    }

    @Override
    public boolean breakOutOnDoubleBlankLine() {
        return false;
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
