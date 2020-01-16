package com.vladsch.flexmark.html.renderer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Factory for instantiating an HTML id generator
 */
public interface HeaderIdGeneratorFactory extends HtmlIdGeneratorFactory {
    /**
     * Create a new HeaderIdGenerator for the specified resolver context.
     *
     * @param context the context for link resolution
     * @return an HTML id generator.
     */
    @NotNull HtmlIdGenerator create(@NotNull LinkResolverContext context);
}
