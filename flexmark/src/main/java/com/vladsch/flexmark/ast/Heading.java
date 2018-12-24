package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.ast.util.TextCollectingVisitor;
import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.ast.BlockContent;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.List;

public class Heading extends Block implements AnchorRefTarget {
    protected int level;
    protected BasedSequence openingMarker = BasedSequence.NULL;
    protected BasedSequence text = BasedSequence.NULL;
    protected BasedSequence closingMarker = BasedSequence.NULL;
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
        return new TextCollectingVisitor().collectAndGetText(this).trim();
    }

    @Override
    public BasedSequence[] getAnchorRefSegments() {
        return new TextCollectingVisitor().collectAndGetSegments(this);
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
        return openingMarker != BasedSequence.NULL;
    }

    public boolean isSetextHeading() {
        return openingMarker == BasedSequence.NULL && closingMarker != BasedSequence.NULL;
    }

    public BasedSequence getOpeningMarker() {
        return openingMarker;
    }

    public void setOpeningMarker(BasedSequence openingMarker) {
        this.openingMarker = openingMarker == null ? BasedSequence.NULL : openingMarker;
    }

    public BasedSequence getText() {
        return text;
    }

    public void setText(BasedSequence text) {
        this.text = text == null ? BasedSequence.NULL : text;
    }

    public BasedSequence getClosingMarker() {
        return closingMarker;
    }

    public void setClosingMarker(BasedSequence closingMarker) {
        this.closingMarker = closingMarker == null ? BasedSequence.NULL : closingMarker;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
