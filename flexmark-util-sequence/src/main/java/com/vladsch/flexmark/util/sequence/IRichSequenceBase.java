package com.vladsch.flexmark.util.sequence;

import static com.vladsch.flexmark.util.misc.Utils.rangeLimit;

import com.vladsch.flexmark.util.collection.iteration.ArrayIterable;
import com.vladsch.flexmark.util.misc.CharPredicate;
import com.vladsch.flexmark.util.misc.Pair;
import com.vladsch.flexmark.util.sequence.builder.ISequenceBuilder;
import com.vladsch.flexmark.util.sequence.mappers.ChangeCase;
import com.vladsch.flexmark.util.sequence.mappers.CharMapper;
import com.vladsch.flexmark.util.sequence.mappers.SpaceMapper;
import java.util.Collection;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/**
 * An abstract base for RichSequence which implements most of the methods allowing subclasses to
 * implement RichSequence with minimal support methods
 */
abstract class IRichSequenceBase<T extends IRichSequence<T>> implements IRichSequence<T> {
  // cached value
  private int hash;

  /**
   * Constructor with pre-computed hash if available, 0 for lazy computation if length() not 0
   *
   * <p>NOTE: the hash code computed for this class is equivalent to the string hash of the same
   * characters to ensure that equals can use the hash code for quick failure. CharSequence hash
   * code is not specified therefore when in doubt about how it is computed then 0 should be passed
   * to this constructor to compute one that is equal to the string content.
   *
   * @param hash hash code for the char sequence.
   */
  IRichSequenceBase(int hash) {
    this.hash = hash;
  }

  /**
   * Equality comparison based on character content of this sequence, with quick fail resorting to
   * content comparison only if length and hashCodes are equal
   *
   * @param object any char sequence
   * @return true if character contents are equal
   */
  @Override
  public final boolean equals(Object object) {
    return SequenceUtils.equals(this, object);
  }

  /**
   * String hash code computation
   *
   * @return hash code of equivalent string
   */
  @Override
  public final int hashCode() {
    int h = hash;
    if (h == 0 && length() > 0) {
      h = SequenceUtils.hashCode(this);
      hash = h;
    }
    return h;
  }

  @Override
  public final boolean equalsIgnoreCase(Object other) {
    return (this == other)
        || (other instanceof CharSequence)
            && ((CharSequence) other).length() == length()
            && matchChars((CharSequence) other, 0, true);
  }

  @Override
  public final boolean equals(Object other, boolean ignoreCase) {
    return (this == other)
        || other instanceof CharSequence
            && ((CharSequence) other).length() == length()
            && matchChars((CharSequence) other, 0, ignoreCase);
  }

  @Override
  public int compareTo(CharSequence o) {
    return SequenceUtils.compare(this, o);
  }

  @Override
  public final T sequenceOf(CharSequence charSequence) {
    return charSequence == null
        ? nullSequence()
        : sequenceOf(charSequence, 0, charSequence.length());
  }

  @Override
  public final T sequenceOf(CharSequence charSequence, int startIndex) {
    return charSequence == null
        ? nullSequence()
        : sequenceOf(charSequence, startIndex, charSequence.length());
  }

  @Override
  public final T subSequence(int startIndex) {
    return subSequence(startIndex, length());
  }

  /**
   * Get a portion of this sequence selected by range
   *
   * @param range range to get, coordinates offset form start of this sequence
   * @return sequence whose contents reflect the selected portion, if range.isNull() then this is
   *     returned
   */
  @Override
  public final T subSequence(Range range) {
    return range.isNull() ? (T) this : subSequence(range.getStart(), range.getEnd());
  }

  /**
   * Get a portion of this sequence before one selected by range
   *
   * @param range range to get, coordinates offset form start of this sequence
   * @return sequence whose contents come before the selected range, if range.isNull() then {@link
   *     #nullSequence()}
   */
  @Override
  public final T subSequenceBefore(Range range) {
    return range.isNull() ? nullSequence() : subSequence(0, range.getStart());
  }

  /**
   * Get a portion of this sequence after one selected by range
   *
   * @param range range to get, coordinates offset form start of this sequence
   * @return sequence whose contents come after the selected range, if range.isNull() then {@link
   *     #nullSequence()}
   */
  @Override
  public final T subSequenceAfter(Range range) {
    return range.isNull() ? nullSequence() : subSequence(range.getEnd());
  }

  /**
   * Get a portions of this sequence before and after one selected by range
   *
   * @param range range to get, coordinates offset form start of this sequence
   * @return sequence whose contents come before and after the selected range, if range.isNull()
   *     then {@link #nullSequence()}
   */
  private final Pair<T, T> subSequenceBeforeAfter(Range range) {
    return new Pair<>(subSequenceBefore(range), subSequenceAfter(range));
  }

  @Override
  public final T endSequence(int startIndex, int endIndex) {
    int length = length();
    int useStart = length - startIndex;
    int useEnd = length - endIndex;

    useEnd = rangeLimit(useEnd, 0, length);
    useStart = rangeLimit(useStart, 0, useEnd);
    return subSequence(useStart, useEnd);
  }

  @Override
  public final T endSequence(int startIndex) {
    return endSequence(startIndex, 0);
  }

  @Override
  public final char endCharAt(int index) {
    int length = length();
    if (index < 0 || index >= length) {
      return SequenceUtils.NUL;
    }
    return charAt(length - index);
  }

  @Override
  public final T midSequence(int startIndex, int endIndex) {
    int length = length();
    int useStart = startIndex < 0 ? length + startIndex : startIndex;
    int useEnd = endIndex < 0 ? length + endIndex : endIndex;

    useEnd = rangeLimit(useEnd, 0, length);
    useStart = rangeLimit(useStart, 0, useEnd);
    return subSequence(useStart, useEnd);
  }

  @Override
  public final T midSequence(int startIndex) {
    return midSequence(startIndex, length());
  }

  @Override
  public final char midCharAt(int index) {
    int length = length();
    if (index < -length || index >= length) {
      return SequenceUtils.NUL;
    }
    return charAt(index < 0 ? length + index : index);
  }

  @Override
  public final char lastChar() {
    return isEmpty() ? SequenceUtils.NUL : charAt(length() - 1);
  }

  @Override
  public final char firstChar() {
    return isEmpty() ? SequenceUtils.NUL : charAt(0);
  }

  @Override
  public char safeCharAt(int index) {
    return index < 0 || index >= length() ? SequenceUtils.NUL : charAt(index);
  }

  @Override
  public T safeSubSequence(int startIndex, int endIndex) {
    int length = length();
    startIndex = Math.max(0, Math.min(length, startIndex));
    endIndex = Math.max(startIndex, Math.min(length, endIndex));
    return subSequence(startIndex, endIndex);
  }

  @Override
  public T safeSubSequence(int startIndex) {
    int length = length();
    startIndex = Math.max(0, Math.min(length, startIndex));
    return subSequence(startIndex, length);
  }

  @Override
  public boolean isCharAt(int index, CharPredicate predicate) {
    return predicate.test(safeCharAt(index));
  }

  @Override
  public String toStringOrNull() {
    return isNull() ? null : toString();
  }

  @Override
  public final int indexOf(CharSequence s) {
    return SequenceUtils.indexOf(this, s);
  }

  @Override
  public final int indexOf(CharSequence s, int fromIndex) {
    return SequenceUtils.indexOf(this, s, fromIndex);
  }

