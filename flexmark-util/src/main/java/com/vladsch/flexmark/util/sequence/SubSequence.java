package com.vladsch.flexmark.util.sequence;

import org.jetbrains.annotations.NotNull;

/**
 * A BasedSequence implementation which wraps original CharSequence to provide a BasedSequence for
 * all its sub sequences, a subSequence() returns a SubSequence from the original base sequence.
 * <p>
 * NOTE: No substitution is performed for '\0' encoding. If your input sequence has '\0'
 * which you want preserved then your text should be wrapped in a {@link MappedRichSequence}
 * wrapper with {@link com.vladsch.flexmark.util.mappers.NullEncoder#encodeNull} mapper
 * and {@link com.vladsch.flexmark.util.mappers.NullEncoder#decodeNull} mapper to get original null chars
 * <p>
 */
public final class SubSequence extends BasedSequenceImpl {
    private final CharSequence baseSeq;
    private final SubSequence base;
    private final int startOffset;
    private final int endOffset;

    @Override
    public SubSequence getBaseSequence() {
        return base;
    }

    @Override
    public Object getBase() {
        return baseSeq;
    }

    public int getStartOffset() {
        return startOffset;
    }

    public int getEndOffset() {
        return endOffset;
    }

    private SubSequence(CharSequence charSequence) {
        assert !(charSequence instanceof BasedSequence);
        base = this;
        baseSeq = charSequence;
        startOffset = 0;
        endOffset = charSequence.length();
    }

    private SubSequence(SubSequence subSequence, int startIndex, int endIndex) {
        assert startIndex >= 0 && endIndex >= startIndex && endIndex <= subSequence.length() : String.format("SubSequence must have startIndex >= 0 && endIndex >= startIndex && endIndex <= %d, got startIndex:%d, endIndex: %d", subSequence.length(), startIndex, endIndex);

        base = subSequence;
        baseSeq = subSequence.baseSeq;
        startOffset = subSequence.startOffset + startIndex;
        endOffset = subSequence.startOffset + endIndex;
    }

    @Override
    public int length() {
        return endOffset - startOffset;
    }

    @Override
    public Range getSourceRange() {
        return new Range(startOffset, endOffset);
    }

    @Override
    public int getIndexOffset(int index) {
        if (index >= 0 && index <= endOffset - startOffset) {
            return startOffset + index;
        }
        throw new StringIndexOutOfBoundsException("SubCharSequence index: " + index + " out of range: 0, " + length());
    }

    @Override
    public char charAt(int index) {
        if (index >= 0 && index < endOffset - startOffset) {
            char c = baseSeq.charAt(index + startOffset);
            return c == NUL ? ENC_NUL : c;
        }
        throw new StringIndexOutOfBoundsException("SubSequence index: " + index + " out of range: 0, " + length());
    }

    @NotNull
    @Override
    public SubSequence subSequence(int startIndex, int endIndex) {
        if (startIndex >= 0 && endIndex <= endOffset - startOffset) {
            return baseSubSequence(startOffset + startIndex, startOffset + endIndex);
        }
        if (startIndex < 0 || startOffset + startIndex > endOffset) {
            throw new StringIndexOutOfBoundsException("SubCharSequence index: " + startIndex + " out of range: 0, " + length());
        }
        throw new StringIndexOutOfBoundsException("SubCharSequence index: " + endIndex + " out of range: 0, " + length());
    }

    @Override
    public SubSequence baseSubSequence(int startIndex, int endIndex) {
        if (startIndex >= 0 && endIndex <= base.length()) {
            return startIndex == startOffset && endIndex == endOffset ? this : base != this ? base.baseSubSequence(startIndex, endIndex) : new SubSequence(this, startIndex, endIndex);
        }
        if (startIndex < 0 || startIndex > base.length()) {
            throw new StringIndexOutOfBoundsException("SubCharSequence index: " + startIndex + " out of range: 0, " + length());
        }
        throw new StringIndexOutOfBoundsException("SubCharSequence index: " + endIndex + " out of range: 0, " + length());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    static BasedSequence create(CharSequence charSequence, int startIndex, int endIndex) {
        if (charSequence instanceof BasedSequence) return ((BasedSequence) charSequence).subSequence(startIndex, endIndex);
        else return charSequence == null ? BasedSequence.NULL : new SubSequence(charSequence).subSequence(startIndex, endIndex);
    }

    /**
     * @deprecated  use {@link BasedSequence#of} instead
     */
    @Deprecated
    public static BasedSequence of(CharSequence charSequence) {
        return BasedSequence.of(charSequence, 0, charSequence.length());
    }

    /**
     * @deprecated  use {@link IRichSequence#of} instead
     */
    @Deprecated
    public static BasedSequence of(CharSequence charSequence, int startIndex) {
        return BasedSequence.of(charSequence, startIndex, charSequence.length());
    }

    /**
     * @deprecated  use {@link IRichSequence#of} instead
     */
    @Deprecated
    public static BasedSequence of(CharSequence charSequence, int startIndex, int endIndex) {
        return BasedSequence.of(charSequence, startIndex, endIndex);
    }
}
