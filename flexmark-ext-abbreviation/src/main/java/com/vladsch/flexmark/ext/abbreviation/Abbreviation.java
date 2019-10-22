package com.vladsch.flexmark.ext.abbreviation;

import com.vladsch.flexmark.ext.abbreviation.internal.AbbreviationRepository;
import com.vladsch.flexmark.util.ast.*;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

/**
 * A node containing the abbreviated text that will be rendered as an abbr tag or a link with title attribute
 */
public class Abbreviation extends Node implements DoNotDecorate, DoNotLinkDecorate, ReferencingNode<AbbreviationRepository, AbbreviationBlock> {
    protected final BasedSequence abbreviation;

    public Abbreviation(BasedSequence chars, BasedSequence abbreviation) {
        super(chars);
        this.abbreviation = abbreviation;
    }

    public BasedSequence getAbbreviation() {
        return abbreviation;
    }

    @NotNull
    @Override
    public BasedSequence[] getSegments() {
        return EMPTY_SEGMENTS;
    }

    @Override
    public void getAstExtra(@NotNull StringBuilder out) {
        astExtraChars(out);
    }

    @NotNull
    @Override
    protected String toStringAttributes() {
        return "text=" + getChars();
    }

    @Override
    public boolean isDefined() {
        return true;
    }

    @NotNull
    @Override
    public BasedSequence getReference() {
        return abbreviation;
    }

    @Override
    public AbbreviationBlock getReferenceNode(Document document) {
        return getReferenceNode(AbbreviationExtension.ABBREVIATIONS.get(document));
    }

    @Override
    public AbbreviationBlock getReferenceNode(AbbreviationRepository repository) {
        return repository.get(getChars().toString());
    }
}
