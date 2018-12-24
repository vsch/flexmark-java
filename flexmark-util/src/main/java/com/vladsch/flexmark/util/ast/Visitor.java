package com.vladsch.flexmark.util.ast;

public interface Visitor<N extends Node> extends NodeAdaptingVisitor<N> {
    void visit(N node);
}
