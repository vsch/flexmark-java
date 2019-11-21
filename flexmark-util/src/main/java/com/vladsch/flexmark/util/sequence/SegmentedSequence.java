package com.vladsch.flexmark.util.sequence;

import com.vladsch.flexmark.util.SegmentedSequenceStats;
import com.vladsch.flexmark.util.collection.iteration.ArrayIterable;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataKeyBase;
import com.vladsch.flexmark.util.sequence.edit.BasedSegmentBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * A BasedSequence which consists of segments of other BasedSequences
 */
public final class SegmentedSequence extends BasedSequenceImpl implements ReplacedBasedSequence {
    private final BasedSequence baseSeq;  // base sequence
    // NOTE: reducing the size of type for baseOffsets and adding only unique nonBasedChars will give greater memory saving than encoding out of base chars as -ve offsets
    // on the other hand if the baseOffset type is int then non base chars as -ve offset should be used
    //    private final char[] nonBaseChars;    // all non-base characters, offset by baseStartOffset. When baseOffsets[] < 0, take -ve - 1 to get index into this array
    private final boolean nonBaseChars;    // all non-base characters, offset by baseStartOffset. When baseOffsets[] < 0, take -ve - 1 to get index into this array
    private final int[] baseOffsets;      // list of base offsets, offset by baseStartOffset
    private final int baseStartOffset;    // start offset for baseOffsets of this sequence, offset from baseSeq for all chars, including non-base chars
    private final int length;             // length of this sequence
    private final int startOffset;        // this sequence's start offset in base
    private final int endOffset;          // this sequence's end offset in base

    @NotNull
    @Override
    public Object getBase() {
        return baseSeq.getBase();
    }

    @NotNull
    @Override
    public BasedSequence getBaseSequence() {
        return baseSeq.getBaseSequence();
    }

    /**
     * Get the first start in the base sequence, non-based sequences are skipped
     *
     * @return start in base sequence
     */
    public int getStartOffset() {
        return startOffset;
    }

    /**
     * Get the last end in the base sequence, non-based sequences are skipped
     *
     * @return end in base sequence
     */
    public int getEndOffset() {
        return endOffset;
    }

    public int[] getBaseOffsets() {
        return baseOffsets;
    }

    public int getBaseStartOffset() {
        return baseStartOffset;
    }

    @Override
    public boolean isOption(int option) {
        return getBaseSequence().isOption(option);
    }

    @Override
    public <T> T getOption(DataKeyBase<T> dataKey) {
        return getBaseSequence().getOption(dataKey);
    }

    @Override
    public @Nullable DataHolder getOptions() {
        return getBaseSequence().getOptions();
    }

    @Override
    public int getIndexOffset(int index) {
        if (index < 0 || index > length) {
            throw new StringIndexOutOfBoundsException("String index: " + index + " out of range: 0, " + length());
        }

        if (index == length && index == 0) {
            throw new StringIndexOutOfBoundsException("String index: " + index + " out of range: 0, " + length());
        }
        int offset = baseOffsets[baseStartOffset + index];
        return offset < 0 ? -1 : offset;
    }

    @Override
    public boolean addSegments(@NotNull BasedSegmentBuilder builder) {
        if (length > 0) {
            // FIX: clean up and optimize the structure. it is error prone and inefficient
            return BasedUtils.generateSegments(builder, this);
        }
        return false;
    }

