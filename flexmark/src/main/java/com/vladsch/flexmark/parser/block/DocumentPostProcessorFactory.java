package com.vladsch.flexmark.parser.block;

import com.vladsch.flexmark.node.Node;
import com.vladsch.flexmark.parser.PostProcessorFactory;

import java.util.Set;

public abstract class DocumentPostProcessorFactory implements PostProcessorFactory {
    /**
     * Node types that this post processor processes
     *
     * @return set of block node types
     */
    @Override
    final public Set<Class<? extends Node>> getNodeTypes() {
        return null;
    }

    @Override
    public Set<Class<? extends Node>> getExcludeDescendantsOfTypes() {
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
    final public boolean affectsGlobalScope() {
        return true;
    }
}
