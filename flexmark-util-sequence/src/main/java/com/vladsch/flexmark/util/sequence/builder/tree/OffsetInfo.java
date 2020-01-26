package com.vladsch.flexmark.util.sequence.builder.tree;

public class OffsetInfo {
    final public int pos;
    final public int offset;
    final public boolean isEndOffset;
    final public int startIndex;
    final public int endIndex;

    public OffsetInfo(int pos, int offset, boolean isEndOffset, int startIndex) {
        this(pos, offset, isEndOffset, startIndex, startIndex);
    }

    public OffsetInfo(int pos, int offset, boolean isEndOffset, int startIndex, int endIndex) {
        this.pos = pos;
        this.offset = offset;
        this.isEndOffset = isEndOffset;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    @Override
    public String toString() {
        return "OffsetInfo{ " +
                "p=" + pos +
                ", o=" + (isEndOffset ? "[" + offset + ")" : "[" + offset + ", " + (offset + 1) + ")") +
                ", i=[" + startIndex + ", " + endIndex + ") }";
    }
}
