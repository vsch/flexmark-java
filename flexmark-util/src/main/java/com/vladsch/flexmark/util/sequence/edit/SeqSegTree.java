package com.vladsch.flexmark.util.sequence.edit;

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

    public int findSegSegIndex(int index, int aggrOffset, int aggrSegments) {
        return findSegSegIndex(index, aggrSegData, aggrOffset, aggrSegments);
    }

    @NotNull
    public SeqSeg getSegment(int pos, @NotNull BasedSequence basedSequence) {
        return getSegment(pos, aggrSegData, seqSegBytes, basedSequence);
    }

    @Nullable
    public SeqSeg getPrevAnchor(int pos, @NotNull BasedSequence basedSequence) {
        return getPrevAnchor(pos, aggrSegData, seqSegBytes, basedSequence);
    }

    // Implementation is static to allow not having to use the class but just its computed data
    public static int aggrLength(int pos, int[] aggrSegData) {
        return aggrSegData[pos << 1];
    }

    public static int byteOffset(int pos, int[] aggrSegData) {
        return aggrSegData[(pos << 1) + 1] & MAX_VALUE;
    }

    public static void setAggrSegData(int pos, int[] aggrSegData, int agrrLength, int byteOffset, int prevAnchorOffset) {
        assert byteOffset <= MAX_VALUE;
        aggrSegData[pos << 1] = agrrLength;
        aggrSegData[(pos << 1) + 1] = byteOffset | (prevAnchorOffset == 0 ? 0 : (prevAnchorOffset << 29) | 0x8000_0000);
    }

    public static boolean hasPreviousAnchor(int pos, int[] aggrSegData) {
        return (aggrSegData[(pos << 1) + 1]) < 0;
    }

    public static int previousAnchorOffset(int pos, int[] aggrSegData) {
        int flags = aggrSegData[(pos << 1) + 1];
        return flags < 0 ? 1 + (flags & F_ANCHOR_FLAGS) >> 29 : 0;
    }

    public static int getStep(int aggrSegs) {
        long[] bits = { aggrSegs };
        BitSet bitSet = BitSet.valueOf(bits);
        int stepBit = bitSet.previousSetBit(32);
        return bitSet.cardinality() > 1 ? 1 << stepBit : 1 << stepBit - 1;
    }

    public static int findSegSegIndex(int index, int[] aggrSegData, int aggrOffset, int aggrSegments) {
        int pos = aggrOffset + (aggrSegments >> 1);   // middle segment index within range
        int step = getStep(aggrSegments);             // step size for top level node

        while (step > 0) {
            if (pos < aggrOffset) pos += step;
            else if (pos >= aggrSegments) pos -= step;
            else {
                int aggrLength = aggrLength(pos, aggrSegData);
                if (index == aggrLength) break;
                else if (index < aggrLength) pos -= step;
                else pos += step;
            }
            step >>= 1;
        }
        return pos;
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
            byteLength += SeqSeg.getSegByteLength(segType, seg.getSegStart(), seg.length());
            if (segType != SeqSeg.SegType.ANCHOR) nonAnchors++;
        }

        int[] aggrSegData = new int[nonAnchors * 2];
        byte[] seqSegBytes = new byte[byteLength];

        int prevAnchorOffset = -1;

        int pos = 0;
        int offset = 0;
        int aggrLength = 0;
        for (Seg seg : segments) {
            int segOffset = offset;

            offset += SeqSeg.addSegBytes(seqSegBytes, offset, seg, allText);
            SeqSeg.SegType segType = SeqSeg.SegType.fromTypeMask(seqSegBytes[segOffset]);
            aggrLength += seg.length();
            setAggrSegData(pos, aggrSegData, aggrLength, segOffset, prevAnchorOffset == -1 ? 0: segOffset - prevAnchorOffset);

            // prep for next iteration
            prevAnchorOffset = (segType == SeqSeg.SegType.ANCHOR) ? segOffset : -1;
            pos += 1;
        }

        return new SeqSegTree(aggrSegData, seqSegBytes);
    }
}
