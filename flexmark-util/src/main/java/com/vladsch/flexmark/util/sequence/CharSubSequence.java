package com.vladsch.flexmark.util.sequence;

import org.jetbrains.annotations.NotNull;

/**
 * A CharSequence that references original char[]
 * a subSequence() returns a sub-sequence from the original base sequence
 */
public final class CharSubSequence extends BasedSequenceImpl {
    private final char[] baseChars;
    private final CharSubSequence base;
    private final int startOffset;
    private final int endOffset;

    @Override
    public CharSubSequence getBaseSequence() {
        return base;
    }

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

    private CharSubSequence(char[] chars) {
        int iMax = chars.length;
        base = this;
        baseChars = chars;
        startOffset = 0;
        endOffset = baseChars.length;
    }

    private CharSubSequence(CharSubSequence baseSeq, int startIndex, int endIndex) {
        assert startIndex >= 0 && endIndex >= startIndex && endIndex <= baseSeq.baseChars.length : String.format("CharSubSequence must have startIndex >= 0 && endIndex >= startIndex && endIndex <= %d, got startIndex:%d, endIndex: %d", baseSeq.baseChars.length, startIndex, endIndex);

        base = baseSeq;
        baseChars = baseSeq.baseChars;
        startOffset = base.startOffset + startIndex;
        endOffset = base.startOffset + endIndex;
    }

    @Override
    public int length() {
        return endOffset - startOffset;
    }

    @Override
    public Range getSourceRange() {
        return new Range(startOffset, endOffset);
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
            return baseChars[index + startOffset];
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

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @NotNull
    @Override
    public String toString() {
        return String.valueOf(baseChars, startOffset, endOffset - startOffset);
    }

    public static CharSubSequence of(CharSequence charSequence) {
        return of(charSequence, 0, charSequence.length());
    }

    public static CharSubSequence of(CharSequence charSequence, int startIndex) {
        assert startIndex <= charSequence.length();

        return of(charSequence, startIndex, charSequence.length());
    }

    public static CharSubSequence of(char[] chars, int startIndex, int endIndex) {
        assert startIndex >= 0 && startIndex >= endIndex && endIndex <= chars.length;

        char[] useChars = new char[chars.length];
        System.arraycopy(chars, 0, useChars, 0, chars.length);
        return startIndex == 0 && endIndex == chars.length ? new CharSubSequence(useChars) : new CharSubSequence(useChars).subSequence(startIndex, endIndex);
    }

    public static CharSubSequence of(CharSequence charSequence, int startIndex, int endIndex) {
        assert startIndex >= 0 && startIndex <= endIndex && endIndex <= charSequence.length();

        if (startIndex == 0 && endIndex == charSequence.length()) {
            if (charSequence instanceof CharSubSequence) return ((CharSubSequence) charSequence);
            else if (charSequence instanceof String) return new CharSubSequence(((String) charSequence).toCharArray());
            else if (charSequence instanceof StringBuilder) {
                char[] chars = new char[charSequence.length()];
                ((StringBuilder) charSequence).getChars(0, charSequence.length(), chars, 0);
                return new CharSubSequence(chars);
            } else return new CharSubSequence(charSequence.toString().toCharArray());
        } else {
            if (charSequence instanceof CharSubSequence) return ((CharSubSequence) charSequence).subSequence(startIndex, endIndex);
            else if (charSequence instanceof String) return new CharSubSequence(((String) charSequence).toCharArray()).subSequence(startIndex, endIndex);
            else if (charSequence instanceof StringBuilder) {
                char[] chars = new char[charSequence.length()];
                ((StringBuilder) charSequence).getChars(0, charSequence.length(), chars, 0);
                return new CharSubSequence(chars).subSequence(startIndex, endIndex);
            } else return new CharSubSequence(charSequence.toString().toCharArray()).subSequence(startIndex, endIndex);
        }
    }
}
