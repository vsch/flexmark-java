package com.vladsch.flexmark.formatter.internal;

import java.util.Set;

/**
 * A renderer for a set of node types.
 */
public interface NodeFormatter {
    /**
     * @return the mapping of nodes this renderer handles to rendering function
     */
    Set<NodeFormattingHandler<?>> getNodeFormattingHandlers();
}
