package com.vladsch.flexmark.node;

import com.vladsch.flexmark.internal.util.BasedSequence;
import com.vladsch.flexmark.internal.util.collection.*;

import java.util.Collection;
import java.util.Map;

public class Document extends Block implements MutableDataHolder {
    final private MutableDataSet dataSet;

    @Override
    public BasedSequence[] getSegments() {
        return EMPTY_SEGMENTS;
    }

    public Document(DataHolder options, BasedSequence chars) {
        super(chars);
        dataSet = new MutableDataSet(options);
    }

    @Override
    public Map<DataKey, Object> getAll() {return dataSet.getAll();}

    @Override
    public Collection<DataKey> keySet() { return dataSet.keySet(); }

    @Override
    public boolean contains(DataKey key) {return dataSet.contains(key);}

    @Override
    public <T> T get(DataKey<T> key) {
        return dataSet.get(key);
    }

    @Override
    public <T> T getOrCompute(DataKey<T> key, DataValueFactory<T> factory) { return dataSet.getOrCompute(key, factory); }

    @Override
    public <T> MutableDataHolder set(DataKey<T> key, T value) { return dataSet.set(key, value);}

    @Override
    public void setAll(DataHolder other) {dataSet.setAll(other);}

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
