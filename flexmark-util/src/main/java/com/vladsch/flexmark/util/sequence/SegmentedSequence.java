package com.vladsch.flexmark.util.sequence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Integer.MIN_VALUE;

/**
 * A CharSequence that references original char sequence and maps '\0' to '\uFFFD'
 * a subSequence() returns a sub-sequence from the original base sequence
 */
public final class SegmentedSequence extends BasedSequenceImpl {
    private final BasedSequence baseSeq;  // base sequence
    private final char[] nonBaseChars;    // all non-base characters, offset by baseStartOffset. When baseOffsets[] < 0, take -ve - 1 to get index into this array
    private final int[] baseOffsets;      // list of base offsets, offset by baseStartOffset
    private final int baseStartOffset;    // start offset for baseOffsets of this sequence, offset from baseSeq for all chars, including non-base chars
    private final int length;             // length of this sequence
    private final int startOffset;        // this sequence's start offset in base
    private final int endOffset;          // this sequence's end offset in base

    @Override
    public Object getBase() {
        return baseSeq.getBase();
    }

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

    @Override
    public Range getIndexRange(final int startOffset, final int endOffset) {
        // we assume that start/end is within our range
        int start = MIN_VALUE;
        int end = MIN_VALUE;
        for (int i = 0; i < baseOffsets.length; i++) {
            if (baseOffsets[i] == startOffset) {
                start = i;
            }
            if (baseOffsets[i] == endOffset) {
                end = i;
            }
            if (start != MIN_VALUE && end != MIN_VALUE) break;
        }

        if (start < 0) start = 0;
        if (end < start) end = start;
        if (start > end) start = end;
        return Range.of(start, end);
    }

    public int[] getBaseOffsets() {
        return baseOffsets;
    }

    public int getBaseStartOffset() {
        return baseStartOffset;
    }

