package com.vladsch.flexmark.internal.util;

public class SourceRange {
    final public static SourceRange NULL = new SourceRange(0, 0);

    final protected int startOffset;
    final protected int endOffset;

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

    public boolean contains(int index) {
        return startOffset <= index && index < endOffset;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SourceRange)) return false;

        SourceRange range = (SourceRange) o;

        if (startOffset != range.startOffset) return false;
        return endOffset == range.endOffset;
    }

    @Override
    public int hashCode() {
        int result = startOffset;
        result = 31 * result + endOffset;
        return result;
    }
}
