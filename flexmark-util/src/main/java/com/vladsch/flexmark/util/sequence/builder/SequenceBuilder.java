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
    private final BasedSegmentBuilder mySegments;
    private final @NotNull BasedSequence myBase;
    private @Nullable BasedSequence myBasedSequence;

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
        myBase = base.getBaseSequence();
        int options = PlainSegmentBuilder.F_DEFAULT;
        // NOTE: if full segmented is not specified, then collect first256 stats for use by tree impl
        if (!myBase.isOption(BasedSequence.O_FULL_SEGMENTED_SEQUENCES) || myBase.isOption(BasedSequence.O_COLLECT_FIRST256_STATS)) options |= PlainSegmentBuilder.F_TRACK_FIRST256;
        if (myBase.isOption(BasedSequence.O_NO_ANCHORS)) options &= ~PlainSegmentBuilder.F_INCLUDE_ANCHORS;
        mySegments = optimizer == null ? BasedSegmentBuilder.emptyBuilder(myBase, options) : BasedSegmentBuilder.emptyBuilder(myBase, optimizer, options);
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
        myBase = base.getBaseSequence();
        // NOTE: if full segmented is not specified, then collect first256 stats for use by tree impl
        if (!myBase.isOption(BasedSequence.O_FULL_SEGMENTED_SEQUENCES) || myBase.isOption(BasedSequence.O_COLLECT_FIRST256_STATS)) options |= PlainSegmentBuilder.F_TRACK_FIRST256;
        if (myBase.isOption(BasedSequence.O_NO_ANCHORS)) options &= ~PlainSegmentBuilder.F_INCLUDE_ANCHORS;
        mySegments = optimizer == null ? BasedSegmentBuilder.emptyBuilder(myBase, options) : BasedSegmentBuilder.emptyBuilder(myBase, optimizer, options);
    }

    @NotNull
    public BasedSequence getBaseSequence() {
        return myBase;
    }

    @NotNull
    public BasedSegmentBuilder getSegmentBuilder() {
        return mySegments;
    }

    @Nullable
    @Override
    public BasedSequence getSingleBasedSequence() {
        Range range = mySegments.getBaseSubSequenceRange();
        return range == null ? null : myBase.subSequence(range.getStart(), range.getEnd());
    }

    @NotNull
    @Override
    public SequenceBuilder subContext() {
        return new SequenceBuilder(myBase, mySegments.myOptions, mySegments.myOptimizer);
    }

    @NotNull
    @Override
    public SequenceBuilder addAll(Iterable<? extends CharSequence> sequences) {
        for (CharSequence s : sequences) {
            add(s);
        }
        return this;
    }

    @NotNull
    @Override
    public SequenceBuilder add(@Nullable CharSequence chars) {
        if (chars instanceof BasedSequence && ((BasedSequence) chars).getBase() == myBase.getBase()) {
            if (((BasedSequence) chars).isNotNull()) {
                ((BasedSequence) chars).addSegments(mySegments);
                myBasedSequence = null;
            }
        } else if (chars != null && chars.length() > 0) {
            mySegments.append(chars);
            myBasedSequence = null;
        }
        return this;
    }

    @NotNull
    public SequenceBuilder append(@Nullable CharSequence chars) {
        return add(chars);
    }

    @NotNull
    public SequenceBuilder append(char c) {
        mySegments.append(c);
        myBasedSequence = null;
        return this;
    }

    @NotNull
    public SequenceBuilder append(char c, int count) {
        if (count > 0) {
            mySegments.append(c, count);
            myBasedSequence = null;
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
        mySegments.append(range);
        myBasedSequence = null;
        return this;
    }

    @NotNull
    public SequenceBuilder addByOffsets(int startOffset, int endOffset) {
        if (startOffset < 0 || startOffset > endOffset || endOffset > myBase.length()) {
            throw new IllegalArgumentException("addByOffsets start/end must be a valid range in [0, " + myBase.length() + "], got: [" + startOffset + ", " + endOffset + "]");
        }
        mySegments.append(Range.of(startOffset, endOffset));
        myBasedSequence = null;
        return this;
    }

    @NotNull
    public SequenceBuilder addByLength(int startOffset, int textLength) {
        return add(myBase.subSequence(startOffset, startOffset + textLength));
    }

    @NotNull
    @Override
    public BasedSequence toSequence() {
        if (myBasedSequence == null) {
            myBasedSequence = SegmentedSequence.create(this);
        }
        return myBasedSequence;
    }

    @Override
    public int length() {
        return mySegments.length();
    }

    @Override
    public boolean isEmpty() {
        return mySegments.length() == 0;
    }

    @NotNull
    public String toStringWithRanges() {
        return mySegments.toStringWithRangesVisibleWhitespace(myBase);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        BasedSequence last = null;
        for (Object part : mySegments) {
            if (part instanceof Range) {
                BasedSequence s = myBase.subSequence(((Range) part).getStart(), ((Range) part).getEnd());

                if (last != null && last.getEndOffset() < s.getStartOffset()
                        && (BasedSequence.WHITESPACE.indexOf(last.charAt(last.length() - 1)) == -1)
                        && BasedSequence.WHITESPACE.indexOf(s.charAt(0)) == -1
                        && s.baseSubSequence(last.getEndOffset(), s.getStartOffset()).endsWith(" ")
                ) {
                    sb.append(' ');
                }

                s.appendTo(sb);
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

    public String toStringNoAddedSpaces() {
        StringBuilder sb = new StringBuilder();
        for (Object part : mySegments) {
            if (part instanceof Range) {
                sb.append(myBase.subSequence(((Range) part).getStart(), ((Range) part).getEnd()));
            } else if (part instanceof CharSequence) {
                sb.append(part);
            } else if (part != null) {
                throw new IllegalStateException("Invalid part type " + part.getClass());
            }
        }
        return sb.toString();
    }

    public static SequenceBuilder emptyBuilder(@NotNull BasedSequence base) {
        return new SequenceBuilder(base, null);
    }

    public static SequenceBuilder emptyBuilder(@NotNull BasedSequence base, @NotNull SegmentOptimizer optimizer) {
        return new SequenceBuilder(base, optimizer);
    }

    public static SequenceBuilder emptyBuilder(@NotNull BasedSequence base, int options) {
        return new SequenceBuilder(base, options, null);
    }

    public static SequenceBuilder emptyBuilder(@NotNull BasedSequence base, int options, @NotNull SegmentOptimizer optimizer) {
        return new SequenceBuilder(base, options, optimizer);
    }
}
