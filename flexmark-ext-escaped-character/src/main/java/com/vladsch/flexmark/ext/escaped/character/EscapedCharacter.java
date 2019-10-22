package com.vladsch.flexmark.ext.escaped.character;

import com.vladsch.flexmark.util.ast.DoNotDecorate;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

/**
 * A EscapedCharacter node
 */
public class EscapedCharacter extends Node implements DoNotDecorate {
    protected BasedSequence openingMarker = BasedSequence.NULL;
    protected BasedSequence text = BasedSequence.NULL;

    @NotNull
    @Override
    public BasedSequence[] getSegments() {
        //return EMPTY_SEGMENTS;
        return new BasedSequence[] { openingMarker, text };
    }

    @Override
    public void getAstExtra(@NotNull StringBuilder out) {
        delimitedSegmentSpanChars(out, openingMarker, text, BasedSequence.NULL, "text");
    }

    public EscapedCharacter() {
    }

    public EscapedCharacter(BasedSequence chars) {
        super(chars);
    }

    public EscapedCharacter(BasedSequence openingMarker, BasedSequence text) {
        super(openingMarker.baseSubSequence(openingMarker.getStartOffset(), text.getEndOffset()));
        this.openingMarker = openingMarker;
        this.text = text;
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
}
