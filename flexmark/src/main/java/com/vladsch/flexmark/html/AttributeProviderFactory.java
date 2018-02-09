package com.vladsch.flexmark.html;

import com.vladsch.flexmark.html.renderer.LinkResolverContext;
import com.vladsch.flexmark.util.ComputableFactory;
import com.vladsch.flexmark.util.dependency.Dependent;

import java.util.Set;

public interface AttributeProviderFactory extends ComputableFactory<AttributeProvider, LinkResolverContext>, Dependent<AttributeProviderFactory> {
    @Override
    Set<Class<? extends AttributeProviderFactory>> getAfterDependents();

    @Override
    Set<Class<? extends AttributeProviderFactory>> getBeforeDependents();

    @Override
    boolean affectsGlobalScope();

    @Override
    AttributeProvider create(LinkResolverContext context);
}
