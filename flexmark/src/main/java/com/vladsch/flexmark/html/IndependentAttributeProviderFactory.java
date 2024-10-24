package com.vladsch.flexmark.html;

import java.util.Set;

public abstract class IndependentAttributeProviderFactory implements AttributeProviderFactory {

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