  @Override
  public final int indexOf(CharSequence s, int fromIndex, int endIndex) {
    return SequenceUtils.indexOf(this, s, fromIndex, endIndex);
  }

  @Override
  public final int indexOf(char c) {
    return SequenceUtils.indexOf(this, c);
  }

  @Override
  public final int indexOf(char c, int fromIndex) {
    return SequenceUtils.indexOf(this, c, fromIndex);
  }

  @Override
  public final int indexOfAny(CharPredicate s) {
    return SequenceUtils.indexOfAny(this, s);
  }

  @Override
  public final int indexOfAny(CharPredicate s, int index) {
    return SequenceUtils.indexOfAny(this, s, index);
  }

  @Override
  public final int indexOfAnyNot(CharPredicate s) {
    return SequenceUtils.indexOfAnyNot(this, s);
  }

  @Override
  public final int indexOfAnyNot(CharPredicate s, int fromIndex) {
    return SequenceUtils.indexOfAnyNot(this, s, fromIndex);
  }

  @Override
  public final int indexOfAnyNot(CharPredicate s, int fromIndex, int endIndex) {
    return SequenceUtils.indexOfAnyNot(this, s, fromIndex, endIndex);
  }

  @Override
  public final int indexOfNot(char c) {
    return SequenceUtils.indexOfNot(this, c);
  }

  @Override
  public final int indexOfNot(char c, int fromIndex) {
    return SequenceUtils.indexOfNot(this, c, fromIndex);
  }

  @Override
  public final int lastIndexOf(char c) {
    return SequenceUtils.lastIndexOf(this, c);
  }

  @Override
  public final int lastIndexOf(char c, int fromIndex) {
    return SequenceUtils.lastIndexOf(this, c, fromIndex);
  }

  @Override
  public final int lastIndexOfNot(char c) {
    return SequenceUtils.lastIndexOfNot(this, c);
  }

  @Override
  public final int lastIndexOfNot(char c, int fromIndex) {
    return SequenceUtils.lastIndexOfNot(this, c, fromIndex);
  }

  @Override
  public final int lastIndexOf(CharSequence s) {
    return SequenceUtils.lastIndexOf(this, s);
  }

  @Override
  public final int lastIndexOf(CharSequence s, int fromIndex) {
    return SequenceUtils.lastIndexOf(this, s, fromIndex);
  }

  @Override
  public final int lastIndexOfAny(CharPredicate s, int fromIndex) {
    return SequenceUtils.lastIndexOfAny(this, s, fromIndex);
  }

  @Override
  public final int lastIndexOfAny(CharPredicate s) {
    return SequenceUtils.lastIndexOfAny(this, s);
  }

  @Override
  public final int lastIndexOfAnyNot(CharPredicate s) {
    return SequenceUtils.lastIndexOfAnyNot(this, s);
  }

  @Override
  public final int lastIndexOfAnyNot(CharPredicate s, int fromIndex) {
    return SequenceUtils.lastIndexOfAnyNot(this, s, fromIndex);
  }

  @Override
  public final int lastIndexOfAnyNot(CharPredicate s, int startIndex, int fromIndex) {
    return SequenceUtils.lastIndexOfAnyNot(this, s, startIndex, fromIndex);
  }

  @Override
  public final int indexOf(char c, int fromIndex, int endIndex) {
    return SequenceUtils.indexOf(this, c, fromIndex, endIndex);
  }

  @Override
  public final int indexOfNot(char c, int fromIndex, int endIndex) {
    return SequenceUtils.indexOfNot(this, c, fromIndex, endIndex);
  }

  @Override
  public final int indexOfAny(CharPredicate s, int fromIndex, int endIndex) {
    return SequenceUtils.indexOfAny(this, s, fromIndex, endIndex);
  }

  @Override
  public final int lastIndexOf(CharSequence s, int startIndex, int fromIndex) {
    return SequenceUtils.lastIndexOf(this, s, startIndex, fromIndex);
  }

  @Override
  public final int lastIndexOf(char c, int startIndex, int fromIndex) {
    return SequenceUtils.lastIndexOf(this, c, startIndex, fromIndex);
  }

  @Override
  public final int lastIndexOfNot(char c, int startIndex, int fromIndex) {
    return SequenceUtils.lastIndexOfNot(this, c, startIndex, fromIndex);
  }

  @Override
  public final int lastIndexOfAny(CharPredicate s, int startIndex, int fromIndex) {
    return SequenceUtils.lastIndexOfAny(this, s, startIndex, fromIndex);
  }

  @Override
  public final int countOfSpaceTab() {
    return SequenceUtils.countOfSpaceTab(this);
  }

  @Override
  public final int countOfNotSpaceTab() {
    return SequenceUtils.countOfNotSpaceTab(this);
  }

  @Override
  public final int countOfWhitespace() {
    return SequenceUtils.countOfWhitespace(this);
  }

  @Override
  public final int countOfNotWhitespace() {
    return SequenceUtils.countOfNotWhitespace(this);
  }

  @Override
  public final int countOfAny(CharPredicate chars, int fromIndex) {
    return SequenceUtils.countOfAny(this, chars, fromIndex);
  }

  @Override
  public final int countOfAny(CharPredicate chars) {
    return SequenceUtils.countOfAny(this, chars);
  }

  @Override
  public final int countOfAnyNot(CharPredicate chars, int fromIndex) {
    return SequenceUtils.countOfAnyNot(this, chars, fromIndex);
  }

  @Override
  public final int countOfAnyNot(CharPredicate chars) {
    return SequenceUtils.countOfAnyNot(this, chars);
  }

  @Override
  public final int countOfAny(CharPredicate s, int fromIndex, int endIndex) {
    return SequenceUtils.countOfAny(this, s, fromIndex, endIndex);
  }

  @Override
  public final int countOfAnyNot(CharPredicate chars, int startIndex, int endIndex) {
    return SequenceUtils.countOfAnyNot(this, chars, startIndex, endIndex);
  }

  @Override
  public final int countLeading(CharPredicate chars) {
    return SequenceUtils.countLeading(this, chars);
  }

  @Override
  public final int countLeading(CharPredicate chars, int fromIndex) {
    return SequenceUtils.countLeading(this, chars, fromIndex);
  }

  @Override
  public final int countLeadingNot(CharPredicate chars) {
    return SequenceUtils.countLeadingNot(this, chars);
  }

  @Override
  public final int countLeadingNot(CharPredicate chars, int fromIndex) {
    return SequenceUtils.countLeadingNot(this, chars, fromIndex);
  }

  @Override
  public final int countTrailing(CharPredicate chars) {
    return SequenceUtils.countTrailing(this, chars);
  }

  @Override
  public final int countTrailing(CharPredicate chars, int fromIndex) {
    return SequenceUtils.countTrailing(this, chars, fromIndex);
  }

  @Override
  public final int countTrailingNot(CharPredicate chars) {
    return SequenceUtils.countTrailingNot(this, chars);
  }

  @Override
  public final int countTrailingNot(CharPredicate chars, int fromIndex) {
    return SequenceUtils.countTrailingNot(this, chars, fromIndex);
  }

  @Override
  public final int countLeadingNot(CharPredicate chars, int startIndex, int endIndex) {
    return SequenceUtils.countLeadingNot(this, chars, startIndex, endIndex);
  }

  @Override
  public final int countTrailingNot(CharPredicate chars, int startIndex, int endIndex) {
    return SequenceUtils.countTrailingNot(this, chars, startIndex, endIndex);
  }

