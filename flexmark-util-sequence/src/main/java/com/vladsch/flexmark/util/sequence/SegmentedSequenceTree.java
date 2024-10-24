package com.vladsch.flexmark.util.sequence;

import com.vladsch.flexmark.util.sequence.builder.IBasedSegmentBuilder;
import com.vladsch.flexmark.util.sequence.builder.ISegmentBuilder;
import com.vladsch.flexmark.util.sequence.builder.SegmentedSequenceStats;
import com.vladsch.flexmark.util.sequence.builder.tree.Segment;
import com.vladsch.flexmark.util.sequence.builder.tree.SegmentTree;
import com.vladsch.flexmark.util.sequence.builder.tree.SegmentTreeRange;

/**
 * A BasedSequence which consists of segments of other BasedSequences NOTE: very efficient for
 * random access but extremely wasteful with space by allocating 4 bytes per character in the
 * sequence with corresponding construction penalty use SegmentedSequenceTree which is binary tree
 * based segmented sequence with minimal overhead and optimized to give penalty free random access
 * for most applications.
 */
final class SegmentedSequenceTree extends SegmentedSequence {
  private final SegmentTree segmentTree; // segment tree
  private final int
      startIndex; // start index of this sub-sequence in the segment tree, 0 for original
  private final int startPos; // start position for segments of this sequence in the tree
  private final int endPos; // end position for segments of this sequence in the tree
  private final ThreadLocal<Cache> cache = new ThreadLocal<>();

  private static class Cache {
    final Segment segment;
    final CharSequence chars;
    final int indexDelta;

    Cache(Segment segment, CharSequence chars, int startIndex) {
      this.segment = segment;
      this.chars = chars;
      indexDelta = startIndex - segment.getStartIndex();
    }

    char charAt(int index) {
      return chars.charAt(index + indexDelta);
    }

    int charIndex(int index) {
      return index + indexDelta;
    }
  }

  private SegmentedSequenceTree(
      BasedSequence baseSeq, int startOffset, int endOffset, int length, SegmentTree segmentTree) {
    super(baseSeq, startOffset, endOffset, length);
    this.segmentTree = segmentTree;
    startIndex = 0;
    startPos = 0;
    endPos = segmentTree.size();
  }

  private SegmentedSequenceTree(
      BasedSequence baseSeq, SegmentTree segmentTree, SegmentTreeRange subSequenceRange) {
    super(
        baseSeq, subSequenceRange.startOffset, subSequenceRange.endOffset, subSequenceRange.length);
    this.segmentTree = segmentTree;
    startIndex = subSequenceRange.startIndex;
    startPos = subSequenceRange.startPos;
    endPos = subSequenceRange.endPos;
  }

  private Cache getCache(int index) {
    Cache cache = this.cache.get();

    if (cache == null || cache.segment.notInSegment(index + startIndex)) {
      Segment segment =
          segmentTree.findSegment(
              index + startIndex, startPos, endPos, baseSeq, cache == null ? null : cache.segment);

      cache = new Cache(segment, segment.getCharSequence(), startIndex);
      this.cache.set(cache);
    }
    return cache;
  }

  private Segment getCachedSegment() {
    Cache cache = this.cache.get();
    return cache == null ? null : cache.segment;
  }

  @Override
  public int getIndexOffset(int index) {
    if (index == length) {
      Cache cache = getCache(index - 1);
      CharSequence charSequence = cache.chars;
      if (charSequence instanceof BasedSequence) {
        return ((BasedSequence) charSequence).getIndexOffset(cache.charIndex(index));
      }

      return -1;
    }

    SequenceUtils.validateIndexInclusiveEnd(index, length());

    Cache cache = getCache(index);
    CharSequence charSequence = cache.chars;
    if (charSequence instanceof BasedSequence) {
      return ((BasedSequence) charSequence).getIndexOffset(cache.charIndex(index));
    }

    return -1;
  }

  @Override
  public void addSegments(IBasedSegmentBuilder<?> builder) {
    segmentTree.addSegments(
        builder, startIndex, startIndex + length, startOffset, endOffset, startPos, endPos);
  }

  @Override
  public SegmentTree getSegmentTree() {
    return segmentTree;
  }

  @Override
  public char charAt(int index) {
    SequenceUtils.validateIndex(index, length());
    return getCache(index).charAt(index);
  }

  @Override
  public BasedSequence subSequence(int startIndex, int endIndex) {
    if (startIndex == 0 && endIndex == length) {
      return this;
    }

    SequenceUtils.validateStartEnd(startIndex, endIndex, length());
    SegmentTreeRange subSequenceRange =
        segmentTree.getSegmentRange(
            startIndex + this.startIndex,
            endIndex + this.startIndex,
            startPos,
            endPos,
            baseSeq,
            getCachedSegment());
    return new SegmentedSequenceTree(baseSeq, segmentTree, subSequenceRange);
  }

  /**
   * Base Constructor
   *
   * @param baseSeq base sequence
   * @param builder builder containing segments for this sequence
   * @return segmented sequence
   */
  static SegmentedSequenceTree create(BasedSequence baseSeq, ISegmentBuilder<?> builder) {
    SegmentTree segmentTree = SegmentTree.build(builder.getSegments(), builder.getText());

    if (baseSeq.anyOptions(F_COLLECT_SEGMENTED_STATS)) {
      SegmentedSequenceStats stats = baseSeq.getOption(SEGMENTED_STATS);
      if (stats != null) {
        stats.addStats(
            builder.noAnchorsSize(),
            builder.length(),
            segmentTree.getTreeData().length * 4 + segmentTree.getSegmentBytes().length);
      }
    }

    return new SegmentedSequenceTree(
        baseSeq.getBaseSequence(),
        builder.getStartOffset(),
        builder.getEndOffset(),
        builder.length(),
        segmentTree);
  }
}
