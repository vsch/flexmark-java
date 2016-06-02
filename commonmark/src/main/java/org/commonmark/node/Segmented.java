package org.commonmark.node;

import org.commonmark.internal.util.BasedSequence;

import java.util.List;

/**
 * A node that uses delimiters in the source form (e.g. <code>*bold*</code>).
 */
public interface Segmented {

    /**
     * @return the opening (beginning) delimiter, e.g. <code>*</code>
     */
    BasedSequence getSegmentChars(int index);
    BasedSequence getContentChars();
    BasedSequence getContentChars(int startIndex, int endIndex);
    List<BasedSequence> getContentCharsList();
    List<BasedSequence> getContentCharsList(int startIndex, int endIndex);
}
