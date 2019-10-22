package com.vladsch.flexmark.html;

import com.vladsch.flexmark.html.renderer.LinkResolverContext;
import com.vladsch.flexmark.util.dependency.Dependent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.function.Function;

public interface LinkResolverFactory extends Function<LinkResolverContext, LinkResolver>, Dependent<LinkResolverFactory> {
    @Override
    @Nullable Set<Class<?>> getAfterDependents();

    @Override
    @Nullable Set<Class<?>> getBeforeDependents();

    @Override
    boolean affectsGlobalScope();

    @Override
    @NotNull LinkResolver apply(@NotNull LinkResolverContext context);
}
