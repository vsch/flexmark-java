package com.vladsch.flexmark.node;

import com.vladsch.flexmark.internal.BlockContent;
import com.vladsch.flexmark.internal.util.TextCollectingVisitor;
import com.vladsch.flexmark.internal.util.sequence.BasedSequence;
import com.vladsch.flexmark.internal.util.sequence.SubSequence;

import java.util.List;

public class Heading extends Block implements AnchorRefTarget {
    protected int level;
    protected BasedSequence openingMarker = SubSequence.NULL;
    protected BasedSequence text = SubSequence.NULL;
    protected BasedSequence closingMarker = SubSequence.NULL;
    protected String anchorRefId = "";

    @Override
    public void getAstExtra(StringBuilder out) {
        delimitedSegmentSpanChars(out, openingMarker, text, closingMarker, "text");
    }

    @Override
    public BasedSequence[] getSegments() {
        return new BasedSequence[] { openingMarker, text, closingMarker };
    }

    @Override
    public String getAnchorRefText() {
        TextCollectingVisitor textCollectingVisitor = new TextCollectingVisitor();
        textCollectingVisitor.visit(this);
        return textCollectingVisitor.getText();
    }

    @Override
    public String getAnchorRefId() {
        return anchorRefId;
    }

    @Override
    public void setAnchorRefId(String anchorRefId) {
        this.anchorRefId = anchorRefId;
    }

    public Heading() {
    }

    public Heading(BasedSequence chars) {
        super(chars);
    }

    public Heading(BasedSequence chars, List<BasedSequence> segments) {
        super(chars, segments);
    }

    public Heading(BlockContent blockContent) {
        super(blockContent);
    }

    public boolean isAtxHeading() {
        return openingMarker != SubSequence.NULL;
    }

    public boolean isSetextHeading() {
        return openingMarker == SubSequence.NULL && closingMarker != SubSequence.NULL;
    }

    public BasedSequence getOpeningMarker() {
        return openingMarker;
    }

    public void setOpeningMarker(BasedSequence openingMarker) {
        this.openingMarker = openingMarker == null ? SubSequence.NULL : openingMarker;
    }

    public BasedSequence getText() {
        return text;
    }

    public void setText(BasedSequence text) {
        this.text = text == null ? SubSequence.NULL : text;
    }

    public BasedSequence getClosingMarker() {
        return closingMarker;
    }

    public void setClosingMarker(BasedSequence closingMarker) {
        this.closingMarker = closingMarker == null ? SubSequence.NULL : closingMarker;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
