package com.vladsch.flexmark.util.sequence;

public class SourceText {
    public static final SourceText NULL = new SourceText(Substring.EMPTY, -1, -1);

    public final CharSequence text;
    public final Range range;

    public CharSequence getText() {
        return text;
    }

    public int getStartOffset() {
        return range.getStart();
    }

    public int getEndOffset() {
        return range.getEnd();
    }

    public Range getSourceRange() {
        return range;
    }

    public SourceText(CharSequence text, int startOffset, int endOffset) {
        this(text, new Range(startOffset, endOffset));
    }

    public SourceText(CharSequence text, Range range) {
        this.text = text;
        this.range = range;
    }
}
