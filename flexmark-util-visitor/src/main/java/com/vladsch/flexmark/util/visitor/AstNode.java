package com.vladsch.flexmark.util.visitor;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Interface for converting to AstAccess
 *
 * @param <N> type of node
 */
public interface AstNode<N> {
    @Nullable N getFirstChild(@NotNull N node);
    @Nullable N getNext(@NotNull N node);
}
