package com.vladsch.flexmark.util.dependency;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @deprecated use {@link DependencyResolver#resolveFlatDependencies(List, Function, Function)}
 */
@Deprecated
public class FlatDependencies<T> extends ResolvedDependencies<FlatDependencyStage<T>> {
  ArrayList<T> dependencies;

  public FlatDependencies(List<FlatDependencyStage<T>> dependentStages) {
    super(dependentStages);
    ArrayList<T> list = new ArrayList<>();
    for (FlatDependencyStage<T> stage : dependentStages) {
      list.addAll(stage.getDependents());
    }

    dependencies = list;
  }
}
