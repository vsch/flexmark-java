package com.vladsch.flexmark.util.dependency;

import com.vladsch.flexmark.util.collection.iteration.ReversibleIndexedIterator;
import com.vladsch.flexmark.util.misc.Ref;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

/**
 * @deprecated use DependencyResolver instead
 */
@Deprecated
public abstract class DependencyHandler<D extends Dependent, S, R extends ResolvedDependencies<S>> {
  protected abstract @NotNull S createStage(List<D> dependents);

  protected abstract @NotNull Class getDependentClass(D dependent);

  protected abstract @NotNull R createResolvedDependencies(List<S> stages);

  public R resolveDependencies(List<D> dependentsList) {
    if (dependentsList.isEmpty()) {
      return createResolvedDependencies(Collections.emptyList());
    } else if (dependentsList.size() == 1) {
      D dependent = dependentsList.get(0);
      List<D> dependents = Collections.singletonList(dependent);
      return createResolvedDependencies(Collections.singletonList(createStage(dependents)));
    } else {
      // resolve dependencies and processing lists
      int dependentCount = dependentsList.size();
      DependentItemMap<D> dependentItemMap = new DependentItemMap<>(dependentCount);

      for (D dependent : dependentsList) {
        Class dependentClass = getDependentClass(dependent);
        if (dependentItemMap.containsKey(dependentClass)) {
          throw new IllegalStateException(
              "Dependent class "
                  + dependentClass
                  + " is duplicated. Only one instance can be present in the list");
        }
        DependentItem<D> item =
            new DependentItem<>(
                dependentItemMap.size(),
                dependent,
                getDependentClass(dependent),
                dependent.affectsGlobalScope());
        dependentItemMap.put(dependentClass, item);
      }

      for (Map.Entry<Class<?>, DependentItem<D>> entry : dependentItemMap) {
        DependentItem<D> item = entry.getValue();
        Set<Class<?>> afterDependencies = item.dependent.getAfterDependents();

        if (afterDependencies != null && !afterDependencies.isEmpty()) {
          for (Class<?> dependentClass : afterDependencies) {
            DependentItem<D> dependentItem = dependentItemMap.get(dependentClass);
            if (dependentItem != null) {
              item.addDependency(dependentItem);
              dependentItem.addDependent(item);
            }
          }
        }

        Set<Class<?>> beforeDependents = item.dependent.getBeforeDependents();
        if (beforeDependents != null && !beforeDependents.isEmpty()) {
          for (Class<?> dependentClass : beforeDependents) {
            DependentItem<D> dependentItem = dependentItemMap.get(dependentClass);
            if (dependentItem != null) {
              dependentItem.addDependency(item);
              item.addDependent(dependentItem);
            }
          }
        }
      }

      dependentItemMap = prioritize(dependentItemMap);
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

      List<S> dependencyStages = new ArrayList<>();

      while (newReady.nextSetBit(0) != -1) {
        // process these independents in unspecified order since they do not have dependencies
        List<D> stageDependents = new ArrayList<>();
        BitSet nextDependents = new BitSet();

        // collect block processors ready for processing, any non-globals go into independents
        while (true) {
          int i = newReady.nextSetBit(0);
          if (i < 0) break;

          newReady.clear(i);
          DependentItem<D> item = dependentItemMap.getValue(i);

          stageDependents.add(item.dependent);
          dependents.clear(i);

          // removeIndex it from dependent's dependencies
          if (item.hasDependents()) {
            while (true) {
              int j = item.dependents.nextSetBit(0);
              if (j < 0) break;

              item.dependents.clear(j);
              DependentItem<D> dependentItem = dependentItemMap.getValue(j);

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

        // can process these in parallel since it will only contain non-globals or globals not
        // dependent on other globals
        newReady = nextDependents;
        dependencyStages.add(createStage(stageDependents));
      }

      if (dependents.nextSetBit(0) != -1) {
        throw new IllegalStateException("have dependents with dependency cycles" + dependents);
      }

      return createResolvedDependencies(dependencyStages);
    }
  }

  protected DependentItemMap<D> prioritize(DependentItemMap<D> dependentMap) {
    return dependentMap;
  }
}
