package com.vladsch.flexmark.node;

import com.vladsch.flexmark.internal.BlockContent;
import com.vladsch.flexmark.internal.util.sequence.BasedSequence;

import java.util.List;

public class IndentedCodeBlock extends Block {

    @Override
    public BasedSequence[] getSegments() {
        return EMPTY_SEGMENTS;
    }

    public IndentedCodeBlock() {
    }

    public IndentedCodeBlock(BasedSequence chars) {
        super(chars);
    }

    public IndentedCodeBlock(BasedSequence chars, List<BasedSequence> segments) {
        super(chars, segments);
    }

    public IndentedCodeBlock(BlockContent blockContent) {
        super(blockContent);
    }
}
