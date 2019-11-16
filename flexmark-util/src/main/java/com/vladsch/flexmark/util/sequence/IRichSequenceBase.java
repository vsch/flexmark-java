package com.vladsch.flexmark.util.sequence;

import com.vladsch.flexmark.util.Pair;
import com.vladsch.flexmark.util.collection.iteration.ArrayIterable;
import com.vladsch.flexmark.util.html.Escaping;
import com.vladsch.flexmark.util.mappers.ChangeCase;
import com.vladsch.flexmark.util.mappers.CharMapper;
import com.vladsch.flexmark.util.mappers.SpaceMapper;
import com.vladsch.flexmark.util.sequence.edit.SequenceBuilder;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import static com.vladsch.flexmark.util.Utils.rangeLimit;

/**
 * An abstract base for RichSequence which implements most of the methods allowing subclasses to
 * implement RichSequence with minimal support methods
 */
@SuppressWarnings("unchecked")
public abstract class IRichSequenceBase<T extends IRichSequence<T>> implements IRichSequence<T> {

    // cached value
    private int hash;

    /**
     * Constructor with pre-computed hash if available, 0 for lazy computation if length() not 0
     *
     * @param hash hash code for the char sequence
     */
    public IRichSequenceBase(int hash) {
        this.hash = hash;
    }

    // @formatter:on

    /**
     * Equality comparison based on character content of this sequence, with quick fail
     * resorting to content comparison only if length and hashCodes are equal
     *
     * @param o any char sequence
     * @return true if character contents are equal
     */
    @Override
    @Contract(pure = true, value = "null -> false")
    final public boolean equals(Object o) {
        // do quick failure of equality
        if (o == this) return true;
        if (!(o instanceof CharSequence)) return false;

        CharSequence chars = (CharSequence) o;
        if (chars.length() != length()) return false;

        if (o instanceof String) {
            String other = (String) o;
            if (other.hashCode() != hashCode()) return false;

            // fall through to slow content comparison
        } else if (o instanceof IRichSequence) {
            IRichSequence<?> other = (IRichSequence<?>) o;
            if (other.hashCode() != hashCode()) return false;

            // fall through to slow content comparison
        }
        return matchChars(chars, 0, false);
    }

    /**
     * String hash code computation
     *
     * @return hash code of equivalent string
     */
    @Override
    @Contract(pure = true)
    final public int hashCode() {
        int h = hash;
        if (h == 0 && length() > 0) {
            for (int i = 0; i < length(); i++) {
                h = 31 * h + charAt(i);
            }
            hash = h;
        }
        return h;
    }

    @Override
    @Contract(pure = true, value = "null -> false")
    final public boolean equalsIgnoreCase(@Nullable Object other) {
        return (this == other) || (other instanceof CharSequence) && ((CharSequence) other).length() == length() && matchChars((CharSequence) other, 0, true);
    }

    @Override
    @Contract(pure = true, value = "null, _ ->false")
    final public boolean equals(@Nullable Object other, boolean ignoreCase) {
        return (this == other) || other instanceof CharSequence && ((CharSequence) other).length() == length() && matchChars((CharSequence) other, 0, ignoreCase);
    }

    @Override
    public int compareTo(@NotNull CharSequence o) {
        return SequenceUtils.compare(this, o);
    }

    @NotNull
    @Override
    final public T sequenceOf(@Nullable CharSequence charSequence) {
        return charSequence == null ? nullSequence() : sequenceOf(charSequence, 0, charSequence.length());
    }

    @NotNull
    @Override
    final public T sequenceOf(@Nullable CharSequence charSequence, int startIndex) {
        return charSequence == null ? nullSequence() : sequenceOf(charSequence, startIndex, charSequence.length());
    }

    @NotNull
    @Override
    final public T subSequence(int startIndex) {
        return subSequence(startIndex, length());
    }

    /**
     * Get a portion of this sequence selected by range
     *
     * @param range range to get, coordinates offset form start of this sequence
     * @return sequence whose contents reflect the selected portion, if range.isNull() then this is returned
     */
    @NotNull
    final public T subSequence(@NotNull Range range) {
        return range.isNull() ? (T) this : subSequence(range.getStart(), range.getEnd());
    }

    /**
     * Get a portion of this sequence before one selected by range
     *
     * @param range range to get, coordinates offset form start of this sequence
     * @return sequence whose contents come before the selected range, if range.isNull() then {@link #nullSequence()}
     */
    @NotNull
    final public T subSequenceBefore(@NotNull Range range) {
        return range.isNull() ? nullSequence() : subSequence(0, range.getStart());
    }

    /**
     * Get a portion of this sequence after one selected by range
     *
     * @param range range to get, coordinates offset form start of this sequence
     * @return sequence whose contents come after the selected range, if range.isNull() then {@link #nullSequence()}
     */
    @NotNull
    final public T subSequenceAfter(@NotNull Range range) {
        return range.isNull() ? nullSequence() : subSequence(range.getEnd());
    }

    /**
     * Get a portions of this sequence before and after one selected by range
     *
     * @param range range to get, coordinates offset form start of this sequence
     * @return sequence whose contents come before and after the selected range, if range.isNull() then {@link #nullSequence()}
     */
    final public Pair<T, T> subSequenceBeforeAfter(Range range) {
        return Pair.of(subSequenceBefore(range), subSequenceAfter(range));
    }

    @NotNull
    @Override
    final public T endSequence(int startIndex, int endIndex) {
        int length = length();
        int useStart = length - startIndex;
        int useEnd = length - endIndex;

        useEnd = rangeLimit(useEnd, 0, length);
        useStart = rangeLimit(useStart, 0, useEnd);
        return subSequence(useStart, useEnd);
    }

    @NotNull
    @Override
    final public T endSequence(int startIndex) {
        return endSequence(startIndex, 0);
    }

    @Override
    final public char endCharAt(int index) {
        int length = length();
        if (index < 0 || index >= length) return SequenceUtils.NUL;
        return charAt(length - index);
    }

    @NotNull
    @Override
    final public T midSequence(int startIndex, int endIndex) {
        int length = length();
        int useStart = startIndex < 0 ? length + startIndex : startIndex;
        int useEnd = endIndex < 0 ? length + endIndex : endIndex;

        useEnd = rangeLimit(useEnd, 0, length);
        useStart = rangeLimit(useStart, 0, useEnd);
        return subSequence(useStart, useEnd);
    }

    @NotNull
    @Override
    final public T midSequence(int startIndex) {
        return midSequence(startIndex, length());
    }

    @Override
    final public char midCharAt(int index) {
        int length = length();
        if (index < -length || index >= length) return SequenceUtils.NUL;
        return charAt(index < 0 ? length + index : index);
    }

    @Override
    final public char lastChar() {
        return isEmpty() ? SequenceUtils.NUL : charAt(length() - 1);
    }

    @Override
    final public char firstChar() {
        return isEmpty() ? SequenceUtils.NUL : charAt(0);
    }

    @Override
    public char safeCharAt(int index) {
        return index < 0 || index >= length() ? SequenceUtils.NUL : charAt(index);
    }

