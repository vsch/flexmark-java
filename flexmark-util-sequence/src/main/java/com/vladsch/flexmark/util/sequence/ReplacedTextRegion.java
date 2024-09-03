package com.vladsch.flexmark.util.sequence;

public class ReplacedTextRegion {
  private final Range base;
  private final Range original;
  private final Range replaced;

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
