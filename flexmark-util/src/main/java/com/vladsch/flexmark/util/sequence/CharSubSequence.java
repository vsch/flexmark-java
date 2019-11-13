package com.vladsch.flexmark.util.sequence;

import org.jetbrains.annotations.NotNull;

/**
 * A CharSequence that references original char[]
 * a subSequence() returns a sub-sequence from the original base sequence
 * <p>
 * NOTE: '\0' changed to '\uFFFD' use {@link com.vladsch.flexmark.util.mappers.NullEncoder#decodeNull} mapper to get original null chars.
 */
public final class CharSubSequence extends BasedSequenceImpl {
    private final char[] baseChars;
    private final CharSubSequence base;
    private final int startOffset;
    private final int endOffset;

    @NotNull
    @Override
    public CharSubSequence getBaseSequence() {
        return base;
    }

    @NotNull
    @Override
    public char[] getBase() {
        return baseChars;
    }

    public int getStartOffset() {
        return startOffset;
    }

    public int getEndOffset() {
        return endOffset;
    }

    private CharSubSequence(char[] chars, int hash) {
        super(hash);

        int iMax = chars.length;
        base = this;
        baseChars = chars;
        startOffset = 0;
        endOffset = baseChars.length;
    }

    private CharSubSequence(CharSubSequence baseSeq, int startIndex, int endIndex) {
        super(0);

        assert startIndex >= 0 && endIndex >= startIndex && endIndex <= baseSeq.baseChars.length : String.format("CharSubSequence must have (startIndex > 0 || endIndex < %d) && endIndex >= startIndex, got startIndex:%d, endIndex: %d", baseSeq.baseChars.length, startIndex, endIndex);
        assert (startIndex > 0 || endIndex < baseSeq.baseChars.length) : String.format("CharSubSequence must be proper subsequences [1, %d) got startIndex:%d, endIndex: %d", Math.max(0, baseSeq.baseChars.length - 1), startIndex, endIndex);

        base = baseSeq;
        baseChars = baseSeq.baseChars;
        startOffset = base.startOffset + startIndex;
        endOffset = base.startOffset + endIndex;
    }

    @Override
    public int length() {
        return endOffset - startOffset;
    }

    @NotNull
    @Override
    public Range getSourceRange() {
        return Range.of(startOffset, endOffset);
    }

    @Override
    public int getIndexOffset(int index) {
        if (index >= 0 && index <= endOffset - startOffset) {
            return startOffset + index;
        }
        throw new StringIndexOutOfBoundsException("SubCharSequence index: " + index + " out of range: 0, " + length());
    }

    @Override
    public char charAt(int index) {
        if (index >= 0 && index < endOffset - startOffset) {
            char c = baseChars[index + startOffset];
            return c == NUL ? ENC_NUL : c;
        }
        throw new StringIndexOutOfBoundsException("CharSubSequence index: " + index + " out of range: 0, " + length());
    }

    @NotNull
    @Override
    public CharSubSequence subSequence(int startIndex, int endIndex) {
        if (startIndex >= 0 && endIndex <= endOffset - startOffset) {
            return base.baseSubSequence(startOffset + startIndex, startOffset + endIndex);
        }
        if (startIndex < 0 || startOffset + startIndex > endOffset) {
            throw new StringIndexOutOfBoundsException("SubCharSequence index: " + startIndex + " out of range: 0, " + length());
        }
        throw new StringIndexOutOfBoundsException("SubCharSequence index: " + endIndex + " out of range: 0, " + length());
    }

    @NotNull
    @Override
    public CharSubSequence baseSubSequence(int startIndex, int endIndex) {
        if (startIndex >= 0 && endIndex <= baseChars.length) {
            return startIndex == startOffset && endIndex == endOffset ? this : base != this ? base.baseSubSequence(startIndex, endIndex) : new CharSubSequence(base, startIndex, endIndex);
        }
        if (startIndex < 0 || startIndex > base.length()) {
            throw new StringIndexOutOfBoundsException("SubCharSequence index: " + startIndex + " out of range: 0, " + length());
        }
        throw new StringIndexOutOfBoundsException("SubCharSequence index: " + endIndex + " out of range: 0, " + length());
    }

    public static CharSubSequence of(CharSequence charSequence) {
        return of(charSequence, 0, charSequence.length());
    }

    public static CharSubSequence of(CharSequence charSequence, int startIndex) {
        assert startIndex <= charSequence.length();

        return of(charSequence, startIndex, charSequence.length());
    }

    /**
     * @param chars      char array
     * @param startIndex start index in array
     * @param endIndex   end index in array
     * @return CharSubSequence based sequence of array
     * @deprecated NOTE: use BasedSequence.of() for creating based sequences
     */
    @Deprecated
    public static CharSubSequence of(char[] chars, int startIndex, int endIndex) {
        assert startIndex >= 0 && startIndex >= endIndex && endIndex <= chars.length;

        char[] useChars = new char[chars.length];
        System.arraycopy(chars, 0, useChars, 0, chars.length);
        return startIndex == 0 && endIndex == chars.length ? new CharSubSequence(useChars, 0) : new CharSubSequence(useChars, 0).subSequence(startIndex, endIndex);
    }

    /**
     * @param charSequence char sequence
     * @param startIndex   start index in sequence
     * @param endIndex     end index in sequence
     * @return char based sequence
     * @deprecated NOTE: use BasedSequence.of() for creating based sequences
     */
    @Deprecated
    public static CharSubSequence of(CharSequence charSequence, int startIndex, int endIndex) {
        assert startIndex >= 0 && startIndex <= endIndex && endIndex <= charSequence.length();

        CharSubSequence charSubSequence;

        if (charSequence instanceof CharSubSequence) {
            charSubSequence = ((CharSubSequence) charSequence);
        } else if (charSequence instanceof String) {
            charSubSequence = new CharSubSequence(((String) charSequence).toCharArray(), ((String) charSequence).hashCode());
        } else if (charSequence instanceof StringBuilder) {
            char[] chars = new char[charSequence.length()];
            ((StringBuilder) charSequence).getChars(0, charSequence.length(), chars, 0);
            charSubSequence = new CharSubSequence(chars, 0);
        } else {
            charSubSequence = new CharSubSequence(charSequence.toString().toCharArray(), 0);
        }

        if (startIndex == 0 && endIndex == charSequence.length()) {
            return charSubSequence;
        } else {
            return charSubSequence.subSequence(startIndex, endIndex);
        }
    }
}
