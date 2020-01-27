package com.vladsch.flexmark.util.sequence;

import com.vladsch.flexmark.util.collection.iteration.ArrayIterable;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataKeyBase;
import com.vladsch.flexmark.util.sequence.builder.BasedSegmentBuilder;
import com.vladsch.flexmark.util.sequence.builder.SequenceBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A BasedSequence which consists of segments of other BasedSequences
 */
public abstract class SegmentedSequence extends BasedSequenceImpl implements ReplacedBasedSequence {
    protected final BasedSequence baseSeq;    // base sequence
    protected final int startOffset;          // this sequence's start offset in base
    protected final int endOffset;            // this sequence's end offset in base
    protected final int length;               // length of this sequence

    protected SegmentedSequence(BasedSequence baseSeq, int startOffset, int endOffset, int length) {
        super(0);
        assert baseSeq == baseSeq.getBaseSequence();

        if (startOffset < 0 && endOffset < 0) {
            // NOTE: segmented sequence with no based segments in it gets both offsets as -1
            startOffset = 0;
            endOffset = 0;
        }

        assert startOffset >= 0 : "startOffset: " + startOffset;
        assert endOffset >= startOffset && endOffset <= baseSeq.length() : "endOffset: " + endOffset;

        this.baseSeq = baseSeq;
        this.startOffset = startOffset;
        this.endOffset = endOffset;
        this.length = length;
    }

    @NotNull
    @Override
    final public Object getBase() {
        return baseSeq.getBase();
    }

    @NotNull
    @Override
    final public BasedSequence getBaseSequence() {
        return baseSeq;
    }

    /**
     * Get the start in the base sequence for this segmented sequence.
     * <p>
     * NOTE: this is the startOffset determined when the sequence was built from segments and may differ from
     * the startOffset of the first based segment in this sequence
     *
     * @return start in base sequence
     */
    final public int getStartOffset() {
        return startOffset;
    }

    /**
     * Get the end offset in the base sequence
     * <p>
     * NOTE: this is the endOffset determined when the sequence was built from segments and may differ from
     * the endOffset of the last based segment in this sequence
     *
     * @return end in base sequence
     */
    final public int getEndOffset() {
        return endOffset;
    }

    @Override
    final public int getOptionFlags() {
        return getBaseSequence().getOptionFlags();
    }

    @Override
    final public boolean allOptions(int options) {
        return getBaseSequence().allOptions(options);
    }

    @Override
    final public boolean anyOptions(int options) {
        return getBaseSequence().anyOptions(options);
    }

    @Override
    final public <T> T getOption(DataKeyBase<T> dataKey) {
        return getBaseSequence().getOption(dataKey);
    }

    @Override
    final public @Nullable DataHolder getOptions() {
        return getBaseSequence().getOptions();
    }

    @Override
    final public int length() {
        return length;
    }

    @NotNull
    @Override
    final public Range getSourceRange() {
        return Range.of(getStartOffset(), getEndOffset());
    }

    @NotNull
    @Override
    final public BasedSequence baseSubSequence(int startIndex, int endIndex) {
        SequenceUtils.validateStartEnd(startIndex, endIndex, baseSeq.length());
        return baseSeq.baseSubSequence(startIndex, endIndex);
    }

    /**
     * Use {@link BasedSequence#getBuilder()} and then {@link SequenceBuilder#addAll(Iterable)} or
     * if you know which are based segments vs. out of base Strings then use {@link BasedSegmentBuilder}
     * to construct segments directly.
     * <p>
     * Use only if you absolutely need to use this old method because it calls the builder.addAll() for all the segments
     * anyway.
     * <p>
     * If you need the location where content would have been use the FencedCodeBlock.getOpeningMarker().getEndOffset() + 1
     *
     * @param basedSequence base sequence for the segments
     * @param segments      list of based sequences to put into a based sequence
     * @return based sequence of segments. Result is a sequence which looks like
     *         all the segments were concatenated, while still maintaining
     *         the original offset for each character when using {@link #getIndexOffset(int)}(int index)
     */
    public static BasedSequence create(BasedSequence basedSequence, @NotNull Iterable<? extends BasedSequence> segments) {
        return create(basedSequence.getBuilder().addAll(segments));
    }

    public static BasedSequence create(BasedSequence... segments) {
        return segments.length == 0 ? BasedSequence.NULL : create(segments[0], new ArrayIterable<>(segments));
    }

    public static BasedSequence create(SequenceBuilder builder) {
        BasedSequence baseSubSequence = builder.getSingleBasedSequence();
        if (baseSubSequence != null) {
            return baseSubSequence;
        } else if (!builder.isEmpty()) {
            BasedSequence baseSequence = builder.getBaseSequence();
            if (baseSequence.anyOptions(F_FULL_SEGMENTED_SEQUENCES)) {
                return SegmentedSequenceFull.create(baseSequence, builder.getSegmentBuilder());
            } else if (baseSequence.anyOptions(F_TREE_SEGMENTED_SEQUENCES)) {
                return SegmentedSequenceTree.create(baseSequence, builder.getSegmentBuilder());
            } else {
                // Can decide based on segments and length but tree based is not slower and much more efficient
//                return SegmentedSequenceFull.create(baseSequence, builder.getSegmentBuilder());
                return SegmentedSequenceTree.create(baseSequence, builder.getSegmentBuilder());
            }
        }
        return BasedSequence.NULL;
    }

    /**
     * @param basedSequence base sequence for the segments
     * @param segments      list of based sequences to put into a based sequence
     * @return based sequence of segments. Result is a sequence which looks like
     *         all the segments were concatenated, while still maintaining
     *         the original offset for each character when using {@link #getIndexOffset(int)}(int index)
     * @deprecated use {@link BasedSequence#getBuilder()} and then {@link SequenceBuilder#addAll(Iterable)} or if you know which are based segments vs. out of base Strings then use {@link BasedSegmentBuilder} to construct segments directly.
     *         If you absolutely need to use the old method then use {@link SegmentedSequence#create(BasedSequence, Iterable)}
     */
    @Deprecated
    public static BasedSequence of(BasedSequence basedSequence, @NotNull Iterable<? extends BasedSequence> segments) {
        return create(basedSequence, segments);
    }

    @Deprecated
    public static BasedSequence of(BasedSequence... segments) {
        return create(segments);
    }
}
