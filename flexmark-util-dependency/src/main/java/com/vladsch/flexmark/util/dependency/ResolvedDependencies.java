package com.vladsch.flexmark.util.dependency;

import java.util.List;
import java.util.function.Function;

/**
 * @deprecated use {@link DependencyResolver#resolveDependencies(List, Function, Function)}
 */
@Deprecated
public class ResolvedDependencies<T> {
    final private List<T> dependentStages;

    public ResolvedDependencies(List<T> dependentStages) {
        this.dependentStages = dependentStages;
    }

    public List<T> getDependentStages() {
        return dependentStages;
    }

    public boolean isEmpty() {
        return getDependentStages().isEmpty();
    }
}
