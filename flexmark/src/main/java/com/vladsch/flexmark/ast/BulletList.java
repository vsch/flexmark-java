package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.ast.BlockContent;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BulletList extends ListBlock {
    private char openingMarker;

    @NotNull
    @Override
    public BasedSequence[] getSegments() {
        return EMPTY_SEGMENTS;
    }

    public BulletList() {
    }

    public BulletList(BasedSequence chars) {
        super(chars);
    }

    public BulletList(BasedSequence chars, List<BasedSequence> segments) {
        super(chars, segments);
    }

    public BulletList(BlockContent blockContent) {
        super(blockContent);
    }

    public char getOpeningMarker() {
        return openingMarker;
    }

    public void setOpeningMarker(char openingMarker) {
        this.openingMarker = openingMarker;
    }
}
