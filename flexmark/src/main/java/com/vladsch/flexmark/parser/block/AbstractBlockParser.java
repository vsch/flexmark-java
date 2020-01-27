package com.vladsch.flexmark.parser.block;

import com.vladsch.flexmark.parser.InlineParser;
import com.vladsch.flexmark.util.ast.BlankLine;
import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.ast.BlockContent;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
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
    public boolean isInterruptible() {
        return false;
    }

    @Override
    public boolean isRawText() {
        return false;
    }

    @Override
    public boolean canContain(ParserState state, BlockParser blockParser, Block block) {
        return false;
    }

    @Override
    public boolean isParagraphParser() {
        return false;
    }

    /**
     * should be overridden in BlockQuote, FencedCode and ListItem
     *
     * @param lastMatchedBlockParser the last matched block parser instance
     * @return true if the blank line should be propagated to parent
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
    public boolean canInterruptBy(BlockParserFactory blockParserFactory) {
        return true;
    }

    @Override
    public MutableDataHolder getDataHolder() {
        if (mutableData == null) {
            mutableData = new MutableDataSet();
        }
        return mutableData;
    }

    public void removeBlankLines() {
        // need to remove blank lines, these were used to extend block quote chars to include blank lines
        Node child = getBlock().getFirstChild();

        while (child != null) {
            Node next = child.getNext();
            if (child instanceof BlankLine) {
                child.unlink();
            }
            child = next;
        }
    }
}
