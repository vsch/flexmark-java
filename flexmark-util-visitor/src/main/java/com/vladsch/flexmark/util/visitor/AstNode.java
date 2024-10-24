package com.vladsch.flexmark.util.visitor;

/**
 * Interface for converting to AstAccess
 *
 * @param <N> type of node
 */
public interface AstNode<N> {

  N getFirstChild(N node);

  N getNext(N node);
}