    // @formatter:off
    @Override final public int indexOf(@NotNull CharSequence s)                                             { return SequenceUtils.indexOf(this, s); }
    @Override final public int indexOf(@NotNull CharSequence s, int fromIndex)                              { return SequenceUtils.indexOf(this, s, fromIndex); }
    @Override final public int indexOf(@NotNull CharSequence s, int fromIndex, int endIndex)                { return SequenceUtils.indexOf(this, s, fromIndex, endIndex);}
    @Override final public int indexOf(char c)                                                              { return SequenceUtils.indexOf(this, c); }
    @Override final public int indexOf(char c, int fromIndex)                                               { return SequenceUtils.indexOf(this, c, fromIndex); }
    @Override final public int indexOfAny(@NotNull CharPredicate s)                                         { return SequenceUtils.indexOfAny(this, s); }
    @Override final public int indexOfAny(@NotNull CharPredicate s, int index)                              { return SequenceUtils.indexOfAny(this, s, index); }
    @Override final public int indexOfAnyNot(@NotNull CharPredicate s)                                      { return SequenceUtils.indexOfAnyNot(this, s); }
    @Override final public int indexOfAnyNot(@NotNull CharPredicate s, int fromIndex)                       { return SequenceUtils.indexOfAnyNot(this, s, fromIndex); }
    @Override final public int indexOfAnyNot(@NotNull CharPredicate s, int fromIndex, int endIndex)         { return SequenceUtils.indexOfAnyNot(this, s,fromIndex, endIndex);}
    @Override final public int indexOfNot(char c)                                                           { return SequenceUtils.indexOfNot(this, c); }
    @Override final public int indexOfNot(char c, int fromIndex)                                            { return SequenceUtils.indexOfNot(this, c, fromIndex); }
    @Override final public int lastIndexOf(char c)                                                          { return SequenceUtils.lastIndexOf(this, c); }
    @Override final public int lastIndexOf(char c, int fromIndex)                                           { return SequenceUtils.lastIndexOf(this, c, fromIndex); }
    @Override final public int lastIndexOfNot(char c)                                                       { return SequenceUtils.lastIndexOfNot(this, c); }
    @Override final public int lastIndexOfNot(char c, int fromIndex)                                        { return SequenceUtils.lastIndexOfNot(this, c, fromIndex); }
    @Override final public int lastIndexOf(@NotNull CharSequence s)                                         { return SequenceUtils.lastIndexOf(this, s); }
    @Override final public int lastIndexOf(@NotNull CharSequence s, int fromIndex)                          { return SequenceUtils.lastIndexOf(this, s, fromIndex); }
    @Override final public int lastIndexOfAny(@NotNull CharPredicate s, int fromIndex)                      { return SequenceUtils.lastIndexOfAny(this, s, fromIndex); }
    @Override final public int lastIndexOfAny(@NotNull CharPredicate s)                                     { return SequenceUtils.lastIndexOfAny(this, s); }
    @Override final public int lastIndexOfAnyNot(@NotNull CharPredicate s)                                  { return SequenceUtils.lastIndexOfAnyNot(this, s); }
    @Override final public int lastIndexOfAnyNot(@NotNull CharPredicate s, int fromIndex)                   { return SequenceUtils.lastIndexOfAnyNot(this, s, fromIndex); }
    @Override final public int lastIndexOfAnyNot(@NotNull CharPredicate s, int startIndex, int fromIndex)   { return SequenceUtils.lastIndexOfAnyNot(this, s, startIndex, fromIndex); }
    @Override final public int indexOf(char c, int fromIndex, int endIndex)                                 { return SequenceUtils.indexOf(this, c, fromIndex, endIndex); }
    @Override final public int indexOfNot(char c, int fromIndex, int endIndex)                              { return SequenceUtils.indexOfNot(this, c, fromIndex, endIndex); }
    @Override final public int indexOfAny(@NotNull CharPredicate s, int fromIndex, int endIndex)            { return SequenceUtils.indexOfAny(this, s, fromIndex, endIndex); }
    @Override final public int lastIndexOf(@NotNull CharSequence s, int startIndex, int fromIndex)          { return SequenceUtils.lastIndexOf(this, s, startIndex, fromIndex); }
    @Override final public int lastIndexOf(char c, int startIndex, int fromIndex)                           { return SequenceUtils.lastIndexOf(this, c, startIndex, fromIndex); }
    @Override final public int lastIndexOfNot(char c, int startIndex, int fromIndex)                        { return SequenceUtils.lastIndexOfNot(this, c, startIndex, fromIndex); }
    @Override final public int lastIndexOfAny(@NotNull CharPredicate s, int startIndex, int fromIndex)      { return SequenceUtils.lastIndexOfAny(this, s, startIndex, fromIndex); }
    // @formatter:on

    // @formatter:off
    @Override final public int countOfSpaceTab()                                                            { return SequenceUtils.countOfSpaceTab(this); }
    @Override final public int countOfNotSpaceTab()                                                         { return SequenceUtils.countOfNotSpaceTab(this); }
    @Override final public int countOfWhitespace()                                                          { return SequenceUtils.countOfWhitespace(this); }
    @Override final public int countOfNotWhitespace()                                                       { return SequenceUtils.countOfNotWhitespace(this); }
    @Override final public int countOfAny(@NotNull CharPredicate chars, int fromIndex)                      { return SequenceUtils.countOfAny(this, chars, fromIndex); }
    @Override final public int countOfAny(@NotNull CharPredicate chars)                                     { return SequenceUtils.countOfAny(this, chars); }
    @Override final public int countOfAnyNot(@NotNull CharPredicate chars, int fromIndex)                   { return SequenceUtils.countOfAnyNot(this, chars, fromIndex); }
    @Override final public int countOfAnyNot(@NotNull CharPredicate chars)                                  { return SequenceUtils.countOfAnyNot(this, chars); }
    @Override final public int countOfAny(@NotNull CharPredicate s, int fromIndex, int endIndex)            { return SequenceUtils.countOfAny(this, s, fromIndex, endIndex);}
    @Override final public int countOfAnyNot(@NotNull CharPredicate chars, int startIndex, int endIndex)    { return SequenceUtils.countOfAnyNot(this, chars, startIndex, endIndex); }
    // @formatter:on

    // @formatter:off
    @Override final public int countLeadingSpaceTab()                                                       { return SequenceUtils.countLeadingSpaceTab(this); }
    @Override final public int countTrailingSpaceTab()                                                      { return SequenceUtils.countTrailingSpaceTab(this); }
    @Override final public int countLeadingNotSpaceTab()                                                    { return SequenceUtils.countLeadingNotSpaceTab(this); }
    @Override final public int countTrailingNotSpaceTab()                                                   { return SequenceUtils.countTrailingNotSpaceTab(this); }

    @Override final public int countLeadingWhitespace()                                                     { return SequenceUtils.countLeadingWhitespace(this); }
    @Override final public int countTrailingWhitespace()                                                    { return SequenceUtils.countTrailingWhitespace(this); }
    @Override final public int countLeadingNotWhitespace()                                                  { return SequenceUtils.countLeadingNotWhitespace(this); }
    @Override final public int countTrailingNotWhitespace()                                                 { return SequenceUtils.countTrailingNotWhitespace(this); }

    @Override final public int countLeading(@NotNull CharPredicate chars)                                   { return SequenceUtils.countLeading(this, chars); }
    @Override final public int countLeading(@NotNull CharPredicate chars, int fromIndex)                    { return SequenceUtils.countLeading(this, chars, fromIndex); }
    @Override final public int countLeadingNot(@NotNull CharPredicate chars)                                { return SequenceUtils.countLeadingNot(this, chars); }
    @Override final public int countLeadingNot(@NotNull CharPredicate chars, int fromIndex)                 { return SequenceUtils.countLeadingNot(this, chars, fromIndex); }

    @Override final public int countTrailing(@NotNull CharPredicate chars)                                  { return SequenceUtils.countTrailing(this, chars); }
    @Override final public int countTrailing(@NotNull CharPredicate chars, int fromIndex)                   { return SequenceUtils.countTrailing(this, chars, fromIndex); }
    @Override final public int countTrailingNot(@NotNull CharPredicate chars)                               { return SequenceUtils.countTrailingNot(this, chars); }
    @Override final public int countTrailingNot(@NotNull CharPredicate chars, int fromIndex)                { return SequenceUtils.countTrailingNot(this, chars, fromIndex); }

    @Override final public int countLeadingNot(@NotNull CharPredicate chars, int startIndex, int endIndex)  { return SequenceUtils.countLeadingNot(this, chars, startIndex, endIndex); }
    @Override final public int countTrailingNot(@NotNull CharPredicate chars, int startIndex, int endIndex) { return SequenceUtils.countTrailingNot(this, chars, startIndex, endIndex); }
    @Override final public int countLeading(@NotNull CharPredicate chars, int fromIndex, int endIndex)      { return SequenceUtils.countLeading(this, chars, fromIndex, endIndex);}

