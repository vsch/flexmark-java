package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.sequence.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
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
    private final ArrayList<BasedSequence> segments = new ArrayList<>();
    private final @NotNull BasedSequence base;
    private @Nullable ArrayList<OffsetTracker> offsetTrackers;

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
        this(base, (OffsetTracker) null);
    }

    public BasedSequenceBuilder(@NotNull BasedSequence base, @Nullable OffsetTracker offsetTracker) {
        this.base = base.getBaseSequence();
        if (offsetTracker != null) addOffsetTracker(offsetTracker);
    }

    private BasedSequenceBuilder(@NotNull BasedSequence base, @NotNull List<OffsetTracker> offsetTrackers) {
        this.base = base.getBaseSequence();
        this.offsetTrackers = new ArrayList<>(offsetTrackers);
    }

    private void addOffsetTracker(@Nullable OffsetTracker tracker) {
        if (offsetTrackers == null) offsetTrackers = new ArrayList<>();
        offsetTrackers.add(tracker);
    }

    @NotNull
    public BasedSequence getBaseSequence() {
        return base;
    }

    @NotNull
    @Override
    public BasedSequenceBuilder subContext() {
        return offsetTrackers == null ? new BasedSequenceBuilder(base) : new BasedSequenceBuilder(base, offsetTrackers);
    }

    @NotNull
    @Override
    public BasedSequenceBuilder addFrom(@NotNull BasedSequenceBuilder other) {
        if (base != other.base) {
            throw new IllegalArgumentException("add BasedSequenceBuilder called with other having different base: " + base.hashCode() + " got: " + other.base.hashCode());
        }

        if (!other.isEmpty()) {
            if (!segments.isEmpty()) {
                BasedSequence lastSegment = getLastSegment();
                BasedSequence s = other.getLastSegment();
                int lastEndOffset = lastSegment.getEndOffset();
                if (s.getStartOffset() < lastEndOffset) {
                    throw new IllegalArgumentException("add BasedSequenceBuilder called with last segment of other having startOffset < lastEndOffset, startOffset: " + s.getStartOffset() + " lastEndOffset: " + lastEndOffset);
                }
            }

            segments.addAll(other.segments);

            if (other.offsetTrackers != null) {
                for (OffsetTracker offsetTracker : other.offsetTrackers) {
                    addOffsetTracker(offsetTracker);
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
        if (chars instanceof BasedSequence && ((BasedSequence) chars).getBase() == base.getBase()) return addBased((BasedSequence) chars);
        else if (chars != null && chars.length() > 0) return addString(chars.toString());
        else return this;
    }

    @NotNull
    private BasedSequenceBuilder addBased(@NotNull BasedSequence chars) {
        if (chars.isNotNull()) {
            if (!segments.isEmpty()) {
                int lastEndOffset = getLastSegment().getEndOffset();
                if (chars.getStartOffset() < lastEndOffset) {
                    throw new IllegalArgumentException("add called with segment having startOffset < lastEndOffset, startOffset: " + chars.getStartOffset() + " lastEndOffset: " + lastEndOffset);
                }
            }

            segments.add(chars);

            if (chars instanceof BasedTrackedSequence) {
                // FIX: for editing
//                addOffsetTracker(((BasedTrackedSequence) chars).getOffsetTrackers());
            }
        }
        return this;
    }

    @NotNull
    public BasedSequenceBuilder addRange(@NotNull Range range) {
        return add(base.subSequence(range));
    }

    @NotNull
    public BasedSequenceBuilder addByOffsets(int startOffset, int endOffset) {
        return add(base.subSequence(startOffset, endOffset));
    }

    @NotNull
    public BasedSequenceBuilder addByLength(int startOffset, int textLength) {
        return add(base.subSequence(startOffset, startOffset + textLength));
    }

    @NotNull
    private BasedSequenceBuilder addString(@Nullable String chars) {
        if (chars == null || chars.isEmpty()) return this;
        else {
            BasedSequence useBase;
            if (segments.isEmpty()) {
                // FIX: when adding non-based sequences before any based sequences are added, need to keep
                useBase = base.subSequence(0, 0);
            } else {
                useBase = segments.get(segments.size() - 1);
                useBase = useBase.subSequence(useBase.length(), useBase.length());
            }
            return add(PrefixedSubSequence.prefixOf(chars, useBase));
        }
    }

    @NotNull
    @Override
    public BasedSequence toSequence() {
        BasedSequence modifiedSeq = SegmentedSequence.of(segments);
        // FIX: for editing
//        if (offsetTrackers != null) {
//            OffsetTracker modifiedTracker = null;
//            for (OffsetTracker offsetTracker : offsetTrackers) {
//                modifiedTracker = offsetTracker.modifiedTracker(modifiedSeq, modifiedTracker);
//            }
//
//            if (modifiedTracker != null) {
//                return BasedTrackedSequence.create(modifiedSeq, modifiedTracker);
//            }
//        }
        return modifiedSeq;
    }

    @NotNull
    public BasedSequence[] toBasedArray() {
        return segments.toArray(BasedSequence.EMPTY_SEGMENTS);
    }

    @NotNull
    public List<BasedSequence> getSegments() {
        return segments;
    }

    @NotNull
    public List<BasedSequence> toLineList() {
        return toSequence().splitListEOL();
    }

    @NotNull
    public BasedSequence[] toLineArray() {
        return toSequence().splitListEOL().toArray(BasedSequence.EMPTY_SEGMENTS);
    }

    @NotNull
    public BasedSequence getLastSegment() {
        return segments.size() == 0 ? BasedSequence.NULL : segments.get(segments.size() - 1);
    }

    public void setLastSegment(@NotNull BasedSequence s) {
        if (segments.size() == 0) {
            throw new IllegalStateException("setLastSegment called with no segments");
        }

        if (s.getBaseSequence() != base) {
            throw new IllegalArgumentException("setLastSegment called with segment having different base: " + base.hashCode() + " got: " + s.getBaseSequence().hashCode());
        }

        if (segments.size() > 1) {
            BasedSequence lastSegment = segments.get(segments.size() - 2);
            if (lastSegment.getEndOffset() < s.getStartOffset()) {
                throw new IllegalArgumentException("setLastSegment called with segment starting before previous endOffset: " + lastSegment.getEndOffset() + " segment startOffset: " + s.getStartOffset());
            }
        }

        segments.set(segments.size() - 1, s);
    }

    @Override
    public int length() {
        int total = 0;
        BasedSequence last = null;
        for (BasedSequence s : segments) {
            if (s.isEmpty()) continue;

            if (last != null && last.getEndOffset() < s.getStartOffset() && (BasedSequence.WHITESPACE.indexOf(last.charAt(last.length() - 1)) == -1) && BasedSequence.WHITESPACE.indexOf(s.charAt(0)) == -1) {
                total++;
            }

            total++;
            last = s;
        }
        return total;
    }

    @Override
    public boolean isEmpty() {
        for (BasedSequence s : segments) {
            if (!s.isEmpty()) return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        BasedSequence last = null;
        for (BasedSequence s : segments) {
            if (s.isEmpty()) continue;

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

        return sb.toString();
    }
}
