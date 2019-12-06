package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.PrefixedSubSequence;
import com.vladsch.flexmark.util.sequence.Range;
import com.vladsch.flexmark.util.sequence.SegmentedSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A Builder for Segmented BasedSequences
 */
public class BasedSequenceBuilder implements SequenceBuilder<BasedSequenceBuilder, BasedSequence> {
    private final BasedSegmentBuilder mySegments;
    private final @NotNull BasedSequence myBase;
    private @Nullable List<BasedSequence> myBasedSequences;

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
    private BasedSequenceBuilder(@NotNull BasedSequence base, @Nullable SegmentOptimizer optimizer) {
        myBase = base.getBaseSequence();
        mySegments = optimizer == null ? BasedSegmentBuilder.emptyBuilder(myBase) : BasedSegmentBuilder.emptyBuilder(myBase, optimizer);
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
    private BasedSequenceBuilder(@NotNull BasedSequence base, int options, @Nullable SegmentOptimizer optimizer) {
        myBase = base.getBaseSequence();
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

    @NotNull
    @Override
    public BasedSequenceBuilder subContext() {
        return new BasedSequenceBuilder(myBase, mySegments.myOptions, mySegments.myOptimizer);
    }

    @NotNull
    @Override
    public BasedSequenceBuilder addAll(@NotNull Collection<? extends CharSequence> sequences) {
        if (!sequences.isEmpty()) {
            for (CharSequence s : sequences) {
                add(s);
            }
        }
        return this;
    }

    @NotNull
    @Override
    public BasedSequenceBuilder add(@Nullable CharSequence chars) {
        if (chars instanceof BasedSequence && ((BasedSequence) chars).getBase() == myBase.getBase()) {
            if (((BasedSequence) chars).isNotNull()) {
                ((BasedSequence) chars).addSegments(mySegments);
                myBasedSequences = null;
            }
        } else if (chars != null && chars.length() > 0) {
            mySegments.append(chars);
            myBasedSequences = null;
        }
        return this;
    }

    @NotNull
    public BasedSequenceBuilder append(@Nullable CharSequence chars) {
        return add(chars);
    }

    @NotNull
    public BasedSequenceBuilder append(int startOffset, int endOffset) {
        return addByOffsets(startOffset, endOffset);
    }

    @NotNull
    public BasedSequenceBuilder append(@NotNull Range chars) {
        return addRange(chars);
    }

    @NotNull
    public BasedSequenceBuilder addRange(@NotNull Range range) {
        mySegments.append(range);
        myBasedSequences = null;
        return this;
    }

    @NotNull
    public BasedSequenceBuilder addByOffsets(int startOffset, int endOffset) {
        if (startOffset < 0 || startOffset > endOffset || endOffset > myBase.length()) {
            throw new IllegalArgumentException("addByOffsets start/end must be a valid range in [0, " + myBase.length() + "], got: [" + startOffset + ", " + endOffset + "]");
        }
        mySegments.append(Range.of(startOffset, endOffset));
        myBasedSequences = null;
        return this;
    }

    @NotNull
    public BasedSequenceBuilder addByLength(int startOffset, int textLength) {
        return add(myBase.subSequence(startOffset, startOffset + textLength));
    }

    @NotNull
    @Override
    public BasedSequence toSequence() {
        return SegmentedSequence.of(getSequences());
    }

    @NotNull
    public List<BasedSequence> getSequences() {
        if (myBasedSequences == null) {
            ArrayList<BasedSequence> sequences = new ArrayList<>();
            BasedSequence lastSequence = null;
            CharSequence prefix = null;
            for (Object part : mySegments) {
                if (part instanceof Range) {
                    lastSequence = myBase.subSequence(((Range) part).getStart(), ((Range) part).getEnd());

                    if (lastSequence.getStartOffset() > mySegments.getStartOffset()) {
                        // add an empty prefix
                        sequences.add(myBase.subSequence(mySegments.getStartOffset(), mySegments.getStartOffset()));
                    }

                    if (prefix != null) {
                        lastSequence = PrefixedSubSequence.prefixOf(prefix, lastSequence);
                        sequences.add(lastSequence);
                        prefix = null;
                    } else {
                        sequences.add(lastSequence);
                    }
                } else if (part instanceof CharSequence) {
                    assert prefix == null;
                    prefix = (CharSequence) part;

                    if (lastSequence != null) {
                        sequences.add(PrefixedSubSequence.prefixOf(prefix, lastSequence.getEmptySuffix()));
                        prefix = null;
                    }
                } else if(part != null) {
                    throw new IllegalStateException("Invalid part type " + part.getClass());
                }
            }

            if (lastSequence != null && lastSequence.getEndOffset() < mySegments.getEndOffset()) {
                sequences.add(myBase.subSequence(mySegments.getEndOffset(), mySegments.getEndOffset()));
            }

            if (prefix != null) {
                // lone string, goes at 0
                sequences.add(PrefixedSubSequence.prefixOf(prefix, myBase.getEmptyPrefix()));
            }
            myBasedSequences = sequences;
        }
        return myBasedSequences;
    }

    @NotNull
    public BasedSequence[] toBasedArray() {
        return getSequences().toArray(BasedSequence.EMPTY_SEGMENTS);
    }

    @NotNull
    public List<BasedSequence> getSegments() {
        return getSequences();
    }

    @NotNull
    public List<BasedSequence> toLineList() {
        return toSequence().splitListEOL();
    }

    @NotNull
    public BasedSequence[] toLineArray() {
        return toSequence().splitListEOL().toArray(BasedSequence.EMPTY_SEGMENTS);
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
            } else if(part != null) {
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
            } else if (part instanceof CharSequence)  {
                sb.append(part);
            } else if(part != null) {
                throw new IllegalStateException("Invalid part type " + part.getClass());
            }
        }
        return sb.toString();
    }

    public static BasedSequenceBuilder emptyBuilder(@NotNull BasedSequence base) {
        return new BasedSequenceBuilder(base, null);
    }

    public static BasedSequenceBuilder emptyBuilder(@NotNull BasedSequence base, @NotNull SegmentOptimizer optimizer) {
        return new BasedSequenceBuilder(base, optimizer);
    }

    public static BasedSequenceBuilder emptyBuilder(@NotNull BasedSequence base, int options) {
        return new BasedSequenceBuilder(base, options, null);
    }

    public static BasedSequenceBuilder emptyBuilder(@NotNull BasedSequence base, int options, @NotNull SegmentOptimizer optimizer) {
        return new BasedSequenceBuilder(base, options, optimizer);
    }
}
