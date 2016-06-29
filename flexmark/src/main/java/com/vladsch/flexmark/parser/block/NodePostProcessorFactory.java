package com.vladsch.flexmark.parser.block;

import com.vladsch.flexmark.node.Document;
import com.vladsch.flexmark.parser.PostProcessorFactory;

import java.util.Set;

public abstract class NodePostProcessorFactory implements PostProcessorFactory {
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
        return false;
    }

    @Override
    abstract public NodePostProcessor create(Document document); 
}
