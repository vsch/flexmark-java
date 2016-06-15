package com.vladsch.flexmark.internal.util;

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
        if (dataSet.containsKey(key)) {
            return key.getValue(dataSet.get(key));
        } else {
            T newValue = key.getFactory().create(this);
            dataSet.put(key, newValue);
            return newValue;
        }
    }
}
