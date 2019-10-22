package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.ast.BlockContent;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ThematicBreak extends Block {
    @NotNull
    @Override
    public BasedSequence[] getSegments() {
        return EMPTY_SEGMENTS;
    }

    public ThematicBreak() {
    }

    public ThematicBreak(BasedSequence chars) {
        super(chars);
    }

    public ThematicBreak(BasedSequence chars, List<BasedSequence> segments) {
        super(chars, segments);
    }

    public ThematicBreak(BlockContent blockContent) {
        super(blockContent);
    }
}
