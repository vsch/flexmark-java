package com.vladsch.flexmark.util.dependency;

import java.util.ArrayList;
import java.util.List;

public class FlatDependencies<T> extends ResolvedDependencies<FlatDependencyStage<T>> {
    ArrayList<T> myLinkResolverFactories;

    public FlatDependencies(List<FlatDependencyStage<T>> dependentStages) {
        super(dependentStages);
        ArrayList<T> list = new ArrayList<T>();
        for (FlatDependencyStage<T> stage : dependentStages) {
            list.addAll(stage.getDependents());
        }

        myLinkResolverFactories = list;
    }
}
