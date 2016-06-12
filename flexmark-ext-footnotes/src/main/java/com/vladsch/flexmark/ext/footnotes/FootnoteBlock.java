package com.vladsch.flexmark.ext.footnotes;

import com.vladsch.flexmark.internal.util.BasedSequence;
import com.vladsch.flexmark.internal.util.SubSequence;
import com.vladsch.flexmark.node.CustomBlock;
import com.vladsch.flexmark.node.Visitor;

/**
 * A strikethrough node containing text and other inline nodes nodes as children.
 */
public class FootnoteBlock extends CustomBlock {
    protected BasedSequence openingMarker = SubSequence.NULL;
    protected BasedSequence text = SubSequence.NULL;
    protected BasedSequence closingMarker = SubSequence.NULL;
    protected BasedSequence footnote = SubSequence.NULL;
    private int footnoteOrdinal = 0;

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
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
