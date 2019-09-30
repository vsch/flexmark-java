package com.vladsch.flexmark.util.visitor;

public class AstVisitHandler<Node, N extends Node> extends AstNodeAdaptingVisitHandler<Node, N, AstVisitor<Node, N>> implements AstVisitor<Node, Node> {
    public AstVisitHandler(Class<? extends N> aClass, AstVisitor<Node, N> adapter) {
        super(aClass, adapter);
    }

    @Override
    public void visit(Node node) {
        //noinspection unchecked
        myAdapter.visit((N) node);
    }
}
