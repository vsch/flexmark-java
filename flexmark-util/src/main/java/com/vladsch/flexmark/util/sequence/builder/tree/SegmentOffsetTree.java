package com.vladsch.flexmark.util.sequence.builder.tree;

import com.vladsch.flexmark.util.DelimitedBuilder;
import com.vladsch.flexmark.util.sequence.BasedSequence;
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

    public int endOffset(int pos) {
        return super.aggrLength(pos);
    }

    public int getStartIndex(int pos) {
        return pos < 0 ? 0 : pos >= startIndices.length ? startIndices.length - 1 : startIndices[pos];
    }

    @NotNull
    public Segment getSegment(int pos, @NotNull BasedSequence baseSeq) {
        return Segment.getSegment(segmentBytes, byteOffset(pos), pos, startIndices[pos], baseSeq);
    }

    public @Nullable SegmentTreePos findSegmentPosByOffset(int offset) {
        return findSegmentPos(offset, treeData, 0, size());
    }

    public @Nullable Segment findSegmentByOffset(int offset, @NotNull BasedSequence baseSeq, @Nullable Segment hint) {
        int startPos = 0;
        int endPos = size();

//        if (hint != null) {
//            // NOTE: first try around cached segment for this index
//            assert hint.offsetNotInSegment(offset) : String.format("FindSegment should not be called, index %d is in range [%d, %d) of hint segment: %s", offset, hint.getStartOffset(), hint.getEndOffset(), hint);
//
//            if (offset >= hint.getStartOffset()) {
//                if (hint.pos + 1 >= endPos) return hint;
//                int nextEnd = endOffset(hint.pos + 1);
//                if (offset < nextEnd) {
//                    // FIX: add stats to track this
////                    System.out.println("Using next segment");
//                    return Segment.getSegment(segmentBytes, byteOffset(hint.pos + 1), hint.pos + 1, startIndices[hint.pos + 1], baseSeq);
//                }
//
//                // can skip next one too
//                startPos = hint.pos + 2;
//            } else {
//                // see if previous contains index
//                if (hint.pos == startPos) return hint;
//
//                int prevPrevEnd = endOffset(hint.pos - 2);
//                if (offset >= prevPrevEnd) {
//                    // it is previous one
//                    // FIX: add stats to track this
////                    System.out.println("Using previous segment");
//                    return Segment.getSegment(segmentBytes, byteOffset(hint.pos - 1), hint.pos - 1, startIndices[hint.pos - 1], baseSeq);
//                }
//                // previous one can be skipped
//                endPos = hint.pos - 1;
//            }
//        }
//
//        // NOTE: most of the time char sequence access starts at 0, so we try the start pos
//        if (startPos >= 0) {
//            int firstLength = endOffset(startPos);
//            if (offset < firstLength) {
//                int prevLength = endOffset(startPos - 1);
//                if (offset >= prevLength) {
//                    // FIX: add stats to track this
////                    System.out.println("Using first segment");
//                    return Segment.getSegment(segmentBytes, byteOffset(startPos), startPos, startIndices[startPos], baseSeq);
//                }
//                // first one is too far, we can skip it
//                endPos = startPos;
//            } else {
//                // first one can be skipped
//                startPos = startPos + 1;
//            }
//        }
//
//        // NOTE: failing that we try the last segment in case it is backwards scan through sequence
//        if (endPos - 1 >= startPos) {
//            // check last one for match
//            int secondToLastLength = endOffset(endPos - 2);
//            if (offset >= secondToLastLength) {
//                int lastLength = endOffset(endPos - 1);
//                if (offset >= lastLength) return null; /* beyond last segment*/
//
//                // FIX: add stats to track this
////                System.out.println("Using last segment");
//                return Segment.getSegment(segmentBytes, byteOffset(endPos - 1), endPos - 1, startIndices[endPos - 1], baseSeq);
//            } else {
//                // previous to last can be skipped
//                endPos = endPos - 1;
//            }
//        }

        // NOTE: all optimizations failed, but not completely wasted since they served to shorten the search range.
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
