
package com.vladsch.flexmark.ext.definition;

import com.vladsch.flexmark.ast.CustomBlock;
import com.vladsch.flexmark.util.sequence.BasedSequence;

/**
 * A Definition block node
 */
public class DefinitionItem extends CustomBlock {
    protected BasedSequence openingMarker = BasedSequence.NULL;
    protected BasedSequence text = BasedSequence.NULL;

    @Override
    public void getAstExtra(StringBuilder out) {
        segmentSpan(out, openingMarker, "open");
        segmentSpan(out, text, "text");
    }

    @Override
    public BasedSequence[] getSegments() {
        return new BasedSequence[] { openingMarker, text };
    }

    public DefinitionItem() {
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
