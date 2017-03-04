package com.vladsch.flexmark.util.dependency;

import java.util.List;

public class FlatDependencyHandler<T extends Dependent<T>> extends DependencyHandler<T, FlatDependencyStage<T>, FlatDependencies<T>> {
    public List<T> resolvedDependencies(List<T> dependentsList) {
        //noinspection unchecked
        FlatDependencies<T> dependencies = resolveDependencies(dependentsList);
        return dependencies.myLinkResolverFactories;
    }

    @Override
    protected FlatDependencyStage<T> createStage(List<T> dependents) {
        return new FlatDependencyStage<T>(dependents);
    }

    @Override
    protected Class<? extends T> getDependentClass(T dependent) {
        //noinspection unchecked
        return (Class<? extends T>) dependent.getClass();
    }

    @Override
    protected FlatDependencies<T> createResolvedDependencies(List<FlatDependencyStage<T>> stages) {
        return new FlatDependencies<T>(stages);
    }

    public static <T extends Dependent<T>> List<T> computeDependencies(List<T> dependentsList) {
        FlatDependencyHandler<T> resolver = new FlatDependencyHandler<T>();
        return resolver.resolvedDependencies(dependentsList);
    }
}
