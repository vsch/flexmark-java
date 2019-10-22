package com.vladsch.flexmark.util.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MutableDataSet extends DataSet implements MutableDataHolder {
    public MutableDataSet() {
        super();
    }

    public MutableDataSet(DataHolder other) {
        super(other);
    }

    @NotNull
    @Override
    public <T> MutableDataSet set(@NotNull DataKey<T> key, @NotNull T value) {
        return set((DataKeyBase<T>) key, value);
    }

    @NotNull
    @Override
    public <T> MutableDataSet set(@NotNull NullableDataKey<T> key, @Nullable T value) {
        return set((DataKeyBase<T>) key, value);
    }

    private <T> MutableDataSet set(DataKeyBase<T> key, T value) {
        synchronized (dataSet) {
            dataSet.put(key, value);
        }
        return this;
    }

    @NotNull
    @Override
    public MutableDataSet setFrom(@NotNull MutableDataSetter dataSetter) {
        synchronized (dataSet) {
            dataSetter.setIn(this);
        }
        return this;
    }

    @NotNull
    @Override
    public MutableDataSet setAll(@NotNull DataHolder other) {
        synchronized (dataSet) {
            dataSet.putAll(other.getAll());
        }
        return this;
    }

    public static MutableDataSet merge(DataHolder... dataHolders) {
        MutableDataSet dataSet = new MutableDataSet();
        for (DataHolder dataHolder : dataHolders) {
            if (dataHolder != null) {
                dataSet.dataSet.putAll(dataHolder.getAll());
            }
        }
        return dataSet;
    }

    @NotNull
    @Override
    public MutableDataHolder setIn(@NotNull MutableDataHolder dataHolder) {
        synchronized (dataSet) {
            dataHolder.setAll(this);
        }
        return dataHolder;
    }

    @NotNull
    @Override
    public <T> MutableDataSet remove(@NotNull DataKeyBase<T> key) {
        synchronized (dataSet) {
            dataSet.remove(key);
        }
        return this;
    }

    @Override
    public @Nullable Object getOrCompute(@NotNull DataKeyBase<?> key, @NotNull DataValueFactory<?> factory) {
        synchronized (dataSet) {
            if (dataSet.containsKey(key)) {
                return dataSet.get(key);
            }

            Object value = factory.apply(this);
            dataSet.put(key,value);
            return value;
        }
    }

    @NotNull
    @Override
    public MutableDataSet toMutable() {
        return this;
    }

    @NotNull
    @Override
    public DataSet toImmutable() {
        synchronized (dataSet) {
            return new DataSet(this);
        }
    }

    @Override
    public @NotNull MutableDataSet toDataSet() {
        return this;
    }

    @NotNull
    @Override
    public MutableDataSet clear() {
        synchronized (dataSet) {
            dataSet.clear();
        }
        return this;
    }
}
