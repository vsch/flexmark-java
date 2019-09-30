package com.vladsch.flexmark.util.visitor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/**
 * Intended to be completed by subclasses for specific node types and node actions
 *
 * @param <C> subclass of this class to have functions returning this to have the correct type
 * @param <N> base node type, this class does not care but in specific handlers it should be a common supertype for all nodes
 * @param <A> action type, subclasses of {@link AstAction} and {@link AstHandler} provide actual functionality
 * @param <H> handler to invoke the functionality during AST traversal for specific node
 */
public class AstNodeHandler<C extends AstNodeHandler<C, N, A, H>, N, A extends AstAction<? extends N>, H extends AstHandler<? extends N, A>> {
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

    public C addHandler(H handler) {
        myCustomHandlersMap.put(handler.getNodeType(), handler);
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

    /**
     * Node processing called for every node being processed
     * <p>
     * Override this to add customizations to standard processing callback.
     *
     * @param node         node being processed
     * @param withChildren whether to process child nodes if there is no handler for the node type
     * @param processor    processor to invoke to perform the processing
     */
    public void processNode(N node, boolean withChildren, BiConsumer<N, H> processor) {
        H handler = getHandler(node);
        if (handler != null) {
            processor.accept(node, handler);
        } else if (withChildren) {
            processChildren(node, processor);
        }
    }

    public void processNode(N parent, BiConsumer<N, H> processor) {
        processNode(parent, true, processor);
    }

    public void processNodeOnly(N parent, BiConsumer<N, H> processor) {
        processNode(parent, false, processor);
    }

    public void processChildren(N parent, BiConsumer<N, H> processor) {
        N node = myAdapter.getFirstChild(parent);
        while (node != null) {
            // A subclass of this visitor might modify the node, resulting in getNext returning a different node or no
            // node after visiting it. So get the next node before visiting.
            N next = myAdapter.getNext(node);
            processNode(node, true, processor);
            node = next;
        }
    }

    /**
     * Process the node and return value from the processor
     *
     * @param node    node to process
     * @param defaultValue default value if no handler is defined for the node
     * @param processor processor to pass the node and handler for processing
     * @param <R>     type of result returned by processor
     * @return result or defaultValue
     */
    public  <R> R processNodeOnly(N node, R defaultValue, BiFunction<N, H, R> processor) {
        H handler = getHandler(node);
        Object[] value = { defaultValue };
        processNode(node, false, (n, h) -> value[0] = processor.apply(n, h));
        return (R) value[0];
    }
}
