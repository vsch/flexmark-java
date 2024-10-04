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
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An abstract base for RichSequence which implements most of the methods allowing subclasses to
 * implement RichSequence with minimal support methods
 */
public abstract class IRichSequenceBase<T extends IRichSequence<T>> implements IRichSequence<T> {
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
  public IRichSequenceBase(int hash) {
    this.hash = hash;
  }

  /**
   * Equality comparison based on character content of this sequence, with quick fail resorting to
   * content comparison only if length and hashCodes are equal
   *
   * @param o any char sequence
   * @return true if character contents are equal
   */
  @Override
  @Contract(pure = true, value = "null -> false")
  public final boolean equals(Object o) {
    return SequenceUtils.equals(this, o);
  }

  /**
   * String hash code computation
   *
   * @return hash code of equivalent string
   */
  @Override
  @Contract(pure = true)
  public final int hashCode() {
    int h = hash;
    if (h == 0 && length() > 0) {
      h = SequenceUtils.hashCode(this);
      hash = h;
    }
    return h;
  }

  @Override
  @Contract(pure = true, value = "null -> false")
  public final boolean equalsIgnoreCase(@Nullable Object other) {
    return (this == other)
        || (other instanceof CharSequence)
            && ((CharSequence) other).length() == length()
            && matchChars((CharSequence) other, 0, true);
  }

  @Override
  @Contract(pure = true, value = "null, _ ->false")
  public final boolean equals(@Nullable Object other, boolean ignoreCase) {
    return (this == other)
        || other instanceof CharSequence
            && ((CharSequence) other).length() == length()
            && matchChars((CharSequence) other, 0, ignoreCase);
  }

  @Override
  public int compareTo(@NotNull CharSequence o) {
    return SequenceUtils.compare(this, o);
  }

  @NotNull
  @Override
  public final T sequenceOf(@Nullable CharSequence charSequence) {
    return charSequence == null
        ? nullSequence()
        : sequenceOf(charSequence, 0, charSequence.length());
  }

  @NotNull
  @Override
  public final T sequenceOf(@Nullable CharSequence charSequence, int startIndex) {
    return charSequence == null
        ? nullSequence()
        : sequenceOf(charSequence, startIndex, charSequence.length());
  }

