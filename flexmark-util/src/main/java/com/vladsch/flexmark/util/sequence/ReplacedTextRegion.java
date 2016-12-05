package com.vladsch.flexmark.util.sequence;

public class ReplacedTextRegion {
    private final Range original;
    private final Range replaced;

    public ReplacedTextRegion(Range original, Range replaced) {
        this.original = original;
        this.replaced = replaced;
    }

    public ReplacedTextRegion(int originalStart, int originalEnd, int replacedStart, int replacedEnd) {
        this.original = new Range(originalStart, originalEnd);
        this.replaced = new Range(replacedStart, replacedEnd);
    }

    public Range getOriginalRange() {
        return original;
    }

    public Range getReplacedRange() {
        return replaced;
    }

    public boolean containsReplacedIndex(int replacedIndex) {
        return replaced.contains(replacedIndex);
    }

    public boolean containsOriginalIndex(int originalIndex) {
        return original.contains(originalIndex);
    }
}
