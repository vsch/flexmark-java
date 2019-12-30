package com.vladsch.flexmark.formatter;

import com.vladsch.flexmark.util.sequence.SequenceUtils;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

/**
 * A renderer for a set of node types.
 */
public interface NodeFormatter {
    /**
     * @return the mapping of nodes this renderer handles to rendering function
     */
    @Nullable Set<NodeFormattingHandler<?>> getNodeFormattingHandlers();

    /**
     * Collect nodes of given type so that they can be quickly accessed without traversing the AST
     * by all formatting extensions.
     *
     * @return the nodes of interest to this formatter during formatting.
     */
    @Nullable Set<Class<?>> getNodeClasses();

    /**
     * Return character which compacts like block quote prefix
     *
     * @return character or NUL if none
     */
    default char getBlockQuoteLikePrefixChar() {
        return SequenceUtils.NUL;
    }
}
