package com.vladsch.flexmark.ext.abbreviation;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.ext.abbreviation.internal.AbbreviationRepository;
import com.vladsch.flexmark.util.sequence.BasedSequence;

/**
 * A node containing the abbreviated text that will be rendered as an abbr tag or a link with title attribute
 */
public class Abbreviation extends Node implements DoNotLinkDecorate, ReferencingNode<AbbreviationRepository, AbbreviationBlock> {
    protected final BasedSequence abbreviation;

    public Abbreviation(BasedSequence chars, BasedSequence abbreviation) {
        super(chars);
        this.abbreviation = abbreviation;
    }

    public BasedSequence getAbbreviation() {
        return abbreviation;
    }

    @Override
    public BasedSequence[] getSegments() {
        return EMPTY_SEGMENTS;
    }

    @Override
    public void getAstExtra(StringBuilder out) {
        astExtraChars(out);
    }

    @Override
    protected String toStringAttributes() {
        return "text=" + getChars();
    }

    @Override
    public boolean isDefined() {
        return true;
    }

    @Override
    public BasedSequence getReference() {
        return abbreviation;
    }

    @Override
    public AbbreviationBlock getReferenceNode(final Document document) {
        return getReferenceNode(AbbreviationExtension.ABBREVIATIONS.getFrom(document));
    }

    @Override
    public AbbreviationBlock getReferenceNode(final AbbreviationRepository repository) {
        return repository.get(getChars().toString());
    }
}
