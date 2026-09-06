package com.vladsch.flexmark.util.visitor;

import org.jspecify.annotations.Nullable;

/**
 * Interface for converting to AstAccess
 *
 * @param <N> type of node
 */
public interface AstNode<N> {
    @Nullable N getFirstChild(N node);
    @Nullable N getNext(N node);
}
