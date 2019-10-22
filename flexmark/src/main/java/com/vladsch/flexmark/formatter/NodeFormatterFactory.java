package com.vladsch.flexmark.formatter;

import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.dependency.Dependent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

/**
 * Factory for instantiating new node renderers when rendering is done.
 */
public interface NodeFormatterFactory extends Dependent<NodeFormatterFactory> {

    /**
     * Create a new node renderer for the specified rendering context.
     *
     * @param options the context for rendering (normally passed on to the node renderer)
     * @return a node renderer
     */
    @NotNull NodeFormatter create(@NotNull DataHolder options);

    @Override
    default @Nullable Set<Class<?>> getAfterDependents() {
        return null;
    }

    @Override
    default @Nullable Set<Class<?>> getBeforeDependents() {
        return null;
    }

    @Override
    default boolean affectsGlobalScope() {
        return false;
    }
}
