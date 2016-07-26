package com.vladsch.flexmark.html;

import java.util.Set;

public abstract class IndependentLinkResolverFactory implements LinkResolverFactory {
    @Override
    public Set<Class<? extends LinkResolverFactory>> getAfterDependents() {
        return null;
    }

    @Override
    public Set<Class<? extends LinkResolverFactory>> getBeforeDependents() {
        return null;
    }

    @Override
    public boolean affectsGlobalScope() {
        return false;
    }
}
