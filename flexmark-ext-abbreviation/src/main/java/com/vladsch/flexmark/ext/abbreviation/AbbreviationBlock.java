package com.vladsch.flexmark.ext.abbreviation;

import com.vladsch.flexmark.ast.CustomBlock;
import com.vladsch.flexmark.util.sequence.BasedSequence;

/**
 * A block node that contains the abbreviation definition
 */
public class AbbreviationBlock extends CustomBlock {
    protected BasedSequence openingMarker = BasedSequence.NULL;
    protected BasedSequence text = BasedSequence.NULL;
    protected BasedSequence closingMarker = BasedSequence.NULL;
    protected BasedSequence abbreviation = BasedSequence.NULL;

    @Override
    public void getAstExtra(StringBuilder out) {
        segmentSpan(out, openingMarker, "open");
        segmentSpan(out, text, "text");
        segmentSpan(out, closingMarker, "close");
        segmentSpan(out, abbreviation, "abbreviation");
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
}
