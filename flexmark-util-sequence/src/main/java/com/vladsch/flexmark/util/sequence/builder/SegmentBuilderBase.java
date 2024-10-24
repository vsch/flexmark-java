package com.vladsch.flexmark.util.sequence.builder;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;

import com.vladsch.flexmark.util.misc.DelimitedBuilder;
import com.vladsch.flexmark.util.sequence.Range;
import com.vladsch.flexmark.util.sequence.SequenceUtils;
import java.util.Arrays;
import java.util.Iterator;
import java.util.function.UnaryOperator;

public class SegmentBuilderBase<S extends SegmentBuilderBase<S>> implements ISegmentBuilder<S> {
  private static final int MIN_PART_CAPACITY = 8;
  private static final int[] EMPTY_PARTS = {};

  private int[] parts = EMPTY_PARTS;
  private int partsSize = 0;
  private int anchorsSize = 0;

  private int startOffset = Range.NULL.getStart();
  private int endOffset = Range.NULL.getEnd();
  private int length = 0;
  private final SegmentStats stats; // committed and dangling text stats
  private final SegmentStats textStats; // dangling text stats
  final int options;

  // NOTE: all text accumulation is in the string builder, dangling text segment is between
  // immutableOffset and text.length()
  private final StringBuilder text =
      new StringBuilder(); // text segment ranges come from this CharSequence
  private int immutableOffset = 0; // text offset for all committed text segments

  private static int[] ensureCapacity(int[] prev, int size) {
    int prevSize = prev.length / 2;
    if (prevSize <= size) {
      int nextSize = Math.max(MIN_PART_CAPACITY, Math.max(prevSize + prevSize >> 1, size));
      return Arrays.copyOf(prev, nextSize * 2);
    }
    return prev;
  }

  private void ensureCapacity(int size) {
    parts = ensureCapacity(parts, size + 1);
  }

  protected SegmentBuilderBase() {
    this(F_INCLUDE_ANCHORS /*| F_TRACK_FIRST256*/);
  }

  protected SegmentBuilderBase(int options) {
    this.options = options & (F_INCLUDE_ANCHORS | F_TRACK_FIRST256);
    stats = new SegmentStats((options & F_TRACK_FIRST256) != 0);
    textStats = new SegmentStats((options & F_TRACK_FIRST256) != 0);
  }

  @Override
  public int getStartOffset() {
    return startOffset <= endOffset ? startOffset : -1;
  }

  public int getStartOffsetIfNeeded() {
    int startOffset = getStartOffset();
    Seg seg = getSegOrNull(0);
    return startOffset != -1 && seg != null && seg.isBase() && startOffset != seg.getStart()
        ? startOffset
        : -1;
  }

  @Override
  public int getEndOffset() {
    return endOffset >= startOffset ? endOffset : -1;
  }

  public int getEndOffsetIfNeeded() {
    int endOffset = getEndOffset();
    Seg seg = getSegOrNull(partsSize - 1);
    return endOffset != -1 && seg != null && seg.isBase() && endOffset != seg.getEnd()
        ? endOffset
        : -1;
  }

  @Override
  public boolean isEmpty() {
    return length == 0;
  }

  @Override
  public Range getBaseSubSequenceRange() {
    if (partsSize == 1 && !haveDanglingText()) {
      Seg seg = getSeg(partsSize - 1);
      if (seg.length() != 0 && anchorsSize == 1) seg = getSeg(partsSize - 2);
      if (seg.isBase() && seg.getStart() == startOffset && seg.getEnd() == endOffset) {
        return seg.getRange();
      }
    }
    return null;
  }

  @Override
  public boolean haveOffsets() {
    return startOffset <= endOffset;
  }

  @Override
  public int size() {
    return partsSize + (haveDanglingText() ? 1 : 0);
  }

  @Override
  public CharSequence getText() {
    return text;
  }

  @Override
  public int noAnchorsSize() {
    return size() - anchorsSize;
  }

  @Override
  public int length() {
    return length;
  }

  @Override
  public int getTextLength() {
    return stats.getTextLength();
  }

  @Override
  public Iterator<Object> iterator() {
    return new PartsIterator(this);
  }

  private static class PartsIterator implements Iterator<Object> {
    private final SegmentBuilderBase<?> builder;
    private int nextIndex;

    PartsIterator(SegmentBuilderBase<?> builder) {
      this.builder = builder;
    }

    @Override
    public boolean hasNext() {
      return nextIndex < builder.size();
    }

    @Override
    public Object next() {
      return builder.getPart(nextIndex++);
    }
  }

  @Override
  public Iterable<Seg> getSegments() {
    return new SegIterable(this);
  }

