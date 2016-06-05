package com.vladsch.flexmark.node;

import com.vladsch.flexmark.internal.BlockContent;
import com.vladsch.flexmark.internal.util.BasedSequence;

import java.util.List;

public class Paragraph extends Block {
    public Paragraph() {
    }

    public Paragraph(BasedSequence chars) {
        super(chars);
    }

    public Paragraph(BasedSequence chars, List<BasedSequence> segments) {
        super(chars, segments);
    }

    public Paragraph(BlockContent blockContent) {
        super(blockContent);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
