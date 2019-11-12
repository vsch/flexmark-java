package com.vladsch.flexmark.util.sequence;

import com.vladsch.flexmark.util.Pair;
import com.vladsch.flexmark.util.Utils;
import com.vladsch.flexmark.util.html.Escaping;
import com.vladsch.flexmark.util.mappers.CharMapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Implementation of BaseSequence
 */
public abstract class BasedSequenceImpl extends IRichSequenceBase<BasedSequence> implements BasedSequence {
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
        return BasedSequence.of(charSequence, startIndex, endIndex);
    }

    @Override
    public <B extends SequenceBuilder<B, BasedSequence>> @NotNull B getBuilder() {
        //noinspection unchecked
        return (B) new BasedSequenceBuilder(getBaseSequence());
    }

    @NotNull
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
    public BasedSequence toMapped(CharMapper mapper) {
        return MappedBasedSequence.mappedOf(this, mapper);
    }

    @NotNull
    @Override
    final public BasedSequence baseSubSequence(int startIndex) {
        return baseSubSequence(startIndex, getBaseSequence().getEndOffset());
    }

    @Override
    public @NotNull BasedSequence baseSubSequence(int startIndex, int endIndex) {
        return getBaseSequence().subSequence(startIndex, endIndex);
    }

    @Override
    public char safeCharAt(int index) {
        return index < 0 || index >= length() ? NUL : charAt(index);
    }

    @Override
    public char safeBaseCharAt(int index) {
        if (index >= 0 && index < length()) return charAt(index);
        else {
            int baseIndex = getStartOffset() + index;
            return getBaseSequence().safeCharAt(baseIndex);
        }
    }

    @NotNull
    @Override
    public BasedSequence getEmptyPrefix() {
        return subSequence(0, 0);
    }

    @NotNull
    @Override
    public BasedSequence getEmptySuffix() {
        return subSequence(length());
    }

    @NotNull
    @Override
    public String unescape() {
        return Escaping.unescapeString(this);
    }

    @NotNull
    @Override
    public String unescapeNoEntities() {
        return Escaping.unescapeString(this, false);
    }

    @NotNull
    @Override
    public BasedSequence unescape(@NotNull ReplacedTextMapper textMapper) {
        return Escaping.unescape(this, textMapper);
    }

    @NotNull
    @Override
    public BasedSequence normalizeEOL(@NotNull ReplacedTextMapper textMapper) {
        return Escaping.normalizeEOL(this, textMapper);
    }

    @NotNull
    @Override
    public BasedSequence normalizeEndWithEOL(@NotNull ReplacedTextMapper textMapper) {
        return Escaping.normalizeEndWithEOL(this, textMapper);
    }

    @Override
    public boolean isContinuedBy(@NotNull BasedSequence other) {
        return other.length() > 0 && length() > 0 && other.getBase() == getBase() && other.getStartOffset() == getEndOffset();
    }

    @Override
    public boolean isContinuationOf(@NotNull BasedSequence other) {
        return other.length() > 0 && length() > 0 && other.getBase() == getBase() && other.getEndOffset() == getStartOffset();
    }

    @NotNull
    @Override
    public BasedSequence spliceAtEnd(@NotNull BasedSequence other) {
        if (other.isEmpty()) {
            return this;
        } else if (isEmpty()) {
            return other;
        }
        assert isContinuedBy(other) : "sequence[" + getStartOffset() + ", " + getEndOffset() + "] is not continued by other[" + other.getStartOffset() + ", " + other.getEndOffset() + "]";
        return baseSubSequence(getStartOffset(), other.getEndOffset());
    }

    @Override
    public boolean containsAllOf(@NotNull BasedSequence other) {
        return getBase() == other.getBase() && other.getStartOffset() >= getStartOffset() && other.getEndOffset() <= getEndOffset();
    }

    @Override
    public boolean containsSomeOf(@NotNull BasedSequence other) {
        return getBase() == other.getBase() && !(getStartOffset() >= other.getEndOffset() || getEndOffset() <= other.getStartOffset());
    }

    @NotNull
    @Override
    public BasedSequence intersect(@NotNull BasedSequence other) {
        if (getBase() != other.getBase()) return BasedSequence.NULL;
        else if (other.getEndOffset() <= getStartOffset()) return subSequence(0, 0);
        else if (other.getStartOffset() >= getEndOffset()) return subSequence(length(), length());
        else return this.baseSubSequence(Utils.max(getStartOffset(), other.getStartOffset()), Utils.min(getEndOffset(), other.getEndOffset()));
    }

    @NotNull
    @Override
    public BasedSequence extendByAny(@NotNull CharSequence charSet, int maxCount) {
        int count = getBaseSequence().countLeading(charSet, getEndOffset(), getEndOffset() + maxCount);
        return count == 0 ? this : baseSubSequence(getStartOffset(), getEndOffset() + count);
    }

    @NotNull
    @Override
    public BasedSequence extendToAny(@NotNull CharSequence charSet) {
        return extendToAny(charSet, Integer.MAX_VALUE - getEndOffset());
    }

    @NotNull
    @Override
    public BasedSequence extendByAny(@NotNull CharSequence charSet) {
        return extendByAny(charSet, Integer.MAX_VALUE - getEndOffset());
    }

    @NotNull
    @Override
    public BasedSequence extendByOneOfAny(@NotNull CharSequence charSet) {
        return extendByAny(charSet, 1);
    }

    @NotNull
    @Override
    public BasedSequence extendToAny(@NotNull CharSequence charSet, int maxCount) {
        if (charSet.length() == 0) return this;
        int count = getBaseSequence().countLeadingNot(charSet, getEndOffset(), getEndOffset() + maxCount);
        return count == getBaseSequence().length() - getEndOffset() ? this : baseSubSequence(getStartOffset(), getEndOffset() + count + 1);
    }

    // @formatter:off
    @NotNull@Override final public BasedSequence extendToEndOfLine(@NotNull CharSequence eolChars) { return extendToEndOfLine(eolChars, false);}
    @NotNull@Override final public BasedSequence extendToEndOfLine(boolean includeEol) { return extendToEndOfLine(IRichSequence.EOL, includeEol);}
    @NotNull@Override final public BasedSequence extendToEndOfLine() { return extendToEndOfLine(IRichSequence.EOL, false);}
    @NotNull@Override final public BasedSequence extendToStartOfLine(@NotNull CharSequence eolChars) { return extendToStartOfLine(eolChars, false);}
    @NotNull@Override final public BasedSequence extendToStartOfLine(boolean includeEol) { return extendToStartOfLine(IRichSequence.EOL, includeEol);}
    @NotNull@Override final public BasedSequence extendToStartOfLine() { return extendToStartOfLine(IRichSequence.EOL, false);}
    // @formatter:on

    @NotNull
    @Override
    final public BasedSequence extendToEndOfLine(@NotNull CharSequence eolChars, boolean includeEol) {
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

    @NotNull
    @Override
    public BasedSequence extendToStartOfLine(@NotNull CharSequence eolChars, boolean includeEol) {
        int startOffset = getStartOffset();

        // if already have eol then no need to check
        if (indexOf(eolChars, firstChar()) != -1) return this;

        BasedSequence baseSequence = getBaseSequence();
        int startOfLine = baseSequence.startOfLine(startOffset);

        if (includeEol) {
            startOfLine = Math.max(0, startOfLine - Math.min(baseSequence.eolEndLength(startOfLine), 1));
        }

        if (startOfLine != startOffset) {
            return baseSequence.subSequence(startOfLine, getEndOffset());
        }
        return this;
    }

    @NotNull
    @Override
    public BasedSequence prefixWith(@Nullable CharSequence prefix) {
        return prefix == null || prefix.length() == 0 ? this : PrefixedSubSequence.prefixOf(prefix.toString(), this);
    }

    @NotNull
    final public BasedSequence prefixWithIndent() {
        return prefixWithIndent(Integer.MAX_VALUE);
    }

    @NotNull
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

    @NotNull
    @Override
    public BasedSequence prefixOf(@NotNull BasedSequence other) {
        if (getBase() != other.getBase()) return BasedSequence.NULL;
        else if (other.getStartOffset() <= getStartOffset()) return subSequence(0, 0);
        else if (other.getStartOffset() >= getEndOffset()) return this;
        else return this.baseSubSequence(getStartOffset(), other.getStartOffset());
    }

    @NotNull
    @Override
    public BasedSequence suffixOf(@NotNull BasedSequence other) {
        if (getBase() != other.getBase()) return BasedSequence.NULL;
        else if (other.getEndOffset() >= getEndOffset()) return subSequence(length(), length());
        else if (other.getEndOffset() <= getStartOffset()) return this;
        else return this.baseSubSequence(other.getEndOffset(), getEndOffset());
    }

    @Override
    public @NotNull Range lineRangeAt(int index) {
        return super.lineRangeAt(index);
    }

    @Override
    public @NotNull Range lineRangeAtAnyEOL(int index) {
        return super.lineRangeAtAnyEOL(index);
    }

    // @formatter:off
    // TEST: all these need tests
    @Override public @NotNull Range baseLineRangeAtIndex(int index) { return getBaseSequence().lineRangeAt(index); }
    @Override public @NotNull Pair<Integer, Integer> baseLineColumnAtIndex(int index) { return getBaseSequence().lineColumnAtIndex(index);}
    @Override public int baseEndOfLine(int index) { return getBaseSequence().endOfLine(index); }
    @Override public int baseEndOfLineAnyEOL(int index) { return getBaseSequence().endOfLineAnyEOL(index); }
    @Override public int baseStartOfLine(int index) { return getBaseSequence().startOfLine(index); }
    @Override public int baseStartOfLineAnyEOL(int index) { return getBaseSequence().startOfLineAnyEOL(index); }
    @Override public int baseColumnAtIndex(int index) { return getBaseSequence().columnAtIndex(index); }

    @Override public int baseEndOfLine() { return baseEndOfLine(getEndOffset()); }
    @Override public int baseEndOfLineAnyEOL() { return baseEndOfLineAnyEOL(getEndOffset()); }
    @Override public int baseColumnAtEnd() { return baseColumnAtIndex(getEndOffset()); }
    @Override public @NotNull Range baseLineRangeAtEnd() { return baseLineRangeAtIndex(getEndOffset()); }
    @Override public @NotNull Pair<Integer, Integer> baseLineColumnAtEnd() { return baseLineColumnAtIndex(getEndOffset());}

    @Override public int baseStartOfLine() { return baseStartOfLine(getStartOffset()); }
    @Override public int baseStartOfLineAnyEOL() { return baseStartOfLineAnyEOL(getStartOffset()); }
    @Override public int baseColumnAtStart() { return baseColumnAtIndex(getStartOffset()); }
    @Override public @NotNull Range baseLineRangeAtStart() { return baseLineRangeAtIndex(getStartOffset()); }
    @Override public @NotNull Pair<Integer, Integer> baseLineColumnAtStart() {return baseLineColumnAtIndex(getStartOffset());}
    // @formatter:on

    @Override
    public @NotNull BasedSequence trackIndex(int index, @NotNull TrackerDirection trackerDirection) {
        return BasedTrackedSequence.trackOffset(this, index, trackerDirection);
    }

    @Override
    public int getTrackedIndex() {
        // if we are here then there is no tracking information
        return -1;
    }

    @Override
    public int getTrackedOffset(int startOffset, int maxOffset) {
        // if we are here then there is no tracking information
        return -1;
    }

    static BasedSequence create(@Nullable CharSequence charSequence, int startIndex, int endIndex) {
        if (charSequence instanceof BasedSequence) return ((BasedSequence) charSequence).subSequence(startIndex, endIndex);
        else if (charSequence instanceof String) return CharSubSequence.of(charSequence).subSequence(startIndex, endIndex);
        else return SubSequence.create(charSequence, startIndex, endIndex);
    }

    /**
     * @deprecated use {@link BasedSequence#of} instead
     */
    @Deprecated
    public static BasedSequence of(@Nullable CharSequence charSequence) {
        return BasedSequence.of(charSequence);
    }

    /**
     * @deprecated use {@link BasedSequence#of} instead
     */
    @Deprecated
    public static BasedSequence of(@Nullable CharSequence charSequence, int startIndex) {
        return BasedSequence.of(charSequence, startIndex);
    }

    /**
     * @deprecated use {@link BasedSequence#of} instead
     */
    @Deprecated
    public static BasedSequence of(@Nullable CharSequence charSequence, int startIndex, int endIndex) {
        return BasedSequence.of(charSequence, startIndex, endIndex);
    }
}
