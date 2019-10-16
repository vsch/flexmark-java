package com.vladsch.flexmark.util.data;

public class MutableDataSet extends DataSet implements MutableDataHolder {
    public MutableDataSet() {
        super();
    }

    public MutableDataSet(DataHolder other) {
        super(other);
    }

    public MutableDataSet(DataHolder other, DataHolder overrides) {
        super(other, overrides);
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
        for (DataKey<?> key : other.getKeys()) {
            set(key, other.get(key));
        }
        return this;
    }

    @Override
    public MutableDataHolder setIn(MutableDataHolder dataHolder) {
        dataHolder.setAll(this);
        return dataHolder;
    }

    @Override
    public <T> T get(DataKey<T> key) {
        return getOrCompute(key);
    }

    @Override
    public <T> MutableDataSet remove(DataKey<T> key) {
        dataSet.remove(key);
        return this;
    }

    @Override
    public <T> T getOrCompute(DataKey<T> key) {
        if (dataSet.containsKey(key)) {
            //noinspection unchecked
            return (T) dataSet.get(key);
        } else {
            T newValue = key.getDefaultValue(this);
            dataSet.put(key, newValue);
            return newValue;
        }
    }

    public static MutableDataSet merge(DataHolder... dataHolders) {
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
