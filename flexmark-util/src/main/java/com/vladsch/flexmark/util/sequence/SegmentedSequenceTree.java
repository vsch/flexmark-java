package com.vladsch.flexmark.util.sequence;

import com.vladsch.flexmark.util.sequence.edit.IBasedSegmentBuilder;
import com.vladsch.flexmark.util.sequence.edit.ISegmentBuilder;
import com.vladsch.flexmark.util.sequence.edit.tree.Segment;
import com.vladsch.flexmark.util.sequence.edit.tree.SegmentTree;
import com.vladsch.flexmark.util.sequence.edit.tree.SegmentTreeRange;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A BasedSequence which consists of segments of other BasedSequences
 * NOTE: very efficient for random access but extremely wasteful with space by allocating 4 bytes per character in the sequence with corresponding construction penalty
 * use SegmentedSequenceTree which is binary tree based segmented sequence with minimal overhead and optimized to give penalty free random access for most applications.
 */
public final class SegmentedSequenceTree extends SegmentedSequence {
    final private SegmentTree mySegmentTree;   // segment tree
    final private int myStartIndex;               // start index of this sub-sequence in the segment tree, 0 for original
    final private int myStartPos;                 // start position for segments of this sequence in the tree
    final private int myEndPos;                   // end position for segments of this sequence in the tree
    final private ThreadLocal<Cache> myCache = new ThreadLocal<>();

    private static class Cache {
        final @NotNull Segment mySegment;
        final @NotNull CharSequence myChars;
        final int myCharsStartIndex;

        public Cache(@NotNull Segment segment, @NotNull CharSequence chars) {
            mySegment = segment;
            myChars = chars;
            myCharsStartIndex = segment.getStartIndex();
        }
    }

    private SegmentedSequenceTree(BasedSequence baseSeq, int startOffset, int endOffset, int length, @NotNull SegmentTree segmentTree) {
        super(baseSeq, startOffset, endOffset, length);
        mySegmentTree = segmentTree;
        myStartIndex = 0;
        myStartPos = 0;
        myEndPos = segmentTree.size();
    }

    private SegmentedSequenceTree(BasedSequence baseSeq, @NotNull SegmentTree segmentTree, @NotNull SegmentTreeRange subSequenceRange) {
        super(baseSeq, subSequenceRange.startOffset, subSequenceRange.endOffset, subSequenceRange.length);
        mySegmentTree = segmentTree;
        myStartIndex = subSequenceRange.startIndex;
        myStartPos = subSequenceRange.startPos;
        myEndPos = subSequenceRange.endPos;
    }

    @NotNull
    private Cache getCache(int index) {
        Cache cache = myCache.get();

        if (cache == null || cache.mySegment.notInSegment(index)) {
            Segment segment = mySegmentTree.findSegment(index + myStartIndex, myStartPos, myEndPos, baseSeq, cache == null ? null : cache.mySegment);
            assert segment != null;

            cache = new Cache(segment, SegmentTree.getCharSequence(segment, myStartIndex, myStartIndex + length, myStartPos, myEndPos));
            myCache.set(cache);
        }
        return cache;
    }

    @Nullable
    private Segment getCachedSegment() {
        Cache cache = myCache.get();
        return cache == null ? null : cache.mySegment;
    }

    @Override
    public int getIndexOffset(int index) {
        if (index == length) {
            Cache cache = getCache(index - 1);
            CharSequence charSequence = cache.myChars;
            if (charSequence instanceof BasedSequence) {
                return ((BasedSequence) charSequence).getIndexOffset(index + myStartIndex - cache.myCharsStartIndex);
            } else {
                return -1;
            }
        } else {
            validateIndexInclusiveEnd(index);

            Cache cache = getCache(index);
            CharSequence charSequence = cache.myChars;
            if (charSequence instanceof BasedSequence) {
                return ((BasedSequence) charSequence).getIndexOffset(index + myStartIndex - cache.myCharsStartIndex);
            } else {
                return -1;
            }
        }
    }

    @Override
    public void addSegments(@NotNull IBasedSegmentBuilder<?> builder) {
        mySegmentTree.addSegments(builder, myStartIndex, myStartIndex + length, startOffset, endOffset, myStartPos, myEndPos);
    }

    @Override
    public char charAt(int index) {
        validateIndex(index);
        Cache cache = getCache(index);
        return cache.myChars.charAt(index + myStartIndex - cache.myCharsStartIndex);
    }

    @NotNull
    @Override
    public BasedSequence subSequence(int startIndex, int endIndex) {
        if (startIndex == 0 && endIndex == length) {
            return this;
        } else {
            validateStartEnd(startIndex, endIndex);
            SegmentTreeRange subSequenceRange = mySegmentTree.getSegmentRange(startIndex + myStartIndex, endIndex + myStartIndex, myStartPos, myEndPos, baseSeq, getCachedSegment());
            return new SegmentedSequenceTree(baseSeq, mySegmentTree, subSequenceRange);
        }
    }

    /**
     * Base Constructor
     *
     * @param builder builder containing segments for this sequence
     */
    public static SegmentedSequenceTree create(@NotNull BasedSequence baseSeq, ISegmentBuilder<?> builder) {
        SegmentTree segmentTree = SegmentTree.build(builder.getSegments(), builder.getText());
        return new SegmentedSequenceTree(baseSeq, builder.getStartOffset(), builder.getEndOffset(), builder.length(), segmentTree);
    }
}
