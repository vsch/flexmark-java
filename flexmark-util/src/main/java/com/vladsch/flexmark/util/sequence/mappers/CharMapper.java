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
     * Returns a composed operator that first applies the {@code before}
     * operator to its input, and then applies this operator to the result.
     * If evaluation of either operator throws an exception, it is relayed to
     * the caller of the composed operator.
     *
     * @param before the operator to apply before this operator is applied
     * @return a composed operator that first applies the {@code before}
     *         operator and then applies this operator
     * @throws NullPointerException if before is null
     * @see #andThen(CharMapper)
     */
    default CharMapper compose(CharMapper before) {
        Objects.requireNonNull(before);
        return before == IDENTITY ? this : (char v) -> map(before.map(v));
    }

    /**
     * Returns a composed operator that first applies this operator to
     * its input, and then applies the {@code after} operator to the result.
     * If evaluation of either operator throws an exception, it is relayed to
     * the caller of the composed operator.
     *
     * @param after the operator to apply after this operator is applied
     * @return a composed operator that first applies this operator and then
     *         applies the {@code after} operator
     * @throws NullPointerException if after is null
     * @see #compose(CharMapper)
     */
    default CharMapper andThen(CharMapper after) {
        Objects.requireNonNull(after);
        return after == IDENTITY ? this : (char t) -> after.map(map(t));
    }

    /**
     * Returns a unary operator that always returns its input argument.
     *
     * @return a unary operator that always returns its input argument
     */
    static CharMapper identity() {
        return IDENTITY;
    }
}
