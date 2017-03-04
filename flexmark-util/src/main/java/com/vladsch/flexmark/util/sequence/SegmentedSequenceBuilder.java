package com.vladsch.flexmark.util.sequence;

import java.util.ArrayList;

/**
 * A CharSequence that references original char sequence and maps '\0' to '\uFFFD'
 * a subSequence() returns a sub-sequence from the original base sequence
 */
public final class SegmentedSequenceBuilder {
    private final ArrayList<BasedSequence> segments;
    private final BasedSequence base;

    public SegmentedSequenceBuilder(BasedSequence base) {
        this.segments = new ArrayList<BasedSequence>();
        this.base = base;
    }

    public SegmentedSequenceBuilder(BasedSequence base, int initialCapacity) {
        this.segments = new ArrayList<BasedSequence>(initialCapacity);
        this.base = base;
    }

    public SegmentedSequenceBuilder append(BasedSequence s) {
        segments.add(s);
        return this;
    }

    public SegmentedSequenceBuilder append(String str) {
        return append(PrefixedSubSequence.of(str, base, 0,0));
    }

    public BasedSequence toBasedSequence() {
        return SegmentedSequence.of(segments, base);
    }

    public BasedSequence[] toSegments() {
        return segments.toArray(new BasedSequence[segments.size()]);
    }

    @Override
    public String toString() {
        int total = 0;
        for (BasedSequence s:segments) total += s.length();
        StringBuilder sb = new StringBuilder(total);
        for (BasedSequence s:segments) s.appendTo(sb);
        return sb.toString();
    }
}
