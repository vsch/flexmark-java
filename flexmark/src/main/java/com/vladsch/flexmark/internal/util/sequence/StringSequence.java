package com.vladsch.flexmark.internal.util.sequence;

/**
 * A CharSequence that references original string and maps '\0' to '\uFFFD'
 * a subSequence() returns a sub-sequence from the original base string
 */
public class StringSequence extends BasedSequenceImpl {
    protected final String base;

    public CharSequence getBase() {
        return base;
    }

    public StringSequence(String base) {
        this.base = base;
    }

    @Override
    public int getStartOffset() {
        return 0;
    }

    @Override
    public int getEndOffset() {
        return length();
    }

    @Override
    public int length() {
        return base.length();
    }

    @Override
    public SourceRange getSourceRange() {
        return new SourceRange(0, length());
    }

    @Override
    public int getIndexOffset(int index) {
        if (index < 0 || index > base.length()) {
            throw new StringIndexOutOfBoundsException("String index: " + index + " out of range: 0, " + length());
        }
        return index;
    }

    @Override
    public char charAt(int index) {
        char c = base.charAt(index);
        return c == '\0' ? '\uFFFD' : c;
    }

    @Override
    public BasedSequence subSequence(int start, int end) {
        return new SubSequence(this, start, end);
    }

    @Override
    public BasedSequence baseSubSequence(int start, int end) {
        if (start < 0 || start > base.length()) {
            throw new StringIndexOutOfBoundsException("String index: " + start + " out of range: 0, " + length());
        }
        if (end < 0 || end > base.length()) {
            throw new StringIndexOutOfBoundsException("String index: " + end + " out of range: 0, " + length());
        }
        return new SubSequence(this, start, end);
    }

    @Override
    public String toString() {
        return base;
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
