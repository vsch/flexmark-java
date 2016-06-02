package org.commonmark.node;

import org.commonmark.internal.BlockContent;
import org.commonmark.internal.util.BasedSequence;

import java.util.List;

public class ThematicBreak extends Block {

    public ThematicBreak() {
    }

    public ThematicBreak(BasedSequence chars) {
        super(chars);
    }

    public ThematicBreak(BasedSequence chars, List<BasedSequence> segments) {
        super(chars, segments);
    }

    public ThematicBreak(BlockContent blockContent) {
        super(blockContent);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
