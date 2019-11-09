package com.vladsch.flexmark.util.sequence;

import com.vladsch.flexmark.util.mappers.CharMapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A RichSequence implementation
 */
public class RichSequenceImpl extends IRichSequenceBase<RichSequence> implements RichSequence {
    final CharSequence charSequence;

    private RichSequenceImpl(CharSequence charSequence) {
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

    @NotNull
    @Override
    public RichSequence subSequence(int startIndex, int endIndex) {
        if (startIndex == 0 && endIndex == charSequence.length()) return this;
        return create(charSequence, startIndex, endIndex);
    }

    @Override
    public int length() {
        return charSequence.length();
    }

    @Override
    public char charAt(int index) {
        return charSequence.charAt(index);
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

    /**
     * @deprecated use {@link RichSequence#of} instead
     */
    @Deprecated
    public static RichSequence of(CharSequence charSequence) {
        return RichSequence.of(charSequence, 0, charSequence.length());
    }

    /**
     * @deprecated use {@link RichSequence#of} instead
     */
    @Deprecated
    public static RichSequence of(CharSequence charSequence, int startIndex) {
        return RichSequence.of(charSequence, startIndex, charSequence.length());
    }

    /**
     * @deprecated use {@link RichSequence#of} instead
     */
    @Deprecated
    public static RichSequence of(CharSequence charSequence, int startIndex, int endIndex) {
        return RichSequence.of(charSequence, startIndex, endIndex);
    }
}
