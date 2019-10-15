package com.vladsch.flexmark.util.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class DataSet implements DataHolder {
    protected final HashMap<DataKey<?>, Object> dataSet;
    protected int consumerCount;

    public DataSet() {
        this(null);
    }

    public DataSet(DataHolder other) {
        dataSet = other == null ? new HashMap<>() : new HashMap<>(other.getAll());
        consumerCount = other == null ? 0 : other.getConsumerDataKeys();
    }

    public int getConsumerDataKeys() {
        return consumerCount;
    }

    public DataSet(DataHolder other, DataHolder overrides) {
        if (other == null && overrides == null) {
            dataSet = new HashMap<>();
            consumerCount = 0;
        } else if (other == null) {
            dataSet = new HashMap<>(overrides.getAll());
            consumerCount = overrides.getConsumerDataKeys();
        } else if (overrides == null) {
            dataSet = new HashMap<>(other.getAll());
            consumerCount = other.getConsumerDataKeys();
        } else {
            dataSet = new HashMap<>(other.getAll());
            consumerCount = other.getConsumerDataKeys();
            dataSet.putAll(overrides.getAll());

            if (consumerCount == 0) {
                consumerCount = overrides.getConsumerDataKeys();
            } else if (overrides.getConsumerDataKeys() > 0) {
                // need to scan to see if they overlap, if not increment consumer count
                for (DataKey<?> dataKey : overrides.getKeys()) {
                    if (dataKey.getDefaultValue(null) instanceof Consumer<?>) {
                        if (!other.contains(dataKey)) {
                            consumerCount++;
                        }
                    }
                }
            }
        }
    }

    @Override
    public Map<DataKey<?>, Object> getAll() {
        return dataSet;
    }

    @Override
    public Collection<DataKey<?>> getKeys() {
        return dataSet.keySet();
    }

    @Override
    public boolean contains(DataKey<?> key) {
        return dataSet.containsKey(key);
    }

    @Override
    public <T> T get(DataKey<T> key) {
        if (dataSet.containsKey(key)) {
            //noinspection unchecked
            return (T) dataSet.get(key);
        } else {
            return key.getDefaultValue(this);
        }
    }

    public static DataSet merge(DataHolder... dataHolders) {
        return MutableDataSet.merge(dataHolders).toImmutable();
    }

    @Override
    public MutableDataSet toMutable() {
        return new MutableDataSet(this);
    }

    @Override
    public DataHolder toImmutable() {
        return this;
    }
}
