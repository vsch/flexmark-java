package com.vladsch.flexmark.html2md.converter;

import com.vladsch.flexmark.util.dependency.Dependent;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.function.Function;

public interface HtmlLinkResolverFactory extends Function<HtmlNodeConverterContext, HtmlLinkResolver>, Dependent {
    @Nullable
    @Override
    Set<Class<?>> getAfterDependents();

    @Nullable
    @Override
    Set<Class<?>> getBeforeDependents();

    @Override
    boolean affectsGlobalScope();

    @Override
    HtmlLinkResolver apply(HtmlNodeConverterContext context);
}
