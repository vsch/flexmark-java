package com.vladsch.flexmark.util.ast;

import com.vladsch.flexmark.util.data.DataKey;

import java.util.*;
import java.util.function.Consumer;

public abstract class NodeRepository<T> implements Map<String, T> {
    protected final ArrayList<T> nodeList = new ArrayList<T>();
    protected final Map<String, T> nodeMap = new HashMap<String, T>();
    protected final KeepType keepType;

    public abstract DataKey<? extends NodeRepository<T>> getDataKey();
    public abstract DataKey<KeepType> getKeepDataKey();

    // function implementing extraction of referenced elements by given node or its children
    public abstract Set<T> getReferencedElements(Node parent);

    protected void visitNodes(Node parent, Consumer<Node> runnable, Class<? extends Node>... classes) {
        ArrayList<VisitHandler<?>> handlers = new ArrayList<>();
        NodeVisitor visitor = new NodeVisitor();
        for (Class<? extends Node> clazz : classes) {
            visitor.addHandler(new VisitHandler<>(clazz, runnable::accept));
        }
        visitor.visit(parent);
    }

    public NodeRepository(KeepType keepType) {
        this.keepType = keepType == null ? KeepType.LOCKED : keepType;
    }

    public String normalizeKey(CharSequence key) {
        return key.toString();
    }

    public T getFromRaw(CharSequence rawKey) {
        return nodeMap.get(normalizeKey(rawKey));
    }

    public T putRawKey(CharSequence key, T t) {
        return put(normalizeKey(key), t);
    }

    public Collection<T> getValues() {
        return nodeMap.values();
    }

    public static <T> boolean transferReferences(NodeRepository<T> destination, NodeRepository<T> included, boolean onlyIfUndefined, Map<String, String> referenceIdMap) {
        // copy references but only if they are not defined in the original document
        boolean transferred = false;
        for (Map.Entry<String, T> entry : included.entrySet()) {
            String key = entry.getKey();

            // map as requested
            if (referenceIdMap != null) referenceIdMap.getOrDefault(key, key);

            if (!onlyIfUndefined || !destination.containsKey(key)) {
                destination.put(key, entry.getValue());
                transferred = true;
            }
        }
        return transferred;
    }

    @Override
    public T put(String s, T t) {
        nodeList.add(t);

        if (keepType == KeepType.LOCKED) throw new IllegalStateException("Not allowed to modify LOCKED repository");
        if (keepType != KeepType.LAST) {
            T another = nodeMap.get(s);
            if (another != null) {
                if (keepType == KeepType.FAIL) throw new IllegalStateException("Duplicate key " + s);
                return another;
            }
        }
        return nodeMap.put(s, t);
    }

    @Override
    public void putAll(Map<? extends String, ? extends T> map) {
        if (keepType == KeepType.LOCKED) throw new IllegalStateException("Not allowed to modify LOCKED repository");
        if (keepType != KeepType.LAST) {
            for (String key : map.keySet()) {
                nodeMap.put(key, map.get(key));
            }
        } else {
            nodeMap.putAll(map);
        }
    }

    @Override
    public T remove(Object o) {
        if (keepType == KeepType.LOCKED) throw new IllegalStateException("Not allowed to modify LOCKED repository");
        return nodeMap.remove(o);
    }

    @Override
    public void clear() {
        if (keepType == KeepType.LOCKED) throw new IllegalStateException("Not allowed to modify LOCKED repository");
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
    public List<T> values() {return nodeList;}

    @Override
    public Set<Entry<String, T>> entrySet() {return nodeMap.entrySet();}

    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override
    public boolean equals(Object o) { return nodeMap.equals(o); }

    @Override
    public int hashCode() {return nodeMap.hashCode();}
}
