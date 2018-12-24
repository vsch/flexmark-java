package com.vladsch.flexmark.parser.block;

import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.parser.PostProcessorFactory;

import java.util.*;

public abstract class NodePostProcessorFactory implements PostProcessorFactory {
    private final HashMap<Class<?>, Set<Class<?>>> NODE_MAP = new HashMap<Class<?>, Set<Class<?>>>();

    // added to force constructor
    public NodePostProcessorFactory(boolean ignored) {
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
        return false;
    }

    protected final void addNodeWithExclusions(Class<? extends Node> nodeType, Class<?>... excludeDescendantsOf) {
        if (excludeDescendantsOf.length > 0) {
            NODE_MAP.put(nodeType, new HashSet<Class<?>>(Arrays.asList(excludeDescendantsOf)));
        } else {
            //noinspection unchecked
            addNodes(nodeType);
        }
    }

    protected final void addNodes(Class<?>... nodeTypes) {
        for (Class<?> nodeType : nodeTypes) {
            //noinspection unchecked
            NODE_MAP.put(nodeType, Collections.EMPTY_SET);
        }
    }

    @Override
    public final Map<Class<?>, Set<Class<?>>> getNodeTypes() {
        return NODE_MAP;
    }

    @Override
    abstract public NodePostProcessor create(Document document);
}
