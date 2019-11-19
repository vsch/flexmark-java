package com.vladsch.flexmark.util.sequence.edit;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface SegmentHolder {
    /**
     * Return full set of parts, starting with lowest segment
     * @return all parts
     */
    @NotNull EditOp[] getParts();

    /**
     * length of sequence built by full parts list
     * @return length, all part lengths should add up to length
     */
    int getLength();

    /**
     * Offsets into full parts list for this segment holder, for non-partial parts
     * @return index into parts where first full part starts or ends
     */
    int getStartIndex();
    int getEndIndex();

    /**
     * This holders start/end offsets
     * @return offset of this sequence built from parts
     */
    int getStartOffset();
    int getEndOffset();

    /**
     * Get any partial part prefix/suffix to whole parts
     * @return partial first/last partial parts
     */
    @Nullable EditOp getFirstPartial();
    @Nullable EditOp getLastPartial();

    /**
     * Get the segment holder representing a subsequence of these parts
     * @param startIndex start index sequence created by parts
     * @param endIndex end index in sequence created by parts
     * @return subsequence holder capable of adding segments representing this subsequence
     */
    @NotNull SegmentHolder getSubSequence(int startIndex, int endIndex);

    /**
     * Add segments for this sequence, replacing out of base characters with strings
     *
     * @param builder builder
     * @return true if had out of base chars
     */
    boolean addSegments(@NotNull BasedSegmentBuilder builder);
}
