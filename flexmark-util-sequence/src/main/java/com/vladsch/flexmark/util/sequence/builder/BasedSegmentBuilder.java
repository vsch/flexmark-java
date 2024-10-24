package com.vladsch.flexmark.util.sequence.builder;

import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.PositionAnchor;
import com.vladsch.flexmark.util.sequence.Range;

public class BasedSegmentBuilder extends SegmentBuilderBase<BasedSegmentBuilder>
    implements IBasedSegmentBuilder<BasedSegmentBuilder> {
  private final BasedSequence baseSeq;
  final SegmentOptimizer optimizer;

  private BasedSegmentBuilder(BasedSequence baseSeq) {
    this(baseSeq, new CharRecoveryOptimizer(PositionAnchor.CURRENT));
  }

  private BasedSegmentBuilder(BasedSequence baseSeq, SegmentOptimizer optimizer) {
    super();
    this.baseSeq = baseSeq.getBaseSequence();
    this.optimizer = optimizer;
  }

  private BasedSegmentBuilder(BasedSequence baseSeq, int options) {
    this(baseSeq, new CharRecoveryOptimizer(PositionAnchor.CURRENT), options);
  }

  private BasedSegmentBuilder(BasedSequence baseSeq, SegmentOptimizer optimizer, int options) {
    super(options);
    this.baseSeq = baseSeq.getBaseSequence();
    this.optimizer = optimizer;
  }

  @Override
  public BasedSequence getBaseSequence() {
    return baseSeq;
  }

  @Override
  protected Object[] optimizeText(Object[] parts) {
    return optimizer.apply(baseSeq, parts);
  }

  @Override
  protected Object[] handleOverlap(Object[] parts) {
    // convert overlap to text from our base
    // range overlaps with last segment in the list
    Range lastSeg = (Range) parts[0];
    CharSequence text = (CharSequence) parts[1];
    Range range = (Range) parts[2];

    Range overlap;
    Range after = Range.NULL;

    if (range.getEnd() <= lastSeg.getStart()) {
      // the whole thing is before
      overlap = range;
    } else if (range.getStart() <= lastSeg.getStart()) {
      // part before, maybe some after
      overlap = Range.of(range.getStart(), Math.min(range.getEnd(), lastSeg.getEnd()));
      if (lastSeg.getEnd() < range.getEnd()) {
        after = Range.of(lastSeg.getEnd(), range.getEnd());
      }
    } else if (range.getEnd() <= lastSeg.getEnd()) {
      // all contained within
      overlap = range;
    } else {
      overlap = range.withEnd(lastSeg.getEnd());
      after = range.withStart(lastSeg.getEnd());
    }

    // append overlap to text
    if (text.length() == 0) {
      parts[1] = baseSeq.subSequence(overlap.getStart(), overlap.getEnd()).toString();
    } else {
      parts[1] =
          text.toString() + baseSeq.subSequence(overlap.getStart(), overlap.getEnd()).toString();
    }
    parts[2] = after;

    return parts;
  }

  @Override
  public String toStringWithRangesVisibleWhitespace() {
    return super.toStringWithRangesVisibleWhitespace(baseSeq);
  }

  @Override
  public String toStringWithRanges() {
    return super.toStringWithRanges(baseSeq);
  }

  @Override
  public String toStringChars() {
    return super.toString(baseSeq);
  }

  public static BasedSegmentBuilder emptyBuilder(BasedSequence sequence) {
    return new BasedSegmentBuilder(sequence);
  }

  public static BasedSegmentBuilder emptyBuilder(BasedSequence sequence, int options) {
    return new BasedSegmentBuilder(sequence, options);
  }

  public static BasedSegmentBuilder emptyBuilder(
      BasedSequence sequence, SegmentOptimizer optimizer, int options) {
    return new BasedSegmentBuilder(sequence, optimizer, options);
  }
}
