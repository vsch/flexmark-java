package com.vladsch.flexmark.internal.util;

import com.vladsch.flexmark.parser.block.BlockParser;

public interface BlockParserTracker {
    void blockParserAdded(BlockParser blockParser);
    void blockParserRemoved(BlockParser blockParser);
}
