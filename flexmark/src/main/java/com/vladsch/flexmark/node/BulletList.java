package com.vladsch.flexmark.node;

import com.vladsch.flexmark.internal.BlockContent;
import com.vladsch.flexmark.internal.util.sequence.BasedSequence;

import java.util.List;

public class BulletList extends ListBlock {
    private char openingMarker;

    @Override
    public BasedSequence[] getSegments() {
        return EMPTY_SEGMENTS;
    }

    public BulletList() {
    }

    public BulletList(BasedSequence chars) {
        super(chars);
    }

    public BulletList(BasedSequence chars, List<BasedSequence> segments) {
        super(chars, segments);
    }

    public BulletList(BlockContent blockContent) {
        super(blockContent);
    }

    public char getOpeningMarker() {
        return openingMarker;
    }

    public void setOpeningMarker(char openingMarker) {
        this.openingMarker = openingMarker;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
