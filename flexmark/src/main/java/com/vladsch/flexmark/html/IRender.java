package com.vladsch.flexmark.html;

import com.vladsch.flexmark.internal.util.options.DataHolder;
import com.vladsch.flexmark.node.Node;

/**
 * Render interface for rendering implementation for RenderingTestCase
 */
public interface IRender {
    public void render(Node node, Appendable output);

    /**
     * Render the tree of nodes to HTML.
     *
     * @param node the root node
     * @return the rendered HTML
     */
    String render(Node node);

    IRender withOptions(DataHolder options);
}
