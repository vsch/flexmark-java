package org.commonmark.internal.util;

/**
 * A CharSequence that references original char sequence and maps '\0' to '\uFFFD'
 * a subSequence() returns a sub-sequence from the original base sequence
 */
public interface BasedSequence extends CharSequence {
    public CharSequence getBase();

    public int getStartOffset();

    public int getEndOffset();

    @Override
    public BasedSequence subSequence(int start, int end);
}
