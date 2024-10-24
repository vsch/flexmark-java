package com.vladsch.flexmark.util.sequence;

import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataKeyBase;
import com.vladsch.flexmark.util.misc.CharPredicate;
import com.vladsch.flexmark.util.misc.Pair;
import com.vladsch.flexmark.util.sequence.builder.IBasedSegmentBuilder;
import com.vladsch.flexmark.util.sequence.builder.SequenceBuilder;
import com.vladsch.flexmark.util.sequence.builder.tree.SegmentTree;
import java.util.ArrayList;
import java.util.List;

/**
 * A CharSequence that references original char sequence with offsets into original preserved.
 *
 * <p>Since equals is used for comparison of sequences and strings by base sequence manager, a base
 * sequence with NUL may not compare equal to an equivalent unwrapped sequence because NUL chars are
 * not converted. For Strings this is handled by using String.equals() for comparison. For other
 * CharacterSequence types the match will fail if original has NUL in it.
 *
 * <p>a subSequence() returns a sub-sequence from the original base sequence with corresponding
 * offsets
 */
public interface BasedSequence extends IRichSequence<BasedSequence>, BasedOptionsHolder {
  BasedSequence NULL = new EmptyBasedSequence();
  BasedSequence EMPTY = new EmptyBasedSequence();
  BasedSequence EOL = CharSubSequence.of(SequenceUtils.EOL);
  List<BasedSequence> EMPTY_LIST = new ArrayList<>();
  BasedSequence[] EMPTY_ARRAY = new BasedSequence[0];

  static BasedSequence of(CharSequence charSequence) {
    return BasedSequenceImpl.create(charSequence);
  }

