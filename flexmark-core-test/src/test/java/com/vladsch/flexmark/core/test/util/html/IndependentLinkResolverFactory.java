package com.vladsch.flexmark.core.test.util.html;

import com.vladsch.flexmark.html.LinkResolverFactory;
import java.util.Set;

abstract class IndependentLinkResolverFactory implements LinkResolverFactory {

  @Override
  public Set<Class<?>> getAfterDependents() {
    return null;
  }

  @Override
  public Set<Class<?>> getBeforeDependents() {
    return null;
  }

  @Override
  public boolean affectsGlobalScope() {
    return false;
  }
}
