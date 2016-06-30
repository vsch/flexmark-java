package com.vladsch.flexmark.ext.gfm.tables;

import com.vladsch.flexmark.internal.util.sequence.BasedSequence;
import com.vladsch.flexmark.node.CustomBlock;

/**
 * Table block containing a {@link TableHead} and optionally a {@link TableBody}.
 */
public class TableBlock extends CustomBlock {
    @Override
    public BasedSequence[] getSegments() {
        return EMPTY_SEGMENTS;
    }
}
