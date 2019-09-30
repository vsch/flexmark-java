package com.vladsch.flexmark.util.visitor;

public interface AstVisitor<Node, N extends Node> extends AstNodeAdaptingVisitor<Node, N> {
    void visit(N node);
}
