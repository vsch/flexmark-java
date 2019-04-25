package com.vladsch.flexmark.util.sequence;

import com.vladsch.flexmark.util.Utils;
import com.vladsch.flexmark.util.html.Escaping;
import com.vladsch.flexmark.util.mappers.CharMapper;

/**
 * A CharSequence that references original char sequence and maps '\0' to '\uFFFD'
 * a subSequence() returns a sub-sequence from the original base sequence
 */
public abstract class BasedSequenceImpl extends RichCharSequenceBase<BasedSequence> implements BasedSequence {
    public static BasedSequence firstNonNull(BasedSequence... sequences) {
        for (BasedSequence sequence : sequences) {
            if (sequence != null && sequence != NULL) return sequence;
        }

        return NULL;
    }

    @Override
    public BasedSequence[] emptyArray() {
        return EMPTY_ARRAY;
    }

    @Override
    public BasedSequence nullSequence() {
        return NULL;
    }

    @Override
    public BasedSequence sequenceOf(CharSequence charSequence, int startIndex, int endIndex) {
        return of(charSequence, startIndex, endIndex);
    }

    @Override
    public Range getIndexRange(int startOffset, int endOffset) {
        // we assume that start/end is within our range
        int baseOffset = getStartOffset();
        if (startOffset > getEndOffset() || endOffset < baseOffset) {
            throw new IllegalArgumentException("getIndexRange(" + startOffset + ", " + endOffset + ") not in range [" + baseOffset + ", " + getEndOffset() + "]");
        }
        return Range.of(startOffset - baseOffset, endOffset - baseOffset);
    }

    @Override
    public final BasedSequence toMapped(CharMapper mapper) {
        return MappedSequence.of(mapper, this);
    }

    @Override
    public BasedSequence baseSubSequence(int start) {
        return baseSubSequence(start, getBaseSequence().getEndOffset());
    }

    @Override
    public String unescape() {
        return Escaping.unescapeString(this);
    }

    @Override
    public String unescapeNoEntities() {
        return Escaping.unescapeString(this, false);
    }

    @Override
    public BasedSequence unescape(ReplacedTextMapper textMapper) {
        return Escaping.unescape(this, textMapper);
    }

    @Override
    public BasedSequence normalizeEOL(ReplacedTextMapper textMapper) {
        return Escaping.normalizeEOL(this, textMapper);
    }

    @Override
    public BasedSequence normalizeEndWithEOL(ReplacedTextMapper textMapper) {
        return Escaping.normalizeEndWithEOL(this, textMapper);
    }

    @Override
    public boolean isContinuedBy(BasedSequence other) {
        return other.length() > 0 && length() > 0 && other.getBase() == getBase() && other.getStartOffset() == getEndOffset();
    }

    @Override
    public boolean isContinuationOf(BasedSequence other) {
        return other.length() > 0 && length() > 0 && other.getBase() == getBase() && other.getEndOffset() == getStartOffset();
    }

    @Override
    public BasedSequence spliceAtEnd(BasedSequence other) {
        if (other.isEmpty()) {
            return this;
        } else if (isEmpty()) {
            return other;
        }
        assert isContinuedBy(other) : "sequence[" + getStartOffset() + ", " + getEndOffset() + "] is not continued by other[" + other.getStartOffset() + ", " + other.getEndOffset() + "]";
        return baseSubSequence(getStartOffset(), other.getEndOffset());
    }

    @Override
    public boolean containsAllOf(BasedSequence other) {
        return getBase() == other.getBase() && other.getStartOffset() >= getStartOffset() && other.getEndOffset() <= getEndOffset();
    }

    @Override
    public boolean containsSomeOf(BasedSequence other) {
        return getBase() == other.getBase() && !(getStartOffset() >= other.getEndOffset() || getEndOffset() <= other.getStartOffset());
    }

    @Override
    public BasedSequence intersect(BasedSequence other) {
        if (getBase() != other.getBase()) return SubSequence.NULL;
        else if (other.getEndOffset() <= getStartOffset()) return subSequence(0, 0);
        else if (other.getStartOffset() >= getEndOffset()) return subSequence(length(), length());
        else return this.baseSubSequence(Utils.max(getStartOffset(), other.getStartOffset()), Utils.min(getEndOffset(), other.getEndOffset()));
    }

    @Override
    public BasedSequence prefixOf(BasedSequence other) {
        if (getBase() != other.getBase()) return SubSequence.NULL;
        else if (other.getStartOffset() <= getStartOffset()) return subSequence(0, 0);
        else if (other.getStartOffset() >= getEndOffset()) return this;
        else return this.baseSubSequence(getStartOffset(), other.getStartOffset());
    }

    @Override
    public BasedSequence suffixOf(BasedSequence other) {
        if (getBase() != other.getBase()) return SubSequence.NULL;
        else if (other.getEndOffset() >= getEndOffset()) return subSequence(length(), length());
        else if (other.getEndOffset() <= getStartOffset()) return this;
        else return this.baseSubSequence(other.getEndOffset(), getEndOffset());
    }

    @Override
    public boolean equals(Object other) {
        return (this == other) || other != null && other instanceof CharSequence && ((CharSequence) other).length() == length() && matchChars((CharSequence) other, 0, false);
    }

    @Override
    public boolean equalsIgnoreCase(CharSequence other) {
        return (this == other) || (other != null) && other.length() == length() && matchChars(other, 0, true);
    }

    @Override
    public boolean equals(Object other, boolean ignoreCase) {
        return (this == other) || other != null && other instanceof CharSequence && ((CharSequence) other).length() == length() && matchChars((CharSequence) other, 0, ignoreCase);
    }

    @Override
    public int compareTo(CharSequence other) {
        int len1 = length();
        int len2 = other.length();
        int iMax = len1 <= len2 ? len1 : len2;
        for (int i = 0; i < iMax; i++) {
            char c1 = charAt(i);
            char c2 = other.charAt(i);
            if (c1 != c2) {
                return c1 - c2;
            }
        }
        return len1 - len2;
    }

    public static BasedSequence of(CharSequence charSequence) {
        if (charSequence instanceof BasedSequence) return ((BasedSequence) charSequence);
        else if (charSequence instanceof String) return CharSubSequence.of(charSequence);
        else if (charSequence != null) return SubSequence.of(charSequence);
        return BasedSequence.NULL;
    }

    public static BasedSequence of(CharSequence charSequence, int start) {
        if (charSequence instanceof BasedSequence) return ((BasedSequence) charSequence).subSequence(start);
        else if (charSequence instanceof String) return CharSubSequence.of(charSequence, start);
        else return SubSequence.of(charSequence, start);
    }

    public static BasedSequence of(CharSequence charSequence, int start, int end) {
        if (charSequence instanceof BasedSequence) return ((BasedSequence) charSequence).subSequence(start, end);
        else if (charSequence instanceof String) return CharSubSequence.of(charSequence, start, end);
        else return SubSequence.of(charSequence, start, end);
    }
}
