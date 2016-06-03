package org.commonmark.node;

import org.commonmark.internal.BlockContent;
import org.commonmark.internal.util.BasedSequence;

import java.util.List;

public class FencedCodeBlock extends Block {
    private int fenceIndent;

    public FencedCodeBlock() {
    }

    public FencedCodeBlock(BasedSequence chars) {
        super(chars);
    }

    public FencedCodeBlock(BasedSequence chars, List<BasedSequence> segments) {
        super(chars, segments);
    }

    public FencedCodeBlock(BlockContent blockContent) {
        super(blockContent);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public BasedSequence getOpeningFence() {
        return getSegmentChars(0);
    }

    /**
     * @see <a href="http://spec.commonmark.org/0.18/#info-string">CommonMark spec</a>
     */
    public BasedSequence getInfo() {
        return getSegmentChars(1).trim();
    }

    @Override
    public BasedSequence getContentChars() {
        return getContentChars(1, segments.size());
    }

    public BasedSequence getClosingFence() {
        return getSegmentChars(segments.size());
    }

    public int getFenceLength() {
        return getInfo().length();
    }

    public int getFenceIndent() {
        return fenceIndent;
    }

    public void setFenceIndent(int fenceIndent) {
        this.fenceIndent = fenceIndent;
    }
}
