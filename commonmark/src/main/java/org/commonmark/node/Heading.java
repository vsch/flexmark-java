package org.commonmark.node;

import org.commonmark.internal.BlockContent;
import org.commonmark.internal.util.BasedSequence;
import org.commonmark.internal.util.SubSequence;

import java.util.List;

public class Heading extends Block {
    protected int level;
    protected BasedSequence openingMarker = SubSequence.NULL;
    protected BasedSequence text = SubSequence.NULL;
    protected BasedSequence closingMarker = SubSequence.NULL;

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
        return openingMarker != SubSequence.NULL;
    }

    public boolean isSetextHeading() {
        return openingMarker == SubSequence.NULL && closingMarker != SubSequence.NULL;
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
