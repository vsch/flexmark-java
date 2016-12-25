package com.vladsch.flexmark.util.sequence;

import com.vladsch.flexmark.util.mappers.CharMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A CharSequence that references original char sequence and maps '\0' to '\uFFFD'
 * a subSequence() returns a sub-sequence from the original base sequence
 */
@SuppressWarnings("SameParameterValue")
public interface BasedSequence extends CharSequence {
    BasedSequence NULL = new EmptyBasedSequence();
    BasedSequence EOL = CharSubSequence.of("\n");
    List<BasedSequence> EMPTY_LIST = new ArrayList<>();
    BasedSequence[] EMPTY_ARRAY = new BasedSequence[0];
    String WHITESPACE_NO_EOL_CHARS = " \t";
    String WHITESPACE_CHARS = " \t\r\n";
    String WHITESPACE_NBSP_CHARS = " \t\r\n\u00A0";

    String EOL_CHARS = "\r\n";
    char EOL_CHAR = EOL_CHARS.charAt(1);
    char EOL_CHAR1 = EOL_CHARS.charAt(0);
    char EOL_CHAR2 = EOL_CHARS.charAt(1);

    /**
     * Get the underlying object on which this sequence contents are based
     *
     * @return underlying object containing original text
     */
    Object getBase();

    /**
     * Get the base sequence for the text
     *
     * @return base sequence
     */
    BasedSequence getBaseSequence();

    /**
     * Get the start offset of this sequence into {@link #getBaseSequence()} and {@link #getBase()} original text source.
     *
     * @return start offset in original text
     */
    int getStartOffset();

    /**
     * Get the end offset of this sequence into {@link #getBaseSequence()} and {@link #getBase()} original text source.
     *
     * @return end offset in original text
     */
    int getEndOffset();

    /**
     * Get the offset of index in this sequence mapped to offset into {@link #getBaseSequence()} and {@link #getBase()} original text source.
     *
     * @param index index for which to get the offset in original source
     * @return offset of index of this sequence in original text
     */
    int getIndexOffset(int index);

    /**
     * Get the range of this sequence in original {@link #getBaseSequence()} and {@link #getBase()} original text source.
     *
     * @return Range of start offset and end offset
     */
    Range getSourceRange();

    /**
     * Get a portion of this sequence
     *
     * @param start offset from start of this sequence
     * @param end   offset from start of this sequence
     * @return based sequence whose contents reflect the selected portion
     */
    @Override
    BasedSequence subSequence(int start, int end);

    /**
     * Get a portion of this sequence
     *
     * @param range range to get, coordinates offset form start of this sequence
     * @return based sequence whose contents reflect the selected portion
     */
    BasedSequence subSequence(Range range);

    /**
     * Get a portion of this sequence starting from a given offset to end of the sequence
     *
     * @param start offset from start of this sequence
     * @return based sequence whose contents reflect the selected portion
     */
    BasedSequence subSequence(int start);

    /**
     * Get a portion of the original sequence that this sequence is based on
     *
     * @param start offset from 0 of original sequence
     * @param end   offset from 0 of original sequence
     * @return based sequence whose contents reflect the selected portion
     */
    BasedSequence baseSubSequence(int start, int end);

    /**
     * Convenience method to get characters offset from end of sequence.
     * no exceptions are thrown, instead a \0 is returned for an invalid index positions
     *
     * @param start offset from end of sequence [ 0..length() )
     * @param end   offset from end of sequence [ 0..length() )
     * @return selected portion spanning length() - start to length() - end of this sequence
     */
    BasedSequence endSequence(int start, int end);

