package com.vladsch.flexmark.parser.block;

import com.vladsch.flexmark.util.ComputableFactory;
import com.vladsch.flexmark.util.dependency.Dependent;
import com.vladsch.flexmark.util.options.DataHolder;

/**
 * Custom block parser factory to create parser instance specific block parser factory 
 */
public interface CustomBlockParserFactory extends ComputableFactory<BlockParserFactory, DataHolder>, Dependent<CustomBlockParserFactory> {
    @Override
    BlockParserFactory create(DataHolder options);
}
