package com.vladsch.flexmark.util.sequence.builder;

import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.Range;
import com.vladsch.flexmark.util.sequence.SegmentedSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A Builder for Segmented BasedSequences
 */
public class SequenceBuilder implements ISequenceBuilder<SequenceBuilder, BasedSequence> {
    private final BasedSegmentBuilder segments;
    private final @NotNull BasedSequence baseSeq;
    private @Nullable BasedSequence resultSeq;

    /**
     * Construct a base sequence builder for given base sequence with default options.
     * <p>
     * NOTE: the builder is always constructed for the base sequence of the base.
     * ie. for the based sequence returned by {@link BasedSequence#getBaseSequence()},
     * so any subsequence from a base can be used as argument for the constructor
     * <p>
     *
     * @param base      base sequence for which to create a builder
     * @param optimizer optimizer for based segment builder, or default {@link CharRecoveryOptimizer}
     */
    private SequenceBuilder(@NotNull BasedSequence base, @Nullable SegmentOptimizer optimizer) {
        baseSeq = base.getBaseSequence();
        int options = PlainSegmentBuilder.F_DEFAULT;
        // NOTE: if full segmented is not specified, then collect first256 stats for use by tree impl
        if (!baseSeq.anyOptions(BasedSequence.F_FULL_SEGMENTED_SEQUENCES) || baseSeq.anyOptions(BasedSequence.F_COLLECT_FIRST256_STATS)) options |= PlainSegmentBuilder.F_TRACK_FIRST256;
        if (baseSeq.anyOptions(BasedSequence.F_NO_ANCHORS)) options &= ~PlainSegmentBuilder.F_INCLUDE_ANCHORS;
        segments = optimizer == null ? BasedSegmentBuilder.emptyBuilder(baseSeq, options) : BasedSegmentBuilder.emptyBuilder(baseSeq, optimizer, options);
    }

    /**
     * Construct a base sequence builder for given base sequence with specific options.
     * <p>
     * NOTE: the builder is always constructed for the base sequence of the base.
     * ie. for the based sequence returned by {@link BasedSequence#getBaseSequence()},
     * so any subsequence from a base can be used as argument for the constructor
     * <p>
     *
     * @param base      base sequence for which to create a builder
     * @param options   builder options
     * @param optimizer optimizer for based segment builder, or default {@link CharRecoveryOptimizer}
     */
    private SequenceBuilder(@NotNull BasedSequence base, int options, @Nullable SegmentOptimizer optimizer) {
        baseSeq = base.getBaseSequence();
        // NOTE: if full segmented is not specified, then collect first256 stats for use by tree impl
        if (!baseSeq.anyOptions(BasedSequence.F_FULL_SEGMENTED_SEQUENCES) || baseSeq.anyOptions(BasedSequence.F_COLLECT_FIRST256_STATS)) options |= PlainSegmentBuilder.F_TRACK_FIRST256;
        if (baseSeq.anyOptions(BasedSequence.F_NO_ANCHORS)) options &= ~PlainSegmentBuilder.F_INCLUDE_ANCHORS;
        segments = optimizer == null ? BasedSegmentBuilder.emptyBuilder(baseSeq, options) : BasedSegmentBuilder.emptyBuilder(baseSeq, optimizer, options);
    }

    @NotNull
    public BasedSequence getBaseSequence() {
        return baseSeq;
    }

    @NotNull
    public BasedSegmentBuilder getSegmentBuilder() {
        return segments;
    }

    @Nullable
    @Override
    public BasedSequence getSingleBasedSequence() {
        Range range = segments.getBaseSubSequenceRange();
        return range == null ? null : baseSeq.subSequence(range.getStart(), range.getEnd());
    }

    @NotNull
    @Override
    public SequenceBuilder getBuilder() {
        return new SequenceBuilder(baseSeq, segments.options, segments.optimizer);
    }

    @NotNull
    @Override
    public SequenceBuilder append(@Nullable CharSequence chars, int startIndex, int endIndex) {
        if (chars instanceof BasedSequence && ((BasedSequence) chars).getBase() == baseSeq.getBase()) {
            if (((BasedSequence) chars).isNotNull()) {
                ((BasedSequence) chars).addSegments(segments);
                resultSeq = null;
            }
        } else if (chars != null && startIndex < endIndex) {
            if (startIndex == 0 && endIndex == chars.length()) {
                segments.append(chars);
            } else {
                segments.append(chars.subSequence(startIndex, endIndex));
            }
            resultSeq = null;
        }
        return this;
    }

