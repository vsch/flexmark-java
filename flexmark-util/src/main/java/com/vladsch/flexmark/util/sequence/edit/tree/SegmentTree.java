package com.vladsch.flexmark.util.sequence.edit.tree;

import com.vladsch.flexmark.util.DelimitedBuilder;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.edit.IBasedSegmentBuilder;
import com.vladsch.flexmark.util.sequence.edit.Seg;
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
        return pos < 0 ? 0 : treeData[pos << 1];
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
    public SegmentTreePos findSegmentPos(int index) {
        return findSegmentPos(index, treeData, 0, treeData.length >> 1);
    }

    @Nullable
    public Segment findSegment(int index, @NotNull BasedSequence basedSequence, @Nullable Segment hint) {
        return findSegment(index, 0, treeData.length / 2, basedSequence, hint);
    }

    @Nullable
    public Segment findSegment(int index, int startPos, int endPos, @NotNull BasedSequence basedSequence, @Nullable Segment hint) {
        if (hint != null) {
            // NOTE: first try around cached segment for this index
            int startIndex = hint.getStartIndex();
            if (index >= startIndex) {
                int endIndex = hint.getEndIndex();
                assert index >= endIndex : String.format("FindSegment should not be called, index %d is in range [%d, %d) of hint segment: %s", index, startIndex, endIndex, hint);
                if (hint.myPos + 1 >= endPos) return null;
                int nextLength = aggrLength(hint.myPos + 1);
                if (index < nextLength) {
                    // FIX: add stats to track this
                    System.out.println("Using next segment");
                    return Segment.getSegment(segmentBytes, byteOffset(hint.myPos + 1), hint.myPos + 1, endIndex, basedSequence);
                }
                // can skip next one too
                startPos = hint.myPos + 2;
            } else {
                // see if previous contains index
                if (hint.myPos == startPos) return null;

                int prevPrevLength = aggrLength(hint.myPos - 2);
                if (index >= prevPrevLength) {
                    // it is previous one
                    // FIX: add stats to track this
                    System.out.println("Using previous segment");
                    return Segment.getSegment(segmentBytes, byteOffset(hint.myPos - 1), hint.myPos - 1, prevPrevLength, basedSequence);
                }
                // previous one can be skipped
                endPos = hint.myPos - 1;
            }
        }

        // NOTE: most of the time char sequence access starts at 0, so we try the start pos
        if (startPos >= 0) {
            int firstLength = aggrLength(startPos);
            if (index < firstLength) {
                int prevLength = aggrLength(startPos - 1);
                if (index >= prevLength) {
                    // FIX: add stats to track this
                    System.out.println("Using first segment");
                    return Segment.getSegment(segmentBytes, byteOffset(startPos), startPos, prevLength, basedSequence);
                }
                // first one is too far, we can skip it
                endPos = startPos;
            } else {
                // first one can be skipped
                startPos = startPos + 1;
            }
        }

        // NOTE: failing that we try the last segment in case it is backwards scan through sequence
        if (endPos - 1 >= startPos) {
            // check last one for match
            int secondToLastLength = aggrLength(endPos - 2);
            if (index >= secondToLastLength) {
                int lastLength = aggrLength(endPos - 1);
                if (index >= lastLength) return null; /* beyond last segment*/

                // FIX: add stats to track this
                System.out.println("Using last segment");
                return Segment.getSegment(segmentBytes, byteOffset(endPos - 1), endPos - 1, secondToLastLength, basedSequence);
            } else {
                // previous to last can be skipped
                endPos = endPos - 1;
            }
        }

        // NOTE: all optimizations failed, but not completely wasted since they served to shorten the search range.
        SegmentTreePos treePos = findSegmentPos(index, startPos, endPos);
        if (treePos != null) {
            return Segment.getSegment(segmentBytes, byteOffset(treePos.pos), treePos.pos, treePos.startIndex, basedSequence);
        }
        return null;
    }

    @NotNull
    public SegmentTreeRange getSegmentRange(int startIndex, int endIndex, int startPos, int endPos, @NotNull BasedSequence basedSequence, @Nullable Segment hint) {
        Segment startSegment = findSegment(startIndex, startPos, endPos, basedSequence, hint);
        assert startSegment != null;
        Segment endSegment = endIndex == startIndex ? startSegment : findSegment(endIndex, startPos, endPos, basedSequence, startSegment);
        assert endSegment != null;

        int startOffset = -1;
        int endOffset = -1;

        // if start segment is text then we look for previous anchor or range to get startOffset base context information, failing that look for next range or anchor
        if (startSegment.isText()) {
            Segment prevSegment = getPrevAnchor(startSegment.myPos, basedSequence);
            if (prevSegment == null && startSegment.myPos > 0) {
                prevSegment = getSegment(startSegment.myPos - 1, basedSequence);
            }

            if (prevSegment != null && prevSegment.isBase()) {
                startOffset = prevSegment.getEndOffset();
            }
        } else {
            startOffset = startSegment.getStartOffset() + startIndex - startSegment.getStartIndex();
        }

        // if end segment is text then we look for next anchor or range to get endOffset base context information
        if (endSegment.isText()) {
            if (endSegment.myPos + 1 < treeData.length / 2) {
                Segment nextSegment = getSegment(endSegment.myPos + 1, basedSequence);
                if (nextSegment.isBase()) {
                    endOffset = nextSegment.getStartOffset();
                }
            }
        } else {
            endOffset = endSegment.getEndOffset() + endIndex - endSegment.getStartIndex();
        }

        if (startOffset < 0) startOffset = endOffset;
        if (endOffset < startOffset) endOffset = startOffset;

        return new SegmentTreeRange(
                startIndex,
                endIndex,
                startOffset,
                endOffset,
                startSegment.myPos,
                endSegment.myPos + 1
        );
    }

    /**
     * Add segments selected by given treeRange
     *
     * @param builder   based segment builder
     * @param treeRange treeRange for which to add segments
     */
    public void addSegments(@NotNull IBasedSegmentBuilder<?> builder, @NotNull SegmentTreeRange treeRange) {
        addSegments(builder, treeRange.startIndex, treeRange.endIndex, treeRange.startOffset, treeRange.endOffset, treeRange.startPos, treeRange.endPos);
    }

    /**
     * Add segments of subsequence of this tree to builder
     *
     * @param builder     builder to which to add the segments
     * @param startIndex  start index of sub-sequence of segment tree
     * @param endIndex    end index of sub-sequence of segment tree
     * @param startOffset start offset of the subsequence to use as start anchor
     * @param endOffset   end offset of the subsequence to use as end anchor
     * @param startPos    start pos of sub-sequence segments  in tree
     * @param endPos      end  pos of sub-sequence segments  in tree
     */
    public void addSegments(@NotNull IBasedSegmentBuilder<?> builder, int startIndex, int endIndex, int startOffset, int endOffset, int startPos, int endPos) {
        // add our stuff to builder
        if (startOffset != -1) {
            builder.appendAnchor(startOffset);
        }

        BasedSequence baseSequence = builder.getBaseSequence();
        for (int i = startPos; i < endPos; i++) {
            Segment segment = getSegment(i, baseSequence);

            if (segment.isText()) {
                // check for previous anchor
                Segment prevAnchor = getPrevAnchor(i, baseSequence);
                if (prevAnchor != null) builder.appendAnchor(prevAnchor.getStartOffset());
            }

            // OPTIMIZE: add append Segment method with start/end offsets to allow builder to extract repeat and first256 information
            //  without needing to scan text, range information does not have any benefit from this
            CharSequence charSequence = getCharSequence(segment, startIndex, endIndex, startPos, endPos);

            if (segment.isText()) {
                builder.append(charSequence);
                // check for next anchor
                int byteOffset = segment.myByteOffset + segment.getByteLength();
                if (byteOffset < segmentBytes.length && (i + 1 >= size() || byteOffset != byteOffset(i + 1))) {
                    Segment nextAnchor = Segment.getSegment(segmentBytes, byteOffset, 0, 0, baseSequence);
                    if (nextAnchor.isAnchor()) {
                        builder.appendAnchor(nextAnchor.getStartOffset());
                    }
                }
            } else {
                assert charSequence instanceof BasedSequence;
                builder.append(((BasedSequence) charSequence).getStartOffset(), ((BasedSequence) charSequence).getEndOffset());
            }
        }

        if (endOffset != -1) {
            builder.appendAnchor(endOffset);
        }
    }

    /**
     * Get char sequence of segment corresponding to sub-sequence in segment tree
     *
     * @param segment    segment
     * @param startIndex start index of sub-sequence of segment tree
     * @param endIndex   end index of sub-sequence of segment tree
     * @param startPos   start pos of sub-sequence segments  in tree
     * @param endPos     end  pos of sub-sequence segments  in tree
     * @return subsequence of segment corresponding to part of it which is in the sub-sequence of the tree
     */
    @NotNull
    public static CharSequence getCharSequence(@NotNull Segment segment, int startIndex, int endIndex, int startPos, int endPos) {
        CharSequence charSequence;
        int pos = segment.myPos;

        if (pos == startPos && pos + 1 == endPos) {
            // need to trim start/end
            charSequence = segment.getCharSequence().subSequence(startIndex - segment.getStartIndex(), endIndex - segment.getStartIndex());
        } else if (pos == startPos) {
            // need to trim start
            charSequence = segment.getCharSequence().subSequence(startIndex - segment.getStartIndex(), segment.length());
        } else if (pos + 1 == endPos) {
            // need to trim end
            charSequence = segment.getCharSequence().subSequence(0, endIndex - segment.getStartIndex());
        } else {
            charSequence = segment.getCharSequence();
        }

        return charSequence;
    }

    @Nullable
    public SegmentTreePos findSegmentPos(int index, int startPos, int endPos) {
        return findSegmentPos(index, treeData, startPos, endPos);
    }

    @NotNull
    public Segment getSegment(int pos, @NotNull BasedSequence basedSequence) {
        return Segment.getSegment(segmentBytes, byteOffset(pos), pos, aggrLength(pos - 1), basedSequence);
    }

    @Nullable
    public Segment getPrevAnchor(int pos, @NotNull BasedSequence basedSequence) {
        return getPrevAnchor(pos, treeData, segmentBytes, basedSequence);
    }

    @NotNull
    public String toString(@NotNull BasedSequence basedSequence) {
        DelimitedBuilder out = new DelimitedBuilder(", ");
        out.append("SegmentTree{aggr: {");
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
        return pos < 0 ? 0 : treeData[pos << 1];
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
        int byteOffsetData = byteOffsetData(pos, treeData);
        return getByteOffset(byteOffsetData) - getAnchorOffset(byteOffsetData);
    }

    @Nullable
    public static SegmentTreePos findSegmentPos(int index, int[] treeData, int startPos, int endPos) {
        // FIX: add segmented sequence stats collection for iteration counts
        // FIX: check first segment and last segment in case it is a scan from start/end of sequence
        if (index == 0 && startPos == 0) return new SegmentTreePos(0, 0, 0);

        int iterations = 0;
        while (startPos < endPos) {
            int pos = (startPos + endPos) >> 1;
            iterations++;
//            System.out.println(String.format("Iteration[%d] pos: %d, [%d, %d)", iterations, pos, startPos, endPos));

            int endIndex = aggrLength(pos, treeData);
            if (index >= endIndex) {
                startPos = pos + 1;
            } else {
                int startIndex = aggrLength(pos - 1, treeData);
                if (index < startIndex) {
                    endPos = pos;
                } else {
//                    System.out.println(String.format("Found on iteration[%d] pos: %d, [%d, %d)", iterations, pos, startPos, endPos));
                    return new SegmentTreePos(pos, startIndex, iterations);
                }
            }
        }
        return null;
    }

    @Nullable
    public static Segment findSegment(int index, int[] treeData, int startPos, int endPos, byte[] segmentBytes, @NotNull BasedSequence basedSequence) {
        SegmentTreePos treePos = findSegmentPos(index, treeData, startPos, endPos);
        if (treePos != null) {
            return Segment.getSegment(segmentBytes, byteOffset(treePos.pos, treeData), treePos.pos, treePos.startIndex, basedSequence);
        }
        return null;
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