    @Override final public int countLeadingColumns(int startColumn, @NotNull CharPredicate chars)           { return SequenceUtils.countLeadingColumns(this, startColumn, chars); }
    @Override final public int countTrailing(@NotNull CharPredicate chars, int startIndex, int fromIndex)   { return SequenceUtils.countTrailing(this, chars, startIndex, fromIndex); }
    // @formatter:on

    // @formatter:off
    @NotNull @Override final public T trimStart(@NotNull CharPredicate chars) {  return subSequence(trimStartRange(0, chars));}
    @NotNull @Override final public T trimmedStart(@NotNull CharPredicate chars) { return trimmedStart(0, chars);}
    @NotNull @Override final public T trimEnd(@NotNull CharPredicate chars) { return trimEnd(0, chars);}
    @NotNull @Override final public T trimmedEnd(@NotNull CharPredicate chars) { return trimmedEnd(0, chars);}
    @NotNull @Override final public T trim(@NotNull CharPredicate chars) { return trim(0, chars);}
    @NotNull @Override final public Pair<T, T> trimmed(@NotNull CharPredicate chars) { return trimmed(0, chars);}
    @NotNull @Override final public T trimStart(int keep) { return trimStart(keep, SequenceUtils.WHITESPACE_SET);}
    @NotNull @Override final public T trimmedStart(int keep) { return trimmedStart(keep, SequenceUtils.WHITESPACE_SET);}
    @NotNull @Override final public T trimEnd(int keep) { return trimEnd(keep, SequenceUtils.WHITESPACE_SET);}
    @NotNull @Override final public T trimmedEnd(int keep) { return trimmedEnd(keep, SequenceUtils.WHITESPACE_SET);}
    @NotNull @Override final public T trim(int keep) { return trim(keep, SequenceUtils.WHITESPACE_SET);}
    @NotNull @Override final public Pair<T, T> trimmed(int keep) { return trimmed(keep, SequenceUtils.WHITESPACE_SET);}
    @NotNull @Override final public T trimStart() { return trimStart(0, SequenceUtils.WHITESPACE_SET);}
    @NotNull @Override final public T trimmedStart() { return trimmedStart(0, SequenceUtils.WHITESPACE_SET);}
    @NotNull @Override final public T trimEnd() { return trimEnd(0, SequenceUtils.WHITESPACE_SET);}
    @NotNull @Override final public T trimmedEnd() { return trimmedEnd(0, SequenceUtils.WHITESPACE_SET);}
    @NotNull @Override final public T trim() { return trim(0, SequenceUtils.WHITESPACE_SET);}
    @NotNull @Override final public Pair<T, T> trimmed() { return trimmed(0, SequenceUtils.WHITESPACE_SET);}
    @NotNull @Override final public T trimStart(int keep, @NotNull CharPredicate chars) { return subSequence(trimStartRange(keep, chars));}
    @NotNull @Override final public T trimmedStart(int keep, @NotNull CharPredicate chars) { return subSequenceBefore(trimStartRange(keep, chars));}
    @NotNull @Override final public T trimEnd(int keep, @NotNull CharPredicate chars) { return subSequence(trimEndRange(keep, chars));}
    @NotNull @Override final public T trimmedEnd(int keep, @NotNull CharPredicate chars) { return subSequenceAfter(trimEndRange(keep, chars));}
    @NotNull @Override final public T trim(int keep, @NotNull CharPredicate chars) { return subSequence(trimRange(keep, chars));}
    @NotNull @Override final public Pair<T, T> trimmed(int keep, @NotNull CharPredicate chars) { return subSequenceBeforeAfter(trimRange(keep, chars));}
    // @formatter:on

    // @formatter:off
    @NotNull @Override final public Range trimStartRange(int keep, @NotNull CharPredicate chars)    { return SequenceUtils.trimStartRange(this, keep, chars); }
    @NotNull @Override final public Range trimEndRange(int keep, @NotNull CharPredicate chars)      { return SequenceUtils.trimEndRange(this, keep, chars); }
    @NotNull @Override final public Range trimRange(int keep, @NotNull CharPredicate chars)         { return SequenceUtils.trimRange(this, keep, chars); }
    @NotNull @Override final public Range trimStartRange(@NotNull CharPredicate chars)              { return SequenceUtils.trimStartRange(this, chars); }
    @NotNull @Override final public Range trimEndRange(@NotNull CharPredicate chars)                { return SequenceUtils.trimEndRange(this, chars); }
    @NotNull @Override final public Range trimRange(@NotNull CharPredicate chars)                   { return SequenceUtils.trimRange(this, chars); }
    @NotNull @Override final public Range trimStartRange(int keep)                                  { return SequenceUtils.trimStartRange(this, keep); }
    @NotNull @Override final public Range trimEndRange(int keep)                                    { return SequenceUtils.trimEndRange(this, keep); }
    @NotNull @Override final public Range trimRange(int keep)                                       { return SequenceUtils.trimRange(this, keep); }
    @NotNull @Override final public Range trimStartRange()                                          { return SequenceUtils.trimStartRange(this); }
    @NotNull @Override final public Range trimEndRange()                                            { return SequenceUtils.trimEndRange(this); }
    @NotNull @Override final public Range trimRange()                                               { return SequenceUtils.trimRange(this); }
    // @formatter:on


    @NotNull
    @Override
    final public T padStart(int length, char pad) {
        return length <= length() ? (T) this : sequenceOf(RepeatedSequence.repeatOf(pad, length - length()));
    }

    @NotNull
    @Override
    final public T padEnd(int length, char pad) {
        return length <= length() ? (T) this : append(RepeatedSequence.repeatOf(pad, length - length()));
    }

    @NotNull
    @Override
    final public T padStart(int length) {
        return padStart(length, ' ');
    }

    @NotNull
    @Override
    final public T padEnd(int length) {
        return padEnd(length, ' ');
    }

    // *****************************************************************
    // EOL Helpers
    // *****************************************************************

    @Override
    final public int eolEndLength() {
        return eolEndLength(length());
    }

    @Override
    final public int eolEndLength(int eolEnd) {
        int pos = Math.min(eolEnd - 1, length() - 1);
        if (pos < 0) return 0;

        int len = 0;
        char c = charAt(pos);
        if (c == '\r') {
            if (safeCharAt(pos + 1) != '\n') {
                len = 1;
            }
        } else if (c == '\n') {
            if (safeCharAt(pos - 1) == '\r') {
                len = 2;
            } else {
                len = 1;
            }
        }
        return len;
    }

    @Override
    final public int eolStartLength(int eolStart) {
        int length = length();
        int pos = Math.min(eolStart, length);

        int len = 0;

        if (pos >= 0 && pos < length) {
            char c = charAt(pos);
            if (c == '\r') {
                if (safeCharAt(pos + 1) == '\n') {
                    len = 2;
                } else {
                    len = 1;
                }
            } else if (c == '\n') {
                if (safeCharAt(pos - 1) != '\r') {
                    len = 1;
                }
            }
        }

        return len;
    }

    // @formatter:off
    @Override final public int endOfLine(int index) {return endOfDelimitedBy(SequenceUtils.EOL, index);}
    @Override final public int endOfLineAnyEOL(int index) {return endOfDelimitedByAny(SequenceUtils.ANY_EOL_SET, index);}
    @Override final public int startOfLine(int index) {return startOfDelimitedBy(SequenceUtils.EOL, index);}
    @Override final public int startOfLineAnyEOL(int index) {return startOfDelimitedByAny(SequenceUtils.ANY_EOL_SET, index);}

    @Override final public int startOfDelimitedByAnyNot(@NotNull CharPredicate s, int index) { return startOfDelimitedByAny(s.negate(),index);}
    @Override final public int endOfDelimitedByAnyNot(@NotNull CharPredicate s, int index) { return endOfDelimitedByAny(s.negate(),index);}
    // @formatter:on

