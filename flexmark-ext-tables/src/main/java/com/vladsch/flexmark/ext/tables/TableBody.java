package com.vladsch.flexmark.ext.tables;

import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

/**
 * Body part of a {@link TableBlock} containing {@link TableRow TableRows}.
 */
public class TableBody extends Node {
    @NotNull
    @Override
    public BasedSequence[] getSegments() {
        return EMPTY_SEGMENTS;
    }

    public TableBody() {
    }

    public TableBody(BasedSequence chars) {
        super(chars);
    }
}
