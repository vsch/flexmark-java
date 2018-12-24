package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.ast.DoNotDecorate;
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
     * @return the sequence for the info part of the node
     * @see <a href="http://spec.commonmark.org/0.18/#info-string">CommonMark spec</a>
     */
    public BasedSequence getInfo() {
        return info;
    }

    public BasedSequence getInfoDelimitedByAny(CharSequence delimiters) {
        BasedSequence language = BasedSequence.NULL;
        if (info.isNotNull() && !info.isBlank()) {
            int space = info.indexOfAny(delimiters);
            if (space == -1) {
                language = info;
            } else {
                language = info.subSequence(0, space);
            }
        }
        return language;
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
