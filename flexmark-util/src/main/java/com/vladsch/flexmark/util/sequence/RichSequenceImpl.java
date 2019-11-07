package com.vladsch.flexmark.util.sequence;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A CharSequence that references original char sequence and maps '\0' to '\uFFFD'
 * a subSequence() returns a sub-sequence from the original base sequence
 */
final public class RichSequenceImpl extends RichSequenceBase<RichSequenceImpl> {
    public static final RichSequenceImpl NULL = new RichSequenceImpl("");
    public static final RichSequenceImpl[] EMPTY_ARRAY = new RichSequenceImpl[0];

    final CharSequence charSequence;

    public RichSequenceImpl(CharSequence charSequence) {
        this.charSequence = charSequence;
    }

    @NotNull
    @Override
    public RichSequenceImpl[] emptyArray() {
        return EMPTY_ARRAY;
    }

    @NotNull
    @Override
    public RichSequenceImpl nullSequence() {
        return NULL;
    }

    @NotNull
    @Override
    public RichSequenceImpl sequenceOf(@Nullable CharSequence charSequence, int startIndex, int endIndex) {
        return of(charSequence, startIndex, endIndex);
    }

    @NotNull
    @Override
    public RichSequenceImpl subSequence(int start, int end) {
        if (start == 0 && end == charSequence.length()) return this;
        return new RichSequenceImpl(charSequence.subSequence(start, end));
    }

    @Override
    public int length() {
        return charSequence.length();
    }

    @Override
    public char charAt(int index) {
        return charSequence.charAt(index);
    }

    public static RichSequenceImpl of(CharSequence charSequence) {
        return of(charSequence, 0, charSequence.length());
    }

    public static RichSequenceImpl of(CharSequence charSequence, int start) {
        return of(charSequence, start, charSequence.length());
    }

    public static RichSequenceImpl of(CharSequence charSequence, int start, int end) {
        if (charSequence instanceof RichSequenceImpl) return ((RichSequenceImpl) charSequence).subSequence(start, end);
        else if (charSequence != null) {
            if (start == 0 && end == charSequence.length()) return new RichSequenceImpl(charSequence);
            else return new RichSequenceImpl(charSequence.subSequence(start, end));
        }
        return NULL;
    }
}
