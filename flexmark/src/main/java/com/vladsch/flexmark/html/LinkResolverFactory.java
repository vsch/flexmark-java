package com.vladsch.flexmark.html;

import com.vladsch.flexmark.html.renderer.LinkResolverBasicContext;
import com.vladsch.flexmark.util.dependency.Dependent;
import java.util.Set;
import java.util.function.Function;

public interface LinkResolverFactory
    extends Function<LinkResolverBasicContext, LinkResolver>, Dependent {
  @Override
  Set<Class<?>> getAfterDependents();

  @Override
  Set<Class<?>> getBeforeDependents();

  @Override
  boolean affectsGlobalScope();

  @Override
  LinkResolver apply(LinkResolverBasicContext context);
}
