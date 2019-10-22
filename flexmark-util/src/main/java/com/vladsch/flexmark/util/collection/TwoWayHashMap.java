package com.vladsch.flexmark.util.collection;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class TwoWayHashMap<F, S> {
    private @NotNull HashMap<F, S> fToSMap = new HashMap<>();
    private @NotNull HashMap<S, F> sToFMap = new HashMap<>();

    public void add(@Nullable F f, @Nullable S s) {
        fToSMap.put(f, s);
        sToFMap.put(s, f);
    }

    public @Nullable S getSecond(@Nullable F f) {
        return fToSMap.get(f);
    }

    public @Nullable F getFirst(@Nullable S s) {
        return sToFMap.get(s);
    }
}
