package com.vladsch.flexmark.util.options;

import com.vladsch.flexmark.util.collection.DataValueFactory;

public class MutableDataSet extends DataSet implements MutableDataHolder {
    public MutableDataSet() {
        super();
    }

    public MutableDataSet(DataHolder other) {
        super(other);
    }

    @Override
    public <T> MutableDataSet set(DataKey<? extends T> key, T value) {
        dataSet.put(key, value);
        return this;
    }

    @Override
    public MutableDataHolder setFrom(MutableDataSetter dataSetter) {
        return dataSetter.setIn(this);
    }

    @Override
    public MutableDataSet setAll(DataHolder other) {
        for (DataKey key : other.keySet()) {
            set(key, other.get(key));
        }
        return this;
    }

    @Override
    public MutableDataHolder setIn(final MutableDataHolder dataHolder) {
        dataHolder.setAll(this);
        return dataHolder;
    }

    @Override
    public <T> T get(DataKey<T> key) {
        return getOrCompute(key, key.getFactory());
    }

    @Override
    public <T> MutableDataSet remove(DataKey<T> key) {
        dataSet.remove(key);
        return this;
    }

    @Override
    public <T> T getOrCompute(DataKey<T> key, DataValueFactory<T> factory) {
        if (dataSet.containsKey(key)) {
            return key.getValue(dataSet.get(key));
        } else {
            T newValue = factory.create(this);
            dataSet.put(key, newValue);
            return newValue;
        }
    }

    public static MutableDataSet merge(DataHolder...dataHolders) {
        MutableDataSet dataSet = new MutableDataSet();
        for (DataHolder dataHolder : dataHolders) {
            if (dataHolder != null) dataSet.dataSet.putAll(dataHolder.getAll());
        }
        return dataSet;
    }

    @Override
    public MutableDataSet toMutable() {
        return this;
    }

    @Override
    public DataHolder toImmutable() {
        return new DataSet(this);
    }

    @Override
    public MutableDataHolder clear() {
        dataSet.clear();
        return this;
    }
}
