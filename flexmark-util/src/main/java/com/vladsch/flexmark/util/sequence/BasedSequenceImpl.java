package com.vladsch.flexmark.util.sequence;

import com.vladsch.flexmark.util.Utils;
import com.vladsch.flexmark.util.html.Escaping;
import com.vladsch.flexmark.util.mappers.CharMapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A CharSequence that references original char sequence and maps '\0' to '\uFFFD'
 * a subSequence() returns a sub-sequence from the original base sequence
 */
public abstract class BasedSequenceImpl extends RichSequenceBase<BasedSequence> implements BasedSequence {
    public static BasedSequence firstNonNull(BasedSequence... sequences) {
        for (BasedSequence sequence : sequences) {
            if (sequence != null && sequence != NULL) return sequence;
        }

        return NULL;
    }

    @NotNull
    @Override
    public BasedSequence[] emptyArray() {
        return EMPTY_ARRAY;
    }

    @NotNull
    @Override
    public BasedSequence nullSequence() {
        return NULL;
    }

    @NotNull
    @Override
    public BasedSequence sequenceOf(@Nullable CharSequence charSequence, int startIndex, int endIndex) {
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

    @NotNull
    @Override
    public final BasedSequence toMapped(CharMapper mapper) {
        return MappedSequence.of(mapper, this);
    }

    @Override
    public BasedSequence baseSubSequence(int start) {
        return baseSubSequence(start, getBaseSequence().getEndOffset());
    }

    @Override
    public char safeCharAt(int index) {
        return index < 0 || index >= length() ? '\0' : charAt(index);
    }

    @Override
    public char safeBaseCharAt(int index) {
        if (index >= 0 && index < length()) return charAt(index);
        else {
            int baseIndex = getStartOffset() + index;
            return getBaseSequence().safeCharAt(baseIndex);
        }
    }

    @Override
    public BasedSequence getEmptyPrefix() {
        return subSequence(0, 0);
    }

    @Override
    public BasedSequence getEmptySuffix() {
        return subSequence(length());
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
    public BasedSequence extendByAny(CharSequence charSet, int maxCount) {
        int count = getBaseSequence().countLeading(charSet, getEndOffset(), getEndOffset() + maxCount);
        return count == 0 ? this : baseSubSequence(getStartOffset(), getEndOffset() + count);
    }

    @Override
    public BasedSequence extendToAny(CharSequence charSet) {
        return extendToAny(charSet, Integer.MAX_VALUE - getEndOffset());
    }

    @Override
    public BasedSequence extendByAny(CharSequence charSet) {
        return extendByAny(charSet, Integer.MAX_VALUE - getEndOffset());
    }

    @Override
    public BasedSequence extendByOneOfAny(CharSequence charSet) {
        return extendByAny(charSet, 1);
    }

    @Override
    public BasedSequence extendToAny(CharSequence charSet, int maxCount) {
        if (charSet.length() == 0) return this;
        int count = getBaseSequence().countLeadingNot(charSet, getEndOffset(), getEndOffset() + maxCount);
        return count == getBaseSequence().length() - getEndOffset() ? this : baseSubSequence(getStartOffset(), getEndOffset() + count + 1);
    }

    // @formatter:off
    @Override final public BasedSequence extendToEndOfLine(CharSequence eolChars) { return extendToEndOfLine(eolChars, false);}
    @Override final public BasedSequence extendToEndOfLine(boolean includeEol) { return extendToEndOfLine(RichSequence.EOL, includeEol);}
    @Override final public BasedSequence extendToEndOfLine() { return extendToEndOfLine(RichSequence.EOL, false);}
    @Override final public BasedSequence extendToStartOfLine(CharSequence eolChars) { return extendToStartOfLine(eolChars, false);}
    @Override final public BasedSequence extendToStartOfLine(boolean includeEol) { return extendToStartOfLine(RichSequence.EOL, includeEol);}
    @Override final public BasedSequence extendToStartOfLine() { return extendToStartOfLine(RichSequence.EOL, false);}
    // @formatter:on

    @Override
    final public BasedSequence extendToEndOfLine(CharSequence eolChars, boolean includeEol) {
        int endOffset = getEndOffset();

        // if already have eol then no need to check
        if (indexOf(eolChars, lastChar()) != -1) return this;

        BasedSequence baseSequence = getBaseSequence();
        int endOfLine = baseSequence.endOfLine(endOffset);

        if (includeEol) {
            endOfLine = Math.min(baseSequence.length(), endOfLine + Math.min(baseSequence.eolStartLength(endOfLine), 1));
        }

        if (endOfLine != endOffset) {
            return baseSequence.subSequence(getStartOffset(), endOfLine);
        }
        return this;
    }

    @Override
    public BasedSequence extendToStartOfLine(CharSequence eolChars, boolean includeEol) {
        int startOffset = getStartOffset();

        // if already have eol then no need to check
        if (indexOf(eolChars, firstChar()) != -1) return this;

        BasedSequence baseSequence = getBaseSequence();
        int startOfLine = baseSequence.startOfLine(startOffset);

        if (includeEol) {
            startOfLine = Math.max(0, startOfLine - Math.min(baseSequence.eolEndLength(startOfLine - 1), 1));
        }

        if (startOfLine != startOffset) {
            return baseSequence.subSequence(startOfLine, getEndOffset());
        }
        return this;
    }

    @NotNull
    @Override
    public BasedSequence sequenceOf(@NotNull Iterable<BasedSequence> sequences) {
        return SegmentedSequence.of(sequences);
    }

    @NotNull
    @Override
    public BasedSequence prefixWith(@Nullable CharSequence prefix) {
        return prefix == null || prefix.length() == 0 ? this : PrefixedSubSequence.of(prefix.toString(), this);
    }

    @Override
    public BasedSequence prefixWithIndent(int maxColumns) {
        int offset = getStartOffset();
        int startOffset = getStartOffset();
        int columns = 0;
        int columnOffset = 0;
        boolean hadTab = false;

        // find '\n'
        while (startOffset >= 0) {
            char c = getBaseSequence().charAt(startOffset);
            if (c == '\t') hadTab = true;
            else if (c == '\n') {
                startOffset++;
                break;
            }
            startOffset--;
        }

        if (startOffset < 0) startOffset = 0;

        if (startOffset < offset) {
            if (hadTab) {
                // see what is the column at offset
                int[] offsetColumns = new int[offset - startOffset];
                int currOffset = startOffset;
                while (currOffset < offset) {
                    if (getBaseSequence().charAt(currOffset) == '\t') {
                        columnOffset += offsetColumns[currOffset - startOffset] = 4 - (columnOffset % 4);
                    } else {
                        columnOffset += offsetColumns[currOffset - startOffset] = 1;
                    }
                    currOffset++;
                }

                while (columns < maxColumns && offset > 0 && (getBaseSequence().charAt(offset - 1) == ' ' || getBaseSequence().charAt(offset - 1) == '\t')) {
                    columns += offsetColumns[offset - 1 - startOffset];
                    if (columns > maxColumns) break;
                    offset--;
                }
            } else {
                while (columns < maxColumns && offset > 0 && (getBaseSequence().charAt(offset - 1) == ' ' || getBaseSequence().charAt(offset - 1) == '\t')) {
                    columns++;
                    offset--;
                }
            }
        }

        return offset == getStartOffset() ? this : baseSubSequence(offset, getEndOffset());
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

    public static BasedSequence of(CharSequence charSequence) {
        return of(charSequence, 0, charSequence.length());
    }

    public static BasedSequence of(CharSequence charSequence, int start) {
        return of(charSequence, start, charSequence.length());
    }

    public static BasedSequence of(CharSequence charSequence, int start, int end) {
        if (charSequence instanceof BasedSequence) return ((BasedSequence) charSequence).subSequence(start, end);
        else if (charSequence instanceof String) return CharSubSequence.of(charSequence).subSequence(start, end);
        else return SubSequence.of(charSequence).subSequence(start, end);
    }
}
