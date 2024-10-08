package com.vladsch.flexmark.util.sequence.mappers;

import java.util.Objects;

public interface CharMapper {
  CharMapper IDENTITY = t -> t;

  /**
   * Map characters
   *
   * @param codePoint code point
   * @return mapped character
   */
  char map(char codePoint);

  /**
   * Returns a composed operator that first applies this operator to its input, and then applies the
   * {@code after} operator to the result. If evaluation of either operator throws an exception, it
   * is relayed to the caller of the composed operator.
   *
   * @param after the operator to apply after this operator is applied
   * @return a composed operator that first applies this operator and then applies the {@code after}
   *     operator
   * @throws NullPointerException if after is null
   */
  default CharMapper andThen(CharMapper after) {
    Objects.requireNonNull(after);
    return after == IDENTITY ? this : (char t) -> after.map(map(t));
  }
}
