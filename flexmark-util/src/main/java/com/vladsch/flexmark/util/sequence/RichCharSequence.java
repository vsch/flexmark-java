package com.vladsch.flexmark.util.sequence;

import com.vladsch.flexmark.util.Pair;
import com.vladsch.flexmark.util.mappers.CharMapper;

import java.util.Locale;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/**
 * A CharSequence that references original char sequence and maps '\0' to '\uFFFD'
 * a subSequence() returns a sub-sequence from the original base sequence
 */
@SuppressWarnings("SameParameterValue")
public interface RichCharSequence<T extends RichCharSequence<?>> extends CharSequence, Comparable<CharSequence> {
    String EOL = "\n";
    String SPACE = " ";

    String WHITESPACE_NO_EOL_CHARS = " \t";
    String WHITESPACE_CHARS = " \t\r\n";
    String WHITESPACE_NBSP_CHARS = " \t\r\n\u00A0";

    String EOL_CHARS = "\r\n";
    char EOL_CHAR = EOL_CHARS.charAt(1);
    char EOL_CHAR1 = EOL_CHARS.charAt(0);
    char EOL_CHAR2 = EOL_CHARS.charAt(1);

    T[] emptyArray();
    T nullSequence();

    /**
     * @return the last character of the sequence or '\0' if empty
     */
    char lastChar();

    /**
     * @return the first character of the sequence or '\0' if empty
     */
    char firstChar();

    /**
     * Get a portion of this sequence
     *
     * @param start offset from start of this sequence
     * @param end   offset from start of this sequence
     * @return based sequence whose contents reflect the selected portion
     */
    @Override
    T subSequence(int start, int end);

    /**
     * Get a portion of this sequence selected by range
     *
     * @param range range to get, coordinates offset form start of this sequence
     * @return based sequence whose contents reflect the selected portion, if range.isNull() then {@link #nullSequence()}
     */
    T subSequence(Range range);

    /**
     * Get a portion of this sequence before one selected by range
     *
     * @param range range to get, coordinates offset form start of this sequence
     * @return based sequence whose contents reflect the selected portion, if range.isNull() then {@link #nullSequence()}
     */
    T subSequenceBefore(Range range);

    /**
     * Get a portion of this sequence after one selected by range
     *
     * @param range range to get, coordinates offset form start of this sequence
     * @return based sequence whose contents reflect the selected portion, if range.isNull() then {@link #nullSequence()}
     */
    T subSequenceAfter(Range range);

    /**
     * Get a portion of this sequence starting from a given offset to end of the sequence
     *
     * @param start offset from start of this sequence
     * @return based sequence whose contents reflect the selected portion
     */
    T subSequence(int start);

    /**
     * Convenience method to get characters offset from end of sequence.
     * no exceptions are thrown, instead a \0 is returned for an invalid index positions
     *
     * @param start offset from end of sequence [ 0..length() )
     * @param end   offset from end of sequence [ 0..length() )
     * @return selected portion spanning length() - start to length() - end of this sequence
     */
    T endSequence(int start, int end);

    /**
     * Convenience method to get characters offset from end of sequence.
     * no exceptions are thrown, instead a \0 is returned for an invalid index positions
     *
     * @param start offset from end of sequence [ 0..length() )
     * @return selected portion spanning length() - start to length() of this sequence
     */
    T endSequence(int start);

    // index from the end of the sequence
    // no exceptions are thrown, instead a \0 is returned for an invalid index
    /**
     * Convenience method to get characters offset from end of sequence.
     * no exceptions are thrown, instead a \0 is returned for an invalid index positions
     *
     * @param index offset from end of sequence
     * @return character located at length() - index in this sequence
     */
    char endCharAt(int index);

    /**
     * Convenience method to get characters offset from start or end of sequence.
     * when offset &gt;= then it is offset from the start of the sequence,
     * when &lt;0 then from the end
     * <p>
     * no exceptions are thrown, instead a \0 is returned for an invalid index positions
     *
     * @param start offset into this sequence
     * @param end   offset into this sequence
     * @return selected portion spanning start to end of this sequence. If offset is &lt;0 then it is taken as relative to length()
     */
    T midSequence(int start, int end);

    /**
     * Convenience method to get characters offset from start or end of sequence.
     * when offset &gt;= then it is offset from the start of the sequence,
     * when &lt;0 then from the end
     * <p>
     * no exceptions are thrown, instead a \0 is returned for an invalid index positions
     *
     * @param start offset into this sequence
     * @return selected portion spanning start to length() of this sequence. If offset is &lt;0 then it is taken as relative to length()
     */
    T midSequence(int start);

    /**
     * Convenience method to get characters offset from start or end of sequence.
     * when index &gt;= then it is offset from the start of the sequence,
     * when &lt;0 then from the end
     * no exceptions are thrown, instead a \0 is returned for an invalid index positions
     *
     * @param index of character to get
     * @return character at index or \0 if index is outside valid range for this sequence
     */
    char midCharAt(int index);

