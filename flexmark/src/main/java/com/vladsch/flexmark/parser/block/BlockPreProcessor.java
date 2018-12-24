package com.vladsch.flexmark.parser.block;

import com.vladsch.flexmark.util.ast.Block;

public interface BlockPreProcessor {

    /**
     * Called on block nodes of interest as given by the NodePreProcessorFactory after all blocks are closed but before inline processing is performed
     *
     * @param state parser state
     * @param block the block node to pre-process
     */
    void preProcess(ParserState state, Block block);
}
