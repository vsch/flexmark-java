package com.vladsch.flexmark.node;

import com.vladsch.flexmark.internal.BlockContent;
import com.vladsch.flexmark.internal.util.sequence.BasedSequence;

import java.util.List;

public class ThematicBreak extends Block {
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
