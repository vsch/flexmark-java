package com.vladsch.flexmark.ext.xwiki.macros;

import com.vladsch.flexmark.ast.CustomNode;
import com.vladsch.flexmark.ast.DoNotDecorate;
import com.vladsch.flexmark.util.sequence.BasedSequence;

/**
 * A macros node
 */
public class Macro extends CustomNode {
    protected BasedSequence openingMarker = BasedSequence.NULL;
    protected BasedSequence name = BasedSequence.NULL;
    protected BasedSequence attributeText = BasedSequence.NULL;
    protected BasedSequence closingMarker = BasedSequence.NULL;

    @Override
    public BasedSequence[] getSegments() {
        //return EMPTY_SEGMENTS;
        return new BasedSequence[] { openingMarker, name, attributeText, closingMarker };
    }

    @Override
    public void getAstExtra(StringBuilder out) {
        segmentSpanChars(out, openingMarker,"open");
        segmentSpanChars(out, name, "name");
        segmentSpanChars(out, attributeText, "attributes");
        segmentSpanChars(out, closingMarker, "close");
    }

    public Macro() {
    }

    public Macro(BasedSequence chars) {
        super(chars);
    }

    public Macro(BasedSequence openingMarker, BasedSequence name, BasedSequence closingMarker) {
        super(openingMarker.baseSubSequence(openingMarker.getStartOffset(), closingMarker.getEndOffset()));
        this.openingMarker = openingMarker;
        this.name = name;
        this.closingMarker = closingMarker;
    }

    public BasedSequence getOpeningMarker() {
        return openingMarker;
    }

    public void setOpeningMarker(BasedSequence openingMarker) {
        this.openingMarker = openingMarker;
    }

    public BasedSequence getName() {
        return name;
    }

    public void setName(BasedSequence name) {
        this.name = name;
    }

    public BasedSequence getClosingMarker() {
        return closingMarker;
    }

    public void setClosingMarker(BasedSequence closingMarker) {
        this.closingMarker = closingMarker;
    }

    public BasedSequence getAttributeText() {
        return attributeText;
    }

    public void setAttributeText(final BasedSequence attributeText) {
        this.attributeText = attributeText;
    }
}
