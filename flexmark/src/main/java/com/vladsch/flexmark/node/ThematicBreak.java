package com.vladsch.flexmark.node;

import com.vladsch.flexmark.internal.BlockContent;
import com.vladsch.flexmark.internal.util.BasedSequence;

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
