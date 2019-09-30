package com.vladsch.flexmark.util.visitor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Intended to be extended by specific type of node action handling
 */
public abstract class AstNodeActionMap<N, A extends AstNodeAction<N>, H extends AstNodeHandler<N,?,A>> {
    private final Map<Class<?>, H> myCustomHandlersMap = new HashMap<>();

    // Usage:
    //myVisitor = new NodeVisitor(
    //        new NodeAdaptingVisitHandler<>(Text.class, this::visit),
    //        new NodeAdaptingVisitHandler<>(HtmlEntity.class, this::visit),
    //        new NodeAdaptingVisitHandler<>(SoftLineBreak.class, this::visit),
    //        new NodeAdaptingVisitHandler<>(HardLineBreak.class, this::visit)
    //);
    public AstNodeActionMap(H... handlers) {
        addHandlers(handlers);
    }

    public AstNodeActionMap(H[]... handlers) {
        addHandlers(handlers);
    }

    public AstNodeActionMap(Collection<H>... handlers) {
        addHandlers(handlers);
    }

    public AstNodeActionMap<N, A, H> addHandlers(H[]... handlers) {
        for (H[] moreVisitors : handlers) {
            for (H handler : moreVisitors) {
                myCustomHandlersMap.put(handler.getNodeType(), handler);
            }
        }
        return this;
    }

    public AstNodeActionMap<N, A, H> addHandlers(Collection<H>... handlers) {
        for (Collection<H> handlerList : handlers) {
            for (H handler : handlerList) {
                myCustomHandlersMap.put(handler.getNodeType(), handler);
            }
        }
        return this;
    }

    public H getHandler(N node) {
        return myCustomHandlersMap.get(node.getClass());
    }

    public H getHandler(Class<?> nodeClass) {
        return myCustomHandlersMap.get(nodeClass);
    }

    public Set<Class<?>> getNodeClasses() {
        return myCustomHandlersMap.keySet();
    }

    protected void process(N node) {
        H handler = getHandler(node);
        if (handler != null) {
            accept(node, handler);
        } else {
            processChildren(node);
        }
    }

    protected void processNodeOnly(N node) {
        H handler = getHandler(node);
        if (handler != null) {
            accept(node, handler);
        }
    }

    protected void processChildren(N parent) {
        N node = getAdapter().getFirstChild(parent);
        while (node != null) {
            // A subclass of this visitor might modify the node, resulting in getNext returning a different node or no
            // node after visiting it. So get the next node before visiting.
            N next = getAdapter().getNext(node);
            process(node);
            node = next;
        }
    }

    abstract protected AstNode<N> getAdapter();
    abstract protected void accept(N node, H handler);
}
