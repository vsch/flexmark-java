package com.vladsch.flexmark.node;

import com.vladsch.flexmark.internal.BlockContent;
import com.vladsch.flexmark.internal.util.BasedSequence;
import com.vladsch.flexmark.internal.util.SegmentedSequence;
import com.vladsch.flexmark.internal.util.SubSequence;

import java.util.List;

public abstract class SegmentedNode extends Node implements Segmented {
    protected List<BasedSequence> sequenceSegments = SubSequence.EMPTY_LIST;

    public SegmentedNode() {

    }

    public SegmentedNode(BasedSequence chars) {
        super(chars);
    }

    public SegmentedNode(BasedSequence chars, List<BasedSequence> sequenceSegments) {
        super(chars);
        this.sequenceSegments = sequenceSegments;
    }

    public SegmentedNode(BlockContent blockContent) {
        this(blockContent.getSpanningChars(), blockContent.getLines());
    }

    public void setContent(BasedSequence chars, List<BasedSequence> segments) {
        setChars(chars);
        this.sequenceSegments = segments;
    }

    public void setContent(BlockContent blockContent) {
        setChars(blockContent.getSpanningChars());
        this.sequenceSegments = blockContent.getLines();
    }

    @Override
    public BasedSequence getSegmentChars(int index) {
        return sequenceSegments.get(index);
    }

    @Override
    public List<BasedSequence> getContentCharsList() {
        return getContentCharsList(0, sequenceSegments.size());
    }

    @Override
    public List<BasedSequence> getContentCharsList(int startIndex, int endIndex) {
        return sequenceSegments.subList(startIndex, endIndex);
    }

    @Override
    public BasedSequence getContentChars() {
        return SegmentedSequence.of(sequenceSegments, getChars().subSequence(getChars().length()));
    }

    @Override
    public BasedSequence getContentChars(int startIndex, int endIndex) {
        return SegmentedSequence.of(getContentCharsList(startIndex, endIndex), getChars());
    }
}
