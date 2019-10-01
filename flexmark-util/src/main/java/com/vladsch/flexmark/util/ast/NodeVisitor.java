package com.vladsch.flexmark.util.ast;

import com.vladsch.flexmark.util.visitor.AstActionHandler;

import java.util.Collection;
import java.util.function.BiConsumer;

/**
 * Configurable node visitor handler which does not know anything about node subclasses
 * while allowing easy configuration of custom visitor for nodes of interest to visit.
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
 * <p>
 * IMPORTANT: This class replaces the old NodeVisitor derived from NodeAdaptedVisitor.
 * <p>
 * If you were overriding {@link #visit(Node)} to provide your own handling of child visits, in the current implementation,
 * it only starts the visiting process and is no longer called for processing every child node.
 * <p>
 * Previously the implementation for visit(Node) looked like:
 * <p>
 * {@code
 * VisitHandler<?> handler = getHandler(node);
 * if (handler != null) {
 *    handler.visit(node);
 * } else {
 *    visitChildren(node);
 * }
 * <p>
 * }
 * <p>
 * you will need to override {@link #processNode(Node node, boolean withChildren, BiConsumer consumer)}, and to change the
 * logic of processing child nodes if withChildren is true and passing child processing to processChildren() instead of visitChildren.
 * <p>
 * {@code
 * H handler = getHandler(node);
 * if (handler != null) {
 *    processor.accept(node, handler);
 * }  else if (withChildren) {
 *    processChildren(node, processor);
 * }
 * }
 * <p>
 * The VisitHandler argument will simply invoke the visit() function on the node.
 */
@SuppressWarnings("rawtypes")
public class NodeVisitor extends AstActionHandler<NodeVisitor, Node, Visitor<Node>, VisitHandler<Node>> implements NodeVisitHandler {
    protected static final VisitHandler<Node>[] EMPTY_HANDLERS = new VisitHandler[0];

    public NodeVisitor() {
        super(Node.AST_ADAPTER);
    }

    public NodeVisitor(VisitHandler... handlers) {
        super(Node.AST_ADAPTER);
        super.addHandlers(handlers);
    }

    public NodeVisitor(VisitHandler[]... handlers) {
        super(Node.AST_ADAPTER);
        super.addHandlers(handlers);
    }

    public NodeVisitor(Collection<VisitHandler> handlers) {
        super(Node.AST_ADAPTER);
        addHandlers(handlers);
    }

    public NodeVisitor addHandlers(Collection<VisitHandler> handlers) {
        return addHandlers(handlers.toArray(EMPTY_HANDLERS));
    }

    // needed for backward compatibility with extension handler arrays typed as VisitHandler<?>[]
    public NodeVisitor addHandlers(VisitHandler... handlers) {
        return super.addHandlers(handlers);
    }

    public NodeVisitor addHandlers(VisitHandler[]... handlers) {
        return super.addHandlers(handlers);
    }

    public NodeVisitor addHandler(VisitHandler handler) {
        return super.addHandler(handler);
    }

    @Override
    final public void visit(Node node) {
        processNode(node, true, this::visit);
    }

    @Override
    final public void visitNodeOnly(Node node) {
        processNode(node, false, this::visit);
    }

    @Override
    final public void visitChildren(Node parent) {
        processChildren(parent, this::visit);
    }

    private void visit(Node node, VisitHandler<?> handler) {
        handler.visit(node);
    }
}
