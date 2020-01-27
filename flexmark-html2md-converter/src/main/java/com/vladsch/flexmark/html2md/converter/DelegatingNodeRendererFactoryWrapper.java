package com.vladsch.flexmark.html2md.converter;

import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.dependency.Dependent;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

/**
 * Factory for instantiating new node renderers with dependencies
 */
class DelegatingNodeRendererFactoryWrapper implements Function<DataHolder, HtmlNodeRenderer>, Dependent, DelegatingNodeRendererFactory {
    final private HtmlNodeRendererFactory nodeRendererFactory;
    private List<DelegatingNodeRendererFactoryWrapper> nodeRenderers;
    private Set<Class<?>> myDelegates = null;

    public DelegatingNodeRendererFactoryWrapper(List<DelegatingNodeRendererFactoryWrapper> nodeRenderers, HtmlNodeRendererFactory nodeRendererFactory) {
        this.nodeRendererFactory = nodeRendererFactory;
        this.nodeRenderers = nodeRenderers;
    }

    @Override
    public HtmlNodeRenderer apply(DataHolder options) {
        return nodeRendererFactory.apply(options);
    }

    public HtmlNodeRendererFactory getFactory() {
        return nodeRendererFactory;
    }

    @Override
    public Set<Class<?>> getDelegates() {
        return nodeRendererFactory instanceof DelegatingNodeRendererFactory ? ((DelegatingNodeRendererFactory) nodeRendererFactory).getDelegates() : null;
    }

    @Nullable
    @Override
    final public Set<Class<?>> getAfterDependents() {
        return null;
    }

    @Nullable
    @Override
    public Set<Class<?>> getBeforeDependents() {
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
