package com.vladsch.flexmark.util.sequence;

import com.vladsch.flexmark.util.sequence.builder.*;
import org.jetbrains.annotations.NotNull;

/**
 * A BasedSequence which consists of segments of other BasedSequences
 * NOTE: very efficient for random access but extremely wasteful with space by allocating 4 bytes per character in the sequence with corresponding construction penalty
 * use SegmentedSequenceTree which is binary tree based segmented sequence with minimal overhead and optimized to give penalty free random access for most applications.
 */
final public class SegmentedSequenceFull extends SegmentedSequence {
    final private boolean nonBaseChars;     // true if contains non-base chars
    final private int[] baseOffsets;        // list of base offsets, offset by baseStartOffset
    final private int baseStartOffset;      // start offset for baseOffsets of this sequence, offset from baseSeq for all chars, including non-base chars

    private SegmentedSequenceFull(BasedSequence baseSeq, int startOffset, int endOffset, int length, boolean nonBaseChars, int[] baseOffsets, int baseStartOffset) {
        super(baseSeq, startOffset, endOffset, length);
        this.nonBaseChars = nonBaseChars;
        this.baseOffsets = baseOffsets;
        this.baseStartOffset = baseStartOffset;
    }

    @Override
    public int getIndexOffset(int index) {
        SequenceUtils.validateIndexInclusiveEnd(index, length());

        int offset = baseOffsets[baseStartOffset + index];
        return offset < 0 ? -1 : offset;
    }

    @Override
    public void addSegments(@NotNull IBasedSegmentBuilder<?> builder) {
        // FIX: clean up and optimize the structure. it is error prone and inefficient
        BasedUtils.generateSegments(builder, this);
    }

    @Override
    public char charAt(int index) {
        SequenceUtils.validateIndex(index, length());

        int offset = baseOffsets[baseStartOffset + index];

        if (offset < 0) {
            /* HACK: allows having characters which are not from original base sequence
                 but with the only penalty for charAt access being an extra indirection,
                 which is a small price to pay for having the flexibility of adding out of
                 context text to the based sequence.
             */
            return (char) (-offset - 1);
        } else {
            return baseSeq.charAt(offset);
        }
    }

    @NotNull
    @Override
    public BasedSequence subSequence(int startIndex, int endIndex) {
        SequenceUtils.validateStartEnd(startIndex, endIndex, length());

        if (startIndex == 0 && endIndex == length) {
            return this;
        } else {
            return subSequence(baseSeq, baseOffsets, baseStartOffset + startIndex, nonBaseChars, endIndex - startIndex);
        }
    }

    /**
     * Base Constructor
     *
     * @param baseSequence base sequence for segmented sequence
     * @param builder builder for which to construct segmented sequence
     * @return segmented sequence
     */
    public static SegmentedSequenceFull create(@NotNull BasedSequence baseSequence, ISegmentBuilder<?> builder) {
        BasedSequence baseSeq = baseSequence.getBaseSequence();
        int length = builder.length();
        int baseStartOffset = 0;
        int[] baseOffsets = new int[length + 1];

        int index = 0;
        for (Object part : builder) {
            if (part instanceof Range) {
                if (((Range) part).isEmpty()) continue;

                int iMax = ((Range) part).getEnd();
                for (int i = ((Range) part).getStart(); i < iMax; i++) {
                    baseOffsets[index++] = i;
                }
            } else if (part instanceof CharSequence) {
                CharSequence sequence = (CharSequence) part;
                int iMax = sequence.length();
                for (int i = 0; i < iMax; i++) {
                    baseOffsets[index++] = -sequence.charAt(i) - 1;
                }
            } else if (part != null) {
                throw new IllegalStateException("Invalid part type " + part.getClass());
            }
        }

        int end = baseOffsets[length - 1];
        baseOffsets[length] = end < 0 ? end - 1 : end + 1;

        int startOffset = builder.getStartOffset();
        int endOffset = builder.getEndOffset();
        boolean nonBaseChars = builder.getTextLength() > 0;

        if (baseSeq.anyOptions(F_COLLECT_SEGMENTED_STATS)) {
            SegmentedSequenceStats stats = baseSeq.getOption(SEGMENTED_STATS);
            if (stats != null) {
                stats.addStats(builder.noAnchorsSize(), length, baseOffsets.length * 4);
            }
        }

        return new SegmentedSequenceFull(
                baseSeq,
                startOffset,
                endOffset,
                length,
                nonBaseChars,
                baseOffsets,
                baseStartOffset
        );
    }

    private SegmentedSequenceFull subSequence(final BasedSequence baseSeq, final int[] baseOffsets, final int baseStartOffset, final boolean nonBaseChars, final int length) {
        int iMax = baseOffsets.length - 1;
        assert baseStartOffset + length <= iMax : "Sub-sequence offsets list length < baseStartOffset + sub-sequence length";

        int startOffset = 0;
        int endOffset = 0;

        if (!nonBaseChars) {
            if (baseStartOffset < iMax) {
                // start is the offset at our start, even when length = 0
                startOffset = baseOffsets[baseStartOffset];
            } else {
                startOffset = baseSeq.getEndOffset();
            }

            if (length == 0) {
                endOffset = startOffset;
            } else {
                endOffset = baseOffsets[baseStartOffset + length - 1] + 1;
                assert startOffset <= endOffset;
            }
        } else {
            // start is the first real start in this sequence or after it in the parent
            boolean finished = false;

            for (int iS = baseStartOffset; iS < iMax; iS++) {
                if (baseOffsets[iS] < 0) continue;
                startOffset = baseOffsets[iS];

                if (length != 0) {
                    // end is the last real offset + 1 in this sequence up to the start index where startOffset was found
                    for (int iE = baseStartOffset + length; iE-- > iS; ) {
                        if (baseOffsets[iE] < 0) continue;

                        endOffset = baseOffsets[iE] + 1;
                        assert startOffset <= endOffset;

                        finished = true;
                        break;
                    }
                }

                if (!finished) {
                    endOffset = startOffset;
                }

                finished = true;
                break;
            }

            if (!finished) {
                // if no real start after then it is the base's end since we had no real start after, these chars and after are all out of base chars
                startOffset = baseSeq.getEndOffset();
                endOffset = startOffset;
            }
        }

        return new SegmentedSequenceFull(
                baseSeq,
                startOffset,
                endOffset,
                length,
                nonBaseChars,
                baseOffsets,
                baseStartOffset
        );
    }

    /**
     * @param basedSequence base sequence for the segments
     * @param segments      list of based sequences to put into a based sequence
     * @return based sequence of segments. Result is a sequence which looks like
     *         all the segments were concatenated, while still maintaining
     *         the original offset for each character when using {@link #getIndexOffset(int)}(int index)
     * @deprecated use {@link BasedSequence#getBuilder()} and then {@link SequenceBuilder#addAll(Iterable)}
     *         or if you know which are based segments vs. out of base Strings then use {@link BasedSegmentBuilder}
     *         to construct segments directly. If you must use segments then use {@link SegmentedSequence#create(BasedSequence, Iterable)}
     *         which does the builder calls for you.
     */
    @Deprecated
    public static BasedSequence of(BasedSequence basedSequence, @NotNull Iterable<? extends BasedSequence> segments) {
        return SegmentedSequence.create(basedSequence, segments);
    }

    @Deprecated
    public static BasedSequence of(BasedSequence... segments) {
        return SegmentedSequence.create(segments);
    }
}
