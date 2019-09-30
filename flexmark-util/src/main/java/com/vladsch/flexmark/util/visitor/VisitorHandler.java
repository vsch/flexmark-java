package com.vladsch.flexmark.util.visitor;

import com.vladsch.flexmark.util.ast.Node;

public interface VisitorHandler<N extends Node> extends Visitor<N> {
    void visit(N node);
    void visitNodeOnly(Node node);
    void visitChildren(Node parent);
}
