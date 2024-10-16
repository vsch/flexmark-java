package com.vladsch.flexmark.core.test.util.html;

import com.vladsch.flexmark.html.LinkResolverFactory;
import java.util.Set;
import org.jetbrains.annotations.Nullable;

abstract class IndependentLinkResolverFactory implements LinkResolverFactory {
  @Nullable
  @Override
  public Set<Class<?>> getAfterDependents() {
    return null;
  }

  @Nullable
  @Override
  public Set<Class<?>> getBeforeDependents() {
    return null;
  }

  @Override
  public boolean affectsGlobalScope() {
    return false;
  }
}
