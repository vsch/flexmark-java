package com.vladsch.flexmark.util.ast;

import com.vladsch.flexmark.util.visitor.AstHandler;

/**
 * Node visit handler for specific node type
 */
public class VisitHandler<N extends Node> extends AstHandler<N, Visitor<N>> {
    public VisitHandler(Class<N> klass, Visitor<N> adapter) {
        super(klass, adapter);
    }

    public void visit(Node node) {
        myAdapter.visit((N)node);
    }
}
