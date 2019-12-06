package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.PrefixedSubSequence;
import com.vladsch.flexmark.util.sequence.Range;
import com.vladsch.flexmark.util.sequence.SegmentedSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * A Builder for Segmented BasedSequences
 */
public class BasedSequenceBuilder2 implements SequenceBuilder<BasedSequenceBuilder2, BasedSequence> {
    private final BasedSegmentBuilder2 mySegments;
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
     * @param optimizer optimizer for based segment builder, or default {@link CharRecoveryOptimizer2}
     */
    private BasedSequenceBuilder2(@NotNull BasedSequence base, @Nullable SegmentOptimizer2 optimizer) {
        myBase = base.getBaseSequence();
        mySegments = optimizer == null ? BasedSegmentBuilder2.emptyBuilder(myBase) : BasedSegmentBuilder2.emptyBuilder(myBase, optimizer);
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
     * @param optimizer optimizer for based segment builder, or default {@link CharRecoveryOptimizer2}
     */
    private BasedSequenceBuilder2(@NotNull BasedSequence base, int options, @Nullable SegmentOptimizer2 optimizer) {
        myBase = base.getBaseSequence();
        mySegments = optimizer == null ? BasedSegmentBuilder2.emptyBuilder(myBase, options) : BasedSegmentBuilder2.emptyBuilder(myBase, optimizer, options);
    }

    @NotNull
    public BasedSequence getBaseSequence() {
        return myBase;
    }

    @NotNull
    public BasedSegmentBuilder2 getSegmentBuilder() {
        return mySegments;
    }

    @NotNull
    @Override
    public BasedSequenceBuilder2 subContext() {
        return new BasedSequenceBuilder2(myBase, mySegments.myOptions, mySegments.myOptimizer);
    }

    @NotNull
    @Override
    public BasedSequenceBuilder2 addAll(@NotNull Collection<? extends CharSequence> sequences) {
        if (!sequences.isEmpty()) {
            for (CharSequence s : sequences) {
                add(s);
            }
        }
        return this;
    }

    @NotNull
    @Override
    public BasedSequenceBuilder2 add(@Nullable CharSequence chars) {
        if (chars instanceof BasedSequence && ((BasedSequence) chars).getBase() == myBase.getBase()) {
            if (((BasedSequence) chars).isNotNull()) {
                ((BasedSequence) chars).addSegments(mySegments);
                myBasedSequences = null;
            }
        } else if (chars != null && chars.length() > 0) {
            mySegments.append(chars.toString());
            myBasedSequences = null;
        }
        return this;
    }

    @NotNull
    public BasedSequenceBuilder2 append(@Nullable CharSequence chars) {
        return add(chars);
    }

    @NotNull
    public BasedSequenceBuilder2 append(int startOffset, int endOffset) {
        return addByOffsets(startOffset, endOffset);
    }

    @NotNull
    public BasedSequenceBuilder2 append(@NotNull Range chars) {
        return addRange(chars);
    }

    @NotNull
    public BasedSequenceBuilder2 addRange(@NotNull Range range) {
        mySegments.append(range);
        myBasedSequences = null;
        return this;
    }

    @NotNull
    public BasedSequenceBuilder2 addByOffsets(int startOffset, int endOffset) {
        if (startOffset < 0 || startOffset > endOffset || endOffset > myBase.length()) {
            throw new IllegalArgumentException("addByOffsets start/end must be a valid range in [0, " + myBase.length() + "], got: [" + startOffset + ", " + endOffset + "]");
        }
        mySegments.append(Range.of(startOffset, endOffset));
        myBasedSequences = null;
        return this;
    }

    @NotNull
    public BasedSequenceBuilder2 addByLength(int startOffset, int textLength) {
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
            if (myOptimizer != null) {
                for (BasedSegmentOptimizer optimizer : myOptimizer) {
                    mySegments.optimizeFor(myBase, optimizer);
                }
            }

            ArrayList<BasedSequence> sequences = new ArrayList<>();
            BasedSequence lastSequence = null;
            String prefix = null;
            for (Seg part : mySegments.getParts()) {
                if (part.isBase()) {
                    lastSequence = myBase.subSequence(part.getStart(), part.getEnd());

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
                } else {
                    assert prefix == null && part.isText();
                    prefix = part.getText();

                    if (lastSequence != null) {
                        sequences.add(PrefixedSubSequence.prefixOf(prefix, lastSequence.getEmptySuffix()));
                        prefix = null;
                    }
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

    @NotNull
    public String toStringWithRangesOptimized() {
        if (myOptimizer != null) {
            for (BasedSegmentOptimizer optimizer : myOptimizer) {
                mySegments.optimizeFor(myBase, optimizer);
            }
        }
        return mySegments.toStringWithRangesVisibleWhitespace(myBase);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        BasedSequence last = null;
        for (Seg part : mySegments.getParts()) {
            if (part.isBase()) {
                BasedSequence s = myBase.subSequence(part.getStart(), part.getEnd());

                if (last != null && last.getEndOffset() < s.getStartOffset()
                        && (BasedSequence.WHITESPACE.indexOf(last.charAt(last.length() - 1)) == -1)
                        && BasedSequence.WHITESPACE.indexOf(s.charAt(0)) == -1
                        && s.baseSubSequence(last.getEndOffset(), s.getStartOffset()).endsWith(" ")
                ) {
                    sb.append(' ');
                }

                s.appendTo(sb);
                last = s;
            } else {
                sb.append(part.getText());
                last = null;
            }
        }
        return sb.toString();
    }

    public String toStringNoAddedSpaces() {
        StringBuilder sb = new StringBuilder();
        for (Seg part : mySegments.getParts()) {
            if (part.isBase()) {
                sb.append(myBase.subSequence(part.getStart(), part.getEnd()));
            } else {
                sb.append(part.getText());
            }
        }
        return sb.toString();
    }

    public static BasedSequenceBuilder2 emptyBuilder(@NotNull BasedSequence base) {
        return new BasedSequenceBuilder2(base, null);
    }

    public static BasedSequenceBuilder2 emptyBuilder(@NotNull BasedSequence base, @NotNull BasedSegmentOptimizer optimizer) {
        return new BasedSequenceBuilder2(base, Collections.singletonList(optimizer));
    }

    public static BasedSequenceBuilder2 emptyBuilder(@NotNull BasedSequence base, @NotNull List<BasedSegmentOptimizer> optimizers) {
        return new BasedSequenceBuilder2(base, optimizers);
    }

    public static BasedSequenceBuilder2 emptyBuilder(@NotNull BasedSequence base, int options) {
        return new BasedSequenceBuilder2(base, options, null);
    }

    public static BasedSequenceBuilder2 emptyBuilder(@NotNull BasedSequence base, int options, @NotNull BasedSegmentOptimizer optimizer) {
        return new BasedSequenceBuilder2(base, options, Collections.singletonList(optimizer));
    }

    public static BasedSequenceBuilder2 emptyBuilder(@NotNull BasedSequence base, int options, @NotNull List<BasedSegmentOptimizer> optimizers) {
        return new BasedSequenceBuilder2(base, options, optimizers);
    }
}
