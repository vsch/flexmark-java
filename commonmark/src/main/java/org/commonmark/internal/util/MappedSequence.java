package org.commonmark.internal.util;

/**
 * A CharSequence that maps characters according to CharMapper
 */
public class MappedSequence extends SubSequence {
    protected final CharMapper mapper;

    public CharMapper getCharMapper() {
        return mapper;
    }


    public MappedSequence(CharSequence base, CharMapper mapper) {
        this(base, 0, base.length(), mapper);
    }

    public MappedSequence(CharSequence base, int startOffset, int endOffset, CharMapper mapper) {
        super(base, startOffset, endOffset);
        this.mapper = mapper;
    }

    @Override
    public char charAt(int index) {
        if (index < 0 || startOffset + index >= endOffset) {
            throw new StringIndexOutOfBoundsException("String index: " + index + " out of range: 0, " + length());
        }
        char c = base.charAt(index + startOffset);
        return mapper.map(c);
    }

    @Override
    public BasedSequence subSequence(int start, int end) {
        if (start < 0 || startOffset + start > endOffset) {
            throw new StringIndexOutOfBoundsException("String index: " + start + " out of range: 0, " + length());
        }
        if (end < 0 || startOffset + end > endOffset) {
            throw new StringIndexOutOfBoundsException("String index: " + end + " out of range: 0, " + length());
        }
        return new MappedSequence(base, startOffset + start, startOffset + end, mapper);
    }

    @Override
    public String toString() {
        int iMax = length();
        StringBuilder sb = new StringBuilder(iMax);
        for (int i = 0; i < iMax; i++) {
            sb.append(mapper.map(base.charAt(i+startOffset)));
        }
        return sb.toString();
    }
}