  @Override
  public final int countLeading(CharPredicate chars, int fromIndex, int endIndex) {
    return SequenceUtils.countLeading(this, chars, fromIndex, endIndex);
  }

  @Override
  public final int countLeadingColumns(int startColumn, CharPredicate chars) {
    return SequenceUtils.countLeadingColumns(this, startColumn, chars);
  }

  @Override
  public final int countTrailing(CharPredicate chars, int startIndex, int fromIndex) {
    return SequenceUtils.countTrailing(this, chars, startIndex, fromIndex);
  }

  @Override
  public final int countLeadingSpace() {
    return SequenceUtils.countLeadingSpace(this);
  }

  @Override
  public final int countLeadingNotSpace() {
    return SequenceUtils.countLeadingNotSpace(this);
  }

  @Override
  public final int countLeadingSpace(int startIndex) {
    return SequenceUtils.countLeadingSpace(this, startIndex);
  }

  @Override
  public final int countLeadingNotSpace(int startIndex) {
    return SequenceUtils.countLeadingNotSpace(this, startIndex);
  }

  @Override
  public final int countLeadingSpace(int startIndex, int endIndex) {
    return SequenceUtils.countLeadingSpace(this, startIndex, endIndex);
  }

  @Override
  public final int countLeadingNotSpace(int startIndex, int endIndex) {
    return SequenceUtils.countLeadingNotSpace(this, startIndex, endIndex);
  }

  @Override
  public final int countTrailingSpace() {
    return SequenceUtils.countTrailingSpace(this);
  }

  @Override
  public final int countTrailingNotSpace() {
    return SequenceUtils.countTrailingNotSpace(this);
  }

  @Override
  public final int countTrailingSpace(int fromIndex) {
    return SequenceUtils.countTrailingSpace(this, fromIndex);
  }

  @Override
  public final int countTrailingNotSpace(int fromIndex) {
    return SequenceUtils.countTrailingNotSpace(this, fromIndex);
  }

  @Override
  public final int countTrailingSpace(int startIndex, int fromIndex) {
    return SequenceUtils.countTrailingSpace(this, startIndex, fromIndex);
  }

  @Override
  public final int countTrailingNotSpace(int startIndex, int fromIndex) {
    return SequenceUtils.countTrailingNotSpace(this, startIndex, fromIndex);
  }

  @Override
  public final int countLeadingSpaceTab() {
    return SequenceUtils.countLeadingSpaceTab(this);
  }

  @Override
  public final int countLeadingNotSpaceTab() {
    return SequenceUtils.countLeadingNotSpaceTab(this);
  }

  @Override
  public final int countLeadingSpaceTab(int startIndex) {
    return SequenceUtils.countLeadingSpaceTab(this, startIndex);
  }

  @Override
  public final int countLeadingNotSpaceTab(int startIndex) {
    return SequenceUtils.countLeadingNotSpaceTab(this, startIndex);
  }

  @Override
  public final int countLeadingSpaceTab(int startIndex, int endIndex) {
    return SequenceUtils.countLeadingSpaceTab(this, startIndex, endIndex);
  }

  @Override
  public final int countLeadingNotSpaceTab(int startIndex, int endIndex) {
    return SequenceUtils.countLeadingNotSpaceTab(this, startIndex, endIndex);
  }

  @Override
  public final int countTrailingSpaceTab() {
    return SequenceUtils.countTrailingSpaceTab(this);
  }

  @Override
  public final int countTrailingNotSpaceTab() {
    return SequenceUtils.countTrailingNotSpaceTab(this);
  }

  @Override
  public final int countTrailingSpaceTab(int fromIndex) {
    return SequenceUtils.countTrailingSpaceTab(this, fromIndex);
  }

  @Override
  public final int countTrailingNotSpaceTab(int fromIndex) {
    return SequenceUtils.countTrailingNotSpaceTab(this, fromIndex);
  }

  @Override
  public final int countTrailingSpaceTab(int startIndex, int fromIndex) {
    return SequenceUtils.countTrailingSpaceTab(this, startIndex, fromIndex);
  }

  @Override
  public final int countTrailingNotSpaceTab(int startIndex, int fromIndex) {
    return SequenceUtils.countTrailingNotSpaceTab(this, startIndex, fromIndex);
  }

  @Override
  public final int countLeadingWhitespace() {
    return SequenceUtils.countLeadingWhitespace(this);
  }

  @Override
  public final int countLeadingNotWhitespace() {
    return SequenceUtils.countLeadingNotWhitespace(this);
  }

  @Override
  public final int countLeadingWhitespace(int startIndex) {
    return SequenceUtils.countLeadingWhitespace(this, startIndex);
  }

  @Override
  public final int countLeadingNotWhitespace(int startIndex) {
    return SequenceUtils.countLeadingNotWhitespace(this, startIndex);
  }

  @Override
  public final int countLeadingWhitespace(int startIndex, int endIndex) {
    return SequenceUtils.countLeadingWhitespace(this, startIndex, endIndex);
  }

  @Override
  public final int countLeadingNotWhitespace(int startIndex, int endIndex) {
    return SequenceUtils.countLeadingNotWhitespace(this, startIndex, endIndex);
  }

  @Override
  public final int countTrailingWhitespace() {
    return SequenceUtils.countTrailingWhitespace(this);
  }

  @Override
  public final int countTrailingNotWhitespace() {
    return SequenceUtils.countTrailingNotWhitespace(this);
  }

  @Override
  public final int countTrailingWhitespace(int fromIndex) {
    return SequenceUtils.countTrailingWhitespace(this, fromIndex);
  }

  @Override
  public final int countTrailingNotWhitespace(int fromIndex) {
    return SequenceUtils.countTrailingNotWhitespace(this, fromIndex);
  }

  @Override
  public final int countTrailingWhitespace(int startIndex, int fromIndex) {
    return SequenceUtils.countTrailingWhitespace(this, startIndex, fromIndex);
  }

  @Override
  public final int countTrailingNotWhitespace(int startIndex, int fromIndex) {
    return SequenceUtils.countTrailingNotWhitespace(this, startIndex, fromIndex);
  }

  @Override
  public final T trimStart(CharPredicate chars) {
    return subSequence(trimStartRange(0, chars));
  }

  @Override
  public final T trimmedStart(CharPredicate chars) {
    return trimmedStart(0, chars);
  }

  @Override
  public final T trimEnd(CharPredicate chars) {
    return trimEnd(0, chars);
  }

  @Override
  public final T trimmedEnd(CharPredicate chars) {
    return trimmedEnd(0, chars);
  }

  @Override
  public final T trim(CharPredicate chars) {
    return trim(0, chars);
  }

  @Override
  public final Pair<T, T> trimmed(CharPredicate chars) {
    return trimmed(0, chars);
  }

  @Override
  public final T trimStart(int keep) {
    return trimStart(keep, CharPredicate.WHITESPACE);
  }

  @Override
  public final T trimmedStart(int keep) {
    return trimmedStart(keep, CharPredicate.WHITESPACE);
  }

  @Override
  public final T trimEnd(int keep) {
    return trimEnd(keep, CharPredicate.WHITESPACE);
  }

  @Override
  public final T trimmedEnd(int keep) {
    return trimmedEnd(keep, CharPredicate.WHITESPACE);
  }

  @Override
  public final T trim(int keep) {
    return trim(keep, CharPredicate.WHITESPACE);
  }

