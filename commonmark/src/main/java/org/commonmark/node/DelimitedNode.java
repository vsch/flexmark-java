package org.commonmark.node;

import org.commonmark.internal.util.BasedSequence;
import org.commonmark.internal.util.SubSequence;

public abstract class DelimitedNode extends Node {
    protected BasedSequence openingMarker = SubSequence.EMPTY;
    protected BasedSequence content = SubSequence.EMPTY;
    protected BasedSequence closingMarker = SubSequence.EMPTY;

    public DelimitedNode() {
    }

    public DelimitedNode(BasedSequence chars) {
        super(chars);
    }

    public DelimitedNode(BasedSequence openingMarker, BasedSequence content, BasedSequence closingMarker) {
        super(new SubSequence(openingMarker.getBase(), openingMarker.getStartOffset(), closingMarker.getEndOffset()));
        this.openingMarker = openingMarker;
        this.content = content;
        this.closingMarker = closingMarker;
    }

    public BasedSequence getOpeningMarker() {
        return openingMarker;
    }

    public void setOpeningMarker(BasedSequence openingMarker) {
        this.openingMarker = openingMarker;
    }

    public BasedSequence getContent() {
        return content;
    }

    public void setContent(BasedSequence content) {
        this.content = content;
    }

    public BasedSequence getClosingMarker() {
        return closingMarker;
    }

    public void setClosingMarker(BasedSequence closingMarker) {
        this.closingMarker = closingMarker;
    }
}
