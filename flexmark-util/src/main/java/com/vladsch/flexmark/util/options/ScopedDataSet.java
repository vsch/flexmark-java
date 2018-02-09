package com.vladsch.flexmark.util.options;

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

    @Override
    public Map<DataKey, Object> getAll() {
        if (parent != null) {
            HashMap<DataKey, Object> all = new HashMap<DataKey, Object>();

            all.putAll(super.getAll());

            for (DataKey key : parent.keySet()) {
                if (!contains(key)) {
                    all.put(key, parent.get(key));
                }
            }

            return all;
        } else {
            return super.getAll();
        }
    }

    @Override
    public Collection<DataKey> keySet() {
        if (parent != null) {
            ArrayList<DataKey> all = new ArrayList<DataKey>();

            all.addAll(super.keySet());

            for (DataKey key : parent.keySet()) {
                if (!contains(key)) {
                    all.add(key);
                }
            }

            return all;
        } else {
            return super.keySet();
        }
    }

    @Override
    public boolean contains(DataKey key) {
        return super.contains(key) || (parent != null && parent.contains(key));
    }

    @Override
    public <T> T get(DataKey<T> key) {
        if (parent == null || super.contains(key) || !parent.contains(key)) {
            return super.get(key);
        } else {
            return parent.get(key);
        }
    }
}
