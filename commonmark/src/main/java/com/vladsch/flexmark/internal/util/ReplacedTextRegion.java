package com.vladsch.flexmark.internal.util;

public class ReplacedTextRegion {
    final private SourceRange original;
    final private SourceRange replaced;

    public ReplacedTextRegion(SourceRange original, SourceRange replaced) {
        this.original = original;
        this.replaced = replaced;
    }

    public ReplacedTextRegion(int originalStart, int originalEnd, int replacedStart, int replacedEnd) {
        this.original = new SourceRange(originalStart, originalEnd);
        this.replaced = new SourceRange(replacedStart, replacedEnd);
    }

    public SourceRange getOriginal() {
        return original;
    }

    public SourceRange getReplaced() {
        return replaced;
    }

    public boolean containsReplacedIndex(int replacedIndex) {
        return replaced.contains(replacedIndex);
    }

    public boolean containsOriginalIndex(int originalIndex) {
        return original.contains(originalIndex);
    }
}
