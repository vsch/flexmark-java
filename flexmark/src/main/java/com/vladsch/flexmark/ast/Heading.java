package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.ast.BlockContent;
import com.vladsch.flexmark.util.ast.TextCollectingVisitor;
import com.vladsch.flexmark.util.ast.TextContainer;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Heading extends Block implements AnchorRefTarget {
    protected int level;
    protected BasedSequence openingMarker = BasedSequence.NULL;
    protected BasedSequence text = BasedSequence.NULL;
    protected BasedSequence closingMarker = BasedSequence.NULL;
    protected String anchorRefId = "";
    protected boolean explicitAnchorRefId = false;

    @Override
    public void getAstExtra(@NotNull StringBuilder out) {
        delimitedSegmentSpanChars(out, openingMarker, text, closingMarker, "text");
    }

    @NotNull
    @Override
    public BasedSequence[] getSegments() {
        return new BasedSequence[] { openingMarker, text, closingMarker };
    }

    @Override
    public String getAnchorRefText() {
        boolean trimLeadingSpaces = HtmlRenderer.HEADER_ID_REF_TEXT_TRIM_LEADING_SPACES.get(getDocument());
        boolean trimTrailingSpaces = HtmlRenderer.HEADER_ID_REF_TEXT_TRIM_TRAILING_SPACES.get(getDocument());

        return new TextCollectingVisitor().collectAndGetText(this, TextContainer.F_FOR_HEADING_ID + (trimLeadingSpaces ? 0 : TextContainer.F_NO_TRIM_REF_TEXT_START) + (trimTrailingSpaces ? 0 : TextContainer.F_NO_TRIM_REF_TEXT_END));
    }

    @Override
    public String getAnchorRefId() {
        return anchorRefId;
    }

    @Override
    public void setAnchorRefId(String anchorRefId) {
        this.anchorRefId = anchorRefId;
    }

    @Override
    public boolean isExplicitAnchorRefId() {
        return explicitAnchorRefId;
    }

    @Override
    public void setExplicitAnchorRefId(boolean explicitAnchorRefId) {
        this.explicitAnchorRefId = explicitAnchorRefId;
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
