package com.vladsch.flexmark.parser.block;

import java.util.function.Function;
import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.dependency.Dependent;

import java.util.Set;

public interface BlockPreProcessorFactory extends Function<ParserState, BlockPreProcessor>, Dependent<BlockPreProcessorFactory> {
    /**
     * Block types that this pre-processors processes
     *
     * @return set of block node types
     */
    Set<Class<? extends Block>> getBlockTypes();

    /**
     * Create a paragraph pre processor for the document
     *
     * @param state parser state, document blocks have already been parsed at this stage
     * @return block pre-processor
     */
    BlockPreProcessor apply(ParserState state);
}
