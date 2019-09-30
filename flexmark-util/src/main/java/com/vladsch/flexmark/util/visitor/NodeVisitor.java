package com.vladsch.flexmark.util.visitor;

import com.vladsch.flexmark.util.ast.Node;

import java.util.Collection;

/**
 * Configurable node visitor handler which does know all node subclasses while allowing easy configuration
 * of which nodes are of interest
 * <p>
 * Usage:
 * myVisitor = new NodeVisitor(
 * new VisitHandler<>(Document.class, this::visit),
 * new VisitHandler<>(HtmlEntity.class, this::visit),
 * new VisitHandler<>(SoftLineBreak.class, this::visit),
 * new VisitHandler<>(HardLineBreak.class, this::visit)
 * );
 * <p>
 * Document doc;
 * myVisitor.visit(doc);
 */
@SuppressWarnings("rawtypes")
public class NodeVisitor extends AstNodeHandler<NodeVisitor, Node, VisitHandler.Visitor<Node>, VisitHandler<Node>> implements NodeVisitHandler {
    protected static final VisitHandler<Node>[] EMPTY_HANDLERS = new VisitHandler[0];

    public NodeVisitor(VisitHandler... handlers) {
        super(Node.AST_ADAPTER);
        addHandlers(handlers);
    }

    public NodeVisitor(VisitHandler[]... handlers) {
        super(Node.AST_ADAPTER);
        addHandlers(handlers);
    }

    public NodeVisitor(Collection<VisitHandler> handlers) {
        super(Node.AST_ADAPTER);
        addHandlers(handlers);
    }

    public NodeVisitor addHandlers(Collection<VisitHandler> handlers) {
        return addHandlers(handlers.toArray(EMPTY_HANDLERS));
    }

    // needed for backward compatibility with extension handler arrays typed as VisitHandler<?>[]
    public NodeVisitor addHandlers(VisitHandler[] handlers) {
        return super.addHandlers(handlers);
    }

    @Override
    public void visit(Node node) {
        processNodeOrChildren(node, this::process);
    }

    @Override
    public void visitNodeOnly(Node node) {
        processNodeOnly(node, this::process);
    }

    @Override
    public void visitChildren(Node parent) {
        processChildren(parent, this::process);
    }

    private void process(Node node, VisitHandler handler) {
        handler.visit(node);
    }
}
