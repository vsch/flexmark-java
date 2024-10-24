package com.vladsch.flexmark.util.sequence.builder;

import com.vladsch.flexmark.util.sequence.RichSequence;

/**
 * A Builder for non based strings. Just a string builder wrapped in a sequence builder interface
 * and wrapping result in RichSequence
 */
public final class RichSequenceBuilder
    implements ISequenceBuilder<RichSequenceBuilder, RichSequence> {

  public static RichSequenceBuilder emptyBuilder() {
    return new RichSequenceBuilder();
  }

  private final StringBuilder segments;

  private RichSequenceBuilder() {
    this.segments = new StringBuilder();
  }

  @Override
  public RichSequenceBuilder getBuilder() {
    return new RichSequenceBuilder();
  }

  @Override
  public char charAt(int index) {
    return segments.charAt(index);
  }

  @Override
  public RichSequenceBuilder append(CharSequence chars, int startIndex, int endIndex) {
    if (chars != null && chars.length() > 0 && startIndex < endIndex) {
      segments.append(chars, startIndex, endIndex);
    }
    return this;
  }

  @Override
  public RichSequenceBuilder append(char c) {
    segments.append(c);
    return this;
  }

  @Override
  public RichSequenceBuilder append(char c, int count) {
    while (count-- > 0) segments.append(c);
    return this;
  }

  @Override
  public RichSequence getSingleBasedSequence() {
    return toSequence();
  }

  @Override
  public RichSequence toSequence() {
    return RichSequence.of(segments);
  }

  @Override
  public int length() {
    return segments.length();
  }

  @Override
  public String toString() {
    return segments.toString();
  }
}
