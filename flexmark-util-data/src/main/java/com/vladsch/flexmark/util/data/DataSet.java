package com.vladsch.flexmark.util.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class DataSet implements DataHolder {
    protected final HashMap<DataKeyBase<?>, Object> dataSet;

    public DataSet() {
        this(null);
    }

    public DataSet(@Nullable DataHolder other) {
        if (other == null) dataSet = new HashMap<>();
        else dataSet = new HashMap<>(other.getAll());
    }

    /**
     * aggregate actions of two data sets, actions not applied
     *
     * @param other     first set of options
     * @param overrides overrides on options
     * @return resulting options where aggregate action keys were aggregated but not applied
     */
    @NotNull
    public static DataHolder aggregateActions(@NotNull DataHolder other, @NotNull DataHolder overrides) {
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
    @NotNull
    public DataHolder aggregate() {
        DataHolder combined = this;
        for (DataKeyAggregator combiner : ourDataKeyAggregators) {
            combined = combiner.aggregate(combined);
        }
        return combined;
    }

    /**
     * Aggregate two sets of options by aggregating their aggregate action keys then applying those actions on the resulting collection
     *
     * @param other     options with aggregate actions already applied, no aggregate action keys are expected or checked
     * @param overrides overrides which may contain aggregate actions
     * @return resulting options with aggregate actions applied and removed from set
     */
    @NotNull
    public static DataHolder aggregate(@Nullable DataHolder other, @Nullable DataHolder overrides) {
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
    public @NotNull Map<? extends DataKeyBase<?>, Object> getAll() {
        return dataSet;
    }

    @Override
    public @NotNull Collection<? extends DataKeyBase<?>> getKeys() {
        return dataSet.keySet();
    }

    @Override
    public boolean contains(@NotNull DataKeyBase<?> key) {
        return dataSet.containsKey(key);
    }

    @Override
    public @Nullable Object getOrCompute(@NotNull DataKeyBase<?> key, @NotNull DataValueFactory<?> factory) {
        if (dataSet.containsKey(key)) {
            return dataSet.get(key);
        } else {
            return factory.apply(this);
        }
    }

    @NotNull
    public static DataSet merge(@NotNull DataHolder... dataHolders) {
        DataSet dataSet = new DataSet();
        for (DataHolder dataHolder : dataHolders) {
            dataSet.dataSet.putAll(dataHolder.getAll());
        }
        return dataSet;
    }

    @NotNull
    @Override
    public MutableDataSet toMutable() {
        return new MutableDataSet(this);
    }

    @NotNull
    @Override
    public DataSet toImmutable() {
        return this;
    }

    @Override
    public @NotNull DataSet toDataSet() {
        return this;
    }

    final private static ArrayList<DataKeyAggregator> ourDataKeyAggregators = new ArrayList<>();

    public static void registerDataKeyAggregator(@NotNull DataKeyAggregator keyAggregator) {
        if (isAggregatorRegistered(keyAggregator)) {
            throw new IllegalStateException("Aggregator " + keyAggregator + " is already registered");
        }

        // find where in the list it should go so that all combiners
        for (int i = 0; i < ourDataKeyAggregators.size(); i++) {
            DataKeyAggregator aggregator = ourDataKeyAggregators.get(i);

            if (invokeSetContains(aggregator.invokeAfterSet(), keyAggregator)) {
                // this one needs to be invoked before
                if (invokeSetContains(keyAggregator.invokeAfterSet(), aggregator)) {
                    throw new IllegalStateException("Circular invokeAfter dependencies for " + keyAggregator + " and " + aggregator);
                }

                // add before this one
                ourDataKeyAggregators.add(i, keyAggregator);
                return;
            }
        }

        // add at the end
        ourDataKeyAggregators.add(keyAggregator);
    }

    static boolean isAggregatorRegistered(@NotNull DataKeyAggregator keyAggregator) {
        for (DataKeyAggregator aggregator : ourDataKeyAggregators) {
            if (aggregator.getClass() == keyAggregator.getClass()) return true;
        }
        return false;
    }

    static boolean invokeSetContains(@Nullable Set<Class<?>> invokeSet, @NotNull DataKeyAggregator aggregator) {
        if (invokeSet == null) return false;
        return invokeSet.contains(aggregator.getClass());
    }

    @Override
    public String toString() {
        return "DataSet{" +
                "dataSet=" + dataSet +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataSet)) return false;

        DataSet set = (DataSet) o;

        return dataSet.equals(set.dataSet);
    }

    @Override
    public int hashCode() {
        return dataSet.hashCode();
    }
}