  @Override
  public final Pair<T, T> trimmed(int keep) {
    return trimmed(keep, CharPredicate.WHITESPACE);
  }

  @Override
  public final T trimStart() {
    return trimStart(0, CharPredicate.WHITESPACE);
  }

  @Override
  public final T trimmedStart() {
    return trimmedStart(0, CharPredicate.WHITESPACE);
  }

  @Override
  public final T trimEnd() {
    return trimEnd(0, CharPredicate.WHITESPACE);
  }

  @Override
  public final T trimmedEnd() {
    return trimmedEnd(0, CharPredicate.WHITESPACE);
  }

  @Override
  public final T trim() {
    return trim(0, CharPredicate.WHITESPACE);
  }

  @Override
  public final Pair<T, T> trimmed() {
    return trimmed(0, CharPredicate.WHITESPACE);
  }

  @Override
  public final T trimStart(int keep, CharPredicate chars) {
    return subSequence(trimStartRange(keep, chars));
  }

  @Override
  public final T trimmedStart(int keep, CharPredicate chars) {
    return subSequenceBefore(trimStartRange(keep, chars));
  }

  @Override
  public final T trimEnd(int keep, CharPredicate chars) {
    return subSequence(trimEndRange(keep, chars));
  }

  @Override
  public final T trimmedEnd(int keep, CharPredicate chars) {
    return subSequenceAfter(trimEndRange(keep, chars));
  }

  @Override
  public final T trim(int keep, CharPredicate chars) {
    return subSequence(trimRange(keep, chars));
  }

  @Override
  public final Pair<T, T> trimmed(int keep, CharPredicate chars) {
    return subSequenceBeforeAfter(trimRange(keep, chars));
  }

  @Override
  public final Range trimStartRange(int keep, CharPredicate chars) {
    return SequenceUtils.trimStartRange(this, keep, chars);
  }

  @Override
  public final Range trimEndRange(int keep, CharPredicate chars) {
    return SequenceUtils.trimEndRange(this, keep, chars);
  }

  @Override
  public final Range trimRange(int keep, CharPredicate chars) {
    return SequenceUtils.trimRange(this, keep, chars);
  }

  @Override
  public final Range trimStartRange(CharPredicate chars) {
    return SequenceUtils.trimStartRange(this, chars);
  }

  @Override
  public final Range trimEndRange(CharPredicate chars) {
    return SequenceUtils.trimEndRange(this, chars);
  }

  @Override
  public final Range trimRange(CharPredicate chars) {
    return SequenceUtils.trimRange(this, chars);
  }

  @Override
  public final Range trimStartRange(int keep) {
    return SequenceUtils.trimStartRange(this, keep);
  }

  @Override
  public final Range trimEndRange(int keep) {
    return SequenceUtils.trimEndRange(this, keep);
  }

  @Override
  public final Range trimRange(int keep) {
    return SequenceUtils.trimRange(this, keep);
  }

  @Override
  public final Range trimStartRange() {
    return SequenceUtils.trimStartRange(this);
  }

  @Override
  public final Range trimEndRange() {
    return SequenceUtils.trimEndRange(this);
  }

  @Override
  public final Range trimRange() {
    return SequenceUtils.trimRange(this);
  }

  @Override
  public final T padding(int length, char pad) {
    return length <= length()
        ? nullSequence()
        : sequenceOf(RepeatedSequence.repeatOf(pad, length - length()));
  }

  @Override
  public final T padding(int length) {
    return padStart(length, ' ');
  }

  @Override
  public T padStart(int length, char pad) {
    T padding = padding(length, pad);
    return padding.isEmpty() ? (T) this : getBuilder().append(padding).append(this).toSequence();
  }

  @Override
  public T padEnd(int length, char pad) {
    T padding = padding(length, pad);
    return padding.isEmpty() ? (T) this : getBuilder().append(this).append(padding).toSequence();
  }

  @Override
  public final int eolEndLength() {
    return SequenceUtils.eolEndLength(this);
  }

  @Override
  public final int eolEndLength(int eolEnd) {
    return SequenceUtils.eolEndLength(this, eolEnd);
  }

  @Override
  public final int eolStartLength(int eolStart) {
    return SequenceUtils.eolStartLength(this, eolStart);
  }

  @Override
  public final int endOfLine(int index) {
    return SequenceUtils.endOfLine(this, index);
  }

  @Override
  public final int endOfLineAnyEOL(int index) {
    return SequenceUtils.endOfLineAnyEOL(this, index);
  }

  @Override
  public final int startOfLine(int index) {
    return SequenceUtils.startOfLine(this, index);
  }

  @Override
  public final int startOfLineAnyEOL(int index) {
    return SequenceUtils.startOfLineAnyEOL(this, index);
  }

  @Override
  public final int startOfDelimitedByAnyNot(CharPredicate s, int index) {
    return startOfDelimitedByAny(s.negate(), index);
  }

  @Override
  public final int endOfDelimitedByAnyNot(CharPredicate s, int index) {
    return endOfDelimitedByAny(s.negate(), index);
  }

  @Override
  public final int startOfDelimitedBy(CharSequence s, int index) {
    return SequenceUtils.startOfDelimitedBy(this, s, index);
  }

  @Override
  public final int startOfDelimitedByAny(CharPredicate s, int index) {
    return SequenceUtils.startOfDelimitedByAny(this, s, index);
  }

  @Override
  public final int endOfDelimitedBy(CharSequence s, int index) {
    return SequenceUtils.endOfDelimitedBy(this, s, index);
  }

  @Override
  public final int endOfDelimitedByAny(CharPredicate s, int index) {
    return SequenceUtils.endOfDelimitedByAny(this, s, index);
  }

  @Override
  public final Range lineRangeAt(int index) {
    return SequenceUtils.lineRangeAt(this, index);
  }

  @Override
  public final Range lineRangeAtAnyEOL(int index) {
    return SequenceUtils.lineRangeAtAnyEOL(this, index);
  }

  @Override
  public final T lineAt(int index) {
    return subSequence(lineRangeAt(index));
  }

  @Override
  public final T lineAtAnyEOL(int index) {
    return subSequence(lineRangeAtAnyEOL(index));
  }

  @Override
  public final Range eolEndRange(int eolEnd) {
    return SequenceUtils.eolEndRange(this, eolEnd);
  }

  @Override
  public Range eolStartRange(int eolStart) {
    return SequenceUtils.eolStartRange(this, eolStart);
  }

  @Override
  public final T trimEOL() {
    int eolLength = eolEndLength();
    return eolLength > 0 ? subSequence(0, length() - eolLength) : (T) this;
  }

  @Override
  public final T trimmedEOL() {
    int eolLength = eolEndLength();
    return eolLength > 0 ? subSequence(length() - eolLength) : nullSequence();
  }

  @Override
  public final T trimTailBlankLines() {
    Range range = trailingBlankLinesRange();
    return range.isNull() ? (T) this : subSequenceBefore(range);
  }

  @Override
  public final T trimLeadBlankLines() {
    Range range = leadingBlankLinesRange();
    return range.isNull() ? (T) this : subSequenceAfter(range);
  }

  @Override
  public final Range leadingBlankLinesRange() {
    return SequenceUtils.leadingBlankLinesRange(this);
  }

  @Override
  public final Range leadingBlankLinesRange(int startIndex) {
    return SequenceUtils.leadingBlankLinesRange(this, startIndex);
  }

