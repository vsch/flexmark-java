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
public abstract class AstActionHandler<C extends AstActionHandler<C, N, A, H>, N, A extends AstAction<N>, H extends AstHandler<N, A>> {
    private final Map<Class<? extends N>, H> myCustomHandlersMap = new HashMap<>();
    private final AstNode<N> myAstAdapter;

    public AstActionHandler(AstNode<N> astAdapter) {
        myAstAdapter = astAdapter;
    }

    @SafeVarargs
    final protected C addActionHandlers(H[]... handlers) {
        for (H[] moreHandlers : handlers) {
            for (H handler : moreHandlers) {
                myCustomHandlersMap.put(handler.getNodeType(), handler);
            }
        }
        //noinspection unchecked
        return (C) this;
    }

    protected C addActionHandler(H handler) {
        myCustomHandlersMap.put(handler.getNodeType(), handler);
        //noinspection unchecked
        return (C) this;
    }

    private A getAction(H handler) {
        return handler == null ? null : handler.getAdapter();
    }

    public A getAction(N node) {
        return getAction(myCustomHandlersMap.get(node.getClass()));
    }

    public A getAction(Class<?> nodeClass) {
        return getAction(myCustomHandlersMap.get(nodeClass));
    }

    protected H getHandler(N node) {
        return myCustomHandlersMap.get(node.getClass());
    }

    protected H getHandler(Class<?> nodeClass) {
        return myCustomHandlersMap.get(nodeClass);
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
     * @param processor    processor to invoke to perform the processing, BiConsumer taking N node, and A action
     */
    public void processNode(N node, boolean withChildren, BiConsumer<N, A> processor) {
        A action = getAction(node);
        if (action != null) {
            processor.accept(node, action);
        } else if (withChildren) {
            processChildren(node, processor);
        }
    }

    public final void processChildren(N node, BiConsumer<N, A> processor) {
        N child = myAstAdapter.getFirstChild(node);
        while (child != null) {
            // A subclass of this visitor might modify the node, resulting in getNext returning a different node or no
            // node after visiting it. So get the next node before visiting.
            N next = myAstAdapter.getNext(child);
            processNode(child, true, processor);
            child = next;
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
    final public <R> R processNodeOnly(N node, R defaultValue, BiFunction<N, A, R> processor) {
        A handler = getAction(node);
        Object[] value = { defaultValue };
        processNode(node, false, (n, h) -> value[0] = processor.apply(n, h));
        //noinspection unchecked
        return (R) value[0];
    }
}
