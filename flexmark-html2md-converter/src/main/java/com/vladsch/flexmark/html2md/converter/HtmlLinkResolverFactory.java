package com.vladsch.flexmark.html2md.converter;

import com.vladsch.flexmark.util.dependency.Dependent;

import java.util.Set;
import java.util.function.Function;

public interface HtmlLinkResolverFactory extends Function<HtmlNodeConverterContext, HtmlLinkResolver>, Dependent<HtmlLinkResolverFactory> {
    @Override
    Set<Class<? extends HtmlLinkResolverFactory>> getAfterDependents();

    @Override
    Set<Class<? extends HtmlLinkResolverFactory>> getBeforeDependents();

    @Override
    boolean affectsGlobalScope();

    @Override
    HtmlLinkResolver apply(HtmlNodeConverterContext context);
}