  @Override
  public final Range leadingBlankLinesRange(int fromIndex, int endIndex) {
    return SequenceUtils.leadingBlankLinesRange(this, fromIndex, endIndex);
  }

  @Override
  public final Range trailingBlankLinesRange() {
    return SequenceUtils.trailingBlankLinesRange(this);
  }

  @Override
  public final Range trailingBlankLinesRange(int fromIndex) {
    return SequenceUtils.trailingBlankLinesRange(this, fromIndex);
  }

  @Override
  public final Range trailingBlankLinesRange(int startIndex, int fromIndex) {
    return SequenceUtils.trailingBlankLinesRange(this, startIndex, fromIndex);
  }

  @Override
  public final Range trailingBlankLinesRange(
      CharPredicate eolChars, int startIndex, int fromIndex) {
    return SequenceUtils.trailingBlankLinesRange(this, eolChars, startIndex, fromIndex);
  }

  @Override
  public final Range leadingBlankLinesRange(CharPredicate eolChars, int fromIndex, int endIndex) {
    return SequenceUtils.leadingBlankLinesRange(this, eolChars, fromIndex, endIndex);
  }

  @Override
  public final List<Range> blankLinesRemovedRanges() {
    return SequenceUtils.blankLinesRemovedRanges(this);
  }

  @Override
  public final List<Range> blankLinesRemovedRanges(int fromIndex) {
    return SequenceUtils.blankLinesRemovedRanges(this, fromIndex);
  }

  @Override
  public final List<Range> blankLinesRemovedRanges(int fromIndex, int endIndex) {
    return SequenceUtils.blankLinesRemovedRanges(this, fromIndex, endIndex);
  }

  @Override
  public final List<Range> blankLinesRemovedRanges(
      CharPredicate eolChars, int fromIndex, int endIndex) {
    return SequenceUtils.blankLinesRemovedRanges(this, eolChars, fromIndex, endIndex);
  }

  @Override
  public T trimToEndOfLine(boolean includeEol) {
    return trimToEndOfLine(CharPredicate.EOL, includeEol, 0);
  }

  @Override
  public T trimToEndOfLine(int index) {
    return trimToEndOfLine(CharPredicate.EOL, false, index);
  }

  @Override
  public T trimToEndOfLine() {
    return trimToEndOfLine(CharPredicate.EOL, false, 0);
  }

  @Override
  public T trimToEndOfLine(boolean includeEol, int index) {
    return trimToEndOfLine(CharPredicate.EOL, includeEol, index);
  }

  @Override
  public T trimToStartOfLine(boolean includeEol) {
    return trimToStartOfLine(CharPredicate.EOL, includeEol, 0);
  }

  @Override
  public T trimToStartOfLine(int index) {
    return trimToStartOfLine(CharPredicate.EOL, false, index);
  }

  @Override
  public T trimToStartOfLine() {
    return trimToStartOfLine(CharPredicate.EOL, false, 0);
  }

  @Override
  public T trimToStartOfLine(boolean includeEol, int index) {
    return trimToStartOfLine(CharPredicate.EOL, includeEol, index);
  }

  @Override
  public T trimToEndOfLine(CharPredicate eolChars, boolean includeEol, int index) {
    int eolPos = endOfDelimitedByAny(eolChars, index);
    if (eolPos < length()) {
      int endIndex = includeEol ? eolPos + eolStartLength(eolPos) : eolPos;
      return subSequence(0, endIndex);
    }
    return (T) this;
  }

  @Override
  public T trimToStartOfLine(CharPredicate eolChars, boolean includeEol, int index) {
    int eolPos = startOfDelimitedByAny(eolChars, index);
    if (eolPos > 0) {
      int startIndex = includeEol ? eolPos - eolEndLength(eolPos - 1) : eolPos;
      return subSequence(startIndex);
    }
    return (T) this;
  }

  @Override
  public final T ifNull(T other) {
    return isNull() ? other : (T) this;
  }

  @Override
  public final T ifNullEmptyAfter(T other) {
    return isNull() ? other.subSequence(other.length(), other.length()) : (T) this;
  }

  @Override
  public final T ifNullEmptyBefore(T other) {
    return isNull() ? other.subSequence(0, 0) : (T) this;
  }

  @Override
  public final T nullIfEmpty() {
    return isEmpty() ? nullSequence() : (T) this;
  }

  @Override
  public final T nullIfBlank() {
    return isBlank() ? nullSequence() : (T) this;
  }

  @Override
  public final T nullIf(boolean condition) {
    return condition ? nullSequence() : (T) this;
  }

  @Override
  public final T nullIfNot(
      BiPredicate<? super T, ? super CharSequence> predicate, CharSequence... matches) {
    return nullIf(predicate.negate(), matches);
  }

  @Override
  public final T nullIf(Predicate<? super CharSequence> predicate, CharSequence... matches) {
    return nullIf((o1, o2) -> predicate.test(o2), matches);
  }

  @Override
  public final T nullIfNot(Predicate<? super CharSequence> predicate, CharSequence... matches) {
    return nullIfNot((o1, o2) -> predicate.test(o2), matches);
  }

  @Override
  public final T nullIf(CharSequence... matches) {
    return nullIf((Predicate<? super CharSequence>) this::matches, matches);
  }

  @Override
  public final T nullIfNot(CharSequence... matches) {
    return nullIfNot((Predicate<? super CharSequence>) this::matches, matches);
  }

  @Override
  public final T nullIfStartsWith(CharSequence... matches) {
    return nullIf((Predicate<? super CharSequence>) this::startsWith, matches);
  }

  @Override
  public final T nullIfNotStartsWith(CharSequence... matches) {
    return nullIfNot((Predicate<? super CharSequence>) this::startsWith, matches);
  }

  @Override
  public final T nullIfEndsWith(CharSequence... matches) {
    return nullIf((Predicate<? super CharSequence>) this::endsWith, matches);
  }

  @Override
  public final T nullIfNotEndsWith(CharSequence... matches) {
    return nullIfNot((Predicate<? super CharSequence>) this::endsWith, matches);
  }

  @Override
  public final T nullIfStartsWithIgnoreCase(CharSequence... matches) {
    return nullIf((Predicate<? super CharSequence>) this::startsWithIgnoreCase, matches);
  }

  @Override
  public final T nullIfNotStartsWithIgnoreCase(CharSequence... matches) {
    return nullIfNot((Predicate<? super CharSequence>) this::startsWithIgnoreCase, matches);
  }

  @Override
  public final T nullIfEndsWithIgnoreCase(CharSequence... matches) {
    return nullIf((Predicate<? super CharSequence>) this::endsWithIgnoreCase, matches);
  }

  @Override
  public final T nullIfNotEndsWithIgnoreCase(CharSequence... matches) {
    return nullIfNot((Predicate<? super CharSequence>) this::endsWithIgnoreCase, matches);
  }

  @Override
  public final T nullIfStartsWith(boolean ignoreCase, CharSequence... matches) {
    return nullIf(
        (Predicate<? super CharSequence>) prefix -> startsWith(prefix, ignoreCase), matches);
  }

  @Override
  public final T nullIfNotStartsWith(boolean ignoreCase, CharSequence... matches) {
    return nullIfNot(
        (Predicate<? super CharSequence>) prefix -> startsWith(prefix, ignoreCase), matches);
  }

  @Override
  public final T nullIfEndsWith(boolean ignoreCase, CharSequence... matches) {
    return nullIf(
        (Predicate<? super CharSequence>) suffix -> endsWith(suffix, ignoreCase), matches);
  }

