package com.vladsch.flexmark.html;

import com.vladsch.flexmark.html.renderer.DelegatingNodeRendererFactory;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.dependency.Dependent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

/**
 * Factory for instantiating new node renderers with dependencies
 */
class DelegatingNodeRendererFactoryWrapper implements Function<DataHolder, NodeRenderer>, Dependent, DelegatingNodeRendererFactory {
    final private NodeRendererFactory nodeRendererFactory;
    private List<DelegatingNodeRendererFactoryWrapper> nodeRenderers;
    private Set<Class<?>> myDelegates = null;

    public DelegatingNodeRendererFactoryWrapper(List<DelegatingNodeRendererFactoryWrapper> nodeRenderers, NodeRendererFactory nodeRendererFactory) {
        this.nodeRendererFactory = nodeRendererFactory;
        this.nodeRenderers = nodeRenderers;
    }

    @Override
    public @NotNull NodeRenderer apply(@NotNull DataHolder options) {
        return nodeRendererFactory.apply(options);
    }

    public @NotNull NodeRendererFactory getFactory() {
        return nodeRendererFactory;
    }

    @Override
    public @Nullable Set<Class<?>> getDelegates() {
        return nodeRendererFactory instanceof DelegatingNodeRendererFactory ? ((DelegatingNodeRendererFactory) nodeRendererFactory).getDelegates() : null;
    }

    @Override
    final public @Nullable Set<Class<?>> getAfterDependents() {
        return null;
    }

    @Override
    public @Nullable Set<Class<?>> getBeforeDependents() {
        if (myDelegates == null && nodeRenderers != null) {
            Set<Class<?>> delegates = getDelegates();
            if (delegates != null) {
                myDelegates = new HashSet<>();
                for (DelegatingNodeRendererFactoryWrapper factory : nodeRenderers) {
                    if (delegates.contains(factory.getFactory().getClass())) {
                        myDelegates.add(factory.getFactory().getClass());
                    }
                }
            }

            // release reference
            nodeRenderers = null;
        }
        return myDelegates;
    }

    @Override
    final public boolean affectsGlobalScope() {
        return false;
    }
}
