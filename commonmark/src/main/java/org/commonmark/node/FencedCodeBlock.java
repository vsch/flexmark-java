package org.commonmark.node;

import org.commonmark.internal.util.BasedSequence;
import org.commonmark.internal.util.SubSequence;

import java.util.List;

public class FencedCodeBlock extends Block {
    private int fenceIndent;
    private BasedSequence openingMarker = SubSequence.EMPTY;
    private BasedSequence closingMarker = null;
    private BasedSequence info = null;

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

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public BasedSequence getOpeningFence() {
        return this.openingMarker;
    }

    /**
     * @see <a href="http://spec.commonmark.org/0.18/#info-string">CommonMark spec</a>
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
