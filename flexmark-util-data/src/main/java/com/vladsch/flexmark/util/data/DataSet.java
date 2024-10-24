package com.vladsch.flexmark.util.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DataSet implements DataHolder {
  protected final Map<DataKeyBase<?>, Object> dataSet;

  public DataSet() {
    this(null);
  }

  DataSet(DataHolder other) {
    if (other == null) {
      dataSet = new HashMap<>();
    } else {
      dataSet = new HashMap<>(other.getAll());
    }
  }

  /**
   * aggregate actions of two data sets, actions not applied
   *
   * @param other first set of options
   * @param overrides overrides on options
   * @return resulting options where aggregate action keys were aggregated but not applied
   */
  public static DataHolder aggregateActions(DataHolder other, DataHolder overrides) {
    DataSet combined = new DataSet(other);
    combined.dataSet.putAll(overrides.getAll());

    for (DataKeyAggregator combiner : ourDataKeyAggregators) {
      combined = combiner.aggregateActions(combined, other, overrides).toDataSet();
    }
    return combined;
  }

  /**
   * Apply aggregate action to data and return result
   *
   * @return resulting data holder
   */
  private DataHolder aggregate() {
    DataHolder combined = this;
    for (DataKeyAggregator combiner : ourDataKeyAggregators) {
      combined = combiner.aggregate(combined);
    }
    return combined;
  }

  /**
   * Aggregate two sets of options by aggregating their aggregate action keys then applying those
   * actions on the resulting collection
   *
   * @param other options with aggregate actions already applied, no aggregate action keys are
   *     expected or checked
   * @param overrides overrides which may contain aggregate actions
   * @return resulting options with aggregate actions applied and removed from set
   */
  public static DataHolder aggregate(DataHolder other, DataHolder overrides) {
    if (other == null && overrides == null) {
      return new DataSet();
    } else if (overrides == null) {
      return other;
    } else if (other == null) {
      return overrides.toDataSet().aggregate().toImmutable();
    } else {
      return aggregateActions(other, overrides).toDataSet().aggregate().toImmutable();
    }
  }

  @Override
  public Map<? extends DataKeyBase<?>, Object> getAll() {
    return dataSet;
  }

  @Override
  public Collection<? extends DataKeyBase<?>> getKeys() {
    return dataSet.keySet();
  }

  @Override
  public boolean contains(DataKeyBase<?> key) {
    return dataSet.containsKey(key);
  }

  @Override
  public Object getOrCompute(DataKeyBase<?> key, DataValueFactory<?> factory) {
    if (dataSet.containsKey(key)) {
      return dataSet.get(key);
    }

    return factory.apply(this);
  }

  @Override
  public MutableDataSet toMutable() {
    return new MutableDataSet(this);
  }

  @Override
  public DataSet toImmutable() {
    return this;
  }

  @Override
  public DataSet toDataSet() {
    return this;
  }

  private static final List<DataKeyAggregator> ourDataKeyAggregators = new ArrayList<>();

  public static void registerDataKeyAggregator(DataKeyAggregator keyAggregator) {
    if (isAggregatorRegistered(keyAggregator)) {
      throw new IllegalStateException("Aggregator " + keyAggregator + " is already registered");
    }

    // find where in the list it should go so that all combiners
    for (int i = 0; i < ourDataKeyAggregators.size(); i++) {
      DataKeyAggregator aggregator = ourDataKeyAggregators.get(i);

      if (invokeSetContains(aggregator.invokeAfterSet(), keyAggregator)) {
        // this one needs to be invoked before
        if (invokeSetContains(keyAggregator.invokeAfterSet(), aggregator)) {
          throw new IllegalStateException(
              "Circular invokeAfter dependencies for " + keyAggregator + " and " + aggregator);
        }

        // add before this one
        ourDataKeyAggregators.add(i, keyAggregator);
        return;
      }
    }

    // add at the end
    ourDataKeyAggregators.add(keyAggregator);
  }

  private static boolean isAggregatorRegistered(DataKeyAggregator keyAggregator) {
    for (DataKeyAggregator aggregator : ourDataKeyAggregators) {
      if (aggregator.getClass() == keyAggregator.getClass()) {
        return true;
      }
    }
    return false;
  }

  private static boolean invokeSetContains(Set<Class<?>> invokeSet, DataKeyAggregator aggregator) {
    if (invokeSet == null) {
      return false;
    }
    return invokeSet.contains(aggregator.getClass());
  }

  @Override
  public String toString() {
    return "DataSet{" + "dataSet=" + dataSet + '}';
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (!(object instanceof DataSet)) {
      return false;
    }

    DataSet set = (DataSet) object;

    return dataSet.equals(set.dataSet);
  }

  @Override
  public int hashCode() {
    return dataSet.hashCode();
  }
}
