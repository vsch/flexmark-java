package com.vladsch.flexmark.util.options;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DataSet implements DataHolder {
    protected final HashMap<DataKey, Object> dataSet;

    public DataSet() {
        dataSet = new HashMap<DataKey, Object>();
    }

    public DataSet(DataHolder other) {
        dataSet = new HashMap<DataKey, Object>();
        dataSet.putAll(other.getAll());
    }

    @Override
    public Map<DataKey, Object> getAll() {
        return dataSet;
    }

    @Override
    public Collection<DataKey> keySet() {
        return dataSet.keySet();
    }

    @Override
    public boolean contains(DataKey key) {
        return dataSet.containsKey(key);
    }

    @Override
    public <T> T get(DataKey<T> key) {
        if (dataSet.containsKey(key)) {
            return key.getValue(dataSet.get(key));
        } else {
            return key.getDefaultValue(this);
        }
    }

    public static DataSet merge(DataHolder...dataHolders) {
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
