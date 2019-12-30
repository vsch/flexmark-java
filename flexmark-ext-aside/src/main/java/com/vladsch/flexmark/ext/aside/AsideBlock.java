package com.vladsch.flexmark.ext.aside;

import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.ast.BlockContent;
import com.vladsch.flexmark.util.ast.BlockQuoteLike;
import com.vladsch.flexmark.util.ast.KeepTrailingBlankLineContainer;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A ExtAside block node
 */
public class AsideBlock extends Block implements BlockQuoteLike, KeepTrailingBlankLineContainer {
    private BasedSequence openingMarker = BasedSequence.NULL;

    @Override
    public void getAstExtra(@NotNull StringBuilder out) {
        segmentSpanChars(out, openingMarker, "marker");
    }

    @NotNull
    @Override
    public BasedSequence[] getSegments() {
        return new BasedSequence[] { openingMarker };
    }

    public AsideBlock() {
    }

    public AsideBlock(BasedSequence chars) {
        super(chars);
    }

    public AsideBlock(BasedSequence chars, List<BasedSequence> segments) {
        super(chars, segments);
    }

    public AsideBlock(BlockContent blockContent) {
        super(blockContent);
    }

    public BasedSequence getOpeningMarker() {
        return openingMarker;
    }

    public void setOpeningMarker(BasedSequence openingMarker) {
        this.openingMarker = openingMarker;
    }
}
