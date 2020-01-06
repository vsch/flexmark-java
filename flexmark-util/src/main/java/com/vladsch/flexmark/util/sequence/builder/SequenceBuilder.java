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
    private final @NotNull BasedSequence altBase;   // sequence used for creating the builder, needed for validation for alt sequence creation
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
        altBase = base;
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
        altBase = base;
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
    public Range getLastRangeOrNull() {
        Object part = segments.getPart(segments.size());
        return part instanceof Range && ((Range) part).isNotNull() ? (Range) part : null;
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
        return new SequenceBuilder(altBase, segments.options, segments.optimizer);
    }

    @Override
    public char charAt(int index) {
        return toSequence().charAt(index);
    }

    @NotNull
    @Override
    public SequenceBuilder append(@Nullable CharSequence chars, int startIndex, int endIndex) {
        if (chars instanceof BasedSequence && ((BasedSequence) chars).getBase() == baseSeq.getBase()) {
            if (((BasedSequence) chars).isNotNull()) {
                if (startIndex == 0 && endIndex == chars.length()) {
                    ((BasedSequence) chars).addSegments(segments);
                } else {
                    ((BasedSequence) chars).subSequence(startIndex, endIndex).addSegments(segments);
                }
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

    /**
     * Construct sequence from this builder using another based sequence which is character identical to this builder's baseSeq
     *
     * @param altSequence based sequence which is character identical to this builder's baseSeq
     * @return builder with offsets mapped to altSequence
     */
    @NotNull
    public BasedSequence toSequence(@NotNull BasedSequence altSequence) {
        if (altSequence == altBase) {
            return toSequence();
        }

//        if (!altSequence.equals(baseSeq)) {
//           int tmp = 0;
//        }
        assert altSequence.equals(altBase) : String.format("altSequence must be character identical to builder.altBase\n" +
                "altBase: '%s'\n" +
                " altSeq: '%s'\n" +
                "", altBase.toVisibleWhitespaceString(), altSequence.toVisibleWhitespaceString());

        // this is an identical but different base sequence, need to map to it. Ranges are indices into altSequence and must be converted to offsets.
        SequenceBuilder altBuilder = new SequenceBuilder(altSequence, segments.options, segments.optimizer);

        for (Object part : segments) {
            if (part instanceof Range) {
                BasedSequence s = altSequence.subSequence(((Range) part).getStart(), ((Range) part).getEnd());
                altBuilder.append(s);
            } else if (part instanceof CharSequence) {
                altBuilder.append((CharSequence) part);
            } else if (part != null) {
                throw new IllegalStateException("Invalid part type " + part.getClass());
            }
        }

//        altBuilder.append(altSequence.getEmptySuffix());

        BasedSequence sequence = SegmentedSequence.create(altBuilder);
        assert sequence.equals(toSequence());
        return sequence;
    }

    /**
     * Construct sequence from this builder using another based sequence which is character identical to this builder's baseSeq
     * but is contiguous as opposed to this builders base sequence.
     *
     * @param altSequence based sequence which is character identical to this builder's baseSeq but its base sequence is contiguous,
     *                    so index of segment should be used in conversion
     * @return builder with offsets mapped to altSequence
     */
    @NotNull
    public BasedSequence fromSequence(@NotNull BasedSequence altSequence) {
        if (altSequence == altBase) {
            return toSequence();
        }

//        if (!altSequence.equals(baseSeq)) {
//           int tmp = 0;
//        }
        assert altSequence.equals(altBase) : String.format("altSequence must be character identical to builder.altBase\n" +
                "altBase: '%s'\n" +
                " altSeq: '%s'\n" +
                "", altBase.toVisibleWhitespaceString(), altSequence.toVisibleWhitespaceString());

        // this is an identical but different base sequence, need to map to it. Ranges are indices into altSequence and must be converted to offsets.
        SequenceBuilder altBuilder = new SequenceBuilder(altSequence, segments.options, segments.optimizer);
        int length = 0;
        for (Object part : segments) {
            if (part instanceof Range) {
                int span = ((Range) part).getSpan();
                BasedSequence s = altSequence.subSequence(length, length + span);
                altBuilder.append(s);
                length += span;
            } else if (part instanceof CharSequence) {
                altBuilder.append((CharSequence) part);
                length += ((CharSequence) part).length();
            } else if (part != null) {
                throw new IllegalStateException("Invalid part type " + part.getClass());
            }
        }

        BasedSequence sequence = SegmentedSequence.create(altBuilder);
        assert sequence.equals(toSequence());
        return sequence;
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
    public String toStringWithRanges(boolean toVisibleWhiteSpace) {
        return toVisibleWhiteSpace ? segments.toStringWithRangesVisibleWhitespace(baseSeq) : segments.toStringWithRanges(baseSeq);
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