    /**
     * Convenience method to get characters offset from end of sequence.
     * no exceptions are thrown, instead a \0 is returned for an invalid index positions
     *
     * @param start offset from end of sequence [ 0..length() )
     * @return selected portion spanning length() - start to length() of this sequence
     */
    BasedSequence endSequence(int start);

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
    BasedSequence midSequence(int start, int end);

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
    BasedSequence midSequence(int start);

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
     * countLeading(CharSequence): count contiguous leading characters from set in this sequence
     * countLeading(char): count contiguous leading characters from set in this sequence
     * countLeading(char,char): count contiguous leading characters from set in this sequence
     * countLeading(char,char,char): count contiguous leading characters from set in this sequence
     * <p>
     * countLeadingNot(CharSequence): count contiguous leading characters not from set in this sequence
     * countLeadingNot(char): count contiguous leading characters not from set in this sequence
     * countLeadingNot(char,char): count contiguous leading characters not from set in this sequence
     * countLeadingNot(char,char,char): count contiguous leading characters not from set in this sequence
     * <p>
     * countTrailing(CharSequence): count contiguous leading characters from set in this sequence
     * countTrailing(char): count contiguous leading characters from set in this sequence
     * countTrailing(char,char): count contiguous leading characters from set in this sequence
     * countTrailing(char,char,char): count contiguous leading characters from set in this sequence
     * <p>
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

    int countChars(char c);
    int countCharsReversed(char c);
    int countNotChars(char c);
    int countNotCharsReversed(char c);

    int countChars(char c, int startIndex);
    int countCharsReversed(char c, int startIndex);
    int countNotChars(char c, int startIndex);
    int countNotCharsReversed(char c, int startIndex);

    int countChars(char c, int startIndex, int endIndex);
    int countNotChars(char c, int startIndex, int endIndex);
    int countCharsReversed(char c, int startIndex, int endIndex);
    int countNotCharsReversed(char c, int startIndex, int endIndex);

    int countChars(CharSequence chars);
    int countCharsReversed(CharSequence chars);
    int countNotChars(CharSequence chars);
    int countNotCharsReversed(CharSequence chars);

    int countChars(CharSequence chars, int startIndex);
    int countCharsReversed(CharSequence chars, int startIndex);
    int countNotChars(CharSequence chars, int startIndex);
    int countNotCharsReversed(CharSequence chars, int startIndex);

    int countChars(CharSequence chars, int startIndex, int endIndex);
    int countNotChars(CharSequence chars, int startIndex, int endIndex);
    int countCharsReversed(CharSequence chars, int startIndex, int endIndex);
    int countNotCharsReversed(CharSequence chars, int startIndex, int endIndex);

    /**
     * Trim, Trim start/end of this sequence, characters to trim are passed in the sequence argument
     * <p>
     * returns trimmed sequence or if nothing matched the original sequence
     * <p>
     * If character set  in the form of character sequence is not passed in the {@link #WHITESPACE_CHARS} are assumed.
     *
     * @param chars set of characters to trim from start of line
     * @return sequence after it is trimmed
     */
    BasedSequence trimStart(CharSequence chars);
    BasedSequence trimEnd(CharSequence chars);
    BasedSequence trim(CharSequence chars);
    BasedSequence trimStart();
    BasedSequence trimEnd();
    BasedSequence trim();
    BasedSequence trimEOL();

    /**
     * Get the characters Trimmed, Trimmed from start/end of this sequence, characters to trim are passed in the sequence argument
     * <p>
     * returns trimmed sequence or if nothing matched the original sequence
     *
     * @param chars set of characters to trim from start of line
     * @return part of the sequence that was trimmed from the start
     */
    BasedSequence trimmedStart(CharSequence chars);
    BasedSequence trimmedEnd(CharSequence chars);
    BasedSequence trimmedStart();
    BasedSequence trimmedEnd();
    BasedSequence trimmedEOL();

    /**
     * Get the length of EOL character at the end of this sequence, if present.
     * <p>
     * \n - 1
     * \r - 1
     * \r\n - 2
     *
     * @return 0, 1, or 2 depending on the EOL suffix of this sequence
     */
    int eolLength();

    boolean isEmpty();
    boolean isBlank();
    boolean isNull();
    boolean isNotNull();

    /**
     * If this sequence is the BasedSequence.NULL instance then returns an empty subSequence from the end of other,
     * otherwise returns this sequence.
     *
     * @param other based sequence from which to take the empty sequence
     * @return this or other.subSequence(other.length(), other.length())
     */
    BasedSequence ifNullEmptyAfter(BasedSequence other);

    /**
     * If this sequence is the BasedSequence.NULL instance then returns an empty subSequence from the start of other,
     * otherwise returns this sequence.
     *
     * @param other based sequence from which to take the empty sequence
     * @return this or other.subSequence(0, 0)
     */
    BasedSequence ifNullEmptyBefore(BasedSequence other);

