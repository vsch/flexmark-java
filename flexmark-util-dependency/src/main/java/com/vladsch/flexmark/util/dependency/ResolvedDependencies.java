package com.vladsch.flexmark.util.dependency;

import java.util.List;

public class ResolvedDependencies<T> {
    private final List<T> dependentStages;

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