    /**
     * removed empty and return BasedSequence.NULL when no segments which is the logical result however,
     * this will mean empty node text in FencedCodeBlock will now return NULL sequence instead of an empty
     * sequence from the document.
     * <p>
     * If you need the location where content would have been use the FencedCodeBlock.getOpeningMarker().getEndOffset() + 1
     *
     * @param segments list of based sequences to put into a based sequence
     * @return based sequence of segments. Result is a sequence which looks like
     *         all the segments were concatenated, while still maintaining
     *         the original offset for each character when using {@link #getIndexOffset(int)}(int index)
     * @deprecated use {@link BasedSequence#getBuilder()} or if you know which are based segments vs. out of base Strings then use {@link BasedSegmentBuilder} to construct segments directly.
     */
    @Deprecated
    public static BasedSequence of(@NotNull Iterable<? extends BasedSequence> segments) {
        BasedSequence lastSegment = null;
        BasedSequence firstSegment = null;
        for (BasedSequence sequence : segments) {
            firstSegment = sequence;
            break;
        }

        if (firstSegment != null) {
            BasedSequence base = firstSegment.getBaseSequence();
            ArrayList<BasedSequence> mergedSequences = new ArrayList<>();
            int startOffset = -1;
            int endOffset = -1;

            for (BasedSequence segment : segments) {
                if (segment == null || segment.isNull()) continue;

                if (base.getBase() != segment.getBase()) {
                    throw new AssertionError("all segments must come from the same base sequence");
                }

                if (startOffset == -1) startOffset = segment.getStartOffset();
                endOffset = segment.getEndOffset();

                if (segment.isEmpty()) continue;  // skip empty sequences, they serve no purpose

                if (segment instanceof ReplacedBasedSequence) {
                    if (lastSegment != null) mergedSequences.add(lastSegment);
                    mergedSequences.add(segment);
                    lastSegment = null;
                } else {
                    if (lastSegment == null) {
                        lastSegment = segment;
                    } else {
                        if (lastSegment.getEndOffset() != segment.getStartOffset()) {
                            mergedSequences.add(lastSegment);
                            lastSegment = segment;
                        } else {
                            lastSegment = lastSegment.baseSubSequence(lastSegment.getStartOffset(), segment.getEndOffset());
                        }
                    }
                }
            }

            if (lastSegment != null) mergedSequences.add(lastSegment);

            if (mergedSequences.size() == 1 && mergedSequences.get(0).getStartOffset() == startOffset && mergedSequences.get(0).getEndOffset() == endOffset) {
                return mergedSequences.get(0);
            } else if (mergedSequences.size() != 0) {
                return new SegmentedSequence(mergedSequences, startOffset, endOffset);
            }
        }
        return BasedSequence.NULL;
    }

    private SegmentedSequence(List<BasedSequence> segments, int startOffset, int endOffset) {
        super(0);

        this.baseSeq = segments.get(0).getBaseSequence();
        this.startOffset = startOffset;
        this.endOffset = endOffset;

        int length = 0;

        BasedSequence base = segments.size() > 0 ? segments.get(0).getBaseSequence() : BasedSequence.NULL;

        int index = 0;
        int lastEnd = base.getStartOffset();

        for (BasedSequence segment : segments) {
            assert base.getBase() == segment.getBase() : "all segments must come from the same base sequence, segments[" + index + "], length so far: " + length;
            assert segment.getStartOffset() >= lastEnd : "segments must be in increasing index order from base sequence start=" + segment.getStartOffset() + " lastEnd:" + lastEnd + ", length=" + length + " at index: " + index;
            lastEnd = segment.getEndOffset();
            length += segment.length();
            index++;
        }

        this.baseStartOffset = 0;
        this.length = length;
        this.baseOffsets = new int[length + 1];
        int nonBasedChars = 0;
        int len = 0;
//        StringBuilder sb = null;

        for (BasedSequence basedSequence : segments) {
            int ciMax = basedSequence.length();

            for (int ci = 0; ci < ciMax; ci++) {
                int offset = basedSequence.getIndexOffset(ci);
                if (offset < 0) {
//                    if (sb == null) sb = new StringBuilder();
//                    sb.append(basedSequence.charAt(ci));
//                    offset = -sb.length();
                    offset = -(int) basedSequence.charAt(ci) - 1;
                    nonBasedChars++;
                }

                assert ci + len < this.baseOffsets.length : "Incorrect array size calculation: length: " + length + " ci + len: " + (ci + len);
                this.baseOffsets[ci + len] = offset;
            }

            len += ciMax;
        }

        int end = baseOffsets[length - 1];
        baseOffsets[length] = end < 0 ? end - 1 : end + 1;
        this.nonBaseChars = nonBasedChars > 0;

        if (base.isOption(O_COLLECT_SEGMENTED_STATS)) {
            SegmentedSequenceStats stats = base.getOption(SEGMENTED_STATS);
            if (stats != null) {
                stats.addStats(startOffset, endOffset, length, nonBasedChars, segments.size());
            }
        }

//        if (sb != null) {
//            this.nonBaseChars = sb.toString().toCharArray();
//        } else {
//            this.nonBaseChars = null;
//        }
    }

