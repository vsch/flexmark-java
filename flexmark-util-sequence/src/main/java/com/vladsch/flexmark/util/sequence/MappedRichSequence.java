package com.vladsch.flexmark.util.sequence;

import com.vladsch.flexmark.util.sequence.builder.ISequenceBuilder;
import com.vladsch.flexmark.util.sequence.builder.RichSequenceBuilder;
import com.vladsch.flexmark.util.sequence.mappers.CharMapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A CharSequence that maps characters according to CharMapper
 */
public class MappedRichSequence extends IRichSequenceBase<RichSequence> implements RichSequence, MappedSequence<RichSequence> {
    final private CharMapper mapper;
    final private RichSequence base;

    private MappedRichSequence(CharMapper mapper, RichSequence baseSeq) {
        super(0);

        this.base = baseSeq;
        this.mapper = mapper;
    }

    @NotNull
    @Override
    public CharMapper getCharMapper() {
        return mapper;
    }

    @NotNull
    @Override
    public RichSequence getCharSequence() {
        return base;
    }

    @Override
    public char charAt(int index) {
        return mapper.map(base.charAt(index));
    }

    public RichSequence getBaseSequence() {
        return base;
    }

    @Override
    public int length() {
        return base.length();
    }

    @NotNull
    @Override
    public RichSequence[] emptyArray() {
        return base.emptyArray();
    }

    @NotNull
    @Override
    public RichSequence nullSequence() {
        return base.nullSequence();
    }

    @NotNull
    @Override
    public RichSequence sequenceOf(@Nullable CharSequence baseSeq, int startIndex, int endIndex) {
        if (baseSeq instanceof MappedRichSequence) {
            return startIndex == 0 && endIndex == baseSeq.length() ? (RichSequence) baseSeq : ((RichSequence) baseSeq).subSequence(startIndex, endIndex).toMapped(mapper);
        } else return new MappedRichSequence(mapper, base.sequenceOf(baseSeq, startIndex, endIndex));
    }

    @Override
    public <B extends ISequenceBuilder<B, RichSequence>> @NotNull B getBuilder() {
        //noinspection unchecked
        return (B) RichSequenceBuilder.emptyBuilder();
    }

    @NotNull
    @Override
    public RichSequence toMapped(CharMapper mapper) {
        return mapper == CharMapper.IDENTITY ? this : new MappedRichSequence(this.mapper.andThen(mapper), base);
    }

    @NotNull
    @Override
    public RichSequence subSequence(int startIndex, int endIndex) {
        RichSequence baseSequence = base.subSequence(startIndex, endIndex);
        return baseSequence == base ? this : new MappedRichSequence(mapper, baseSequence);
    }

    public static RichSequence mappedOf(CharMapper mapper, RichSequence baseSeq) {
        return mappedOf(mapper, baseSeq, 0, baseSeq.length());
    }

    public static RichSequence mappedOf(CharMapper mapper, RichSequence baseSeq, int startIndex) {
        return mappedOf(mapper, baseSeq, startIndex, baseSeq.length());
    }

    public static RichSequence mappedOf(CharMapper mapper, RichSequence baseSeq, int startIndex, int endIndex) {
        if (baseSeq instanceof MappedRichSequence) return startIndex == 0 && endIndex == baseSeq.length() ? baseSeq.toMapped(mapper) : baseSeq.subSequence(startIndex, endIndex).toMapped(mapper);
        else return new MappedRichSequence(mapper, baseSeq.subSequence(startIndex, endIndex));
    }
}
