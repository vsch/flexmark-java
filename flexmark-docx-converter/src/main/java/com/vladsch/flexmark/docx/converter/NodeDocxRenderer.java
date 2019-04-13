package com.vladsch.flexmark.docx.converter;

import java.util.Set;

/**
 * A renderer for a set of node types.
 */
public interface NodeDocxRenderer {
    /**
     * @return the mapping of nodes this renderer handles to rendering function
     */
    Set<NodeDocxRendererHandler<?>> getNodeFormattingHandlers();

    /**
     * Collect nodes of given type so that they can be quickly accessed without traversing the AST
     * by all formatting extensions.
     *
     * @return the nodes of interest to this formatter during formatting.
     */
    Set<Class<?>> getNodeClasses();

    /**
     * Return mapping of all nodes whose children should be wrapped in a bookmark if the parent node
     * has an id attribute. Default for block nodes to have a bookmark before the node element and for
     * inline nodes to have a bookmark around the element.
     * <p>
     * CAUTION: final classes need to be returned, not super classes. Comparison is done by hash on class
     *
     * @return the nodes of interest to this formatter during formatting.
     */
    Set<Class<?>> getBookmarkWrapsChildrenClasses();
}
