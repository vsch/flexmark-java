package com.vladsch.flexmark.html;

import com.vladsch.flexmark.html.renderer.LinkResolverContext;
import com.vladsch.flexmark.util.dependency.Dependent;
import java.util.Set;
import java.util.function.Function;

public interface AttributeProviderFactory
    extends Function<LinkResolverContext, AttributeProvider>, Dependent {
  @Override
  Set<Class<?>> getAfterDependents();

  @Override
  Set<Class<?>> getBeforeDependents();

  @Override
  boolean affectsGlobalScope();

  @Override
  AttributeProvider apply(LinkResolverContext context);
}
