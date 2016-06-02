package org.commonmark.internal.util;

/**
 * A CharSequence that references original char sequence and maps '\0' to '\uFFFD'
 * a subSequence() returns a sub-sequence from the original base sequence
 */
public class SubSequence implements BasedSequence {
    final public static BasedSequence EMPTY = new BasedSequence() {
        @Override
        public int length() {
            return 0;
        }

        @Override
        public char charAt(int i) {
            throw new StringIndexOutOfBoundsException("String index out of range");
        }

        @Override
        public BasedSequence subSequence(int i, int i1) {
            if (i == 0 && i1 == 0) return this;
            throw new StringIndexOutOfBoundsException("EMPTY substring only subSequence(0, 0) is allowed");
        }

        @Override
        public CharSequence getBase() {
            return Substring.EMPTY;
        }

        @Override
        public int getStartOffset() {
            return 0;
        }

        @Override
        public int getEndOffset() {
            return 0;
        }
    };

    protected final CharSequence base;
    protected final int startOffset;
    protected final int endOffset;

    public CharSequence getBase() {
        return base;
    }

    public int getStartOffset() {
        return startOffset;
    }

    public int getEndOffset() {
        return endOffset;
    }

    public SubSequence(CharSequence base) {
        this(base, 0, base.length());
    }

    public SubSequence(CharSequence base, int startOffset, int endOffset) {
        if (startOffset < 0) {
            throw new StringIndexOutOfBoundsException("beginIndex must be at least 0");
        }
        if (endOffset < 0) {
            throw new StringIndexOutOfBoundsException("endIndex must be at least 0");
        }
        if (endOffset < startOffset) {
            throw new StringIndexOutOfBoundsException("endIndex must not be less than beginIndex");
        }
        if (endOffset > base.length()) {
            throw new StringIndexOutOfBoundsException("endIndex must not be greater than length");
        }
        this.base = base;
        this.startOffset = startOffset;
        this.endOffset = endOffset;
    }

    @Override
    public int length() {
        return endOffset - startOffset;
    }

    @Override
    public char charAt(int index) {
        if (index < 0 || startOffset + index >= endOffset) {
            throw new StringIndexOutOfBoundsException("String index out of range: " + index);
        }
        char c = base.charAt(index + startOffset);
        return c == '\0' ? '\uFFFD' : c;
    }

    @Override
    public BasedSequence subSequence(int start, int end) {
        if (start < 0 || startOffset + start > endOffset) {
            throw new StringIndexOutOfBoundsException("String index out of range: " + start);
        }
        if (end < 0 || startOffset + end > endOffset) {
            throw new StringIndexOutOfBoundsException("String index out of range: " + end);
        }
        return new SubSequence(base, startOffset + start, startOffset + end);
    }

    @Override
    public String toString() {
        return String.valueOf(base.subSequence(startOffset, endOffset));
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
