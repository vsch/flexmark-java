package com.vladsch.flexmark.parser.block;

import com.vladsch.flexmark.util.ComputableFactory;
import com.vladsch.flexmark.util.dependency.Dependent;

public interface ParagraphPreProcessorFactory extends ComputableFactory<ParagraphPreProcessor, ParserState>, Dependent<ParagraphPreProcessorFactory> {

    /**
     * Create a paragraph pre processor for the document
     *
     * @param state parser state, document blocks have already been parsed at this stage
     * @return block pre-processor
     */
    @Override ParagraphPreProcessor create(ParserState state);
}
