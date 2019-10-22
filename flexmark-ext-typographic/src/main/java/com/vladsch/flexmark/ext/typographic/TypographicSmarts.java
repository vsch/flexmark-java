package com.vladsch.flexmark.ext.typographic;

import com.vladsch.flexmark.util.ast.DoNotAttributeDecorate;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.TypographicText;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

/**
 * A TypographicSmarts node
 */
public class TypographicSmarts extends Node implements DoNotAttributeDecorate, TypographicText {
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
    public void getAstExtra(@NotNull StringBuilder out) {
        out.append(" typographic: ").append(typographicText).append(" ");
    }

    public String getTypographicText() {
        return typographicText;
    }

    public void setTypographicText(String typographicText) {
        this.typographicText = typographicText;
    }

    @NotNull
    @Override
    public BasedSequence[] getSegments() {
        return EMPTY_SEGMENTS;
    }

    @NotNull
    @Override
    protected String toStringAttributes() {
        return "text=" + getChars();
    }
}
