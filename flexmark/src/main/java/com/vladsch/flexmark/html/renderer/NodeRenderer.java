package com.vladsch.flexmark.html.renderer;

import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.node.Node;

import java.util.Set;

/**
 * A renderer for a set of node types.
 */
public interface NodeRenderer {

    /**
     * @return the types of nodes that this renderer handles
     */
    Set<Class<? extends Node>> getNodeTypes();

    /**
     * Render the specified node.
     *
     * @param context
     * @param html
     * @param node the node to render, will be an instance of one of {@link #getNodeTypes()}
     */
    void render(NodeRendererContext context, HtmlWriter html, Node node);
}
