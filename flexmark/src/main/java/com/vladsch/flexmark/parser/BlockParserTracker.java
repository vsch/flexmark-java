package com.vladsch.flexmark.parser;

import com.vladsch.flexmark.parser.block.BlockParser;

public interface BlockParserTracker {
    void added(BlockParser blockParser);
    void removed(BlockParser blockParser);
}
