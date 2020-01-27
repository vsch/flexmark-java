package com.vladsch.flexmark.parser.block;

import com.vladsch.flexmark.parser.PostProcessorFactory;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Set;

public abstract class DocumentPostProcessorFactory implements PostProcessorFactory {
    /**
     * Node types that this post processor processes
     *
     * @return set of block node types
     */
    @Override
    final public Map<Class<?>, Set<Class<?>>> getNodeTypes() {
        return null;
    }

    @Nullable
    @Override
    public Set<Class<?>> getAfterDependents() {
        return null;
    }

    @Nullable
    @Override
    public Set<Class<?>> getBeforeDependents() {
        return null;
    }

    @Override
    final public boolean affectsGlobalScope() {
        return true;
    }
}
