package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.DelimitedBuilder;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Binary search tree of sequence segments
 */
public class SegmentTree {
    final public static int MAX_VALUE = Integer.MAX_VALUE >> 2;
    final public static int F_ANCHOR_FLAGS = ~MAX_VALUE;
    final public static int F_ANCHOR_OFFSET = Integer.MAX_VALUE & ~MAX_VALUE;

    final private int[] treeData;    // tuples of aggrLength, segByteOffset with flags for prev anchor offset of 1 to 5
    final private byte[] segmentBytes;      // byte serialized segments

    public SegmentTree(int[] treeData, byte[] segmentBytes) {
        this.treeData = treeData;
        this.segmentBytes = segmentBytes;
    }

    public int[] getTreeData() {
        return treeData;
    }

    public byte[] getSegmentBytes() {
        return segmentBytes;
    }

    public int size() {
        return treeData.length / 2;
    }

    public int aggrLength(int pos) {
        return treeData[pos << 1];
    }

    public int byteOffsetData(int pos) {
        return treeData[(pos << 1) + 1];
    }

    public int byteOffset(int pos) {
        return getByteOffset(treeData[(pos << 1) + 1]);
    }

    public static int getByteOffset(int byteOffsetData) {
        int offset = byteOffsetData & MAX_VALUE;
        return offset == MAX_VALUE ? -1 : offset;
    }

    public static int getAnchorOffset(int byteOffsetData) {
        int anchorBits = byteOffsetData & F_ANCHOR_FLAGS;
        return anchorBits != 0 ? (anchorBits < 0 ? 0x0004 : 0) | ((byteOffsetData & F_ANCHOR_OFFSET) >> 29) : 0;
    }

    public boolean hasPreviousAnchor(int pos) {
        return getAnchorOffset(treeData[(pos << 1) + 1]) > 0;
    }

    public int previousAnchorOffset(int pos) {
        int byteOffsetData = byteOffsetData(pos);
        return getByteOffset(byteOffsetData) - getAnchorOffset(byteOffsetData);
    }

    @Nullable
    public SegTreePos findSegmentPos(int index) {
        return findSegmentPos(index, treeData, 0, treeData.length >> 1);
    }

    @Nullable
    public Segment findSegment(int index, @NotNull BasedSequence basedSequence) {
        SegTreePos treePos = findSegmentPos(index, treeData, 0, treeData.length >> 1);
        if (treePos != null) {
            return Segment.getSegment(segmentBytes, byteOffset(treePos.pos), treePos.pos, treePos.startIndex, basedSequence);
        }
        return null;
    }

    @Nullable
    public Segment findSegment(int index, int startPos, int endPos, @NotNull BasedSequence basedSequence) {
        SegTreePos treePos = findSegmentPos(index, startPos, endPos);
        if (treePos != null) {
            return Segment.getSegment(segmentBytes, byteOffset(treePos.pos), treePos.pos, treePos.startIndex, basedSequence);
        }
        return null;
    }

    @Nullable
    public SegTreePos findSegmentPos(int index, int startPos, int endPos) {
        return findSegmentPos(index, treeData, startPos, endPos);
    }

    @NotNull
    public Segment getSegment(int pos, @NotNull BasedSequence basedSequence) {
        return Segment.getSegment(segmentBytes, byteOffset(pos), pos, aggrLength(pos), basedSequence);
    }

    @Nullable
    public Segment getPrevAnchor(int pos, @NotNull BasedSequence basedSequence) {
        return getPrevAnchor(pos, treeData, segmentBytes, basedSequence);
    }

    @NotNull
    public String toString(@NotNull BasedSequence basedSequence) {
        DelimitedBuilder out = new DelimitedBuilder(", ");
        out.append("SeqSegTree{aggr: {");
        int iMax = treeData.length / 2;
        for (int i = 0; i < iMax; i++) {
            out.append("[").append(aggrLength(i)).append(", ").append(byteOffset(i)).append(":");
            if (hasPreviousAnchor(i)) {
                out.append(", ").append(previousAnchorOffset(i)).append(":");
            }
            out.append("]").mark();
        }

        out.unmark().append(" }, seg: { ");
        int offset = 0;
        while (offset < segmentBytes.length) {
            Segment segment = Segment.getSegment(segmentBytes, offset, 0, 0, basedSequence);
            out.append(offset).append(":").append(segment).mark();
            offset += segment.getByteLength();
        }
        out.unmark().append(" } }");
        return out.toString();
    }

    // Implementation is static to allow not having to use the class but just its computed data
    public static int aggrLength(int pos, int[] treeData) {
        return treeData[pos << 1];
    }

    public static int byteOffsetData(int pos, int[] treeData) {
        return treeData[(pos << 1) + 1];
    }

    public static int byteOffset(int pos, int[] treeData) {
        return getByteOffset(byteOffsetData(pos, treeData));
    }

    public static void setTreeData(int pos, int[] treeData, int agrrLength, int byteOffset, int prevAnchorOffset) {
        assert byteOffset <= MAX_VALUE;
        treeData[pos << 1] = agrrLength;
        treeData[(pos << 1) + 1] = byteOffset | (prevAnchorOffset == 0 ? 0 : prevAnchorOffset << 29);
    }

