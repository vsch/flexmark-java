package org.commonmark.internal.util;

/**
 * A CharSequence that references original string and maps '\0' to '\uFFFD'
 * a subSequence() returns a sub-sequence from the original base string
 */
public class StringSequence extends BasedSequenceImpl  {
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
    public BasedSequence toMapped(CharMapper mapper) {
        return new SubSequence(this, 0, length(), mapper);
    }

    @Override
    public int length() {
        return base.length();
    }

    @Override
    public SourceRange getRange() {
        return new SourceRange(0, length());
    }

    @Override
    public char charAt(int index) {
        return base.charAt(index);
    }

    @Override
    public BasedSequence subSequence(int start, int end) {
        return new SubSequence(this, start, end, NullCharacterMapper.INSTANCE);
    }

    @Override
    public BasedSequence baseSubSequence(int start, int end) {
        if (start < 0 || start > base.length()) {
            throw new StringIndexOutOfBoundsException("String index out of range: " + start);
        }
        if (end < 0 || end > base.length()) {
            throw new StringIndexOutOfBoundsException("String index out of range: " + end);
        }
        return new SubSequence(this, start, end, NullCharacterMapper.INSTANCE);
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
