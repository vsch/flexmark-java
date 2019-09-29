package com.vladsch.flexmark.util.sequence;

import java.util.ArrayList;
import java.util.List;

/**
 * A CharSequence that references original char sequence and maps '\0' to '\uFFFD'
 * a subSequence() returns a sub-sequence from the original base sequence
 */
@SuppressWarnings("SameParameterValue")
public interface BasedSequence extends RichCharSequence<BasedSequence> {
    BasedSequence NULL = new EmptyBasedSequence();
    BasedSequence EMPTY = new EmptyBasedSequence();
    BasedSequence EOL = CharSubSequence.of(RichCharSequence.EOL);
    BasedSequence SPACE = CharSubSequence.of(RichCharSequence.SPACE);
    List<BasedSequence> EMPTY_LIST = new ArrayList<BasedSequence>();
    BasedSequence[] EMPTY_ARRAY = new BasedSequence[0];
    BasedSequence[] EMPTY_SEGMENTS = new BasedSequence[0];

    /**
     * Get the underlying object on which this sequence contents are based
     *
     * @return underlying object containing original text
     */
    Object getBase();

    /**
     * Get the base sequence for the text
     *
     * @return base sequence
     */
    BasedSequence getBaseSequence();

    /**
     * Get the start offset of this sequence into {@link #getBaseSequence()} and {@link #getBase()} original text source.
     *
     * @return start offset in original text
     */
    int getStartOffset();

    /**
     * Get the end offset of this sequence into {@link #getBaseSequence()} and {@link #getBase()} original text source.
     *
     * @return end offset in original text
     */
    int getEndOffset();

    /**
     * Get the offset of index in this sequence mapped to offset into {@link #getBaseSequence()} and {@link #getBase()} original text source.
     *
     * @param index index for which to get the offset in original source
     * @return offset of index of this sequence in original text
     */
    int getIndexOffset(int index);

    /**
     * Get the range of indices that map into {@link #getBaseSequence()} with startOffset and endOffset
     *
     * @param startOffset start offset into base sequence
     * @param endOffset   end offset into base sequence
     * @return range into this sequence that spans start and end offset.
     */
    Range getIndexRange(int startOffset, int endOffset);

    /**
     * Get the range of this sequence in original {@link #getBaseSequence()} and {@link #getBase()} original text source.
     *
     * @return Range of start offset and end offset
     */
    Range getSourceRange();

    /**
     * Get a portion of the original sequence that this sequence is based on
     *
     * @param start offset from 0 of original sequence
     * @param end   offset from 0 of original sequence
     * @return based sequence whose contents reflect the selected portion
     */
    BasedSequence baseSubSequence(int start, int end);

    /**
     * Get a portion of the original sequence that this sequence is based on
     *
     * @param start offset from 0 of original sequence
     * @return based sequence from start to the end
     */
    BasedSequence baseSubSequence(int start);

    /**
     * Safe, if index out of range returns '\0'
     *
     * @param index index in string
     * @return character or '\0' if index out of sequence range
     */
    char safeCharAt(int index);

    /**
     * Safe, if index out of range but based sequence has characters will return those, else returns '\0'
     * <p>
     * Allows peeking into preceding/following characters to the ones contained in this sequence
     *
     * @param index index in string
     * @return character or '\0' if index out of base sequence
     */
    char safeBaseCharAt(int index);

    /**
     * Get empty prefix to this sequence
     *
     * @return same as subSequence(0,0)
     */
    BasedSequence getEmptyPrefix();

    /**
     * Get empty suffix to this sequence
     *
     * @return same as subSequence(length())
     */
    BasedSequence getEmptySuffix();

    /**
     * Get the unescaped string of this sequence content
     *
     * @return unescaped text
     */
    String unescape();

    /**
     * Get the unescaped string of this sequence content without unescaping entities
     *
     * @return unescaped text
     */
    String unescapeNoEntities();

    /**
     * Get the unescaped string of this sequence content
     *
     * @param textMapper replaced text mapper which will be uses to map unescaped index to original source index
     * @return unescaped text in based sequence
     */
    BasedSequence unescape(ReplacedTextMapper textMapper);

    /**
     * replace any \r\n and \r by \n
     *
     * @param textMapper replaced text mapper which will be uses to map unescaped index to original source index
     * @return based sequence with only \n for line separators
     */
    BasedSequence normalizeEOL(ReplacedTextMapper textMapper);

    /**
     * replace any \r\n and \r by \n, append terminating EOL if one is not present
     *
     * @param textMapper replaced text mapper which will be uses to map unescaped index to original source index
     * @return based sequence with only \n for line separators and terminated by \n
     */
    BasedSequence normalizeEndWithEOL(ReplacedTextMapper textMapper);

