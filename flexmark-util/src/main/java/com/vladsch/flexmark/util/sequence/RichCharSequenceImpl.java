package com.vladsch.flexmark.util.sequence;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A CharSequence that references original char sequence and maps '\0' to '\uFFFD'
 * a subSequence() returns a sub-sequence from the original base sequence
 */
final public class RichCharSequenceImpl extends RichCharSequenceBase<RichCharSequenceImpl> {
    public static final RichCharSequenceImpl NULL = new RichCharSequenceImpl("");
    public static final RichCharSequenceImpl[] EMPTY_ARRAY = new RichCharSequenceImpl[0];

    final CharSequence charSequence;

    public RichCharSequenceImpl(CharSequence charSequence) {
        this.charSequence = charSequence;
    }

    @NotNull
    @Override
    public RichCharSequenceImpl[] emptyArray() {
        return EMPTY_ARRAY;
    }

    @NotNull
    @Override
    public RichCharSequenceImpl nullSequence() {
        return NULL;
    }

    @NotNull
    @Override
    public RichCharSequenceImpl sequenceOf(@Nullable CharSequence charSequence, int startIndex, int endIndex) {
        return of(charSequence, startIndex, endIndex);
    }

    @NotNull
    @Override
    public RichCharSequenceImpl subSequence(int start, int end) {
        if (start == 0 && end == charSequence.length()) return this;
        return new RichCharSequenceImpl(charSequence.subSequence(start, end));
    }

    @Override
    public int length() {
        return charSequence.length();
    }

    @Override
    public char charAt(int index) {
        return charSequence.charAt(index);
    }

    public static RichCharSequenceImpl of(CharSequence charSequence) {
        return of(charSequence, 0, charSequence.length());
    }

    public static RichCharSequenceImpl of(CharSequence charSequence, int start) {
        return of(charSequence, start, charSequence.length());
    }

    public static RichCharSequenceImpl of(CharSequence charSequence, int start, int end) {
        if (charSequence instanceof RichCharSequenceImpl) return ((RichCharSequenceImpl) charSequence).subSequence(start, end);
        else if (charSequence != null) {
            if (start == 0 && end == charSequence.length()) return new RichCharSequenceImpl(charSequence);
            else return new RichCharSequenceImpl(charSequence.subSequence(start, end));
        }
        return NULL;
    }
}
