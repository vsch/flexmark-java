package com.vladsch.flexmark.ext.abbreviation;

import com.vladsch.flexmark.internal.util.BasedSequence;
import com.vladsch.flexmark.internal.util.SubSequence;
import com.vladsch.flexmark.node.CustomBlock;
import com.vladsch.flexmark.node.Visitor;

/**
 * A strikethrough node containing text and other inline nodes nodes as children.
 */
public class AbbreviationBlock extends CustomBlock {
    protected BasedSequence openingMarker = SubSequence.EMPTY;
    protected BasedSequence text = SubSequence.EMPTY;
    protected BasedSequence closingMarker = SubSequence.EMPTY;
    protected BasedSequence abbreviation = SubSequence.EMPTY;

    @Override
    public String getAstExtra() {
        return segmentSpan(openingMarker, "open")
                + segmentSpan(text, "text")
                + segmentSpan(closingMarker, "close")
                + segmentSpan(abbreviation, "abbreviation");
    }

    @Override
    public BasedSequence[] getSegments() {
        return new BasedSequence[] { openingMarker, text, closingMarker, abbreviation };
    }

    public AbbreviationBlock() {
    }

    public AbbreviationBlock(BasedSequence chars) {
        super(chars);
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

    public BasedSequence getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(BasedSequence abbreviation) {
        this.abbreviation = abbreviation;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
