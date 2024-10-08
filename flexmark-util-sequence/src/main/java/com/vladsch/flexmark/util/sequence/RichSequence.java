package com.vladsch.flexmark.util.sequence;

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
}
