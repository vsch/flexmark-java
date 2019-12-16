package com.vladsch.flexmark.util.sequence.builder.tree;

import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.PositionAnchor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BasedOffsetTracker {
    protected final @NotNull BasedSequence sequence;
    protected final @NotNull SegmentOffsetTree segmentOffsetTree;
    private @Nullable Segment lastSegment;

    protected BasedOffsetTracker(@NotNull BasedSequence sequence, @NotNull SegmentTree segmentTree) {
        this.sequence = sequence;
        this.segmentOffsetTree = segmentTree.getSegmentOffsetTree(sequence.getBaseSequence());
    }

    /**
     * Return the index in the sequence of this based offset tracker that corresponds
     * to the given offset in the base sequence from which this sequence was derived.
     * <p>
     * NOTE: indented use is the recover the editing caret position from original text after some text
     * transformation such as formatting, rendering HTML or paragraph wrapping.
     *
     * @param offset offset in base sequence
     * @param anchor position anchor for resolving index when original offset is in a missing
     *               range of the base sequence or offset may or may not correctly reflect
     *               the desired index position
     *               {@link PositionAnchor#CURRENT} no editing was performed around offset
     *               {@link PositionAnchor#PREVIOUS} caret offset is the result of removing characters by backspacing
     *               {@link PositionAnchor#NEXT} caret offset is the result of inserting characters
     * @return index in sequence that corresponds to the offset in base sequence
     */
    public int getOffsetIndex(int offset, @NotNull PositionAnchor anchor) {
        Segment seg = segmentOffsetTree.findSegmentByOffset(offset, sequence.getBaseSequence(), lastSegment);
        if (seg == null) {
            assert offset < sequence.getStartOffset() || offset >= sequence.getEndOffset();
            return offset < sequence.getStartOffset() ? 0 : sequence.length();
        }

        lastSegment = seg;

        if (seg.offsetNotInSegment(offset)) {
            // offset in missing segment, need to check the context of the offset in original sequence
            // and compare to result sequence index
            if (offset < seg.getStartOffset()) {
                if (seg.getPos() > 0) {
                    Segment prevSeg = segmentOffsetTree.getSegment(seg.getPos() - 1, sequence.getBaseSequence());
                    int indexInPrev = offset == prevSeg.getEndOffset() ? prevSeg.length() : prevSeg.length() - (seg.getStartOffset() - offset);
                    assert indexInPrev >= 0 && indexInPrev <= prevSeg.length();

                    switch (anchor) {
                        case CURRENT:
                        case NEXT:
                            return seg.getStartIndex();

                        default:
                        case PREVIOUS:
                            return prevSeg.getStartIndex() + indexInPrev;
                    }
                }
            } else {
                if (offset == seg.getEndOffset()) {
                    return seg.getStartIndex() + seg.length();
                } else if (seg.getPos() + 1 < segmentOffsetTree.size()) {
                    Segment nextSeg = segmentOffsetTree.getSegment(seg.getPos() + 1, sequence.getBaseSequence());
                    int indexInNext = offset - seg.getEndOffset();
                    assert indexInNext >= 0 && indexInNext <= nextSeg.length();

                    switch (anchor) {
                        case PREVIOUS:
                            return seg.getStartIndex();

                        default:
                        case NEXT:
                        case CURRENT:
                            return nextSeg.getStartIndex() + indexInNext;
                    }
                }
            }
        }

        return offset - seg.getStartOffset() + seg.getStartIndex();
    }

    /**
     * Create a based offset tracker for the given sequence
     *
     * @param sequence sequence which to create offset tracker
     * @return based offset tracker
     */
    @NotNull
    public static BasedOffsetTracker create(@NotNull BasedSequence sequence) {
        SegmentTree segmentTree = sequence.getSegmentTree();
        return new BasedOffsetTracker(sequence, segmentTree);
    }
}
