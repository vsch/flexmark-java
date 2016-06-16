package com.vladsch.flexmark.parser;

import com.vladsch.flexmark.parser.block.ParserState;

import java.util.Set;

public interface ParagraphPreProcessorFactory {
    /**
     * @return true if this block pre-processor affects the document's properties in such a way that changes how inline elements are
     * parsed.
     */
    boolean getAffectsDocumentProperties();

    /**
     * @return null or a list of block pre-processors that must be executed before calling this one
     * if any of the blocks in the list affect global state then these will be run on ALL blocks of the document
     * before this pre processor is called.
     */
    Set<Class<? extends ParagraphPreProcessorFactory>> getRunAfter();

    /**
     * Create a paragraph pre processor for the document
     *
     * @param state parser state, document blocks have already been parsed at this stage
     * @return block pre-processor
     */
    ParagraphPreProcessor create(ParserState state);
}
