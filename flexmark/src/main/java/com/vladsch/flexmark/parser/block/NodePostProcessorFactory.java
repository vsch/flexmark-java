package com.vladsch.flexmark.parser.block;

import com.vladsch.flexmark.parser.PostProcessorFactory;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public abstract class NodePostProcessorFactory implements PostProcessorFactory {
    final private HashMap<Class<?>, Set<Class<?>>> NODE_MAP = new HashMap<>();

    // added to force constructor
    public NodePostProcessorFactory(boolean ignored) {
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
        return false;
    }

    protected final void addNodeWithExclusions(Class<? extends Node> nodeType, Class<?>... excludeDescendantsOf) {
        if (excludeDescendantsOf.length > 0) {
            NODE_MAP.put(nodeType, new HashSet<>(Arrays.asList(excludeDescendantsOf)));
        } else {
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
    final public Map<Class<?>, Set<Class<?>>> getNodeTypes() {
        return NODE_MAP;
    }

    @NotNull
    @Override
    abstract public NodePostProcessor apply(@NotNull Document document);
}
