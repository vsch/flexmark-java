package com.vladsch.flexmark.util.sequence.builder.tree;

import com.vladsch.flexmark.util.misc.DelimitedBuilder;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.builder.IBasedSegmentBuilder;
import com.vladsch.flexmark.util.sequence.builder.Seg;

/**
 * Segment tree which uses offsets instead of aggregated length of segments
 *
 * <p>Used to find original base offsets in SegmentedSequence result
 *
 * <p>NOTE: although it is a SegmentTree, most of the SegmentTree functions use index into sequence
 * for context and cannot be used with offset data. Their use will throw {@link
 * IllegalStateException} if invoked.
 */
public class SegmentOffsetTree extends SegmentTree {
  private final int[] startIndices;

  protected SegmentOffsetTree(int[] treeData, byte[] segmentBytes, int[] startIndices) {
    super(treeData, segmentBytes);
    this.startIndices = startIndices;
  }

  public static SegmentOffsetTree build(Iterable<Seg> segments, CharSequence allText) {
    SegmentTreeData segmentTreeData = buildTreeData(segments, allText, false);
    return new SegmentOffsetTree(
        segmentTreeData.treeData, segmentTreeData.segmentBytes, segmentTreeData.startIndices);
  }

  @Override
  public Segment getSegment(int pos, BasedSequence baseSeq) {
    return Segment.getSegment(segmentBytes, byteOffset(pos), pos, startIndices[pos], baseSeq);
  }

  Segment getPreviousText(Segment segment, BasedSequence baseSeq) {
    if (segment.getPos() == 0) {
      if (segment.getStartIndex() > 0) {
        Segment textSeg = getSegment(0, -1, 0, baseSeq);
        if (textSeg.isText()) {
          return textSeg;
        }
      }
    } else {
      Segment prevSegment = getSegment(segment.getPos() - 1, baseSeq);
      return getNextText(prevSegment, baseSeq);
    }
    return null;
  }

  Segment getNextText(Segment segment, BasedSequence baseSeq) {
    if (segment.getByteOffset() + segment.getByteLength() < segmentBytes.length) {
      Segment textSeg =
          getSegment(
              segment.getByteOffset() + segment.getByteLength(),
              -1,
              segment.getEndIndex(),
              baseSeq);
      if (textSeg.isText()) {
        return textSeg;
      }
    }
    return null;
  }

  Segment findSegmentByOffset(int offset, BasedSequence baseSeq, Segment hint) {
    int startPos = 0;
    int endPos = size();

    SegmentTreePos treePos = super.findSegmentPos(offset, startPos, endPos);
    if (treePos != null) {
      return Segment.getSegment(
          segmentBytes, byteOffset(treePos.pos), treePos.pos, startIndices[treePos.pos], baseSeq);
    }

    return null;
  }

  @Override
  public String toString(BasedSequence baseSeq) {
    DelimitedBuilder out = new DelimitedBuilder(", ");
    out.append(getClass().getSimpleName()).append("{aggr: {");
    int iMax = size();
    for (int i = 0; i < iMax; i++) {
      out.append("[").append(aggrLength(i)).append(", ").append(byteOffset(i)).append(":");
      out.append(", :").append(startIndices[i]);
      out.append("]").mark();
    }

    out.unmark().append(" }, seg: { ");
    int offset = 0;
    while (offset < segmentBytes.length) {
      Segment segment = Segment.getSegment(segmentBytes, offset, 0, 0, baseSeq);
      out.append(offset).append(":").append(segment).mark();
      offset += segment.getByteLength();
    }
    out.unmark().append(" } }");
    return out.toString();
  }

  @Deprecated
  @Override
  public boolean hasPreviousAnchor(int pos) {
    return false;
  }

  @Deprecated
  @Override
  public int previousAnchorOffset(int pos) {
    return 0;
  }

  @Deprecated
  @Override
  public int aggrLength(int pos) {
    // NOTE: used by toString() so can only deprecate
    return super.aggrLength(pos);
  }

  @Deprecated
  @Override
  public Segment findSegment(int index, BasedSequence baseSeq, Segment hint) {
    throw new IllegalStateException("Method in SegmentOffsetTree should not be used");
  }

  @Deprecated
  @Override
  public Segment findSegment(
      int index, int startPos, int endPos, BasedSequence baseSeq, Segment hint) {
    throw new IllegalStateException("Method in SegmentOffsetTree should not be used");
  }

  @Deprecated
  @Override
  public SegmentTreeRange getSegmentRange(
      int startIndex, int endIndex, int startPos, int endPos, BasedSequence baseSeq, Segment hint) {
    return super.getSegmentRange(startIndex, endIndex, startPos, endPos, baseSeq, hint);
  }

  @Deprecated
  @Override
  public void addSegments(IBasedSegmentBuilder<?> builder, SegmentTreeRange treeRange) {
    throw new IllegalStateException("Method in SegmentOffsetTree should not be used");
  }

  @Deprecated
  @Override
  public void addSegments(
      IBasedSegmentBuilder<?> builder,
      int startIndex,
      int endIndex,
      int startOffset,
      int endOffset,
      int startPos,
      int endPos) {
    throw new IllegalStateException("Method in SegmentOffsetTree should not be used");
  }

  @Deprecated
  @Override
  public SegmentTreePos findSegmentPos(int index, int startPos, int endPos) {
    throw new IllegalStateException("Method in SegmentOffsetTree should not be used");
  }

  @Deprecated
  @Override
  public Segment getPrevAnchor(int pos, BasedSequence baseSeq) {
    throw new IllegalStateException("Method in SegmentOffsetTree should not be used");
  }
}
