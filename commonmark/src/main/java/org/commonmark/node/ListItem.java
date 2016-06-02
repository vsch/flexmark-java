package org.commonmark.node;

import org.commonmark.internal.BlockContent;
import org.commonmark.internal.util.BasedSequence;

import java.util.List;

public class ListItem extends Block {
    public ListItem() {
    }

    public ListItem(BasedSequence chars) {
        super(chars);
    }

    public ListItem(BasedSequence chars, List<BasedSequence> segments) {
        super(chars, segments);
    }

    public ListItem(BlockContent blockContent) {
        super(blockContent);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