    /**
     * Factory function
     *
     * @param charSequence char sequence from which to construct a rich char sequence
     * @return rich char sequence from given inputs
     */
    T sequenceOf(CharSequence charSequence);

    /**
     * Factory function
     *
     * @param charSequence char sequence from which to construct a rich char sequence
     * @param startIndex   start index of the sequence to use
     * @return rich char sequence from given inputs
     */
    T sequenceOf(CharSequence charSequence, int startIndex);

    /**
     * Factory function
     *
     * @param charSequence char sequence from which to construct a rich char sequence
     * @param startIndex   start index of the sequence to use
     * @param endIndex     end index of the sequence to use
     * @return rich char sequence from given inputs
     */
    T sequenceOf(CharSequence charSequence, int startIndex, int endIndex);

    /**
     * All index methods return the position or -1 if not found of the given character, characters or string.
     * <p>
     * The basic methods have overloads for 1, 2, or 3 characters and CharSequence parameters.
     * If fromIndex is not given then for forward searching methods 0 is taken as the value,
     * for reverse searching methods length() is taken as the value
     * <p>
     * For forward searching methods fromIndex is the minimum start position for search and endIndex
     * is the maximum end position, if not given the length() of string is assumed.
     * <p>
     * For reverse searching methods fromIndex is the maximum start position for search and startIndex
     * is the minimum end position, if not given then 0 is assumed.
     * <p>
     * Variations of arguments are for convenience and speed when 1, 2 or 3 characters are needed to be tested.
     * Methods that take a character set in the form of CharSequence will route their call to character based
     * methods if the CharSequence is only 1, 2 or 3 characters long.
     * <p>
     * indexOf(CharSequence): returns the index of the next occurrence of given text in this sequence
     * <p>
     * indexOfAny(CharSequence): returns the index of the next occurrence of any of the characters in this sequence
     * indexOfAny(char): returns the index of the next occurrence of any of the characters in this sequence
     * indexOfAny(char,char): returns the index of the next occurrence of any of the characters in this sequence
     * indexOfAny(char,char,char): returns the index of the next occurrence of any of the characters in this sequence
     * <p>
     * indexOfAnyNot(CharSequence): returns the index of the next occurrence of any of the characters not in this sequence
     * indexOfAnyNot(char): returns the index of the next occurrence of any of the characters not in this sequence
     * indexOfAnyNot(char,char): returns the index of the next occurrence of any of the characters not in this sequence
     * indexOfAnyNot(char,char,char): returns the index of the next occurrence of any of the characters not in this sequence
     * <p>
     * lastIndexOf(CharSequence): returns the index of the previous occurrence of given text in this sequence, reversed search
     * <p>
     * lastIndexOfAny(CharSequence): returns the index of the previous occurrence of any of the characters in this sequence, reversed search
     * lastIndexOfAny(char): returns the index of the previous occurrence of any of the characters in this sequence, reversed search
     * lastIndexOfAny(char,char): returns the index of the previous occurrence of any of the characters in this sequence, reversed search
     * lastIndexOfAny(char,char,char): returns the index of the previous occurrence of any of the characters in this sequence, reversed search
     * <p>
     * lastIndexOfAnyNot(CharSequence): returns the index of the previous occurrence of any of the characters not in this sequence, reversed search
     * lastIndexOfAnyNot(Char): returns the index of the previous occurrence of any of the characters not in this sequence, reversed search
     * lastIndexOfAnyNot(char,char): returns the index of the previous occurrence of any of the characters not in this sequence, reversed search
     * lastIndexOfAnyNot(char,char,char): returns the index of the previous occurrence of any of the characters not in this sequence, reversed search
     *
     * @param s character sequence whose occurrence to find
     * @return index where found or -1
     */
    int indexOf(CharSequence s);
    int indexOf(CharSequence s, int fromIndex);
    int indexOf(CharSequence s, int fromIndex, int endIndex);

    int indexOf(char c);
    int indexOfAny(char c1, char c2);
    int indexOfAny(char c1, char c2, char c3);
    int indexOfAny(CharSequence s);

    int indexOf(char c, int fromIndex);
    int indexOfAny(char c1, char c2, int fromIndex);
    int indexOfAny(char c1, char c2, char c3, int fromIndex);
    int indexOfAny(CharSequence s, int fromIndex);

    int indexOf(char c, int fromIndex, int endIndex);
    int indexOfAny(char c1, char c2, int fromIndex, int endIndex);
    int indexOfAny(char c1, char c2, char c3, int fromIndex, int endIndex);
    int indexOfAny(CharSequence s, int fromIndex, int endIndex);

    int indexOfNot(char c);
    int indexOfAnyNot(char c1, char c2);
    int indexOfAnyNot(char c1, char c2, char c3);
    int indexOfAnyNot(CharSequence s);

    int indexOfNot(char c, int fromIndex);
    int indexOfAnyNot(char c1, char c2, int fromIndex);
    int indexOfAnyNot(char c1, char c2, char c3, int fromIndex);
    int indexOfAnyNot(CharSequence s, int fromIndex);

