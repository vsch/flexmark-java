package com.vladsch.flexmark.ext.xwiki.macros;

import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

/**
 * A macros node
 */
public class MacroClose extends Node {
    protected BasedSequence openingMarker = BasedSequence.NULL;
    protected BasedSequence name = BasedSequence.NULL;
    protected BasedSequence closingMarker = BasedSequence.NULL;

    @NotNull
    @Override
    public BasedSequence[] getSegments() {
        //return EMPTY_SEGMENTS;
        return new BasedSequence[] { openingMarker, name, closingMarker };
    }

    @Override
    public void getAstExtra(@NotNull StringBuilder out) {
        delimitedSegmentSpanChars(out, openingMarker, name, closingMarker, "name");
    }

    public MacroClose() {
    }

    public MacroClose(BasedSequence chars) {
        super(chars);
    }

    public MacroClose(BasedSequence openingMarker, BasedSequence name, BasedSequence closingMarker) {
        super(openingMarker.baseSubSequence(openingMarker.getStartOffset(), closingMarker.getEndOffset()));
        this.openingMarker = openingMarker;
        this.name = name;
        this.closingMarker = closingMarker;
    }

    public BasedSequence getOpeningMarker() {
        return openingMarker;
    }

    public void setOpeningMarker(BasedSequence openingMarker) {
        this.openingMarker = openingMarker;
    }

    public BasedSequence getName() {
        return name;
    }

    public void setName(BasedSequence name) {
        this.name = name;
    }

    public BasedSequence getClosingMarker() {
        return closingMarker;
    }

    public void setClosingMarker(BasedSequence closingMarker) {
        this.closingMarker = closingMarker;
    }
}
