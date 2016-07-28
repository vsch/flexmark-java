package com.vladsch.flexmark.internal.util.ast;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Intended to be extended by specific type of node visitor see {@link AttributeProviderAdapter}
 * @param <H> subclass of {@link NodeAdaptingVisitHandler}
 */
public abstract class NodeAdaptedVisitor<H extends NodeAdaptingVisitHandler<?, ?>> {
    final protected Map<Class<?>, H> myCustomHandlersMap = new HashMap<>();

    // Usage:
    //myVisitor = new NodeVisitor(
    //        new NodeAdaptedVisitHandler<>(Text.class, TextCollectingVisitor.this::visit),
    //        new NodeAdaptedVisitHandler<>(HtmlEntity.class, TextCollectingVisitor.this::visit),
    //        new NodeAdaptedVisitHandler<>(SoftLineBreak.class, TextCollectingVisitor.this::visit),
    //        new NodeAdaptedVisitHandler<>(HardLineBreak.class, TextCollectingVisitor.this::visit)
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
    
    //public void visit(Node node) {
    //    A handler = myCustomVisitorsMap.get(node.getClass());
    //    if (handler != null) {
    //        callFunctionHere();
    //    } else {
    //        visitChildren(node);
    //    }
    //}
    //
    //public void visitNodeOnly(Node node) {
    //    A handler = myCustomVisitorsMap.get(node.getClass());
    //    if (handler != null) {
    //        callFunctionHere();
    //    }
    //}
}
