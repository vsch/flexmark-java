package com.vladsch.flexmark.ext.jekyll.front.matter;

import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

/**
 * A JekyllFrontMatter block node
 */
public class JekyllFrontMatterBlock extends Block {
    protected BasedSequence openingMarker = BasedSequence.NULL;
    protected BasedSequence closingMarker = BasedSequence.NULL;

    @Override
    public void getAstExtra(@NotNull StringBuilder out) {
        segmentSpan(out, openingMarker, "open");
        segmentSpan(out, getContent(), "content");
        segmentSpan(out, closingMarker, "close");
    }

    @NotNull
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
