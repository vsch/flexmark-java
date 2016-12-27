package com.vladsch.flexmark.ext.typographic;

import com.vladsch.flexmark.ast.DoNotDecorate;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;

/**
 * A TypographicSmarts node
 */
public class TypographicSmarts extends Node implements DoNotDecorate {
    private String typographicText;

    public TypographicSmarts() {
    }

    public TypographicSmarts(BasedSequence chars) {
        super(chars);
    }

    public TypographicSmarts(String typographicText) {
        this.typographicText = typographicText;
    }

    public TypographicSmarts(BasedSequence chars, String typographicText) {
        super(chars);
        this.typographicText = typographicText;
    }

    @Override
    public void getAstExtra(StringBuilder out) {
        out.append(" typographic: ").append(typographicText).append(" ");
    }

    public String getTypographicText() {
        return typographicText;
    }

    public void setTypographicText(String typographicText) {
        this.typographicText = typographicText;
    }

    @Override
    public BasedSequence[] getSegments() {
        return EMPTY_SEGMENTS;
    }

    @Override
    protected String toStringAttributes() {
        return "text=" + getChars();
    }
}
