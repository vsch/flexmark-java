package com.vladsch.flexmark.util.sequence;

import com.vladsch.flexmark.util.mappers.CharMapper;

/**
 * A CharSequence that maps characters according to CharMapper
 */
public final class MappedSequence extends BasedSequenceImpl {
    private final CharMapper mapper;
    private final BasedSequence base;

    public CharMapper getCharMapper() {
        return mapper;
    }

    private MappedSequence(CharMapper mapper, CharSequence baseSeq) {
        this.base = of(baseSeq);
        this.mapper = mapper;
    }

    private MappedSequence(CharMapper mapper, CharSequence baseSeq, int start) {
        this.base = of(baseSeq, start);
        this.mapper = mapper;
    }

    private MappedSequence(CharMapper mapper, CharSequence baseSeq, int start, int end) {
        this.base = of(baseSeq, start, end);
        this.mapper = mapper;
    }

    public static MappedSequence of(CharMapper mapper, CharSequence baseSeq) {
        return new MappedSequence(mapper, baseSeq);
    }

    public static MappedSequence of(CharMapper mapper, CharSequence baseSeq, int start) {
        return new MappedSequence(mapper, baseSeq, start);
    }

    public static MappedSequence of(CharMapper mapper, CharSequence baseSeq, int start, int end) {
        return new MappedSequence(mapper, baseSeq, start, end);
    }

    @Override
    public char charAt(int index) {
        char c = base.charAt(index);
        return mapper.map(c);
    }

    @Override
    public BasedSequence subSequence(int start, int end) {
        final BasedSequence baseSequence = base.subSequence(start, end);
        return baseSequence == base ? this : new MappedSequence(mapper, baseSequence);
    }

    @Override
    public BasedSequence baseSubSequence(final int start, final int end) {
        final BasedSequence baseSequence = base.subSequence(start, end);
        return baseSequence == base ? this : new MappedSequence(mapper, baseSequence);
    }

    @Override
    public Object getBase() {
        return base.getBase();
    }

    @Override
    public BasedSequence getBaseSequence() {
        return base.getBaseSequence();
    }

    @Override
    public int getStartOffset() {
        return base.getStartOffset();
    }

    @Override
    public int getEndOffset() {
        return base.getEndOffset();
    }

    @Override
    public int getIndexOffset(final int index) {
        return base.getIndexOffset(index);
    }

    @Override
    public Range getSourceRange() {
        return base.getSourceRange();
    }

    @Override
    public int length() {
        return base.length();
    }

    @Override
    public String toString() {
        //noinspection StringBufferReplaceableByString
        StringBuilder sb = new StringBuilder(length());
        sb.append(this);
        return sb.toString();
    }
}
