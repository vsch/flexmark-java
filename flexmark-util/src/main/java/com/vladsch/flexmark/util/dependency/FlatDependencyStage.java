package com.vladsch.flexmark.util.dependency;

import java.util.List;

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
