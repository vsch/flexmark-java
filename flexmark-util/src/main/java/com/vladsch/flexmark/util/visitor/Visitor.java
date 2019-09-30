package com.vladsch.flexmark.util.visitor;

import com.vladsch.flexmark.util.ast.Node;

public interface Visitor<N extends Node> extends AstNodeAction<N> {
    void visit(N node);
}
