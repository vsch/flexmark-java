package com.vladsch.flexmark.html;

import org.jetbrains.annotations.Nullable;

import java.util.Set;

public abstract class IndependentAttributeProviderFactory implements AttributeProviderFactory {
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