    private SegmentedSequence(final BasedSequence baseSeq, final int[] baseOffsets, final int baseStartOffset, final boolean nonBaseChars, final int length) {
        super(0);

        int iMax = baseOffsets.length - 1;
        assert baseStartOffset + length <= iMax : "Sub-sequence offsets list length < baseStartOffset + sub-sequence length";

        int startOffset = 0;
        int endOffset = 0;

        if (!nonBaseChars) {
            if (baseStartOffset < iMax) {
                // start is the offset at our start, even when length = 0
                startOffset = baseOffsets[baseStartOffset];
            } else {
                startOffset = baseSeq.getEndOffset();
            }

            if (length == 0) {
                endOffset = startOffset;
            } else {
                endOffset = baseOffsets[baseStartOffset + length - 1] + 1;
                assert startOffset <= endOffset;
            }
        } else {
            // start is the first real start in this sequence or after it in the parent
            boolean finished = false;

            for (int iS = baseStartOffset; iS < iMax; iS++) {
                if (baseOffsets[iS] < 0) continue;
                startOffset = baseOffsets[iS];

                if (length != 0) {
                    // end is the last real offset + 1 in this sequence up to the start index where startOffset was found
                    for (int iE = baseStartOffset + length; iE-- > iS; ) {
                        if (baseOffsets[iE] < 0) continue;

                        endOffset = baseOffsets[iE] + 1;
                        assert startOffset <= endOffset;

                        finished = true;
                        break;
                    }
                }

                if (!finished) {
                    endOffset = startOffset;
                }

                finished = true;
                break;
            }

            if (!finished) {
                // if no real start after then it is the base's end since we had no real start after, these chars and after are all out of base chars
                startOffset = baseSeq.getEndOffset();
                endOffset = startOffset;
            }
        }

        this.baseSeq = baseSeq;
        this.baseOffsets = baseOffsets;
        this.baseStartOffset = baseStartOffset;
        this.nonBaseChars = nonBaseChars;
        this.length = length;
        this.startOffset = startOffset;
        this.endOffset = endOffset;
    }

    @Override
    public int length() {
        return length;
    }

    @NotNull
    @Override
    public Range getSourceRange() {
        return Range.of(getStartOffset(), getEndOffset());
    }

    @Override
    public char charAt(int index) {
        if (index < 0 || index >= length) {
            throw new StringIndexOutOfBoundsException("String index: " + index + " out of range: 0, " + length());
        }

        int offset = baseOffsets[baseStartOffset + index];

        if (offset < 0) {
            /* KLUDGE: allows having characters which are not from original base sequence
                       but with the only penalty for charAt access being an extra indirection,
                       which is a small price to pay for having the flexibility of adding out of
                       context text to the based sequence.
             */
            return (char) (-offset - 1);
        }
        return baseSeq.charAt(offset);
    }

    @NotNull
    @Override
    public BasedSequence baseSubSequence(int startIndex, int endIndex) {
        if (startIndex < 0 || startIndex > baseSeq.length()) {
            throw new StringIndexOutOfBoundsException("String index out of range: " + startIndex);
        }
        if (endIndex < 0 || endIndex > baseSeq.length()) {
            throw new StringIndexOutOfBoundsException("String index out of range: " + endIndex);
        }

        return baseSeq.baseSubSequence(startIndex, endIndex);
    }

    @NotNull
    @Override
    public BasedSequence subSequence(int startIndex, int endIndex) {
        if (startIndex < 0 || startIndex > length) {
            throw new StringIndexOutOfBoundsException("String index out of range: " + startIndex);
        }

        if (endIndex < 0 || endIndex > length) {
            throw new StringIndexOutOfBoundsException("String index out of range: " + endIndex);
        }

        if (startIndex == 0 && endIndex == length) {
            return this;
        } else {
            return new SegmentedSequence(baseSeq, baseOffsets, baseStartOffset + startIndex, nonBaseChars, endIndex - startIndex);
        }
    }

    public static BasedSequence of(BasedSequence... segments) {
        return of(new ArrayIterable<>(segments));
    }
}
