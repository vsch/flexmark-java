package com.vladsch.flexmark.ext.tables;

import com.vladsch.flexmark.internal.BlockContent;
import com.vladsch.flexmark.internal.util.sequence.BasedSequence;
import com.vladsch.flexmark.node.CustomBlock;

import java.util.List;

/**
 * Table block containing a {@link TableHead} and optionally a {@link TableBody}.
 */
public class TableBlock extends CustomBlock<TableVisitor> {
    public TableBlock() {
    }

    public TableBlock(BasedSequence chars) {
        super(chars);
    }

    public TableBlock(BasedSequence chars, List<BasedSequence> lineSegments) {
        super(chars, lineSegments);
    }

    public TableBlock(List<BasedSequence> lineSegments) {
        super(lineSegments);
    }

    public TableBlock(BlockContent blockContent) {
        super(blockContent);
    }

    @Override
    public BasedSequence[] getSegments() {
        return new BasedSequence[0];
    }

    @Override
    public void accept(TableVisitor visitor) {
        visitor.visit(this);
    }
}