    public static boolean hasPreviousAnchor(int pos, int[] treeData) {
        return getAnchorOffset(treeData[(pos << 1) + 1]) > 0;
    }

    public static int previousAnchorOffset(int pos, int[] treeData) {
        return byteOffset(pos, treeData) - getAnchorOffset(treeData[(pos << 1) + 1]);
    }

    @Nullable
    public static SegTreePos findSegmentPos(int index, int[] treeData, int startPos, int endPos) {
        // FIX: add segmented sequence stats collection for iteration counts
        int iterations = 0;
        while (startPos < endPos) {
            int pos = (startPos + endPos) >> 1;
            iterations++;

            int endIndex = aggrLength(pos, treeData);
            if (index >= endIndex) {
                startPos = pos + 1;
            } else {
                int startIndex = pos == 0 ? 0 : aggrLength(pos - 1, treeData);
                if (index < startIndex) {
                    endPos = pos;
                } else {
                    return new SegTreePos(pos, startIndex, iterations);
                }
            }
        }
        return null;
    }

    @Nullable
    public static Segment findSegment(int index, int[] treeData, int startPos, int endPos, byte[] segmentBytes, @NotNull BasedSequence basedSequence) {
        SegTreePos treePos = findSegmentPos(index, treeData, startPos, endPos);
        if (treePos != null) {
            return Segment.getSegment(segmentBytes, byteOffset(treePos.pos, treeData), treePos.pos, treePos.startIndex, basedSequence);
        }
        return null;
    }

    final public static class SegTreePos {
        final public int pos;
        final public int startIndex;
        final public int iterations;

        public SegTreePos(int pos, int startIndex, int iterations) {
            this.pos = pos;
            this.startIndex = startIndex;
            this.iterations = iterations;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof SegTreePos)) return false;

            SegTreePos pos1 = (SegTreePos) o;

            if (pos != pos1.pos) return false;
            return startIndex == pos1.startIndex;
        }

        @Override
        public int hashCode() {
            int result = pos;
            result = 31 * result + startIndex;
            return result;
        }

        @Override
        public String toString() {
            return "{" + pos + ", s: " + startIndex + ", i: " + iterations + '}';
        }
    }

    @NotNull
    public static Segment getSegment(int pos, int[] treeData, byte[] segmentBytes, @NotNull BasedSequence basedSequence) {
        return Segment.getSegment(segmentBytes, byteOffset(pos, treeData), pos, aggrLength(pos, treeData), basedSequence);
    }

    @Nullable
    public static Segment getPrevAnchor(int pos, int[] treeData, byte[] segmentBytes, @NotNull BasedSequence basedSequence) {
        int byteOffsetData = byteOffsetData(pos, treeData);
        int anchorOffset = getAnchorOffset(byteOffsetData);
        if (anchorOffset > 0) {
            int byteOffset = getByteOffset(byteOffsetData) - anchorOffset;
            Segment anchor = Segment.getSegment(segmentBytes, byteOffset, -1, 0, basedSequence);
            assert anchor.isAnchor();
            return anchor;
        } else {
            return null;
        }
    }

    public static SegmentTree build(@NotNull Iterable<Seg> segments, @NotNull CharSequence allText) {
        int byteLength = 0;
        int nonAnchors = 0;
        for (Seg seg : segments) {
            Segment.SegType segType = Segment.getSegType(seg, allText);
            int byteOffset = byteLength;
            byteLength += Segment.getSegByteLength(segType, seg.getSegStart(), seg.length());
            if (segType != Segment.SegType.ANCHOR) nonAnchors++;
//            System.out.println(String.format("seg: %s, segOffset: %d, bytes: %d, totalBytes: %d, nonAnchors: %d, len: %d", seg, byteOffset, byteLength - byteOffset, byteLength, nonAnchors, seg.length()));
        }

        int[] treeData = new int[nonAnchors * 2];
        byte[] segmentBytes = new byte[byteLength];

        int prevAnchorOffset = -1;

        int pos = 0;
        int offset = 0;
        int aggrLength = 0;
        int segOffset = 0;

        for (Seg seg : segments) {
            segOffset = offset;

            offset = Segment.addSegBytes(segmentBytes, offset, seg, allText);
            Segment.SegType segType = Segment.SegType.fromTypeMask(segmentBytes[segOffset]);

//            System.out.println(String.format("seg: %s, segOffset: %d, bytes: %d, totalBytes: %d, nonAnchors: %d, len: %d, aggrLen: %d, prevAnchor: %d", seg, segOffset, offset - segOffset, offset, pos, seg.length(), aggrLength, prevAnchorOffset));

            // prep for next iteration
            if (segType == Segment.SegType.ANCHOR) {
                prevAnchorOffset = segOffset;
            } else {
                aggrLength += seg.length();
                setTreeData(pos, treeData, aggrLength, segOffset, prevAnchorOffset == -1 ? 0 : segOffset - prevAnchorOffset);
                pos += 1;
                prevAnchorOffset = -1;
            }
        }

//        setTreeData(pos, treeData, aggrLength, MAX_VALUE, prevAnchorOffset == -1 || segOffset <= prevAnchorOffset ? 0 : segOffset - prevAnchorOffset);

        return new SegmentTree(treeData, segmentBytes);
    }
}
