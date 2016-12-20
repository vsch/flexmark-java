package com.vladsch.flexmark.util.sequence;

import com.vladsch.flexmark.util.mappers.CharMapper;

/**
 * A CharSequence that maps characters according to CharMapper
 */
public class MappedSequence extends SubSequence {
    protected final CharMapper mapper;

    public CharMapper getCharMapper() {
        return mapper;
    }

    public MappedSequence(CharSequence baseSeq, CharMapper mapper) {
        super(baseSeq);
        this.mapper = mapper;
    }

    public MappedSequence(SubSequence baseSeq, int start, int end, CharMapper mapper) {
        super(baseSeq, start, end);
        this.mapper = mapper;
    }

    public MappedSequence(CharSequence baseSeq, int startOffset, int endOffset, CharMapper mapper) {
        super(baseSeq, startOffset, endOffset);
        this.mapper = mapper;
    }

    @Override
    public char charAt(int index) {
        char c = super.charAt(index);
        return mapper.map(c);
    }

    @Override
    public BasedSequence subSequence(int start, int end) {
        return new MappedSequence(base, startOffset + start, startOffset + end, mapper);
    }

    @Override
    public String toString() {
        int iMax = length();
        StringBuilder sb = new StringBuilder(iMax);
        for (int i = 0; i < iMax; i++) {
            sb.append(mapper.map(super.charAt(i)));
        }
        return sb.toString();
    }
}
