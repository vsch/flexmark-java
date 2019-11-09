package com.vladsch.flexmark.util.sequence;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A Builder for Segmented BasedSequences
 */
public class BasedSequenceBuilder implements SequenceBuilder<BasedSequenceBuilder, BasedSequence> {
    private final ArrayList<BasedSequence> segments;
    private final @NotNull BasedSequence base;
    private int lastEndOffset = -1;
    private @Nullable ArrayList<BasedOffsetTracker> offsetTrackers;

    public BasedSequenceBuilder(BasedSequence base) {
        this(base, -1, null);
    }

    public BasedSequenceBuilder(@NotNull BasedSequence base, int initialCapacity) {
        this(base, initialCapacity, null);
    }

    public BasedSequenceBuilder(@NotNull BasedSequence base, int initialCapacity, @Nullable BasedOffsetTracker offsetTracker) {
        this.segments = initialCapacity < 0 ? new ArrayList<>() : new ArrayList<>(initialCapacity);
        this.base = base.getBaseSequence();
        if (offsetTracker != null) addOffsetTracker(offsetTracker);
    }

    private BasedSequenceBuilder(@NotNull BasedSequence base, @NotNull List<BasedOffsetTracker> offsetTrackers) {
        this.segments = new ArrayList<>();
        this.base = base.getBaseSequence();
        this.offsetTrackers = new ArrayList<>(offsetTrackers);
    }

    private void addOffsetTracker(@Nullable BasedOffsetTracker tracker) {
        if (offsetTrackers == null) offsetTrackers = new ArrayList<>();
        offsetTrackers.add(tracker);
    }

    @NotNull
    @Override
    public BasedSequenceBuilder subContext() {
        return offsetTrackers == null ? new BasedSequenceBuilder(base) : new BasedSequenceBuilder(base, offsetTrackers);
    }

    @NotNull
    @Override
    public BasedSequenceBuilder addAll(@NotNull BasedSequenceBuilder builder) {
        if (base != builder.base) {
            throw new IllegalArgumentException("add BasedSequenceBuilder called with other having different base: " + base.hashCode() + " got: " + builder.base.hashCode());
        }

        if (!builder.isEmpty()) {
            BasedSequence s = builder.getLastSegment();
            if (lastEndOffset >= 0) {
                if (s.getStartOffset() < lastEndOffset) {
                    throw new IllegalArgumentException("add BasedSequenceBuilder called with last segment of other having startOffset < lastEndOffset, startOffset: " + s.getStartOffset() + " lastEndOffset: " + lastEndOffset);
                }
            }

            lastEndOffset = s.getEndOffset();
            segments.addAll(builder.segments);

            if (builder.offsetTrackers != null) {
                for (BasedOffsetTracker offsetTracker : builder.offsetTrackers) {
                    addOffsetTracker(offsetTracker);
                }
            }
        }
        return this;
    }

    @NotNull
    @Override
    public BasedSequenceBuilder addAll(@NotNull Collection<BasedSequence> list) {
        if (!list.isEmpty()) {
            for (BasedSequence s : list) {
                add(s);
            }
        }
        return this;
    }

    @NotNull
    @Override
    public BasedSequenceBuilder add(@NotNull CharSequence chars) {
        if (chars instanceof BasedSequence) return add((BasedSequence) chars);
        else if (chars.length() > 0) return add(chars.toString());
        else return this;
    }

    public BasedSequenceBuilder add(BasedSequence s) {
        if (s.isNotNull()) {
            if (s.getBaseSequence() != base) {
                throw new IllegalArgumentException("add called with segment having different base: " + base.hashCode() + " got: " + s.getBaseSequence().hashCode());
            }

            if (lastEndOffset >= 0) {
                if (s.getStartOffset() < lastEndOffset) {
                    throw new IllegalArgumentException("add called with segment having startOffset < lastEndOffset, startOffset: " + s.getStartOffset() + " lastEndOffset: " + lastEndOffset);
                }
            }

            lastEndOffset = s.getEndOffset();
            segments.add(s);

            if (s instanceof TrackedBasedSequence) {
                addOffsetTracker(((TrackedBasedSequence) s).getOffsetTracker());
            }
        }
        return this;
    }

    @NotNull
    public BasedSequenceBuilder add(@NotNull Range range) {
        return add(base.subSequence(range));
    }

    @NotNull
    public BasedSequenceBuilder add(int startOffset, int endOffset) {
        return add(base.subSequence(startOffset, endOffset));
    }

    @NotNull
    public BasedSequenceBuilder addByLength(int startOffset, int textLength) {
        return add(base.subSequence(startOffset, startOffset + textLength));
    }

    public BasedSequenceBuilder add(String str) {
        if (str.isEmpty()) return this;
        else {
            BasedSequence useBase;
            if (segments.isEmpty()) {
                useBase = base.subSequence(0, 0);
            } else {
                useBase = segments.get(segments.size() - 1);
                useBase = useBase.subSequence(useBase.length(), useBase.length());
            }
            return add(PrefixedSubSequence.prefixOf(str, useBase));
        }
    }

    @NotNull
    @Override
    public BasedSequence toSequence() {
        BasedSequence modifiedSeq = SegmentedSequence.of(segments);
        if (offsetTrackers != null) {
            BasedOffsetTracker modifiedTracker = null;
            for (BasedOffsetTracker offsetTracker : offsetTrackers) {
                modifiedTracker = offsetTracker.modifiedTracker(modifiedSeq, modifiedTracker);
            }

            if (modifiedTracker != null) {
                return TrackedBasedSequence.create(modifiedSeq, modifiedTracker);
            }
        }
        return modifiedSeq;
    }

    @NotNull
    public BasedSequence[] toArray() {
        return segments.toArray(BasedSequence.EMPTY_SEGMENTS);
    }

    @NotNull
    public List<BasedSequence> toList() {
        return segments;
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

            if (last != null && last.getEndOffset() < s.getStartOffset() && (BasedSequence.WHITESPACE_CHARS.indexOf(last.charAt(last.length() - 1)) == -1) && BasedSequence.WHITESPACE_CHARS.indexOf(s.charAt(0)) == -1) {
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
        int total = 0;
        StringBuilder sb = new StringBuilder();
        BasedSequence last = null;
        for (BasedSequence s : segments) {
            if (s.isEmpty()) continue;

            if (last != null && last.getEndOffset() < s.getStartOffset()
                && (BasedSequence.WHITESPACE_CHARS.indexOf(last.charAt(last.length() - 1)) == -1)
                && BasedSequence.WHITESPACE_CHARS.indexOf(s.charAt(0)) == -1
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
