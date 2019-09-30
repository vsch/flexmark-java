package com.vladsch.flexmark.util.visitor;

import com.vladsch.flexmark.util.ast.Node;

import java.util.Collection;

/**
 * Intended to be extended by specific type of node visitor
 */
public class NodeVisitor extends AstNodeActionMap<Node, Visitor<Node>, VisitHandler<Node>> implements VisitorHandler<Node> {
    public NodeVisitor(VisitHandler<Node>... handlers) {
        super(handlers);
    }

    public NodeVisitor(VisitHandler<Node>[]... handlers) {
        super(handlers);
    }

    public NodeVisitor(Collection<VisitHandler<Node>>... handlers) {
        super(handlers);
    }

    @Override
    public void visit(Node node) {
        process(node);
    }

    @Override
    public void visitNodeOnly(Node node) {
        processNodeOnly(node);
    }

    @Override
    public void visitChildren(Node parent) {
        processChildren(parent);

    }

    @Override
    protected AstNode<Node> getAdapter() {
        return Node.AST_ADAPTER;
    }

    @Override
    protected void accept(Node node, VisitHandler<Node> handler) {
        handler.getNodeAdapter().visit(node);
    }
}
