package com.vladsch.flexmark.ext.tables;

import com.vladsch.flexmark.util.ast.BlankLineBreakNode;
import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.ast.BlockContent;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Table block containing a {@link TableHead} and optionally a {@link TableBody}.
 */
public class TableBlock extends Block implements BlankLineBreakNode {
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

    @NotNull
    @Override
    public BasedSequence[] getSegments() {
        return new BasedSequence[0];
    }

    TableCaption getCaption() {
        Node child = getLastChild();
        return child instanceof TableCaption ? (TableCaption) child : null;
    }
}
