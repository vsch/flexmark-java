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

    @Override
    public <T> MutableDataSet set(DataKey<T> key, @NotNull T value) {
        return set((DataKeyBase<T>) key, value);
    }

    @Override
    public <T> MutableDataSet set(NullableDataKey<T> key, @Nullable T value) {
        return set((DataKeyBase<T>) key, value);
    }

    private <T> MutableDataSet set(DataKeyBase<T> key, T value) {
        dataSet.put(key, value);
        return this;
    }

    @Override
    public MutableDataSet setFrom(MutableDataSetter dataSetter) {
        dataSetter.setIn(this);
        return this;
    }

    @Override
    public MutableDataSet setAll(DataHolder other) {
        dataSet.putAll(other.getAll());
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
        dataHolder.setAll(this);
        return dataHolder;
    }

    @Override
    public <T> MutableDataSet remove(DataKeyBase<T> key) {
        dataSet.remove(key);
        return this;
    }

    @Override
    public @Nullable Object getOrCompute(@NotNull DataKeyBase<?> key, @NotNull DataValueFactory<?> factory) {
        return dataSet.computeIfAbsent(key, k -> factory.apply(this));
    }

    @NotNull
    @Override
    public MutableDataSet toMutable() {
        return this;
    }

    @NotNull
    @Override
    public DataSet toImmutable() {
        return new DataSet(this);
    }

    @Override
    public @NotNull MutableDataSet toDataSet() {
        return this;
    }

    @Override
    public MutableDataSet clear() {
        dataSet.clear();
        return this;
    }
}