    @Override
    public int getIndexOffset(int index) {
        if (index < 0 || index > length) {
            throw new StringIndexOutOfBoundsException("String index: " + index + " out of range: 0, " + length());
        }

        if (index == length) {
            if (length == 0) {
                throw new StringIndexOutOfBoundsException("String index: " + index + " out of range: 0, " + length());
            }
            int offset = baseOffsets[baseStartOffset + index - 1];
            if (offset < 0) {
                return -1;
            } else {
                return offset + 1;
            }
        }
        int offset = baseOffsets[baseStartOffset + index];
        return offset < 0 ? -1 : offset;
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
     * all the segments were concatenated, while still maintaining
     * the original offset for each character when using {@link #getIndexOffset(int)}(int index)
     */
    public static BasedSequence of(List<BasedSequence> segments) {
        if (segments.size() != 0) {
            BasedSequence lastSegment = null;
            BasedSequence firstSegment = segments.get(0);
            BasedSequence base = firstSegment.getBaseSequence();
            ArrayList<BasedSequence> mergedSequences = new ArrayList<BasedSequence>();
            int startOffset = -1;
            int endOffset = -1;

            for (BasedSequence segment : segments) {
                if (segment.isNull()) continue;

                if (base.getBase() != segment.getBase()) {
                    assert false : "all segments must come from the same base sequence";
                }

                if (startOffset == -1) startOffset = segment.getStartOffset();
                endOffset = segment.getEndOffset();

                if (segment.isEmpty()) continue;  // skip empty sequences, they serve no purpose

                if (segment instanceof PrefixedSubSequence || segment instanceof SegmentedSequence) {
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

            if (mergedSequences.size() == 1) {
                return mergedSequences.get(0);
            } else if (mergedSequences.size() != 0) {
                return new SegmentedSequence(mergedSequences, startOffset, endOffset);
            }
        }
        return SubSequence.NULL;
    }

    private SegmentedSequence(List<BasedSequence> segments, final int startOffset, final int endOffset) {
        this.baseSeq = segments.get(0).getBaseSequence();
        this.startOffset = startOffset;
        this.endOffset = endOffset;

        int length = 0;

        BasedSequence base = segments.size() > 0 ? segments.get(0).getBaseSequence() : SubSequence.NULL;

        int index = 0;
        int lastEnd = base.getStartOffset();
        for (BasedSequence segment : segments) {
            assert base.getBase() == segment.getBase() : "all segments must come from the same base sequence, segments[" + index + "], length so far: " + length;
            if (segment.getStartOffset() < lastEnd) {
                assert false : "segments must be in increasing index order from base sequence start=" + segment.getStartOffset() + ", length=" + length + " at index: " + index;
            }
            lastEnd = segment.getEndOffset();
            length += segment.length();
            index++;
        }

        this.baseStartOffset = 0;
        this.length = length;
        this.baseOffsets = new int[length];
        int len = 0;
        StringBuilder sb = null;

        for (BasedSequence basedSequence : segments) {
            int ciMax = basedSequence.length();

            for (int ci = 0; ci < ciMax; ci++) {
                int offset = basedSequence.getIndexOffset(ci);
                if (offset < 0) {
                    if (sb == null) sb = new StringBuilder();
                    sb.append(basedSequence.charAt(ci));
                    offset = -sb.length();
                }

                assert ci + len < this.baseOffsets.length : "Incorrect array size calculation: length: " + length + " ci + len: " + (ci + len);
                this.baseOffsets[ci + len] = offset;
            }

            len += ciMax;
        }

        if (sb != null) {
            this.nonBaseChars = sb.toString().toCharArray();
        } else {
            this.nonBaseChars = null;
        }
    }

    private SegmentedSequence(BasedSequence baseSeq, int[] baseOffsets, int baseStartOffset, char[] nonBaseChars, int length) {
        this.baseSeq = baseSeq;
        this.baseOffsets = baseOffsets;
        this.baseStartOffset = baseStartOffset;
        this.nonBaseChars = nonBaseChars;
        this.length = length;
        this.startOffset = computeStartOffset();
        this.endOffset = computeEndOffset();
    }

    private int computeStartOffset() {
        int iMax = baseOffsets.length;
        assert baseStartOffset + length <= iMax : "Sub-sequence offsets list length < baseStartOffset + sub-sequence length";

        if (nonBaseChars != null) {
            // start is the first real start in this sequence or after it, in the parent 
            for (int i = baseStartOffset; i < iMax; i++) {
                if (baseOffsets[i] >= 0) return baseOffsets[i];
            }

            // if no real start after then it is the base's end since we had no real start after, these chars and after are all out of base chars
            return baseSeq.getEndOffset();
        }

        // here there are no nonBaseChars, all sequences are based sequences or we are at end of parent base sequence
        return baseStartOffset < iMax ? baseOffsets[baseStartOffset] : baseSeq.getEndOffset();
    }

    private int computeEndOffset() {
        // ensure that 0 length end returns start
        if (length == 0) return getStartOffset();

        int iMax = baseOffsets.length;

        if (nonBaseChars != null) {
            // end is the last real end in this sequence
            for (int i = baseStartOffset + length; i-- > 0; ) {
                if (baseOffsets[i] >= 0) return baseOffsets[i] + 1;
            }

            // failing that it is the same as startOffset
            return getStartOffset();
        }

        // here there are no nonBaseChars, all sequences are based sequences
        return baseOffsets[baseStartOffset + length - 1] + 1;
    }

    @Override
    public int length() {
        return length;
    }

    @Override
    public Range getSourceRange() {
        return new Range(getStartOffset(), getEndOffset());
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
            return nonBaseChars[-offset - 1];
        }
        return baseSeq.charAt(offset);
    }

    @Override
    public BasedSequence baseSubSequence(int start, int end) {
        if (start < 0 || start > baseSeq.length()) {
            throw new StringIndexOutOfBoundsException("String index out of range: " + start);
        }
        if (end < 0 || end > baseSeq.length()) {
            throw new StringIndexOutOfBoundsException("String index out of range: " + end);
        }

        return baseSeq.baseSubSequence(start, end);
    }

    @Override
    public BasedSequence subSequence(int start, int end) {
        if (start < 0 || start > length) {
            throw new StringIndexOutOfBoundsException("String index out of range: " + start);
        }

        if (end < 0 || end > length) {
            throw new StringIndexOutOfBoundsException("String index out of range: " + end);
        }

        if (start == 0 && end == length) {
            return this;
        } else {
            return new SegmentedSequence(baseSeq, baseOffsets, baseStartOffset + start, nonBaseChars, end - start);
        }
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || (obj instanceof CharSequence && toString().equals(obj.toString()));
    }
    
    
    public static BasedSequence of(BasedSequence... segments) {
        return of(Arrays.asList(segments));
    }
}
