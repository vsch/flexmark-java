package com.vladsch.flexmark.util.html;

import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

public interface LineProcessor {
    /**
     * Line processor
     * @param line   line from appendable
     * @param lineInfo information about the line
     * @return true to continue processing, false to stop
     */
    boolean processLine(BasedSequence line, @NotNull LineInfo lineInfo);
}
