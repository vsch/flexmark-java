package org.commonmark.internal.util;

import org.commonmark.internal.util.mappers.LowerCaseMapper;
import org.commonmark.internal.util.mappers.UpperCaseMapper;

import java.util.Locale;

/**
 * A CharSequence that references original char sequence and maps '\0' to '\uFFFD'
 * a subSequence() returns a sub-sequence from the original base sequence
 */
public abstract class BasedSequenceImpl implements BasedSequence {

    public static final String WHITESPACE_CHARS = " \t\n";

    @Override
    public int countLeading(String chars) {
        return countChars(chars, 0, length());
    }

    @Override
    public int countLeadingNot(String chars) {
        return countNotChars(chars, 0, length());
    }

    @Override
    public int countTrailing(String chars) {
        return countCharsReversed(chars, 0, length());
    }

    @Override
    public int countTrailingNot(String chars) {
        return countNotCharsReversed(chars, 0, length());
    }

    @Override
    public BasedSequence trimStart(String chars) {
        int trim = countChars(chars, 0, length());
        return trim > 0 ? subSequence(trim, length()) : this;
    }

    @Override
    public BasedSequence trimEnd(String chars) {
        int trim = countCharsReversed(chars, 0, length());
        return trim > 0 ? subSequence(0, length() - trim) : this;
    }

    @Override
    public BasedSequence trim(String chars) {
        int trimStart = countChars(chars, 0, length());
        int trimEnd = countCharsReversed(chars, 0, length());
        return trimStart > 0 || trimEnd > 0 ? subSequence(trimStart, length() - trimEnd) : this;
    }

    @Override
    public BasedSequence trimStart() {
        int trim = countChars(WHITESPACE_CHARS, 0, length());
        return trim > 0 ? subSequence(trim, length()) : this;
    }

    @Override
    public BasedSequence trimEnd() {
        int trim = countCharsReversed(WHITESPACE_CHARS, 0, length());
        return trim > 0 ? subSequence(0, length() - trim) : this;
    }

    @Override
    public BasedSequence trim() {
        int trimStart = countChars(WHITESPACE_CHARS, 0, length());
        if (trimStart == length()) {
            return subSequence(trimStart, trimStart);
        }

        int trimEnd = countCharsReversed(WHITESPACE_CHARS, 0, length());
        return trimStart > 0 || trimEnd > 0 ? subSequence(trimStart, length() - trimEnd) : this;
    }

    @Override
    public boolean isEmpty() {
        return length() == 0;
    }

    @Override
    public boolean isBlank() {
        int trim = countChars(WHITESPACE_CHARS, 0, length());
        return trim == length();
    }

    @Override
    public boolean endsWith(String suffix) {
        return matchCharsReversed(suffix, length());
    }

    @Override
    public boolean startsWith(String prefix) {
        return matchChars(prefix, 0);
    }

    @Override
    public MappedSequence toLowerCase() {
        return toMapped(LowerCaseMapper.INSTANCE);
    }

    @Override
    public MappedSequence toUpperCase() {
        return toMapped(UpperCaseMapper.INSTANCE);
    }

    @Override
    public MappedSequence toLowerCase(Locale locale) {
        return toMapped(new LowerCaseMapper(locale));
    }

    @Override
    public MappedSequence toUpperCase(Locale locale) {
        return toMapped(new UpperCaseMapper(locale));
    }

    @Override
    final public MappedSequence toMapped(CharMapper mapper) {
        return new MappedSequence(this, mapper);
    }

    private static boolean contains(String chars, char c) {
        int iMax = chars.length();

        for (int i = 0; i < iMax; i++) {
            if (chars.charAt(i) == c) return true;
        }

        return false;
    }

    @Override
    public boolean matchChars(String chars, int startIndex) {
        int iMax = chars.length();
        if (iMax > length() - startIndex) return false;

        for (int i = 0; i < iMax; i++) {
            if (chars.charAt(i) != charAt(i + startIndex)) return false;
        }
        return true;
    }

    @Override
    public boolean matchCharsReversed(String chars, int endIndex) {
        return matchChars(chars, endIndex - chars.length());
    }

    @Override
    public int countChars(String chars, int startIndex, int endIndex) {
        int count = 0;

        for (int i = startIndex; i < endIndex; i++) {
            if (!contains(chars, charAt(i))) break;
            count++;
        }

        return count;
    }

    @Override
    public int countCharsReversed(String chars, int startIndex, int endIndex) {
        int count = 0;

        for (int i = endIndex; i-- > startIndex; ) {
            if (!contains(chars, charAt(i))) break;
            count++;
        }

        return count;
    }

    @Override
    public int countNotChars(String chars, int startIndex, int endIndex) {
        int count = 0;

        for (int i = startIndex; i < endIndex; i++) {
            if (contains(chars, charAt(i))) break;
            count++;
        }

        return count;
    }

    @Override
    public int countNotCharsReversed(String chars, int startIndex, int endIndex) {
        int count = 0;

        for (int i = endIndex; i-- > startIndex; ) {
            if (contains(chars, charAt(i))) break;
            count++;
        }

        return count;
    }

    @Override
    public int countChars(String chars) {
        return countChars(chars, 0, chars.length());
    }

    @Override
    public int countCharsReversed(String chars) {
        return countCharsReversed(chars, 0, chars.length());
    }

    @Override
    public int countNotChars(String chars) {
        return countNotChars(chars, 0, chars.length());
    }

    @Override
    public int countNotCharsReversed(String chars) {
        return countNotCharsReversed(chars, 0, chars.length());
    }

    @Override
    public BasedSequence subSequence(int start) {
        return subSequence(start, length());
    }

    @Override
    public String toString() {
        int iMax = length();
        StringBuilder sb = new StringBuilder(iMax);

        for (int i = 0; i < iMax; i++) {
            sb.append(charAt(i));
        }

        return sb.toString();
    }
}
