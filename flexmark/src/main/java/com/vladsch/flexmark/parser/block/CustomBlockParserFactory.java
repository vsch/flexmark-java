package com.vladsch.flexmark.parser.block;

import com.vladsch.flexmark.internal.util.DataHolder;

/**
 * Custom block parser factory to create parser instance specific block parser factory 
 */
public interface CustomBlockParserFactory {
    BlockParserFactory create(DataHolder options);
}