  private static class SegIterable implements Iterable<Seg> {
    private final SegmentBuilderBase<?> builder;

    SegIterable(SegmentBuilderBase<?> builder) {
      this.builder = builder;
    }

    @Override
    public Iterator<Seg> iterator() {
      return new SegIterator(builder);
    }
  }

  private static class SegIterator implements Iterator<Seg> {
    private final SegmentBuilderBase<?> builder;
    private int nextIndex;

    SegIterator(SegmentBuilderBase<?> builder) {
      this.builder = builder;
    }

    @Override
    public boolean hasNext() {
      return nextIndex < builder.size();
    }

    @Override
    public Seg next() {
      return builder.getSegPart(nextIndex++);
    }
  }

  @Override
  public boolean isIncludeAnchors() {
    return (options & F_INCLUDE_ANCHORS) != 0;
  }

  /**
   * Span for offsets of this list
   *
   * @return -ve if no information in the list, or span of offsets
   */
  @Override
  public int getSpan() {
    return startOffset > endOffset ? -1 : endOffset - startOffset;
  }

  private Seg getSegOrNull(int index) {
    int i = index * 2;
    return i + 1 >= parts.length ? null : Seg.segOf(parts[i], parts[i + 1]);
  }

  private Seg getSeg(int index) {
    int i = index * 2;
    return i + 1 >= parts.length ? Seg.NULL : Seg.segOf(parts[i], parts[i + 1]);
  }

  public Object getPart(int index) {
    if (index == partsSize && haveDanglingText()) {
      // return dangling text
      return text.subSequence(immutableOffset, text.length());
    }

    int i = index * 2;
    Seg seg = i + 1 >= parts.length ? Seg.NULL : Seg.segOf(parts[i], parts[i + 1]);
    return seg.isBase()
        ? seg.getRange()
        : seg.isText() ? text.subSequence(seg.getTextStart(), seg.getTextEnd()) : Range.NULL;
  }

  private Seg getSegPart(int index) {
    if (index == partsSize && haveDanglingText()) {
      // return dangling text
      return Seg.textOf(
          immutableOffset, text.length(), textStats.isTextFirst256(), textStats.isRepeatedText());
    }

    int i = index * 2;
    return i + 1 >= parts.length ? Seg.NULL : Seg.segOf(parts[i], parts[i + 1]);
  }

  private void setSegEnd(int index, int endOffset) {
    int i = index * 2;

    // adjust anchor count
    if (parts[i] == endOffset) {
      if (parts[i] != parts[i + 1]) anchorsSize++;
    } else if (parts[i] == parts[i + 1]) anchorsSize--;

    parts[i + 1] = endOffset;
  }

  private void addSeg(int startOffset, int endOffset) {
    ensureCapacity(partsSize);
    int i = partsSize * 2;
    parts[i] = startOffset;
    parts[i + 1] = endOffset;
    partsSize++;
    if (startOffset == endOffset) anchorsSize++;
  }

  private Seg lastSegOrNull() {
    return partsSize == 0 ? null : getSegOrNull(partsSize - 1);
  }

  private boolean haveDanglingText() {
    return text.length() > immutableOffset;
  }

  protected Object[] optimizeText(Object[] parts) {
    return parts;
  }

  protected Object[] handleOverlap(Object[] parts) {
    // range overlaps with last segment in the list
    Range lastSeg = (Range) parts[0];
    CharSequence text = (CharSequence) parts[1];
    Range range = (Range) parts[2];

    if (lastSeg.getEnd() < range.getEnd()) {
      // there is a part of the overlap outside the last seg range
      if (text.length() > 0) {
        // append the chopped off base part
        parts[2] = Range.of(lastSeg.getEnd(), range.getEnd());
      } else {
        // extend the last base seg to include this range
        parts[0] = lastSeg.withEnd(range.getEnd());
        parts[2] = Range.NULL;
      }
    } else {
      parts[2] = Range.NULL;
    }
    return parts;
  }

