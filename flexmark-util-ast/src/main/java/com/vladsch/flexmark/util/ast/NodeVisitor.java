package com.vladsch.flexmark.util.ast;

import com.vladsch.flexmark.util.visitor.AstActionHandler;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

// @formatter:off

/*
 * Configurable node visitor handler which does not know anything about node subclasses
 * while allowing easy configuration of custom visitor for nodes of interest to visit.
 * <p>
 * Usage:
 *
 * myVisitor = new NodeVisitor(
 *     new VisitHandler&lt;&gt;(Document.class, this::visit),
 *     new VisitHandler&lt;&gt;(HtmlEntity.class, this::visit),
 *     new VisitHandler&lt;&gt;(SoftLineBreak.class, this::visit),
 *     new VisitHandler&lt;&gt;(HardLineBreak.class, this::visit)
 * );
 *
 * <p>
 * Document doc;
 * myVisitor.visit(doc);
 * <p>
 * NOTE: This class replaces the old NodeVisitor derived from NodeAdaptedVisitor.
 * <p>
 * If you were overriding {@link #visit(Node)} to provide your own handling of child visits, in the current implementation,
 * it only starts the visiting process and is no longer called for processing every child node.
 * <p>
 * Previously the implementation for visit(Node) looked like:
 * <p>
 *
 * @Override
 * public void visit(Node node) {
 *    processNode(node, true, this::visit);
 *
 *    VisitHandler&lt;?&gt; handler = getHandler(node);
 *    if (handler != null) {
 *        handler.visit(node);
 *    } else {
 *        visitChildren(node);
 *    }
 * }
 *
 * <p>
 * you will need to override {@link #processNode(Node node, boolean withChildren, BiConsumer consumer)}, and to change the
 * logic of processing child nodes if withChildren is true and passing child processing to processChildren() instead of visitChildren.
 * <p>
 *
 * @Override
 * public void processNode(Node node, boolean withChildren, BiConsumer&lt;Node, Visitor&lt;Node&gt;&gt; processor) {
 *     Visitor&lt;?&gt; handler = getAction(node);
 *     if (handler != null) {
 *         processor.accept(node, visitor);
 *     }  else if (withChildren) {
 *         processChildren(node, processor);
 *     }
 * }
 *
 */

// @formatter:on
@SuppressWarnings("rawtypes")
public class NodeVisitor extends AstActionHandler<NodeVisitor, Node, Visitor<Node>, VisitHandler<Node>> implements NodeVisitHandler {
    protected static final VisitHandler[] EMPTY_HANDLERS = new VisitHandler[0];

    public NodeVisitor() {
        super(Node.AST_ADAPTER);
    }

    public NodeVisitor(@NotNull VisitHandler... handlers) {
        super(Node.AST_ADAPTER);
        super.addActionHandlers(handlers);
    }

    public NodeVisitor(@NotNull VisitHandler[]... handlers) {
        super(Node.AST_ADAPTER);
        //noinspection unchecked
        super.addActionHandlers(handlers);
    }

    public NodeVisitor(@NotNull Collection<VisitHandler> handlers) {
        super(Node.AST_ADAPTER);
        addHandlers(handlers);
    }

    // add handler variations
    public @NotNull NodeVisitor addTypedHandlers(@NotNull Collection<VisitHandler<?>> handlers) {
        return super.addActionHandlers(handlers.toArray(EMPTY_HANDLERS));
    }

    @SuppressWarnings("UnusedReturnValue")
    public @NotNull NodeVisitor addHandlers(@NotNull Collection<VisitHandler> handlers) {
        return super.addActionHandlers(handlers.toArray(EMPTY_HANDLERS));
    }

    public @NotNull NodeVisitor addHandlers(@NotNull VisitHandler[] handlers) {
        return super.addActionHandlers(handlers);
    }

    public @NotNull NodeVisitor addHandlers(@NotNull VisitHandler[]... handlers) {
        //noinspection unchecked
        return super.addActionHandlers(handlers);
    }

    @SuppressWarnings("UnusedReturnValue")
    public @NotNull NodeVisitor addHandler(@NotNull VisitHandler handler) {
        //noinspection unchecked
        return super.addActionHandler(handler);
    }

    @Override
    final public void visit(@NotNull Node node) {
        processNode(node, true, this::visit);
    }

    @Override
    final public void visitNodeOnly(@NotNull Node node) {
        processNode(node, false, this::visit);
    }

    @Override
    final public void visitChildren(@NotNull Node parent) {
        processChildren(parent, this::visit);
    }

    private void visit(@NotNull Node node, @NotNull Visitor<Node> handler) {
        handler.visit(node);
    }
}
