package com.vladsch.flexmark.formatter.internal;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.util.options.DataHolder;

import java.util.Set;

/**
 * A renderer for a set of node types.
 */
public interface NodeFormatter {
    /**
     * @return the mapping of nodes this renderer handles to rendering function
     */
    Set<NodeFormattingHandler<?>> getNodeFormattingHandlers();

    /**
     * Collect nodes of given type so that they can be quickly accessed without traversing the AST
     * by all formatting extensions.
     *
     * @param options builder options
     * @return the nodes of interest to this formatter during formatting.
     */
    Set<Class<?>> getNodeClasses(DataHolder options);
}
