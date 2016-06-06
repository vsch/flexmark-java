package com.vladsch.flexmark.internal.util;

import java.util.ArrayList;
import java.util.List;

/**
 * A CharSequence that references original char sequence and maps '\0' to '\uFFFD'
 * a subSequence() returns a sub-sequence from the original base sequence
 */
public class SubSequence extends BasedSequenceImpl {
    final public static BasedSequence EMPTY = new EmptyBasedSequence();
    final public static BasedSequence NULL = new EmptyBasedSequence();

    final public static BasedSequence EOL = new StringSequence("\n");
    final public static List<BasedSequence> EMPTY_LIST = new ArrayList<>();
    
    public static BasedSequence firstNonNull(BasedSequence...sequences) {
        for (BasedSequence sequence : sequences) {
            if (sequence != NULL) return sequence; 
        }
        
        return NULL;
    }

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
            throw new StringIndexOutOfBoundsException("beginIndex:" + startOffset + " must be at least 0");
        }
        if (endOffset < 0) {
            throw new StringIndexOutOfBoundsException("endIndex:" + endOffset + " must be at least 0");
        }
        if (endOffset < startOffset) {
            throw new StringIndexOutOfBoundsException("endIndex:" + endOffset + " must not be less than beginIndex:" + startOffset);
        }
        if (endOffset > base.length()) {
            throw new StringIndexOutOfBoundsException("endIndex:" + endOffset + " must not be greater than length");
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
    public SourceRange getSourceRange() {
        return new SourceRange(startOffset, endOffset);
    }

    @Override
    public int getIndexOffset(int index) {
        if (index < 0 || startOffset + index > endOffset) {
            throw new StringIndexOutOfBoundsException("String index: " + index + " out of range: 0, " + length());
        }
        return startOffset + index;
    }

    @Override
    public char charAt(int index) {
        if (index < 0 || startOffset + index >= endOffset) {
            throw new StringIndexOutOfBoundsException("String index: " + index + " out of range: 0, " + length());
        }
        char c = base.charAt(index + startOffset);
        return c == '\0' ? '\uFFFD' : c;
    }

    @Override
    public BasedSequence subSequence(int start, int end) {
        if (start < 0 || startOffset + start > endOffset) {
            throw new StringIndexOutOfBoundsException("String index: " + start + " out of range: 0, " + length());
        }
        if (end < 0 || startOffset + end > endOffset) {
            throw new StringIndexOutOfBoundsException("String index: " + end + " out of range: 0, " + length());
        }
        return new SubSequence(base, startOffset + start, startOffset + end);
    }

    @Override
    public BasedSequence baseSubSequence(int start, int end) {
        if (start < 0 || start > base.length()) {
            throw new StringIndexOutOfBoundsException("String index: " + start + " out of range: 0, " + length());
        }
        if (end < 0 || end > base.length()) {
            throw new StringIndexOutOfBoundsException("String index: " + end + " out of range: 0, " + length());
        }
        return new SubSequence(base, start, end);
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || (obj instanceof CharSequence && toString().equals(obj.toString()));
    }

    private static class EmptyBasedSequence extends BasedSequenceImpl {
        @Override
        public int length() {
            return 0;
        }

        @Override
        public char charAt(int index) {
            throw new StringIndexOutOfBoundsException("String index: " + index + " out of range: 0, " + length());
        }

        @Override
        public int getIndexOffset(int index) {
            if (index == 0) return 0;
            throw new StringIndexOutOfBoundsException("String index: " + index + " out of range: 0, " + length());
        }

        @Override
        public BasedSequence subSequence(int i, int i1) {
            if (i == 0 && i1 == 0) return this;
            throw new StringIndexOutOfBoundsException("EMPTY substring only subSequence(0, 0) is allowed");
        }

        @Override
        public BasedSequence baseSubSequence(int start, int end) {
            return subSequence(start, end);
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

        @Override
        public SourceRange getSourceRange() {
            return SourceRange.NULL;
        }

        @Override
        public String toString() {
            return "";
        }
    }

}
