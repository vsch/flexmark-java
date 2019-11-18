package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.sequence.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * A Builder for Segmented BasedSequences
 * <p>
 * FIX: when adding non-based sequences before any based sequences are added, need to keep
 * them pending until a based sequence is added to make them prefixes of that sequence
 * can also concatenate non-based sequences together. This would eliminate the need to
 * make add non-based sequence as prefixed sequence
 */
public class BasedSequenceBuilder implements SequenceBuilder<BasedSequenceBuilder, BasedSequence> {
    private final BasedSegmentBuilder mySegments;
    private final @NotNull BasedSequence myBase;
    private final @Nullable List<BasedSegmentOptimizer> myOptimizers;
    private @Nullable List<BasedSequence> myBasedSequences;
    private long myBasedSequencesTimestamp;

    /**
     * Construct a base sequence builder for given base sequence.
     * <p>
     * NOTE: the builder is always constructed for the base sequence of the base.
     * ie. for the based sequence returned by {@link BasedSequence#getBaseSequence()},
     * so any subsequence from a base can be used as argument for the constructor
     *
     * @param base base sequence for which to create a builder
     */
    public BasedSequenceBuilder(@NotNull BasedSequence base) {
        myBase = base.getBaseSequence();
        mySegments = BasedSegmentBuilder.sequenceBuilder(myBase);
        myOptimizers = null;
    }

    public BasedSequenceBuilder(@NotNull BasedSequence base, @NotNull BasedSegmentOptimizer optimizer) {
        myBase = base.getBaseSequence();
        mySegments = BasedSegmentBuilder.sequenceBuilder(myBase);
        myOptimizers = Collections.singletonList(optimizer);
    }

    private BasedSequenceBuilder(@NotNull BasedSequence base, @NotNull List<BasedSegmentOptimizer> optimizers) {
        myBase = base.getBaseSequence();
        mySegments = BasedSegmentBuilder.sequenceBuilder(myBase);
        myOptimizers = optimizers;
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
        return myOptimizers == null ? new BasedSequenceBuilder(myBase) : new BasedSequenceBuilder(myBase, myOptimizers);
    }

    @NotNull
    @Override
    public BasedSequenceBuilder addFrom(@NotNull BasedSequenceBuilder other) {
        if (!other.isEmpty()) {
            if (myBase != other.myBase) {
//            throw new IllegalArgumentException("add BasedSequenceBuilder called with other having different base: " + myBase.hashCode() + " got: " + other.myBase.hashCode());
                for (Object part : other.mySegments.getParts()) {
                    if (part instanceof Range) {
                        mySegments.append(other.myBase.subSequence((Range) part).toString());
                    } else {
                        mySegments.append((String) part);
                    }
                }
            } else {
                for (Object part : other.mySegments.getParts()) {
                    mySegments.append(part);
                }
            }
        }
        return this;
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
        if (chars instanceof BasedSequence && ((BasedSequence) chars).getBase() == myBase.getBase()) return addBased((BasedSequence) chars);
        else if (chars != null && chars.length() > 0) return addString(chars.toString());
        else return this;
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
    private BasedSequenceBuilder addBased(@NotNull BasedSequence chars) {
        if (chars.isNotNull()) {
            BasedUtils.generateSegments(mySegments, myBase, chars);
//            if (!(chars instanceof ReplacedBasedSequence)) {
//                mySegments.append(chars.getSourceRange());
//            } else {
//                if (myOptimizers == null) {
//                    int startOffset = chars.getStartOffset() - myBase.getStartOffset();
//                    int endOffset = chars.getEndOffset() - myBase.getStartOffset();
//                    mySegments.append(startOffset, startOffset);
//                    mySegments.append(chars.toString());
//                    mySegments.append(endOffset, endOffset);
//                } else {
//                }
//                ((ReplacedBasedSequence) chars).addSegments(mySegments);
//            }
            myBasedSequences = null;
        }
        return this;
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
    private BasedSequenceBuilder addString(@Nullable String chars) {
        if (chars != null && !chars.isEmpty()) {
            mySegments.append(chars);
            myBasedSequences = null;
        }
        return this;
    }

    @NotNull
    @Override
    public BasedSequence toSequence() {
        List<BasedSequence> sequences = getSequences();
        BasedSequence modifiedSeq = SegmentedSequence.of(sequences);
        return modifiedSeq;
    }

    @NotNull
    public List<BasedSequence> getSequences() {
        if (myBasedSequences == null || myBasedSequencesTimestamp != mySegments.getModificationTimestamp()) {
            if (myOptimizers != null) {
                for (BasedSegmentOptimizer optimizer : myOptimizers) {
                    mySegments.optimizeFor(myBase, optimizer);
                }
            }

            myBasedSequencesTimestamp = mySegments.getModificationTimestamp();

            ArrayList<BasedSequence> sequences = new ArrayList<>();
            BasedSequence lastSequence = null;
            String prefix = null;
            for (Object part : mySegments.getParts()) {
                if (part instanceof String) {
                    assert prefix == null;
                    prefix = (String) part;

                    if (lastSequence != null) {
                        sequences.add(PrefixedSubSequence.prefixOf(prefix, lastSequence.getEmptySuffix()));
                        prefix = null;
                    }
                } else {
                    assert part instanceof Range;
                    lastSequence = myBase.subSequence((Range) part);
                    if (prefix != null) {
                        lastSequence = PrefixedSubSequence.prefixOf(prefix, lastSequence);
                        sequences.add(lastSequence);
                        prefix = null;
                    } else {
                        sequences.add(lastSequence);
                    }
                }
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
        return mySegments.toStringWithRanges(myBase);
    }

    @NotNull
    public String toStringWithRangesOptimized() {
        if (myOptimizers != null) {
            for (BasedSegmentOptimizer optimizer : myOptimizers) {
                mySegments.optimizeFor(myBase, optimizer);
            }
        }
        return mySegments.toStringWithRanges(myBase);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        BasedSequence last = null;
        for (Object part : mySegments.getParts()) {
            if (part instanceof String) {
                sb.append((String) part);
                last = null;
            } else {
                if (((Range) part).isEmpty()) continue;

                BasedSequence s = myBase.subSequence((Range) part);

                if (last != null && last.getEndOffset() < s.getStartOffset()
                        && (BasedSequence.WHITESPACE.indexOf(last.charAt(last.length() - 1)) == -1)
                        && BasedSequence.WHITESPACE.indexOf(s.charAt(0)) == -1
                        && s.baseSubSequence(last.getEndOffset(), s.getStartOffset()).endsWith(" ")
                ) {
                    sb.append(' ');
                }

                s.appendTo(sb);
                last = s;
            }
        }
        return sb.toString();
    }

    public String toStringNoAddedSpaces() {
        StringBuilder sb = new StringBuilder();
        for (Object part : mySegments.getParts()) {
            if (part instanceof String) {
                sb.append((String) part);
            } else {
                sb.append(myBase.subSequence((Range) part));
            }
        }
        return sb.toString();
    }
}
