package com.vladsch.flexmark.util.sequence;

import com.vladsch.flexmark.util.misc.CharPredicate;
import com.vladsch.flexmark.util.misc.Pair;
import com.vladsch.flexmark.util.misc.Utils;
import com.vladsch.flexmark.util.sequence.builder.BasedSegmentBuilder;
import com.vladsch.flexmark.util.sequence.builder.IBasedSegmentBuilder;
import com.vladsch.flexmark.util.sequence.builder.SequenceBuilder;
import com.vladsch.flexmark.util.sequence.builder.tree.SegmentTree;
import com.vladsch.flexmark.util.sequence.mappers.CharMapper;

/** Implementation of BaseSequence */
public abstract class BasedSequenceImpl extends IRichSequenceBase<BasedSequence>
    implements BasedSequence {
  public static BasedSequence firstNonNull(BasedSequence... sequences) {
    for (BasedSequence sequence : sequences) {
      if (sequence != null && sequence != NULL) {
        return sequence;
      }
    }

    return NULL;
  }

  protected BasedSequenceImpl(int hash) {
    super(hash);
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
    return BasedSequence.of(charSequence).subSequence(startIndex, endIndex);
  }

  @Override
  public SequenceBuilder getBuilder() {
    return SequenceBuilder.emptyBuilder(this);
  }

  @Override
  public void addSegments(IBasedSegmentBuilder<?> builder) {
    builder.append(getStartOffset(), getEndOffset());
  }

  /**
   * Get the segment tree for this sequence or null if sequence is contiguous from startOffset to
   * endOffset
   *
   * @return null for contiguous sequences, else segment tree for this sequence
   */
  @Override
  public SegmentTree getSegmentTree() {
    // default implementation
    BasedSegmentBuilder segmentBuilder = BasedSegmentBuilder.emptyBuilder(getBaseSequence());
    addSegments(segmentBuilder);
    return SegmentTree.build(segmentBuilder.getSegments(), segmentBuilder.getText());
  }

  @Override
  public BasedSequence toMapped(CharMapper mapper) {
    return MappedBasedSequence.mappedOf(this, mapper);
  }

  @Override
  public final BasedSequence baseSubSequence(int startIndex) {
    return baseSubSequence(startIndex, getBaseSequence().getEndOffset());
  }

  @Override
  public BasedSequence baseSubSequence(int startIndex, int endIndex) {
    return getBaseSequence().subSequence(startIndex, endIndex);
  }

  @Override
  public char safeCharAt(int index) {
    return index < 0 || index >= length() ? SequenceUtils.NUL : charAt(index);
  }

  @Override
  public char safeBaseCharAt(int index) {
    int startOffset = getStartOffset();
    if (index >= startOffset && index < startOffset + length()) {
      return charAt(index - startOffset);
    }

    return getBaseSequence().safeCharAt(index);
  }

  @Override
  public boolean isBaseCharAt(int index, CharPredicate predicate) {
    return predicate.test(safeBaseCharAt(index));
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
  public String toStringOrNull() {
    return isNull() ? null : toString();
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
    return other.length() > 0
        && length() > 0
        && other.getBase() == getBase()
        && other.getStartOffset() == getEndOffset();
  }

  @Override
  public boolean isContinuationOf(BasedSequence other) {
    return other.length() > 0
        && length() > 0
        && other.getBase() == getBase()
        && other.getEndOffset() == getStartOffset();
  }

  @Override
  public BasedSequence spliceAtEnd(BasedSequence other) {
    if (other.isEmpty()) {
      return this;
    } else if (isEmpty()) {
      return other;
    }
    return baseSubSequence(getStartOffset(), other.getEndOffset());
  }

  @Override
  public boolean containsAllOf(BasedSequence other) {
    return getBase() == other.getBase()
        && other.getStartOffset() >= getStartOffset()
        && other.getEndOffset() <= getEndOffset();
  }

  @Override
  public boolean containsSomeOf(BasedSequence other) {
    return getBase() == other.getBase()
        && !(getStartOffset() >= other.getEndOffset() || getEndOffset() <= other.getStartOffset());
  }

  @Override
  public BasedSequence intersect(BasedSequence other) {
    if (getBase() != other.getBase()) {
      return BasedSequence.NULL;
    } else if (other.getEndOffset() <= getStartOffset()) {
      return subSequence(0, 0);
    } else if (other.getStartOffset() >= getEndOffset()) {
      return subSequence(length(), length());
    } else {
      return this.baseSubSequence(
          Utils.max(getStartOffset(), other.getStartOffset()),
          Utils.min(getEndOffset(), other.getEndOffset()));
    }
  }

  @Override
  public boolean containsSomeIn(CharPredicate charSet) {
    int iMax = length();
    for (int i = 0; i < iMax; i++) {
      if (charSet.test(charAt(i))) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean containsSomeNotIn(CharPredicate charSet) {
    int iMax = length();
    for (int i = 0; i < iMax; i++) {
      if (!charSet.test(charAt(i))) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean containsOnlyIn(CharPredicate charSet) {
    return !containsSomeNotIn(charSet);
  }

  @Override
  public boolean containsOnlyNotIn(CharPredicate charSet) {
    return !containsSomeIn(charSet);
  }

  @Override
  public BasedSequence extendByAny(CharPredicate charSet, int maxCount) {
    int count = getBaseSequence().countLeading(charSet, getEndOffset(), getEndOffset() + maxCount);
    return count == 0 ? this : baseSubSequence(getStartOffset(), getEndOffset() + count);
  }

  @Override
  public BasedSequence extendByAnyNot(CharPredicate charSet) {
    return extendByAnyNot(charSet, Integer.MAX_VALUE - getEndOffset());
  }

  @Override
  public BasedSequence extendByOneOfAnyNot(CharPredicate charSet) {
    return extendByAnyNot(charSet, 1);
  }

  @Override
  public BasedSequence extendByAnyNot(CharPredicate charSet, int maxCount) {
    int count =
        getBaseSequence().countLeadingNot(charSet, getEndOffset(), getEndOffset() + maxCount);
    return count == getBaseSequence().length() - getEndOffset()
        ? this
        : baseSubSequence(getStartOffset(), getEndOffset() + count + 1);
  }

  @Override
  public final BasedSequence extendToEndOfLine(CharPredicate eolChars) {
    return extendToEndOfLine(eolChars, false);
  }

  @Override
  public final BasedSequence extendToStartOfLine(CharPredicate eolChars) {
    return extendToStartOfLine(eolChars, false);
  }

  @Override
  public final BasedSequence extendToEndOfLine(CharPredicate eolChars, boolean includeEol) {
    int endOffset = getEndOffset();

    // if already have eol then no need to check
    if (eolChars.test(lastChar())) {
      return this;
    }

    BasedSequence baseSequence = getBaseSequence();
    int endOfLine = baseSequence.endOfLine(endOffset);

    if (includeEol) {
      endOfLine =
          Math.min(
              baseSequence.length(),
              endOfLine + Math.min(baseSequence.eolStartLength(endOfLine), 1));
    }

    if (endOfLine != endOffset) {
      return baseSequence.subSequence(getStartOffset(), endOfLine);
    }
    return this;
  }

  @Override
  public BasedSequence extendToStartOfLine(CharPredicate eolChars, boolean includeEol) {
    int startOffset = getStartOffset();

    // if already have eol then no need to check
    if (eolChars.test(firstChar())) {
      return this;
    }

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

  @Override
  public BasedSequence prefixWith(CharSequence prefix) {
    return prefix == null || prefix.length() == 0
        ? this
        : PrefixedSubSequence.prefixOf(prefix.toString(), this);
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
      if (c == '\t') {
        hadTab = true;
      } else if (c == '\n') {
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

        while (columns < maxColumns
            && offset > 0
            && (getBaseSequence().charAt(offset - 1) == ' '
                || getBaseSequence().charAt(offset - 1) == '\t')) {
          columns += offsetColumns[offset - 1 - startOffset];
          if (columns > maxColumns) {
            break;
          }
          offset--;
        }
      } else {
        while (columns < maxColumns
            && offset > 0
            && (getBaseSequence().charAt(offset - 1) == ' '
                || getBaseSequence().charAt(offset - 1) == '\t')) {
          columns++;
          offset--;
        }
      }
    }

    return offset == getStartOffset() ? this : baseSubSequence(offset, getEndOffset());
  }

  @Override
  public BasedSequence prefixOf(BasedSequence other) {
    if (getBase() != other.getBase()) {
      return BasedSequence.NULL;
    } else if (other.getStartOffset() <= getStartOffset()) {
      return subSequence(0, 0);
    } else if (other.getStartOffset() >= getEndOffset()) {
      return this;
    } else {
      return this.baseSubSequence(getStartOffset(), other.getStartOffset());
    }
  }

  @Override
  public BasedSequence suffixOf(BasedSequence other) {
    if (getBase() != other.getBase()) {
      return BasedSequence.NULL;
    } else if (other.getEndOffset() >= getEndOffset()) {
      return subSequence(length(), length());
    } else if (other.getEndOffset() <= getStartOffset()) {
      return this;
    } else {
      return this.baseSubSequence(other.getEndOffset(), getEndOffset());
    }
  }

  // TEST: all these need tests
  @Override
  public Range baseLineRangeAtIndex(int index) {
    return getBaseSequence().lineRangeAt(index);
  }

  @Override
  public Pair<Integer, Integer> baseLineColumnAtIndex(int index) {
    return getBaseSequence().lineColumnAtIndex(index);
  }

  @Override
  public int baseEndOfLine(int index) {
    return getBaseSequence().endOfLine(index);
  }

  @Override
  public int baseEndOfLineAnyEOL(int index) {
    return getBaseSequence().endOfLineAnyEOL(index);
  }

  @Override
  public int baseStartOfLine(int index) {
    return getBaseSequence().startOfLine(index);
  }

  @Override
  public int baseStartOfLineAnyEOL(int index) {
    return getBaseSequence().startOfLineAnyEOL(index);
  }

  @Override
  public int baseColumnAtIndex(int index) {
    return getBaseSequence().columnAtIndex(index);
  }

  @Override
  public int baseEndOfLine() {
    return baseEndOfLine(getEndOffset());
  }

  @Override
  public int baseEndOfLineAnyEOL() {
    return baseEndOfLineAnyEOL(getEndOffset());
  }

  @Override
  public int baseColumnAtEnd() {
    return baseColumnAtIndex(getEndOffset());
  }

  @Override
  public Range baseLineRangeAtEnd() {
    return baseLineRangeAtIndex(getEndOffset());
  }

  @Override
  public Pair<Integer, Integer> baseLineColumnAtEnd() {
    return baseLineColumnAtIndex(getEndOffset());
  }

  @Override
  public int baseStartOfLine() {
    return baseStartOfLine(getStartOffset());
  }

  @Override
  public int baseStartOfLineAnyEOL() {
    return baseStartOfLineAnyEOL(getStartOffset());
  }

  @Override
  public int baseColumnAtStart() {
    return baseColumnAtIndex(getStartOffset());
  }

  @Override
  public Range baseLineRangeAtStart() {
    return baseLineRangeAtIndex(getStartOffset());
  }

  @Override
  public Pair<Integer, Integer> baseLineColumnAtStart() {
    return baseLineColumnAtIndex(getStartOffset());
  }

  static BasedSequence create(CharSequence charSequence) {
    if (charSequence == null) {
      return BasedSequence.NULL;
    } else if (charSequence instanceof BasedSequence) {
      return (BasedSequence) charSequence;
    } else {
      return SubSequence.create(charSequence);
    }
  }
}
