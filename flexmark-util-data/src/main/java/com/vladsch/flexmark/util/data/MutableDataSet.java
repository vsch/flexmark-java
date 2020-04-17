package com.vladsch.flexmark.util.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MutableDataSet extends DataSet implements MutableDataHolder {
    public MutableDataSet() {
        super();
    }

    public MutableDataSet(@Nullable DataHolder other) {
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

    private <T> MutableDataSet set(@NotNull DataKeyBase<T> key, T value) {
        dataSet.put(key, value);
        return this;
    }

    @NotNull
    @Override
    public MutableDataSet setFrom(@NotNull MutableDataSetter dataSetter) {
        dataSetter.setIn(this);
        return this;
    }

    @NotNull
    @Override
    public MutableDataSet setAll(@NotNull DataHolder other) {
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

    @NotNull
    @Override
    public MutableDataSet remove(@NotNull DataKeyBase<?> key) {
        dataSet.remove(key);
        return this;
    }

    @Override
    public @Nullable Object getOrCompute(@NotNull DataKeyBase<?> key, @NotNull DataValueFactory<?> factory) {
        if (dataSet.containsKey(key)) {
            return dataSet.get(key);
        } else {
            Object value = factory.apply(this);
            dataSet.put(key, value);
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
        return new DataSet(this);
    }

    @Override
    public @NotNull MutableDataSet toDataSet() {
        return this;
    }

    @NotNull
    @Override
    public MutableDataSet clear() {
        dataSet.clear();
        return this;
    }
}
