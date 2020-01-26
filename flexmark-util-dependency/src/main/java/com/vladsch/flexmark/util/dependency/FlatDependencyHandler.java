package com.vladsch.flexmark.util.dependency;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FlatDependencyHandler<T extends Dependent<T>> extends DependencyHandler<T, FlatDependencyStage<T>, FlatDependencies<T>> {
    public List<T> resolvedDependencies(List<T> dependentsList) {
        FlatDependencies<T> dependencies = resolveDependencies(dependentsList);
        return dependencies.dependencies;
    }

    @NotNull
    @Override
    protected FlatDependencyStage<T> createStage(List<T> dependents) {
        return new FlatDependencyStage<>(dependents);
    }

    @NotNull
    @Override
    protected Class<? extends T> getDependentClass(T dependent) {
        //noinspection unchecked
        return (Class<? extends T>) dependent.getClass();
    }

    @NotNull
    @Override
    protected FlatDependencies<T> createResolvedDependencies(List<FlatDependencyStage<T>> stages) {
        return new FlatDependencies<>(stages);
    }

    public static <T extends Dependent<T>> List<T> computeDependencies(List<T> dependentsList) {
        FlatDependencyHandler<T> resolver = new FlatDependencyHandler<>();
        return resolver.resolvedDependencies(dependentsList);
    }
}
