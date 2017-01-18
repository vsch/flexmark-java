package com.vladsch.flexmark.parser;

import com.vladsch.flexmark.ast.Block;
import com.vladsch.flexmark.ast.Document;
import com.vladsch.flexmark.parser.block.BlockPreProcessor;
import com.vladsch.flexmark.parser.block.ParserState;
import com.vladsch.flexmark.util.ComputableFactory;
import com.vladsch.flexmark.util.dependency.Dependent;

import java.util.Set;

public interface InlineParserExtensionFactory extends ComputableFactory<InlineParserExtension, InlineParser>, Dependent<InlineParserExtensionFactory> {
    /**
     * Starting Characters for this inline processor
     *
     * @return set of characters for which this processor should be invoiked
     */
    CharSequence getCharacters();

    /**
     * Create a paragraph pre processor for the document
     *
     * @param inlineParser inline parser instance
     * @return inline parser extension
     */
    InlineParserExtension create(InlineParser inlineParser);
}
