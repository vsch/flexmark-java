package com.vladsch.flexmark.util.visitor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Intended to be completed by subclasses for specific node types and node actions
 *
 * @param <A> node action type: visit, format, render, etc.
 * @param <H> node handler type for converting from node super class to subclass, actual action
 *            performed by subclass of this class
 */
public abstract class AstNodeActionMap<N, A extends AstNodeAction<N>, H extends AstNodeHandler<N, A>> {
    private final Map<Class<?>, H> myCustomHandlersMap = new HashMap<>();

    public AstNodeActionMap(H... handlers) {
        addHandlers(handlers);
    }

    public AstNodeActionMap(H[]... handlers) {
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

    public AstNodeActionMap<N, A, H> addHandlers(Collection<H> handlers) {
        for (H handler : handlers) {
            myCustomHandlersMap.put(handler.getNodeType(), handler);
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
