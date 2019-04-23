package com.vladsch.flexmark.html;

import com.vladsch.flexmark.html.renderer.LinkResolverContext;
import java.util.function.Function;
import com.vladsch.flexmark.util.dependency.Dependent;

import java.util.Set;

public interface AttributeProviderFactory extends Function<LinkResolverContext, AttributeProvider>, Dependent<AttributeProviderFactory> {
    @Override
    Set<Class<? extends AttributeProviderFactory>> getAfterDependents();

    @Override
    Set<Class<? extends AttributeProviderFactory>> getBeforeDependents();

    @Override
    boolean affectsGlobalScope();

    @Override
    AttributeProvider apply(LinkResolverContext context);
}
