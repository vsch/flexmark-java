package com.vladsch.flexmark.ext.footnotes;

import com.vladsch.flexmark.ast.DelimitedNode;
import com.vladsch.flexmark.ext.footnotes.internal.FootnoteRepository;
import com.vladsch.flexmark.util.ast.DoNotDecorate;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.ReferencingNode;
import com.vladsch.flexmark.util.sequence.BasedSequence;

/**
 * A Footnote referencing node
 */
public class Footnote extends Node implements DelimitedNode, DoNotDecorate, ReferencingNode<FootnoteRepository, FootnoteBlock> {
    protected BasedSequence openingMarker = BasedSequence.NULL;
    protected BasedSequence text = BasedSequence.NULL;
    protected BasedSequence closingMarker = BasedSequence.NULL;
    protected FootnoteBlock footnoteBlock;

    public int getReferenceOrdinal() {
        return referenceOrdinal;
    }

    public void setReferenceOrdinal(final int referenceOrdinal) {
        this.referenceOrdinal = referenceOrdinal;
    }

    protected int referenceOrdinal;

    @Override
    public BasedSequence getReference() {
        return text;
    }

    @Override
    public FootnoteBlock getReferenceNode(final Document document) {
        if (footnoteBlock != null || text.isEmpty()) return footnoteBlock;
        footnoteBlock = getFootnoteBlock(document.get(FootnoteExtension.FOOTNOTES));
        return footnoteBlock;
    }

    @Override
    public FootnoteBlock getReferenceNode(final FootnoteRepository repository) {
        if (footnoteBlock != null || text.isEmpty()) return footnoteBlock;
        footnoteBlock = getFootnoteBlock(repository);
        return footnoteBlock;
    }

    public boolean isDefined() {
        return footnoteBlock != null;
    }

    public FootnoteBlock getFootnoteBlock(FootnoteRepository footnoteRepository) {
        return text.isEmpty() ? null : footnoteRepository.get(text.toString());
    }

    public FootnoteBlock getFootnoteBlock() {
        return footnoteBlock;
    }

    public void setFootnoteBlock(FootnoteBlock footnoteBlock) {
        this.footnoteBlock = footnoteBlock;
    }

    @Override
    public BasedSequence[] getSegments() {
        return new BasedSequence[] { openingMarker, text, closingMarker };
    }

    @Override
    public void getAstExtra(StringBuilder out) {
        out.append(" ordinal: ").append(footnoteBlock != null ? footnoteBlock.getFootnoteOrdinal() : 0).append(" ");
        delimitedSegmentSpanChars(out, openingMarker, text, closingMarker, "text");
    }

    public Footnote() {
    }

    public Footnote(BasedSequence chars) {
        super(chars);
    }

    public Footnote(BasedSequence openingMarker, BasedSequence text, BasedSequence closingMarker) {
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
