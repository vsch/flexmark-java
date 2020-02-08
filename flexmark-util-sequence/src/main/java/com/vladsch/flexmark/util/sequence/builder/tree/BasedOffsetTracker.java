package com.vladsch.flexmark.util.sequence.builder.tree;

import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BasedOffsetTracker {
    protected final @NotNull BasedSequence sequence;   // sequence on which this tracker is based, not the base sequence of original sequence
    protected final @NotNull SegmentOffsetTree segmentOffsetTree;
    private @Nullable Segment lastSegment;

    protected BasedOffsetTracker(@NotNull BasedSequence sequence, @NotNull SegmentTree segmentTree) {
        this.sequence = sequence;
        this.segmentOffsetTree = segmentTree.getSegmentOffsetTree(sequence.getBaseSequence());
    }

    protected BasedOffsetTracker(@NotNull BasedSequence sequence, @NotNull SegmentOffsetTree segmentOffsetTree) {
        this.sequence = sequence;
        this.segmentOffsetTree = segmentOffsetTree;
    }

    public int size() {
        return segmentOffsetTree.size();
    }

    /**
     * Return the range of indices in the sequence of this based offset tracker that correspond
     * to the given offset in the base sequence from which this sequence was derived.
     * <p>
     * NOTE: indented use is the recover the editing caret position from original text after some text
     * transformation such as formatting, rendering HTML or paragraph wrapping.
     *
     * @param offset      offset in base sequence
     * @param isEndOffset if true then offset represents the range [offset, offset) so it is located between character at offset-1 and character at offset
     *                    if false then offset represents the character at offset and the range [offset, offset+1)
     * @return information about the offset in this sequence
     */
    @NotNull
    public OffsetInfo getOffsetInfo(int offset, boolean isEndOffset) {
        // if is end offset then will not
        int offsetEnd = isEndOffset ? offset : offset + 1;

        // if offsetEnd <= firstSegment.startOffset then indexRange is [0,0)
        // if offset >= lastSegment.endOffset then indexRange is [sequence.length, sequence.length)

        // otherwise, find segment for the offset in the segmentOffsetTree:

        // if offsetEnd > segment.startOffset && offset < segment.endOffset then
        //      indexRange.start = segment.startIndex + offset - segment.startOffset, indexRange.length = offsetEnd-offset

        // if offsetEnd == segment.startOffset
        //      indexRange is preceding TEXT segment indexRange or if none then [segment.startIndex, segment.startIndex)

        // if offset == segment.endOffset
        //      indexRange is preceding TEXT segment indexRange or if none then [segment.startIndex, segment.startIndex)

        OffsetInfo lastResult;

        if (offsetEnd <= sequence.getStartOffset()) {
            // before sequence
            lastResult = new OffsetInfo(-1, offset, true, 0);
        } else if (offset >= sequence.getEndOffset()) {
            // after sequence
            lastResult = new OffsetInfo(segmentOffsetTree.size(), offset, true, sequence.length());
        } else {
            Segment seg = segmentOffsetTree.findSegmentByOffset(offset, sequence.getBaseSequence(), lastSegment);
            if (seg == null) {
                // outside the sequence
                if (offset < segmentOffsetTree.getSegment(0, sequence).getStartOffset()) {
                    lastResult = new OffsetInfo(-1, offset, true, 0);
                } else {
                    if (offset < segmentOffsetTree.getSegment(segmentOffsetTree.size() - 1, sequence).getEndOffset()) {
                        // RELEASE: remove exception
                        throw new IllegalStateException("Unexpected");
                    }
                    lastResult = new OffsetInfo(segmentOffsetTree.size(), offset, true, sequence.length());
                }
            } else {
                lastSegment = seg;

                if (offsetEnd > seg.getStartOffset() && offset < seg.getEndOffset()) {
                    // inside base segment
                    int startIndex = seg.getStartIndex() + offset - seg.getStartOffset();
                    int endIndex = seg.getStartIndex() + offsetEnd - seg.getStartOffset();
                    lastResult = new OffsetInfo(seg.getPos(), offset, isEndOffset, startIndex, endIndex);
                } else if (offsetEnd <= seg.getStartOffset()) {
                    int startIndex;
                    int endIndex;
                    Segment textSegment = segmentOffsetTree.getPreviousText(seg, sequence);
                    if (textSegment != null) {
                        startIndex = textSegment.getStartIndex();
                        endIndex = textSegment.getEndIndex();
                    } else {
                        endIndex = startIndex = seg.getStartIndex();
                    }
                    lastResult = new OffsetInfo(seg.getPos() - 1, offset, true, startIndex, endIndex);
                } else if (offset >= seg.getEndOffset()) {
                    int startIndex;
                    int endIndex;
                    Segment textSegment = segmentOffsetTree.getNextText(seg, sequence);
                    if (textSegment != null) {
                        startIndex = textSegment.getStartIndex();
                        endIndex = textSegment.getEndIndex();
                    } else {
                        endIndex = startIndex = seg.getEndIndex();
                    }
                    lastResult = new OffsetInfo(seg.getPos() + 1, offset, true, startIndex, endIndex);
                } else {
                    throw new IllegalStateException(String.format("Unexpected offset: [%d, %d), seg: %s, not inside nor at start nor at end", offset, offsetEnd, seg.toString()));
                }
            }
        }

        return lastResult;
    }

    @NotNull
    public BasedSequence getSequence() {
        return sequence;
    }

    @NotNull
    public SegmentOffsetTree getSegmentOffsetTree() {
        return segmentOffsetTree;
    }

    @Override
    public String toString() {
        return "BasedOffsetTracker{" +
                "tree=" + segmentOffsetTree +
                '}';
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

    /**
     * Create a based offset tracker for the given sequence
     *
     * @param sequence sequence which to create offset tracker
     * @param segmentOffsetTree segment offset tree for the sequence
     * @return based offset tracker
     */
    @NotNull
    public static BasedOffsetTracker create(@NotNull BasedSequence sequence, @NotNull SegmentOffsetTree segmentOffsetTree) {
        return new BasedOffsetTracker(sequence, segmentOffsetTree);
    }
}
