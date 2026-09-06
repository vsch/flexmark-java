package com.vladsch.flexmark.html;

import com.vladsch.flexmark.html.renderer.LinkResolverBasicContext;
import com.vladsch.flexmark.util.dependency.Dependent;
import org.jspecify.annotations.Nullable;

import java.util.Set;
import java.util.function.Function;

public interface LinkResolverFactory extends Function<LinkResolverBasicContext, LinkResolver>, Dependent {
    @Override
    @Nullable Set<Class<?>> getAfterDependents();

    @Override
    @Nullable Set<Class<?>> getBeforeDependents();

    @Override
    boolean affectsGlobalScope();

    @Override
    LinkResolver apply(LinkResolverBasicContext context);
}
