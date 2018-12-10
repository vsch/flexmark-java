package com.vladsch.flexmark.ext.tables.internal;

import com.vladsch.flexmark.ast.DoNotDecorate;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ext.tables.TableCell;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.CharSubSequence;

/**
 * Table cell separator only used during parsing, not part of the AST, use the {@link TableCell#getOpeningMarker()} and {@link TableCell#getClosingMarker()}
 *
 */
class TableColumnSeparator extends Node implements DoNotDecorate {
    public TableColumnSeparator() {
    }

    public TableColumnSeparator(BasedSequence chars) {
        super(chars);
    }

    public TableColumnSeparator(String chars) {
        super(CharSubSequence.of(chars));
    }

    @Override
    public BasedSequence[] getSegments() {
        return EMPTY_SEGMENTS;
    }

    @Override
    public void getAstExtra(StringBuilder out) {
        astExtraChars(out);
    }

    @Override
    protected String toStringAttributes() {
        return "text=" + getChars();
    }

}
