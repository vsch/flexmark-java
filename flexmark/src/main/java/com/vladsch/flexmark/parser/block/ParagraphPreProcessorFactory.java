package com.vladsch.flexmark.parser.block;

import com.vladsch.flexmark.util.dependency.Dependent;

import java.util.function.Function;

public interface ParagraphPreProcessorFactory extends Function<ParserState, ParagraphPreProcessor>, Dependent {

    /**
     * Create a paragraph pre processor for the document
     *
     * @param state parser state, document blocks have already been parsed at this stage
     * @return block pre-processor
     */
    @Override
    ParagraphPreProcessor apply(ParserState state);
}