  private void processParts(
      int segStart,
      int segEnd,
      boolean resolveOverlap,
      boolean nullNextRange,
      UnaryOperator<Object[]> transform) {
    CharSequence text = this.text.subSequence(immutableOffset, this.text.length());

    Seg lastSeg = lastSegOrNull();
    Range prevRange = lastSeg == null || !lastSeg.isBase() ? Range.NULL : lastSeg.getRange();

    if (!isIncludeAnchors() && haveOffsets()) {
      // need to use max with endOffset since anchors are not stored
      if (prevRange.isNull() || prevRange.getEnd() < endOffset)
        prevRange = Range.emptyOf(endOffset);
    }

    if (!haveOffsets()) startOffset = segStart;

    // NOTE: cannot incorporate segEnd if overlap is being resolved
    if (!resolveOverlap) endOffset = Math.max(endOffset, segEnd);

    Object[] parts = {
      prevRange, text, nullNextRange ? Range.NULL : Range.of(segStart, segEnd),
    };

    Object[] originalParts = parts.clone();
    Object[] optimizedText = transform.apply(parts);

    if (Arrays.equals(optimizedText, originalParts)) {
      // nothing changed, make sure it was not called to resolve overlap

      if (segEnd > segStart || isIncludeAnchors()) {
        // NOTE: only commit text if adding real range after it
        if (text.length() > 0) {
          commitText();
        }

        length += segEnd - segStart;
        addSeg(segStart, segEnd);
      }
    } else {
      // remove dangling text information
      textStats.commitText();
      stats.commitText();
      stats.remove(textStats);
      textStats.clear();
      length -= text.length();
      this.text.delete(immutableOffset, this.text.length());

      if (lastSeg != null && lastSeg.isBase()) {
        // remove last seg from parts, it will be added on return
        length -= lastSeg.length();
        partsSize--;
        if (lastSeg.length() == 0) anchorsSize--;
      }

      int iMax = optimizedText.length;
      int optStartOffset = MAX_VALUE;
      int optEndOffset = MIN_VALUE;

      for (int i = 0; i < iMax; i++) {
        Object oPart = optimizedText[i];
        if (oPart instanceof CharSequence) {
          CharSequence optText = (CharSequence) oPart;
          if (optText.length() > 0) {
            addText(optText);
          }
        } else if (oPart instanceof Range) {
          if (((Range) oPart).isNotNull()) {
            int optRangeStart = ((Range) oPart).getStart();
            int optRangeEnd = ((Range) oPart).getEnd();

            if (optStartOffset == MAX_VALUE) optStartOffset = optRangeStart;

            if (optRangeStart < optEndOffset) {
              throw new IllegalStateException(
                  String.format(
                      "Accumulated range [%d, %d) overlaps Transformed Range[%d]: [%d, %d)",
                      optStartOffset, optEndOffset, i, optRangeStart, optRangeEnd));
            }

            optEndOffset = Math.max(optEndOffset, optRangeEnd);

            boolean haveDanglingText = haveDanglingText();

            if (haveDanglingText && resolveOverlap) {
              processParts(optRangeStart, optRangeEnd, false, false, this::optimizeText);
            } else {
              // adjust offsets since they could have expanded
              startOffset = Math.min(startOffset, optRangeStart);
              endOffset = Math.max(endOffset, optRangeEnd);

              if (optRangeStart != optRangeEnd || isIncludeAnchors()) {
                if (haveDanglingText) {
                  commitText();
                }

                // add base segment
                length += optRangeEnd - optRangeStart;
                addSeg(optRangeStart, optRangeEnd);
              }
            }
          }
        } else if (oPart != null) {
          throw new IllegalStateException("Invalid optimized part type " + oPart.getClass());
        }
      }
    }
  }

  private void commitText() {
    addSeg(
        Seg.getTextStart(immutableOffset, textStats.isTextFirst256()),
        Seg.getTextEnd(text.length(), textStats.isRepeatedText()));
    immutableOffset = text.length();
    stats.commitText();
    textStats.clear();
  }

  private void addText(CharSequence optText) {
    length += optText.length();
    text.append(optText);

    stats.addText(optText);
    textStats.addText(optText);
  }

  /**
   * append anchor in original sequence coordinates, no checking is done other than overlap with
   * tail range fast
   *
   * @param offset offset in original sequence
   * @return this
   */
  @Override
  public S appendAnchor(int offset) {
    return append(offset, offset);
  }

  /**
   * append range in original sequence coordinates, no checking is done other than overlap with tail
   * range fast
   *
   * @param range range in original sequence
   * @return this
   */
  @Override
  public S append(Range range) {
    return append(range.getStart(), range.getEnd());
  }

