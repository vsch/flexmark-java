package com.vladsch.flexmark.util.sequence;

class ReplacedTextRegion {
  private final Range base;
  private final Range original;
  private final Range replaced;

  ReplacedTextRegion(Range base, Range original, Range replaced) {
    this.base = base;
    this.original = original;
    this.replaced = replaced;
  }

  Range getOriginalRange() {
    return original;
  }

  Range getReplacedRange() {
    return replaced;
  }

  boolean containsReplacedIndex(int replacedIndex) {
    return replaced.contains(replacedIndex);
  }
}
