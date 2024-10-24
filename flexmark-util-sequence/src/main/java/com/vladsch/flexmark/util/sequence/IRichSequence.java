package com.vladsch.flexmark.util.sequence;

import com.vladsch.flexmark.util.misc.CharPredicate;
import com.vladsch.flexmark.util.misc.Pair;
import com.vladsch.flexmark.util.sequence.builder.ISequenceBuilder;
import com.vladsch.flexmark.util.sequence.mappers.CharMapper;
import java.util.Collection;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/**
 * A CharSequence that provides a rich set of manipulation methods.
 *
 * <p>safe access methods return '\0' for no char response.
 */
interface IRichSequence<T extends IRichSequence<T>>
    extends CharSequence, Comparable<CharSequence>, SequenceUtils {
  /**
   * Comparison to another CharSequence should result in a match if their contents are equal
   * regardless of the implementation. Should not resort to content comparison unless
   *
   * @param other another char sequence
   * @return true if character sequences are equal
   */
  @Override
  boolean equals(Object other);

  /**
   * Should return hashCode of the underlying character sequence which is equal to the String value
   * of that sequence
   *
   * @return hash code as if it was a string of the sequence content
   */
  @Override
  int hashCode();

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
   * return char at index or '\0' if index &lt;0 or &gt;=length()
   *
   * @param index index
   * @return char or '\0'
   */
  char safeCharAt(int index);

  /**
   * Get a portion of this sequence
   *
   * <p>NOTE: the returned value should only depend on start/end indices. If a subsequence of this
   * sequence with matching start/end should equal (using equals()) all such subsequences of this
   * sequence.
   *
   * @param startIndex offset from startIndex of this sequence
   * @param endIndex offset from startIndex of this sequence
   * @return based sequence whose contents reflect the selected portion
   */
  @Override
  T subSequence(int startIndex, int endIndex);

  /**
   * Get a portion of this sequence, if index &lt; 0 use 0, if &gt; length() use length
   *
   * <p>NOTE: the returned value should only depend on start/end indices. If a subsequence of this
   * sequence with matching start/end should equal (using equals()) all such subsequences of this
   * sequence.
   *
   * @param startIndex offset from startIndex of this sequence
   * @param endIndex offset from startIndex of this sequence
   * @return based sequence whose contents reflect the selected portion
   */
  T safeSubSequence(int startIndex, int endIndex);

  /**
   * Get a portion of this sequence, if index &lt; 0 use 0, if &gt; length() use length
   *
   * <p>NOTE: the returned value should only depend on start/end indices. If a subsequence of this
   * sequence with matching start/end should equal (using equals()) all such subsequences of this
   * sequence.
   *
   * @param startIndex offset from startIndex of this sequence
   * @return based sequence whose contents reflect the selected portion
   */
  T safeSubSequence(int startIndex);

  /**
   * Get a portion of this sequence selected by range
   *
   * @param range range to get, coordinates offset form start of this sequence
   * @return based sequence whose contents reflect the selected portion, if range.isNull() then
   *     {@link #nullSequence()}
   */
  T subSequence(Range range);

  /**
   * Get a portion of this sequence before one selected by range
   *
   * @param range range to get, coordinates offset form start of this sequence
   * @return based sequence whose contents reflect the selected portion, if range.isNull() then
   *     {@link #nullSequence()}
   */
  T subSequenceBefore(Range range);

  /**
   * Get a portion of this sequence after one selected by range
   *
   * @param range range to get, coordinates offset form start of this sequence
   * @return based sequence whose contents reflect the selected portion, if range.isNull() then
   *     {@link #nullSequence()}
   */
  T subSequenceAfter(Range range);

  /**
   * Get a portion of this sequence starting from a given offset to endIndex of the sequence
   *
   * @param startIndex offset from startIndex of this sequence
   * @return based sequence whose contents reflect the selected portion
   */
  T subSequence(int startIndex);

  /**
   * Convenience method to get characters offset from endIndex of sequence. no exceptions are
   * thrown, instead a \0 is returned for an invalid index positions
   *
   * @param startIndex offset from endIndex of sequence [ 0..length() )
   * @param endIndex offset from endIndex of sequence [ 0..length() )
   * @return selected portion spanning length() - startIndex to length() - endIndex of this sequence
   */
  T endSequence(int startIndex, int endIndex);

  /**
   * Convenience method to get characters offset from endIndex of sequence. no exceptions are
   * thrown, instead a \0 is returned for an invalid index positions
   *
   * @param startIndex offset from endIndex of sequence [ 0..length() )
   * @return selected portion spanning length() - startIndex to length() of this sequence
   */
  T endSequence(int startIndex);

  // index from the end of the sequence
  // no exceptions are thrown, instead a \0 is returned for an invalid index
  /**
   * Convenience method to get characters offset from end of sequence. no exceptions are thrown,
   * instead a \0 is returned for an invalid index positions
   *
   * @param index offset from end of sequence
   * @return character located at length() - index in this sequence
   */
  char endCharAt(int index);

  /**
   * Convenience method to get characters offset from start or end of sequence. when offset &gt;=0
   * then it is offset from the start of the sequence, when &lt;0 then from the end
   *
   * <p>no exceptions are thrown, instead a \0 is returned for an invalid index positions
   *
   * @param startIndex offset into this sequence
   * @param endIndex offset into this sequence
   * @return selected portion spanning startIndex to endIndex of this sequence. If offset is &lt;0
   *     then it is taken as relative to length()
   */
  T midSequence(int startIndex, int endIndex);

  /**
   * Convenience method to get characters offset from start or end of sequence. when offset &gt;=
   * then it is offset from the start of the sequence, when &lt;0 then from the end
   *
   * <p>no exceptions are thrown, instead a \0 is returned for an invalid index positions
   *
   * @param startIndex offset into this sequence
   * @return selected portion spanning startIndex to length() of this sequence. If offset is &lt;0
   *     then it is taken as relative to length()
   */
  T midSequence(int startIndex);

  /**
   * Convenience method to get characters offset from start or end of sequence. when index &gt;=
   * then it is offset from the start of the sequence, when &lt;0 then from the end no exceptions
   * are thrown, instead a \0 is returned for an invalid index positions
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
   * @param startIndex start index of the sequence to use
   * @return rich char sequence from given inputs
   */
  T sequenceOf(CharSequence charSequence, int startIndex);

  /**
   * Factory function
   *
   * @param charSequence char sequence from which to construct a rich char sequence
   * @param startIndex start index of the sequence to use
   * @param endIndex end index of the sequence to use
   * @return rich char sequence from given inputs
   */
  T sequenceOf(CharSequence charSequence, int startIndex, int endIndex);

  /**
   * Get a sequence builder for this sequence type
   *
   * @param <B> type of builder
   * @return builder which can build this type of sequence
   */
  <B extends ISequenceBuilder<B, T>> B getBuilder();

  /**
   * All index methods return the position or -1 if not found of the given character, characters or
   * string.
   *
   * <p>The basic methods have overloads for 1, 2, or 3 characters and CharSequence parameters. If
   * fromIndex is not given then for forward searching methods 0 is taken as the value, for reverse
   * searching methods length() is taken as the value
   *
   * <p>For forward searching methods fromIndex is the minimum start position for search and
   * endIndex is the maximum end position, if not given the length() of string is assumed.
   *
   * <p>For reverse searching methods fromIndex is the maximum start position for search and
   * startIndex is the minimum end position, if not given then 0 is assumed.
   *
   * @param s character pre whose occurrence to find
   * @return index where found or -1
   */
  int indexOf(CharSequence s);

  int indexOf(CharSequence s, int fromIndex);

  int indexOf(CharSequence s, int fromIndex, int endIndex);

  int indexOf(char c, int fromIndex, int endIndex);

  int indexOf(char c, int fromIndex);

  int indexOf(char c);

  int indexOfAny(CharPredicate s, int fromIndex, int endIndex);

  int indexOfAny(CharPredicate s, int fromIndex);

  int indexOfAny(CharPredicate s);

  int indexOfNot(char c, int fromIndex, int endIndex);

  int indexOfNot(char c, int fromIndex);

  int indexOfNot(char c);

  int indexOfAnyNot(CharPredicate s, int fromIndex, int endIndex);

  int indexOfAnyNot(CharPredicate s, int fromIndex);

  int indexOfAnyNot(CharPredicate s);

  int lastIndexOf(CharSequence s);

  int lastIndexOf(CharSequence s, int fromIndex);

  int lastIndexOf(CharSequence s, int startIndex, int fromIndex);

  int lastIndexOf(char c, int startIndex, int fromIndex);

  int lastIndexOf(char c, int fromIndex);

  int lastIndexOf(char c);

  int lastIndexOfAny(CharPredicate s, int startIndex, int fromIndex);

  int lastIndexOfAny(CharPredicate s, int fromIndex);

  int lastIndexOfAny(CharPredicate s);

  int lastIndexOfNot(char c);

  int lastIndexOfNot(char c, int fromIndex);

  int lastIndexOfNot(char c, int startIndex, int fromIndex);

  int lastIndexOfAnyNot(CharPredicate s, int startIndex, int fromIndex);

  int lastIndexOfAnyNot(CharPredicate s, int fromIndex);

  int lastIndexOfAnyNot(CharPredicate s);

  /**
   * Count leading/trailing characters of this sequence
   *
   * <p>Parameters can be CharPredicate, counts any contiguous leading/trailing characters in the
   * sequence which are contained in the given char set.
   *
   * <p>All functions have overloads: with no fromIndex then 0 is taken for leading and length() for
   * trailing methods with fromIndex then this is taken as the start for leading and end for
   * trailing methods with fromIndex and endIndex, counting will start at fromIndex and stop at
   * endIndex
   *
   * <p>countLeading(CharPredicate): count contiguous leading characters from set in this sequence
   * countLeadingNot(CharPredicate): count contiguous leading characters not from set in this
   * sequence countTrailing(CharPredicate): count contiguous leading characters from set in this
   * sequence countTrailingNot(CharPredicate): count contiguous leading characters not from set in
   * this sequence
   *
   * @param chars predicate for set of contiguous characters which should be counted
   * @return number of chars in contiguous span at start of sequence
   */
  int countLeading(CharPredicate chars);

  int countLeadingNot(CharPredicate chars);

  int countLeading(CharPredicate chars, int startIndex);

  int countLeadingNot(CharPredicate chars, int startIndex);

  int countLeading(CharPredicate chars, int startIndex, int endIndex);

  int countLeadingNot(CharPredicate chars, int startIndex, int endIndex);

  int countTrailing(CharPredicate chars);

  int countTrailingNot(CharPredicate chars);

  int countTrailing(CharPredicate chars, int startIndex);

  int countTrailingNot(CharPredicate chars, int startIndex);

  int countTrailing(CharPredicate chars, int startIndex, int endIndex);

  int countTrailingNot(CharPredicate chars, int startIndex, int endIndex);

  int countLeadingSpace();

  int countLeadingNotSpace();

  int countLeadingSpace(int startIndex);

  int countLeadingNotSpace(int startIndex);

  int countLeadingSpace(int startIndex, int endIndex);

  int countLeadingNotSpace(int startIndex, int endIndex);

  int countTrailingSpace();

  int countTrailingNotSpace();

  int countTrailingSpace(int fromIndex);

  int countTrailingNotSpace(int fromIndex);

  int countTrailingSpace(int startIndex, int fromIndex);

  int countTrailingNotSpace(int startIndex, int fromIndex);

  int countLeadingSpaceTab();

  int countLeadingNotSpaceTab();

  int countLeadingSpaceTab(int startIndex);

  int countLeadingNotSpaceTab(int startIndex);

  int countLeadingSpaceTab(int startIndex, int endIndex);

  int countLeadingNotSpaceTab(int startIndex, int endIndex);

  int countTrailingSpaceTab();

  int countTrailingNotSpaceTab();

  int countTrailingSpaceTab(int fromIndex);

  int countTrailingNotSpaceTab(int fromIndex);

  int countTrailingSpaceTab(int startIndex, int fromIndex);

  int countTrailingNotSpaceTab(int startIndex, int fromIndex);

  int countLeadingWhitespace();

  int countLeadingNotWhitespace();

  int countLeadingWhitespace(int startIndex);

  int countLeadingNotWhitespace(int startIndex);

  int countLeadingWhitespace(int startIndex, int endIndex);

  int countLeadingNotWhitespace(int startIndex, int endIndex);

  int countTrailingWhitespace();

  int countTrailingNotWhitespace();

  int countTrailingWhitespace(int fromIndex);

  int countTrailingNotWhitespace(int fromIndex);

  int countTrailingWhitespace(int startIndex, int fromIndex);

  int countTrailingNotWhitespace(int startIndex, int fromIndex);

  int countOfSpaceTab();

  int countOfNotSpaceTab();

  int countOfWhitespace();

  int countOfNotWhitespace();

  int countOfAny(CharPredicate chars);

  int countOfAnyNot(CharPredicate chars);

  int countOfAny(CharPredicate chars, int startIndex);

  int countOfAnyNot(CharPredicate chars, int startIndex);

  int countOfAny(CharPredicate chars, int startIndex, int endIndex);

  int countOfAnyNot(CharPredicate chars, int startIndex, int endIndex);

  /**
   * Count column of indent given by chars in the set in this sequence, expanding tabs to 4th column
   *
   * @param startColumn column of where this sequence starts
   * @param chars whitespace characters
   * @return column of first non-whitespace as given by chars
   */
  int countLeadingColumns(int startColumn, CharPredicate chars);

  /**
   * Range of kept sequence when trim start/end of this sequence is performed, characters to trim
   * are passed in the sequence argument
   *
   * <p>returns range of kept sequence or if nothing matched then Range.NULL is returned, meaning
   * keep all
   *
   * @param keep minimum length of would be trimmed characters to keep. ie. keep 4, will leave 0..4
   *     as is but remove any &gt;4
   * @param chars set of characters to trim from start of line
   * @return range in this sequence to keep or Range.NULL if to keep all
   */
  Range trimStartRange(int keep, CharPredicate chars);

  Range trimEndRange(int keep, CharPredicate chars);

  Range trimRange(int keep, CharPredicate chars);

  Range trimStartRange(CharPredicate chars);

  Range trimEndRange(CharPredicate chars);

  Range trimRange(CharPredicate chars);

  Range trimStartRange(int keep);

  Range trimEndRange(int keep);

  Range trimRange(int keep);

  Range trimStartRange();

  Range trimEndRange();

  Range trimRange();

  /**
   * Trim, Trim start/end of this sequence, characters to trim are passed in the sequence argument
   *
   * <p>returns trimmed sequence or if nothing matched the original sequence
   *
   * @param keep minimum length of would be trimmed characters to keep. ie. keep 4, will leave 0..4
   *     as is but remove any &gt;4
   * @param chars set of characters to trim from start of line
   * @return sequence after it is trimmed
   */
  T trimStart(int keep, CharPredicate chars);

  T trimEnd(int keep, CharPredicate chars);

  T trim(int keep, CharPredicate chars);

  T trimStart(int keep);

  T trimEnd(int keep);

  T trim(int keep);

  T trimStart(CharPredicate chars);

  T trimEnd(CharPredicate chars);

  T trim(CharPredicate chars);

  T trimStart();

  T trimEnd();

  T trim();

  /**
   * Get the characters Trimmed, Trimmed from start/end of this sequence, characters to trim are
   * passed in the sequence argument
   *
   * <p>returns trimmed sequence or if nothing matched the original sequence
   *
   * <p>The pair returning functions return before and after sequence
   *
   * @param keep minimum length of would be trimmed characters to keep. ie. keep 4, will leave 0..4
   *     as is but remove any &gt;4
   * @param chars set of characters to trim from start of line
   * @return part of the sequence that was trimmed from the start
   */
  T trimmedStart(int keep, CharPredicate chars);

  T trimmedEnd(int keep, CharPredicate chars);

  Pair<T, T> trimmed(int keep, CharPredicate chars);

  T trimmedStart(int keep);

  T trimmedEnd(int keep);

  Pair<T, T> trimmed(int keep);

  T trimmedStart(CharPredicate chars);

  T trimmedEnd(CharPredicate chars);

  Pair<T, T> trimmed(CharPredicate chars);

  T trimmedStart();

  T trimmedEnd();

  Pair<T, T> trimmed();

  /**
   * Get the chars needed for padding to length
   *
   * @param length length
   * @param pad char to use for padding
   * @return padding chars
   */
  T padding(int length, char pad);

  T padding(int length);

  /**
   * Pad this sequence to given length
   *
   * @param length length to pad
   * @param pad char to use for padding
   * @return sequence padded
   */
  T padStart(int length, char pad);

  T padEnd(int length, char pad);

  boolean isEmpty();

  boolean isBlank();

  boolean isNotEmpty();

  boolean isNotBlank();

  boolean isNull();

  boolean isNotNull();

  /**
   * If this sequence is the nullSequence() instance then returns other, otherwise returns this
   * sequence.
   *
   * @param other based sequence to return if this is nullSequence()
   * @return this or other
   */
  T ifNull(T other);

  /**
   * If this sequence is the nullSequence() instance then returns an empty subSequence from the end
   * of other, otherwise returns this sequence.
   *
   * @param other based sequence from which to take the empty sequence
   * @return this or other.subSequence(other.length(), other.length())
   */
  T ifNullEmptyAfter(T other);

  /**
   * If this sequence is the nullSequence() instance then returns an empty subSequence from the
   * start of other, otherwise returns this sequence.
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
   * If predicate returns true for this sequence and one of given sequences return nullSequence()
   * otherwise returns this sequence.
   *
   * @param predicate bi predicate for test, first argument is always this, second is one of the
   *     match sequences
   * @param matches match sequence list
   * @return this or nullSequence()
   */
  T nullIf(BiPredicate<? super T, ? super CharSequence> predicate, CharSequence... matches);

  T nullIfNot(BiPredicate<? super T, ? super CharSequence> predicate, CharSequence... matches);

  /**
   * If predicate returns true for one of given sequences return nullSequence() otherwise returns
   * this sequence.
   *
   * @param predicate sequence predicate
   * @param matches match sequence list
   * @return this or nullSequence()
   */
  T nullIf(Predicate<? super CharSequence> predicate, CharSequence... matches);

  T nullIfNot(Predicate<? super CharSequence> predicate, CharSequence... matches);

  /**
   * If this sequence matches one of given sequences return nullSequence() otherwise returns this
   * sequence.
   *
   * @param matches match sequence list
   * @return this or nullSequence()
   */
  T nullIf(CharSequence... matches);

  T nullIfNot(CharSequence... matches);

  T nullIfStartsWith(CharSequence... matches);

  T nullIfNotStartsWith(CharSequence... matches);

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

  /*
    EOL helper methods
  */

  /**
   * Get the length of EOL character at the end of this sequence, if present.
   *
   * <p>\n - 1 \r - 1 \r\n - 2
   *
   * @return 0 if no EOL, 1, or 2 depending on the EOL suffix of this sequence
   */
  int eolEndLength();

  /**
   * Get the length of EOL character at the given index of this sequence, if present.
   *
   * <p>\n - 1 \r - 1 \r\n - 2
   *
   * @param eolEnd index where the EOL ends, if any, any value can be passed for this argument. If
   *     &gt; length of this sequence then it is the same as passing length(), if 0 or less then 0
   *     is returned.
   * @return 0 if no EOL, 1, or 2 depending on the EOL suffix of this sequence
   */
  int eolEndLength(int eolEnd);

  /**
   * Get the length of EOL character at the given index of this sequence, if present.
   *
   * <p>\n - 1 \r - 1 \r\n - 2
   *
   * @param eolStart index where the EOL starts, if any, any value can be passed for this argument.
   *     If &gt;= length of this sequence then 0 is returned if 0 or less then it is the same as 0
   * @return 0 if no EOL, 1, or 2 depending on the EOL suffix of this sequence
   */
  int eolStartLength(int eolStart);

  /**
   * Return Range of eol at given index
   *
   * @param eolEnd index where the EOL ends, if any, any value can be passed for this argument. If
   *     &gt; length of this sequence then it is the same as passing length(), if 0 or less then 0
   *     is returned.
   * @return range of eol given by index of its end or Range.NULL if no eol ends at index
   */
  Range eolEndRange(int eolEnd);

  /**
   * Return Range of eol at given index
   *
   * @param eolStart index where the EOL starts, if any, any value can be passed for this argument.
   *     If &gt;= length of this sequence then 0 is returned if 0 or less then it is the same as 0
   * @return range of eol given by index of its end or Range.NULL if no eol starts at index
   */
  Range eolStartRange(int eolStart);

  /**
   * Trim last eol at the end of this sequence,
   *
   * @return sequence with one EOL trimmed off if it had one
   */
  T trimEOL();

  /**
   * Get Trimmed part by {@link #trimEOL()}
   *
   * @return trimmed off EOL if sequence had one or {@link #nullSequence()}
   */
  T trimmedEOL();

  /**
   * Find start/end region in this sequence delimited by any characters in argument or the
   * CharSequence
   *
   * <p>For Any and AnyNot methods uses the CharSequence argument as a character set of possible
   * delimiting characters
   *
   * @param s character sequence delimiting the region
   * @param index from which to start looking for end of region
   * @return index of end of region delimited by s
   */
  int endOfDelimitedBy(CharSequence s, int index);

  int endOfDelimitedByAny(CharPredicate s, int index);

  int endOfDelimitedByAnyNot(CharPredicate s, int index);

  int startOfDelimitedBy(CharSequence s, int index);

  int startOfDelimitedByAny(CharPredicate s, int index);

  int startOfDelimitedByAnyNot(CharPredicate s, int index);

  /**
   * Get the offset of the end of line at given index, end of line delimited by \n
   *
   * @param index index where to start searching for end of line
   * @return index of end of line delimited by \n
   */
  int endOfLine(int index);

  /**
   * Get the offset of the end of line at given index, end of line delimited by \n or any of \n \r
   * \r\n for Any methods.
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
   * Get the offset of the start of line at given index, start of line delimited by \n or any of \n
   * \r \r\n for Any methods.
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
   * Get Range of leading blank lines at given index offsets in sequence
   *
   * @param eolChars characters to consider as EOL, note {@link #eolStartLength(int)} should report
   *     length of EOL found if length &gt; 1
   * @param fromIndex minimum index in sequence to check and include in range of blank lines can be
   *     any value, if less than 0 it is the same as 0, if greater than length() it is the same as
   *     length()
   * @param endIndex index in sequence from which to start blank line search, also maximum index to
   *     include in blank lines range can be any value, if less than 0 it is the same as 0, if
   *     greater than length() it is the same as length()
   * @return range of blank lines at or before fromIndex and ranging to minimum of startIndex,
   *     Range.NULL if none found if the range in sequence contains only whitespace characters then
   *     the whole range will be returned even if contains no EOL characters
   */
  Range leadingBlankLinesRange(CharPredicate eolChars, int fromIndex, int endIndex);

  /**
   * Get Range of trailing blank lines at given index offsets in sequence
   *
   * @param eolChars characters to consider as EOL, note {@link #eolStartLength(int)} should report
   *     length of EOL found if length &gt; 1
   * @param startIndex index in sequence from which to start blank line search, also maximum index
   *     to include in blank lines range can be any value, if less than 0 it is the same as 0, if
   *     greater than length() it is the same as length()
   * @param fromIndex maximum index in sequence to check and include in range of blank lines can be
   *     any value, if less than 0 it is the same as 0, if greater than length() it is the same as
   *     length()
   * @return range of blank lines at or before fromIndex and ranging to minimum of startIndex if the
   *     range in sequence contains only whitespace characters then the whole range will be returned
   *     even if contains no EOL characters
   */
  Range trailingBlankLinesRange(CharPredicate eolChars, int startIndex, int fromIndex);

  Range leadingBlankLinesRange();

  Range leadingBlankLinesRange(int startIndex);

  Range leadingBlankLinesRange(int fromIndex, int endIndex);

  Range trailingBlankLinesRange();

  Range trailingBlankLinesRange(int fromIndex);

  Range trailingBlankLinesRange(int startIndex, int fromIndex);

  List<Range> blankLinesRemovedRanges();

  List<Range> blankLinesRemovedRanges(int fromIndex);

  List<Range> blankLinesRemovedRanges(int fromIndex, int endIndex);

  List<Range> blankLinesRemovedRanges(CharPredicate eolChars, int fromIndex, int endIndex);

  /**
   * Trim end to end of line containing index
   *
   * @param eolChars characters to consider as EOL, note {@link #eolStartLength(int)} should report
   *     length of EOL found if length &gt; 1
   * @param includeEol true if EOL is to be included in the line
   * @param index index for offset contained by the line can be any value, if less than 0 it is the
   *     same as 0, if greater than length() it is the same as length()
   * @return trimmed version of the sequence to given EOL or the original sequence
   */
  T trimToEndOfLine(CharPredicate eolChars, boolean includeEol, int index);

  T trimToEndOfLine(boolean includeEol, int index);

  T trimToEndOfLine(boolean includeEol);

  T trimToEndOfLine(int index);

  T trimToEndOfLine();

  /**
   * Trim start to start of line containing index
   *
   * @param eolChars characters to consider as EOL, note {@link #eolStartLength(int)} should report
   *     length of EOL found if length &gt; 1
   * @param includeEol true if EOL is to be included in the line
   * @param index index for offset contained by the line can be any value, if less than 0 it is the
   *     same as 0, if greater than length() it is the same as length()
   * @return trimmed version of the sequence to given EOL or the original sequence
   */
  T trimToStartOfLine(CharPredicate eolChars, boolean includeEol, int index);

  T trimToStartOfLine(boolean includeEol, int index);

  T trimToStartOfLine(boolean includeEol);

  T trimToStartOfLine(int index);

  T trimToStartOfLine();

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
   * @param other characters to match against
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
   * @param chars characters to match against
   * @param startIndex index from which to start the match
   * @param ignoreCase if true match ignoring case differences
   * @return true if characters at the start index of this sequence match
   */
  boolean matchChars(CharSequence chars, int startIndex, boolean ignoreCase);

  boolean matchChars(CharSequence chars, int startIndex);

  boolean matchCharsIgnoreCase(CharSequence chars, int startIndex);

  /**
   * Test the sequence portion for a match to another CharSequence
   *
   * @param chars characters to match against
   * @param startIndex index from which to start the match
   * @param endIndex index at which to end the matching
   * @param fullMatchOnly if true will do quick fail if length of chars is longer than characters
   *     after startIndex in this sequence
   * @param ignoreCase if true match ignoring case differences
   * @return count of characters at the start index of this sequence matching corresponding
   *     characters in chars
   */
  int matchedCharCount(
      CharSequence chars, int startIndex, int endIndex, boolean fullMatchOnly, boolean ignoreCase);

  int matchedCharCount(CharSequence chars, int startIndex, int endIndex, boolean ignoreCase);

  int matchedCharCount(CharSequence chars, int startIndex, boolean ignoreCase);

  int matchedCharCount(CharSequence chars, int startIndex, int endIndex);

  int matchedCharCount(CharSequence chars, int startIndex);

  int matchedCharCountIgnoreCase(CharSequence chars, int startIndex, int endIndex);

  int matchedCharCountIgnoreCase(CharSequence chars, int startIndex);

  /**
   * Test the sequence portion for a match to another CharSequence, reverse order
   *
   * @param chars characters to match against
   * @param endIndex index from which to start the match and proceed to 0
   * @param ignoreCase if true match ignoring case differences
   * @return true if characters at the start index of this sequence match
   */
  boolean matchCharsReversed(CharSequence chars, int endIndex, boolean ignoreCase);

  boolean matchCharsReversed(CharSequence chars, int endIndex);

  boolean matchCharsReversedIgnoreCase(CharSequence chars, int endIndex);

  /**
   * Test the sequence portion for a match to another CharSequence, equivalent to taking
   * this.subSequence(startIndex, fromIndex) and then count matching chars going from end of both
   * sequences
   *
   * @param chars characters to match against
   * @param startIndex index at which to stop the match
   * @param fromIndex index from which to start the match, not inclusive, matching starts at
   *     fromIndex-1 and proceeds towards 0
   * @param ignoreCase if true match ignoring case differences
   * @return count of characters at the from index of this sequence matching corresponding
   *     characters in chars in reverse order
   */
  int matchedCharCountReversed(
      CharSequence chars, int startIndex, int fromIndex, boolean ignoreCase);

  int matchedCharCountReversed(CharSequence chars, int startIndex, int fromIndex);

  int matchedCharCountReversedIgnoreCase(CharSequence chars, int startIndex, int fromIndex);

  int matchedCharCountReversed(CharSequence chars, int fromIndex, boolean ignoreCase);

  int matchedCharCountReversed(CharSequence chars, int fromIndex);

  int matchedCharCountReversedIgnoreCase(CharSequence chars, int fromIndex);

  /**
   * test if this sequence ends with given characters
   *
   * @param suffix characters to test
   * @return true if ends with suffix
   */
  boolean endsWith(CharSequence suffix);

  boolean endsWith(CharPredicate chars);

  boolean endsWithEOL(); // EOL "\n"

  boolean endsWithAnyEOL(); // EOL_CHARS "\r\n"

  boolean endsWithSpace(); // SPACE " "

  boolean endsWithSpaceTab(); // SPACE_TAB " \t"

  boolean endsWithWhitespace(); // WHITESPACE_CHARS " \t\r\n"

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
   * @param suffix characters to test
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

  boolean startsWith(CharPredicate chars);

  boolean startsWithEOL(); // EOL "\n"

  boolean startsWithAnyEOL(); // EOL_CHARS "\r\n"

  boolean startsWithSpace(); // SPACE " "

  boolean startsWithSpaceTab(); // SPACE_TAB " \t"

  boolean startsWithWhitespace(); // WHITESPACE_CHARS " \t\r\n"

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
   * @param prefix characters to test
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
   * @param suffix characters to remove
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
   * @param prefix characters to remove
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
   * Remove suffix if present but only if this sequence is longer than the suffix, ignoring case
   * differences
   *
   * @param suffix characters to remove
   * @return sequence with suffix removed, or same sequence if no suffix was present
   */
  T removeProperSuffixIgnoreCase(CharSequence suffix);

  /**
   * Remove suffix if present but only if this sequence is longer than the suffix
   *
   * @param suffix characters to remove
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
   * Remove prefix if present but only if this sequence is longer than the suffix, ignoring case
   * differences
   *
   * @param prefix characters to remove
   * @return sequence with prefix removed, or same sequence if no prefix was present
   */
  T removeProperPrefixIgnoreCase(CharSequence prefix);

  /**
   * Remove prefix if present but only if this sequence is longer than the suffix
   *
   * @param prefix characters to remove
   * @param ignoreCase case ignored when true
   * @return sequence with prefix removed, or same sequence if no prefix was present
   */
  T removeProperPrefix(CharSequence prefix, boolean ignoreCase);

  /**
   * Insert char sequence at given index
   *
   * @param index index of insertion. if &gt;length of this sequence then same as length, if &lt;0
   *     then same as 0
   * @param chars char sequence to insert
   * @return resulting sequence based sequence implementation may throw an IllegalArgumentException
   *     if inserting another based sequence out of order based on offsets
   */
  T insert(int index, CharSequence chars);

  /**
   * Delete range in sequence
   *
   * @param startIndex start index of deletion
   * @param endIndex end index, not inclusive, of insertion
   * @return resulting sequence
   */
  T delete(int startIndex, int endIndex);

  /**
   * Replace part of the sequence with a char sequence
   *
   * @param startIndex start index of replaced part
   * @param endIndex end index of replaced part
   * @param replacement char sequence
   * @return resulting sequence
   */
  T replace(int startIndex, int endIndex, CharSequence replacement);

  /**
   * Replace all occurrences of one sequence with another
   *
   * @param find sequence to find
   * @param replace replacement sequence
   * @return array of indices
   */
  T replace(CharSequence find, CharSequence replace);

  /**
   * Map characters of this sequence to: Uppercase, Lowercase or use custom mapping
   *
   * @return lowercase version of sequence
   */
  T toLowerCase();

  T toUpperCase();

  T toMapped(CharMapper mapper);

  /**
   * Map spaces to non break spaces
   *
   * @return mapped sequence with spc changed to NbSp
   */
  T toNbSp();

  /**
   * Map non break spaces to spaces
   *
   * @return mapped sequence with NbSp changed to spc
   */
  T toSpc();

  String toVisibleWhitespaceString();

  /**
   * Split helpers based on delimiter character sets contained in CharSequence
   *
   * @param delimiter delimiter char sequence to use for splitting
   * @param limit max number of segments to split
   * @param flags flags for desired options: SPLIT_INCLUDE_DELIMS: include delimiters as part of
   *     split item SPLIT_TRIM_PARTS: trim the segments, if trimChars is not null or empty then this
   *     flag is turned on automatically SPLIT_SKIP_EMPTY: skip empty segments (or empty after
   *     trimming if enabled) SPLIT_INCLUDE_DELIM_PARTS: include delimiters as separate split item
   *     of its own SPLIT_TRIM_SKIP_EMPTY: same as SPLIT_TRIM_PARTS | SPLIT_SKIP_EMPTY
   * @param trimChars set of characters that should be used for trimming individual split results
   * @return List of split results
   */
  List<T> splitList(CharSequence delimiter, int limit, int flags, CharPredicate trimChars);

  List<T> splitList(CharSequence delimiter, int limit, int flags);

  List<T> splitList(CharSequence delimiter);

  T[] split(CharSequence delimiter, int limit, int flags, CharPredicate trimChars);

  T[] split(CharSequence delimiter, int limit, int flags);

  T[] split(CharSequence delimiter);

  /**
   * Split helpers based on delimiter character sets contained in CharPredicate
   *
   * @param delimiter sequence of chars on which to split this sequence
   * @param limit max number of segments to split
   * @param includeDelims if true include delimiters as part of split item
   * @param trimChars set of characters that should be used for trimming individual split results
   * @return List of split results
   */
  List<T> splitList(
      CharSequence delimiter, int limit, boolean includeDelims, CharPredicate trimChars);

  List<T> splitList(CharSequence delimiter, boolean includeDelims, CharPredicate trimChars);

  T[] split(CharSequence delimiter, int limit, boolean includeDelims, CharPredicate trimChars);

  T[] split(CharSequence delimiter, boolean includeDelims, CharPredicate trimChars);

  // NOTE: these default to including delimiters as part of split item

  T[] splitEOL();

  T[] splitEOL(boolean includeDelims);

  List<T> splitListEOL();

  List<T> splitListEOL(boolean includeDelims);

  List<T> splitListEOL(boolean includeDelims, CharPredicate trimChars);

  /**
   * Get indices of all occurrences of a sequence
   *
   * @param s sequence whose indices to find
   * @return array of indices
   */
  int[] indexOfAll(CharSequence s);

  /**
   * Prefix this sequence with a char sequence
   *
   * @param prefix char sequence
   * @return resulting sequence
   */
  T prefixWith(CharSequence prefix);

  /**
   * Prefix this sequence with a char sequence
   *
   * @param suffix char sequence
   * @return resulting sequence
   */
  T suffixWith(CharSequence suffix);

  /**
   * Prefix this sequence with a char sequence if not already starting with prefix
   *
   * @param prefix char sequence
   * @return resulting sequence
   */
  T prefixOnceWith(CharSequence prefix);

  /**
   * Suffix this sequence with a char sequence if not already ending with suffix
   *
   * @param suffix char sequence
   * @return resulting sequence
   */
  T suffixOnceWith(CharSequence suffix);

  T appendEOL();

  T suffixWithEOL();

  T prefixWithEOL();

  T prefixOnceWithEOL();

  T suffixOnceWithEOL();

  T appendSpace();

  T suffixWithSpace();

  T prefixWithSpace();

  T appendSpaces(int count);

  T suffixWithSpaces(int count);

  T prefixWithSpaces(int count);

  T prefixOnceWithSpace();

  T suffixOnceWithSpace();

  /**
   * Append helpers
   *
   * @param out string builder
   * @param startIndex start index
   * @param endIndex end index
   * @param charMapper mapping to use for output or null if none
   * @return this
   */
  T appendTo(StringBuilder out, CharMapper charMapper, int startIndex, int endIndex);

  T appendTo(StringBuilder out, CharMapper charMapper);

  T appendTo(StringBuilder out, CharMapper charMapper, int startIndex);

  T appendTo(StringBuilder out, int startIndex, int endIndex);

  T appendTo(StringBuilder out);

  T appendTo(StringBuilder out, int startIndex);

  /**
   * Append given ranges of this sequence to string builder
   *
   * @param out string builder to append to
   * @param charMapper mapping to use for output or null if none
   * @param ranges ranges to append, null range or one for which range.isNull() is true ranges are
   *     skipped
   * @return this
   */
  T appendRangesTo(StringBuilder out, CharMapper charMapper, Range... ranges);

  T appendRangesTo(StringBuilder out, Range... ranges);

  T appendRangesTo(StringBuilder out, CharMapper charMapper, Iterable<? extends Range> ranges);

  T appendRangesTo(StringBuilder out, Iterable<? extends Range> ranges);

  /**
   * Build a sequence of ranges in this sequence
   *
   * <p>NOTE: BasedSequence ranges must be non-overlapping and ordered by startOffset or
   * IllegalArgumentException will be thrown
   *
   * @param ranges ranges to extract
   * @return resulting sequence
   */
  T extractRanges(Range... ranges);

  T extractRanges(Iterable<Range> ranges);

  /**
   * Concatenate this sequence and list of others, returning sequence of result
   *
   * @param sequences list of char sequences to append to this sequence, null sequences are skipped
   * @return appended sequence
   */
  T append(CharSequence... sequences);

  T append(Iterable<? extends CharSequence> sequences);

  /**
   * Get the line and column information from index into sequence
   *
   * @param index index for which to get line information
   * @return Pair(line, column) where line and column are 0 based, throws IllegalArgumentException
   *     if index &lt; 0 or &gt; length.
   */
  Pair<Integer, Integer> lineColumnAtIndex(int index);

  int columnAtIndex(int index);

  /**
   * Safe, if index out of range returns '\0'
   *
   * @param index index in string
   * @param predicate character set predicate
   * @return true if character at index tests true
   */
  boolean isCharAt(int index, CharPredicate predicate);

  /**
   * Return string or null if BaseSequence.NULL
   *
   * @return string or null if BaseSequence.NULL
   */
  String toStringOrNull();

  boolean isIn(String[] texts);

  boolean isIn(Collection<? extends CharSequence> texts);
}
