package com.vladsch.flexmark.formatter;

import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.dependency.Dependent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

/**
 * Factory for instantiating new node renderers when rendering is done.
 */
public interface NodeFormatterFactory extends Dependent {

    /**
     * Create a new node renderer for the specified rendering context.
     *
     * @param options the context for rendering (normally passed on to the node renderer)
     * @return a node renderer
     */
    @NotNull NodeFormatter create(@NotNull DataHolder options);

    /**
     * @return null or a list of processors that must be executed before calling this one
     *         if any of the blocks in the list affect global state then these will be run on ALL blocks of the document
     *         before this pre processor is called.
     */
    @Override
    default @Nullable Set<Class<?>> getAfterDependents() {
        return null;
    }

    /**
     * @return null or a list of processors before which this has to be run
     *         if any of the blocks in the list affect global state then these will be run on ALL blocks of the document
     *         before this pre processor is called.
     */
    @Override
    default @Nullable Set<Class<?>> getBeforeDependents() {
        return null;
    }

    @Override
    default boolean affectsGlobalScope() {
        return false;
    }
}
