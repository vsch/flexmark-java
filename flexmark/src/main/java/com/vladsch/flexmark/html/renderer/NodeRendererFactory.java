package com.vladsch.flexmark.html.renderer;

import com.vladsch.flexmark.util.options.DataHolder;

/**
 * Factory for instantiating new node renderers when rendering is done.
 */
public interface NodeRendererFactory {

    /**
     * Create a new node renderer for the specified rendering context.
     *
     * @param options the context for rendering (normally passed on to the node renderer)
     * @return a node renderer
     */
    NodeRenderer create(DataHolder options);
}
