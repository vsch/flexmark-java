package com.vladsch.flexmark.util.sequence;

/**
 * A CharSequence that references original char sequence and maps '\0' to '\uFFFD'
 * a subSequence() returns a sub-sequence from the original base sequence
 */
public final class SubSequence extends BasedSequenceImpl {
    private final CharSequence baseSeq;
    private final SubSequence base;
    private final int startOffset;
    private final int endOffset;

    @Override
    public SubSequence getBaseSequence() {
        return base;
    }

    @Override
    public Object getBase() {
        return baseSeq;
    }

    public int getStartOffset() {
        return startOffset;
    }

    public int getEndOffset() {
        return endOffset;
    }

    private SubSequence(CharSequence charSequence) {
        assert !(charSequence instanceof BasedSequence);
        base = this;
        baseSeq = charSequence;
        startOffset = 0;
        endOffset = charSequence.length();
    }

    private SubSequence(SubSequence subSequence, int start, int end) {
        assert start > 0 || end < subSequence.length();

        base = subSequence;
        baseSeq = subSequence.baseSeq;
        startOffset = subSequence.startOffset + start;
        endOffset = subSequence.startOffset + end;
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
            char c = baseSeq.charAt(index + startOffset);
            return c == '\0' ? '\uFFFD' : c;
        }
        throw new StringIndexOutOfBoundsException("SubCharSequence index: " + index + " out of range: 0, " + length());
    }

    @Override
    public SubSequence subSequence(final Range range) {
        return subSequence(range.getStart(), range.getEnd());
    }

    @Override
    public SubSequence subSequence(final int start) {
        return subSequence(start, length());
    }

    @Override
    public SubSequence subSequence(int start, int end) {
        if (start >= 0 && end <= endOffset - startOffset) {
            return baseSubSequence(startOffset + start, startOffset + end);
        }
        if (start < 0 || startOffset + start > endOffset) {
            throw new StringIndexOutOfBoundsException("SubCharSequence index: " + start + " out of range: 0, " + length());
        }
        throw new StringIndexOutOfBoundsException("SubCharSequence index: " + end + " out of range: 0, " + length());
    }

    @Override
    public SubSequence baseSubSequence(int start, int end) {
        if (start >= 0 && end <= base.length()) {
            return start == startOffset && end == endOffset ? this : base != this ? base.baseSubSequence(start, end) : new SubSequence(this, start, end);
        }
        if (start < 0 || start > base.length()) {
            throw new StringIndexOutOfBoundsException("SubCharSequence index: " + start + " out of range: 0, " + length());
        }
        throw new StringIndexOutOfBoundsException("SubCharSequence index: " + end + " out of range: 0, " + length());
    }

    @Override
    public BasedSequence appendTo(final StringBuilder out, final int start, final int end) {
        out.append(baseSeq, startOffset + start, startOffset + end);
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
        StringBuilder sb = new StringBuilder(length());
        appendTo(sb, 0, length());
        return sb.toString();
    }

    public static BasedSequence of(CharSequence charSequence) {
        if (charSequence instanceof BasedSequence) return (BasedSequence) charSequence;
        else {
            return charSequence == null ? BasedSequence.NULL : new SubSequence(charSequence);
        }
    }

    public static BasedSequence of(CharSequence charSequence, int start) {
        if (charSequence instanceof BasedSequence) return ((BasedSequence) charSequence).subSequence(start);
        else {
            return charSequence == null ? BasedSequence.NULL : start == 0 ? new SubSequence(charSequence) : new SubSequence(charSequence).subSequence(start, charSequence.length());
        }
    }

    public static BasedSequence of(CharSequence charSequence, int start, int end) {
        if (charSequence instanceof BasedSequence) return ((BasedSequence) charSequence).subSequence(start, end);
        else {
            return charSequence == null ? BasedSequence.NULL : start == 0 && end == charSequence.length() ? new SubSequence(charSequence) : new SubSequence(charSequence).subSequence(start, end);
        }
    }
}
