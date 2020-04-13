package com.vladsch.flexmark.util.misc;

import org.jetbrains.annotations.NotNull;

import java.util.BitSet;
import java.util.Objects;
import java.util.function.IntPredicate;

/**
 * Interface for set of characters to use for inclusion exclusion tests
 * Can be used for code points since the argument is int
 */
public interface CharPredicate extends IntPredicate {
    CharPredicate NONE = value -> false;
    CharPredicate ALL = value -> true;
    CharPredicate SPACE = value -> value == ' ';
    CharPredicate TAB = value -> value == '\t';
    CharPredicate EOL = value -> value == '\n';
    CharPredicate ANY_EOL = value -> value == '\n' || value == '\r';
    CharPredicate ANY_EOL_NUL = value -> value == '\n' || value == '\r' || value == '\0';
    CharPredicate BACKSLASH = value -> value == '\\';
    CharPredicate SLASH = value -> value == '/';
    CharPredicate LINE_SEP = value -> value == '\u2028';
    CharPredicate HASH = value -> value == '#';
    CharPredicate SPACE_TAB = value -> value == ' ' || value == '\t';
    CharPredicate SPACE_TAB_NUL = value -> value == ' ' || value == '\t' || value == '\0';
    CharPredicate SPACE_TAB_LINE_SEP = value -> value == ' ' || value == '\t' || value == '\u2028';
    CharPredicate SPACE_EOL = value -> value == ' ' || value == '\n';
    CharPredicate SPACE_ANY_EOL = value -> value == ' ' || value == '\r' || value == '\n';
    CharPredicate SPACE_TAB_NBSP = value -> value == ' ' || value == '\t' || value == '\u00A0';
    CharPredicate SPACE_TAB_EOL = value -> value == ' ' || value == '\t' || value == '\n';
    CharPredicate WHITESPACE = value -> value == ' ' || value == '\t' || value == '\n' || value == '\r';
    CharPredicate WHITESPACE_OR_NUL = value -> value == ' ' || value == '\t' || value == '\n' || value == '\r' || value == '\0';
    CharPredicate WHITESPACE_NBSP = value -> value == ' ' || value == '\t' || value == '\n' || value == '\r' || value == '\u00A0';
    CharPredicate WHITESPACE_NBSP_OR_NUL = value -> value == ' ' || value == '\t' || value == '\n' || value == '\r' || value == '\u00A0' || value == '\0';
    CharPredicate HEXADECIMAL_DIGITS = value -> value >= '0' && value <= '9' || value >= 'a' && value <= 'f' || value >= 'A' && value <= 'F';
    CharPredicate DECIMAL_DIGITS = value -> value >= '0' && value <= '9';
    CharPredicate OCTAL_DIGITS = value -> value >= '0' && value <= '7';
    CharPredicate BINARY_DIGITS = value -> value >= '0' && value <= '1';

    @Deprecated
    CharPredicate FALSE = NONE;
    @Deprecated
    CharPredicate TRUE = ALL;
    @Deprecated
    CharPredicate SPACE_TAB_OR_NUL = SPACE_TAB_NUL;

    @Override
    boolean test(int value);

    default boolean test(char value) {
        return test((int) value);
    }

    /**
     * Returns a composed predicate that represents a short-circuiting logical
     * AND of this predicate and another.  When evaluating the composed
     * predicate, if this predicate is {@code false}, then the {@code other}
     * predicate is not evaluated.
     *
     * <p>Any exceptions thrown during evaluation of either predicate are relayed
     * to the caller; if evaluation of this predicate throws an exception, the
     * {@code other} predicate will not be evaluated.
     *
     * @param other a predicate that will be logically-ANDed with this
     *              predicate
     * @return a composed predicate that represents the short-circuiting logical
     *         AND of this predicate and the {@code other} predicate
     * @throws NullPointerException if other is null
     */
    @NotNull
    default CharPredicate and(@NotNull CharPredicate other) {
        Objects.requireNonNull(other);
        return this == NONE || other == NONE ? NONE : this == ALL ? other : other == ALL ? this : (value) -> test(value) && other.test(value);
    }

    /**
     * Returns a predicate that represents the logical negation of this
     * predicate.
     *
     * @return a predicate that represents the logical negation of this
     *         predicate
     */
    @NotNull
    default CharPredicate negate() {
        return this == NONE ? ALL : this == ALL ? NONE : (value) -> !test(value);
    }

    /**
     * Returns a composed predicate that represents a short-circuiting logical
     * OR of this predicate and another.  When evaluating the composed
     * predicate, if this predicate is {@code true}, then the {@code other}
     * predicate is not evaluated.
     *
     * <p>Any exceptions thrown during evaluation of either predicate are relayed
     * to the caller; if evaluation of this predicate throws an exception, the
     * {@code other} predicate will not be evaluated.
     *
     * @param other a predicate that will be logically-ORed with this
     *              predicate
     * @return a composed predicate that represents the short-circuiting logical
     *         OR of this predicate and the {@code other} predicate
     * @throws NullPointerException if other is null
     */
    @NotNull
    default CharPredicate or(@NotNull CharPredicate other) {
        Objects.requireNonNull(other);
        return this == ALL || other == ALL ? ALL : this == NONE ? other : other == NONE ? this : (value) -> test(value) || other.test(value);
    }

