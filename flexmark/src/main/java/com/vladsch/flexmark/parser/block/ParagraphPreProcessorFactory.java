package com.vladsch.flexmark.parser.block;

import java.util.function.Function;
import com.vladsch.flexmark.util.dependency.Dependent;

public interface ParagraphPreProcessorFactory extends Function<ParserState, ParagraphPreProcessor>, Dependent<ParagraphPreProcessorFactory> {

    /**
     * Create a paragraph pre processor for the document
     *
     * @param state parser state, document blocks have already been parsed at this stage
     * @return block pre-processor
     */
    @Override
    ParagraphPreProcessor apply(ParserState state);
}
