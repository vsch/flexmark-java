package com.vladsch.flexmark.util.sequence;

import org.jetbrains.annotations.NotNull;

public interface RichSequence extends IRichSequence<RichSequence> {
  RichSequence NULL = RichSequenceImpl.create("", 0, 0);
  RichSequence[] EMPTY_ARRAY = new RichSequence[0];

  static RichSequence of(CharSequence charSequence) {
    return RichSequenceImpl.create(charSequence, 0, charSequence.length());
  }

  static RichSequence of(CharSequence charSequence, int startIndex) {
    return RichSequenceImpl.create(charSequence, startIndex, charSequence.length());
  }

  static RichSequence of(CharSequence charSequence, int startIndex, int endIndex) {
    return RichSequenceImpl.create(charSequence, startIndex, endIndex);
  }

  @NotNull
  static RichSequence ofSpaces(int count) {
    return of(RepeatedSequence.ofSpaces(count));
  }

  @NotNull
  static RichSequence repeatOf(char c, int count) {
    return of(RepeatedSequence.repeatOf(String.valueOf(c), 0, count));
  }

  @NotNull
  static RichSequence repeatOf(@NotNull CharSequence chars, int count) {
    return of(RepeatedSequence.repeatOf(chars, 0, chars.length() * count));
  }

  @NotNull
  static RichSequence repeatOf(@NotNull CharSequence chars, int startIndex, int endIndex) {
    return of(RepeatedSequence.repeatOf(chars, startIndex, endIndex));
  }
}
