package com.vladsch.flexmark.util.ast;

public class VisitHandler<N extends Node> extends NodeAdaptingVisitHandler<N, Visitor<N>> {
    public VisitHandler(Class<? extends N> aClass, Visitor<N> adapter) {
        super(aClass, adapter);
    }

    public void visit(Node node) {
        //noinspection unchecked
        myAdapter.visit((N) node);
    }
}
