package com.vladsch.flexmark.html.renderer;

import org.jetbrains.annotations.Nullable;

import java.util.Set;

/**
 * A renderer for a set of node types.
 */
public interface NodeRenderer {
    /**
     * @return the mapping of nodes this renderer handles to rendering function
     */
    @Nullable Set<NodeRenderingHandler<?>> getNodeRenderingHandlers();
}
