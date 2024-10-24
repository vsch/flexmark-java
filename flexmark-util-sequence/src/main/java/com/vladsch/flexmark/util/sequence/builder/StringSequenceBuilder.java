package com.vladsch.flexmark.util.sequence.builder;

/**
 * A Builder for non based strings. Just a string builder wrapped in a sequence builder interface
 */
public final class StringSequenceBuilder
    implements ISequenceBuilder<StringSequenceBuilder, CharSequence> {

  public static StringSequenceBuilder emptyBuilder() {
    return new StringSequenceBuilder();
  }

  private final StringBuilder segments;

  private StringSequenceBuilder() {
    this.segments = new StringBuilder();
  }

  @Override
  public StringSequenceBuilder getBuilder() {
    return new StringSequenceBuilder();
  }

  @Override
  public char charAt(int index) {
    return segments.charAt(index);
  }

  @Override
  public StringSequenceBuilder append(CharSequence chars, int startIndex, int endIndex) {
    if (chars != null && chars.length() > 0 && startIndex < endIndex) {
      segments.append(chars, startIndex, endIndex);
    }
    return this;
  }

  @Override
  public StringSequenceBuilder append(char c) {
    segments.append(c);
    return this;
  }

  @Override
  public StringSequenceBuilder append(char c, int count) {
    while (count-- > 0) segments.append(c);
    return this;
  }

  @Override
  public CharSequence getSingleBasedSequence() {
    return toSequence();
  }

  @Override
  public CharSequence toSequence() {
    return segments;
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
