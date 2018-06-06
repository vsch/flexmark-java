package com.vladsch.flexmark.html.renderer;

/**
 * Factory for instantiating new node renderers when rendering is done.
 */
public interface HeaderIdGeneratorFactory {

    /**
     * Create a new node renderer for the specified rendering context.
     *
     * @param context the context for rendering (normally passed on to the node renderer)
     * @return a node renderer
     *
     * @deprecated implement {@link HtmlIdGeneratorFactory} instead
     */
    HtmlIdGenerator create(LinkResolverContext context);
}
