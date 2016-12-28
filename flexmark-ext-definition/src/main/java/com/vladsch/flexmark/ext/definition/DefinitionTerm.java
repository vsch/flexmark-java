
package com.vladsch.flexmark.ext.definition;

import com.vladsch.flexmark.ast.CustomBlock;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;

/**
 * A Definition block node
 */
public class DefinitionTerm extends CustomBlock {
    protected BasedSequence text = BasedSequence.NULL;
    protected BasedSequence closingMarker = BasedSequence.NULL;

    @Override
    public void getAstExtra(StringBuilder out) {
        segmentSpan(out, text, "text");
        segmentSpan(out, closingMarker, "close");
    }

    @Override
    public BasedSequence[] getSegments() {
        return new BasedSequence[] { text, closingMarker };
    }

    public DefinitionTerm() {
    }

    public DefinitionTerm(BasedSequence chars) {
        super(chars);
    }

    public DefinitionTerm(Node node) {
        super();
        appendChild(node);
        this.setCharsFromContent();
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
}
