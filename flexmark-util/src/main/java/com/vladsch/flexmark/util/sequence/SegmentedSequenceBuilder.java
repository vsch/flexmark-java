package com.vladsch.flexmark.util.sequence;

inteimport org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * A Builder for Segmented BasedSequences
 */
public final class SegmentedSequenceBuilder {
    private final ArrayList<BasedSequence> segments;
    private final BasedSequence base;

    public SegmentedSequenceBuilder(BasedSequence base) {
        this.segments = new ArrayList<>();
        this.base = base;
    }

    public SegmentedSequenceBuilder(BasedSequence base, int initialCapacity) {
        this.segments = new ArrayList<>(initialCapacity);
        this.base = base;
    }

    public SegmentedSequenceBuilder subContext() {
        return new SegmentedSequenceBuilder(base);
    }

    public SegmentedSequenceBuilder append(SegmentedSequenceBuilder other) {
        if (base != other.base) {
            throw new IllegalArgumentException("append SegmentedSequenceBuilder called with other having different base: " + base.hashCode() + " got: " + other.base.hashCode());
        }
        segments.addAll(other.segments);
        return this;
    }

    public SegmentedSequenceBuilder append(CharSequence s) {
        if (s instanceof BasedSequence) return append((BasedSequence) s);
        else if (s.length() > 0) return append(s.toString());
        else return this;
    }

    public SegmentedSequenceBuilder append(BasedSequence s) {
        if (s.isNotNull()) {
            if (s.getBaseSequence() != base) {
                throw new IllegalArgumentException("setLastSegment called with segment having different base: " + base.hashCode() + " got: " + s.getBaseSequence().hashCode());
            }
            segments.add(s);
        }
        return this;
    }

    public SegmentedSequenceBuilder append(String str) {
        if (str.isEmpty()) return this;
        else {
            BasedSequence useBase;
            if (segments.isEmpty()) {
                useBase = base.subSequence(0, 0);
            } else {
                useBase = segments.get(segments.size() - 1);
                useBase = useBase.subSequence(useBase.length(), useBase.length());
            }
            return append(PrefixedSubSequence.prefixOf(str, useBase));
        }
    }

    public BasedSequence toBasedSequence() {
        return SegmentedSequence.of(segments);
    }

    public BasedSequence[] toSegments() {
        return segments.toArray(BasedSequence.EMPTY_SEGMENTS);
    }

    public List<BasedSequence> getSegments() {
        return segments;
    }

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
