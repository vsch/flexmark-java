package com.vladsch.flexmark.util.dependency;

import java.util.BitSet;

public class DependentItem<D> {
  final int index;
  final D dependent;
  public final boolean isGlobalScope;
  private BitSet dependencies;
  BitSet dependents;

  DependentItem(int index, D dependent, boolean isGlobalScope) {
    this.index = index;
    this.dependent = dependent;
    this.isGlobalScope = isGlobalScope;
  }

  void addDependency(DependentItem<D> dependency) {
    if (this.dependencies == null) {
      this.dependencies = new BitSet();
    }
    this.dependencies.set(dependency.index);
  }

  boolean removeDependency(DependentItem<D> dependency) {
    if (this.dependencies != null) {
      this.dependencies.clear(dependency.index);
    }
    return hasDependencies();
  }

  void addDependent(DependentItem<D> dependent) {
    if (this.dependents == null) {
      this.dependents = new BitSet();
    }
    this.dependents.set(dependent.index);
  }

  boolean hasDependencies() {
    return dependencies != null && dependencies.nextSetBit(0) != -1;
  }

  boolean hasDependents() {
    return dependents != null && dependents.nextSetBit(0) != -1;
  }
}
