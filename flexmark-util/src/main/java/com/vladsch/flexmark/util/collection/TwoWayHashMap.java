package com.vladsch.flexmark.util.collection;

import java.util.HashMap;

public class TwoWayHashMap<F,S> {
    private HashMap<F, S> fToSMap = new HashMap<>();
    private HashMap<S, F> sToFMap = new HashMap<>();

    public void add(F f, S s) {
        fToSMap.put(f,s);
        sToFMap.put(s, f);
    }

    public S getSecond(F f) {
        return fToSMap.get(f);
    }

    public F getFirst(S s) {
        return sToFMap.get(s);
    }
}
