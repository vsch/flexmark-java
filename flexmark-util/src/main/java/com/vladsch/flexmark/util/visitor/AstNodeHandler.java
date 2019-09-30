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
public abstract class AstNodeHandler<C extends AstNodeHandler<C, N, A, H>, N, A extends AstAction<? extends N>, H extends AstHandler<? extends N, A>> {
    private final Map<Class<? extends N>, H> myCustomHandlersMap = new HashMap<>();
    private final AstNode<N> myAdapter;

    public AstNodeHandler(AstNode<N> adapter) {
        myAdapter = adapter;
    }

    public C addHandlers(H[]... handlers) {
        for (H[] moreHandlers : handlers) {
            for (H handler : moreHandlers) {
                myCustomHandlersMap.put(handler.getNodeType(), handler);
            }
        }
        return (C) this;
    }

    public H getHandler(N node) {
        return (H) myCustomHandlersMap.get(node.getClass());
    }

    public H getHandler(Class<?> nodeClass) {
        return (H) myCustomHandlersMap.get(nodeClass);
    }

    public Set<Class<? extends N>> getNodeClasses() {
        return myCustomHandlersMap.keySet();
    }

    protected void process(N node) {
        H handler = getHandler(node);
        if (handler != null) {
            process(node, handler);
        } else {
            processChildren(node);
        }
    }

    protected void processNodeOnly(N node) {
        H handler = getHandler(node);
        if (handler != null) {
            process(node, handler);
        }
    }

    protected void processChildren(N parent) {
        N node = myAdapter.getFirstChild(parent);
        while (node != null) {
            // A subclass of this visitor might modify the node, resulting in getNext returning a different node or no
            // node after visiting it. So get the next node before visiting.
            N next = myAdapter.getNext(node);
            process(node);
            node = next;
        }
    }

    abstract protected void process(N node, H handler);
}
