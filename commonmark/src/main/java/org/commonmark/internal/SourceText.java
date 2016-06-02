package org.commonmark.internal;

import org.commonmark.internal.util.Substring;

public class SourceText {
    final public static SourceText NULL = new SourceText(Substring.EMPTY, -1, -1);

    final private CharSequence text;
    final private int startOffset;
    final private int endOffset;

    public CharSequence getText() {
        return text;
    }

    public int getStartOffset() {
        return startOffset;
    }

    public int getEndOffset() {
        return endOffset;
    }

    public SourceText(CharSequence text, int startOffset, int endOffset) {
        this.text = text;
        this.startOffset = startOffset;
        this.endOffset = endOffset;
    }
}
