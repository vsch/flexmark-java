package com.vladsch.flexmark.ext.tables;

import com.vladsch.flexmark.ast.BlockContent;
import com.vladsch.flexmark.ast.CustomBlock;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.List;

/**
 * Table block containing a {@link TableHead} and optionally a {@link TableBody}.
 */
public class TableBlock extends CustomBlock {
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

    TableCaption getCaption() {
        Node child = getLastChild();
        return child instanceof TableCaption ? (TableCaption) child : null;
    }
}
