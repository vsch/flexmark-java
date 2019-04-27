package com.vladsch.flexmark.parser.block;

import java.util.function.Function;
import com.vladsch.flexmark.util.dependency.Dependent;
import com.vladsch.flexmark.util.data.DataHolder;

/**
 * Custom block parser factory to create parser instance specific block parser factory
 */
public interface CustomBlockParserFactory extends Function<DataHolder, BlockParserFactory>, Dependent<CustomBlockParserFactory> {
    @Override
    BlockParserFactory apply(DataHolder options);
}
