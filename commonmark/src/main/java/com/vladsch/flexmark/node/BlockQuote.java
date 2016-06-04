package com.vladsch.flexmark.node;

import com.vladsch.flexmark.internal.BlockContent;
import com.vladsch.flexmark.internal.util.BasedSequence;
import com.vladsch.flexmark.internal.util.SubSequence;

import java.util.List;

public class BlockQuote extends Block {
    private BasedSequence marker = SubSequence.NULL;

    public BlockQuote() {
    }

    public BlockQuote(BasedSequence chars) {
        super(chars);
    }

    public BlockQuote(BasedSequence chars, List<BasedSequence> segments) {
        super(chars, segments);
    }

    public BlockQuote(BlockContent blockContent) {
        super(blockContent);
    }

    public BasedSequence getMarker() {
        return marker;
    }

    public void setMarker(BasedSequence marker) {
        this.marker = marker;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
