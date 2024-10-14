package com.vladsch.flexmark.util.ast;

import com.vladsch.flexmark.util.visitor.AstActionHandler;
import java.util.Collection;
import org.jetbrains.annotations.NotNull;

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
public class NodeVisitor
    extends AstActionHandler<NodeVisitor, Node, Visitor<Node>, VisitHandler<Node>>
    implements NodeVisitHandler {
  private static final VisitHandler[] EMPTY_HANDLERS = new VisitHandler[0];

  public NodeVisitor() {
    super(Node.AST_ADAPTER);
  }

  public NodeVisitor(@NotNull VisitHandler... handlers) {
    super(Node.AST_ADAPTER);
    super.addActionHandlers(handlers);
  }

  private @NotNull NodeVisitor addHandlers(@NotNull Collection<VisitHandler> handlers) {
    return super.addActionHandlers(handlers.toArray(EMPTY_HANDLERS));
  }

  @NotNull
  NodeVisitor addHandler(@NotNull VisitHandler handler) {
    return super.addActionHandler(handler);
  }

  @Override
  public final void visit(@NotNull Node node) {
    processNode(node, true, this::visit);
  }

  @Override
  public final void visitNodeOnly(@NotNull Node node) {
    processNode(node, false, this::visit);
  }

  @Override
  public final void visitChildren(@NotNull Node parent) {
    processChildren(parent, this::visit);
  }

  private void visit(@NotNull Node node, @NotNull Visitor<Node> handler) {
    handler.visit(node);
  }
}