    int indexOfNot(char c, int fromIndex, int endIndex);
    int indexOfAnyNot(char c1, char c2, int fromIndex, int endIndex);
    int indexOfAnyNot(char c1, char c2, char c3, int fromIndex, int endIndex);
    int indexOfAnyNot(CharSequence s, int fromIndex, int endIndex);

    int lastIndexOf(CharSequence s);
    int lastIndexOf(CharSequence s, int fromIndex);
    int lastIndexOf(CharSequence s, int startIndex, int fromIndex);

    int lastIndexOf(char c);
    int lastIndexOfAny(char c1, char c2);
    int lastIndexOfAny(char c1, char c2, char c3);
    int lastIndexOfAny(CharSequence s);

    int lastIndexOf(char c, int fromIndex);
    int lastIndexOfAny(char c1, char c2, int fromIndex);
    int lastIndexOfAny(char c1, char c2, char c3, int fromIndex);
    int lastIndexOfAny(CharSequence s, int fromIndex);

    int lastIndexOf(char c, int startIndex, int fromIndex);
    int lastIndexOfAny(char c1, char c2, int startIndex, int fromIndex);
    int lastIndexOfAny(char c1, char c2, char c3, int startIndex, int fromIndex);
    int lastIndexOfAny(CharSequence s, int startIndex, int fromIndex);

    int lastIndexOfNot(char c);
    int lastIndexOfAnyNot(char c1, char c2);
    int lastIndexOfAnyNot(char c1, char c2, char c3);
    int lastIndexOfAnyNot(CharSequence s);

    int lastIndexOfNot(char c, int fromIndex);
    int lastIndexOfAnyNot(char c1, char c2, int fromIndex);
    int lastIndexOfAnyNot(char c1, char c2, char c3, int fromIndex);
    int lastIndexOfAnyNot(CharSequence s, int fromIndex);

    int lastIndexOfNot(char c, int startIndex, int fromIndex);
    int lastIndexOfAnyNot(char c1, char c2, int startIndex, int fromIndex);
    int lastIndexOfAnyNot(char c1, char c2, char c3, int startIndex, int fromIndex);
    int lastIndexOfAnyNot(CharSequence s, int startIndex, int fromIndex);

    /**
     * Count leading/trailing characters of this sequence
     * <p>
     * Parameters can be: 1, 2 or 3 characters or CharSequence. For character arguments counts any contiguous
     * leading/trailing characters in the sequence.
     * <p>
     * For CharSequence counts counts any contiguous
     * leading/trailing characters in the sequence which are contained in the given char sequence.
     * <p>
     * All functions have overloads:
     * with no fromIndex then 0 is taken for leading and length() for trailing methods
     * with fromIndex then this is taken as the start for leading and end for trailing methods
     * with fromIndex and endIndex, counting will start at fromIndex and stop at endIndex
     * <p>
     * countLeading(): count contiguous leading space/tab characters in this sequence
     * countLeading(CharSequence): count contiguous leading characters from set in this sequence
     * countLeading(char): count contiguous leading characters from set in this sequence
     * countLeading(char,char): count contiguous leading characters from set in this sequence
     * countLeading(char,char,char): count contiguous leading characters from set in this sequence
     * <p>
     * countLeadingNot(): count contiguous leading not space/tabs in this sequence
     * countLeadingNot(CharSequence): count contiguous leading characters not from set in this sequence
     * countLeadingNot(char): count contiguous leading characters not from set in this sequence
     * countLeadingNot(char,char): count contiguous leading characters not from set in this sequence
     * countLeadingNot(char,char,char): count contiguous leading characters not from set in this sequence
     * <p>
     * countTrailing(): count contiguous leading space/tab in this sequence
     * countTrailing(CharSequence): count contiguous leading characters from set in this sequence
     * countTrailing(char): count contiguous leading characters from set in this sequence
     * countTrailing(char,char): count contiguous leading characters from set in this sequence
     * countTrailing(char,char,char): count contiguous leading characters from set in this sequence
     * <p>
     * countTrailingNot(): count contiguous leading not space/tabs in this sequence
     * countTrailingNot(CharSequence): count contiguous leading characters not from set in this sequence
     * countTrailingNot(char): count contiguous leading characters not from set in this sequence
     * countTrailingNot(char,char): count contiguous leading characters not from set in this sequence
     * countTrailingNot(char,char,char): count contiguous leading characters not from set in this sequence
     *
     * @param chars set of contiguous characters which should be counted at start of sequence
     * @return number of chars in contiguous span at start of sequence
     */
    int countLeading(CharSequence chars);
    int countLeadingNot(CharSequence chars);
    int countLeading(CharSequence chars, int startIndex);
    int countLeadingNot(CharSequence chars, int startIndex);
    int countLeading(CharSequence chars, int startIndex, int endIndex);
    int countLeadingNot(CharSequence chars, int startIndex, int endIndex);