    @NotNull
    static CharPredicate standardOrAnyOf(char c1) {
        return SPACE.test(c1) ? SPACE
                : EOL.test(c1) ? EOL
                : TAB.test(c1) ? TAB
                : value1 -> value1 == (int) c1;
    }

    @NotNull
    static CharPredicate standardOrAnyOf(char c1, char c2) {
        return c1 == c2 ? standardOrAnyOf(c1)
                : SPACE_TAB.test(c1) && SPACE_TAB.test(c2) ? SPACE_TAB
                : ANY_EOL.test(c1) && ANY_EOL.test(c2) ? ANY_EOL
                : value -> value == (int) c1 || value == (int) c2;
    }

    @NotNull
    static CharPredicate standardOrAnyOf(char c1, char c2, char c3) {
        return c1 == c2 && c2 == c3 ? standardOrAnyOf(c1)
                : c1 == c2 || c1 == c3 ? standardOrAnyOf(c2, c3)
                : c2 == c3 ? standardOrAnyOf(c1, c3)
                : value -> value == (int) c1 || value == (int) c2 || value == (int) c3;
    }

    @NotNull
    static CharPredicate standardOrAnyOf(char c1, char c2, char c3, char c4) {
        return c1 == c2 && c2 == c3 && c3 == c4 ? standardOrAnyOf(c1)
                : c1 == c2 || c1 == c3 || c1 == c4 ? standardOrAnyOf(c2, c3, c4)
                : c2 == c3 || c2 == c4 ? standardOrAnyOf(c1, c3, c4)
                : c3 == c4 ? standardOrAnyOf(c1, c2, c3)
                : WHITESPACE.test(c1) && WHITESPACE.test(c2) && WHITESPACE.test(c3) && WHITESPACE.test(c4) ? WHITESPACE
                : value -> value == (int) c1 || value == (int) c2 || value == (int) c3 || value == (int) c4;
    }

    @NotNull
    static CharPredicate anyOf(char... chars) {
        switch (chars.length) {
            case 0:
                return NONE;
            case 1:
                return standardOrAnyOf(chars[0]);
            case 2:
                return standardOrAnyOf(chars[0], chars[1]);
            case 3:
                return standardOrAnyOf(chars[0], chars[1], chars[2]);
            case 4:
                return standardOrAnyOf(chars[0], chars[1], chars[2], chars[3]);
            default:
                return anyOf(String.valueOf(chars));
        }
    }

    static int indexOf(@NotNull CharSequence thizz, char c) {
        return indexOf(thizz, c, 0, thizz.length());
    }

    static int indexOf(@NotNull CharSequence thizz, char c, int fromIndex, int endIndex) {
        fromIndex = Math.max(fromIndex, 0);
        endIndex = Math.min(thizz.length(), endIndex);

        for (int i = fromIndex; i < endIndex; i++) {
            if (c == thizz.charAt(i)) return i;
        }
        return -1;
    }

    @NotNull
    static CharPredicate anyOf(@NotNull CharSequence chars) {
        int maxFixed = 4;
        switch (chars.length()) {
            case 0:
                return NONE;
            case 1:
                return standardOrAnyOf(chars.charAt(0));
            case 2:
                return standardOrAnyOf(chars.charAt(0), chars.charAt(1));
            case 3:
                return standardOrAnyOf(chars.charAt(0), chars.charAt(1), chars.charAt(2));
            case 4:
                return standardOrAnyOf(chars.charAt(0), chars.charAt(1), chars.charAt(2), chars.charAt(3));
            default:
                // create bit set for ascii and add any above as a string index of test
                BitSet ascii = null;
                StringBuilder others = null;
                int iMax = chars.length();

                for (int i = 0; i < iMax; i++) {
                    char c = chars.charAt(i);
                    if (c <= 127) {
                        if (ascii == null) ascii = new BitSet();
                        ascii.set(c);
                    } else {
                        if (others == null) others = new StringBuilder();
                        if (indexOf(others, c) == -1) {
                            others.append(c);
                        }
                    }
                }

                String finalOthers = others == null ? null : others.toString();
                CharPredicate testOthers = finalOthers == null || finalOthers.isEmpty() ? null : finalOthers.length() <= maxFixed ? anyOf(others) : value -> indexOf(finalOthers, (char) value) != -1;
                CharPredicate testAscii = ascii == null || ascii.cardinality() == 0 ? null : ascii::get;

                assert testAscii != null || testOthers != null;

                if (testAscii != null && testOthers != null) {
                    return testAscii.or(testOthers);
                } else if (testAscii != null) {
                    return testAscii;
                } else {
                    return testOthers;
                }
        }
    }
}
