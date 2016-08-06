package com.vladsch.flexmark.html;

import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.util.ComputableFactory;
import com.vladsch.flexmark.util.dependency.Dependent;

import java.util.Set;

public interface LinkResolverFactory extends ComputableFactory<LinkResolver, NodeRendererContext>, Dependent<LinkResolverFactory> {
    @Override
    Set<Class<? extends LinkResolverFactory>> getAfterDependents();
    
    @Override
    Set<Class<? extends LinkResolverFactory>> getBeforeDependents();

    @Override
    boolean affectsGlobalScope();

    @Override
    LinkResolver create(NodeRendererContext context);
}
