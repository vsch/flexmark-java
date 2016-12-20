package com.vladsch.flexmark.util.sequence;

import com.sun.tools.javac.util.ArrayUtils;
import com.sun.xml.internal.fastinfoset.util.CharArray;

/**
 * A CharSequence that references original char[] and maps '\0' to '\uFFFD'
 * a subSequence() returns a sub-sequence from the original base sequence
 */
public final class SubCharSequence extends BasedSequenceImpl {
    private final char[] baseChars;
    private final SubCharSequence base;
    private final int startOffset;
    private final int endOffset;

    public SubCharSequence getBase() {
        return base;
    }

    public char[] getBaseChars() {
        return baseChars;
    }

    public int getStartOffset() {
        return startOffset;
    }

    public int getEndOffset() {
        return endOffset;
    }

    public SubCharSequence(CharSequence charSequence) {
        this(charSequence.toString());
    }

    public SubCharSequence(String text) {
        this(text.toCharArray());
    }

    public SubCharSequence(char[] chars) {
        int iMax = chars.length;
        for (int i = 0; i < iMax; i++) {
            if (chars[i] == '\0') chars[i] = '\uFFFD';
        }
        
        baseChars = chars;
        startOffset = 0;
        endOffset = baseChars.length;
        base = this;
    }

    private SubCharSequence(SubCharSequence baseSeq, int start, int end) {
        base = baseSeq;
        baseChars = baseSeq.baseChars;
        startOffset = base.startOffset + start;
        endOffset = base.startOffset + end;
    }

    public SubCharSequence(CharSequence charSequence, int start, int end) {
        this(charSequence.toString(), start, end);
    }

    public SubCharSequence(String text, int start, int end) {
        this(text.toCharArray(), start, end);
    }

    public SubCharSequence(char[] chars, int start, int end) {
        if (start == 0 && end == chars.length) {
            baseChars = chars;
            startOffset = 0;
            endOffset = chars.length;
            base = this;
        } else if (start >= 0 && end <= chars.length) {
            base = new SubCharSequence(chars);
            baseChars = base.baseChars;
            startOffset = base.startOffset + start;
            endOffset = base.startOffset + end;
        } else {
            if (start < 0) {
                throw new StringIndexOutOfBoundsException("beginIndex:" + start + " must be at least 0");
            }
            if (end < 0) {
                throw new StringIndexOutOfBoundsException("endIndex:" + end + " must be at least 0");
            }
            if (end < start) {
                throw new StringIndexOutOfBoundsException("endIndex:" + end + " must not be less than beginIndex:" + start);
            }
            throw new StringIndexOutOfBoundsException("endIndex:" + end + " must not be greater than length");
        }
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
    public SubCharSequence subSequence(int start, int end) {
        if (start >= 0 && end <= endOffset - startOffset) {
            return base.baseSubSequence(startOffset + start, startOffset + end);
        }
        if (start < 0 || startOffset + start > endOffset) {
            throw new StringIndexOutOfBoundsException("SubCharSequence index: " + start + " out of range: 0, " + length());
        }
        throw new StringIndexOutOfBoundsException("SubCharSequence index: " + end + " out of range: 0, " + length());
    }

    @Override
    public SubCharSequence baseSubSequence(int start, int end) {
        if (start >= 0 && end <= baseChars.length) {
            return start == startOffset && end == endOffset ? this : base != this ? base.baseSubSequence(start, end) : new SubCharSequence(base, start, end);
        }
        if (start < 0 || start > base.length()) {
            throw new StringIndexOutOfBoundsException("SubCharSequence index: " + start + " out of range: 0, " + length());
        }
        throw new StringIndexOutOfBoundsException("SubCharSequence index: " + end + " out of range: 0, " + length());
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
}
