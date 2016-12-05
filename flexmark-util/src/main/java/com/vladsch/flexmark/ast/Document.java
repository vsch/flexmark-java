package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.collection.DataValueFactory;
import com.vladsch.flexmark.util.options.*;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.Collection;
import java.util.Map;

public class Document extends Block implements MutableDataHolder {
    private final MutableDataSet dataSet;

    @Override
    public BasedSequence[] getSegments() {
        return EMPTY_SEGMENTS;
    }

    public Document(DataHolder options, BasedSequence chars) {
        super(chars);
        dataSet = new MutableDataSet(options);
    }

    @Override
    public Map<DataKey, Object> getAll() { return dataSet.getAll(); }

    @Override
    public Collection<DataKey> keySet() { return dataSet.keySet(); }

    @Override
    public boolean contains(DataKey key) { return dataSet.contains(key); }

    @Override
    public <T> T get(DataKey<T> key) {
        return dataSet.get(key);
    }

    @Override
    public <T> T getOrCompute(DataKey<T> key, DataValueFactory<T> factory) { return dataSet.getOrCompute(key, factory); }

    @Override
    public <T> MutableDataHolder set(DataKey<T> key, T value) { return dataSet.set(key, value);}

    @Override
    public MutableDataHolder setFrom(MutableDataSetter dataSetter) { return dataSet.setFrom(dataSetter); }

    @Override
    public void setAll(DataHolder other) { dataSet.setAll(other); }

    @Override
    public MutableDataHolder toMutable() { return dataSet.toMutable(); }

    @Override
    public DataHolder toImmutable() { return dataSet.toImmutable(); }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }
}
