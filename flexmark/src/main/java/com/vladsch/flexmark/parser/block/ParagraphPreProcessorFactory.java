package com.vladsch.flexmark.parser.block;

import com.vladsch.flexmark.internal.util.dependency.Dependent;

public interface ParagraphPreProcessorFactory extends Dependent<ParagraphPreProcessorFactory> {

    /**
     * Create a paragraph pre processor for the document
     *
     * @param state parser state, document blocks have already been parsed at this stage
     * @return block pre-processor
     */
    ParagraphPreProcessor create(ParserState state);
}
