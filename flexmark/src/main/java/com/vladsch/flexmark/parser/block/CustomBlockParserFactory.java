package com.vladsch.flexmark.parser.block;

import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.dependency.Dependent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 * Custom block parser factory to create parser instance specific block parser factory
 */
public interface CustomBlockParserFactory extends Function<DataHolder, BlockParserFactory>, Dependent<CustomBlockParserFactory> {
    @Override
    @NotNull BlockParserFactory apply(@NotNull DataHolder options);
}
