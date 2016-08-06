package com.vladsch.flexmark.parser.block;

import com.vladsch.flexmark.ast.Block;
import com.vladsch.flexmark.ast.BlockContent;
import com.vladsch.flexmark.parser.InlineParser;
import com.vladsch.flexmark.util.options.MutableDataHolder;
import com.vladsch.flexmark.util.options.MutableDataSet;
import com.vladsch.flexmark.util.sequence.BasedSequence;

public abstract class AbstractBlockParser implements BlockParser {
    private MutableDataSet mutableData = null;
    private boolean isClosed = false;

    @Override
    public boolean isClosed() {
        return isClosed;
    }

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

    /**
     * should be overridden in BlockQuote, FencedCode and ListItem
     * @return true if the blank line should be propagated to parent
     * @param lastMatchedBlockParser
     */
    @Override
    public boolean isPropagatingLastBlankLine(BlockParser lastMatchedBlockParser) {
        return true;
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
        isClosed = true;
    }

    @Override
    public MutableDataHolder getDataHolder() {
        if (mutableData == null) {
            mutableData = new MutableDataSet();
        }
        return mutableData;
    }
}
