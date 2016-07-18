
package com.vladsch.flexmark.ext.jekyll.front.matter;

import com.vladsch.flexmark.internal.util.sequence.BasedSequence;
import com.vladsch.flexmark.internal.util.sequence.SubSequence;
import com.vladsch.flexmark.node.CustomBlock;
import com.vladsch.flexmark.node.Node;

/**
 * A JekyllFrontMatter block node
 */
public class JekyllFrontMatterBlock extends CustomBlock {
    protected BasedSequence openingMarker = SubSequence.NULL;
    protected BasedSequence closingMarker = SubSequence.NULL;

    @Override
    public void getAstExtra(StringBuilder out) {
        segmentSpan(out, openingMarker, "open");
        segmentSpan(out, getContent(), "content");
        segmentSpan(out, closingMarker, "close");
    }

    @Override
    public BasedSequence[] getSegments() {
        return new BasedSequence[] { openingMarker, closingMarker };
    }

    public JekyllFrontMatterBlock() {
    }

    public JekyllFrontMatterBlock(BasedSequence chars) {
        super(chars);
    }

    public JekyllFrontMatterBlock(Node node) {
        super();
        appendChild(node);
        this.setCharsFromContent();
    }

    public BasedSequence getOpeningMarker() {
        return openingMarker;
    }

    public void setOpeningMarker(BasedSequence openingMarker) {
        this.openingMarker = openingMarker;
    }

    public BasedSequence getContent() {
        return getContentChars();
    }

    public BasedSequence getClosingMarker() {
        return closingMarker;
    }

    public void setClosingMarker(BasedSequence closingMarker) {
        this.closingMarker = closingMarker;
    }

    public void accept(JekyllFrontMatterVisitor visitor) {
        visitor.visit(this);
    }
}
