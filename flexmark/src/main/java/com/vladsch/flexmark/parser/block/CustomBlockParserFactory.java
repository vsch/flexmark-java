package com.vladsch.flexmark.parser.block;

import com.vladsch.flexmark.parser.SpecialLeadInHandler;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.dependency.Dependent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

/**
 * Custom block parser factory to create parser instance specific block parser factory
 */
public interface CustomBlockParserFactory extends Function<DataHolder, BlockParserFactory>, Dependent<CustomBlockParserFactory> {
    @Override
    @NotNull BlockParserFactory apply(@NotNull DataHolder options);

    /**
     * @param options options for this parser session
     * @return special lead in character escaper for the block parser elements
     */
    default @Nullable SpecialLeadInHandler getLeadInEscaper(@NotNull DataHolder options) {
        return null;
    }

    /**
     * @param options options for this parser session
     * @return special lead in character un-escaper for the block parser elements
     */
    default @Nullable SpecialLeadInHandler getLeadInUnEscaper(@NotNull DataHolder options) {
        return null;
    }
}
