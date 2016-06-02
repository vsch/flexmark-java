package org.commonmark.internal.util;

/**
 * A CharSequence that references original char sequence, maps '\0' to '\uFFFD' and is prefixed with a fixed string
 * a subSequence() returns a sub-sequence from the original base sequence, possibly with a prefix if it falls in range
 */
public class PrefixedSubSequence extends SubSequence {
    private final String prefix;
    private final int prefixLength;

    public PrefixedSubSequence(String prefix) {
        this(prefix, SubSequence.EMPTY, 0, 0, NullCharacterMapper.INSTANCE);
    }

    public PrefixedSubSequence(String prefix, CharSequence base) {
        this(prefix, base, 0, base.length(), NullCharacterMapper.INSTANCE);
    }

    public PrefixedSubSequence(String prefix, CharSequence base, CharMapper mapper) {
        this(prefix, base, 0, base.length(), mapper);
    }

    public PrefixedSubSequence(String prefix, CharSequence base, int startOffset, int endOffset) {
        this(prefix, base, startOffset, endOffset, NullCharacterMapper.INSTANCE);
    }

    public PrefixedSubSequence(String prefix, CharSequence base, int startOffset, int endOffset, CharMapper mapper) {
        super(base, startOffset, endOffset, mapper);

        this.prefix = prefix;
        this.prefixLength = prefix.length();
    }

    public PrefixedSubSequence(String prefix, SubSequence subSequence) {
        this(prefix, subSequence.getBase(), subSequence.getStartOffset(), subSequence.getEndOffset());
    }

    public PrefixedSubSequence(String prefix, PrefixedSubSequence subSequence) {
        this(prefix + (subSequence.prefix), subSequence.base, subSequence.startOffset, subSequence.endOffset);
    }

    @Override
    public int length() {
        return prefixLength + super.length();
    }

    @Override
    public char charAt(int index) {
        if (index < 0 || startOffset + index >= endOffset + prefixLength) {
            throw new StringIndexOutOfBoundsException("String index out of range: " + index);
        }

        if (index < prefixLength) {
            char c = prefix.charAt(index);
            return mapper.map(c, this, index);
        } else {
            return super.charAt(index - prefixLength);
        }
    }

    @Override
    public BasedSequence subSequence(int start, int end) {
        if (start < 0 || startOffset + start > endOffset + prefixLength) {
            throw new StringIndexOutOfBoundsException("String index out of range: " + start);
        }

        if (end < 0 || startOffset + end > endOffset + prefixLength) {
            throw new StringIndexOutOfBoundsException("String index out of range: " + end);
        }

        if (start < prefixLength) {
            if (end <= prefixLength) {
                // all from prefix
                return new PrefixedSubSequence(prefix.substring(start, end), base, startOffset, startOffset, mapper);
            } else {
                // some from prefix some from base
                return new PrefixedSubSequence(prefix.substring(start), base, startOffset, startOffset + end - prefixLength, mapper);
            }
        } else {
            // all from base
            return new SubSequence(base, startOffset + start - prefixLength, startOffset + end - prefixLength, mapper);
        }
    }

    @Override
    public String toString() {
        return String.valueOf(base.subSequence(startOffset, endOffset));
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || (obj instanceof CharSequence && toString().equals(obj.toString()));
    }
}
