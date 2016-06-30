package com.vladsch.flexmark.ext.emoji;

import com.vladsch.flexmark.internal.util.sequence.BasedSequence;
import com.vladsch.flexmark.internal.util.sequence.SubSequence;
import com.vladsch.flexmark.node.CustomNode;
import com.vladsch.flexmark.node.DelimitedNode;
import com.vladsch.flexmark.node.Visitor;

/**
 * A strikethrough node containing text and other inline nodes nodes as children.
 */
public class Emoji extends CustomNode implements DelimitedNode {
    protected BasedSequence openingMarker = SubSequence.NULL;
    protected BasedSequence text = SubSequence.NULL;
    protected BasedSequence closingMarker = SubSequence.NULL;

    @Override
    public BasedSequence[] getSegments() {
        return new BasedSequence[] { openingMarker, text, closingMarker };
    }

    @Override
    public void getAstExtra(StringBuilder out) {
        delimitedSegmentSpanChars(out, openingMarker, text, closingMarker, "text");
    }

    public Emoji() {
    }

    public Emoji(BasedSequence chars) {
        super(chars);
    }

    public Emoji(BasedSequence openingMarker, BasedSequence text, BasedSequence closingMarker) {
        super(new SubSequence(openingMarker.getBase(), openingMarker.getStartOffset(), closingMarker.getEndOffset()));
        this.openingMarker = openingMarker;
        this.text = text;
        this.closingMarker = closingMarker;
    }

    public BasedSequence getOpeningMarker() {
        return openingMarker;
    }

    public void setOpeningMarker(BasedSequence openingMarker) {
        this.openingMarker = openingMarker;
    }

    public BasedSequence getText() {
        return text;
    }

    public void setText(BasedSequence text) {
        this.text = text;
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
