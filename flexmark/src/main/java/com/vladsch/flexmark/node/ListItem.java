package com.vladsch.flexmark.node;

import com.vladsch.flexmark.internal.BlockContent;
import com.vladsch.flexmark.internal.util.BasedSequence;
import com.vladsch.flexmark.internal.util.SubSequence;

import java.util.List;

public abstract class ListItem extends Block {
    protected BasedSequence openingMarker = SubSequence.NULL;

    @Override
    public void getAstExtra(StringBuilder out) {
        segmentSpanChars(out, openingMarker, "open");
    }

    @Override
    public BasedSequence[] getSegments() {
        return new BasedSequence[] { openingMarker };
    }

    public BasedSequence getOpeningMarker() {
        return openingMarker;
    }

    public void setOpeningMarker(BasedSequence openingMarker) {
        this.openingMarker = openingMarker;
    }

    public boolean isInTightList() {
        return getParent() instanceof ListBlock && ((ListBlock) getParent()).isTight();
    }

    public boolean isParagraphInTightList() {
        return getParent() instanceof ListBlock && ((ListBlock) getParent()).isTight();
    }

    public ListItem() {
    }

    public ListItem(BasedSequence chars) {
        super(chars);
    }

    public ListItem(BasedSequence chars, List<BasedSequence> segments) {
        super(chars, segments);
    }

    public ListItem(BlockContent blockContent) {
        super(blockContent);
    }
}