  @Override
  public final T nullIfNotEndsWith(boolean ignoreCase, CharSequence... matches) {
    return nullIfNot(
        (Predicate<? super CharSequence>) suffix -> endsWith(suffix, ignoreCase), matches);
  }

  @Override
  public final T nullIf(
      BiPredicate<? super T, ? super CharSequence> predicate, CharSequence... matches) {
    for (CharSequence match : matches) {
      if (predicate.test((T) this, match)) {
        return nullSequence();
      }
    }
    return (T) this;
  }

  @Override
  public final boolean isNull() {
    return this == nullSequence();
  }

  @Override
  public final boolean isNotNull() {
    return this != nullSequence();
  }

  @Override
  public final boolean isEmpty() {
    return SequenceUtils.isEmpty(this);
  }

  @Override
  public final boolean isBlank() {
    return SequenceUtils.isBlank(this);
  }

  @Override
  public final boolean isNotEmpty() {
    return SequenceUtils.isNotEmpty(this);
  }

  @Override
  public final boolean isNotBlank() {
    return SequenceUtils.isNotBlank(this);
  }

  @Override
  public final boolean endsWith(CharSequence suffix) {
    return SequenceUtils.endsWith(this, suffix);
  }

  @Override
  public final boolean endsWith(CharSequence suffix, boolean ignoreCase) {
    return SequenceUtils.endsWith(this, suffix, ignoreCase);
  }

  @Override
  public final boolean startsWith(CharSequence prefix) {
    return SequenceUtils.startsWith(this, prefix);
  }

  @Override
  public final boolean startsWith(CharSequence prefix, boolean ignoreCase) {
    return SequenceUtils.startsWith(this, prefix, ignoreCase);
  }

  @Override
  public final boolean endsWith(CharPredicate chars) {
    return SequenceUtils.endsWith(this, chars);
  }

  @Override
  public final boolean startsWith(CharPredicate chars) {
    return SequenceUtils.startsWith(this, chars);
  }

  @Override
  public final boolean endsWithEOL() {
    return SequenceUtils.endsWithEOL(this);
  }

  @Override
  public final boolean endsWithAnyEOL() {
    return SequenceUtils.endsWithAnyEOL(this);
  }

  @Override
  public final boolean endsWithSpace() {
    return SequenceUtils.endsWithSpace(this);
  }

  @Override
  public final boolean endsWithSpaceTab() {
    return SequenceUtils.endsWithSpaceTab(this);
  }

  @Override
  public final boolean endsWithWhitespace() {
    return SequenceUtils.endsWithWhitespace(this);
  }

  @Override
  public final boolean startsWithEOL() {
    return SequenceUtils.startsWithEOL(this);
  }

  @Override
  public final boolean startsWithAnyEOL() {
    return SequenceUtils.startsWithAnyEOL(this);
  }

  @Override
  public final boolean startsWithSpace() {
    return SequenceUtils.startsWithSpace(this);
  }

  @Override
  public final boolean startsWithSpaceTab() {
    return SequenceUtils.startsWithSpaceTab(this);
  }

  @Override
  public final boolean startsWithWhitespace() {
    return SequenceUtils.startsWithWhitespace(this);
  }

  @Override
  public final T removeSuffix(CharSequence suffix) {
    return !endsWith(suffix) ? (T) this : subSequence(0, length() - suffix.length());
  }

  @Override
  public final T removePrefix(CharSequence prefix) {
    return !startsWith(prefix) ? (T) this : subSequence(prefix.length(), length());
  }

  @Override
  public final T removeProperSuffix(CharSequence suffix) {
    return length() <= suffix.length() || !endsWith(suffix)
        ? (T) this
        : subSequence(0, length() - suffix.length());
  }

  @Override
  public final T removeProperPrefix(CharSequence prefix) {
    return length() <= prefix.length() || !startsWith(prefix)
        ? (T) this
        : subSequence(prefix.length(), length());
  }

  @Override
  public final boolean endsWithIgnoreCase(CharSequence suffix) {
    return length() > 0 && matchCharsReversed(suffix, length() - 1, true);
  }

  @Override
  public final boolean startsWithIgnoreCase(CharSequence prefix) {
    return length() > 0 && matchChars(prefix, 0, true);
  }

  @Override
  public final T removeSuffixIgnoreCase(CharSequence suffix) {
    return !endsWithIgnoreCase(suffix) ? (T) this : subSequence(0, length() - suffix.length());
  }

  @Override
  public final T removePrefixIgnoreCase(CharSequence prefix) {
    return !startsWithIgnoreCase(prefix) ? (T) this : subSequence(prefix.length(), length());
  }

  @Override
  public final T removeProperSuffixIgnoreCase(CharSequence suffix) {
    return length() <= suffix.length() || !endsWithIgnoreCase(suffix)
        ? (T) this
        : subSequence(0, length() - suffix.length());
  }

  @Override
  public final T removeProperPrefixIgnoreCase(CharSequence prefix) {
    return length() <= prefix.length() || !startsWithIgnoreCase(prefix)
        ? (T) this
        : subSequence(prefix.length(), length());
  }

  @Override
  public final T removeSuffix(CharSequence suffix, boolean ignoreCase) {
    return !endsWith(suffix, ignoreCase) ? (T) this : subSequence(0, length() - suffix.length());
  }

  @Override
  public final T removePrefix(CharSequence prefix, boolean ignoreCase) {
    return !startsWith(prefix, ignoreCase) ? (T) this : subSequence(prefix.length(), length());
  }

  @Override
  public final T removeProperSuffix(CharSequence suffix, boolean ignoreCase) {
    return length() <= suffix.length() || !endsWith(suffix, ignoreCase)
        ? (T) this
        : subSequence(0, length() - suffix.length());
  }

  @Override
  public final T removeProperPrefix(CharSequence prefix, boolean ignoreCase) {
    return length() <= prefix.length() || !startsWith(prefix, ignoreCase)
        ? (T) this
        : subSequence(prefix.length(), length());
  }

  @Override
  public T insert(int index, CharSequence chars) {
    index = Math.max(0, Math.min(length(), index));

    if (chars.length() == 0) {
      return (T) this;
    } else if (index == 0) {
      return prefixWith(chars);
    } else if (index == length()) {
      return suffixWith(chars);
    } else {
      return getBuilder()
          .add(subSequence(0, index))
          .add(chars)
          .add(subSequence(index))
          .toSequence();
    }
  }

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

  @Override
  public final T toLowerCase() {
    return toMapped(ChangeCase.toLowerCase);
  }

  @Override
  public final T toUpperCase() {
    return toMapped(ChangeCase.toUpperCase);
  }

  @Override
  public final T toNbSp() {
    return toMapped(SpaceMapper.toNonBreakSpace);
  }

  @Override
  public final T toSpc() {
    return toMapped(SpaceMapper.fromNonBreakSpace);
  }

  @Override
  public final boolean matches(CharSequence chars, boolean ignoreCase) {
    return SequenceUtils.matches(this, chars, ignoreCase);
  }

  @Override
  public final boolean matches(CharSequence chars) {
    return SequenceUtils.matches(this, chars);
  }

  @Override
  public final boolean matchesIgnoreCase(CharSequence chars) {
    return SequenceUtils.matchesIgnoreCase(this, chars);
  }

  @Override
  public final boolean matchChars(CharSequence chars, int startIndex, boolean ignoreCase) {
    return SequenceUtils.matchChars(this, chars, startIndex, ignoreCase);
  }

