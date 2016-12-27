package com.vladsch.flexmark.ast;

public interface Visitor<N extends Node> extends NodeAdaptingVisitor<N> {
    void visit(N node);
}
