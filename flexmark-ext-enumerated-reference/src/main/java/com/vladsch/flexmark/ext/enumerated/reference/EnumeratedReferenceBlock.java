package com.vladsch.flexmark.ext.enumerated.reference;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.ext.enumerated.reference.internal.EnumeratedReferenceRepository;
import com.vladsch.flexmark.parser.ListOptions;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;

/**
 * A EnumeratedReference block node
 */
public class EnumeratedReferenceBlock extends Block implements ReferenceNode<EnumeratedReferenceRepository, EnumeratedReferenceBlock, EnumeratedReferenceText>, ParagraphItemContainer {
    protected BasedSequence openingMarker = BasedSequence.NULL;
    protected BasedSequence text = BasedSequence.NULL;
    protected BasedSequence closingMarker = BasedSequence.NULL;
    protected BasedSequence enumeratedReference = BasedSequence.NULL;

    @Override
    public int compareTo(final EnumeratedReferenceBlock other) {
        return getText().compareTo(other.getText());
    }

    @Override
    public EnumeratedReferenceText getReferencingNode(final Node node) {
        return node instanceof EnumeratedReferenceText ? (EnumeratedReferenceText) node : null;
    }

    @Override
    public void getAstExtra(StringBuilder out) {
        segmentSpan(out, openingMarker, "open");
        segmentSpan(out, text, "text");
        segmentSpan(out, closingMarker, "close");
        segmentSpan(out, enumeratedReference, "enumeratedReference");
    }

    @Override
    public BasedSequence[] getSegments() {
        return new BasedSequence[] { openingMarker, text, closingMarker, enumeratedReference };
    }

    public EnumeratedReferenceBlock() {
    }

    public EnumeratedReferenceBlock(BasedSequence chars) {
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

    public BasedSequence getEnumeratedReference() {
        return enumeratedReference;
    }

    public void setEnumeratedReference(BasedSequence enumeratedReference) {
        this.enumeratedReference = enumeratedReference;
    }

    @Override
    public boolean isItemParagraph(final Paragraph node) {
        return node == getFirstChild();
    }

    @Override
    public boolean isParagraphWrappingDisabled(final Paragraph node, final ListOptions listOptions, final DataHolder options) {
        return true;
    }
}
