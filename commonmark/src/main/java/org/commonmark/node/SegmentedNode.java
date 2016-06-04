package org.commonmark.node;

import org.commonmark.internal.BlockContent;
import org.commonmark.internal.util.BasedSequence;
import org.commonmark.internal.util.SegmentedSequence;
import org.commonmark.internal.util.SubSequence;

import java.util.List;

public abstract class SegmentedNode extends Node implements Segmented {
    protected List<BasedSequence> segments = SubSequence.EMPTY_LIST;

    public SegmentedNode() {

    }

    public SegmentedNode(BasedSequence chars) {
        super(chars);
    }

    public SegmentedNode(BasedSequence chars, List<BasedSequence> segments) {
        super(chars);
        this.segments = segments;
    }

    public SegmentedNode(BlockContent blockContent) {
        this(blockContent.getSpanningChars(), blockContent.getLines());
    }

    public void setContent(BasedSequence chars, List<BasedSequence> segments) {
        setChars(chars);
        this.segments = segments;
    }

    public void setContent(BlockContent blockContent) {
        setChars(blockContent.getSpanningChars());
        this.segments = blockContent.getLines();
    }

    @Override
    public BasedSequence getSegmentChars(int index) {
        return segments.get(index);
    }

    @Override
    public List<BasedSequence> getContentCharsList() {
        return getContentCharsList(0, segments.size());
    }

    @Override
    public List<BasedSequence> getContentCharsList(int startIndex, int endIndex) {
        return segments.subList(startIndex, endIndex);
    }

    @Override
    public BasedSequence getContentChars() {
        return SegmentedSequence.of(segments, getChars().subSequence(getChars().length()));
    }

    @Override
    public BasedSequence getContentChars(int startIndex, int endIndex) {
        return SegmentedSequence.of(getContentCharsList(startIndex, endIndex), getChars());
    }
}
