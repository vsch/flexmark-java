package com.vladsch.flexmark.ext.tables.internal;

import com.vladsch.flexmark.ext.tables.TableCell;
import com.vladsch.flexmark.util.ast.DoNotDecorate;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

/**
 * Table cell separator only used during parsing, not part of the AST, use the {@link TableCell#getOpeningMarker()} and {@link TableCell#getClosingMarker()}
 */
class TableColumnSeparator extends Node implements DoNotDecorate {
    public TableColumnSeparator() {
    }

    public TableColumnSeparator(BasedSequence chars) {
        super(chars);
    }

    public TableColumnSeparator(String chars) {
        super(BasedSequence.of(chars));
    }

    @NotNull
    @Override
    public BasedSequence[] getSegments() {
        return EMPTY_SEGMENTS;
    }

    @Override
    public void getAstExtra(@NotNull StringBuilder out) {
        astExtraChars(out);
    }

    @NotNull
    @Override
    protected String toStringAttributes() {
        return "text=" + getChars();
    }
}