  @Override
  public final boolean matchChars(CharSequence chars, int startIndex) {
    return SequenceUtils.matchChars(this, chars, startIndex);
  }

  @Override
  public final boolean matchCharsIgnoreCase(CharSequence chars, int startIndex) {
    return SequenceUtils.matchCharsIgnoreCase(this, chars, startIndex);
  }

  @Override
  public final boolean matchChars(CharSequence chars, boolean ignoreCase) {
    return SequenceUtils.matchChars(this, chars, ignoreCase);
  }

  @Override
  public final boolean matchChars(CharSequence chars) {
    return SequenceUtils.matchChars(this, chars);
  }

  @Override
  public final boolean matchCharsIgnoreCase(CharSequence chars) {
    return SequenceUtils.matchCharsIgnoreCase(this, chars);
  }

  @Override
  public final boolean matchCharsReversed(CharSequence chars, int endIndex, boolean ignoreCase) {
    return SequenceUtils.matchCharsReversed(this, chars, endIndex, ignoreCase);
  }

  @Override
  public final boolean matchCharsReversed(CharSequence chars, int endIndex) {
    return SequenceUtils.matchCharsReversed(this, chars, endIndex);
  }

  @Override
  public final boolean matchCharsReversedIgnoreCase(CharSequence chars, int endIndex) {
    return SequenceUtils.matchCharsReversedIgnoreCase(this, chars, endIndex);
  }

  @Override
  public final int matchedCharCount(
      CharSequence chars, int startIndex, int endIndex, boolean ignoreCase) {
    return SequenceUtils.matchedCharCount(this, chars, startIndex, endIndex, ignoreCase);
  }

  @Override
  public final int matchedCharCount(CharSequence chars, int startIndex, boolean ignoreCase) {
    return SequenceUtils.matchedCharCount(this, chars, startIndex, ignoreCase);
  }

  @Override
  public final int matchedCharCount(CharSequence chars, int startIndex, int endIndex) {
    return SequenceUtils.matchedCharCount(this, chars, startIndex, endIndex);
  }

  @Override
  public final int matchedCharCount(CharSequence chars, int startIndex) {
    return SequenceUtils.matchedCharCount(this, chars, startIndex);
  }

  @Override
  public final int matchedCharCountIgnoreCase(CharSequence chars, int startIndex) {
    return SequenceUtils.matchedCharCountIgnoreCase(this, chars, startIndex);
  }

  @Override
  public final int matchedCharCountIgnoreCase(CharSequence chars, int startIndex, int endIndex) {
    return SequenceUtils.matchedCharCountIgnoreCase(this, chars, startIndex, endIndex);
  }

  @Override
  public final int matchedCharCountReversedIgnoreCase(
      CharSequence chars, int startIndex, int fromIndex) {
    return SequenceUtils.matchedCharCountReversedIgnoreCase(this, chars, startIndex, fromIndex);
  }

  @Override
  public final int matchedCharCountReversed(CharSequence chars, int startIndex, int fromIndex) {
    return SequenceUtils.matchedCharCountReversed(this, chars, startIndex, fromIndex);
  }

  @Override
  public final int matchedCharCountReversed(CharSequence chars, int fromIndex, boolean ignoreCase) {
    return SequenceUtils.matchedCharCountReversed(this, chars, fromIndex, ignoreCase);
  }

  @Override
  public final int matchedCharCountReversed(CharSequence chars, int fromIndex) {
    return SequenceUtils.matchedCharCountReversed(this, chars, fromIndex);
  }

  @Override
  public final int matchedCharCountReversedIgnoreCase(CharSequence chars, int fromIndex) {
    return SequenceUtils.matchedCharCountReversedIgnoreCase(this, chars, fromIndex);
  }

  @Override
  public final int matchedCharCount(
      CharSequence chars, int startIndex, int endIndex, boolean fullMatchOnly, boolean ignoreCase) {
    return SequenceUtils.matchedCharCount(
        this, chars, startIndex, endIndex, fullMatchOnly, ignoreCase);
  }

  @Override
  public final int matchedCharCountReversed(
      CharSequence chars, int startIndex, int fromIndex, boolean ignoreCase) {
    return SequenceUtils.matchedCharCountReversed(this, chars, startIndex, fromIndex, ignoreCase);
  }

  @Override
  public String toString() {
    int iMax = length();
    StringBuilder sb = new StringBuilder(iMax);

    for (int i = 0; i < iMax; i++) {
      sb.append(charAt(i));
    }

    return sb.toString();
  }

  @Override
  public final String normalizeEOL() {
    return Escaping.normalizeEOL(toString());
  }

  @Override
  public final String normalizeEndWithEOL() {
    return Escaping.normalizeEndWithEOL(toString());
  }

  @Override
  public final String toVisibleWhitespaceString() {
    return SequenceUtils.toVisibleWhitespaceString(this);
  }

  @Override
  public final List<T> splitList(CharSequence delimiter) {
    return SequenceUtils.splitList((T) this, delimiter, 0, 0, null);
  }

  @Override
  public final List<T> splitList(
      CharSequence delimiter, int limit, boolean includeDelims, CharPredicate trimChars) {
    return SequenceUtils.splitList(
        (T) this,
        delimiter,
        limit,
        includeDelims ? SequenceUtils.SPLIT_INCLUDE_DELIMS : 0,
        trimChars);
  }

  @Override
  public final List<T> splitList(CharSequence delimiter, int limit, int flags) {
    return SequenceUtils.splitList((T) this, delimiter, limit, flags, null);
  }

  @Override
  public final List<T> splitList(
      CharSequence delimiter, boolean includeDelims, CharPredicate trimChars) {
    return SequenceUtils.splitList(
        (T) this, delimiter, 0, includeDelims ? SequenceUtils.SPLIT_INCLUDE_DELIMS : 0, trimChars);
  }

  // NOTE: these default to including delimiters as part of split item

  @Override
  public final List<T> splitListEOL() {
    return SequenceUtils.splitList(
        (T) this, SequenceUtils.EOL, 0, SequenceUtils.SPLIT_INCLUDE_DELIMS, null);
  }

  @Override
  public final List<T> splitListEOL(boolean includeDelims) {
    return SequenceUtils.splitList(
        (T) this,
        SequenceUtils.EOL,
        0,
        includeDelims ? SequenceUtils.SPLIT_INCLUDE_DELIMS : 0,
        null);
  }

  @Override
  public final List<T> splitListEOL(boolean includeDelims, CharPredicate trimChars) {
    return SequenceUtils.splitList(
        (T) this,
        SequenceUtils.EOL,
        0,
        includeDelims ? SequenceUtils.SPLIT_INCLUDE_DELIMS : 0,
        trimChars);
  }

  @Override
  public final List<T> splitList(
      CharSequence delimiter, int limit, int flags, CharPredicate trimChars) {
    return SequenceUtils.splitList((T) this, delimiter, limit, flags, trimChars);
  }

  @Override
  public final T[] splitEOL() {
    return split(SequenceUtils.EOL, 0, SequenceUtils.SPLIT_INCLUDE_DELIMS, null);
  }

  @Override
  public final T[] splitEOL(boolean includeDelims) {
    return split(
        SequenceUtils.EOL, 0, includeDelims ? SequenceUtils.SPLIT_INCLUDE_DELIMS : 0, null);
  }

