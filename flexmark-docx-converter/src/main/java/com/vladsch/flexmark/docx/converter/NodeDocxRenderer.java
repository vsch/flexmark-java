package com.vladsch.flexmark.docx.converter;

import com.vladsch.flexmark.docx.converter.internal.NodeDocxRendererHandler;

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
}
