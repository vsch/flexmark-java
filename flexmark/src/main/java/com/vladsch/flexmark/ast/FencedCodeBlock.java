package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.List;

public class FencedCodeBlock extends Block implements DoNotDecorate {
    private int fenceIndent;
    private BasedSequence openingMarker = BasedSequence.NULL;
    private BasedSequence info = BasedSequence.NULL;
    private BasedSequence closingMarker = BasedSequence.NULL;

    @Override
    public void getAstExtra(StringBuilder out) {
        BasedSequence content = getContentChars();
        int lines = getContentLines().size();
        segmentSpanChars(out, openingMarker, "open");
        segmentSpanChars(out, info, "info");
        segmentSpan(out, content, "content");
        out.append(" lines[").append(lines).append("]");
        segmentSpanChars(out, closingMarker, "close");
    }

    @Override
    public BasedSequence[] getSegments() {
        return new BasedSequence[] { openingMarker, info, getContentChars(), closingMarker };
    }

    public FencedCodeBlock() {
    }

    public FencedCodeBlock(BasedSequence chars) {
        super(chars);
    }

    public FencedCodeBlock(BasedSequence chars, BasedSequence openingMarker, BasedSequence info, List<BasedSequence> segments, BasedSequence closingMarker) {
        super(chars, segments);
        this.openingMarker = openingMarker;
        this.info = info;
        this.closingMarker = closingMarker;
    }

    public BasedSequence getOpeningMarker() {
        return openingMarker;
    }

    public void setOpeningMarker(BasedSequence openingMarker) {
        this.openingMarker = openingMarker;
    }

    public void setInfo(BasedSequence info) {
        this.info = info;
    }

    public BasedSequence getClosingMarker() {
        return closingMarker;
    }

    public void setClosingMarker(BasedSequence closingMarker) {
        this.closingMarker = closingMarker;
    }

    public BasedSequence getOpeningFence() {
        return this.openingMarker;
    }

    /**
     * @see <a href="http://spec.commonmark.org/0.18/#info-string">CommonMark spec</a>
     *
     * @return the sequence for the info part of the node
     */
    public BasedSequence getInfo() {
        return info;
    }

    public BasedSequence getClosingFence() {
        return this.closingMarker;
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
