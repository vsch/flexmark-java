package com.vladsch.flexmark.internal.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class NodeRepository<T> implements Map<String, T> {
    final private Map<String, T> nodeMap = new HashMap<>();
    private ModifyBehavior modifyBehavior;

    public NodeRepository(ModifyBehavior modifyBehavior) {
        this.modifyBehavior = modifyBehavior;
    }

    public void setModifyBehavior(ModifyBehavior modifyBehavior) {
        if (this.modifyBehavior == ModifyBehavior.LOCKED) throw new IllegalStateException("Not allowed to modify LOCKED repository");
        //if (modifyBehavior != ModifyBehavior.FAIL_ON_MODIFY && modifyBehavior != ModifyBehavior.FAIL_ON_DUPLICATE) throw new IllegalStateException("Only allowed to modify non-empty repository modifyBehavior to FAIL_ON_MODIFY and FAIL_ON_DUPLICATE");
        this.modifyBehavior = modifyBehavior;
    }

    public abstract PropertyKey<? extends NodeRepository<T>> getPropertyKey();

    public String normalizeKey(CharSequence key) {
        return key.toString();
    }

    public T getFromRaw(CharSequence rawKey) {
        return nodeMap.get(normalizeKey(rawKey));
    }

    public T putRawKey(CharSequence key, T t) {
        return put(normalizeKey(key), t); 
    }

    @Override
    public T put(String s, T t) {
        if (modifyBehavior == ModifyBehavior.LOCKED) throw new IllegalStateException("Not allowed to modify LOCKED repository");
        if (modifyBehavior != ModifyBehavior.KEEP_LAST) {
            T another = nodeMap.get(s);
            if (another != null) {
                if (modifyBehavior == ModifyBehavior.FAIL_ON_DUPLICATE) throw new IllegalStateException("Duplicate key " + s);
                return another;
            }
        }
        return nodeMap.put(s, t);
    }

    @Override
    public void putAll(Map<? extends String, ? extends T> map) {
        if (modifyBehavior == ModifyBehavior.LOCKED) throw new IllegalStateException("Not allowed to modify LOCKED repository");
        if (modifyBehavior != ModifyBehavior.KEEP_LAST) {
            for (String key : map.keySet()) {
                nodeMap.put(key, map.get(key));
            }
        } else {
            nodeMap.putAll(map);
        }
    }

    @Override
    public T remove(Object o) {
        if (modifyBehavior == ModifyBehavior.LOCKED) throw new IllegalStateException("Not allowed to modify LOCKED repository");
        return nodeMap.remove(o);
    }

    @Override
    public void clear() {
        if (modifyBehavior == ModifyBehavior.LOCKED) throw new IllegalStateException("Not allowed to modify LOCKED repository");
        nodeMap.clear();
    }

    @Override
    public int size() {return nodeMap.size();}

    @Override
    public boolean isEmpty() {return nodeMap.isEmpty();}

    @Override
    public boolean containsKey(Object o) {return nodeMap.containsKey(o);}

    @Override
    public boolean containsValue(Object o) {return nodeMap.containsValue(o);}

    @Override
    public T get(Object o) {return nodeMap.get(o);}

    @Override
    public Set<String> keySet() {return nodeMap.keySet();}

    @Override
    public Collection<T> values() {return nodeMap.values();}

    @Override
    public Set<Entry<String, T>> entrySet() {return nodeMap.entrySet();}

    @Override
    public boolean equals(Object o) {return nodeMap.equals(o);}

    @Override
    public int hashCode() {return nodeMap.hashCode();}
}
