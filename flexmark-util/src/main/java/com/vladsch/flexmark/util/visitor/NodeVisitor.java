package com.vladsch.flexmark.util.visitor;

import com.vladsch.flexmark.util.ast.Node;

import java.util.Collection;

/**
 * Configurable node visitor handler which does know all node subclasses while allowing easy configuration
 * of which nodes are of interest
 * <p>
 * Usage:
 * myVisitor = new NodeVisitor(
 * new VisitHandler<>(Text.class, this::visit),
 * new VisitHandler<>(HtmlEntity.class, this::visit),
 * new VisitHandler<>(SoftLineBreak.class, this::visit),
 * new VisitHandler<>(HardLineBreak.class, this::visit)
 * );
 */
public class NodeVisitor extends AstNodeActionMap<Node, Visitor<Node>, VisitHandler<Node>> implements VisitorHandler {
    @SuppressWarnings("rawtypes")
    protected static final VisitHandler[] EMPTY_HANDLERS = new VisitHandler[0];

    @SuppressWarnings("rawtypes")
    public NodeVisitor(VisitHandler... handlers) {
        super(handlers);
    }

    @SuppressWarnings("rawtypes")
    public NodeVisitor(VisitHandler[]... handlers) {
        super(handlers);
    }

    @SuppressWarnings("rawtypes")
    public NodeVisitor(Collection<VisitHandler> handlers) {
        super(handlers.toArray(EMPTY_HANDLERS));
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

    @SuppressWarnings("rawtypes")
    @Override
    protected void accept(Node node, VisitHandler handler) {
        handler.visit(node);

    }
}
