package com.vladsch.flexmark.util.visitor;

import java.util.Collection;

/**
 * Node visitor that visits all children by default and allows configuring node handlers to handle specific classes.
 * <p>
 * Can be used to only process certain nodes. If you override a method and want visiting to descend into children,
 * call {@link #visitChildren}.
 */
public class AstNodeVisitor<Node> extends AstNodeAdaptedVisitor<Node, AstVisitHandler<Node, ?>> {
    public AstNodeVisitor(AstAdapter<Node, Node> adapter, AstVisitHandler<Node, ?>... handlers) {
        super(adapter, handlers);
    }

    public AstNodeVisitor(AstAdapter<Node, Node> adapter, AstVisitHandler<Node, ?>[]... handlers) {
        super(adapter, handlers);
    }

    public AstNodeVisitor(AstAdapter<Node, Node> adapter, Collection<AstVisitHandler<Node, ?>> handlers) {
        super(adapter, handlers);
    }

    @Override
    public void visit(Node node) {
        AstVisitHandler<Node, ?> handler = getHandler(node);
        if (handler != null) {
            handler.visit(node);
        } else {
            visitChildren(node);
        }
    }

    @Override
    public void visitNoChildren(Node node) {
        AstVisitHandler<Node, ?> handler = getHandler(node);
        if (handler != null) {
            handler.visit(node);
        }
    }
}
