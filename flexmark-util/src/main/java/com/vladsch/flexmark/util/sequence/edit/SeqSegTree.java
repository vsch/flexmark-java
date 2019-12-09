package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.DelimitedBuilder;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.BitSet;

/**
 * Binary search tree of sequence segments
 */
public class SeqSegTree {
    final public static int MAX_VALUE = Integer.MAX_VALUE >> 2;
    final public static int F_ANCHOR_FLAGS = ~MAX_VALUE;
    final public static int F_ANCHOR_OFFSET = Integer.MAX_VALUE & ~MAX_VALUE;

    final private int[] aggrSegData;    // tuples of aggrLength, segByteOffset with flags for prev anchor offset of 1 to 5
    final private byte[] seqSegBytes;      // byte serialized segments

    public SeqSegTree(int[] aggrSegData, byte[] seqSegBytes) {
        this.aggrSegData = aggrSegData;
        this.seqSegBytes = seqSegBytes;
    }

    public int[] getAggrSegData() {
        return aggrSegData;
    }

    public byte[] getSeqSegBytes() {
        return seqSegBytes;
    }

    public int size() {
        return aggrSegData.length / 2;
    }

    public int aggrLength(int pos) {
        return aggrLength(pos, aggrSegData);
    }

    public int byteOffset(int pos) {
        return byteOffset(pos, aggrSegData);
    }

    public boolean hasPreviousAnchor(int pos) {
        return hasPreviousAnchor(pos, aggrSegData);
    }

    public int previousAnchorOffset(int pos) {
        return previousAnchorOffset(pos, aggrSegData);
    }

    @Nullable
    public SegTreePos findSegSegPos(int index) {
        return findSegSegPos(index, aggrSegData, 0, aggrSegData.length / 2);
    }

    @Nullable
    public SegTreePos findSegSegPos(int index, int aggrOffset, int aggrSegments) {
        return findSegSegPos(index, aggrSegData, aggrOffset, aggrSegments);
    }

    @NotNull
    public SeqSeg getSegment(int pos, @NotNull BasedSequence basedSequence) {
        return getSegment(pos, aggrSegData, seqSegBytes, basedSequence);
    }

    @Nullable
    public SeqSeg getPrevAnchor(int pos, @NotNull BasedSequence basedSequence) {
        return getPrevAnchor(pos, aggrSegData, seqSegBytes, basedSequence);
    }

    @NotNull
    public String toString(@NotNull BasedSequence basedSequence) {
        DelimitedBuilder out = new DelimitedBuilder(", ");
        out.append("SeqSegTree{aggr: {");
        int iMax = aggrSegData.length / 2;
        for (int i = 0; i < iMax; i++) {
            out.append("[").append(aggrLength(i)).append(", ").append(byteOffset(i)).append(":");
            if (hasPreviousAnchor(i)) {
                out.append(", ").append(previousAnchorOffset(i)).append(":");
            }
            out.append("]").mark();
        }

        out.unmark().append(" }, seg: { ");
        int offset = 0;
        while (offset < seqSegBytes.length) {
            SeqSeg seqSeg = SeqSeg.getSeqSeg(seqSegBytes, offset, basedSequence);
            out.append(offset).append(":").append(seqSeg).mark();
            offset += seqSeg.getByteLength();
        }
        out.unmark().append(" } }");
        return out.toString();
    }

    // Implementation is static to allow not having to use the class but just its computed data
    public static int aggrLength(int pos, int[] aggrSegData) {
        return aggrSegData[pos << 1];
    }

    public static int byteOffset(int pos, int[] aggrSegData) {
        int offset = aggrSegData[(pos << 1) + 1] & MAX_VALUE;
        return offset == MAX_VALUE ? -1 : offset;
    }

    public static void setAggrSegData(int pos, int[] aggrSegData, int agrrLength, int byteOffset, int prevAnchorOffset) {
        assert byteOffset <= MAX_VALUE;
        aggrSegData[pos << 1] = agrrLength;
        aggrSegData[(pos << 1) + 1] = byteOffset | (prevAnchorOffset == 0 ? 0 : prevAnchorOffset << 29);
    }

    public static boolean hasPreviousAnchor(int pos, int[] aggrSegData) {
        return anchorOffset(aggrSegData[(pos << 1) + 1]) > 0;
    }

    public static int anchorOffset(int byteOffset) {
        int anchorBits = byteOffset & F_ANCHOR_FLAGS;
        return anchorBits != 0 ? (anchorBits < 0 ? 0x0004 : 0) | ((byteOffset & F_ANCHOR_OFFSET) >> 29) : 0;
    }

    public static int previousAnchorOffset(int pos, int[] aggrSegData) {
        return byteOffset(pos, aggrSegData) - anchorOffset(aggrSegData[(pos << 1) + 1]);
    }

