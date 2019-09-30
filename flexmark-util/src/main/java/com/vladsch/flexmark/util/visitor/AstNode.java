package com.vladsch.flexmark.util.visitor;

public interface AstNode<N> {
    N getFirstChild(N node);
    N getNext(N node);
}
