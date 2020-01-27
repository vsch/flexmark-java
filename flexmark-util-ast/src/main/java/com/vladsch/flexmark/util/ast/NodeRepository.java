package com.vladsch.flexmark.util.ast;

import com.vladsch.flexmark.util.data.DataKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;

public abstract class NodeRepository<T> implements Map<String, T> {
    protected final ArrayList<T> nodeList = new ArrayList<>();
    protected final Map<String, T> nodeMap = new HashMap<>();
    protected final KeepType keepType;

    public abstract @NotNull DataKey<? extends NodeRepository<T>> getDataKey();
    public abstract @NotNull DataKey<KeepType> getKeepDataKey();

    // function implementing extraction of referenced elements by given node or its children
    public abstract @NotNull Set<T> getReferencedElements(Node parent);

    @SafeVarargs
    protected final void visitNodes(@NotNull Node parent, @NotNull Consumer<Node> runnable, @NotNull Class<? extends Node>... classes) {
        NodeVisitor visitor = new NodeVisitor();
        for (Class<? extends Node> clazz : classes) {
            visitor.addHandler(new VisitHandler<>(clazz, runnable::accept));
        }
        visitor.visit(parent);
    }

    public NodeRepository(@Nullable KeepType keepType) {
        this.keepType = keepType == null ? KeepType.LOCKED : keepType;
    }

    public @NotNull String normalizeKey(@NotNull CharSequence key) {
        return key.toString();
    }

    public @Nullable T getFromRaw(@NotNull CharSequence rawKey) {
        return nodeMap.get(normalizeKey(rawKey));
    }

    public @Nullable T putRawKey(@NotNull CharSequence key, @NotNull T t) {
        return put(normalizeKey(key), t);
    }

    public @NotNull Collection<T> getValues() {
        return nodeMap.values();
    }

    public static <T> boolean transferReferences(@NotNull NodeRepository<T> destination, @NotNull NodeRepository<T> included, boolean onlyIfUndefined, @Nullable Map<String, String> referenceIdMap) {
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
    public @Nullable T put(@NotNull String s, @NotNull T t) {
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
    public void putAll(@NotNull Map<? extends String, ? extends T> map) {
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
    public @Nullable T remove(@NotNull Object o) {
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
    public boolean containsKey(@NotNull Object o) {return nodeMap.containsKey(o);}

    @Override
    public boolean containsValue(Object o) {return nodeMap.containsValue(o);}

    @Override
    public @Nullable T get(@NotNull Object o) {return nodeMap.get(o);}

    @NotNull
    @Override
    public Set<String> keySet() {return nodeMap.keySet();}

    @NotNull
    @Override
    public List<T> values() {return nodeList;}

    @NotNull
    @Override
    public Set<Entry<String, T>> entrySet() {return nodeMap.entrySet();}

    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override
    public boolean equals(Object o) { return nodeMap.equals(o); }

    @Override
    public int hashCode() {return nodeMap.hashCode();}
}
