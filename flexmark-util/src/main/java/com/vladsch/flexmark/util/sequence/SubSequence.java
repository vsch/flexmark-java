package com.vladsch.flexmark.util.sequence;

/**
 * A CharSequence that references original char sequence and maps '\0' to '\uFFFD'
 * a subSequence() returns a sub-sequence from the original base sequence
 */
public class SubSequence extends BasedSequenceImpl {
    protected final CharSequence baseSeq;
    protected final SubSequence base;
    protected final int startOffset;
    protected final int endOffset;

    public SubSequence getBaseSeq() {
        return base;
    }

    public CharSequence getBase() {
        return baseSeq;
    }

    public int getStartOffset() {
        return startOffset;
    }

    public int getEndOffset() {
        return endOffset;
    }

    public SubSequence(CharSequence charSequence) {
        if (charSequence instanceof SubSequence) {
            base = (SubSequence) charSequence;
            baseSeq = base.baseSeq;
            startOffset = base.startOffset;
            endOffset = base.endOffset;
        } else if (charSequence instanceof BasedSequence) {
            base = this;
            baseSeq = ((BasedSequence) charSequence).getBase();
            startOffset = ((BasedSequence) charSequence).getStartOffset();
            endOffset = ((BasedSequence) charSequence).getEndOffset();
        } else {
            base = this;
            baseSeq = charSequence;
            startOffset = 0;
            endOffset = charSequence.length();
        }
    }

    protected SubSequence(SubSequence subSequence, int start, int end) {
        base = subSequence;
        baseSeq = subSequence.baseSeq;
        startOffset = subSequence.startOffset + start;
        endOffset = subSequence.startOffset + end;
    }

    public SubSequence(CharSequence charSequence, int start, int end) {
        if (start == 0 && end == charSequence.length()) {
            if (charSequence instanceof SubSequence) {
                base = (SubSequence) charSequence;
                baseSeq = base.baseSeq;
                startOffset = base.startOffset;
                endOffset = base.endOffset;
            } else if (charSequence instanceof BasedSequence) {
                base = this;
                baseSeq = ((BasedSequence) charSequence).getBase();
                startOffset = ((BasedSequence) charSequence).getStartOffset();
                endOffset = ((BasedSequence) charSequence).getEndOffset();
            } else {
                base = this;
                baseSeq = charSequence;
                startOffset = 0;
                endOffset = charSequence.length();
            }
        } else if (start >= 0 && end <= charSequence.length()) {
            base = new SubSequence(charSequence);
            baseSeq = base.baseSeq;
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
    public BasedSequence subSequence(int start, int end) {
        if (start >= 0 && end <= endOffset - startOffset) {
            return baseSubSequence(startOffset + start, startOffset + end);
        }
        if (start < 0 || startOffset + start > endOffset) {
            throw new StringIndexOutOfBoundsException("SubCharSequence index: " + start + " out of range: 0, " + length());
        }
        throw new StringIndexOutOfBoundsException("SubCharSequence index: " + end + " out of range: 0, " + length());
    }

    @Override
    public BasedSequence baseSubSequence(int start, int end) {
        if (start >= 0 && end <= base.length()) {
            return start == startOffset && end == endOffset ? this : base != this ? base.baseSubSequence(start, end) : new SubSequence(this, start, end);
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
}
