package com.vladsch.flexmark.parser.block;

import com.vladsch.flexmark.parser.PostProcessorFactory;

import java.util.Map;
import java.util.Set;

public abstract class DocumentPostProcessorFactory implements PostProcessorFactory {
    /**
     * Node types that this post processor processes
     *
     * @return set of block node types
     */
    @Override
    public final Map<Class<?>, Set<Class<?>>> getNodeTypes() {
        return null;
    }

    @Override
    public Set<Class<? extends PostProcessorFactory>> getAfterDependents() {
        return null;
    }

    @Override
    public Set<Class<? extends PostProcessorFactory>> getBeforeDependents() {
        return null;
    }

    @Override
    public final boolean affectsGlobalScope() {
        return true;
    }
}
