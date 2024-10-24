package com.vladsch.flexmark.html;

import com.vladsch.flexmark.html.renderer.LinkResolverBasicContext;
import com.vladsch.flexmark.util.dependency.Dependent;
import java.util.Set;
import java.util.function.Function;

public interface UriContentResolverFactory
    extends Function<LinkResolverBasicContext, UriContentResolver>, Dependent {
  @Override
  Set<Class<?>> getAfterDependents();

  @Override
  Set<Class<?>> getBeforeDependents();

  @Override
  boolean affectsGlobalScope();

  @Override
  UriContentResolver apply(LinkResolverBasicContext context);
}
