package org.commonmark.node;

import org.commonmark.internal.BlockContent;
import org.commonmark.internal.util.BasedSequence;

import java.util.List;

public class Heading extends Block {
    protected int level;
    protected BasedSequence openingMarker = null;
    protected BasedSequence text = null;
    protected BasedSequence closingMarker = null;

    public Heading() {
    }

    public Heading(BasedSequence chars) {
        super(chars);
    }

    public Heading(BasedSequence chars, List<BasedSequence> segments) {
        super(chars, segments);
    }

    public Heading(BlockContent blockContent) {
        super(blockContent);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public boolean isAtxHeading() {
        return openingMarker != null;
    }

    public boolean isSetextHeading() {
        return openingMarker == null && closingMarker != null;
    }

    public BasedSequence getOpeningMarker() {
        return openingMarker;
    }

    public void setOpeningMarker(BasedSequence openingMarker) {
        this.openingMarker = openingMarker;
    }

    public BasedSequence getText() {
        return text;
    }

    public void setText(BasedSequence text) {
        this.text = text;
    }

    public BasedSequence getClosingMarker() {
        return closingMarker;
    }

    public void setClosingMarker(BasedSequence closingMarker) {
        this.closingMarker = closingMarker;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
