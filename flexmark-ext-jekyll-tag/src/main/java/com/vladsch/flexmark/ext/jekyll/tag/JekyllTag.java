package com.vladsch.flexmark.ext.jekyll.tag;

import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

/**
 * A JekyllTag node
 */
public class JekyllTag extends Block {
    protected BasedSequence openingMarker = BasedSequence.NULL;
    protected BasedSequence tag = BasedSequence.NULL;
    protected BasedSequence parameters = BasedSequence.NULL;
    protected BasedSequence closingMarker = BasedSequence.NULL;

    @NotNull
    @Override
    public BasedSequence[] getSegments() {
        //return EMPTY_SEGMENTS;
        return new BasedSequence[] { openingMarker, tag, parameters, closingMarker };
    }

    @Override
    public void getAstExtra(@NotNull StringBuilder out) {
        segmentSpanChars(out, openingMarker, "open");
        segmentSpanChars(out, tag, "tag");
        segmentSpanChars(out, parameters, "parameters");
        segmentSpanChars(out, closingMarker, "close");
    }

    public JekyllTag() {
    }

    public JekyllTag(BasedSequence chars) {
        super(chars);
    }

    public JekyllTag(BasedSequence openingMarker, BasedSequence tag, BasedSequence parameters, BasedSequence closingMarker) {
        super(openingMarker.baseSubSequence(openingMarker.getStartOffset(), closingMarker.getEndOffset()));
        this.openingMarker = openingMarker;
        this.tag = tag;
        this.parameters = parameters;
        this.closingMarker = closingMarker;
    }

    public BasedSequence getOpeningMarker() {
        return openingMarker;
    }

    public void setOpeningMarker(BasedSequence openingMarker) {
        this.openingMarker = openingMarker;
    }

    public BasedSequence getTag() {
        return tag;
    }

    public void setTag(BasedSequence text) {
        this.tag = text;
    }

    public BasedSequence getParameters() {
        return parameters;
    }

    public void setParameters(BasedSequence parameters) {
        this.parameters = parameters;
    }

    public BasedSequence getClosingMarker() {
        return closingMarker;
    }

    public void setClosingMarker(BasedSequence closingMarker) {
        this.closingMarker = closingMarker;
    }
}
