package com.vladsch.flexmark.formatter;

import com.vladsch.flexmark.parser.PostProcessorFactory;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.dependency.Dependent;

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
    NodeFormatter create(DataHolder options);

    @Override
    default Set<? extends Class> getAfterDependents() {
        return null;
    }

    @Override
    default Set<? extends Class> getBeforeDependents() {
        return null;
    }

    @Override
    default boolean affectsGlobalScope() {
        return false;
    }
}
