package com.vladsch.flexmark.html.renderer;

/**
 * Factory for instantiating new ast renderers when rendering is done.
 */
public interface HeaderIdGeneratorFactory {

    /**
     * Create a new ast renderer for the specified rendering context.
     *
     * @param context the context for rendering (normally passed on to the ast renderer)
     * @return a ast renderer
     */
    HtmlIdGenerator create(NodeRendererContext context);
}
