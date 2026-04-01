package com.vladsch.flexmark.ext.docusaurus.admonition;

import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.ast.BlockContent;
import com.vladsch.flexmark.util.sequence.BasedSequence;

class DocusaurusAdmonitionBlock extends Block {
    private BasedSequence openingMarker = BasedSequence.NULL;
    private BasedSequence type = BasedSequence.NULL;
    private BasedSequence closingMarker = BasedSequence.NULL;
    private final BlockContent content = new BlockContent();

    public DocusaurusAdmonitionBlock() {
    }

    @Override
    public BasedSequence[] getSegments() {
        return new BasedSequence[]{openingMarker, type, closingMarker};
    }

    public BasedSequence getOpeningMarker() {
        return this.openingMarker;
    }

    public BasedSequence getType() {
        return this.type;
    }

    public BasedSequence getClosingMarker() {
        return this.closingMarker;
    }

    public BlockContent getContent() {
        return this.content;
    }

    public void setOpeningMarker(BasedSequence openingMarker) {
        this.openingMarker = openingMarker;
    }

    public void setType(BasedSequence type) {
        this.type = type;
    }

    public void setClosingMarker(BasedSequence closingMarker) {
        this.closingMarker = closingMarker;
    }
}