  @Override
  public final T[] split(CharSequence delimiter, boolean includeDelims, CharPredicate trimChars) {
    return split(delimiter, 0, includeDelims ? SequenceUtils.SPLIT_INCLUDE_DELIMS : 0, trimChars);
  }

  @Override
  public final T[] split(CharSequence delimiter) {
    return split(delimiter, 0, 0, null);
  }

  @Override
  public final T[] split(
      CharSequence delimiter, int limit, boolean includeDelims, CharPredicate trimChars) {
    return split(
        delimiter, limit, includeDelims ? SequenceUtils.SPLIT_INCLUDE_DELIMS : 0, trimChars);
  }

  @Override
  public final T[] split(CharSequence delimiter, int limit, int flags) {
    return split(delimiter, limit, flags, null);
  }

  @Override
  public final T[] split(CharSequence delimiter, int limit, int flags, CharPredicate trimChars) {
    return SequenceUtils.splitList((T) this, delimiter, limit, flags, trimChars)
        .toArray(emptyArray());
  }

  @Override
  public final T appendTo(StringBuilder out, CharMapper charMapper) {
    return appendTo(out, charMapper, 0, length());
  }

  @Override
  public final T appendTo(StringBuilder out, CharMapper charMapper, int startIndex) {
    return appendTo(out, charMapper, startIndex, length());
  }

  @Override
  public final T appendTo(StringBuilder out) {
    return appendTo(out, null, 0, length());
  }

  @Override
  public final T appendTo(StringBuilder out, int startIndex) {
    return appendTo(out, null, startIndex, length());
  }

  @Override
  public final T appendTo(StringBuilder out, int startIndex, int endIndex) {
    return appendTo(out, null, startIndex, endIndex);
  }

  @Override
  public final T appendTo(StringBuilder out, CharMapper charMapper, int startIndex, int endIndex) {
    CharSequence useSequence = charMapper == null ? this : toMapped(charMapper);
    out.append(useSequence, startIndex, endIndex);
    return (T) this;
  }

  @Override
  public final T appendRangesTo(StringBuilder out, CharMapper charMapper, Range... ranges) {
    return appendRangesTo(out, charMapper, new ArrayIterable<>(ranges));
  }

  @Override
  public final T appendRangesTo(StringBuilder out, Range... ranges) {
    return appendRangesTo(out, null, new ArrayIterable<>(ranges));
  }

  @Override
  public final T appendRangesTo(StringBuilder out, Iterable<? extends Range> ranges) {
    return appendRangesTo(out, null, ranges);
  }

  @Override
  public final T appendRangesTo(
      StringBuilder out, CharMapper charMapper, Iterable<? extends Range> ranges) {
    CharSequence useSequence = charMapper == null ? this : toMapped(charMapper);

    for (Range range : ranges) {
      if (range != null && range.isNotNull())
        out.append(useSequence, range.getStart(), range.getEnd());
    }
    return (T) this;
  }

  @Override
  public final int[] indexOfAll(CharSequence s) {
    return SequenceUtils.indexOfAll(this, s);
  }

  @Override
  public final T appendEOL() {
    return suffixWith(SequenceUtils.EOL);
  }

  @Override
  public final T suffixWithEOL() {
    return suffixWith(SequenceUtils.EOL);
  }

  @Override
  public final T prefixWithEOL() {
    return prefixWith(SequenceUtils.EOL);
  }

  @Override
  public final T prefixOnceWithEOL() {
    return prefixOnceWith(SequenceUtils.EOL);
  }

  @Override
  public final T suffixOnceWithEOL() {
    return suffixOnceWith(SequenceUtils.EOL);
  }

  @Override
  public final T appendSpace() {
    return suffixWith(SequenceUtils.SPACE);
  }

  @Override
  public final T suffixWithSpace() {
    return suffixWith(SequenceUtils.SPACE);
  }

  @Override
  public final T prefixWithSpace() {
    return prefixWith(SequenceUtils.SPACE);
  }

  @Override
  public final T appendSpaces(int count) {
    return suffixWith(RepeatedSequence.ofSpaces(count));
  }

  @Override
  public final T suffixWithSpaces(int count) {
    return suffixWith(RepeatedSequence.ofSpaces(count));
  }

  @Override
  public final T prefixWithSpaces(int count) {
    return prefixWith(RepeatedSequence.ofSpaces(count));
  }

  @Override
  public final T prefixOnceWithSpace() {
    return prefixOnceWith(SequenceUtils.SPACE);
  }

  @Override
  public final T suffixOnceWithSpace() {
    return suffixOnceWith(SequenceUtils.SPACE);
  }

  @Override
  public T prefixWith(CharSequence prefix) {
    return prefix == null || prefix.length() == 0
        ? (T) this
        : getBuilder().add(prefix).add(this).toSequence();
  }

  @Override
  public T suffixWith(CharSequence suffix) {
    // convoluted to allow BasedCharSequence to use PrefixedCharSequence so all fits into
    // SegmentedCharSequence
    if (suffix == null || suffix.length() == 0) {
      return (T) this;
    }
    return getBuilder().add(this).add(suffix).toSequence();
  }

  @Override
  public final T prefixOnceWith(CharSequence prefix) {
    return prefix == null || prefix.length() == 0 || startsWith(prefix)
        ? (T) this
        : prefixWith(prefix);
  }

  @Override
  public final T suffixOnceWith(CharSequence suffix) {
    return suffix == null || suffix.length() == 0 || endsWith(suffix)
        ? (T) this
        : suffixWith(suffix);
  }

  @Override
  public final T replace(int startIndex, int endIndex, CharSequence replacement) {
    int length = length();
    startIndex = Math.max(startIndex, 0);
    endIndex = Math.min(endIndex, length);

    ISequenceBuilder<?, T> segments = getBuilder();
    return segments
        .add(subSequence(0, startIndex))
        .add(replacement)
        .add(subSequence(endIndex))
        .toSequence();
  }

  @Override
  public final T replace(CharSequence find, CharSequence replace) {
    int[] indices = indexOfAll(find);
    if (indices.length == 0) {
      return (T) this;
    }
    ISequenceBuilder<?, T> segments = getBuilder();

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

  @Override
  public final T append(CharSequence... sequences) {
    return append(new ArrayIterable<>(sequences));
  }

  @Override
  public final T append(Iterable<? extends CharSequence> sequences) {
    ISequenceBuilder<?, T> segments = getBuilder();
    segments.add(this);
    for (CharSequence sequence : sequences) {
      segments.add(sequence);
    }
    return segments.toSequence();
  }

  @Override
  public final T extractRanges(Range... ranges) {
    return extractRanges(new ArrayIterable<>(ranges));
  }

  @Override
  public final T extractRanges(Iterable<Range> ranges) {
    ISequenceBuilder<?, T> segments = getBuilder();
    for (Range range : ranges) {
      if (!(range == null || range.isNull())) segments.add(range.safeSubSequence(this));
    }
    return segments.toSequence();
  }

  @Override
  public final int columnAtIndex(int index) {
    return SequenceUtils.columnAtIndex(this, index);
  }

  @Override
  public final Pair<Integer, Integer> lineColumnAtIndex(int index) {
    return SequenceUtils.lineColumnAtIndex(this, index);
  }

  @Override
  public boolean isIn(String[] texts) {
    return SequenceUtils.containedBy(texts, this);
  }

  @Override
  public boolean isIn(Collection<? extends CharSequence> texts) {
    return SequenceUtils.containedBy(texts, this);
  }
}
