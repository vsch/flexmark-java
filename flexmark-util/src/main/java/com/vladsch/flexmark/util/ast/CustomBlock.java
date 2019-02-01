package com.vladsch.flexmark.util.ast;

import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.List;

/**
 * @deprecated Use Block directly, this adds nothing useful
 */

@Deprecated
public abstract class CustomBlock extends Block {
    public CustomBlock() {
    }

    public CustomBlock(BasedSequence chars) {
        super(chars);
    }

    public CustomBlock(BasedSequence chars, List<BasedSequence> lineSegments) {
        super(chars, lineSegments);
    }

    public CustomBlock(List<BasedSequence> lineSegments) {
        super(lineSegments);
    }

    public CustomBlock(BlockContent blockContent) {
        super(blockContent);
    }
}