    public static int getStep(int aggrSegs) {
        long[] bits = { aggrSegs };
        BitSet bitSet = BitSet.valueOf(bits);
        int stepBit = bitSet.previousSetBit(32);
        return bitSet.cardinality() > 1 ? 1 << stepBit : 1 << stepBit - 1;
    }

    @Nullable
    public static SegTreePos findSegSegPos(int index, int[] aggrSegData, int aggrOffset, int aggrSegments) {
        int pos = aggrOffset + (aggrSegments >> 1) + (aggrSegments & 1);   // middle segment index within range
        int step = getStep(aggrSegments);          // step size for top level node
        int aggrEndOffset = aggrOffset + aggrSegments;
        int iterations = 0;

        while (step >= 0) {
            iterations++;

            if (pos < aggrOffset) pos += step;
            else if (pos >= aggrEndOffset) {
                if (pos == aggrEndOffset) {
                    // RELEASE: remove test and assert
                    int endIndex = aggrLength(pos - 1, aggrSegData);
                    if (index < endIndex) {
                        int tmp = 0;
                    }
                    assert index >= endIndex;
                    break;
                }
                pos -= step;
            } else {
                int startIndex = pos > 0 ? aggrLength(pos - 1, aggrSegData) : 0;
                int endIndex = aggrLength(pos, aggrSegData);
                if (index < startIndex) {
                    pos -= step;
                } else if (index >= endIndex) {
                    pos += step;
                } else {
                    return new SegTreePos(pos, startIndex, iterations);
                }
            }

            if (step <= 0) break;
            step >>= 1;
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
    public static SeqSeg getSegment(int pos, int[] aggrSegData, byte[] seqSegBytes, @NotNull BasedSequence basedSequence) {
        int byteOffset = byteOffset(pos, aggrSegData);
        return SeqSeg.getSeqSeg(seqSegBytes, byteOffset, basedSequence);
    }

    @Nullable
    public static SeqSeg getPrevAnchor(int pos, int[] aggrSegData, byte[] seqSegBytes, @NotNull BasedSequence basedSequence) {
        int anchorOffset = previousAnchorOffset(pos, aggrSegData);
        if (anchorOffset > 0) {
            int byteOffset = byteOffset(pos, aggrSegData) - anchorOffset;
            SeqSeg anchor = SeqSeg.getSeqSeg(seqSegBytes, byteOffset, basedSequence);
            assert anchor.isAnchor();
            return anchor;
        } else {
            return null;
        }
    }

    public static SeqSegTree build(@NotNull Iterable<Seg> segments, @NotNull CharSequence allText) {
        int byteLength = 0;
        int nonAnchors = 0;
        for (Seg seg : segments) {
            SeqSeg.SegType segType = SeqSeg.getSegType(seg, allText);
            int byteOffset = byteLength;
            byteLength += SeqSeg.getSegByteLength(segType, seg.getSegStart(), seg.length());
            if (segType != SeqSeg.SegType.ANCHOR) nonAnchors++;
//            System.out.println(String.format("seg: %s, segOffset: %d, bytes: %d, totalBytes: %d, nonAnchors: %d, len: %d", seg, byteOffset, byteLength - byteOffset, byteLength, nonAnchors, seg.length()));
        }

        int[] aggrSegData = new int[nonAnchors * 2];
        byte[] seqSegBytes = new byte[byteLength];

        int prevAnchorOffset = -1;

        int pos = 0;
        int offset = 0;
        int aggrLength = 0;
        int segOffset = 0;

        for (Seg seg : segments) {
            segOffset = offset;

            offset = SeqSeg.addSegBytes(seqSegBytes, offset, seg, allText);
            SeqSeg.SegType segType = SeqSeg.SegType.fromTypeMask(seqSegBytes[segOffset]);

//            System.out.println(String.format("seg: %s, segOffset: %d, bytes: %d, totalBytes: %d, nonAnchors: %d, len: %d, aggrLen: %d, prevAnchor: %d", seg, segOffset, offset - segOffset, offset, pos, seg.length(), aggrLength, prevAnchorOffset));

            // prep for next iteration
            if (segType == SeqSeg.SegType.ANCHOR) {
                prevAnchorOffset = segOffset;
            } else {
                aggrLength += seg.length();
                setAggrSegData(pos, aggrSegData, aggrLength, segOffset, prevAnchorOffset == -1 ? 0 : segOffset - prevAnchorOffset);
                pos += 1;
                prevAnchorOffset = -1;
            }
        }

//        setAggrSegData(pos, aggrSegData, aggrLength, MAX_VALUE, prevAnchorOffset == -1 || segOffset <= prevAnchorOffset ? 0 : segOffset - prevAnchorOffset);

        return new SeqSegTree(aggrSegData, seqSegBytes);
    }
}
