package com.vladsch.flexmark.util.sequence.builder.tree;

import com.vladsch.flexmark.util.misc.DelimitedBuilder;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.builder.BasedSegmentBuilder;
import com.vladsch.flexmark.util.sequence.builder.IBasedSegmentBuilder;
import com.vladsch.flexmark.util.sequence.builder.Seg;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Segment tree which uses offsets instead of aggregated length of segments
 * <p>
 * Used to find original base offsets in SegmentedSequence result
 * <p>
 * NOTE: although it is a SegmentTree, most of the SegmentTree functions use index
 * into sequence for context and cannot be used with offset data.
 * Their use will throw {@link IllegalStateException} if invoked.
 */
public class SegmentOffsetTree extends SegmentTree {
    final protected @NotNull int[] startIndices;

    protected SegmentOffsetTree(@NotNull int[] treeData, @NotNull byte[] segmentBytes, @NotNull int[] startIndices) {
        super(treeData, segmentBytes);
        this.startIndices = startIndices;
    }

    @NotNull
    public static SegmentOffsetTree build(@NotNull Iterable<Seg> segments, @NotNull CharSequence allText) {
        SegmentTreeData segmentTreeData = buildTreeData(segments, allText, false);
        assert segmentTreeData.startIndices != null;
        return new SegmentOffsetTree(segmentTreeData.treeData, segmentTreeData.segmentBytes, segmentTreeData.startIndices);
    }

    @NotNull
    public static SegmentOffsetTree build(@NotNull BasedSegmentBuilder builder) {
        @NotNull SegmentTreeData segmentTreeData = buildTreeData(builder.getSegments(), builder.getText(), true);
        return new SegmentTree(segmentTreeData.treeData, segmentTreeData.segmentBytes).getSegmentOffsetTree(builder.getBaseSequence());
    }

    @NotNull
    public static SegmentOffsetTree build(@NotNull BasedSequence baseSeq) {
        return baseSeq.getSegmentTree().getSegmentOffsetTree(baseSeq);
    }

    public int endOffset(int pos) {
        return super.aggrLength(pos);
    }

    public int getStartIndex(int pos) {
        return pos < 0 ? 0 : pos >= startIndices.length ? startIndices[startIndices.length - 1] : startIndices[pos];
    }

    @NotNull
    public Segment getSegment(int pos, @NotNull BasedSequence baseSeq) {
        return Segment.getSegment(segmentBytes, byteOffset(pos), pos, startIndices[pos], baseSeq);
    }

    public @Nullable SegmentTreePos findSegmentPosByOffset(int offset) {
        return findSegmentPos(offset, treeData, 0, size());
    }

    @Nullable
    public Segment getPreviousText(@NotNull Segment segment, @NotNull BasedSequence baseSeq) {
        if (segment.getPos() == 0) {
            if (segment.getStartIndex() > 0) {
                Segment textSeg = getSegment(0, -1, 0, baseSeq);
                if (textSeg.isText()) return textSeg;
            }
        } else {
            Segment prevSegment = getSegment(segment.getPos() - 1, baseSeq);
            return getNextText(prevSegment, baseSeq);
        }
        return null;
    }

    @Nullable
    public Segment getNextText(@NotNull Segment segment, @NotNull BasedSequence baseSeq) {
        if (segment.getByteOffset() + segment.getByteLength() < segmentBytes.length) {
            Segment textSeg = getSegment(segment.getByteOffset() + segment.getByteLength(), -1, segment.getEndIndex(), baseSeq);
            if (textSeg.isText()) return textSeg;
        }
        return null;
    }

    public @Nullable Segment findSegmentByOffset(int offset, @NotNull BasedSequence baseSeq, @Nullable Segment hint) {
        int startPos = 0;
        int endPos = size();

        SegmentTreePos treePos = super.findSegmentPos(offset, startPos, endPos);
        if (treePos != null) {
            return Segment.getSegment(segmentBytes, byteOffset(treePos.pos), treePos.pos, startIndices[treePos.pos], baseSeq);
        }

        return null;
    }

    @NotNull
    public String toString(@NotNull BasedSequence baseSeq) {
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
        //NOTE: used by toString() so can only deprecate
        return super.aggrLength(pos);
    }

    @Deprecated
    @Override
    public @Nullable SegmentTreePos findSegmentPos(int index) {
        throw new IllegalStateException("Method in SegmentOffsetTree should not be used");
    }

    @Deprecated
    @Override
    public @Nullable Segment findSegment(int index, @NotNull BasedSequence baseSeq, @Nullable Segment hint) {
        throw new IllegalStateException("Method in SegmentOffsetTree should not be used");
    }

    @Deprecated
    @Override
    public @Nullable Segment findSegment(int index, int startPos, int endPos, @NotNull BasedSequence baseSeq, @Nullable Segment hint) {
        throw new IllegalStateException("Method in SegmentOffsetTree should not be used");
    }

    @Deprecated
    @Override
    public @NotNull SegmentTreeRange getSegmentRange(int startIndex, int endIndex, int startPos, int endPos, @NotNull BasedSequence baseSeq, @Nullable Segment hint) {
        return super.getSegmentRange(startIndex, endIndex, startPos, endPos, baseSeq, hint);
    }

    @Deprecated
    @Override
    public void addSegments(@NotNull IBasedSegmentBuilder<?> builder, @NotNull SegmentTreeRange treeRange) {
        throw new IllegalStateException("Method in SegmentOffsetTree should not be used");
    }

    @Deprecated
    @Override
    public void addSegments(@NotNull IBasedSegmentBuilder<?> builder, int startIndex, int endIndex, int startOffset, int endOffset, int startPos, int endPos) {
        throw new IllegalStateException("Method in SegmentOffsetTree should not be used");
    }

    @Deprecated
    @Override
    public @Nullable SegmentTreePos findSegmentPos(int index, int startPos, int endPos) {
        throw new IllegalStateException("Method in SegmentOffsetTree should not be used");
    }

    @Deprecated
    @Override
    public @Nullable Segment getPrevAnchor(int pos, @NotNull BasedSequence baseSeq) {
        throw new IllegalStateException("Method in SegmentOffsetTree should not be used");
    }
}
