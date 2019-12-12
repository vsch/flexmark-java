package com.vladsch.flexmark.util.dependency;

import java.util.ArrayList;
import java.util.List;

public class FlatDependencies<T> extends ResolvedDependencies<FlatDependencyStage<T>> {
    ArrayList<T> linkResolverFactories;

    public FlatDependencies(List<FlatDependencyStage<T>> dependentStages) {
        super(dependentStages);
        ArrayList<T> list = new ArrayList<>();
        for (FlatDependencyStage<T> stage : dependentStages) {
            list.addAll(stage.getDependents());
        }

        linkResolverFactories = list;
    }
}
