package com.vladsch.flexmark.util.sequence;

public class ReplacedTextRegion {
    final private Range base;
    final private Range original;
    final private Range replaced;

    public ReplacedTextRegion(Range base, Range original, Range replaced) {
        this.base = base;
        this.original = original;
        this.replaced = replaced;
    }

    public Range getBaseRange() {
        return base;
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

    public boolean containsBaseIndex(int originalIndex) {
        return base.contains(originalIndex);
    }

    public boolean containsOriginalIndex(int originalIndex) {
        return original.contains(originalIndex);
    }
}
