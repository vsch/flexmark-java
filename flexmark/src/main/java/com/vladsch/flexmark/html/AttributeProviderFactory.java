package com.vladsch.flexmark.html;

import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.internal.util.ComputableFactory;
import com.vladsch.flexmark.internal.util.dependency.Dependent;

import java.util.Set;

public interface AttributeProviderFactory extends ComputableFactory<AttributeProvider, NodeRendererContext>, Dependent<AttributeProviderFactory> {
    @Override
    Set<Class<? extends AttributeProviderFactory>> getAfterDependents();
    
    @Override
    Set<Class<? extends AttributeProviderFactory>> getBeforeDependents();

    @Override
    boolean affectsGlobalScope();

    @Override
    AttributeProvider create(NodeRendererContext context);
}
