package com.vladsch.flexmark.ext.footnotes;

import com.vladsch.flexmark.internal.util.BasedSequence;
import com.vladsch.flexmark.internal.util.SubSequence;
import com.vladsch.flexmark.node.CustomBlock;
import com.vladsch.flexmark.node.Visitor;

/**
 * A strikethrough node containing text and other inline nodes nodes as children.
 */
public class FootnoteBlock extends CustomBlock {
    protected BasedSequence openingMarker = SubSequence.EMPTY;
    protected BasedSequence text = SubSequence.EMPTY;
    protected BasedSequence closingMarker = SubSequence.EMPTY;
    protected BasedSequence footnote = SubSequence.EMPTY;
    private int footnoteOrdinal = 0;

    public int getFootnoteOrdinal() {
        return footnoteOrdinal;
    }

    public void setFootnoteOrdinal(int footnoteOrdinal) {
        this.footnoteOrdinal = footnoteOrdinal;
    }

    @Override
    public String getAstExtra() {
        return segmentSpan(openingMarker, "open")
                + segmentSpan(text, "text")
                + segmentSpan(closingMarker, "close")
                + segmentSpan(footnote, "footnote");
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
