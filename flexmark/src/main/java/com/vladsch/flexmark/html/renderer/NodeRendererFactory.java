package com.vladsch.flexmark.html.renderer;

import com.vladsch.flexmark.util.options.DataHolder;

/**
 * Factory for instantiating new ast renderers when rendering is done.
 */
public interface NodeRendererFactory {

    /**
     * Create a new ast renderer for the specified rendering context.
     *
     * @param options the context for rendering (normally passed on to the ast renderer)
     * @return a ast renderer
     */
    NodeRenderer create(DataHolder options);

}