    int countTrailing(CharSequence chars);
    int countTrailingNot(CharSequence chars);
    int countTrailing(CharSequence chars, int startIndex);
    int countTrailingNot(CharSequence chars, int startIndex);
    int countTrailing(CharSequence chars, int startIndex, int endIndex);
    int countTrailingNot(CharSequence chars, int startIndex, int endIndex);

    int countLeading(char c);
    int countLeadingNot(char c);
    int countLeading(char c, int startIndex);
    int countLeadingNot(char c, int startIndex);
    int countLeading(char c, int startIndex, int endIndex);
    int countLeadingNot(char c, int startIndex, int endIndex);

    int countTrailing(char c);
    int countTrailingNot(char c);
    int countTrailing(char c, int startIndex);
    int countTrailingNot(char c, int startIndex);
    int countTrailing(char c, int startIndex, int endIndex);
    int countTrailingNot(char c, int startIndex, int endIndex);

    int countLeading();
    int countLeadingNot();
    int countTrailing();
    int countTrailingNot();
    int countOf();
    int countOfNot();

    int countOf(char c);
    int countOfNot(char c);
    int countOf(char c, int startIndex);
    int countOfNot(char c, int startIndex);
    int countOf(char c, int startIndex, int endIndex);
    int countOfNot(char c, int startIndex, int endIndex);

    int countOfAny(CharSequence chars);
    int countOfAnyNot(CharSequence chars);
    int countOfAny(CharSequence chars, int startIndex);
    int countOfAnyNot(CharSequence chars, int startIndex);
    int countOfAny(CharSequence chars, int startIndex, int endIndex);
    int countOfAnyNot(CharSequence chars, int startIndex, int endIndex);

    int countLeadingColumns(int startColumn, CharSequence chars);

    /**
     * Range of kept sequence when  trim start/end of this sequence is performed, characters to trim are passed in the sequence argument
     * <p>
     * returns range of kept sequence or if nothing matched then Range.NULL is returned, meaning keep all
     * <p>
     * If character set  in the form of character sequence is not passed in the {@link #WHITESPACE_CHARS} are assumed.
     *
     * @param keep  minimum length of string to keep
     * @param chars set of characters to trim from start of line
     * @return range in this sequence to keep or Range.NULL if to keep all
     */
    Range trimStartRange(int keep, CharSequence chars);
    Range trimEndRange(int keep, CharSequence chars);
    Range trimRange(int keep, CharSequence chars);

    Range trimStartRange(CharSequence chars);
    Range trimEndRange(CharSequence chars);
    Range trimRange(CharSequence chars);

    Range trimStartRange(int keep);
    Range trimEndRange(int keep);
    Range trimRange(int keep);

    Range trimStartRange();
    Range trimEndRange();
    Range trimRange();

    /**
     * Trim, Trim start/end of this sequence, characters to trim are passed in the sequence argument
     * <p>
     * returns trimmed sequence or if nothing matched the original sequence
     * <p>
     * If character set  in the form of character sequence is not passed in the {@link #WHITESPACE_CHARS} are assumed.
     *
     * @param keep  minimum length of string to keep
     * @param chars set of characters to trim from start of line
     * @return sequence after it is trimmed
     */
    T trimStart(int keep, CharSequence chars);
    T trimEnd(int keep, CharSequence chars);
    T trim(int keep, CharSequence chars);

    T trimStart(int keep);
    T trimEnd(int keep);
    T trim(int keep);

    T trimStart(CharSequence chars);
    T trimEnd(CharSequence chars);
    T trim(CharSequence chars);

    T trimStart();
    T trimEnd();
    T trim();

    /**
     * Get the characters Trimmed, Trimmed from start/end of this sequence, characters to trim are passed in the sequence argument
     * <p>
     * returns trimmed sequence or if nothing matched the original sequence
     * <p>
     * The pair returning functions return before and after sequence
     *
     * @param keep  minimum length of trimmed characters to keep. ie. leave this many
     * @param chars set of characters to trim from start of line
     * @return part of the sequence that was trimmed from the start
     */
    T trimmedStart(int keep, CharSequence chars);
    T trimmedEnd(int keep, CharSequence chars);
    Pair<T, T> trimmed(int keep, CharSequence chars);

    T trimmedStart(int keep);
    T trimmedEnd(int keep);
    Pair<T, T> trimmed(int keep);

    T trimmedStart(CharSequence chars);
    T trimmedEnd(CharSequence chars);
    Pair<T, T> trimmed(CharSequence chars);

    T trimmedStart();
    T trimmedEnd();
    Pair<T, T> trimmed();

    T padStart(int length, char pad);
    T padEnd(int length, char pad);
    T padStart(int length);
    T padEnd(int length);

    boolean isEmpty();
    boolean isBlank();
    boolean isNull();
    boolean isNotNull();

    /**
     * If this sequence is the nullSequence() instance then returns other,
     * otherwise returns this sequence.
     *
     * @param other based sequence to return if this is nullSequence()
     * @return this or other
     */
    T ifNull(T other);

