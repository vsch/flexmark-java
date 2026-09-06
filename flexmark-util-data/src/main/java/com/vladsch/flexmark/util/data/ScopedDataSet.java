package com.vladsch.flexmark.util.data;

import org.jspecify.annotations.Nullable;

import java.util.*;

public class ScopedDataSet extends DataSet {
    protected final DataHolder parent;

    public ScopedDataSet(@Nullable DataHolder parent) {
        super();
        this.parent = parent;
    }

    public ScopedDataSet(@Nullable DataHolder parent, @Nullable DataHolder other) {
        super(other);
        this.parent = parent;
    }

    public DataHolder getParent() {
        return parent;
    }

    @Override
    public Map<? extends DataKeyBase<?>, Object> getAll() {
        if (parent != null) {
            HashMap<DataKeyBase<?>, Object> all = new HashMap<>(parent.getAll());
            all.putAll(super.getAll());
            return all;
        } else {
            return super.getAll();
        }
    }

    @Override
    public Collection<? extends DataKeyBase<?>> getKeys() {
        if (parent != null) {
            HashSet<DataKeyBase<?>> all = new HashSet<>(parent.getKeys());
            all.addAll(super.getKeys());
            return all;
        } else {
            return super.getKeys();
        }
    }

    @Override
    public MutableDataSet toMutable() {
        MutableDataSet mutableDataSet = new MutableDataSet();
        mutableDataSet.dataSet.putAll(super.getAll());
        return parent != null ? new MutableScopedDataSet(parent, mutableDataSet) : mutableDataSet;
    }

    @Override
    public boolean contains(DataKeyBase<?> key) {
        return super.contains(key) || (parent != null && parent.contains(key));
    }

    @Override
    public @Nullable Object getOrCompute(DataKeyBase<?> key, DataValueFactory<?> factory) {
        if (parent == null || super.contains(key) || !parent.contains(key)) {
            return super.getOrCompute(key, factory);
        } else {
            return parent.getOrCompute(key, factory);
        }
    }
}
