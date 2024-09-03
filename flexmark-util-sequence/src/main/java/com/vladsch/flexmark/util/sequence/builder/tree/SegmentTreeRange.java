package com.vladsch.flexmark.util.sequence.builder.tree;

/** Class used to extract subSequence information from segment tree */
public class SegmentTreeRange {
  public final int startIndex;
  public final int endIndex;
  public final int startOffset;
  public final int endOffset;
  public final int startPos;
  public final int endPos;
  public final int length;

  public SegmentTreeRange(
      int startIndex, int endIndex, int startOffset, int endOffset, int startPos, int endPos) {
    this.startIndex = startIndex;
    this.endIndex = endIndex;
    this.startOffset = startOffset;
    this.endOffset = endOffset;
    this.startPos = startPos;
    this.endPos = endPos;
    this.length = endIndex - startIndex;
  }

  @Override
  public String toString() {
    return "SegmentTreeRange{"
        + "startIndex="
        + startIndex
        + ", endIndex="
        + endIndex
        + ", startOffset="
        + startOffset
        + ", endOffset="
        + endOffset
        + ", startPos="
        + startPos
        + ", endPos="
        + endPos
        + ", length="
        + length
        + '}';
  }
}
