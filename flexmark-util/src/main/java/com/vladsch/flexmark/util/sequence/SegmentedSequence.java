package com.vladsch.flexmark.util.sequence;

import java.util.ArrayList;
import java.util.List;

/**
 * A CharSequence that references original char sequence and maps '\0' to '\uFFFD'
 * a subSequence() returns a sub-sequence from the original base sequence
 */
public class SegmentedSequence extends BasedSequenceImpl {
    protected final CharSequence baseSeq;
    protected final char[] nonBaseChars;
    protected final int[] baseOffsets;
    protected final int baseStartOffset;
    protected final int length;      // list of start/end indices

    public CharSequence getBase() {
        return baseSeq;
    }

    public int getStartOffset() {
        int iMax = baseOffsets.length;

        if (nonBaseChars != null) {
            for (int i = baseStartOffset; i < iMax; i++) {
                if (baseOffsets[i] >= 0) return baseOffsets[i];
            }
            return 0;
        }
        return iMax > 0 ? baseOffsets[baseStartOffset] : 0;
    }

    public int getEndOffset() {
        int iMax = baseOffsets.length;

        if (nonBaseChars != null) {
            for (int i = iMax; i-- > baseStartOffset; ) {
                if (baseOffsets[i] >= 0) return baseOffsets[i];
            }
            return 0;
        }

        // ensure that 0 length end returns start
        if (length == 0) return iMax > 0 ? baseOffsets[baseStartOffset] : 0;
        return iMax > 0 ? baseOffsets[baseStartOffset + length - 1] + 1 : 0;
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
            if (index == 0) {
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

    public static BasedSequence of(List<BasedSequence> segments, BasedSequence empty) {
        if (segments.size() == 0) {
            return empty;
        }

        BasedSequence lastSegment = null;
        BasedSequence firstSegment = segments.get(0);
        CharSequence base = firstSegment.getBase();
        ArrayList<BasedSequence> mergedSequences = new ArrayList<>();

        for (BasedSequence basedSequence : segments) {
            assert base == basedSequence.getBase() : "all segments must come from the same base sequence";

            if (basedSequence instanceof PrefixedSubSequence || basedSequence instanceof SegmentedSequence) {
                if (lastSegment != null) mergedSequences.add(lastSegment);
                mergedSequences.add(basedSequence);
                lastSegment = null;
            } else {
                if (lastSegment == null) {
                    lastSegment = basedSequence;
                } else {
                    if (lastSegment.getEndOffset() != basedSequence.getStartOffset()) {
                        mergedSequences.add(lastSegment);
                        lastSegment = basedSequence;
                    } else {
                        lastSegment = lastSegment.baseSubSequence(lastSegment.getStartOffset(), basedSequence.getEndOffset());
                    }
                }
            }
        }

        if (lastSegment != null) mergedSequences.add(lastSegment);

        if (mergedSequences.size() == 1) {
            return mergedSequences.get(0);
        }

        return new SegmentedSequence(mergedSequences);
    }

    private SegmentedSequence(List<BasedSequence> segments) {
        this.baseSeq = segments.get(0).getBase();

        int length = 0;

        CharSequence base = segments.size() > 0 ? segments.get(0).getBase() : null;

        for (BasedSequence basedSequence : segments) {
            assert base == basedSequence.getBase() : "all segments must come from the same base sequence";
            assert basedSequence.getStartOffset() >= length : "segments must be in increasing index order from base sequence start=" + basedSequence.getStartOffset() + ", length=" + length;
            length += basedSequence.length();
        }

        this.baseStartOffset = 0;
        this.length = length;
        this.baseOffsets = new int[length];
        int i = 0;
        length = 0;
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

                this.baseOffsets[ci + length] = offset;
            }

            length += ciMax;
            i++;
        }

        if (sb != null) {
            this.nonBaseChars = sb.toString().toCharArray();
        } else {
            this.nonBaseChars = null;
        }
    }

    private SegmentedSequence(CharSequence baseSeq, int[] baseOffsets, int baseStartOffset, char[] nonBaseChars, int length) {
        this.baseSeq = baseSeq;
        this.baseOffsets = baseOffsets;
        this.baseStartOffset = baseStartOffset;
        this.nonBaseChars = nonBaseChars;
        this.length = length;
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
            // KLUDGE: allows having characters which are not from original base sequence
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

        return new SubSequence(baseSeq, start, end);
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
            if (nonBaseChars != null) {
                return new SegmentedSequence(baseSeq, baseOffsets, baseStartOffset + start, nonBaseChars, end - start);
            } else {
                return new SegmentedSequence(baseSeq, baseOffsets, baseStartOffset + start, nonBaseChars, end - start);
            }
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
}