    /**
     * If this sequence is the nullSequence() instance then returns an empty subSequence from the end of other,
     * otherwise returns this sequence.
     *
     * @param other based sequence from which to take the empty sequence
     * @return this or other.subSequence(other.length(), other.length())
     */
    T ifNullEmptyAfter(T other);

    /**
     * If this sequence is the nullSequence() instance then returns an empty subSequence from the start of other,
     * otherwise returns this sequence.
     *
     * @param other based sequence from which to take the empty sequence
     * @return this or other.subSequence(0, 0)
     */
    T ifNullEmptyBefore(T other);

    /**
     * If this sequence is empty return nullSequence() otherwise returns this sequence.
     *
     * @return this or nullSequence()
     */
    T nullIfEmpty();

    /**
     * If this sequence is blank return nullSequence() otherwise returns this sequence.
     *
     * @return this or nullSequence()
     */
    T nullIfBlank();

    /**
     * If condition is true return nullSequence() otherwise returns this sequence.
     *
     * @param condition when true return NULL else this
     * @return this or nullSequence()
     */
    T nullIf(boolean condition);

    /**
     * If predicate returns true for this sequence and one of given sequences return nullSequence() otherwise returns this sequence.
     *
     * @param predicate bi predicate for test, first argument is always this, second is one of the match sequences
     * @param matches   match sequence list
     * @return this or nullSequence()
     */
    T nullIf(BiPredicate<? super T, ? super CharSequence> predicate, CharSequence... matches);
    T nullIfNot(BiPredicate<? super T, ? super CharSequence> predicate, CharSequence... matches);

    /**
     * If predicate returns true for one of given sequences return nullSequence() otherwise returns this sequence.
     *
     * @param matches match sequence list
     * @return this or nullSequence()
     */
    T nullIf(Predicate<? super CharSequence> predicate, CharSequence... matches);
    T nullIfNot(Predicate<? super CharSequence> predicate, CharSequence... matches);

    /**
     * If this sequence matches one of given sequences return nullSequence() otherwise returns this sequence.
     *
     * @param matches match sequence list
     * @return this or nullSequence()
     */
    T nullIf(CharSequence... matches);
    T nullIfNot(CharSequence... matches);

    T nullIfStartsWith(CharSequence... matches);
    T nullIfNotStartsWith(CharSequence... matches);

    @Deprecated
    default T nullIfStartsWithNot(CharSequence... matches) {
        return nullIfNotStartsWith(matches);
    }

    T nullIfEndsWith(CharSequence... matches);
    T nullIfNotEndsWith(CharSequence... matches);
    T nullIfStartsWithIgnoreCase(CharSequence... matches);
    T nullIfNotStartsWithIgnoreCase(CharSequence... matches);
    T nullIfEndsWithIgnoreCase(CharSequence... matches);
    T nullIfNotEndsWithIgnoreCase(CharSequence... matches);
    T nullIfStartsWith(boolean ignoreCase, CharSequence... matches);
    T nullIfNotStartsWith(boolean ignoreCase, CharSequence... matches);
    T nullIfEndsWith(boolean ignoreCase, CharSequence... matches);
    T nullIfNotEndsWith(boolean ignoreCase, CharSequence... matches);

    @Deprecated
    default T nullIfEndsWithNot(CharSequence... matches) {
        return nullIfNotEndsWith(matches);
    }

    /*
       EOL helper methods
     */

    /**
     * Get the length of EOL character at the end of this sequence, if present.
     * <p>
     * \n - 1
     * \r - 1
     * \r\n - 2
     *
     * @return 0 if no EOL, 1, or 2 depending on the EOL suffix of this sequence
     */
    int eolEndLength();

    @Deprecated
    default int eolStartLength() {
        return eolEndLength();
    }

    /**
     * Get the length of EOL character at the given index of this sequence, if present.
     * <p>
     * \n - 1
     * \r - 1
     * \r\n - 2
     *
     * @param eolEnd index where the EOL ends, if any, any value can be passed for this
     *               argument. If > length of this sequence then it is the same as passing length(),
     *               if 0 or less then 0 is returned.
     * @return 0 if no EOL, 1, or 2 depending on the EOL suffix of this sequence
     */
    int eolEndLength(int eolEnd);

    /**
     * Get the length of EOL character at the given index of this sequence, if present.
     * <p>
     * \n - 1
     * \r - 1
     * \r\n - 2
     *
     * @param eolStart index where the EOL starts, if any, any value can be passed for this
     *                 argument. If >= length of this sequence then 0 is returned
     *                 if 0 or less then it is the same as 0
     * @return 0 if no EOL, 1, or 2 depending on the EOL suffix of this sequence
     */
    int eolStartLength(int eolStart);

    @Deprecated
    default int eolLength(int eolStart) {
        return eolStartLength(eolStart);
    }

    /**
     * Return Range of eol at given index
     *
     * @param eolEnd index where the EOL ends, if any, any value can be passed for this
     *               argument. If > length of this sequence then it is the same as passing length(),
     *               if 0 or less then 0 is returned.
     * @return range of eol given by index of its end or Range.NULL if no eol ends at index
     */
    Range eolEndRange(int eolEnd);

