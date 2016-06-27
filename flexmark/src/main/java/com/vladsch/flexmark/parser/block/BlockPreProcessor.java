package com.vladsch.flexmark.parser.block;

import com.vladsch.flexmark.node.Block;

public interface BlockPreProcessor {

    /**
     * Called on block nodes of interest as given by the NodePreProcessorFactory after all blocks are closed but before inline processing is performed
     *
     * @param state parser state
     * @param block the block node to pre-process
     * @return result of block processing. If the resulting block is not the original block then it will replace the original in the AST
     */
    Block preProcess(ParserState state, Block block);
}
