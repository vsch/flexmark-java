package com.vladsch.flexmark.ext.abbreviation;

import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.ReferenceNode;
import com.vladsch.flexmark.ext.abbreviation.internal.AbbreviationRepository;
import com.vladsch.flexmark.util.sequence.BasedSequence;

/**
 * A block node that contains the abbreviation definition
 */
public class AbbreviationBlock extends Block implements ReferenceNode<AbbreviationRepository, AbbreviationBlock, Abbreviation> {
    protected BasedSequence openingMarker = BasedSequence.NULL;
    protected BasedSequence text = BasedSequence.NULL;
    protected BasedSequence closingMarker = BasedSequence.NULL;
    protected BasedSequence abbreviation = BasedSequence.NULL;

    @Override
    public Abbreviation getReferencingNode(final Node node) {
        return node instanceof Abbreviation ? (Abbreviation) node : null;
    }

    @Override
    public int compareTo(final AbbreviationBlock o) {
        return text.compareTo(o.getText());
    }

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