    @Override
    final public int startOfDelimitedBy(@NotNull CharSequence s, int index) {
        index = rangeLimit(index, 0, length());
        int offset = lastIndexOf(s, index - 1);
        return offset == -1 ? 0 : offset + 1;
    }

    @Override
    final public int startOfDelimitedByAny(@NotNull CharPredicate s, int index) {
        index = rangeLimit(index, 0, length());
        int offset = lastIndexOfAny(s, index - 1);
        return offset == -1 ? 0 : offset + 1;
    }

    @Override
    final public int endOfDelimitedBy(@NotNull CharSequence s, int index) {
        int length = length();
        index = rangeLimit(index, 0, length);
        int offset = indexOf(s, index);
        return offset == -1 ? length : offset;
    }

    @Override
    final public int endOfDelimitedByAny(@NotNull CharPredicate s, int index) {
        int length = length();
        index = rangeLimit(index, 0, length);
        int offset = indexOfAny(s, index);
        return offset == -1 ? length : offset;
    }

    @NotNull
    @Override
    public Range lineRangeAt(int index) {
        return Range.of(startOfLine(index), endOfLine(index));
    }

    @NotNull
    @Override
    public Range lineRangeAtAnyEOL(int index) {
        return Range.of(startOfLineAnyEOL(index), endOfLineAnyEOL(index));
    }

    // @formatter:off
    @NotNull @Override final public T lineAt(int index) {return subSequence(lineRangeAt(index));}
    @NotNull @Override final public T lineAtAnyEOL(int index) {return subSequence(lineRangeAtAnyEOL(index));}
    // @formatter:on

    @NotNull
    @Override
    final public T trimEOL() {
        int eolLength = eolEndLength();
        return eolLength > 0 ? subSequence(0, length() - eolLength) : (T) this;
    }

    @NotNull
    @Override
    final public T trimmedEOL() {
        int eolLength = eolEndLength();
        return eolLength > 0 ? subSequence(length() - eolLength) : nullSequence();
    }

    @NotNull
    @Override
    final public Range eolEndRange(int eolEnd) {
        int eolLength = eolEndLength(eolEnd);
        return eolLength == 0 ? Range.NULL : Range.of(eolEnd - eolLength, eolEnd);
    }

    @NotNull
    @Override
    public Range eolStartRange(int eolStart) {
        int eolLength = eolStartLength(eolStart);
        return eolLength == 0 ? Range.NULL : Range.of(eolStart, eolStart + eolLength);
    }

    // @formatter:off
    @NotNull @Override final public T trimTailBlankLines() {Range range = trailingBlankLinesRange();return range.isNull() ?  (T) this : subSequenceBefore(range);}
    @NotNull @Override final public T trimLeadBlankLines() {Range range = leadingBlankLinesRange();return range.isNull() ?  (T) this : subSequenceAfter(range);}
    @NotNull @Override final public Range leadingBlankLinesRange() {return leadingBlankLinesRange(SequenceUtils.EOL_SET, 0, length());}
    @NotNull @Override final public Range leadingBlankLinesRange(int startIndex) {return leadingBlankLinesRange(SequenceUtils.EOL_SET, startIndex, length());}
    @NotNull @Override final public Range leadingBlankLinesRange(int fromIndex, int endIndex) { return leadingBlankLinesRange(SequenceUtils.EOL_SET, fromIndex, endIndex);}
    @NotNull @Override final public Range trailingBlankLinesRange() {return trailingBlankLinesRange(SequenceUtils.EOL_SET, 0, length());}
    @NotNull @Override final public Range trailingBlankLinesRange(int fromIndex) {return trailingBlankLinesRange(SequenceUtils.EOL_SET, fromIndex, length());}
    @NotNull @Override final public Range trailingBlankLinesRange(int startIndex, int fromIndex) { return trailingBlankLinesRange(SequenceUtils.EOL_SET,startIndex,fromIndex);}
    // @formatter:on

    @NotNull
    @Override
    final public Range trailingBlankLinesRange(CharPredicate eolChars, int startIndex, int fromIndex) {
        fromIndex = Math.min(fromIndex, length());
        startIndex = rangeLimit(startIndex, 0, fromIndex);

        int iMax = fromIndex;
        int lastEOL = iMax;
        int i;

        for (i = iMax; i-- > startIndex; ) {
            char c = charAt(i);
            if (eolChars.test(c)) lastEOL = Math.min(i + Math.min(eolStartLength(i), 1), fromIndex);
            else if (c != ' ' && c != '\t') break;
        }

        if (i < startIndex) return Range.of(startIndex, fromIndex);
        else if (lastEOL != iMax) return Range.of(lastEOL, fromIndex);
        else return Range.NULL;
    }

    @NotNull
    @Override
    final public Range leadingBlankLinesRange(@NotNull CharPredicate eolChars, int fromIndex, int endIndex) {
        endIndex = Math.min(endIndex, length());
        fromIndex = rangeLimit(fromIndex, 0, endIndex);

        int iMax = endIndex;
        int lastEOL = -1;
        int i;

        for (i = fromIndex; i < iMax; i++) {
            char c = charAt(i);
            if (eolChars.test(c)) lastEOL = i;
            else if (c != ' ' && c != '\t') break;
        }

        if (i == iMax) return Range.of(fromIndex, endIndex);
        else if (lastEOL >= 0) return Range.of(fromIndex, Math.min(lastEOL + Math.min(eolStartLength(lastEOL), 1), endIndex));
        else return Range.NULL;
    }

    // @formatter:off
    @NotNull @Override final public List<Range> blankLinesRemovedRanges() { return blankLinesRemovedRanges(SequenceUtils.EOL_SET, 0, length());}
    @NotNull @Override final public List<Range> blankLinesRemovedRanges(int fromIndex) { return blankLinesRemovedRanges(SequenceUtils.EOL_SET, fromIndex, length());}
    @NotNull @Override final public List<Range> blankLinesRemovedRanges(int fromIndex, int endIndex) { return blankLinesRemovedRanges(SequenceUtils.EOL_SET, fromIndex, endIndex);}
    // @formatter:on

    @NotNull
    @Override
    final public List<Range> blankLinesRemovedRanges(@NotNull CharPredicate eolChars, int fromIndex, int endIndex) {
        endIndex = Math.min(endIndex, length());
        fromIndex = rangeLimit(fromIndex, 0, endIndex);
        int lastPos = fromIndex;
        ArrayList<Range> ranges = new ArrayList<>();

        while (lastPos < endIndex) {
            Range blankLines = leadingBlankLinesRange(eolChars, lastPos, endIndex);
            if (blankLines.isNull()) {
                int endOfLine = Math.min(endOfLine(lastPos) + 1, endIndex);
                if (lastPos < endOfLine) ranges.add(Range.of(lastPos, endOfLine));
                lastPos = endOfLine;
            } else {
                if (lastPos < blankLines.getStart()) ranges.add(Range.of(lastPos, blankLines.getStart()));
                lastPos = blankLines.getEnd();
            }
        }
        return ranges;
    }

    // @formatter:off
    @NotNull @Override public T trimToEndOfLine(boolean includeEol)                 { return trimToEndOfLine(SequenceUtils.EOL_SET, includeEol, 0); }
    @NotNull @Override public T trimToEndOfLine(int index)                          { return trimToEndOfLine(SequenceUtils.EOL_SET, false, 0); }
    @NotNull @Override public T trimToEndOfLine()                                   { return trimToEndOfLine(SequenceUtils.EOL_SET, false, 0); }
    @NotNull @Override public T trimToEndOfLine(boolean includeEol, int index)      { return trimToEndOfLine(SequenceUtils.EOL_SET, includeEol, index); }

