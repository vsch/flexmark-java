package com.vladsch.flexmark.util.dependency;

import java.util.List;
import java.util.function.Function;

/**
 * @deprecated use {@link DependencyResolver#resolveFlatDependencies(List, Function, Function)}
 */
@Deprecated
public class FlatDependencyStage<T> {
    final private List<T> dependents;

    public FlatDependencyStage(List<T> dependents) {
        // compute mappings
        this.dependents = dependents;
    }

    public List<T> getDependents() {
        return dependents;
    }
}
