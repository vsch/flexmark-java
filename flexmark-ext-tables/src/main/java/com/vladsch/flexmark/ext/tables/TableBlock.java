package com.vladsch.flexmark.ext.tables;

import com.vladsch.flexmark.internal.util.BasedSequence;
import com.vladsch.flexmark.node.CustomBlock;

/**
 * Table block containing a {@link TableHead} and optionally a {@link TableBody}.
 */
public class TableBlock extends CustomBlock {
    @Override
    public BasedSequence[] getSegments() {
        return new BasedSequence[0];
    }
}
