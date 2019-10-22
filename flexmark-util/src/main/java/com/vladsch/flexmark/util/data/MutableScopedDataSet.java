package com.vladsch.flexmark.util.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MutableScopedDataSet extends MutableDataSet {
    protected final DataHolder parent;

    public MutableScopedDataSet(DataHolder parent) {
        super();
        this.parent = parent;
    }

    public MutableScopedDataSet(DataHolder parent, MutableDataHolder other) {
        super(other);
        this.parent = parent;
    }

    public DataHolder getParent() {
        return parent;
    }

    @Override
    public @NotNull Map<? extends DataKeyBase<?>, Object> getAll() {
        if (parent != null) {
            HashMap<DataKeyBase<?>, Object> all = new HashMap<>(super.getAll());
            for (DataKeyBase<?> key : parent.getKeys()) {
                if (!contains(key)) {
                    all.put(key, key.get(parent));
                }
            }

            return all;
        } else {
            return super.getAll();
        }
    }

    @Override
    public @NotNull Collection<? extends DataKeyBase<?>> getKeys() {
        if (parent != null) {
            ArrayList<DataKeyBase<?>> all = new ArrayList<>(super.getKeys());
            for (DataKeyBase<?> key : parent.getKeys()) {
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
    public boolean contains(@NotNull DataKeyBase<?> key) {
        return super.contains(key) || (parent != null && parent.contains(key));
    }

    @Override
    public @Nullable Object getOrCompute(@NotNull DataKeyBase<?> key, @NotNull DataValueFactory<?> factory) {
        if (parent == null || super.contains(key) || !parent.contains(key)) {
            return super.getOrCompute(key, factory);
        } else {
            return parent.getOrCompute(key, factory);
        }
    }
}
