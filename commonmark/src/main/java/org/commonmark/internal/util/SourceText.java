package org.commonmark.internal.util;

public class SourceText {
    final public static SourceText NULL = new SourceText(Substring.EMPTY, -1, -1);

    final public CharSequence text;
    final public SourceRange range;

    public CharSequence getText() {
        return text;
    }

    public int getStartOffset() {
        return range.startOffset;
    }

    public int getEndOffset() {
        return range.endOffset;
    }

    public SourceRange getSourceRange() {
        return range;
    }

    public SourceText(CharSequence text, int startOffset, int endOffset) {
        this(text, new SourceRange(startOffset, endOffset));
    }

    public SourceText(CharSequence text, SourceRange range) {
        this.text = text;
        this.range = range;
    }
}
