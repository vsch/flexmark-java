package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.SubSequence;

import java.util.List;

public class BlockQuote extends Block {
    private BasedSequence openingMarker = SubSequence.NULL;

    @Override
    public void getAstExtra(StringBuilder out) {
        segmentSpanChars(out, openingMarker, "marker");
    }

    @Override
    public BasedSequence[] getSegments() {
        return new BasedSequence[] { openingMarker };
    }

    public BlockQuote() {
    }

    public BlockQuote(BasedSequence chars) {
        super(chars);
    }

    public BlockQuote(BasedSequence chars, List<BasedSequence> segments) {
        super(chars, segments);
    }

    public BlockQuote(BlockContent blockContent) {
        super(blockContent);
    }

    public BasedSequence getOpeningMarker() {
        return openingMarker;
    }

    public void setOpeningMarker(BasedSequence openingMarker) {
        this.openingMarker = openingMarker;
    }
}
