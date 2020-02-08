package com.vladsch.flexmark.util.sequence;

import com.vladsch.flexmark.util.sequence.builder.ISequenceBuilder;
import com.vladsch.flexmark.util.sequence.builder.RichSequenceBuilder;
import com.vladsch.flexmark.util.sequence.mappers.CharMapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A RichSequence implementation
 * <p>
 * NOTE: '\0' changed to '\uFFFD' use {@link com.vladsch.flexmark.util.sequence.mappers.NullEncoder#decodeNull} mapper to get original null chars.
 */
public class RichSequenceImpl extends IRichSequenceBase<RichSequence> implements RichSequence {
    final CharSequence charSequence;

    private RichSequenceImpl(CharSequence charSequence) {
        super(charSequence instanceof String ? charSequence.hashCode() : 0);
        this.charSequence = charSequence;
    }

    @NotNull
    @Override
    public RichSequence[] emptyArray() {
        return EMPTY_ARRAY;
    }

    @NotNull
    @Override
    public RichSequence nullSequence() {
        return NULL;
    }

    @NotNull
    @Override
    public RichSequence sequenceOf(@Nullable CharSequence charSequence, int startIndex, int endIndex) {
        return of(charSequence, startIndex, endIndex);
    }

    @Override
    public <B extends ISequenceBuilder<B, RichSequence>> @NotNull B getBuilder() {
        //noinspection unchecked
        return (B) RichSequenceBuilder.emptyBuilder();
    }

    @NotNull
    @Override
    public RichSequence subSequence(int startIndex, int endIndex) {
        SequenceUtils.validateStartEnd(startIndex, endIndex, length());
        if (startIndex == 0 && endIndex == charSequence.length()) return this;
        return create(charSequence, startIndex, endIndex);
    }

    @Override
    public int length() {
        return charSequence.length();
    }

    @Override
    public char charAt(int index) {
        char c = charSequence.charAt(index);
        return c == SequenceUtils.NUL ? SequenceUtils.ENC_NUL : c;
    }

    @NotNull
    @Override
    public RichSequence toMapped(CharMapper mapper) {
        return MappedRichSequence.mappedOf(mapper, this);
    }

    static RichSequence create(CharSequence charSequence, int startIndex, int endIndex) {
        if (charSequence instanceof RichSequence) return ((RichSequence) charSequence).subSequence(startIndex, endIndex);
        else if (charSequence != null) {
            if (startIndex == 0 && endIndex == charSequence.length()) return new RichSequenceImpl(charSequence);
            else return new RichSequenceImpl(charSequence.subSequence(startIndex, endIndex));
        } else return NULL;
    }

    @Deprecated
    public static RichSequence of(CharSequence charSequence) {
        return RichSequence.of(charSequence, 0, charSequence.length());
    }

    @Deprecated
    public static RichSequence of(CharSequence charSequence, int startIndex) {
        return RichSequence.of(charSequence, startIndex, charSequence.length());
    }

    @Deprecated
    public static RichSequence of(CharSequence charSequence, int startIndex, int endIndex) {
        return RichSequence.of(charSequence, startIndex, endIndex);
    }
}
