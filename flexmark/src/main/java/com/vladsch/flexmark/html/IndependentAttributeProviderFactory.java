package com.vladsch.flexmark.html;

import java.util.Set;

public abstract class IndependentAttributeProviderFactory implements AttributeProviderFactory {
    @Override
    public Set<Class<? extends AttributeProviderFactory>> getAfterDependents() {
        return null;
    }

    @Override
    public Set<Class<? extends AttributeProviderFactory>> getBeforeDependents() {
        return null;
    }

    @Override
    public boolean affectsGlobalScope() {
        return false;
    }
}
