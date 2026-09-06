package com.vladsch.flexmark.util.data;

import org.jspecify.annotations.Nullable;

public class MutableDataSet extends DataSet implements MutableDataHolder {
    public MutableDataSet() {
        super();
    }

    public MutableDataSet(@Nullable DataHolder other) {
        super(other);
    }

    @Override
    public <T> MutableDataSet set(DataKey<T> key, T value) {
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

    @Override
    public MutableDataHolder setIn(MutableDataHolder dataHolder) {
        dataHolder.setAll(this);
        return dataHolder;
    }

    @Override
    public MutableDataSet remove(DataKeyBase<?> key) {
        dataSet.remove(key);
        return this;
    }

    @Override
    public @Nullable Object getOrCompute(DataKeyBase<?> key, DataValueFactory<?> factory) {
        if (dataSet.containsKey(key)) {
            return dataSet.get(key);
        } else {
            Object value = factory.apply(this);
            dataSet.put(key, value);
            return value;
        }
    }

    @Override
    public MutableDataSet toMutable() {
        return this;
    }

    @Override
    public DataSet toImmutable() {
        return new DataSet(this);
    }

    @Override
    public MutableDataSet toDataSet() {
        return this;
    }

    @Override
    public MutableDataSet clear() {
        dataSet.clear();
        return this;
    }
}
