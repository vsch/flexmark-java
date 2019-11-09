package com.vladsch.flexmark.ext.typographic;

import com.vladsch.flexmark.util.ast.DelimitedNode;
import com.vladsch.flexmark.util.ast.DoNotAttributeDecorate;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.TypographicText;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

/**
 * A TypographicQuotes node
 */
public class TypographicQuotes extends Node implements DelimitedNode, DoNotAttributeDecorate, TypographicText {
    protected BasedSequence openingMarker = BasedSequence.NULL;
    protected BasedSequence text = BasedSequence.NULL;
    protected BasedSequence closingMarker = BasedSequence.NULL;
    protected String typographicOpening;
    protected String typographicClosing;

    @NotNull
    @Override
    public BasedSequence[] getSegments() {
        //return EMPTY_SEGMENTS;
        return new BasedSequence[] { openingMarker, text, closingMarker };
    }

    @Override
    public void getAstExtra(@NotNull StringBuilder out) {
        if (openingMarker.isNotNull()) out.append(" typographicOpening: ").append(typographicOpening).append(" ");
        if (closingMarker.isNotNull()) out.append(" typographicClosing: ").append(typographicClosing).append(" ");
        delimitedSegmentSpanChars(out, openingMarker, text, closingMarker, "text");
    }

    public TypographicQuotes() {
    }

    public TypographicQuotes(BasedSequence chars) {
        super(chars);
    }

    public TypographicQuotes(BasedSequence openingMarker, BasedSequence text, BasedSequence closingMarker) {
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

    public String getTypographicOpening() {
        return typographicOpening;
    }

    public void setTypographicOpening(String typographicOpening) {
        this.typographicOpening = typographicOpening;
    }

    public String getTypographicClosing() {
        return typographicClosing;
    }

    public void setTypographicClosing(String typographicClosing) {
        this.typographicClosing = typographicClosing;
    }
}
