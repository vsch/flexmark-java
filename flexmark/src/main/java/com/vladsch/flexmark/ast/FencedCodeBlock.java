package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.ast.DoNotDecorate;
import com.vladsch.flexmark.util.misc.CharPredicate;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FencedCodeBlock extends Block implements DoNotDecorate {
    private int fenceIndent;
    private BasedSequence openingMarker = BasedSequence.NULL;
    private BasedSequence info = BasedSequence.NULL;
    private BasedSequence attributes = BasedSequence.NULL;
    private BasedSequence closingMarker = BasedSequence.NULL;

    @Override
    public void getAstExtra(@NotNull StringBuilder out) {
        BasedSequence content = getContentChars();
        int lines = getContentLines().size();
        segmentSpanChars(out, openingMarker, "open");
        segmentSpanChars(out, info, "info");
        segmentSpanChars(out, attributes, "attributes");
        segmentSpan(out, content, "content");
        out.append(" lines[").append(lines).append("]");
        segmentSpanChars(out, closingMarker, "close");
    }

    @NotNull
    @Override
    public BasedSequence[] getSegments() {
        return new BasedSequence[] { openingMarker, info, attributes, getContentChars(), closingMarker };
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

    public BasedSequence getAttributes() {
        return attributes;
    }

    public void setAttributes(BasedSequence attributes) {
        this.attributes = attributes;
    }

    public BasedSequence getInfoDelimitedByAny(CharPredicate delimiters) {
        BasedSequence language = BasedSequence.NULL;
        if (info.isNotNull() && !info.isBlank()) {
            int delimiter = info.indexOfAny(delimiters);
            if (delimiter == -1) {
                language = info;
            } else {
                language = info.subSequence(0, delimiter);
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
