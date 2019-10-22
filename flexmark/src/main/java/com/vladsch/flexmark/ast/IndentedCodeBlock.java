package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.ast.BlockContent;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class IndentedCodeBlock extends Block {

    @NotNull
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
