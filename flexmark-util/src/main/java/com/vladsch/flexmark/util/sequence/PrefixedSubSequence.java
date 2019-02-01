package com.vladsch.flexmark.util.sequence;

/**
 * A CharSequence that references original char sequence, maps '\0' to '\uFFFD' and is prefixed with a fixed string
 * a subSequence() returns a sub-sequence from the original base sequence, possibly with a prefix if it falls in range
 */
public final class PrefixedSubSequence extends BasedSequenceImpl {
    private final String prefix;
    private final int prefixLength;
    private final BasedSequence base;

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
    public Range getSourceRange() {
        return base.getSourceRange();
    }

    @Override
    public BasedSequence baseSubSequence(int start, int end) {
        return base.baseSubSequence(start, end);
    }

    private PrefixedSubSequence(String prefix, BasedSequence baseSeq, int start, int end, boolean replaceChar) {
        this.prefix = replaceChar ? prefix.replace('\0', '\uFFFD') : prefix;
        this.prefixLength = prefix.length();
        this.base = of(baseSeq, start, end);
    }

    @Override
    public int length() {
        return prefixLength + base.length();
    }

    @Override
    public int getIndexOffset(int index) {
        if (index < prefixLength) {
            // KLUDGE: to allow creation of segmented sequences that have prefixed characters not from original base
            return -1;
        }
        return base.getIndexOffset(index - prefixLength);
    }

    @Override
    public char charAt(int index) {
        if (index >= 0 && index < base.length() + prefixLength) {
            if (index < prefixLength) {
                return prefix.charAt(index);
            } else {
                return base.charAt(index - prefixLength);
            }
        }
        throw new StringIndexOutOfBoundsException("String index out of range: " + index);
    }

    @Override
    public BasedSequence subSequence(int start, int end) {
        if (start >= 0 && end <= base.length() + prefixLength) {
            if (start < prefixLength) {
                if (end <= prefixLength) {
                    // all from prefix
                    return new PrefixedSubSequence(prefix.substring(start, end), base.subSequence(0, 0), 0, 0, false);
                } else {
                    // some from prefix some from base
                    return new PrefixedSubSequence(prefix.substring(start), base, 0, end - prefixLength, false);
                }
            } else {
                // all from base
                return base.subSequence(start - prefixLength, end - prefixLength);
            }
        }
        if (start < 0 || start > base.length() + prefixLength) {
            throw new StringIndexOutOfBoundsException("String index out of range: " + start);
        }
        throw new StringIndexOutOfBoundsException("String index out of range: " + end);
    }

    @Override
    public String toString() {
        return prefix + String.valueOf(base);
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || (obj instanceof CharSequence && toString().equals(obj.toString()));
    }

    public static PrefixedSubSequence repeatOf(CharSequence prefix, int count, BasedSequence baseSeq) {
        return of(RepeatedCharSequence.of(prefix, count).toString(), baseSeq, 0, baseSeq.length());
    }

    public static PrefixedSubSequence repeatOf(char prefix, int count, BasedSequence baseSeq) {
        return of(RepeatedCharSequence.of(prefix, count).toString(), baseSeq, 0, baseSeq.length());
    }

    public static PrefixedSubSequence of(String prefix, BasedSequence baseSeq) {
        return of(prefix, baseSeq, 0, baseSeq.length());
    }

    public static PrefixedSubSequence of(String prefix, BasedSequence baseSeq, int start) {
        return of(prefix, baseSeq, start, baseSeq.length());
    }

    public static PrefixedSubSequence of(String prefix, BasedSequence baseSeq, int start, int end) {
        return new PrefixedSubSequence(prefix, baseSeq, start, end, true);
    }
}
