package com.vladsch.flexmark.ext.toc;

import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.ast.BlockContent;
import com.vladsch.flexmark.util.ast.DoNotDecorate;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A sim toc contents node containing all text that came after the sim toc node
 */
public class SimTocContent extends Block implements DoNotDecorate {
    @NotNull
    @Override
    public BasedSequence[] getSegments() {
        //return EMPTY_SEGMENTS;
        return EMPTY_SEGMENTS;
    }

    @Override
    public void getAstExtra(@NotNull StringBuilder out) {
    }

    public SimTocContent() {
    }

    public SimTocContent(BasedSequence chars) {
        super(chars);
    }

    public SimTocContent(BasedSequence chars, List<BasedSequence> lineSegments) {
        super(chars, lineSegments);
    }

    public SimTocContent(List<BasedSequence> lineSegments) {
        super(lineSegments);
    }

    public SimTocContent(BlockContent blockContent) {
        super(blockContent);
    }
}
