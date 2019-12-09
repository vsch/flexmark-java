package com.vladsch.flexmark.util.sequence.edit.tree;

/**
 * Class used to extract subSequence information from segment tree
 */
public class SegmentTreeRange {
    final public int startIndex;
    final public int endIndex;
    final public int startOffset;
    final public int endOffset;
    final public int startPos;
    final public int endPos;

    public SegmentTreeRange(int startIndex, int endIndex, int startOffset, int endOffset, int startPos, int endPos) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.startOffset = startOffset;
        this.endOffset = endOffset;
        this.startPos = startPos;
        this.endPos = endPos;
    }
}
