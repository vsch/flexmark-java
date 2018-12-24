package com.vladsch.flexmark.parser.block;

import com.vladsch.flexmark.parser.block.BlockParser;

public interface BlockParserTracker {
    void blockParserAdded(BlockParser blockParser);
    void blockParserRemoved(BlockParser blockParser);
}
