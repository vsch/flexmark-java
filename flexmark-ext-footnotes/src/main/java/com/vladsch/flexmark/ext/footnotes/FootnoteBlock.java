package com.vladsch.flexmark.ext.footnotes;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.ext.footnotes.internal.FootnoteRepository;
import com.vladsch.flexmark.parser.ListOptions;
import com.vladsch.flexmark.util.ast.CustomBlock;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.ReferenceNode;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;

/**
 * A Footnote definition node containing text and other inline nodes nodes as children.
 */
public class FootnoteBlock extends CustomBlock implements ReferenceNode<FootnoteRepository, FootnoteBlock, Footnote>, ParagraphItemContainer {
    protected BasedSequence openingMarker = BasedSequence.NULL;
    protected BasedSequence text = BasedSequence.NULL;
    protected BasedSequence closingMarker = BasedSequence.NULL;
    protected BasedSequence footnote = BasedSequence.NULL;
    private int footnoteOrdinal = 0;
    private int firstReferenceOffset = Integer.MAX_VALUE;
    private int footnoteReferences = 0;

    @Override
    public int compareTo(final FootnoteBlock other) {
        return text.compareTo(other.text);
    }

    public int getFootnoteReferences() {
        return footnoteReferences;
    }

    public void setFootnoteReferences(final int footnoteReferences) {
        this.footnoteReferences = footnoteReferences;
    }

    @Override
    public Footnote getReferencingNode(final Node node) {
        return node instanceof Footnote ? (Footnote) node : null;
    }

    public int getFirstReferenceOffset() {
        return firstReferenceOffset;
    }

    public void setFirstReferenceOffset(int firstReferenceOffset) {
        this.firstReferenceOffset = firstReferenceOffset;
    }

    public void addFirstReferenceOffset(int firstReferenceOffset) {
        if (this.firstReferenceOffset < firstReferenceOffset) this.firstReferenceOffset = firstReferenceOffset;
    }

    public boolean isReferenced() {
        return this.firstReferenceOffset < Integer.MAX_VALUE;
    }

    public int getFootnoteOrdinal() {
        return footnoteOrdinal;
    }

    public void setFootnoteOrdinal(int footnoteOrdinal) {
        this.footnoteOrdinal = footnoteOrdinal;
    }

    @Override
    public void getAstExtra(StringBuilder out) {
        out.append(" ordinal: " + footnoteOrdinal + " ");
        segmentSpan(out, openingMarker, "open");
        segmentSpan(out, text, "text");
        segmentSpan(out, closingMarker, "close");
        segmentSpan(out, footnote, "footnote");
    }

    @Override
    public BasedSequence[] getSegments() {
        return new BasedSequence[] { openingMarker, text, closingMarker, footnote };
    }

    public FootnoteBlock() {
    }

    public FootnoteBlock(BasedSequence chars) {
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

    public BasedSequence getFootnote() {
        return footnote;
    }

    public void setFootnote(BasedSequence footnote) {
        this.footnote = footnote;
    }

    @Override
    public boolean isItemParagraph(final Paragraph node) {
        return node == getFirstChild();
    }

    @Override
    public boolean isParagraphWrappingDisabled(final Paragraph node, final ListOptions listOptions, final DataHolder options) {
        return false;
    }
}