    @NotNull
    public SequenceBuilder append(char c) {
        segments.append(c);
        resultSeq = null;
        return this;
    }

    @NotNull
    public SequenceBuilder append(char c, int count) {
        if (count > 0) {
            segments.append(c, count);
            resultSeq = null;
        }
        return this;
    }

    @NotNull
    public SequenceBuilder append(int startOffset, int endOffset) {
        return addByOffsets(startOffset, endOffset);
    }

    @NotNull
    public SequenceBuilder append(@NotNull Range chars) {
        return addRange(chars);
    }

    @NotNull
    public SequenceBuilder addRange(@NotNull Range range) {
        segments.append(range);
        resultSeq = null;
        return this;
    }

    @NotNull
    public SequenceBuilder addByOffsets(int startOffset, int endOffset) {
        if (startOffset < 0 || startOffset > endOffset || endOffset > baseSeq.length()) {
            throw new IllegalArgumentException("addByOffsets start/end must be a valid range in [0, " + baseSeq.length() + "], got: [" + startOffset + ", " + endOffset + "]");
        }
        segments.append(Range.of(startOffset, endOffset));
        resultSeq = null;
        return this;
    }

    @NotNull
    public SequenceBuilder addByLength(int startOffset, int textLength) {
        return add(baseSeq.subSequence(startOffset, startOffset + textLength));
    }

    @NotNull
    @Override
    public BasedSequence toSequence() {
        if (resultSeq == null) {
            resultSeq = SegmentedSequence.create(this);
        }
        return resultSeq;
    }

    @Override
    public int length() {
        return segments.length();
    }

    @NotNull
    public String toStringWithRanges() {
        return segments.toStringWithRangesVisibleWhitespace(baseSeq);
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder();
        BasedSequence last = null;
        for (Object part : segments) {
            if (part instanceof Range) {
                BasedSequence s = baseSeq.subSequence(((Range) part).getStart(), ((Range) part).getEnd());

                if (s.isNotEmpty()) {
                    if (last != null && last.isNotEmpty() && last.getEndOffset() < s.getStartOffset()
                            && (BasedSequence.WHITESPACE.indexOf(last.charAt(last.length() - 1)) == -1)
                            && BasedSequence.WHITESPACE.indexOf(s.charAt(0)) == -1
                            && s.baseSubSequence(last.getEndOffset(), s.getStartOffset()).endsWith(" ")
                    ) {
                        sb.append(' ');
                    }

                    s.appendTo(sb);
                }

                last = s;
            } else if (part instanceof CharSequence) {
                sb.append(part);
                last = null;
            } else if (part != null) {
                throw new IllegalStateException("Invalid part type " + part.getClass());
            }
        }
        return sb.toString();
    }

    @NotNull
    public String toStringNoAddedSpaces() {
        StringBuilder sb = new StringBuilder();
        for (Object part : segments) {
            if (part instanceof Range) {
                sb.append(baseSeq.subSequence(((Range) part).getStart(), ((Range) part).getEnd()));
            } else if (part instanceof CharSequence) {
                sb.append(part);
            } else if (part != null) {
                throw new IllegalStateException("Invalid part type " + part.getClass());
            }
        }
        return sb.toString();
    }

    @NotNull
    public static SequenceBuilder emptyBuilder(@NotNull BasedSequence base) {
        return new SequenceBuilder(base, null);
    }

    @NotNull
    public static SequenceBuilder emptyBuilder(@NotNull BasedSequence base, @NotNull SegmentOptimizer optimizer) {
        return new SequenceBuilder(base, optimizer);
    }

    @NotNull
    public static SequenceBuilder emptyBuilder(@NotNull BasedSequence base, int options) {
        return new SequenceBuilder(base, options, null);
    }

    @NotNull
    public static SequenceBuilder emptyBuilder(@NotNull BasedSequence base, int options, @NotNull SegmentOptimizer optimizer) {
        return new SequenceBuilder(base, options, optimizer);
    }
}
