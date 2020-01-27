package com.vladsch.flexmark.util.sequence;

import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataKeyBase;
import com.vladsch.flexmark.util.sequence.builder.IBasedSegmentBuilder;
import com.vladsch.flexmark.util.sequence.mappers.CharMapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A BasedSequence which maps characters according to CharMapper
 */
final public class MappedBasedSequence extends BasedSequenceImpl implements MappedSequence<BasedSequence>, ReplacedBasedSequence {
    final private CharMapper mapper;
    final private BasedSequence baseSeq;

    private MappedBasedSequence(BasedSequence baseSeq, CharMapper mapper) {
        super(0);

        this.baseSeq = baseSeq;
        this.mapper = mapper;
    }

    @NotNull
    @Override
    public CharMapper getCharMapper() {
        return mapper;
    }

    @Override
    public char charAt(int index) {
        return mapper.map(baseSeq.charAt(index));
    }

    @NotNull
    @Override
    public BasedSequence getCharSequence() {
        return baseSeq;
    }

    @Override
    public int length() {
        return baseSeq.length();
    }

    @Override
    public @NotNull BasedSequence toMapped(CharMapper mapper) {
        return mapper == CharMapper.IDENTITY ? this : new MappedBasedSequence(baseSeq, this.mapper.andThen(mapper));
    }

    @Override
    public int getOptionFlags() {
        return getBaseSequence().getOptionFlags();
    }

    @Override
    public boolean allOptions(int options) {
        return getBaseSequence().allOptions(options);
    }

    @Override
    public boolean anyOptions(int options) {
        return getBaseSequence().anyOptions(options);
    }

    @Override
    public <T> T getOption(DataKeyBase<T> dataKey) {
        return getBaseSequence().getOption(dataKey);
    }

    @Override
    public @Nullable DataHolder getOptions() {
        return getBaseSequence().getOptions();
    }

    @NotNull
    @Override
    public BasedSequence sequenceOf(@Nullable CharSequence baseSeq, int startIndex, int endIndex) {
        if (baseSeq instanceof MappedBasedSequence) {
            return startIndex == 0 && endIndex == baseSeq.length() ? (BasedSequence) baseSeq : ((BasedSequence) baseSeq).subSequence(startIndex, endIndex).toMapped(mapper);
        } else return new MappedBasedSequence(this.baseSeq.sequenceOf(baseSeq, startIndex, endIndex), mapper);
    }

    @NotNull
    @Override
    public BasedSequence subSequence(int startIndex, int endIndex) {
        SequenceUtils.validateStartEnd(startIndex, endIndex, length());

        if (startIndex == 0 && endIndex == baseSeq.length()) {
            return this;
        }
        return new MappedBasedSequence(baseSeq.subSequence(startIndex, endIndex), mapper);
    }

    @NotNull
    @Override
    public Object getBase() {
        return baseSeq.getBase();
    }

    @NotNull
    @Override
    public BasedSequence getBaseSequence() {
        return baseSeq.getBaseSequence();
    }

    @Override
    public int getStartOffset() {
        return baseSeq.getStartOffset();
    }

    @Override
    public int getEndOffset() {
        return baseSeq.getEndOffset();
    }

    @Override
    public int getIndexOffset(int index) {
        return baseSeq.charAt(index) == charAt(index) ? baseSeq.getIndexOffset(index) : -1;
    }

    @Override
    public void addSegments(@NotNull IBasedSegmentBuilder<?> builder) {
        BasedUtils.generateSegments(builder, this);
    }

    @NotNull
    @Override
    public Range getSourceRange() {
        return baseSeq.getSourceRange();
    }

    @NotNull
    public static BasedSequence mappedOf(@NotNull BasedSequence baseSeq, @NotNull CharMapper mapper) {
        return new MappedBasedSequence(baseSeq, mapper);
    }
}
