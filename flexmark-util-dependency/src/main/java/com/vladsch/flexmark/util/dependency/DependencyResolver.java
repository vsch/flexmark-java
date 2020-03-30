package com.vladsch.flexmark.util.dependency;

import com.vladsch.flexmark.util.collection.iteration.ReversibleIndexedIterator;
import com.vladsch.flexmark.util.misc.Ref;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;

public class DependencyResolver {
    public static <D extends Dependent> @NotNull List<D> resolveFlatDependencies(@NotNull List<D> dependentsList, @Nullable Function<DependentItemMap<D>, DependentItemMap<D>> itemSorter, @Nullable Function<? super D, Class<?>> classExtractor) {
        List<List<D>> list = resolveDependencies(dependentsList, itemSorter, classExtractor);
        if (list.isEmpty()) {
            return Collections.emptyList();
        } else if (list.size() == 1) {
            return list.get(0);
        } else {
            int totalSize = 0;
            for (List<D> subList : list) {
                totalSize += subList.size();
            }

            ArrayList<D> flatList = new ArrayList<>(totalSize);
            for (List<D> subList : list) {
                flatList.addAll(subList);
            }
            return flatList;
        }
    }

    public static <D extends Dependent> @NotNull List<List<D>> resolveDependencies(@NotNull List<D> dependentsList, @Nullable Function<DependentItemMap<D>, DependentItemMap<D>> itemSorter, @Nullable Function<? super D, Class<?>> classExtractor) {
        if (dependentsList.size() == 0) {
            return Collections.emptyList();
        } else if (dependentsList.size() == 1) {
            return Collections.singletonList(dependentsList);
        } else {
            // resolve dependencies and processing lists
            int dependentCount = dependentsList.size();
            DependentItemMap<D> dependentItemMap = new DependentItemMap<>(dependentCount);
            if (classExtractor == null) classExtractor = D::getClass;

            for (D dependent : dependentsList) {
                Class<?> dependentClass = classExtractor.apply(dependent);
                if (dependentItemMap.containsKey(dependentClass)) {
                    throw new IllegalStateException("Dependent class " + dependentClass + " is duplicated. Only one instance can be present in the list");
                }
                DependentItem<D> item = new DependentItem<D>(dependentItemMap.size(), dependent, classExtractor.apply(dependent), dependent.affectsGlobalScope());
                dependentItemMap.put(dependentClass, item);
            }

            for (Map.Entry<Class<?>, DependentItem<D>> entry : dependentItemMap) {
                DependentItem<D> item = entry.getValue();
                Set<Class<?>> afterDependencies = item.dependent.getAfterDependents();

                if (afterDependencies != null && afterDependencies.size() > 0) {
                    for (Class<?> dependentClass : afterDependencies) {
                        if (dependentClass == LastDependent.class) {
                            // must come after all others
                            for (DependentItem<D> dependentItem : dependentItemMap.valueIterable()) {
                                if (dependentItem != null && dependentItem != item) {
                                    item.addDependency(dependentItem);
                                    dependentItem.addDependent(item);
                                }
                            }
                        } else {
                            DependentItem<D> dependentItem = dependentItemMap.get(dependentClass);
                            if (dependentItem != null) {
                                item.addDependency(dependentItem);
                                dependentItem.addDependent(item);
                            }
                        }
                    }
                }

                Set<Class<?>> beforeDependents = item.dependent.getBeforeDependents();
                if (beforeDependents != null && beforeDependents.size() > 0) {
                    for (Class<?> dependentClass : beforeDependents) {
                        if (dependentClass == FirstDependent.class) {
                            // must come before all others
                            for (DependentItem<D> dependentItem : dependentItemMap.valueIterable()) {
                                if (dependentItem != null && dependentItem != item) {
                                    dependentItem.addDependency(item);
                                    item.addDependent(dependentItem);
                                }
                            }
                        } else {
                            DependentItem<D> dependentItem = dependentItemMap.get(dependentClass);
                            if (dependentItem != null) {
                                dependentItem.addDependency(item);
                                item.addDependent(dependentItem);
                            }
                        }
                    }
                }
            }

            if (itemSorter != null) {
                dependentItemMap = itemSorter.apply(dependentItemMap);
            }
            dependentCount = dependentItemMap.size();

            BitSet newReady = new BitSet(dependentCount);
            Ref<BitSet> newReadyRef = new Ref<>(newReady);
            ReversibleIndexedIterator<DependentItem<D>> iterator = dependentItemMap.valueIterator();
            while (iterator.hasNext()) {
                DependentItem<D> item = iterator.next();
                if (!item.hasDependencies()) {
                    newReadyRef.value.set(item.index);
                }
            }

            BitSet dependents = new BitSet(dependentCount);
            dependents.set(0, dependentItemMap.size());

            ArrayList<List<D>> dependencyStages = new ArrayList<>();

            while (newReady.nextSetBit(0) != -1) {
                // process these independents in unspecified order since they do not have dependencies
                ArrayList<D> stageDependents = new ArrayList<>();
                BitSet nextDependents = new BitSet();

                // collect block processors ready for processing, any non-globals go into independents
                while (true) {
                    int i = newReady.nextSetBit(0);
                    if (i < 0) break;

                    newReady.clear(i);
                    DependentItem<D> item = dependentItemMap.getValue(i);
                    assert item != null;

                    stageDependents.add(item.dependent);
                    dependents.clear(i);

                    // removeIndex it from dependent's dependencies
                    if (item.hasDependents()) {
                        while (true) {
                            int j = item.dependents.nextSetBit(0);
                            if (j < 0) break;

                            item.dependents.clear(j);
                            DependentItem<D> dependentItem = dependentItemMap.getValue(j);
                            assert dependentItem != null;

                            if (!dependentItem.removeDependency(item)) {
                                if (item.isGlobalScope) {
                                    nextDependents.set(j);
                                } else {
                                    newReady.set(j);
                                }
                            }
                        }
                    } else if (item.isGlobalScope) {
                        // globals go in their own stage
                        nextDependents.or(newReady);
                        break;
                    }
                }

                // can process these in parallel since it will only contain non-globals or globals not dependent on other globals
                newReady = nextDependents;
                dependencyStages.add(stageDependents);
            }

            if (dependents.nextSetBit(0) != -1) {
                throw new IllegalStateException("have dependents with dependency cycles" + dependents);
            }

            return dependencyStages;
        }
    }
}
