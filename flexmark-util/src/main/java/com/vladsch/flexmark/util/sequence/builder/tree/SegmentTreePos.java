package com.vladsch.flexmark.util.sequence.builder.tree;

final public class SegmentTreePos {
    final public int pos;
    final public int startIndex;
    final public int iterations;

    public SegmentTreePos(int pos, int startIndex, int iterations) {
        this.pos = pos;
        this.startIndex = startIndex;
        this.iterations = iterations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SegmentTreePos)) return false;

        SegmentTreePos pos1 = (SegmentTreePos) o;

        if (pos != pos1.pos) return false;
        return startIndex == pos1.startIndex;
    }

    @Override
    public int hashCode() {
        int result = pos;
        result = 31 * result + startIndex;
        return result;
    }

    @Override
    public String toString() {
        return "{" + pos + ", s: " + startIndex + ", i: " + iterations + '}';
    }
}
