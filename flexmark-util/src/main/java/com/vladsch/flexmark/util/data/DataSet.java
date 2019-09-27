package com.vladsch.flexmark.util.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DataSet implements DataHolder {
    protected final HashMap<DataKey<?>, Object> dataSet;

    public DataSet() {
        this(null);
    }

    public DataSet(DataHolder other) {
        dataSet = other == null ? new HashMap<DataKey<?>, Object>() : new HashMap<DataKey<?>, Object>(other.getAll());
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
            return (T) dataSet.get(key);
        } else {
            return key.getDefaultValue(this);
        }
    }

    public static DataSet merge(DataHolder... dataHolders) {
        DataSet dataSet = new DataSet();
        for (DataHolder dataHolder : dataHolders) {
            if (dataHolder != null) dataSet.dataSet.putAll(dataHolder.getAll());
        }
        return dataSet;
    }

    @Override
    public MutableDataHolder toMutable() {
        return new MutableDataSet(this);
    }

    @Override
    public DataHolder toImmutable() {
        return this;
    }
}