    @NotNull @Override public T trimToStartOfLine(boolean includeEol)               { return trimToStartOfLine(SequenceUtils.EOL_SET, includeEol, 0); }
    @NotNull @Override public T trimToStartOfLine(int index)                        { return trimToStartOfLine(SequenceUtils.EOL_SET, false, 0); }
    @NotNull @Override public T trimToStartOfLine()                                 { return trimToStartOfLine(SequenceUtils.EOL_SET, false, 0); }
    @NotNull @Override public T trimToStartOfLine(boolean includeEol, int index)    { return trimToStartOfLine(SequenceUtils.EOL_SET, includeEol, index); }
    // @formatter:on

    @NotNull
    @Override
    public T trimToEndOfLine(@NotNull CharPredicate eolChars, boolean includeEol, int index) {
        int eolPos = endOfDelimitedByAny(eolChars, index);
        if (eolPos < length()) {
            int endIndex = includeEol ? eolPos + eolStartLength(eolPos) : eolPos;
            return subSequence(0, endIndex);
        }
        return (T) this;
    }

    @NotNull
    @Override
    public T trimToStartOfLine(@NotNull CharPredicate eolChars, boolean includeEol, int index) {
        int eolPos = startOfDelimitedByAny(eolChars, index);
        if (eolPos > 0) {
            int startIndex = includeEol ? eolPos - eolEndLength(eolPos - 1) : eolPos;
            return subSequence(startIndex);
        }
        return (T) this;
    }

    // @formatter:off
    @NotNull @Override final public T ifNull(@NotNull T other) {return isNull() ? other : (T) this;}
    @NotNull @Override final public T ifNullEmptyAfter(@NotNull T other) {return isNull() ? other.subSequence(other.length(), other.length()) : (T) this;}
    @NotNull @Override final public T ifNullEmptyBefore(@NotNull T other) {return isNull() ? other.subSequence(0, 0) : (T) this;}
    @NotNull @Override final public T nullIfEmpty() {return isEmpty() ? nullSequence() : (T) this;}
    @NotNull @Override final public T nullIfBlank() {return isBlank() ? nullSequence() : (T) this;}
    @NotNull @Override final public T nullIf(boolean condition) {return condition ? nullSequence() : (T) this;}
    @NotNull @Override final public T nullIfNot(@NotNull BiPredicate<? super T, ? super CharSequence> predicate, CharSequence... matches) {return nullIf(predicate.negate(),matches);}
    @NotNull @Override final public T nullIf(@NotNull Predicate<? super CharSequence> predicate, CharSequence... matches) {return nullIf((o1, o2) -> predicate.test(o2), matches);}
    @NotNull @Override final public T nullIfNot(@NotNull Predicate<? super CharSequence> predicate, CharSequence... matches) {return nullIfNot((o1, o2) -> predicate.test(o2), matches);}
    @NotNull @Override final public T nullIf(CharSequence... matches) {return nullIf((Predicate<? super CharSequence>) this::matches,matches);}
    @NotNull @Override final public T nullIfNot(CharSequence... matches) {return nullIfNot((Predicate<? super CharSequence>) this::matches,matches);}
    @NotNull @Override final public T nullIfStartsWith(CharSequence... matches) {return nullIf((Predicate<? super CharSequence>) this::startsWith,matches);}
    @NotNull @Override final public T nullIfNotStartsWith(CharSequence... matches) {return nullIfNot((Predicate<? super CharSequence>) this::startsWith,matches);}
    @NotNull @Override final public T nullIfEndsWith(CharSequence... matches) {return nullIf((Predicate<? super CharSequence>) this::endsWith,matches);}
    @NotNull @Override final public T nullIfNotEndsWith(CharSequence... matches) {return nullIfNot((Predicate<? super CharSequence>) this::endsWith,matches);}
    @NotNull @Override final public T nullIfStartsWithIgnoreCase(CharSequence... matches) {return nullIf((Predicate<? super CharSequence>) this::startsWithIgnoreCase,matches);}
    @NotNull @Override final public T nullIfNotStartsWithIgnoreCase(CharSequence... matches) {return nullIfNot((Predicate<? super CharSequence>) this::startsWithIgnoreCase,matches);}
    @NotNull @Override final public T nullIfEndsWithIgnoreCase(CharSequence... matches) {return nullIf((Predicate<? super CharSequence>) this::endsWithIgnoreCase,matches);}
    @NotNull @Override final public T nullIfNotEndsWithIgnoreCase(CharSequence... matches) {return nullIfNot((Predicate<? super CharSequence>) this::endsWithIgnoreCase,matches);}
    @NotNull @Override final public T nullIfStartsWith(boolean ignoreCase, CharSequence... matches) {return nullIf((Predicate<? super CharSequence>) prefix -> startsWith(prefix, ignoreCase),matches);}
    @NotNull @Override final public T nullIfNotStartsWith(boolean ignoreCase, CharSequence... matches) {return nullIfNot((Predicate<? super CharSequence>) prefix -> startsWith(prefix, ignoreCase),matches);}
    @NotNull @Override final public T nullIfEndsWith(boolean ignoreCase, CharSequence... matches) {return nullIf((Predicate<? super CharSequence>) suffix -> endsWith(suffix, ignoreCase),matches);}
    @NotNull @Override final public T nullIfNotEndsWith(boolean ignoreCase, CharSequence... matches) {return nullIfNot((Predicate<? super CharSequence>) suffix -> endsWith(suffix, ignoreCase),matches);}
    // @formatter:on

    @NotNull
    @Override
    final public T nullIf(@NotNull BiPredicate<? super T, ? super CharSequence> predicate, CharSequence... matches) {
        for (CharSequence match : matches) {
            if (predicate.test((T) this, match)) return nullSequence();
        }
        return (T) this;
    }

    // @formatter:off
    @Override final public boolean isEmpty() {return length() == 0;}
    @Override final public boolean isBlank() {return isEmpty() || countLeading(SequenceUtils.WHITESPACE_SET, 0, length()) == length();}
    @Override final public boolean isNotEmpty() {return length() != 0;}
    @Override final public boolean isNotBlank() {return !isBlank();}
    @Override final public boolean isNull() {return this == nullSequence();}
    @Override final public boolean isNotNull() {return this != nullSequence();}

    @Override final public boolean endsWith(@NotNull CharSequence suffix) {return length() > 0 && matchCharsReversed(suffix, length() - 1, false);}
    @Override final public boolean endsWith(@NotNull CharSequence suffix, boolean ignoreCase) {return length() > 0 && matchCharsReversed(suffix, length() - 1, ignoreCase);}
    @Override final public boolean startsWith(@NotNull CharSequence prefix) {return length() > 0 && matchChars(prefix, 0, false);}
    @Override final public boolean startsWith(@NotNull CharSequence prefix, boolean ignoreCase) {return length() > 0 && matchChars(prefix, 0, ignoreCase);}

    @Override final public boolean endsWith(@NotNull CharPredicate chars) {return countTrailing(chars) > 0;}
    @Override final public boolean startsWith(@NotNull CharPredicate chars) {return countLeading(chars) > 0;}

    @Override final public boolean endsWithEOL() { return endsWith(SequenceUtils.EOL_SET); }
    @Override final public boolean endsWithAnyEOL() { return endsWith(SequenceUtils.ANY_EOL_SET); }
    @Override final public boolean endsWithSpace() { return endsWith(SequenceUtils.SPACE_SET); }
    @Override final public boolean endsWithSpaceTab() { return endsWith(SequenceUtils.SPACE_TAB_SET); }
    @Override final public boolean endsWithWhitespace() { return endsWith(SequenceUtils.WHITESPACE_SET); }

    @Override final public boolean startsWithEOL() { return startsWith(SequenceUtils.EOL_SET); }
    @Override final public boolean startsWithAnyEOL() { return startsWith(SequenceUtils.ANY_EOL_SET); }
    @Override final public boolean startsWithSpace() { return startsWith(SequenceUtils.SPACE_SET); }
    @Override final public boolean startsWithSpaceTab() { return startsWith(SequenceUtils.SPACE_TAB_SET); }
    @Override final public boolean startsWithWhitespace() { return startsWith(SequenceUtils.WHITESPACE_SET); }
    // @formatter:on

