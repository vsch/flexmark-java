package com.vladsch.flexmark.util.ast;

import com.vladsch.flexmark.util.visitor.AstActionHandler;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Intended to be extended by specific type of node visitor
 *
 * @param <H> subclass of {@link NodeAdaptingVisitHandler}
 * @deprecated Use {@link AstActionHandler} instead.
 * see: {@link NodeVisitor}, {@link com.vladsch.flexmark.ast.util.LinkResolverAdapter}, {@link com.vladsch.flexmark.ast.util.AttributeProviderAdapter}
 */
@Deprecated
public abstract class NodeAdaptedVisitor<H extends NodeAdaptingVisitHandler<?, ?>> {
    private final Map<Class<?>, H> myCustomHandlersMap = new HashMap<>();

    // Usage:
    //myVisitor = new NodeVisitor(
    //        new NodeAdaptingVisitHandler<>(Text.class, this::visit),
    //        new NodeAdaptingVisitHandler<>(HtmlEntity.class, this::visit),
    //        new NodeAdaptingVisitHandler<>(SoftLineBreak.class, this::visit),
    //        new NodeAdaptingVisitHandler<>(HardLineBreak.class, this::visit)
    //);
    public NodeAdaptedVisitor(H... handlers) {
        addHandlers(handlers);
    }

    public NodeAdaptedVisitor(H[]... handlers) {
        addHandlers(handlers);
    }

    public NodeAdaptedVisitor(Collection<H> handlers) {
        addHandlers(handlers);
    }

    public NodeAdaptedVisitor<H> addHandlers(H... handlers) {
        for (H handler : handlers) {
            myCustomHandlersMap.put(handler.getNodeType(), handler);
        }
        return this;
    }

    public NodeAdaptedVisitor<H> addHandlers(H[]... handlers) {
        for (H[] moreVisitors : handlers) {
            for (H handler : moreVisitors) {
                myCustomHandlersMap.put(handler.getNodeType(), handler);
            }
        }
        return this;
    }

    public NodeAdaptedVisitor<H> addHandlers(Collection<H> handlers) {
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
