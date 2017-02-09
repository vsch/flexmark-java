package com.vladsch.flexmark.parser.delimiter;

import com.vladsch.flexmark.ast.Text;

/**
 * A delimiter run is one or more of the same delimiter character.
 */
public interface DelimiterRun {

    DelimiterRun getPrevious();
    DelimiterRun getNext();
    char getDelimiterChar();
    Text getNode();

    /**
     * @return whether this can open a delimiter
     */
    boolean canOpen();

    /**
     * @return whether this can close a delimiter
     */
    boolean canClose();

    /**
     * @return the number of characters in this delimiter run (that are left for processing)
     */
    int length();
}
