package com.vladsch.flexmark.util.ast;

import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A node that uses delimiters in the source form (e.g. <code>*bold*</code>).
 */
public interface Content {
    @NotNull BasedSequence getSpanningChars();
    int getLineCount();
    @NotNull BasedSequence getLineChars(int index);
    @NotNull BasedSequence getContentChars();
    @NotNull BasedSequence getContentChars(int startLine, int endLine);
    @NotNull List<BasedSequence> getContentLines();
    @NotNull List<BasedSequence> getContentLines(int startLine, int endLine);
}
