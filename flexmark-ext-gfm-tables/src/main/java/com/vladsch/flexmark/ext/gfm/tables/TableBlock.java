package com.vladsch.flexmark.ext.gfm.tables;

import com.vladsch.flexmark.ast.CustomBlock;
import com.vladsch.flexmark.util.sequence.BasedSequence;

/**
 * Table block containing a {@link TableHead} and optionally a {@link TableBody}.
 */
public class TableBlock extends CustomBlock {
    @Override
    public BasedSequence[] getSegments() {
        return EMPTY_SEGMENTS;
    }
}
