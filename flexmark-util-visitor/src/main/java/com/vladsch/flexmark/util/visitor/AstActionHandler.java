package com.vladsch.flexmark.util.visitor;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
    final private @NotNull Map<Class<? extends N>, H> customHandlersMap = new HashMap<>();
    final private @NotNull AstNode<N> astAdapter;

    public AstActionHandler(@NotNull AstNode<N> astAdapter) {
        this.astAdapter = astAdapter;
    }

    @SafeVarargs
    final protected @NotNull C addActionHandlers(@NotNull H[]... handlers) {
        for (H[] moreHandlers : handlers) {
            for (H handler : moreHandlers) {
                customHandlersMap.put(handler.getNodeType(), handler);
            }
        }
        //noinspection unchecked
        return (C) this;
    }

    protected @NotNull C addActionHandler(@NotNull H handler) {
        customHandlersMap.put(handler.getNodeType(), handler);
        //noinspection unchecked
        return (C) this;
    }

    private @Nullable A getAction(@Nullable H handler) {
        return handler == null ? null : handler.getAdapter();
    }

    public @Nullable A getAction(@NotNull N node) {
        return getAction(customHandlersMap.get(node.getClass()));
    }

    public @Nullable A getAction(@NotNull Class<?> nodeClass) {
        return getAction(customHandlersMap.get(nodeClass));
    }

    protected @Nullable H getHandler(@NotNull N node) {
        return customHandlersMap.get(node.getClass());
    }

    protected @Nullable H getHandler(@NotNull Class<?> nodeClass) {
        return customHandlersMap.get(nodeClass);
    }

    public @NotNull Set<Class<? extends N>> getNodeClasses() {
        return customHandlersMap.keySet();
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
    protected void processNode(@NotNull N node, boolean withChildren, @NotNull BiConsumer<N, A> processor) {
        A action = getAction(node);
        if (action != null) {
            processor.accept(node, action);
        } else if (withChildren) {
            processChildren(node, processor);
        }
    }

    protected final void processChildren(@NotNull N node, @NotNull BiConsumer<N, A> processor) {
        N child = astAdapter.getFirstChild(node);
        while (child != null) {
            // A subclass of this visitor might modify the node, resulting in getNext returning a different node or no
            // node after visiting it. So get the next node before visiting.
            N next = astAdapter.getNext(child);
            processNode(child, true, processor);
            child = next;
        }
    }

    /**
     * Process the node and return value from the processor
     *
     * @param node         node to process
     * @param defaultValue default value if no handler is defined for the node
     * @param processor    processor to pass the node and handler for processing
     * @param <R>          type of result returned by processor
     * @return result or defaultValue
     */
    final protected <R> R processNodeOnly(@NotNull N node, R defaultValue, @NotNull BiFunction<N, A, R> processor) {
        Object[] value = { defaultValue };
        processNode(node, false, (n, h) -> value[0] = processor.apply(n, h));
        //noinspection unchecked
        return (R) value[0];
    }
}
