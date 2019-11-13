package com.vladsch.flexmark.util.sequence;

import org.jetbrains.annotations.NotNull;

/**
 * A BasedSequence with an out of scope of original char sequence prefix
 * <p>
 * a subSequence() returns a sub-sequence from the original base sequence, possibly with a prefix if it falls in range
 */
public final class PrefixedSubSequence extends BasedSequenceImpl implements ReplacedBasedSequence {
    private final CharSequence prefix;
    private final BasedSequence base;

    @NotNull
    @Override
    public Object getBase() {
        return base.getBase();
    }

    @NotNull
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

    @NotNull
    @Override
    public Range getSourceRange() {
        return base.getSourceRange();
    }

    @NotNull
    @Override
    public BasedSequence baseSubSequence(int startIndex, int endIndex) {
        return base.baseSubSequence(startIndex, endIndex);
    }

    private PrefixedSubSequence(CharSequence prefix, BasedSequence baseSeq, int startIndex, int endIndex) {
        super(0);

        this.prefix = prefix;
        this.base = baseSeq.subSequence(startIndex, endIndex);
    }

    @Override
    public int length() {
        return prefix.length() + base.length();
    }

    @Override
    public int getIndexOffset(int index) {
        if (index < prefix.length()) {
            // NOTE: to allow creation of segmented sequences from modified original base return -1 for all such modified content positions
            return -1;
        }
        return base.getIndexOffset(index - prefix.length());
    }

    @Override
    public char charAt(int index) {
        int prefixLength = prefix.length();
        if (index >= 0 && index < base.length() + prefixLength) {
            if (index < prefixLength) {
                return prefix.charAt(index);
            } else {
                return base.charAt(index - prefixLength);
            }
        }
        throw new StringIndexOutOfBoundsException("String index out of range: " + index);
    }

    @NotNull
    @Override
    public BasedSequence subSequence(int startIndex, int endIndex) {
        int prefixLength = prefix.length();
        if (startIndex >= 0 && endIndex <= base.length() + prefixLength) {
            if (startIndex < prefixLength) {
                if (endIndex <= prefixLength) {
                    // all from prefix
                    return new PrefixedSubSequence(prefix.subSequence(startIndex, endIndex), base.subSequence(0, 0), 0, 0);
                } else {
                    // some from prefix some from base
                    return new PrefixedSubSequence(prefix.subSequence(startIndex, prefixLength), base, 0, endIndex - prefixLength);
                }
            } else {
                // all from base
                return base.subSequence(startIndex - prefixLength, endIndex - prefixLength);
            }
        }
        if (startIndex < 0 || startIndex > base.length() + prefixLength) {
            throw new StringIndexOutOfBoundsException("String index out of range: " + startIndex);
        }
        throw new StringIndexOutOfBoundsException("String index out of range: " + endIndex);
    }

    @NotNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(prefix);
        base.appendTo(sb);
        return sb.toString();
    }

    public static PrefixedSubSequence repeatOf(CharSequence prefix, int count, BasedSequence baseSeq) {
        return prefixOf(RepeatedSequence.repeatOf(prefix, count).toString(), baseSeq, 0, baseSeq.length());
    }

    public static PrefixedSubSequence repeatOf(char prefix, int count, BasedSequence baseSeq) {
        return prefixOf(RepeatedSequence.repeatOf(prefix, count), baseSeq, 0, baseSeq.length());
    }

    public static PrefixedSubSequence prefixOf(CharSequence prefix, BasedSequence baseSeq) {
        return prefixOf(prefix, baseSeq, 0, baseSeq.length());
    }

    public static PrefixedSubSequence prefixOf(CharSequence prefix, BasedSequence baseSeq, int startIndex) {
        return prefixOf(prefix, baseSeq, startIndex, baseSeq.length());
    }

    public static PrefixedSubSequence prefixOf(CharSequence prefix, BasedSequence baseSeq, int startIndex, int endIndex) {
        return new PrefixedSubSequence(prefix, baseSeq, startIndex, endIndex);
    }

    @Deprecated
    public static PrefixedSubSequence of(CharSequence prefix, BasedSequence baseSeq) {
        return prefixOf(prefix, baseSeq);
    }

    @Deprecated
    public static PrefixedSubSequence of(CharSequence prefix, BasedSequence baseSeq, int startIndex) {
        return prefixOf(prefix, baseSeq, startIndex);
    }

    @Deprecated
    public static PrefixedSubSequence of(CharSequence prefix, BasedSequence baseSeq, int startIndex, int endIndex) {
        return prefixOf(prefix, baseSeq, startIndex, endIndex);
    }
}
