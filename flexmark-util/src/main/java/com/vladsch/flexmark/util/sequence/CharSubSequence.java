package com.vladsch.flexmark.util.sequence;

/**
 * A CharSequence that references original char[] and maps '\0' to '\uFFFD'
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
        for (int i = 0; i < iMax; i++) {
            if (chars[i] == '\0') chars[i] = '\uFFFD';
        }

        baseChars = chars;
        startOffset = 0;
        endOffset = baseChars.length;
        base = this;
    }

    private CharSubSequence(CharSubSequence baseSeq, int start, int end) {
        //if (start < 0 || end < 0 || end < start) {
        //    int tmp = 0;
        //}
        assert start >= 0 && end >= 0 && end >= start;

        //if (start <= 0 && end >= baseSeq.length()) {
        //    int tmp = 0;
        //}
        assert start > 0 || end < baseSeq.length();

        base = baseSeq;
        baseChars = baseSeq.baseChars;
        startOffset = base.startOffset + start;
        endOffset = base.startOffset + end;
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
        if (index >= 0 || index <= endOffset - startOffset) {
            return startOffset + index;
        }
        throw new StringIndexOutOfBoundsException("SubCharSequence index: " + index + " out of range: 0, " + length());
    }

    @Override
    public char charAt(int index) {
        if (index >= 0 || index < endOffset - startOffset) {
            return baseChars[index + startOffset];
        }
        throw new StringIndexOutOfBoundsException("SubCharSequence index: " + index + " out of range: 0, " + length());
    }

    @Override
    public CharSubSequence subSequence(final Range range) {
        return subSequence(range.getStart(), range.getEnd());
    }

    @Override
    public CharSubSequence subSequence(final int start) {
        return subSequence(start, length());
    }

    @Override
    public CharSubSequence subSequence(int start, int end) {
        if (start >= 0 && end <= endOffset - startOffset) {
            return base.baseSubSequence(startOffset + start, startOffset + end);
        }
        if (start < 0 || startOffset + start > endOffset) {
            throw new StringIndexOutOfBoundsException("SubCharSequence index: " + start + " out of range: 0, " + length());
        }
        throw new StringIndexOutOfBoundsException("SubCharSequence index: " + end + " out of range: 0, " + length());
    }

    @Override
    public CharSubSequence baseSubSequence(int start, int end) {
        if (start >= 0 && end <= baseChars.length) {
            return start == startOffset && end == endOffset ? this : base != this ? base.baseSubSequence(start, end) : new CharSubSequence(base, start, end);
        }
        if (start < 0 || start > base.length()) {
            throw new StringIndexOutOfBoundsException("SubCharSequence index: " + start + " out of range: 0, " + length());
        }
        throw new StringIndexOutOfBoundsException("SubCharSequence index: " + end + " out of range: 0, " + length());
    }

    @Override
    public BasedSequence appendTo(final StringBuilder out, final int start, final int end) {
        out.append(baseChars, startOffset + start, end - start);
        return this;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || (obj instanceof CharSequence && toString().equals(obj.toString()));
    }

    @Override
    public String toString() {
        return String.valueOf(baseChars, startOffset, endOffset - startOffset);
    }

    public static CharSubSequence of(CharSequence charSequence) {
        return of(charSequence, 0, charSequence.length());
    }

    public static CharSubSequence of(CharSequence charSequence, int start) {
        return of(charSequence, start, charSequence.length());
    }

    public static CharSubSequence of(char[] chars, int start, int end) {
        char[] useChars = new char[chars.length];
        System.arraycopy(chars, 0, useChars, 0, chars.length);
        return start == 0 && end == chars.length ? new CharSubSequence(useChars) : new CharSubSequence(useChars).subSequence(start, end);
    }

    public static CharSubSequence of(CharSequence charSequence, int start, int end) {
        if (start == 0 && end == charSequence.length()) {
            if (charSequence instanceof CharSubSequence) return ((CharSubSequence) charSequence);
            else if (charSequence instanceof String) return new CharSubSequence(((String) charSequence).toCharArray());
            else if (charSequence instanceof StringBuilder) {
                char[] chars = new char[charSequence.length()];
                ((StringBuilder) charSequence).getChars(0, charSequence.length(), chars, 0);
                return new CharSubSequence(chars);
            } else return new CharSubSequence(charSequence.toString().toCharArray());
        } else {
            if (charSequence instanceof CharSubSequence) return ((CharSubSequence) charSequence).subSequence(start, end);
            else if (charSequence instanceof String) return new CharSubSequence(((String) charSequence).toCharArray()).subSequence(start, end);
            else if (charSequence instanceof StringBuilder) {
                char[] chars = new char[charSequence.length()];
                ((StringBuilder) charSequence).getChars(0, charSequence.length(), chars, 0);
                return new CharSubSequence(chars).subSequence(start, end);
            } else return new CharSubSequence(charSequence.toString().toCharArray()).subSequence(start, end);
        }
    }
}
