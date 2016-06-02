package org.commonmark.node;

/**
 * A node that uses delimiters in the source form (e.g. <code>*bold*</code>).
 */
public interface Segmented {

    /**
     * @return the opening (beginning) delimiter, e.g. <code>*</code>
     */
    CharSequence getSegmentChars(CharSequence charSequence, int segmentIndex);

    int[] getSegmentOffsets();
    int getSegmentStartOffset(int segmentIndex);
    int getSegmentEndOffset(int segmentIndex);
}