    /**
     * If this sequence is empty return BasedSequence.NULL otherwise returns this sequence.
     *
     * @return this or SubSequence.NULL
     */
    BasedSequence nullIfEmpty();

    /**
     * If this sequence is blank return BasedSequence.NULL otherwise returns this sequence.
     *
     * @return this or SubSequence.NULL
     */
    BasedSequence nullIfBlank();

    /**
     * If condition is true return BasedSequence.NULL otherwise returns this sequence.
     *
     * @param condition when true return NULL else this
     * @return this or SubSequence.NULL
     */
    BasedSequence nullIf(boolean condition);

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
     * Get the offset of the end of line at given index, end of line delimited by \n or any of \n \r \r\n for Any methods.
     *
     * @param index index where to start searching for end of line
     * @return index of end of line delimited by \n
     */
    int endOfLine(int index);
    int endOfLineAnyEOL(int index);
    int startOfLine(int index);
    int startOfLineAnyEOL(int index);

    /**
     * Get the unescaped string of this sequence content
     *
     * @return unescaped text
     */
    String unescape();
    /**
     * Get the unescaped string of this sequence content
     *
     * @param textMapper replaced text mapper which will be uses to map unescaped index to original source index
     * @return unescaped text in based sequence
     */
    BasedSequence unescape(ReplacedTextMapper textMapper);

    /**
     * replace any \r\n and \r by \n
     *
     * @return string with only \n for line separators
     */
    String normalizeEOL();

    /**
     * replace any \r\n and \r by \n
     *
     * @param textMapper replaced text mapper which will be uses to map unescaped index to original source index
     * @return based sequence with only \n for line separators
     */
    BasedSequence normalizeEOL(ReplacedTextMapper textMapper);

    /**
     * replace any \r\n and \r by \n, append terminating EOL if one is not present
     *
     * @return string with only \n for line separators and terminated by \n
     */
    String normalizeEndWithEOL();
    /**
     * replace any \r\n and \r by \n, append terminating EOL if one is not present
     *
     * @param textMapper replaced text mapper which will be uses to map unescaped index to original source index
     * @return based sequence with only \n for line separators and terminated by \n
     */
    BasedSequence normalizeEndWithEOL(ReplacedTextMapper textMapper);

    /**
     * Test the sequence for a match to another CharSequence
     *
     * @param chars characters to match against
     * @return true if match
     */
    boolean matches(CharSequence chars);

    /**
     * Test the sequence portion for a match to another CharSequence
     *
     * @param chars characters to match against
     * @return true if characters at the start of this sequence match
     */
    boolean matchChars(CharSequence chars);

    /**
     * Test the sequence portion for a match to another CharSequence
     *
     * @param chars      characters to match against
     * @param startIndex index from which to start the match
     * @return true if characters at the start index of this sequence match
     */
    boolean matchChars(CharSequence chars, int startIndex);

    /**
     * Test the sequence portion for a match to another CharSequence, reverse order
     *
     * @param chars    characters to match against
     * @param endIndex index from which to start the match and proceed to 0
     * @return true if characters at the start index of this sequence match
     */
    boolean matchCharsReversed(CharSequence chars, int endIndex);

    /**
     * test if this sequence ends with given characters
     *
     * @param suffix characters to test
     * @return true if ends with suffix
     */
    boolean endsWith(CharSequence suffix);

    /**
     * test if this sequence starts with given characters
     *
     * @param prefix characters to test
     * @return true if starts with prefix
     */
    boolean startsWith(CharSequence prefix);

    /**
     * Remove suffix if present
     *
     * @param suffix characters to remove
     * @return sequence with suffix removed, or same sequence if no suffix was present
     */
    BasedSequence removeSuffix(CharSequence suffix);

    /**
     * Remove prefix if present
     *
     * @param prefix characters to remove
     * @return sequence with prefix removed, or same sequence if no prefix was present
     */
    BasedSequence removePrefix(CharSequence prefix);

