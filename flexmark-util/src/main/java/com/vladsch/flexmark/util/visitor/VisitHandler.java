package com.vladsch.flexmark.util.visitor;

import com.vladsch.flexmark.util.ast.Node;

/**
 * Node visit handler for specific node type
 */
public class VisitHandler<N extends Node> extends AstHandler<N, VisitorAdapter<N>> {
    public VisitHandler(Class<N> klass, VisitorAdapter<N> adapter) {
        super(klass, adapter);
    }

    public void visit(Node node) {
        getNodeAdapter().visitNode(node);
    }
}
