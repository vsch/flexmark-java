package com.vladsch.flexmark.util.sequence;

import java.util.ArrayList;

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

    public SegmentedSequenceBuilder append(CharSequence s) {
        if (s instanceof BasedSequence) return append((BasedSequence) s);
        else if (s.length() > 0) return append(s.toString());
        else return this;
    }

    public SegmentedSequenceBuilder append(BasedSequence s) {
        segments.add(s);
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