    /**
     * Remove suffix if present but only if this sequence is longer than the suffix
     *
     * @param suffix characters to remove
     * @return sequence with suffix removed, or same sequence if no suffix was present
     */
    BasedSequence removeProperSuffix(CharSequence suffix);

    /**
     * Remove prefix if present but only if this sequence is longer than the suffix
     *
     * @param prefix characters to remove
     * @return sequence with prefix removed, or same sequence if no prefix was present
     */
    BasedSequence removeProperPrefix(CharSequence prefix);

    /**
     * Map characters of this sequence to: Uppercase, Lowercase or use custom mapping
     *
     * @return  lowercase version of sequence
     */
    MappedSequence toLowerCase();
    MappedSequence toUpperCase();
    MappedSequence toLowerCase(Locale locale);
    MappedSequence toUpperCase(Locale locale);
    MappedSequence toMapped(CharMapper mapper);

    /**
     * Trim leading trailing blank lines in this sequence
     * @return  return sequence with trailing blank lines trimmed off
     */
    BasedSequence trimTailBlankLines();
    BasedSequence trimLeadBlankLines();
    String toVisibleWhitespaceString();

    /**
     * Test if the given sequence is a continuation of this sequence in original source text
     *
     * @param other sequence to test
     * @return true if the given sequence is a continuation of this one in the original text
     */
    boolean isContinuedBy(BasedSequence other);

    /**
     * Test if this sequence is a continuation of the given sequence in original source text
     *
     * @param other sequence to test
     * @return true if this sequence is a continuation of the given sequence in original source text
     */
    boolean isContinuationOf(BasedSequence other);

    /**
     * Splice the given sequence to the end of this one and return a BasedSequence of the result.
     * Does not copy anything, creates a new based sequence of the original text but one that spans
     * characters of this sequence and other
     *
     * @param other sequence to append to end of this one
     * @return based sequence that contains the span from start of this sequence and end of other
     * <p>
     * assertion will fail if the other sequence is not a continuation of this one
     */
    BasedSequence spliceAtEnd(BasedSequence other);

    /**
     * start/end offset based containment, not textual
     *
     * @param other based sequence from the same parent
     * @return true if other is contained in this
     */
    boolean contains(BasedSequence other);

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
    List<BasedSequence> split(char delimiter, int limit, int flags, String trimChars);
    List<BasedSequence> split(char delimiter);
    List<BasedSequence> split(char delimiter, int limit);
    List<BasedSequence> split(char delimiter, int limit, int flags);
    List<BasedSequence> split(CharSequence delimiter);
    List<BasedSequence> split(CharSequence delimiter, int limit);
    List<BasedSequence> split(CharSequence delimiter, int limit, int flags);
    List<BasedSequence> split(CharSequence delimiter, int limit, int flags, String trimChars);

    /**
     * @return the last character of the sequence or '\0' if empty
     */
    char lastChar();

    /**
     * @return the first character of the sequence or '\0' if empty
     */
    char firstChar();

    class EmptyBasedSequence extends BasedSequenceImpl {
        @Override
        public int length() {
            return 0;
        }

        @Override
        public char charAt(int index) {
            throw new StringIndexOutOfBoundsException("String index: " + index + " out of range: 0, " + length());
        }

        @Override
        public int getIndexOffset(int index) {
            if (index == 0) return 0;
            throw new StringIndexOutOfBoundsException("String index: " + index + " out of range: 0, " + length());
        }

        @Override
        public BasedSequence subSequence(int i, int i1) {
            if (i == 0 && i1 == 0) return this;
            throw new StringIndexOutOfBoundsException("EMPTY subSequence(" + i + "," + i1 + ") only subSequence(0, 0) is allowed");
        }

        @Override
        public BasedSequence baseSubSequence(int start, int end) {
            return subSequence(start, end);
        }

        @Override
        public BasedSequence getBaseSequence() {
            return BasedSequence.NULL;
        }

        @Override
        public BasedSequence getBase() {
            return BasedSequence.NULL;
        }

        @Override
        public int getStartOffset() {
            return 0;
        }

        @Override
        public int getEndOffset() {
            return 0;
        }

        @Override
        public Range getSourceRange() {
            return Range.NULL;
        }

        @Override
        public String toString() {
            return "";
        }
    }
}