    /**
     * Return Range of eol at given index
     *
     * @param eolStart index where the EOL starts, if any, any value can be passed for this
     *                 argument. If >= length of this sequence then 0 is returned
     *                 if 0 or less then it is the same as 0
     * @return range of eol given by index of its end or Range.NULL if no eol starts at index
     */
    Range eolStartRange(int eolStart);

    /**
     * Trim eol at the end of this sequence
     *
     * @return sequence with one EOL trimmed off if it had one
     */
    T trimEOL();

    /**
     * Get Trimmed eol from the end of this sequence
     *
     * @return trimmed off EOL if sequence had one or {@link #nullSequence()}
     */
    T trimmedEOL();

    /**
     * Find start/end region in this sequence delimited by any characters in argument or the CharSequence
     * <p>
     * For Any and AnyNot methods uses the CharSequence argument as a character set of possible delimiting characters
     *
     * @param s     character sequence delimiting the region
     * @param index from which to start looking for end of region
     * @return index of end of region delimited by s
     */
    int endOfDelimitedBy(CharSequence s, int index);
    int endOfDelimitedByAny(CharSequence s, int index);
    int endOfDelimitedByAnyNot(CharSequence s, int index);

    int startOfDelimitedBy(CharSequence s, int index);
    int startOfDelimitedByAny(CharSequence s, int index);
    int startOfDelimitedByAnyNot(CharSequence s, int index);

    /**
     * Get the offset of the end of line at given index, end of line delimited by \n
     *
     * @param index index where to start searching for end of line
     * @return index of end of line delimited by \n
     */
    int endOfLine(int index);

    /**
     * Get the offset of the end of line at given index, end of line delimited by \n or any of \n \r \r\n for Any methods.
     *
     * @param index index where to start searching for end of line
     * @return index of end of line delimited by \n
     */
    int endOfLineAnyEOL(int index);

    /**
     * Get the offset of the start of line at given index, start of line delimited by \n
     *
     * @param index index where to start searching for end of line
     * @return index of end of line delimited by \n
     */
    int startOfLine(int index);

    /**
     * Get the offset of the start of line at given index, start of line delimited by \n or any of \n \r \r\n for Any methods.
     *
     * @param index index where to start searching for end of line
     * @return index of end of line delimited by \n
     */
    int startOfLineAnyEOL(int index);

    /**
     * Get the line characters at given index, line delimited by \n
     *
     * @param index index at which to get the line
     * @return range in sequence for the line delimited by '\n', containing index
     */
    Range lineRangeAt(int index);
    /**
     * Get the line characters at given index, line delimited by \n, \r or \r\n
     *
     * @param index index at which to get the line
     * @return range in sequence for the line delimited by end of line, containing index
     */
    Range lineRangeAtAnyEOL(int index);

    /**
     * Get the line characters at given index, line delimited by \n
     *
     * @param index index at which to get the line
     * @return sub-sequence for the line containing index
     */
    T lineAt(int index);

    /**
     * Get the line characters at given index, line delimited by \n, \r or \r\n
     *
     * @param index index at which to get the line
     * @return sub-sequence for the line containing index
     */
    T lineAtAnyEOL(int index);

    /**
     * Trim leading trailing blank lines in this sequence
     *
     * @return return sequence with trailing blank lines trimmed off
     */
    T trimTailBlankLines();
    T trimLeadBlankLines();

    /**
     * Get next Range of blank lines at given index offsets in sequence
     *
     *
     * @param eolChars  characters to consider as EOL, note {@link #eolStartLength(int)} should report length of EOL found if it is > 1
     * @param startIndex minimum index in sequence to check and include in range of blank lines
     *                   can be any value, if less than 0 it is the same as 0,
     *                   if greater than length() it is the same as length()
     * @param fromIndex  index in sequence from which to start blank line search, also maximum index to include in blank lines range
     *                   can be any value, if less than 0 it is the same as 0,
     *                   if greater than length() it is the same as length()
     * @return range of blank lines at or before fromIndex and ranging to minimum of startIndex, Range.NULL if none found
     *         if the range in sequence contains only whitespace characters then the whole range will be returned
     *         even if contains no EOL characters
     */
    Range blankLinesRange(CharSequence eolChars, int startIndex, int fromIndex);
    /**
     * Get previous Range of blank lines at given index offsets in sequence
     *
     * @param eolChars  characters to consider as EOL, note {@link #eolStartLength(int)} should report length of EOL found if it is > 1
     * @param fromIndex index in sequence from which to start blank line search, also maximum index to include in blank lines range
     *                  can be any value, if less than 0 it is the same as 0,
     *                  if greater than length() it is the same as length()
     * @param endIndex  maximum index in sequence to check and include in range of blank lines
     *                  can be any value, if less than 0 it is the same as 0,
     *                  if greater than length() it is the same as length()
     * @return range of blank lines at or before fromIndex and ranging to minimum of startIndex
     *         if the range in sequence contains only whitespace characters then the whole range will be returned
     *         even if contains no EOL characters
     */
    Range lastBlankLinesRange(CharSequence eolChars, int fromIndex, int endIndex);

