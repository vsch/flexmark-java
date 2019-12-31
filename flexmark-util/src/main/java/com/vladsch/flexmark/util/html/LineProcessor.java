package com.vladsch.flexmark.util.html;

import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

public interface LineProcessor {
    /**
     * Line processor
     * @param line   line from appendable
     * @param index  index of the line in appendable
     * @param textStart start index of text of line (if there is text before it is prefix)
     * @param textEnd   end index of text of line (if there is more text it is EOL)
     * @param sumPrefix total prefix length for all previous lines
     * @param sumText   total text only length for all previous lines
     * @param sumLength total length for all previous lines
     * @return true to continue processing, false to stop
     */
    boolean processLine(@NotNull BasedSequence line, int index, int textStart, int textEnd, int sumPrefix, int sumText, int sumLength);
}
