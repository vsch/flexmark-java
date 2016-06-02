package org.commonmark.node;

import org.commonmark.internal.BlockContent;
import org.commonmark.internal.util.BasedSequence;

import java.util.List;

public abstract class ListBlock extends Block {

    private boolean tight;

    public ListBlock() {
    }

    public ListBlock(BasedSequence chars) {
        super(chars);
    }

    public ListBlock(BasedSequence chars, List<BasedSequence> segments) {
        super(chars, segments);
    }

    public ListBlock(BlockContent blockContent) {
        super(blockContent);
    }

    public boolean isTight() {
        return tight;
    }

    public void setTight(boolean tight) {
        this.tight = tight;
    }
}