    Range blankLinesRange();
    Range blankLinesRange(int startIndex);
    Range blankLinesRange(int fromIndex, int endIndex);
    Range lastBlankLinesRange();
    Range lastBlankLinesRange(int fromIndex);
    Range lastBlankLinesRange(int startIndex, int fromIndex);

    /**
     * replace any \r\n and \r by \n
     *
     * @return string with only \n for line separators
     */
    String normalizeEOL();

    /**
     * replace any \r\n and \r by \n, append terminating EOL if one is not present
     *
     * @return string with only \n for line separators and terminated by \n
     */
    String normalizeEndWithEOL();

    /*
     * comparison helpers
     */

    /**
     * Test the sequence for a match to another CharSequence
     *
     * @param chars characters to match against
     * @return true if match
     */
    boolean matches(CharSequence chars);
    boolean matchesIgnoreCase(CharSequence chars);
    boolean matches(CharSequence chars, boolean ignoreCase);

    /**
     * Test the sequence for a match to another CharSequence, ignoring case differences
     *
     * @param other characters to match against
     * @return true if match
     */
    boolean equalsIgnoreCase(Object other);

    /**
     * Test the sequence for a match to another CharSequence
     *
     * @param other      characters to match against
     * @param ignoreCase case ignored when true
     * @return true if match
     */
    boolean equals(Object other, boolean ignoreCase);

    /**
     * Test the sequence portion for a match to another CharSequence
     *
     * @param chars characters to match against
     * @return true if characters at the start of this sequence match
     */
    boolean matchChars(CharSequence chars);
    boolean matchCharsIgnoreCase(CharSequence chars);
    boolean matchChars(CharSequence chars, boolean ignoreCase);

    /**
     * Test the sequence portion for a match to another CharSequence
     *
     * @param chars      characters to match against
     * @param startIndex index from which to start the match
     * @return true if characters at the start index of this sequence match
     */
    boolean matchChars(CharSequence chars, int startIndex);
    boolean matchCharsIgnoreCase(CharSequence chars, int startIndex);
    boolean matchChars(CharSequence chars, int startIndex, boolean ignoreCase);

    /**
     * Test the sequence portion for a match to another CharSequence, reverse order
     *
     * @param chars    characters to match against
     * @param endIndex index from which to start the match and proceed to 0
     * @return true if characters at the start index of this sequence match
     */
    boolean matchCharsReversed(CharSequence chars, int endIndex);
    boolean matchCharsReversedIgnoreCase(CharSequence chars, int endIndex);
    boolean matchCharsReversed(CharSequence chars, int endIndex, boolean ignoreCase);

    /**
     * test if this sequence ends with given characters
     *
     * @param suffix characters to test
     * @return true if ends with suffix
     */
    boolean endsWith(CharSequence suffix);

    /**
     * test if this sequence ends with given characters, ignoring case differences
     *
     * @param suffix characters to test
     * @return true if ends with suffix
     */
    boolean endsWithIgnoreCase(CharSequence suffix);

    /**
     * test if this sequence ends with given characters
     *
     * @param suffix     characters to test
     * @param ignoreCase case ignored when true
     * @return true if ends with suffix
     */
    boolean endsWith(CharSequence suffix, boolean ignoreCase);

    /**
     * test if this sequence starts with given characters
     *
     * @param prefix characters to test
     * @return true if starts with prefix
     */
    boolean startsWith(CharSequence prefix);

    /**
     * test if this sequence starts with given characters, ignoring case differences
     *
     * @param prefix characters to test
     * @return true if starts with prefix
     */
    boolean startsWithIgnoreCase(CharSequence prefix);

    /**
     * test if this sequence starts with given characters
     *
     * @param prefix     characters to test
     * @param ignoreCase case ignored when true
     * @return true if starts with prefix
     */
    boolean startsWith(CharSequence prefix, boolean ignoreCase);

    /**
     * Remove suffix if present
     *
     * @param suffix characters to remove
     * @return sequence with suffix removed, or same sequence if no suffix was present
     */
    T removeSuffix(CharSequence suffix);

    /**
     * Remove suffix if present, ignoring case differences
     *
     * @param suffix characters to remove
     * @return sequence with suffix removed, or same sequence if no suffix was present
     */
    T removeSuffixIgnoreCase(CharSequence suffix);

    /**
     * Remove suffix if present
     *
     * @param suffix     characters to remove
     * @param ignoreCase case ignored when true
     * @return sequence with suffix removed, or same sequence if no suffix was present
     */
    T removeSuffix(CharSequence suffix, boolean ignoreCase);

    /**
     * Remove prefix if present
     *
     * @param prefix characters to remove
     * @return sequence with prefix removed, or same sequence if no prefix was present
     */
    T removePrefix(CharSequence prefix);

