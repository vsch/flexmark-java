package com.vladsch.flexmark.ext.gfm.strikethrough;

import com.vladsch.flexmark.internal.util.BasedSequence;
import com.vladsch.flexmark.internal.util.SubSequence;
import com.vladsch.flexmark.node.CustomNode;
import com.vladsch.flexmark.node.DelimitedNode;
import com.vladsch.flexmark.node.Visitor;

/**
 * A strikethrough node containing text and other inline nodes nodes as children.
 */
public class Strikethrough extends CustomNode implements DelimitedNode {
    protected BasedSequence openingMarker = SubSequence.EMPTY;
    protected BasedSequence content = SubSequence.EMPTY;
    protected BasedSequence closingMarker = SubSequence.EMPTY;

    public Strikethrough() {
    }

    public Strikethrough(BasedSequence chars) {
        super(chars);
    }

    public Strikethrough(BasedSequence openingMarker, BasedSequence content, BasedSequence closingMarker) {
        super(new SubSequence(openingMarker.getBase(), openingMarker.getStartOffset(), closingMarker.getEndOffset()));
        this.openingMarker = openingMarker;
        this.content = content;
        this.closingMarker = closingMarker;
    }

    public BasedSequence getOpeningMarker() {
        return openingMarker;
    }

    public void setOpeningMarker(BasedSequence openingMarker) {
        this.openingMarker = openingMarker;
    }

    public BasedSequence getContent() {
        return content;
    }

    public void setContent(BasedSequence content) {
        this.content = content;
    }

    public BasedSequence getClosingMarker() {
        return closingMarker;
    }

    public void setClosingMarker(BasedSequence closingMarker) {
        this.closingMarker = closingMarker;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