    // @formatter:off
    @NotNull @Override final public T removeSuffix(@NotNull CharSequence suffix) {return !endsWith(suffix) ? (T) this : subSequence(0, length() - suffix.length());}
    @NotNull @Override final public T removePrefix(@NotNull CharSequence prefix) {return !startsWith(prefix) ? (T) this : subSequence(prefix.length(), length());}
    @NotNull @Override final public T removeProperSuffix(@NotNull CharSequence suffix) {return length() <= suffix.length() || !endsWith(suffix) ? (T) this : subSequence(0, length() - suffix.length());}
    @NotNull @Override final public T removeProperPrefix(@NotNull CharSequence prefix) {return length() <= prefix.length() || !startsWith(prefix) ? (T) this : subSequence(prefix.length(), length());}
    @Override final public boolean endsWithIgnoreCase(@NotNull CharSequence suffix) {return length() > 0 && matchCharsReversed(suffix, length() - 1, true);}
    @Override final public boolean startsWithIgnoreCase(@NotNull CharSequence prefix) {return length() > 0 && matchChars(prefix, 0, true);}
    @NotNull @Override final public T removeSuffixIgnoreCase(@NotNull CharSequence suffix) {return !endsWithIgnoreCase(suffix) ? (T) this : subSequence(0, length() - suffix.length());}
    @NotNull @Override final public T removePrefixIgnoreCase(@NotNull CharSequence prefix) {return !startsWithIgnoreCase(prefix) ? (T) this : subSequence(prefix.length(), length());}
    @NotNull @Override final public T removeProperSuffixIgnoreCase(@NotNull CharSequence suffix) {return length() <= suffix.length() || !endsWithIgnoreCase(suffix) ? (T) this : subSequence(0, length() - suffix.length());}
    @NotNull @Override final public T removeProperPrefixIgnoreCase(@NotNull CharSequence prefix) {return length() <= prefix.length() || !startsWithIgnoreCase(prefix) ? (T) this : subSequence(prefix.length(), length());}
    @NotNull @Override final public T removeSuffix(@NotNull CharSequence suffix, boolean ignoreCase) {return !endsWith(suffix, ignoreCase) ? (T) this : subSequence(0, length() - suffix.length());}
    @NotNull @Override final public T removePrefix(@NotNull CharSequence prefix, boolean ignoreCase) {return !startsWith(prefix, ignoreCase) ? (T) this : subSequence(prefix.length(), length());}
    @NotNull @Override final public T removeProperSuffix(@NotNull CharSequence suffix, boolean ignoreCase) {return length() <= suffix.length() || !endsWith(suffix, ignoreCase) ? (T) this : subSequence(0, length() - suffix.length());}
    @NotNull @Override final public T removeProperPrefix(@NotNull CharSequence prefix, boolean ignoreCase) {return length() <= prefix.length() || !startsWith(prefix, ignoreCase) ? (T) this : subSequence(prefix.length(), length());}
    // @formatter:on

    @NotNull
    @Override
    public T insert(int index, @NotNull CharSequence chars) {
        index = Math.max(0, Math.min(length(), index));

        if (chars.length() == 0) {
            return (T) this;
        } else if (index == 0) {
            return prefixWith(chars);
        } else if (index == length()) {
            return suffixWith(chars);
        } else {
            return getBuilder().add(subSequence(0, index)).add(chars).add(subSequence(index)).toSequence();
        }
    }

    @NotNull
    @Override
    public T delete(int startIndex, int endIndex) {
        endIndex = Math.max(0, Math.min(length(), endIndex));
        startIndex = Math.min(endIndex, Math.max(0, startIndex));

        if (startIndex == endIndex) {
            return (T) this;
        } else if (startIndex == 0) {
            return subSequence(endIndex);
        } else if (endIndex == length()) {
            return subSequence(0, startIndex);
        } else {
            return getBuilder().add(subSequence(0, startIndex)).add(subSequence(endIndex)).toSequence();
        }
    }

    @NotNull
    @Override
    final public T toLowerCase() {
        return toMapped(ChangeCase.toLowerCase);
    }

    @NotNull
    @Override
    final public T toUpperCase() {
        return toMapped(ChangeCase.toUpperCase);
    }

    @NotNull
    @Override
    final public T toNbSp() {
        return toMapped(SpaceMapper.toNonBreakSpace);
    }

    @NotNull
    @Override
    final public T toSpc() {
        return toMapped(SpaceMapper.fromNonBreakSpace);
    }

    // TEST:
    // @formatter:off
    @Override final public boolean matches(@NotNull CharSequence chars, boolean ignoreCase)                                     { return SequenceUtils.matches(this, chars, ignoreCase);}
    @Override final public boolean matches(@NotNull CharSequence chars)                                                         { return SequenceUtils.matches(this, chars);}
    @Override final public boolean matchesIgnoreCase(@NotNull CharSequence chars)                                               { return SequenceUtils.matchesIgnoreCase(this, chars); }

    @Override final public boolean matchChars(@NotNull CharSequence chars, int startIndex, boolean ignoreCase)                  { return SequenceUtils.matchChars(this, chars, startIndex, ignoreCase); }
    @Override final public boolean matchChars(@NotNull CharSequence chars, int startIndex)                                      { return SequenceUtils.matchChars(this, chars, startIndex);}
    @Override final public boolean matchCharsIgnoreCase(@NotNull CharSequence chars, int startIndex)                            { return SequenceUtils.matchCharsIgnoreCase(this, chars, startIndex); }

    @Override final public boolean matchChars(@NotNull CharSequence chars, boolean ignoreCase)                                  { return SequenceUtils.matchChars(this, chars, ignoreCase); }
    @Override final public boolean matchChars(@NotNull CharSequence chars)                                                      { return SequenceUtils.matchChars(this, chars); }
    @Override final public boolean matchCharsIgnoreCase(@NotNull CharSequence chars)                                            { return SequenceUtils.matchCharsIgnoreCase(this, chars); }

    @Override final public boolean matchCharsReversed(@NotNull CharSequence chars, int endIndex, boolean ignoreCase)            { return SequenceUtils.matchCharsReversed(this, chars, endIndex, ignoreCase); }
    @Override final public boolean matchCharsReversed(@NotNull CharSequence chars, int endIndex)                                { return SequenceUtils.matchCharsReversed(this, chars, endIndex); }
    @Override final public boolean matchCharsReversedIgnoreCase(@NotNull CharSequence chars, int endIndex)                      { return SequenceUtils.matchCharsReversedIgnoreCase(this, chars, endIndex); }

    @Override final public int matchedCharCount(@NotNull CharSequence chars, int startIndex, int endIndex, boolean ignoreCase)  { return SequenceUtils.matchedCharCount(this, chars, startIndex, endIndex, ignoreCase); }
    @Override final public int matchedCharCount(@NotNull CharSequence chars, int startIndex, boolean ignoreCase)                { return SequenceUtils.matchedCharCount(this, chars, startIndex, ignoreCase); }
    @Override final public int matchedCharCount(@NotNull CharSequence chars, int startIndex, int endIndex)                      { return SequenceUtils.matchedCharCount(this, chars, startIndex, endIndex); }
    @Override final public int matchedCharCount(@NotNull CharSequence chars, int startIndex)                                    { return SequenceUtils.matchedCharCount(this, chars, startIndex); }
    @Override final public int matchedCharCountIgnoreCase(@NotNull CharSequence chars, int startIndex)                          { return SequenceUtils.matchedCharCountIgnoreCase(this, chars, startIndex); }
    @Override final public int matchedCharCountIgnoreCase(@NotNull CharSequence chars, int startIndex, int endIndex)            { return SequenceUtils.matchedCharCountIgnoreCase(this, chars, startIndex, endIndex); }

