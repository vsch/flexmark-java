package com.vladsch.flexmark.util.visitor;

import com.vladsch.flexmark.util.ast.Node;

public class VisitHandler<N extends Node> extends AstNodeHandler<Node, N, Visitor<N>> implements AstNodeAction<Node> {
    public VisitHandler(Class<? extends N> klass, Visitor<N> adapter) {
        super(klass, adapter);
    }

    @Override
    public N apply(Node node) {
        return (N) node;
    }

    public void visit(Node node) {
        getNodeAdapter().visit(apply(node));
    }
}
