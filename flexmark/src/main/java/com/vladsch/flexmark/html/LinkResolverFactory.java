package com.vladsch.flexmark.html;

import com.vladsch.flexmark.internal.util.ComputableFactory;
import com.vladsch.flexmark.internal.util.dependency.Dependent;
import com.vladsch.flexmark.node.Document;

import java.util.Set;

public interface LinkResolverFactory extends ComputableFactory<LinkResolver, Document>, Dependent<LinkResolverFactory> {
    @Override
    Set<Class<? extends LinkResolverFactory>> getAfterDependents();
    
    @Override
    Set<Class<? extends LinkResolverFactory>> getBeforeDependents();

    @Override
    boolean affectsGlobalScope();

    @Override
    LinkResolver create(Document param);
}