    /**
     * Test if the given sequence is a continuation of this sequence in original source text
     *
     * @param other sequence to test
     * @return true if the given sequence is a continuation of this one in the original text
     */
    boolean isContinuedBy(BasedSequence other);

    /**
     * Test if this sequence is a continuation of the given sequence in original source text
     *
     * @param other sequence to test
     * @return true if this sequence is a continuation of the given sequence in original source text
     */
    boolean isContinuationOf(BasedSequence other);

    /**
     * Splice the given sequence to the end of this one and return a BasedSequence of the result.
     * Does not copy anything, creates a new based sequence of the original text but one that spans
     * characters of this sequence and other
     *
     * @param other sequence to append to end of this one
     * @return based sequence that contains the span from start of this sequence and end of other
     * <p>
     * assertion will fail if the other sequence is not a continuation of this one
     */
    BasedSequence spliceAtEnd(BasedSequence other);

    /**
     * start/end offset based containment, not textual
     *
     * @param other based sequence from the same base
     * @return true if other is contained in this
     */
    boolean containsAllOf(BasedSequence other);

    /**
     * start/end offset based containment, not textual
     *
     * @param other based sequence from the same base
     * @return true if other is contained in this
     */
    boolean containsSomeOf(BasedSequence other);

    /**
     * Get the prefix part of this from other, start/end offset based containment, not textual
     *
     * @param other based sequence from the same base
     * @return prefix part of this as compared to other, start/end offset based, not content
     */
    BasedSequence prefixOf(BasedSequence other);

    /**
     * Get the suffix part of this from other, start/end offset based containment, not textual
     *
     * @param other based sequence from the same base
     * @return suffix part of this as compared to other, start/end offset based, not content
     */
    BasedSequence suffixOf(BasedSequence other);

    /**
     * start/end offset based intersection, not textual
     *
     * @param other based sequence from the same parent
     * @return sequence which is the intersection of the range of this and other
     */
    BasedSequence intersect(BasedSequence other);

    /**
     * Extend this based sequence to include characters from underlying based sequence
     *
     * @param charSet  set of characters to include
     * @param maxCount maximum extra characters to include
     * @return sequence which
     */
    BasedSequence extendByAny(CharSequence charSet, int maxCount);

    default BasedSequence extendByAny(CharSequence charSet) {
        return extendByAny(charSet, Integer.MAX_VALUE - getEndOffset());
    }

    default BasedSequence extendByOneOfAny(CharSequence charSet) {
        return extendByAny(charSet, 1);
    }

    /**
     * Extend this based sequence to include up to the next character from underlying based sequence
     *
     * @param charSet  set of characters to include
     * @param maxCount maximum extra characters to include
     * @return sequence which
     */
    BasedSequence extendToAny(CharSequence charSet, int maxCount);

    default BasedSequence extendToAny(CharSequence charSet) {
        return extendToAny(charSet, Integer.MAX_VALUE - getEndOffset());
    }

    /**
     * Extend this based sequence to include characters from underlying based sequence
     * taking tab expansion to 4th spaces into account
     *
     * @param maxColumns maximum columns to include
     * @return sequence which
     */
    BasedSequence prefixWithIndent(int maxColumns);

    default BasedSequence prefixWithIndent() {
        return prefixWithIndent(Integer.MAX_VALUE);
    }

    class EmptyBasedSequence extends BasedSequenceImpl {
        @Override
        public int length() {
            return 0;
        }

        @Override
        public char charAt(int index) {
            throw new StringIndexOutOfBoundsException("String index: " + index + " out of range: 0, " + length());
        }

        @Override
        public int getIndexOffset(int index) {
            if (index == 0) return 0;
            throw new StringIndexOutOfBoundsException("String index: " + index + " out of range: 0, " + length());
        }

        @Override
        public BasedSequence subSequence(int i, int i1) {
            if (i == 0 && i1 == 0) return this;
            throw new StringIndexOutOfBoundsException("EMPTY subSequence(" + i + "," + i1 + ") only subSequence(0, 0) is allowed");
        }

        @Override
        public BasedSequence baseSubSequence(int start, int end) {
            return subSequence(start, end);
        }

        @Override
        public BasedSequence getBaseSequence() {
            return BasedSequence.NULL;
        }

        @Override
        public BasedSequence getBase() {
            return BasedSequence.NULL;
        }

        @Override
        public int getStartOffset() {
            return 0;
        }

        @Override
        public int getEndOffset() {
            return 0;
        }

        @Override
        public Range getSourceRange() {
            return Range.NULL;
        }

        @Override
        public String toString() {
            return "";
        }
    }
}
