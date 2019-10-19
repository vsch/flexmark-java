package com.vladsch.flexmark.util.data;

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
    public MutableDataSet setFrom(MutableDataSetter dataSetter) {
        dataSetter.setIn(this);
        return this;
    }

    @Override
    public MutableDataSet setAll(DataHolder other) {
        DataSet original = new DataSet(this); //keep a copy in case we have aggregateAction keys which other also has
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

    @Override
    public MutableDataSet toMutable() {
        return this;
    }

    @Override
    public DataSet toImmutable() {
        return new DataSet(this);
    }

    @Override
    public MutableDataSet clear() {
        dataSet.clear();
        return this;
    }
}