  @Override
  SequenceBuilder getBuilder();

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
   * Get the start offset of this sequence into {@link #getBaseSequence()} and {@link #getBase()}
   * original text source.
   *
   * @return start offset in original text
   */
  int getStartOffset();

  /**
   * Get the end offset of this sequence into {@link #getBaseSequence()} and {@link #getBase()}
   * original text source.
   *
   * @return end offset in original text
   */
  int getEndOffset();

  /**
   * Get the offset of index in this sequence mapped to offset into {@link #getBaseSequence()} and
   * {@link #getBase()} original text source. NOTE: if the character at given index does not equal
   * the corresponding character in the base sequence then this method should return -1 otherwise
   * segmented based sequence will be created for original base character
   *
   * @param index index for which to get the offset in original source
   * @return offset of index of this sequence in original text
   */
  int getIndexOffset(int index);

  /**
   * Add segments for this sequence, replacing out of base characters with strings
   *
   * @param builder builder
   */
  void addSegments(IBasedSegmentBuilder<?> builder);

  /**
   * Get the segment tree for this sequence
   *
   * @return segment tree
   */
  SegmentTree getSegmentTree();

  /**
   * Get the range of this sequence in original {@link #getBaseSequence()} and {@link #getBase()}
   * original text source.
   *
   * @return Range of start offset and end offset
   */
  Range getSourceRange();

  /**
   * Get a portion of this sequence
   *
   * @param startIndex offset from startIndex of this sequence
   * @param endIndex offset from startIndex of this sequence
   * @return based sequence which represents the requested range of this sequence.
   */
  @Override
  BasedSequence subSequence(int startIndex, int endIndex);

  /**
   * Get a portion of this sequence's base sequence
   *
   * <p>NOTE: this means that if this sequence applies modifications to the original sequence then
   * these modifications are NOT be applied to the returned sequence.
   *
   * <p>NOTE: It should only be implemented in classes which provide base sequences such as {@link
   * CharSubSequence} and {@link SubSequence} others use inherited implementation of {@link
   * BasedSequenceImpl}
   *
   * @param startIndex offset from 0 of original sequence
   * @param endIndex offset from 0 of original sequence
   * @return based sequence whose contents reflect the selected portion
   */
  BasedSequence baseSubSequence(int startIndex, int endIndex);

  /**
   * Get a portion of the original sequence that this sequence is based on
   *
   * @param startIndex offset from 0 of original sequence
   * @return based sequence from startIndex to the endIndex
   */
  BasedSequence baseSubSequence(int startIndex);

  /**
   * Safe, if index out of range but based sequence has characters will return those, else returns
   * '\0'
   *
   * <p>Allows peeking into preceding/following characters to the ones contained in this sequence
   *
   * @param index index in string
   * @return character or '\0' if index out of base sequence
   */
  char safeBaseCharAt(int index);

  /**
   * Safe, if index out of range but based sequence has characters will return those, else returns
   * '\0'
   *
   * <p>Allows peeking into preceding/following characters to the ones contained in this sequence
   *
   * @param index index in string
   * @param predicate character set predicate
   * @return true if character at index tests true
   */
  boolean isBaseCharAt(int index, CharPredicate predicate);

  /**
   * Get empty prefix to this sequence
   *
   * @return same as subSequence(0,0)
   */
  BasedSequence getEmptyPrefix();

  /**
   * Get empty suffix to this sequence
   *
   * @return same as subSequence(length())
   */
  BasedSequence getEmptySuffix();

  /**
   * Get the unescaped string of this sequence content
   *
   * @return unescaped text
   */
  String unescape();

  /**
   * Get the unescaped string of this sequence content without unescaping entities
   *
   * @return unescaped text
   */
  String unescapeNoEntities();

  /**
   * Get the unescaped string of this sequence content
   *
   * @param textMapper replaced text mapper which will be uses to map unescaped index to original
   *     source index
   * @return unescaped text in based sequence
   */
  BasedSequence unescape(ReplacedTextMapper textMapper);

  /**
   * replace any \r\n and \r by \n
   *
   * @param textMapper replaced text mapper which will be uses to map unescaped index to original
   *     source index
   * @return based sequence with only \n for line separators
   */
  BasedSequence normalizeEOL(ReplacedTextMapper textMapper);

  /**
   * replace any \r\n and \r by \n, append terminating EOL if one is not present
   *
   * @param textMapper replaced text mapper which will be uses to map unescaped index to original
   *     source index
   * @return based sequence with only \n for line separators and terminated by \n
   */
  BasedSequence normalizeEndWithEOL(ReplacedTextMapper textMapper);

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
   * Splice the given sequence to the end of this one and return a BasedSequence of the result. Does
   * not copy anything, creates a new based sequence of the original text but one that spans
   * characters of this sequence and other
   *
   * @param other sequence to append to end of this one
   * @return based sequence that contains the span from start of this sequence and end of other
   *     <p>assertion will fail if the other sequence is not a continuation of this one
   */
  BasedSequence spliceAtEnd(BasedSequence other);

  /**
   * start/end offset based containment, not textual
   *
   * @param other based sequence from the same base
   * @return true if other is contained in this
   */
  boolean containsAllOf(BasedSequence other);

  /**
   * start/end offset based containment, not textual
   *
   * @param other based sequence from the same base
   * @return true if other is contained in this
   */
  boolean containsSomeOf(BasedSequence other);

  /**
   * Get the prefix part of this from other, start/end offset based containment, not textual
   *
   * @param other based sequence from the same base
   * @return prefix part of this as compared to other, start/end offset based, not content
   */
  BasedSequence prefixOf(BasedSequence other);

  /**
   * Get the suffix part of this from other, start/end offset based containment, not textual
   *
   * @param other based sequence from the same base
   * @return suffix part of this as compared to other, start/end offset based, not content
   */
  BasedSequence suffixOf(BasedSequence other);

  /**
   * start/end offset based intersection, not textual
   *
   * @param other based sequence from the same parent
   * @return sequence which is the intersection of the range of this and other
   */
  BasedSequence intersect(BasedSequence other);

  /**
   * Extend this based sequence to include characters from underlying based sequence
   *
   * @param charSet set of characters to include
   * @param maxCount maximum extra characters to include
   * @return sequence which
   */
  BasedSequence extendByAny(CharPredicate charSet, int maxCount);

  /**
   * Test for line containing some of the characters in the set
   *
   * @param charSet set of characters to be tested
   * @return true if line contains some characters in the set
   */
  boolean containsSomeIn(CharPredicate charSet);

  /**
   * Test for line containing some characters not in the set
   *
   * @param charSet set of characters to be tested
   * @return true if line contains some characters not in the set
   */
  boolean containsSomeNotIn(CharPredicate charSet);

  /**
   * Test for line contains only characters from the set
   *
   * @param charSet set of characters to be tested
   * @return true if line contains some characters from the set
   */
  boolean containsOnlyIn(CharPredicate charSet);

  /**
   * Test for line containing only characters not in the set
   *
   * @param charSet set of characters to be tested
   * @return true if line contains some characters from the set
   */
  boolean containsOnlyNotIn(CharPredicate charSet);

  /**
   * Extend this based sequence to include characters from underlying based sequence not in
   * character set
   *
   * @param charSet set of characters to include
   * @param maxCount maximum extra characters to include
   * @return sequence which
   */
  BasedSequence extendByAnyNot(CharPredicate charSet, int maxCount);

  BasedSequence extendByAnyNot(CharPredicate charSet);

  BasedSequence extendByOneOfAnyNot(CharPredicate charSet);

  /**
   * Extend in contained based sequence
   *
   * @param eolChars characters to consider as EOL, note {@link #eolStartLength(int)} {@link
   *     #eolEndLength(int)} should report length of EOL found if length &gt; 1
   * @param includeEol if to include the eol in the string
   * @return resulting sequence after extension. If already spanning the line then this sequence is
   *     returned. if the last character of this sequence are found in eolChars then no extension
   *     will be performed since it already includes the line end
   */
  BasedSequence extendToEndOfLine(CharPredicate eolChars, boolean includeEol);

  BasedSequence extendToEndOfLine(CharPredicate eolChars);

  /**
   * Extend in contained based sequence
   *
   * @param eolChars characters to consider as EOL, note {@link #eolStartLength(int)} {@link
   *     #eolEndLength(int)} should report length of EOL found if length &gt; 1
   * @param includeEol if to include the eol in the string
   * @return resulting sequence after extension. If already spanning the line then this sequence is
   *     returned. if the first character of this sequence are found in eolChars then no extension
   *     will be performed since it already includes the line end
   */
  BasedSequence extendToStartOfLine(CharPredicate eolChars, boolean includeEol);

  BasedSequence extendToStartOfLine(CharPredicate eolChars);

  /**
   * Extend this based sequence to include characters from underlying based sequence taking tab
   * expansion to 4th spaces into account
   *
   * @param maxColumns maximum columns to include, default {@link Integer#MAX_VALUE}
   * @return sequence which
   */
  BasedSequence prefixWithIndent(int maxColumns);

  /*
   These are convenience methods returning coordinates in Base Sequence of this sequence
  */

  Pair<Integer, Integer> baseLineColumnAtIndex(int index);

  Range baseLineRangeAtIndex(int index);

  int baseEndOfLine(int index);

  int baseEndOfLineAnyEOL(int index);

  int baseStartOfLine(int index);

  int baseStartOfLineAnyEOL(int index);

  int baseColumnAtIndex(int index);

  Pair<Integer, Integer> baseLineColumnAtStart();

  Pair<Integer, Integer> baseLineColumnAtEnd();

  int baseEndOfLine();

  int baseEndOfLineAnyEOL();

  int baseStartOfLine();

  int baseStartOfLineAnyEOL();

  Range baseLineRangeAtStart();

  Range baseLineRangeAtEnd();

  int baseColumnAtEnd();

  int baseColumnAtStart();

  class EmptyBasedSequence extends BasedSequenceImpl {
    EmptyBasedSequence() {
      super(0);
    }

    @Override
    public int getOptionFlags() {
      return 0;
    }

    @Override
    public boolean allOptions(int options) {
      return false;
    }

    @Override
    public boolean anyOptions(int options) {
      return false;
    }

    @Override
    public <T> T getOption(DataKeyBase<T> dataKey) {
      return dataKey.get(null);
    }

    @Override
    public DataHolder getOptions() {
      return null;
    }

    @Override
    public int length() {
      return 0;
    }

    @Override
    public char charAt(int index) {
      throw new StringIndexOutOfBoundsException("EMPTY sequence has no characters");
    }

    @Override
    public int getIndexOffset(int index) {
      SequenceUtils.validateIndexInclusiveEnd(index, length());
      return 0;
    }

    @Override
    public BasedSequence subSequence(int i, int i1) {
      SequenceUtils.validateStartEnd(i, i1, length());
      return this;
    }

    @Override
    public BasedSequence baseSubSequence(int startIndex, int endIndex) {
      return subSequence(startIndex, endIndex);
    }

    @Override
    public BasedSequence getBaseSequence() {
      return this;
    }

    @Override
    public BasedSequence getBase() {
      return this;
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
  }
}
