package org.commonmark.internal.util;

public class SourceRange {
    final public static SourceRange NULL = new SourceRange(0, 0);

    final public int startOffset;
    final public int endOffset;

    public int getStartOffset() {
        return startOffset;
    }

    public int getEndOffset() {
        return endOffset;
    }

    public SourceRange(int startOffset, int endOffset) {
        this.startOffset = startOffset;
        this.endOffset = endOffset;
    }

    public BasedSequence subSequence(CharSequence charSequence) {
        return new SubSequence(charSequence, startOffset, endOffset);
    }
}
