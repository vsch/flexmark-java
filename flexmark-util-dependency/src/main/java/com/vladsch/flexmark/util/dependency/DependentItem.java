package com.vladsch.flexmark.util.dependency;

import java.util.BitSet;

public class DependentItem<D> {
    final public int index;
    final public D dependent;
    final public Class<?> dependentClass;
    final public boolean isGlobalScope;
    BitSet dependencies;
    BitSet dependents;

    public DependentItem(int index, D dependent, Class<?> dependentClass, boolean isGlobalScope) {
        this.index = index;
        this.dependent = dependent;
        this.dependentClass = dependentClass;
        this.isGlobalScope = isGlobalScope;
    }

    public void addDependency(DependentItem<D> dependency) {
        if (this.dependencies == null) this.dependencies = new BitSet();
        this.dependencies.set(dependency.index);
    }

    public void addDependency(BitSet dependencies) {
        if (this.dependencies == null) this.dependencies = new BitSet();
        this.dependencies.or(dependencies);
    }

    public boolean removeDependency(DependentItem<D> dependency) {
        if (this.dependencies != null) {
            this.dependencies.clear(dependency.index);
        }
        return hasDependencies();
    }

    public boolean removeDependency(BitSet dependencies) {
        if (this.dependencies != null) {
            this.dependencies.andNot(dependencies);
        }
        return hasDependencies();
    }

    public void addDependent(DependentItem<D> dependent) {
        if (this.dependents == null) this.dependents = new BitSet();
        this.dependents.set(dependent.index);
    }

    public void addDependent(BitSet dependents) {
        if (this.dependents == null) this.dependents = new BitSet();
        this.dependents.or(dependents);
    }

    public void removeDependent(DependentItem<D> dependent) {
        if (this.dependents != null) {
            this.dependents.clear(dependent.index);
        }
    }

    public void removeDependent(BitSet dependents) {
        if (this.dependents != null) {
            this.dependents.andNot(dependents);
        }
    }

    public boolean hasDependencies() {
        return dependencies != null && dependencies.nextSetBit(0) != -1;
    }

    public boolean hasDependents() {
        return dependents != null && dependents.nextSetBit(0) != -1;
    }
}
