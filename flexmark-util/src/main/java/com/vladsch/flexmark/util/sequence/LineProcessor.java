package com.vladsch.flexmark.util.sequence;

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