    @Override final public int matchedCharCountReversedIgnoreCase(@NotNull CharSequence chars, int startIndex, int fromIndex)   { return SequenceUtils.matchedCharCountReversedIgnoreCase(this, chars, startIndex, fromIndex); }
    @Override final public int matchedCharCountReversed(@NotNull CharSequence chars, int startIndex, int fromIndex)             { return SequenceUtils.matchedCharCountReversed(this, chars, startIndex, fromIndex); }

    @Override final public int matchedCharCountReversed(@NotNull CharSequence chars, int fromIndex, boolean ignoreCase)         { return SequenceUtils.matchedCharCountReversed(this, chars, fromIndex, ignoreCase); }
    @Override final public int matchedCharCountReversed(@NotNull CharSequence chars, int fromIndex)                             { return SequenceUtils.matchedCharCountReversed(this, chars, fromIndex); }
    @Override final public int matchedCharCountReversedIgnoreCase(@NotNull CharSequence chars, int fromIndex)                   { return SequenceUtils.matchedCharCountReversedIgnoreCase(this, chars, fromIndex); }
    @Override final public int matchedCharCount(@NotNull CharSequence chars, int startIndex, int endIndex, boolean fullMatchOnly, boolean ignoreCase) { return SequenceUtils.matchedCharCount(this, chars, startIndex, endIndex, fullMatchOnly, ignoreCase); }
    @Override final public int matchedCharCountReversed(@NotNull CharSequence chars, int startIndex, int fromIndex, boolean ignoreCase) { return SequenceUtils.matchedCharCountReversed(this, chars, startIndex, fromIndex, ignoreCase);}
    // @formatter:on

    @NotNull
    @Override
    public String toString() {
        int iMax = length();
        StringBuilder sb = new StringBuilder(iMax);

        for (int i = 0; i < iMax; i++) {
            sb.append(charAt(i));
        }

        return sb.toString();
    }

    @NotNull
    @Override
    final public String normalizeEOL() {
        return Escaping.normalizeEOL(toString());
    }

    @NotNull
    @Override
    final public String normalizeEndWithEOL() {
        return Escaping.normalizeEndWithEOL(toString());
    }

