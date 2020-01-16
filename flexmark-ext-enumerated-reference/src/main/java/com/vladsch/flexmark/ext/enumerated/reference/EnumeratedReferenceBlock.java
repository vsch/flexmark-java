package com.vladsch.flexmark.ext.enumerated.reference;

import com.vladsch.flexmark.ast.Paragraph;
import com.vladsch.flexmark.ast.ParagraphItemContainer;
import com.vladsch.flexmark.parser.ListOptions;
import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.ReferenceNode;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.SequenceUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A EnumeratedReference block node
 */
public class EnumeratedReferenceBlock extends Block implements ReferenceNode<EnumeratedReferenceRepository, EnumeratedReferenceBlock, EnumeratedReferenceText>, ParagraphItemContainer {
    protected BasedSequence openingMarker = BasedSequence.NULL;
    protected BasedSequence text = BasedSequence.NULL;
    protected BasedSequence closingMarker = BasedSequence.NULL;
    protected BasedSequence enumeratedReference = BasedSequence.NULL;

    @Override
    public int compareTo(EnumeratedReferenceBlock other) {
        return SequenceUtils.compare(text, other.text, true);
    }

    @Nullable
    @Override
    public EnumeratedReferenceText getReferencingNode(@NotNull Node node) {
        return node instanceof EnumeratedReferenceText ? (EnumeratedReferenceText) node : null;
    }

    @Override
    public void getAstExtra(@NotNull StringBuilder out) {
        segmentSpan(out, openingMarker, "open");
        segmentSpan(out, text, "text");
        segmentSpan(out, closingMarker, "close");
        segmentSpan(out, enumeratedReference, "enumeratedReference");
    }

    @NotNull
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
    public boolean isItemParagraph(Paragraph node) {
        return node == getFirstChild();
    }

    @Override
    public boolean isParagraphWrappingDisabled(Paragraph node, ListOptions listOptions, DataHolder options) {
        return true;
    }

    @Override
    public boolean isParagraphInTightListItem(Paragraph node) {
        return true;
    }
}