    /**
     * Remove prefix if present, ignoring case differences
     *
     * @param prefix characters to remove
     * @return sequence with prefix removed, or same sequence if no prefix was present
     */
    T removePrefixIgnoreCase(CharSequence prefix);

    /**
     * Remove prefix if present
     *
     * @param prefix     characters to remove
     * @param ignoreCase case ignored when true
     * @return sequence with prefix removed, or same sequence if no prefix was present
     */
    T removePrefix(CharSequence prefix, boolean ignoreCase);

    /**
     * Remove suffix if present but only if this sequence is longer than the suffix
     *
     * @param suffix characters to remove
     * @return sequence with suffix removed, or same sequence if no suffix was present
     */
    T removeProperSuffix(CharSequence suffix);

    /**
     * Remove suffix if present but only if this sequence is longer than the suffix, ignoring case differences
     *
     * @param suffix characters to remove
     * @return sequence with suffix removed, or same sequence if no suffix was present
     */
    T removeProperSuffixIgnoreCase(CharSequence suffix);

    /**
     * Remove suffix if present but only if this sequence is longer than the suffix
     *
     * @param suffix     characters to remove
     * @param ignoreCase case ignored when true
     * @return sequence with suffix removed, or same sequence if no suffix was present
     */
    T removeProperSuffix(CharSequence suffix, boolean ignoreCase);

    /**
     * Remove prefix if present but only if this sequence is longer than the suffix
     *
     * @param prefix characters to remove
     * @return sequence with prefix removed, or same sequence if no prefix was present
     */
    T removeProperPrefix(CharSequence prefix);

    /**
     * Remove prefix if present but only if this sequence is longer than the suffix, ignoring case differences
     *
     * @param prefix characters to remove
     * @return sequence with prefix removed, or same sequence if no prefix was present
     */
    T removeProperPrefixIgnoreCase(CharSequence prefix);

    /**
     * Remove prefix if present but only if this sequence is longer than the suffix
     *
     * @param prefix     characters to remove
     * @param ignoreCase case ignored when true
     * @return sequence with prefix removed, or same sequence if no prefix was present
     */
    T removeProperPrefix(CharSequence prefix, boolean ignoreCase);

    /**
     * Map characters of this sequence to: Uppercase, Lowercase or use custom mapping
     *
     * @return lowercase version of sequence
     */
    T toLowerCase();
    T toUpperCase();
    T toLowerCase(Locale locale);
    T toUpperCase(Locale locale);
    T toMapped(CharMapper mapper);

    String toVisibleWhitespaceString();

    int SPLIT_INCLUDE_DELIMS = 1;
    int SPLIT_TRIM_PARTS = 2;
    int SPLIT_SKIP_EMPTY = 4;
    int SPLIT_INCLUDE_DELIM_PARTS = 8;
    int SPLIT_TRIM_SKIP_EMPTY = SPLIT_TRIM_PARTS | SPLIT_SKIP_EMPTY;

    /**
     * Split helpers based on delimiter character sets contained in CharSequence
     *
     * @param delimiter delimiter char or set of chars in CharSequence to split this sequence on
     * @param limit     max number of segments to split
     * @param flags     flags for desired options:
     *                  SPLIT_INCLUDE_DELIMS: include delimiters as part of split item
     *                  SPLIT_TRIM_PARTS: trim the segments
     *                  SPLIT_SKIP_EMPTY: skip empty segments (or empty after trimming if enabled)
     *                  SPLIT_INCLUDE_DELIM_PARTS: include delimiters as separate split item of its own
     *                  SPLIT_TRIM_SKIP_EMPTY: same as SPLIT_TRIM_PARTS | SPLIT_SKIP_EMPTY
     * @param trimChars set of characters that should be used for trimming individual split results
     * @return List of split results
     */
    T[] split(char delimiter, int limit, int flags, String trimChars);
    T[] split(char delimiter);
    T[] split(char delimiter, int limit);
    T[] split(char delimiter, int limit, int flags);
    T[] split(CharSequence delimiter);
    T[] split(CharSequence delimiter, int limit);
    T[] split(CharSequence delimiter, int limit, int flags);
    T[] split(CharSequence delimiter, int limit, int flags, String trimChars);

    /**
     * Get indices of all occurrences of a sequence
     *
     * @param s sequence whose indices to find
     * @return array of indices
     */
    int[] indexOfAll(CharSequence s);

    /**
     * Replace all occurrences of one sequence with another
     *
     * @param find    sequence to find
     * @param replace replacement sequence
     * @return array of indices
     */
    T replace(CharSequence find, CharSequence replace);

    T appendTo(StringBuilder out);
    T appendTo(StringBuilder out, int start);
    T appendTo(StringBuilder out, int start, int end);
    T append(CharSequence... others);

    /**
     * Get the line and column information from index into sequence
     *
     * @param index index for which to get line information
     * @return Pair(line, column) where line and column are 0 based,
     *         throws IllegalArgumentException if index &lt; 0 or &gt; length.
     */
    Pair<Integer, Integer> getLineColumnAtIndex(int index);

    int getColumnAtIndex(int index);
}
