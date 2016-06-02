package org.commonmark.node;

import org.commonmark.internal.BlockContent;
import org.commonmark.internal.util.BasedSequence;

import java.util.List;

public class Document extends Block {
    public Document() {
    }

    public Document(BasedSequence chars) {
        super(chars);

    }

    public Document(BasedSequence chars, List<BasedSequence> segments) {
        super(chars, segments);
    }

    public Document(BlockContent blockContent) {
        super(blockContent);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
