package com.vladsch.flexmark.ext.tables;

import com.vladsch.flexmark.util.ast.DoNotCollectText;
import com.vladsch.flexmark.util.ast.DoNotDecorate;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

/**
 * Body part of a {@link TableBlock} containing {@link TableRow TableRows}.
 */
public class TableSeparator extends Node implements DoNotDecorate, DoNotCollectText {
    @NotNull
    @Override
    public BasedSequence[] getSegments() {
        return EMPTY_SEGMENTS;
    }

    public TableSeparator() {
    }

    public TableSeparator(BasedSequence chars) {
        super(chars);
    }
}
