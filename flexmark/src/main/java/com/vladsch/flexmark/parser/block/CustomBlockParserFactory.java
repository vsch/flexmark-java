package com.vladsch.flexmark.parser.block;

import com.vladsch.flexmark.internal.util.ComputableFactory;
import com.vladsch.flexmark.internal.util.options.DataHolder;

/**
 * Custom block parser factory to create parser instance specific block parser factory 
 */
public interface CustomBlockParserFactory extends ComputableFactory<BlockParserFactory, DataHolder> {
    @Override
    BlockParserFactory create(DataHolder options);
}