  /**
   * append range in original sequence coordinates, no checking is done other than overlap with tail
   * range fast
   *
   * @param startOffset start offset in original sequence
   * @param endOffset end offset in original sequence
   * @return this
   */
  @Override
  public S append(int startOffset, int endOffset) {
    if (endOffset < 0 || startOffset > endOffset) {
      return (S) this;
    }

    int rangeSpan = endOffset - startOffset;
    if (rangeSpan == 0 && (!isIncludeAnchors() || startOffset < this.endOffset)) {
      if (startOffset >= this.endOffset) {
        // can optimize text
        if (haveDanglingText()) {
          processParts(startOffset, endOffset, false, false, this::optimizeText);
        } else {
          if (!haveOffsets()) this.startOffset = startOffset;
          this.endOffset = startOffset;
        }
      }
      return (S) this;
    }

    if (this.endOffset > startOffset) {
      // overlap
      processParts(startOffset, endOffset, true, false, this::handleOverlap);
    } else if (this.endOffset == startOffset) {
      // adjacent, merge the two if no text between them
      if (haveDanglingText()) {
        processParts(startOffset, endOffset, false, false, this::optimizeText);
      } else {
        this.endOffset = endOffset;
        length += rangeSpan;

        if (partsSize == 0) {
          // no last segment, add this one
          addSeg(startOffset, endOffset);
        } else {
          // combine this with the last segment
          setSegEnd(partsSize - 1, endOffset);
        }
      }
    } else {
      // disjoint

      if (haveDanglingText()) {
        processParts(startOffset, endOffset, false, false, this::optimizeText);
      } else {
        if (!haveOffsets()) this.startOffset = startOffset;
        this.endOffset = endOffset;
        length += rangeSpan;
        addSeg(startOffset, endOffset);
      }
    }
    return (S) this;
  }

  @Override
  public S append(CharSequence text) {
    int length = text.length();
    if (length != 0) {
      stats.addText(text);
      textStats.addText(text);

      this.text.append(text);
      this.length += length;
    }
    return (S) this;
  }

  S append(char c) {
    stats.addText(c);
    textStats.addText(c);

    text.append(c);
    length++;

    return (S) this;
  }

  S append(char c, int repeat) {
    if (repeat > 0) {
      stats.addText(c, repeat);
      textStats.addText(c, repeat);
      length += repeat;

      while (repeat-- > 0) text.append(c);
    }
    return (S) this;
  }

  private String toString(
      CharSequence chars,
      CharSequence rangePrefix,
      CharSequence rangeSuffix,
      UnaryOperator<CharSequence> textMapper) {
    if (endOffset > chars.length()) {
      throw new IllegalArgumentException(
          "baseSequence length() must be at least " + endOffset + ", got: " + chars.length());
    }

    if (haveDanglingText() && haveOffsets()) {
      processParts(endOffset, endOffset, false, true, this::optimizeText);
    }

    StringBuilder out = new StringBuilder();

    int iMax = partsSize;
    for (int i = 0; i < iMax; i++) {
      Seg part = getSeg(i);

      if (!part.isBase()) {
        out.append(textMapper.apply(text.subSequence(part.getTextStart(), part.getTextEnd())));
      } else {
        out.append(rangePrefix)
            .append(textMapper.apply(chars.subSequence(part.getStart(), part.getEnd())))
            .append(rangeSuffix);
      }
    }

    if (haveDanglingText()) {
      out.append(textMapper.apply(text.subSequence(immutableOffset, text.length())));
    }

    return out.toString();
  }

  @Override
  public String toStringWithRangesVisibleWhitespace(CharSequence chars) {
    return toString(chars, "⟦", "⟧", SequenceUtils::toVisibleWhitespaceString);
  }

  @Override
  public String toStringWithRanges(CharSequence chars) {
    return toString(chars, "⟦", "⟧", UnaryOperator.identity());
  }

  @Override
  public String toString(CharSequence chars) {
    return toString(chars, "", "", UnaryOperator.identity());
  }

  public String toStringPrep() {
    if (haveDanglingText() && haveOffsets()) {
      processParts(endOffset, endOffset, false, true, this::optimizeText);
    }
    return toString();
  }

  @Override
  public String toString() {
    DelimitedBuilder sb = new DelimitedBuilder(", ");
    sb.append(this.getClass().getSimpleName()).append("{");

    if (haveOffsets()) {
      sb.append("[").append(startOffset).mark().append(endOffset).unmark().append(")").mark();
    } else {
      sb.append("NULL").mark();
    }

    //        // CAUTION: this method is invoked from the debugger and any non-pure method
    // invocation messes up internal structures
    SegmentStats committedStats = stats.committedCopy();
    sb.append(committedStats)
        .mark()
        .append("l=")
        .append(length)
        .mark()
        .append("sz=")
        .append(size())
        .mark()
        .append("na=")
        .append(noAnchorsSize());

    if (size() > 0) sb.append(": ");

    int iMax = partsSize;
    for (int i = 0; i < iMax; i++) {
      Seg part = getSeg(i);
      sb.append(part.toString(text)).mark();
    }

    if (haveDanglingText()) {
      Seg part =
          Seg.textOf(
              immutableOffset,
              text.length(),
              textStats.isTextFirst256(),
              textStats.isRepeatedText());
      sb.append(part.toString(text)).mark();
    }

    sb.unmark().append(" }");
    return sb.toString();
  }
}
