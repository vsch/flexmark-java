package com.vladsch.flexmark.html.renderer;

/**
 * Factory for instantiating an HTML id generator
 */
public interface HeaderIdGeneratorFactory {
    /**
     * Create a new node renderer for the specified rendering context.
     *
     * @param context the context for link resolution
     * @return an HTML id generator
     */
    HtmlIdGenerator create(LinkResolverContext context);
}
