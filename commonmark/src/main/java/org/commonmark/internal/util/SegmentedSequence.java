package org.commonmark.internal.util;

import java.util.ArrayList;
import java.util.List;

/**
 * A CharSequence that references original char sequence and maps '\0' to '\uFFFD'
 * a subSequence() returns a sub-sequence from the original base sequence
 */
public class SegmentedSequence extends BasedSequenceImpl {

    final protected CharSequence base;
    final protected CharMapper mapper;
    final protected char[] chars;
    final protected int charsOffset;
    final protected int[] startOffsets;         // list of start/end indices
    final protected int[] endOffsets;      // list of start/end indices
    final protected int length;      // list of start/end indices

    public CharSequence getBase() {
        return base;
    }

    public int getStartOffset() {
        return startOffsets[0];
    }

    public int getEndOffset() {
        return endOffsets[endOffsets.length - 1];
    }

    public SegmentedSequence(List<BasedSequence> basedSequences) {
        this(basedSequences, NullCharacterMapper.INSTANCE);
    }

    public SegmentedSequence(List<BasedSequence> basedSequences, CharMapper mapper) {
        this.base = basedSequences.get(0);
        this.mapper = mapper;

        int length = 0;
        for (BasedSequence basedSequence : basedSequences) {
            assert this.base == basedSequence.getBase() : "all segments must come from the same base sequence";
            assert basedSequence.getStartOffset() >= length : "segments must be in increasing index order from base sequence";
            length += basedSequence.length();
        }

        this.charsOffset = 0;
        this.length = length;
        this.chars = new char[length];
        this.startOffsets = new int[basedSequences.size()];
        this.endOffsets = new int[basedSequences.size()];
        int i = 0;
        length = 0;
        for (BasedSequence basedSequence : basedSequences) {
            this.startOffsets[i] = basedSequence.getStartOffset();
            this.endOffsets[i] = basedSequence.getEndOffset();
            int ciMax = basedSequence.length();

            for (int ci = 0; ci < ciMax; ci++){
                 this.chars[ci+length] = basedSequence.charAt(ci);
            }

            length += ciMax;
        }
    }

    private SegmentedSequence(CharSequence base, char[] chars, int charsOffset, int[] startOffsets, int[] endOffsets, int length, CharMapper mapper) {
        this.base = base;
        this.mapper = mapper;
        this.chars = chars;
        this.charsOffset = charsOffset;
        this.startOffsets = startOffsets;
        this.endOffsets = endOffsets;
        this.length = length;
    }

    @Override
    public int length() {
        return length;
    }

    @Override
    public SourceRange getRange() {
        return new SourceRange(getStartOffset(), getEndOffset());
    }

    @Override
    public char charAt(int index) {
        if (index < 0 || index > length) {
            throw new StringIndexOutOfBoundsException("String index out of range: " + index);
        }
        char c = chars[charsOffset + index];
        return c == '\0' ? '\uFFFD' : c;
    }

    @Override
    public BasedSequence subSequence(int start, int end) {
        if (start < 0 || start > length) {
            throw new StringIndexOutOfBoundsException("String index out of range: " + start);
        }
        if (end < 0 || end > length) {
            throw new StringIndexOutOfBoundsException("String index out of range: " + end);
        }

        // here we need to extract a list of sub-sequences
        ArrayList<BasedSequence> subList = new ArrayList<>();
        int startIndex = -1;
        int endIndex = -1;
        int startOffset = 0;
        int endOffset = 0;
        int iMax = startOffsets.length;
        int i = 0;
        int length = 0;

        for (i = 0; i < iMax; i++) {
            if (length >= start && startIndex == -1) {
                startIndex = i;
                startOffset = start - length + startOffsets[i];
            }

            if (length >= end) {
                endIndex = i;
                endOffset = end - length + startOffsets[i];
                break;
            }

            length += endOffsets[i] - startOffsets[i];
        }

        if (startIndex == endIndex) {
            // only one just return SubSequence
            return new SubSequence(base, startOffset, endOffset, mapper);
        }

        int[] startOffsets = new int[endIndex - startIndex + 1];
        int[] endOffsets = new int[endIndex - startIndex + 1];

        startOffsets[0] = startOffset;
        endOffsets[0] = this.endOffsets[startIndex];
        if (endIndex - startIndex - 1 > 0) {
            System.arraycopy(this.startOffsets, startIndex, startOffsets, 1, endIndex - startIndex - 1);
            System.arraycopy(this.endOffsets, endIndex, endOffsets, 1, endIndex - startIndex - 1);
        }
        startOffsets[endIndex] = this.startOffsets[endIndex];
        endOffsets[endIndex] = endOffset;

        return new SegmentedSequence(base, this.chars, this.charsOffset + start, startOffsets, endOffsets, end - start, mapper);
    }

    @Override
    public BasedSequence toMapped(CharMapper mapper) {
        return new SegmentedSequence(base, this.chars, this.charsOffset, startOffsets, endOffsets, length, mapper);
    }

    @Override
    public String toString() {
        return String.valueOf(chars, charsOffset, length);
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
