package com.vladsch.flexmark.internal.util.options;

import com.vladsch.flexmark.internal.util.collection.DataValueFactory;
import org.jetbrains.annotations.NotNull;

public class MutableDataSet extends DataSet implements MutableDataHolder {
    public MutableDataSet() {
        super();
    }

    public MutableDataSet(DataHolder other) {
        super(other);
    }

    @Override
    public <T> MutableDataSet set(DataKey<T> key, T value) {
        dataSet.put(key, value);
        return this;
    }

    @Override
    public void setAll(DataHolder other) {
        for (DataKey key : other.keySet()) {
            set(key, other.get(key));
        }
    }

    @Override
    public <T> T get(DataKey<T> key) {
        return getOrCompute(key, key.getFactory());
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
    
    @NotNull
    @Override
    public MutableDataHolder toMutable() {
        return this;
    }

    @NotNull
    @Override
    public DataHolder toImmutable() {
        return new DataSet(this);
    }

    @Override
    public void clear() {
        dataSet.clear();
    }
}
