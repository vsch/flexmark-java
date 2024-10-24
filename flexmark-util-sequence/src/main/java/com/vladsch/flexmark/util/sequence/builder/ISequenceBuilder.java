package com.vladsch.flexmark.util.sequence.builder;

public interface ISequenceBuilder<T extends ISequenceBuilder<T, S>, S extends CharSequence>
    extends Appendable {
  /**
   * NOTE: returns non-null value if the underlying {@link
   * IBasedSegmentBuilder#getBaseSubSequenceRange()} returns non-null value
   *
   * @return sub-sequence of base representing the single segment or null if sequence not
   *     representable by a single subsequence
   */
  S getSingleBasedSequence();

  T getBuilder();

  default T addAll(Iterable<? extends CharSequence> sequences) {
    return append(sequences);
  }

  char charAt(int index);

  default T append(Iterable<? extends CharSequence> sequences) {
    for (CharSequence chars : sequences) {
      append(chars, 0, chars.length());
    }
    return (T) this;
  }

  default T add(CharSequence chars) {
    return append(chars);
  }

  @Override
  default T append(CharSequence chars) {
    if (chars != null) {
      return append(chars, 0, chars.length());
    }
    return (T) this;
  }

  default T append(CharSequence chars, int startIndex) {
    if (chars != null) {
      return append(chars, startIndex, chars.length());
    }
    return (T) this;
  }

  @Override
  T append(CharSequence chars, int startIndex, int endIndex);

  @Override
  T append(char c);

  T append(char c, int count);

  S toSequence();

  int length();

  default boolean isEmpty() {
    return length() <= 0;
  }

  default boolean isNotEmpty() {
    return length() > 0;
  }
}
