package com.vladsch.flexmark.parser;

import com.vladsch.flexmark.ast.Document;

public interface InlineParserExtension {
    void finalizeDocument(final InlineParser inlineParser);
    void finalizeBlock(final InlineParser inlineParser);

    /**
     * Parse input
     *
     * @return true if character input was processed
     */
    boolean parse(InlineParser inlineParser);
}
