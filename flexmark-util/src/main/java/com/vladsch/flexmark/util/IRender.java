package com.vladsch.flexmark.util;

import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.options.DataHolder;

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

    /**
     * Get Options for parsing
     * @return DataHolder for options
     */
    DataHolder getOptions();
}
