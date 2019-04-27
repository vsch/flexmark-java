package com.vladsch.flexmark.docx.converter;

import com.vladsch.flexmark.util.data.DataHolder;

/**
 * Factory for instantiating new node renderers when rendering is done.
 */
public interface NodeDocxRendererFactory {

    /**
     * Create a new node renderer for the specified rendering context.
     *
     * @param options the context for rendering (normally passed on to the node renderer)
     * @return a node renderer
     */
    NodeDocxRenderer create(DataHolder options);
}
