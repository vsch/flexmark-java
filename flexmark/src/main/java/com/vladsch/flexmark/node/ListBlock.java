package com.vladsch.flexmark.node;

import com.vladsch.flexmark.internal.BlockContent;
import com.vladsch.flexmark.internal.util.sequence.BasedSequence;

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

    public boolean isLoose() {
        return !tight;
    }

    public void setTight(boolean tight) {
        this.tight = tight;
    }

    public void setLoose(boolean loose) {
        this.tight = !loose;
    }

    @Override
    public Node getLastBlankLineChild() {
        return getLastChild();
    }

    @Override
    public void getAstExtra(StringBuilder out) {
        super.getAstExtra(out);
        if (isTight()) out.append(" isTight");
        else out.append(" isLoose");
    }
}