    @NotNull
    @Override
    final public String toVisibleWhitespaceString() {
        StringBuilder sb = new StringBuilder();
        int iMax = length();
        for (int i = 0; i < iMax; i++) {
            char c = charAt(i);
            String s = SequenceUtils.visibleSpacesMap.get(c);

            if (s != null) {
                sb.append(s);
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    // @formatter:off
    @NotNull @Override final public List<T> splitList(@NotNull CharSequence delimiter)                                                                          { return splitList(delimiter, 0, 0, null); }
    @NotNull @Override final public List<T> splitList(@NotNull CharSequence delimiter, int limit, boolean includeDelims, @Nullable CharPredicate trimChars)     { return splitList(delimiter, limit, includeDelims ? SequenceUtils.SPLIT_INCLUDE_DELIMS : 0, trimChars); }
    @NotNull @Override final public List<T> splitList(@NotNull CharSequence delimiter, int limit, int flags)                                                    { return splitList(delimiter, limit, flags, null); }
    @NotNull @Override final public List<T> splitList(@NotNull CharSequence delimiter, boolean includeDelims, @Nullable CharPredicate trimChars)                { return splitList(delimiter, 0, includeDelims ? SequenceUtils.SPLIT_INCLUDE_DELIMS : 0, trimChars); }

    // NOTE: these default to including delimiters as part of split item
    @NotNull @Override final public List<T> splitListEOL()                                                                                                      { return splitList(SequenceUtils.EOL, 0, SequenceUtils.SPLIT_INCLUDE_DELIMS, null); }
    @NotNull @Override final public List<T> splitListEOL(boolean includeDelims)                                                                                 { return splitList(SequenceUtils.EOL, 0, includeDelims ? SequenceUtils.SPLIT_INCLUDE_DELIMS : 0, null); }
    @NotNull @Override final public List<T> splitListEOL(boolean includeDelims, @Nullable CharPredicate trimChars)                                              { return splitList(SequenceUtils.EOL, 0, includeDelims ? SequenceUtils.SPLIT_INCLUDE_DELIMS : 0, trimChars); }

    @NotNull @Override final public T[] splitEOL()                                                                                                              { return split(SequenceUtils.EOL, 0, SequenceUtils.SPLIT_INCLUDE_DELIMS,null); }
    @NotNull @Override final public T[] splitEOL(boolean includeDelims)                                                                                         { return split(SequenceUtils.EOL, 0, includeDelims ? SequenceUtils.SPLIT_INCLUDE_DELIMS : 0, null); }
    @NotNull @Override final public T[] split(@NotNull CharSequence delimiter, boolean includeDelims, @Nullable CharPredicate trimChars)                        { return split(SequenceUtils.EOL, 0, includeDelims ? SequenceUtils.SPLIT_INCLUDE_DELIMS : 0, trimChars); }
    @NotNull @Override final public T[] split(@NotNull CharSequence delimiter)                                                                                  { return split(delimiter, 0, 0, null); }
    @NotNull @Override final public T[] split(@NotNull CharSequence delimiter, int limit, boolean includeDelims, @Nullable CharPredicate trimChars)             { return split(delimiter, limit, includeDelims ? SequenceUtils.SPLIT_INCLUDE_DELIMS : 0, trimChars); }
    @NotNull @Override final public T[] split(@NotNull CharSequence delimiter, int limit, int flags)                                                            { return split(delimiter, limit, flags, null); }
    // @formatter:on

    @NotNull
    @Override
    final public T[] split(@NotNull CharSequence delimiter, int limit, int flags, @Nullable CharPredicate trimChars) {
        return splitList(delimiter, limit, flags, trimChars).toArray(emptyArray());
    }

    @NotNull
    final public List<T> splitList(@NotNull CharSequence delimiter, int limit, int flags, @Nullable CharPredicate trimChars) {
        if (trimChars == null) trimChars = SequenceUtils.WHITESPACE_SET;
        else flags |= SequenceUtils.SPLIT_TRIM_PARTS;

        if (limit < 1) limit = Integer.MAX_VALUE;

        boolean includeDelimiterParts = (flags & SequenceUtils.SPLIT_INCLUDE_DELIM_PARTS) != 0;
        int includeDelimiter = !includeDelimiterParts && (flags & SequenceUtils.SPLIT_INCLUDE_DELIMS) != 0 ? delimiter.length() : 0;
        boolean trimParts = (flags & SequenceUtils.SPLIT_TRIM_PARTS) != 0;
        boolean skipEmpty = (flags & SequenceUtils.SPLIT_SKIP_EMPTY) != 0;
        ArrayList<T> items = new ArrayList<>();

        int lastPos = 0;
        int length = length();
        if (limit > 1) {
            while (lastPos < length) {
                int pos = indexOf(delimiter, lastPos);
                if (pos < 0) break;

                if (lastPos < pos || !skipEmpty) {
                    T item = subSequence(lastPos, pos + includeDelimiter);
                    if (trimParts) item = item.trim(trimChars);
                    if (!item.isEmpty() || !skipEmpty) {
                        items.add(item);
                        if (includeDelimiterParts) {
                            items.add(subSequence(pos, pos + delimiter.length()));
                        }
                        if (items.size() >= limit - 1) {
                            lastPos = pos + 1;
                            break;
                        }
                    }
                }
                lastPos = pos + 1;
            }
        }

        if (lastPos < length) {
            T item = subSequence(lastPos, length);
            if (trimParts) item = item.trim(trimChars);
            if (!item.isEmpty() || !skipEmpty) {
                items.add(item);
            }
        }
        return items;
    }

    // @formatter:off
    @NotNull @Override final public T appendTo(@NotNull StringBuilder out, @Nullable CharMapper charMapper) {return appendTo(out, charMapper, 0, length());}
    @NotNull @Override final public T appendTo(@NotNull StringBuilder out, @Nullable CharMapper charMapper, int startIndex) {return appendTo(out, charMapper, startIndex, length());}
    @NotNull @Override final public T appendTo(@NotNull StringBuilder out) {return appendTo(out, null, 0, length());}
    @NotNull @Override final public T appendTo(@NotNull StringBuilder out, int startIndex) {return appendTo(out, null, startIndex, length());}
    @NotNull @Override final public T appendTo(@NotNull StringBuilder out, int startIndex, int endIndex) {return appendTo(out, null, startIndex, endIndex);}
    // @formatter:on

    @NotNull
    @Override
    final public T appendTo(@NotNull StringBuilder out, @Nullable CharMapper charMapper, int startIndex, int endIndex) {
        CharSequence useSequence = charMapper == null ? this : toMapped(charMapper);
        out.append(useSequence, startIndex, endIndex);
        return (T) this;
    }

    // @formatter:off
    @NotNull @Override final public T appendRangesTo(@NotNull StringBuilder out, @Nullable CharMapper charMapper, Range... ranges) {return appendRangesTo(out, charMapper, new ArrayIterable<>(ranges));}
    @NotNull @Override final public T appendRangesTo(@NotNull StringBuilder out, Range... ranges) {return appendRangesTo(out, null, new ArrayIterable<>(ranges));}
    @NotNull @Override final public T appendRangesTo(@NotNull StringBuilder out, Iterable<? extends Range> ranges) {return appendRangesTo(out, null, ranges);}
    // @formatter:on

    @NotNull
    @Override
    final public T appendRangesTo(@NotNull StringBuilder out, @Nullable CharMapper charMapper, Iterable<? extends Range> ranges) {
        CharSequence useSequence = charMapper == null ? this : toMapped(charMapper);

        for (Range range : ranges) {
            if (range != null && range.isNotNull()) out.append(useSequence, range.getStart(), range.getEnd());
        }
        return (T) this;
    }

    @NotNull
    @Override
    final public int[] indexOfAll(@NotNull CharSequence s) {
        int length = s.length();
        if (length == 0) return SequenceUtils.EMPTY_INDICES;
        int pos = indexOf(s);
        if (pos == -1) return SequenceUtils.EMPTY_INDICES;

        int iMax = 0;
        int[] indices = new int[32];
        indices[iMax++] = pos;

        while (true) {
            pos = indexOf(s, pos + length);
            if (pos == -1) break;
            if (indices.length <= iMax) indices = SequenceUtils.expandTo(indices, iMax + 1, 32);
            indices[iMax++] = pos;
        }
        return SequenceUtils.truncateTo(indices, iMax);
    }

    // @formatter:off
    @Override @NotNull final public T appendEOL() { return suffixWith(SequenceUtils.EOL); }
    @Override @NotNull final public T suffixWithEOL() { return suffixWith(SequenceUtils.EOL); }
    @Override @NotNull final public T prefixWithEOL() { return prefixWith(SequenceUtils.EOL); }
    @Override @NotNull final public T prefixOnceWithEOL() { return prefixOnceWith(SequenceUtils.EOL); }
    @Override @NotNull final public T suffixOnceWithEOL() { return suffixOnceWith(SequenceUtils.EOL); }

    @Override @NotNull final public T appendSpace() { return suffixWith(SequenceUtils.SPACE); }
    @Override @NotNull final public T suffixWithSpace() { return suffixWith(SequenceUtils.SPACE); }
    @Override @NotNull final public T prefixWithSpace() { return prefixWith(SequenceUtils.SPACE); }
    @Override @NotNull final public T appendSpaces(int count) { return suffixWith(RepeatedSequence.ofSpaces(count)); }
    @Override @NotNull final public T suffixWithSpaces(int count) { return suffixWith(RepeatedSequence.ofSpaces(count)); }
    @Override @NotNull final public T prefixWithSpaces(int count) { return prefixWith(RepeatedSequence.ofSpaces(count)); }
    @Override @NotNull final public T prefixOnceWithSpace() { return prefixOnceWith(SequenceUtils.SPACE); }
    @Override @NotNull final public T suffixOnceWithSpace() { return suffixOnceWith(SequenceUtils.SPACE); }
    // @formatter:on

    @NotNull
    @Override
    public T prefixWith(@Nullable CharSequence prefix) {
        return prefix == null || prefix.length() == 0 ? (T) this : getBuilder().add(prefix).add(this).toSequence();
    }

    @NotNull
    @Override
    public T suffixWith(@Nullable CharSequence suffix) {
        // convoluted to allow BasedCharSequence to use PrefixedCharSequence so all fits into SegmentedCharSequence
        return suffix == null || suffix.length() == 0 ? (T) this : getBuilder().add(this).add(suffix).toSequence();
    }

    @NotNull
    @Override
    final public T prefixOnceWith(@Nullable CharSequence prefix) {
        return prefix == null || prefix.length() == 0 || startsWith(prefix) ? (T) this : prefixWith(prefix);
    }

    @NotNull
    @Override
    final public T suffixOnceWith(@Nullable CharSequence suffix) {
        return suffix == null || suffix.length() == 0 || endsWith(suffix) ? (T) this : suffixWith(suffix);
    }

    @NotNull
    @Override
    final public T replace(int startIndex, int endIndex, @NotNull CharSequence replacement) {
        int length = length();
        startIndex = Math.max(startIndex, 0);
        endIndex = Math.min(endIndex, length);

        SequenceBuilder<?, T> segments = getBuilder();
        return segments.add(subSequence(0, startIndex)).add(replacement).add(subSequence(endIndex)).toSequence();
    }

    @NotNull
    @Override
    final public T replace(@NotNull CharSequence find, @NotNull CharSequence replace) {
        int[] indices = indexOfAll(find);
        if (indices.length == 0) return (T) this;
        SequenceBuilder<?, T> segments = getBuilder();

        int iMax = indices.length;
        int length = length();

        int i = 0;
        int lastPos = 0;
        while (i < iMax) {
            int pos = indices[i++];
            if (lastPos < pos) segments.add(subSequence(lastPos, pos));
            lastPos = pos + find.length();
            segments.add(replace);
        }

        if (lastPos < length) {
            segments.add(subSequence(lastPos, length));
        }

        return segments.toSequence();
    }

    @NotNull
    @Override
    final public T append(CharSequence... sequences) {
        return append(new ArrayIterable<>(sequences));
    }

    @NotNull
    @Override
    final public T append(Iterable<? extends CharSequence> sequences) {
        SequenceBuilder<?, T> segments = getBuilder();
        segments.add(this);
        for (CharSequence sequence : sequences) {
            segments.add(sequence);
        }
        return segments.toSequence();
    }

    @NotNull
    @Override
    final public T extractRanges(Range... ranges) {
        return extractRanges(new ArrayIterable<>(ranges));
    }

    @NotNull
    @Override
    final public T extractRanges(Iterable<Range> ranges) {
        SequenceBuilder<?, T> segments = getBuilder();
        for (Range range : ranges) {
            if (!(range == null || range.isNull())) segments.add(range.safeSubSequence(this));
        }
        return segments.toSequence();
    }

    @Override
    final public int columnAtIndex(int index) {
        int lineStart = lastIndexOfAny(SequenceUtils.ANY_EOL_SET, index);
        return index - (lineStart == -1 ? 0 : lineStart + eolStartLength(lineStart));
    }

    @NotNull
    @Override
    final public Pair<Integer, Integer> lineColumnAtIndex(int index) {
        int iMax = length();
        if (index < 0 || index > iMax) {
            throw new IllegalArgumentException("Index: " + index + " out of range [0, " + iMax + "]");
        }

        boolean hadCr = false;
        int line = 0;
        int col = 0;
        for (int i = 0; i < index; i++) {
            char c1 = charAt(i);
            if (c1 == '\r') {
                col = 0;
                line++;
                hadCr = true;
            } else if (c1 == '\n') {
                if (!hadCr) {
                    line++;
                }
                col = 0;
                hadCr = false;
            } else {
                col++;
            }
        }

        return new Pair<>(line, col);
    }
}
