package com.vladsch.flexmark.util.visitor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Intended to be extended by specific type of node visitor
 *
 * @param <H> subclass of {@link AstNodeAdaptingVisitHandler}
 */
public abstract class AstNodeAdaptedVisitor<Node, H extends AstNodeAdaptingVisitHandler<Node, ?, ?>> implements AstNodeVisitorBase<Node>, AstAdapter<Node,Node> {
    private final Map<Class<?>, H> myCustomHandlersMap = new HashMap<Class<?>, H>();
    final AstAdapter<Node,Node> adapter;

    // Usage:
    //myVisitor = new NodeVisitor(
    //        new NodeAdaptingVisitHandler<>(Text.class, this::visit),
    //        new NodeAdaptingVisitHandler<>(HtmlEntity.class, this::visit),
    //        new NodeAdaptingVisitHandler<>(SoftLineBreak.class, this::visit),
    //        new NodeAdaptingVisitHandler<>(HardLineBreak.class, this::visit)
    //);
    public AstNodeAdaptedVisitor(AstAdapter<Node,Node> adapter, H... handlers) {
        this.adapter = adapter;
        addHandlers(handlers);
    }

    public AstNodeAdaptedVisitor(AstAdapter<Node,Node> adapter, H[]... handlers) {
        this.adapter = adapter;
        addHandlers(handlers);
    }

    public AstNodeAdaptedVisitor(AstAdapter<Node,Node> adapter, Collection<H> handlers) {
        this.adapter = adapter;
        addHandlers(handlers);
    }

    public AstNodeAdaptedVisitor<Node, H> addHandlers(H... handlers) {
        for (H handler : handlers) {
            myCustomHandlersMap.put(handler.getNodeType(), handler);
        }
        return this;
    }

    @Override
    public Node getFirstChild(Node node) {
        return adapter.getFirstChild(node);
    }

    @Override
    public Node getNext(Node node) {
        return adapter.getNext(node);
    }

    public AstNodeAdaptedVisitor<Node, H> addHandlers(H[]... handlers) {
        for (H[] moreVisitors : handlers) {
            for (H handler : moreVisitors) {
                myCustomHandlersMap.put(handler.getNodeType(), handler);
            }
        }
        return this;
    }

    public AstNodeAdaptedVisitor<Node, H> addHandlers(Collection<H> handlers) {
        for (H handler : handlers) {
            myCustomHandlersMap.put(handler.getNodeType(), handler);
        }
        return this;
    }

    public H getHandler(Node node) {
        return myCustomHandlersMap.get(node.getClass());
    }

    public H getHandler(Class<?> nodeClass) {
        return myCustomHandlersMap.get(nodeClass);
    }

    public Set<Class<?>> getNodeClasses() {
        return myCustomHandlersMap.keySet();
    }
}
