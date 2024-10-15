package com.vladsch.flexmark.util.sequence.builder.tree;

public class OffsetInfo {
  public final int pos;
  private final int offset;
  final boolean isEndOffset;
  public final int startIndex;
  public final int endIndex;

  OffsetInfo(int pos, int offset, boolean isEndOffset, int startIndex) {
    this(pos, offset, isEndOffset, startIndex, startIndex);
  }

  OffsetInfo(int pos, int offset, boolean isEndOffset, int startIndex, int endIndex) {
    this.pos = pos;
    this.offset = offset;
    this.isEndOffset = isEndOffset;
    this.startIndex = startIndex;
    this.endIndex = endIndex;
  }

  @Override
  public String toString() {
    return "OffsetInfo{ "
        + "p="
        + pos
        + ", o="
        + (isEndOffset ? "[" + offset + ")" : "[" + offset + ", " + (offset + 1) + ")")
        + ", i=["
        + startIndex
        + ", "
        + endIndex
        + ") }";
  }
}
