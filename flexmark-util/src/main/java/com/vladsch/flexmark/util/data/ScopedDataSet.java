package com.vladsch.flexmark.util.data;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ScopedDataSet extends DataSet {
    protected final DataHolder parent;

    public ScopedDataSet(DataHolder parent) {
        super();
        this.parent = parent;
    }

    public ScopedDataSet(DataHolder parent, DataHolder other) {
        super(other);
        this.parent = parent;
    }

    public DataHolder getParent() {
        return parent;
    }

    @NotNull
    @Override
    public Map<DataKey<?>, Object> getAll() {
        if (parent != null) {

            HashMap<DataKey<?>, Object> all = new HashMap<>(super.getAll());

            for (DataKey<?> key : parent.getKeys()) {
                if (!contains(key)) {
                    all.put(key, parent.get(key));
                }
            }

            return all;
        } else {
            return super.getAll();
        }
    }

    @NotNull
    @Override
    public Collection<DataKey<?>> getKeys() {
        if (parent != null) {

            ArrayList<DataKey<?>> all = new ArrayList<>(super.getKeys());

            for (DataKey<?> key : parent.getKeys()) {
                if (!contains(key)) {
                    all.add(key);
                }
            }

            return all;
        } else {
            return super.getKeys();
        }
    }

    @Override
    public boolean contains(@NotNull DataKey<?> key) {
        return super.contains(key) || (parent != null && parent.contains(key));
    }

    @Override
    public <T> T get(@NotNull DataKey<T> key) {
        if (parent == null || super.contains(key) || !parent.contains(key)) {
            return super.get(key);
        } else {
            return parent.get(key);
        }
    }
}