  @NotNull
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
  @NotNull
  public final T subSequence(@NotNull Range range) {
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
  @NotNull
  public final T subSequenceBefore(@NotNull Range range) {
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
  @NotNull
  public final T subSequenceAfter(@NotNull Range range) {
    return range.isNull() ? nullSequence() : subSequence(range.getEnd());
  }

  /**
   * Get a portions of this sequence before and after one selected by range
   *
   * @param range range to get, coordinates offset form start of this sequence
   * @return sequence whose contents come before and after the selected range, if range.isNull()
   *     then {@link #nullSequence()}
   */
  public final Pair<T, T> subSequenceBeforeAfter(Range range) {
    return new Pair<>(subSequenceBefore(range), subSequenceAfter(range));
  }

  @NotNull
  @Override
  public final T endSequence(int startIndex, int endIndex) {
    int length = length();
    int useStart = length - startIndex;
    int useEnd = length - endIndex;

    useEnd = rangeLimit(useEnd, 0, length);
    useStart = rangeLimit(useStart, 0, useEnd);
    return subSequence(useStart, useEnd);
  }

  @NotNull
  @Override
  public final T endSequence(int startIndex) {
    return endSequence(startIndex, 0);
  }

  @Override
  public final char endCharAt(int index) {
    int length = length();
    if (index < 0 || index >= length) return SequenceUtils.NUL;
    return charAt(length - index);
  }

  @NotNull
  @Override
  public final T midSequence(int startIndex, int endIndex) {
    int length = length();
    int useStart = startIndex < 0 ? length + startIndex : startIndex;
    int useEnd = endIndex < 0 ? length + endIndex : endIndex;

    useEnd = rangeLimit(useEnd, 0, length);
    useStart = rangeLimit(useStart, 0, useEnd);
    return subSequence(useStart, useEnd);
  }

  @NotNull
  @Override
  public final T midSequence(int startIndex) {
    return midSequence(startIndex, length());
  }

  @Override
  public final char midCharAt(int index) {
    int length = length();
    if (index < -length || index >= length) return SequenceUtils.NUL;
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

  public final void validateIndex(int index) {
    SequenceUtils.validateIndex(index, length());
  }

  public final void validateIndexInclusiveEnd(int index) {
    SequenceUtils.validateIndexInclusiveEnd(index, length());
  }

  public final void validateStartEnd(int startIndex, int endIndex) {
    SequenceUtils.validateStartEnd(startIndex, endIndex, length());
  }

  @Override
  public char safeCharAt(int index) {
    return index < 0 || index >= length() ? SequenceUtils.NUL : charAt(index);
  }

  @NotNull
  @Override
  public T safeSubSequence(int startIndex, int endIndex) {
    int length = length();
    startIndex = Math.max(0, Math.min(length, startIndex));
    endIndex = Math.max(startIndex, Math.min(length, endIndex));
    return subSequence(startIndex, endIndex);
  }

  @NotNull
  @Override
  public T safeSubSequence(int startIndex) {
    int length = length();
    startIndex = Math.max(0, Math.min(length, startIndex));
    return subSequence(startIndex, length);
  }

  @Override
  public boolean isCharAt(int index, @NotNull CharPredicate predicate) {
    return predicate.test(safeCharAt(index));
  }

  @Override
  public @Nullable String toStringOrNull() {
    return isNull() ? null : toString();
  }

  @Override
  public final int indexOf(@NotNull CharSequence s) {
    return SequenceUtils.indexOf(this, s);
  }

  @Override
  public final int indexOf(@NotNull CharSequence s, int fromIndex) {
    return SequenceUtils.indexOf(this, s, fromIndex);
  }

  @Override
  public final int indexOf(@NotNull CharSequence s, int fromIndex, int endIndex) {
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
  public final int indexOfAny(@NotNull CharPredicate s) {
    return SequenceUtils.indexOfAny(this, s);
  }

  @Override
  public final int indexOfAny(@NotNull CharPredicate s, int index) {
    return SequenceUtils.indexOfAny(this, s, index);
  }

  @Override
  public final int indexOfAnyNot(@NotNull CharPredicate s) {
    return SequenceUtils.indexOfAnyNot(this, s);
  }

  @Override
  public final int indexOfAnyNot(@NotNull CharPredicate s, int fromIndex) {
    return SequenceUtils.indexOfAnyNot(this, s, fromIndex);
  }

  @Override
  public final int indexOfAnyNot(@NotNull CharPredicate s, int fromIndex, int endIndex) {
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
  public final int lastIndexOf(@NotNull CharSequence s) {
    return SequenceUtils.lastIndexOf(this, s);
  }

  @Override
  public final int lastIndexOf(@NotNull CharSequence s, int fromIndex) {
    return SequenceUtils.lastIndexOf(this, s, fromIndex);
  }

  @Override
  public final int lastIndexOfAny(@NotNull CharPredicate s, int fromIndex) {
    return SequenceUtils.lastIndexOfAny(this, s, fromIndex);
  }

  @Override
  public final int lastIndexOfAny(@NotNull CharPredicate s) {
    return SequenceUtils.lastIndexOfAny(this, s);
  }

  @Override
  public final int lastIndexOfAnyNot(@NotNull CharPredicate s) {
    return SequenceUtils.lastIndexOfAnyNot(this, s);
  }

  @Override
  public final int lastIndexOfAnyNot(@NotNull CharPredicate s, int fromIndex) {
    return SequenceUtils.lastIndexOfAnyNot(this, s, fromIndex);
  }

  @Override
  public final int lastIndexOfAnyNot(@NotNull CharPredicate s, int startIndex, int fromIndex) {
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
  public final int indexOfAny(@NotNull CharPredicate s, int fromIndex, int endIndex) {
    return SequenceUtils.indexOfAny(this, s, fromIndex, endIndex);
  }

  @Override
  public final int lastIndexOf(@NotNull CharSequence s, int startIndex, int fromIndex) {
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
  public final int lastIndexOfAny(@NotNull CharPredicate s, int startIndex, int fromIndex) {
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
  public final int countOfAny(@NotNull CharPredicate chars, int fromIndex) {
    return SequenceUtils.countOfAny(this, chars, fromIndex);
  }

  @Override
  public final int countOfAny(@NotNull CharPredicate chars) {
    return SequenceUtils.countOfAny(this, chars);
  }

  @Override
  public final int countOfAnyNot(@NotNull CharPredicate chars, int fromIndex) {
    return SequenceUtils.countOfAnyNot(this, chars, fromIndex);
  }

  @Override
  public final int countOfAnyNot(@NotNull CharPredicate chars) {
    return SequenceUtils.countOfAnyNot(this, chars);
  }

  @Override
  public final int countOfAny(@NotNull CharPredicate s, int fromIndex, int endIndex) {
    return SequenceUtils.countOfAny(this, s, fromIndex, endIndex);
  }

  @Override
  public final int countOfAnyNot(@NotNull CharPredicate chars, int startIndex, int endIndex) {
    return SequenceUtils.countOfAnyNot(this, chars, startIndex, endIndex);
  }

  @Override
  public final int countLeading(@NotNull CharPredicate chars) {
    return SequenceUtils.countLeading(this, chars);
  }

  @Override
  public final int countLeading(@NotNull CharPredicate chars, int fromIndex) {
    return SequenceUtils.countLeading(this, chars, fromIndex);
  }

  @Override
  public final int countLeadingNot(@NotNull CharPredicate chars) {
    return SequenceUtils.countLeadingNot(this, chars);
  }

  @Override
  public final int countLeadingNot(@NotNull CharPredicate chars, int fromIndex) {
    return SequenceUtils.countLeadingNot(this, chars, fromIndex);
  }

  @Override
  public final int countTrailing(@NotNull CharPredicate chars) {
    return SequenceUtils.countTrailing(this, chars);
  }

  @Override
  public final int countTrailing(@NotNull CharPredicate chars, int fromIndex) {
    return SequenceUtils.countTrailing(this, chars, fromIndex);
  }

  @Override
  public final int countTrailingNot(@NotNull CharPredicate chars) {
    return SequenceUtils.countTrailingNot(this, chars);
  }

  @Override
  public final int countTrailingNot(@NotNull CharPredicate chars, int fromIndex) {
    return SequenceUtils.countTrailingNot(this, chars, fromIndex);
  }

  @Override
  public final int countLeadingNot(@NotNull CharPredicate chars, int startIndex, int endIndex) {
    return SequenceUtils.countLeadingNot(this, chars, startIndex, endIndex);
  }

  @Override
  public final int countTrailingNot(@NotNull CharPredicate chars, int startIndex, int endIndex) {
    return SequenceUtils.countTrailingNot(this, chars, startIndex, endIndex);
  }

  @Override
  public final int countLeading(@NotNull CharPredicate chars, int fromIndex, int endIndex) {
    return SequenceUtils.countLeading(this, chars, fromIndex, endIndex);
  }

  @Override
  public final int countLeadingColumns(int startColumn, @NotNull CharPredicate chars) {
    return SequenceUtils.countLeadingColumns(this, startColumn, chars);
  }

  @Override
  public final int countTrailing(@NotNull CharPredicate chars, int startIndex, int fromIndex) {
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

  @NotNull
  @Override
  public final T trimStart(@NotNull CharPredicate chars) {
    return subSequence(trimStartRange(0, chars));
  }

  @NotNull
  @Override
  public final T trimmedStart(@NotNull CharPredicate chars) {
    return trimmedStart(0, chars);
  }

  @NotNull
  @Override
  public final T trimEnd(@NotNull CharPredicate chars) {
    return trimEnd(0, chars);
  }

  @NotNull
  @Override
  public final T trimmedEnd(@NotNull CharPredicate chars) {
    return trimmedEnd(0, chars);
  }

  @NotNull
  @Override
  public final T trim(@NotNull CharPredicate chars) {
    return trim(0, chars);
  }

  @NotNull
  @Override
  public final Pair<T, T> trimmed(@NotNull CharPredicate chars) {
    return trimmed(0, chars);
  }

  @NotNull
  @Override
  public final T trimStart(int keep) {
    return trimStart(keep, CharPredicate.WHITESPACE);
  }

  @NotNull
  @Override
  public final T trimmedStart(int keep) {
    return trimmedStart(keep, CharPredicate.WHITESPACE);
  }

  @NotNull
  @Override
  public final T trimEnd(int keep) {
    return trimEnd(keep, CharPredicate.WHITESPACE);
  }

  @NotNull
  @Override
  public final T trimmedEnd(int keep) {
    return trimmedEnd(keep, CharPredicate.WHITESPACE);
  }

  @NotNull
  @Override
  public final T trim(int keep) {
    return trim(keep, CharPredicate.WHITESPACE);
  }

  @NotNull
  @Override
  public final Pair<T, T> trimmed(int keep) {
    return trimmed(keep, CharPredicate.WHITESPACE);
  }

  @NotNull
  @Override
  public final T trimStart() {
    return trimStart(0, CharPredicate.WHITESPACE);
  }

  @NotNull
  @Override
  public final T trimmedStart() {
    return trimmedStart(0, CharPredicate.WHITESPACE);
  }

  @NotNull
  @Override
  public final T trimEnd() {
    return trimEnd(0, CharPredicate.WHITESPACE);
  }

  @NotNull
  @Override
  public final T trimmedEnd() {
    return trimmedEnd(0, CharPredicate.WHITESPACE);
  }

  @NotNull
  @Override
  public final T trim() {
    return trim(0, CharPredicate.WHITESPACE);
  }

  @NotNull
  @Override
  public final Pair<T, T> trimmed() {
    return trimmed(0, CharPredicate.WHITESPACE);
  }

  @NotNull
  @Override
  public final T trimStart(int keep, @NotNull CharPredicate chars) {
    return subSequence(trimStartRange(keep, chars));
  }

  @NotNull
  @Override
  public final T trimmedStart(int keep, @NotNull CharPredicate chars) {
    return subSequenceBefore(trimStartRange(keep, chars));
  }

  @NotNull
  @Override
  public final T trimEnd(int keep, @NotNull CharPredicate chars) {
    return subSequence(trimEndRange(keep, chars));
  }

  @NotNull
  @Override
  public final T trimmedEnd(int keep, @NotNull CharPredicate chars) {
    return subSequenceAfter(trimEndRange(keep, chars));
  }

  @NotNull
  @Override
  public final T trim(int keep, @NotNull CharPredicate chars) {
    return subSequence(trimRange(keep, chars));
  }

  @NotNull
  @Override
  public final Pair<T, T> trimmed(int keep, @NotNull CharPredicate chars) {
    return subSequenceBeforeAfter(trimRange(keep, chars));
  }

  @NotNull
  @Override
  public final Range trimStartRange(int keep, @NotNull CharPredicate chars) {
    return SequenceUtils.trimStartRange(this, keep, chars);
  }

  @NotNull
  @Override
  public final Range trimEndRange(int keep, @NotNull CharPredicate chars) {
    return SequenceUtils.trimEndRange(this, keep, chars);
  }

  @NotNull
  @Override
  public final Range trimRange(int keep, @NotNull CharPredicate chars) {
    return SequenceUtils.trimRange(this, keep, chars);
  }

  @NotNull
  @Override
  public final Range trimStartRange(@NotNull CharPredicate chars) {
    return SequenceUtils.trimStartRange(this, chars);
  }

  @NotNull
  @Override
  public final Range trimEndRange(@NotNull CharPredicate chars) {
    return SequenceUtils.trimEndRange(this, chars);
  }

  @NotNull
  @Override
  public final Range trimRange(@NotNull CharPredicate chars) {
    return SequenceUtils.trimRange(this, chars);
  }

  @NotNull
  @Override
  public final Range trimStartRange(int keep) {
    return SequenceUtils.trimStartRange(this, keep);
  }

  @NotNull
  @Override
  public final Range trimEndRange(int keep) {
    return SequenceUtils.trimEndRange(this, keep);
  }

  @NotNull
  @Override
  public final Range trimRange(int keep) {
    return SequenceUtils.trimRange(this, keep);
  }

  @NotNull
  @Override
  public final Range trimStartRange() {
    return SequenceUtils.trimStartRange(this);
  }

  @NotNull
  @Override
  public final Range trimEndRange() {
    return SequenceUtils.trimEndRange(this);
  }

  @NotNull
  @Override
  public final Range trimRange() {
    return SequenceUtils.trimRange(this);
  }

  @NotNull
  @Override
  public final T padding(int length, char pad) {
    return length <= length()
        ? nullSequence()
        : sequenceOf(RepeatedSequence.repeatOf(pad, length - length()));
  }

  @NotNull
  @Override
  public final T padding(int length) {
    return padStart(length, ' ');
  }

  @NotNull
  @Override
  public T padStart(int length, char pad) {
    T padding = padding(length, pad);
    return padding.isEmpty() ? (T) this : getBuilder().append(padding).append(this).toSequence();
  }

  @NotNull
  @Override
  public T padEnd(int length, char pad) {
    T padding = padding(length, pad);
    return padding.isEmpty() ? (T) this : getBuilder().append(this).append(padding).toSequence();
  }

  @NotNull
  @Override
  public T padStart(int length) {
    return padStart(length, ' ');
  }

  @NotNull
  @Override
  public T padEnd(int length) {
    return padEnd(length, ' ');
  }

  // *****************************************************************
  // EOL Helpers
  // *****************************************************************

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
  public final int startOfDelimitedByAnyNot(@NotNull CharPredicate s, int index) {
    return startOfDelimitedByAny(s.negate(), index);
  }

  @Override
  public final int endOfDelimitedByAnyNot(@NotNull CharPredicate s, int index) {
    return endOfDelimitedByAny(s.negate(), index);
  }

  @Override
  public final int startOfDelimitedBy(@NotNull CharSequence s, int index) {
    return SequenceUtils.startOfDelimitedBy(this, s, index);
  }

  @Override
  public final int startOfDelimitedByAny(@NotNull CharPredicate s, int index) {
    return SequenceUtils.startOfDelimitedByAny(this, s, index);
  }

  @Override
  public final int endOfDelimitedBy(@NotNull CharSequence s, int index) {
    return SequenceUtils.endOfDelimitedBy(this, s, index);
  }

  @Override
  public final int endOfDelimitedByAny(@NotNull CharPredicate s, int index) {
    return SequenceUtils.endOfDelimitedByAny(this, s, index);
  }

  @Override
  @NotNull
  public final Range lineRangeAt(int index) {
    return SequenceUtils.lineRangeAt(this, index);
  }

  @Override
  @NotNull
  public final Range lineRangeAtAnyEOL(int index) {
    return SequenceUtils.lineRangeAtAnyEOL(this, index);
  }

  @NotNull
  @Override
  public final T lineAt(int index) {
    return subSequence(lineRangeAt(index));
  }

  @NotNull
  @Override
  public final T lineAtAnyEOL(int index) {
    return subSequence(lineRangeAtAnyEOL(index));
  }

  @NotNull
  @Override
  public final Range eolEndRange(int eolEnd) {
    return SequenceUtils.eolEndRange(this, eolEnd);
  }

  @NotNull
  @Override
  public Range eolStartRange(int eolStart) {
    return SequenceUtils.eolStartRange(this, eolStart);
  }

  @NotNull
  @Override
  public final T trimEOL() {
    int eolLength = eolEndLength();
    return eolLength > 0 ? subSequence(0, length() - eolLength) : (T) this;
  }

  @NotNull
  @Override
  public final T trimmedEOL() {
    int eolLength = eolEndLength();
    return eolLength > 0 ? subSequence(length() - eolLength) : nullSequence();
  }

  @NotNull
  @Override
  public final T trimTailBlankLines() {
    Range range = trailingBlankLinesRange();
    return range.isNull() ? (T) this : subSequenceBefore(range);
  }

  @NotNull
  @Override
  public final T trimLeadBlankLines() {
    Range range = leadingBlankLinesRange();
    return range.isNull() ? (T) this : subSequenceAfter(range);
  }

  @NotNull
  @Override
  public final Range leadingBlankLinesRange() {
    return SequenceUtils.leadingBlankLinesRange(this);
  }

  @NotNull
  @Override
  public final Range leadingBlankLinesRange(int startIndex) {
    return SequenceUtils.leadingBlankLinesRange(this, startIndex);
  }

  @NotNull
  @Override
  public final Range leadingBlankLinesRange(int fromIndex, int endIndex) {
    return SequenceUtils.leadingBlankLinesRange(this, fromIndex, endIndex);
  }

  @NotNull
  @Override
  public final Range trailingBlankLinesRange() {
    return SequenceUtils.trailingBlankLinesRange(this);
  }

  @NotNull
  @Override
  public final Range trailingBlankLinesRange(int fromIndex) {
    return SequenceUtils.trailingBlankLinesRange(this, fromIndex);
  }

  @NotNull
  @Override
  public final Range trailingBlankLinesRange(int startIndex, int fromIndex) {
    return SequenceUtils.trailingBlankLinesRange(this, startIndex, fromIndex);
  }

  @NotNull
  @Override
  public final Range trailingBlankLinesRange(
      CharPredicate eolChars, int startIndex, int fromIndex) {
    return SequenceUtils.trailingBlankLinesRange(this, eolChars, startIndex, fromIndex);
  }

  @NotNull
  @Override
  public final Range leadingBlankLinesRange(
      @NotNull CharPredicate eolChars, int fromIndex, int endIndex) {
    return SequenceUtils.leadingBlankLinesRange(this, eolChars, fromIndex, endIndex);
  }

  @NotNull
  @Override
  public final List<Range> blankLinesRemovedRanges() {
    return SequenceUtils.blankLinesRemovedRanges(this);
  }

  @NotNull
  @Override
  public final List<Range> blankLinesRemovedRanges(int fromIndex) {
    return SequenceUtils.blankLinesRemovedRanges(this, fromIndex);
  }

  @NotNull
  @Override
  public final List<Range> blankLinesRemovedRanges(int fromIndex, int endIndex) {
    return SequenceUtils.blankLinesRemovedRanges(this, fromIndex, endIndex);
  }

  @NotNull
  @Override
  public final List<Range> blankLinesRemovedRanges(
      @NotNull CharPredicate eolChars, int fromIndex, int endIndex) {
    return SequenceUtils.blankLinesRemovedRanges(this, eolChars, fromIndex, endIndex);
  }

  @NotNull
  @Override
  public T trimToEndOfLine(boolean includeEol) {
    return trimToEndOfLine(CharPredicate.EOL, includeEol, 0);
  }

  @NotNull
  @Override
  public T trimToEndOfLine(int index) {
    return trimToEndOfLine(CharPredicate.EOL, false, index);
  }

  @NotNull
  @Override
  public T trimToEndOfLine() {
    return trimToEndOfLine(CharPredicate.EOL, false, 0);
  }

  @NotNull
  @Override
  public T trimToEndOfLine(boolean includeEol, int index) {
    return trimToEndOfLine(CharPredicate.EOL, includeEol, index);
  }

  @NotNull
  @Override
  public T trimToStartOfLine(boolean includeEol) {
    return trimToStartOfLine(CharPredicate.EOL, includeEol, 0);
  }

  @NotNull
  @Override
  public T trimToStartOfLine(int index) {
    return trimToStartOfLine(CharPredicate.EOL, false, index);
  }

  @NotNull
  @Override
  public T trimToStartOfLine() {
    return trimToStartOfLine(CharPredicate.EOL, false, 0);
  }

  @NotNull
  @Override
  public T trimToStartOfLine(boolean includeEol, int index) {
    return trimToStartOfLine(CharPredicate.EOL, includeEol, index);
  }

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

  @NotNull
  @Override
  public final T ifNull(@NotNull T other) {
    return isNull() ? other : (T) this;
  }

  @NotNull
  @Override
  public final T ifNullEmptyAfter(@NotNull T other) {
    return isNull() ? other.subSequence(other.length(), other.length()) : (T) this;
  }

  @NotNull
  @Override
  public final T ifNullEmptyBefore(@NotNull T other) {
    return isNull() ? other.subSequence(0, 0) : (T) this;
  }

  @NotNull
  @Override
  public final T nullIfEmpty() {
    return isEmpty() ? nullSequence() : (T) this;
  }

  @NotNull
  @Override
  public final T nullIfBlank() {
    return isBlank() ? nullSequence() : (T) this;
  }

  @NotNull
  @Override
  public final T nullIf(boolean condition) {
    return condition ? nullSequence() : (T) this;
  }

  @NotNull
  @Override
  public final T nullIfNot(
      @NotNull BiPredicate<? super T, ? super CharSequence> predicate, CharSequence... matches) {
    return nullIf(predicate.negate(), matches);
  }

  @NotNull
  @Override
  public final T nullIf(
      @NotNull Predicate<? super CharSequence> predicate, CharSequence... matches) {
    return nullIf((o1, o2) -> predicate.test(o2), matches);
  }

  @NotNull
  @Override
  public final T nullIfNot(
      @NotNull Predicate<? super CharSequence> predicate, CharSequence... matches) {
    return nullIfNot((o1, o2) -> predicate.test(o2), matches);
  }

  @NotNull
  @Override
  public final T nullIf(CharSequence... matches) {
    return nullIf((Predicate<? super CharSequence>) this::matches, matches);
  }

  @NotNull
  @Override
  public final T nullIfNot(CharSequence... matches) {
    return nullIfNot((Predicate<? super CharSequence>) this::matches, matches);
  }

  @NotNull
  @Override
  public final T nullIfStartsWith(CharSequence... matches) {
    return nullIf((Predicate<? super CharSequence>) this::startsWith, matches);
  }

  @NotNull
  @Override
  public final T nullIfNotStartsWith(CharSequence... matches) {
    return nullIfNot((Predicate<? super CharSequence>) this::startsWith, matches);
  }

  @NotNull
  @Override
  public final T nullIfEndsWith(CharSequence... matches) {
    return nullIf((Predicate<? super CharSequence>) this::endsWith, matches);
  }

  @NotNull
  @Override
  public final T nullIfNotEndsWith(CharSequence... matches) {
    return nullIfNot((Predicate<? super CharSequence>) this::endsWith, matches);
  }

  @NotNull
  @Override
  public final T nullIfStartsWithIgnoreCase(CharSequence... matches) {
    return nullIf((Predicate<? super CharSequence>) this::startsWithIgnoreCase, matches);
  }

  @NotNull
  @Override
  public final T nullIfNotStartsWithIgnoreCase(CharSequence... matches) {
    return nullIfNot((Predicate<? super CharSequence>) this::startsWithIgnoreCase, matches);
  }

  @NotNull
  @Override
  public final T nullIfEndsWithIgnoreCase(CharSequence... matches) {
    return nullIf((Predicate<? super CharSequence>) this::endsWithIgnoreCase, matches);
  }

  @NotNull
  @Override
  public final T nullIfNotEndsWithIgnoreCase(CharSequence... matches) {
    return nullIfNot((Predicate<? super CharSequence>) this::endsWithIgnoreCase, matches);
  }

  @NotNull
  @Override
  public final T nullIfStartsWith(boolean ignoreCase, CharSequence... matches) {
    return nullIf(
        (Predicate<? super CharSequence>) prefix -> startsWith(prefix, ignoreCase), matches);
  }

  @NotNull
  @Override
  public final T nullIfNotStartsWith(boolean ignoreCase, CharSequence... matches) {
    return nullIfNot(
        (Predicate<? super CharSequence>) prefix -> startsWith(prefix, ignoreCase), matches);
  }

  @NotNull
  @Override
  public final T nullIfEndsWith(boolean ignoreCase, CharSequence... matches) {
    return nullIf(
        (Predicate<? super CharSequence>) suffix -> endsWith(suffix, ignoreCase), matches);
  }

  @NotNull
  @Override
  public final T nullIfNotEndsWith(boolean ignoreCase, CharSequence... matches) {
    return nullIfNot(
        (Predicate<? super CharSequence>) suffix -> endsWith(suffix, ignoreCase), matches);
  }

  @NotNull
  @Override
  public final T nullIf(
      @NotNull BiPredicate<? super T, ? super CharSequence> predicate, CharSequence... matches) {
    for (CharSequence match : matches) {
      if (predicate.test((T) this, match)) return nullSequence();
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
  public final boolean endsWith(@NotNull CharSequence suffix) {
    return SequenceUtils.endsWith(this, suffix);
  }

  @Override
  public final boolean endsWith(@NotNull CharSequence suffix, boolean ignoreCase) {
    return SequenceUtils.endsWith(this, suffix, ignoreCase);
  }

  @Override
  public final boolean startsWith(@NotNull CharSequence prefix) {
    return SequenceUtils.startsWith(this, prefix);
  }

  @Override
  public final boolean startsWith(@NotNull CharSequence prefix, boolean ignoreCase) {
    return SequenceUtils.startsWith(this, prefix, ignoreCase);
  }

  @Override
  public final boolean endsWith(@NotNull CharPredicate chars) {
    return SequenceUtils.endsWith(this, chars);
  }

  @Override
  public final boolean startsWith(@NotNull CharPredicate chars) {
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

  @NotNull
  @Override
  public final T removeSuffix(@NotNull CharSequence suffix) {
    return !endsWith(suffix) ? (T) this : subSequence(0, length() - suffix.length());
  }

  @NotNull
  @Override
  public final T removePrefix(@NotNull CharSequence prefix) {
    return !startsWith(prefix) ? (T) this : subSequence(prefix.length(), length());
  }

  @NotNull
  @Override
  public final T removeProperSuffix(@NotNull CharSequence suffix) {
    return length() <= suffix.length() || !endsWith(suffix)
        ? (T) this
        : subSequence(0, length() - suffix.length());
  }

  @NotNull
  @Override
  public final T removeProperPrefix(@NotNull CharSequence prefix) {
    return length() <= prefix.length() || !startsWith(prefix)
        ? (T) this
        : subSequence(prefix.length(), length());
  }

  @Override
  public final boolean endsWithIgnoreCase(@NotNull CharSequence suffix) {
    return length() > 0 && matchCharsReversed(suffix, length() - 1, true);
  }

  @Override
  public final boolean startsWithIgnoreCase(@NotNull CharSequence prefix) {
    return length() > 0 && matchChars(prefix, 0, true);
  }

  @NotNull
  @Override
  public final T removeSuffixIgnoreCase(@NotNull CharSequence suffix) {
    return !endsWithIgnoreCase(suffix) ? (T) this : subSequence(0, length() - suffix.length());
  }

  @NotNull
  @Override
  public final T removePrefixIgnoreCase(@NotNull CharSequence prefix) {
    return !startsWithIgnoreCase(prefix) ? (T) this : subSequence(prefix.length(), length());
  }

  @NotNull
  @Override
  public final T removeProperSuffixIgnoreCase(@NotNull CharSequence suffix) {
    return length() <= suffix.length() || !endsWithIgnoreCase(suffix)
        ? (T) this
        : subSequence(0, length() - suffix.length());
  }

  @NotNull
  @Override
  public final T removeProperPrefixIgnoreCase(@NotNull CharSequence prefix) {
    return length() <= prefix.length() || !startsWithIgnoreCase(prefix)
        ? (T) this
        : subSequence(prefix.length(), length());
  }

  @NotNull
  @Override
  public final T removeSuffix(@NotNull CharSequence suffix, boolean ignoreCase) {
    return !endsWith(suffix, ignoreCase) ? (T) this : subSequence(0, length() - suffix.length());
  }

  @NotNull
  @Override
  public final T removePrefix(@NotNull CharSequence prefix, boolean ignoreCase) {
    return !startsWith(prefix, ignoreCase) ? (T) this : subSequence(prefix.length(), length());
  }

  @NotNull
  @Override
  public final T removeProperSuffix(@NotNull CharSequence suffix, boolean ignoreCase) {
    return length() <= suffix.length() || !endsWith(suffix, ignoreCase)
        ? (T) this
        : subSequence(0, length() - suffix.length());
  }

  @NotNull
  @Override
  public final T removeProperPrefix(@NotNull CharSequence prefix, boolean ignoreCase) {
    return length() <= prefix.length() || !startsWith(prefix, ignoreCase)
        ? (T) this
        : subSequence(prefix.length(), length());
  }

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
      return getBuilder()
          .add(subSequence(0, index))
          .add(chars)
          .add(subSequence(index))
          .toSequence();
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
  public final T toLowerCase() {
    return toMapped(ChangeCase.toLowerCase);
  }

  @NotNull
  @Override
  public final T toUpperCase() {
    return toMapped(ChangeCase.toUpperCase);
  }

  @NotNull
  @Override
  public final T toNbSp() {
    return toMapped(SpaceMapper.toNonBreakSpace);
  }

  @NotNull
  @Override
  public final T toSpc() {
    return toMapped(SpaceMapper.fromNonBreakSpace);
  }

  @Override
  public final boolean matches(@NotNull CharSequence chars, boolean ignoreCase) {
    return SequenceUtils.matches(this, chars, ignoreCase);
  }

  @Override
  public final boolean matches(@NotNull CharSequence chars) {
    return SequenceUtils.matches(this, chars);
  }

  @Override
  public final boolean matchesIgnoreCase(@NotNull CharSequence chars) {
    return SequenceUtils.matchesIgnoreCase(this, chars);
  }

  @Override
  public final boolean matchChars(@NotNull CharSequence chars, int startIndex, boolean ignoreCase) {
    return SequenceUtils.matchChars(this, chars, startIndex, ignoreCase);
  }

  @Override
  public final boolean matchChars(@NotNull CharSequence chars, int startIndex) {
    return SequenceUtils.matchChars(this, chars, startIndex);
  }

  @Override
  public final boolean matchCharsIgnoreCase(@NotNull CharSequence chars, int startIndex) {
    return SequenceUtils.matchCharsIgnoreCase(this, chars, startIndex);
  }

  @Override
  public final boolean matchChars(@NotNull CharSequence chars, boolean ignoreCase) {
    return SequenceUtils.matchChars(this, chars, ignoreCase);
  }

  @Override
  public final boolean matchChars(@NotNull CharSequence chars) {
    return SequenceUtils.matchChars(this, chars);
  }

  @Override
  public final boolean matchCharsIgnoreCase(@NotNull CharSequence chars) {
    return SequenceUtils.matchCharsIgnoreCase(this, chars);
  }

  @Override
  public final boolean matchCharsReversed(
      @NotNull CharSequence chars, int endIndex, boolean ignoreCase) {
    return SequenceUtils.matchCharsReversed(this, chars, endIndex, ignoreCase);
  }

  @Override
  public final boolean matchCharsReversed(@NotNull CharSequence chars, int endIndex) {
    return SequenceUtils.matchCharsReversed(this, chars, endIndex);
  }

  @Override
  public final boolean matchCharsReversedIgnoreCase(@NotNull CharSequence chars, int endIndex) {
    return SequenceUtils.matchCharsReversedIgnoreCase(this, chars, endIndex);
  }

  @Override
  public final int matchedCharCount(
      @NotNull CharSequence chars, int startIndex, int endIndex, boolean ignoreCase) {
    return SequenceUtils.matchedCharCount(this, chars, startIndex, endIndex, ignoreCase);
  }

  @Override
  public final int matchedCharCount(
      @NotNull CharSequence chars, int startIndex, boolean ignoreCase) {
    return SequenceUtils.matchedCharCount(this, chars, startIndex, ignoreCase);
  }

  @Override
  public final int matchedCharCount(@NotNull CharSequence chars, int startIndex, int endIndex) {
    return SequenceUtils.matchedCharCount(this, chars, startIndex, endIndex);
  }

  @Override
  public final int matchedCharCount(@NotNull CharSequence chars, int startIndex) {
    return SequenceUtils.matchedCharCount(this, chars, startIndex);
  }

  @Override
  public final int matchedCharCountIgnoreCase(@NotNull CharSequence chars, int startIndex) {
    return SequenceUtils.matchedCharCountIgnoreCase(this, chars, startIndex);
  }

  @Override
  public final int matchedCharCountIgnoreCase(
      @NotNull CharSequence chars, int startIndex, int endIndex) {
    return SequenceUtils.matchedCharCountIgnoreCase(this, chars, startIndex, endIndex);
  }

  @Override
  public final int matchedCharCountReversedIgnoreCase(
      @NotNull CharSequence chars, int startIndex, int fromIndex) {
    return SequenceUtils.matchedCharCountReversedIgnoreCase(this, chars, startIndex, fromIndex);
  }

  @Override
  public final int matchedCharCountReversed(
      @NotNull CharSequence chars, int startIndex, int fromIndex) {
    return SequenceUtils.matchedCharCountReversed(this, chars, startIndex, fromIndex);
  }

  @Override
  public final int matchedCharCountReversed(
      @NotNull CharSequence chars, int fromIndex, boolean ignoreCase) {
    return SequenceUtils.matchedCharCountReversed(this, chars, fromIndex, ignoreCase);
  }

  @Override
  public final int matchedCharCountReversed(@NotNull CharSequence chars, int fromIndex) {
    return SequenceUtils.matchedCharCountReversed(this, chars, fromIndex);
  }

  @Override
  public final int matchedCharCountReversedIgnoreCase(@NotNull CharSequence chars, int fromIndex) {
    return SequenceUtils.matchedCharCountReversedIgnoreCase(this, chars, fromIndex);
  }

  @Override
  public final int matchedCharCount(
      @NotNull CharSequence chars,
      int startIndex,
      int endIndex,
      boolean fullMatchOnly,
      boolean ignoreCase) {
    return SequenceUtils.matchedCharCount(
        this, chars, startIndex, endIndex, fullMatchOnly, ignoreCase);
  }

  @Override
  public final int matchedCharCountReversed(
      @NotNull CharSequence chars, int startIndex, int fromIndex, boolean ignoreCase) {
    return SequenceUtils.matchedCharCountReversed(this, chars, startIndex, fromIndex, ignoreCase);
  }

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
  public final String normalizeEOL() {
    return Escaping.normalizeEOL(toString());
  }

  @NotNull
  @Override
  public final String normalizeEndWithEOL() {
    return Escaping.normalizeEndWithEOL(toString());
  }

  @NotNull
  @Override
  public final String toVisibleWhitespaceString() {
    return SequenceUtils.toVisibleWhitespaceString(this);
  }

  @NotNull
  @Override
  public final List<T> splitList(@NotNull CharSequence delimiter) {
    return SequenceUtils.splitList((T) this, delimiter, 0, 0, null);
  }

  @NotNull
  @Override
  public final List<T> splitList(
      @NotNull CharSequence delimiter,
      int limit,
      boolean includeDelims,
      @Nullable CharPredicate trimChars) {
    return SequenceUtils.splitList(
        (T) this,
        delimiter,
        limit,
        includeDelims ? SequenceUtils.SPLIT_INCLUDE_DELIMS : 0,
        trimChars);
  }

  @NotNull
  @Override
  public final List<T> splitList(@NotNull CharSequence delimiter, int limit, int flags) {
    return SequenceUtils.splitList((T) this, delimiter, limit, flags, null);
  }

  @NotNull
  @Override
  public final List<T> splitList(
      @NotNull CharSequence delimiter, boolean includeDelims, @Nullable CharPredicate trimChars) {
    return SequenceUtils.splitList(
        (T) this, delimiter, 0, includeDelims ? SequenceUtils.SPLIT_INCLUDE_DELIMS : 0, trimChars);
  }

  // NOTE: these default to including delimiters as part of split item
  @NotNull
  @Override
  public final List<T> splitListEOL() {
    return SequenceUtils.splitList(
        (T) this, SequenceUtils.EOL, 0, SequenceUtils.SPLIT_INCLUDE_DELIMS, null);
  }

  @NotNull
  @Override
  public final List<T> splitListEOL(boolean includeDelims) {
    return SequenceUtils.splitList(
        (T) this,
        SequenceUtils.EOL,
        0,
        includeDelims ? SequenceUtils.SPLIT_INCLUDE_DELIMS : 0,
        null);
  }

  @NotNull
  @Override
  public final List<T> splitListEOL(boolean includeDelims, @Nullable CharPredicate trimChars) {
    return SequenceUtils.splitList(
        (T) this,
        SequenceUtils.EOL,
        0,
        includeDelims ? SequenceUtils.SPLIT_INCLUDE_DELIMS : 0,
        trimChars);
  }

  @NotNull
  @Override
  public final List<T> splitList(
      @NotNull CharSequence delimiter, int limit, int flags, @Nullable CharPredicate trimChars) {
    return SequenceUtils.splitList((T) this, delimiter, limit, flags, trimChars);
  }

  @NotNull
  @Override
  public final T[] splitEOL() {
    return split(SequenceUtils.EOL, 0, SequenceUtils.SPLIT_INCLUDE_DELIMS, null);
  }

  @NotNull
  @Override
  public final T[] splitEOL(boolean includeDelims) {
    return split(
        SequenceUtils.EOL, 0, includeDelims ? SequenceUtils.SPLIT_INCLUDE_DELIMS : 0, null);
  }

  @NotNull
  @Override
  public final T[] split(
      @NotNull CharSequence delimiter, boolean includeDelims, @Nullable CharPredicate trimChars) {
    return split(delimiter, 0, includeDelims ? SequenceUtils.SPLIT_INCLUDE_DELIMS : 0, trimChars);
  }

  @NotNull
  @Override
  public final T[] split(@NotNull CharSequence delimiter) {
    return split(delimiter, 0, 0, null);
  }

  @NotNull
  @Override
  public final T[] split(
      @NotNull CharSequence delimiter,
      int limit,
      boolean includeDelims,
      @Nullable CharPredicate trimChars) {
    return split(
        delimiter, limit, includeDelims ? SequenceUtils.SPLIT_INCLUDE_DELIMS : 0, trimChars);
  }

  @NotNull
  @Override
  public final T[] split(@NotNull CharSequence delimiter, int limit, int flags) {
    return split(delimiter, limit, flags, null);
  }

  @NotNull
  @Override
  public final T[] split(
      @NotNull CharSequence delimiter, int limit, int flags, @Nullable CharPredicate trimChars) {
    return SequenceUtils.splitList((T) this, delimiter, limit, flags, trimChars)
        .toArray(emptyArray());
  }

  @NotNull
  @Override
  public final T appendTo(@NotNull StringBuilder out, @Nullable CharMapper charMapper) {
    return appendTo(out, charMapper, 0, length());
  }

  @NotNull
  @Override
  public final T appendTo(
      @NotNull StringBuilder out, @Nullable CharMapper charMapper, int startIndex) {
    return appendTo(out, charMapper, startIndex, length());
  }

  @NotNull
  @Override
  public final T appendTo(@NotNull StringBuilder out) {
    return appendTo(out, null, 0, length());
  }

  @NotNull
  @Override
  public final T appendTo(@NotNull StringBuilder out, int startIndex) {
    return appendTo(out, null, startIndex, length());
  }

  @NotNull
  @Override
  public final T appendTo(@NotNull StringBuilder out, int startIndex, int endIndex) {
    return appendTo(out, null, startIndex, endIndex);
  }

  @NotNull
  @Override
  public final T appendTo(
      @NotNull StringBuilder out, @Nullable CharMapper charMapper, int startIndex, int endIndex) {
    CharSequence useSequence = charMapper == null ? this : toMapped(charMapper);
    out.append(useSequence, startIndex, endIndex);
    return (T) this;
  }

  @NotNull
  @Override
  public final T appendRangesTo(
      @NotNull StringBuilder out, @Nullable CharMapper charMapper, Range... ranges) {
    return appendRangesTo(out, charMapper, new ArrayIterable<>(ranges));
  }

  @NotNull
  @Override
  public final T appendRangesTo(@NotNull StringBuilder out, Range... ranges) {
    return appendRangesTo(out, null, new ArrayIterable<>(ranges));
  }

  @NotNull
  @Override
  public final T appendRangesTo(@NotNull StringBuilder out, Iterable<? extends Range> ranges) {
    return appendRangesTo(out, null, ranges);
  }

  @NotNull
  @Override
  public final T appendRangesTo(
      @NotNull StringBuilder out,
      @Nullable CharMapper charMapper,
      Iterable<? extends Range> ranges) {
    CharSequence useSequence = charMapper == null ? this : toMapped(charMapper);

    for (Range range : ranges) {
      if (range != null && range.isNotNull())
        out.append(useSequence, range.getStart(), range.getEnd());
    }
    return (T) this;
  }

  @NotNull
  @Override
  public final int[] indexOfAll(@NotNull CharSequence s) {
    return SequenceUtils.indexOfAll(this, s);
  }

  @Override
  @NotNull
  public final T appendEOL() {
    return suffixWith(SequenceUtils.EOL);
  }

  @Override
  @NotNull
  public final T suffixWithEOL() {
    return suffixWith(SequenceUtils.EOL);
  }

  @Override
  @NotNull
  public final T prefixWithEOL() {
    return prefixWith(SequenceUtils.EOL);
  }

  @Override
  @NotNull
  public final T prefixOnceWithEOL() {
    return prefixOnceWith(SequenceUtils.EOL);
  }

  @Override
  @NotNull
  public final T suffixOnceWithEOL() {
    return suffixOnceWith(SequenceUtils.EOL);
  }

  @Override
  @NotNull
  public final T appendSpace() {
    return suffixWith(SequenceUtils.SPACE);
  }

  @Override
  @NotNull
  public final T suffixWithSpace() {
    return suffixWith(SequenceUtils.SPACE);
  }

  @Override
  @NotNull
  public final T prefixWithSpace() {
    return prefixWith(SequenceUtils.SPACE);
  }

  @Override
  @NotNull
  public final T appendSpaces(int count) {
    return suffixWith(RepeatedSequence.ofSpaces(count));
  }

  @Override
  @NotNull
  public final T suffixWithSpaces(int count) {
    return suffixWith(RepeatedSequence.ofSpaces(count));
  }

  @Override
  @NotNull
  public final T prefixWithSpaces(int count) {
    return prefixWith(RepeatedSequence.ofSpaces(count));
  }

  @Override
  @NotNull
  public final T prefixOnceWithSpace() {
    return prefixOnceWith(SequenceUtils.SPACE);
  }

  @Override
  @NotNull
  public final T suffixOnceWithSpace() {
    return suffixOnceWith(SequenceUtils.SPACE);
  }

  @NotNull
  @Override
  public T prefixWith(@Nullable CharSequence prefix) {
    return prefix == null || prefix.length() == 0
        ? (T) this
        : getBuilder().add(prefix).add(this).toSequence();
  }

  @NotNull
  @Override
  public T suffixWith(@Nullable CharSequence suffix) {
    // convoluted to allow BasedCharSequence to use PrefixedCharSequence so all fits into
    // SegmentedCharSequence
    if (suffix == null || suffix.length() == 0) return (T) this;
    return getBuilder().add(this).add(suffix).toSequence();
  }

  @NotNull
  @Override
  public final T prefixOnceWith(@Nullable CharSequence prefix) {
    return prefix == null || prefix.length() == 0 || startsWith(prefix)
        ? (T) this
        : prefixWith(prefix);
  }

  @NotNull
  @Override
  public final T suffixOnceWith(@Nullable CharSequence suffix) {
    return suffix == null || suffix.length() == 0 || endsWith(suffix)
        ? (T) this
        : suffixWith(suffix);
  }

  @NotNull
  @Override
  public final T replace(int startIndex, int endIndex, @NotNull CharSequence replacement) {
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

  @NotNull
  @Override
  public final T replace(@NotNull CharSequence find, @NotNull CharSequence replace) {
    int[] indices = indexOfAll(find);
    if (indices.length == 0) return (T) this;
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

  @NotNull
  @Override
  public final T append(CharSequence... sequences) {
    return append(new ArrayIterable<>(sequences));
  }

  @NotNull
  @Override
  public final T append(Iterable<? extends CharSequence> sequences) {
    ISequenceBuilder<?, T> segments = getBuilder();
    segments.add(this);
    for (CharSequence sequence : sequences) {
      segments.add(sequence);
    }
    return segments.toSequence();
  }

  @NotNull
  @Override
  public final T extractRanges(Range... ranges) {
    return extractRanges(new ArrayIterable<>(ranges));
  }

  @NotNull
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

  @NotNull
  @Override
  public final Pair<Integer, Integer> lineColumnAtIndex(int index) {
    return SequenceUtils.lineColumnAtIndex(this, index);
  }

  @Override
  public boolean isIn(@NotNull String[] texts) {
    return SequenceUtils.containedBy(texts, this);
  }

  @Override
  public boolean isIn(@NotNull Collection<? extends CharSequence> texts) {
    return SequenceUtils.containedBy(texts, this);
  }
}
