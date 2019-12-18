package com.vladsch.flexmark.util.sequence.builder.tree;

public class OffsetInfo {
    final public int offset;
    final public boolean isEndOffset;
    final public int startIndex;
    final public int endIndex;

    public OffsetInfo(int offset, boolean isEndOffset, int startIndex) {
        this(offset, isEndOffset, startIndex, startIndex);
    }

    public OffsetInfo(int offset, boolean isEndOffset, int startIndex, int endIndex) {
        this.offset = offset;
        this.isEndOffset = isEndOffset;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    @Override
    public String toString() {
        return "OffsetInfo{ " +
                "o=" + (isEndOffset ? "[" + offset + ")" : "[" + offset + ", " + (offset + 1) + ")") +
                ", i=[" + startIndex + ", " + endIndex + ") }";
    }
}
