package com.vladsch.flexmark.ext.enumerated.reference;

import com.vladsch.flexmark.ast.DelimitedNode;
import com.vladsch.flexmark.util.ast.DoNotDecorate;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.ReferencingNode;
import com.vladsch.flexmark.util.sequence.BasedSequence;

/**
 * A EnumeratedReference node
 */
public class EnumeratedReferenceBase extends Node implements DelimitedNode, DoNotDecorate, ReferencingNode<EnumeratedReferenceRepository, EnumeratedReferenceBlock> {
    protected BasedSequence openingMarker = BasedSequence.NULL;
    protected BasedSequence text = BasedSequence.NULL;
    protected BasedSequence closingMarker = BasedSequence.NULL;
    protected EnumeratedReferenceBlock enumeratedReferenceBlock;

    @Override
    public BasedSequence getReference() {
        return text;
    }

    @Override
    public EnumeratedReferenceBlock getReferenceNode(final Document document) {
        return enumeratedReferenceBlock;
    }

    @Override
    public EnumeratedReferenceBlock getReferenceNode(final EnumeratedReferenceRepository repository) {
        if (enumeratedReferenceBlock != null || text.isEmpty()) return enumeratedReferenceBlock;
        enumeratedReferenceBlock = getEnumeratedReferenceBlock(repository);
        return enumeratedReferenceBlock;
    }

    public boolean isDefined() {
        return enumeratedReferenceBlock != null;
    }

    public EnumeratedReferenceBlock getEnumeratedReferenceBlock(EnumeratedReferenceRepository enumeratedReferenceRepository) {
        return text.isEmpty() ? null : enumeratedReferenceRepository.get(EnumeratedReferenceRepository.getType(text.toString()));
    }

    public EnumeratedReferenceBlock getEnumeratedReferenceBlock() {
        return enumeratedReferenceBlock;
    }

    public void setEnumeratedReferenceBlock(EnumeratedReferenceBlock enumeratedReferenceBlock) {
        this.enumeratedReferenceBlock = enumeratedReferenceBlock;
    }

    @Override
    public BasedSequence[] getSegments() {
        return new BasedSequence[] { openingMarker, text, closingMarker };
    }

    @Override
    public void getAstExtra(StringBuilder out) {
        delimitedSegmentSpanChars(out, openingMarker, text, closingMarker, "text");
    }

    public EnumeratedReferenceBase() {
    }

    public EnumeratedReferenceBase(BasedSequence chars) {
        super(chars);
    }

    public EnumeratedReferenceBase(BasedSequence openingMarker, BasedSequence text, BasedSequence closingMarker) {
        super(openingMarker.baseSubSequence(openingMarker.getStartOffset(), closingMarker.getEndOffset()));
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
}
