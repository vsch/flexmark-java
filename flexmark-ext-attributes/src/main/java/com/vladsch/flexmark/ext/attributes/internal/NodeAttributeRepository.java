package com.vladsch.flexmark.ext.attributes.internal;

import com.vladsch.flexmark.ext.attributes.AttributesExtension;
import com.vladsch.flexmark.ext.attributes.AttributesNode;
import com.vladsch.flexmark.util.ast.KeepType;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataKey;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@SuppressWarnings("WeakerAccess")
public class NodeAttributeRepository implements Map<Node, ArrayList<AttributesNode>> {
    protected final HashMap<Node, ArrayList<AttributesNode>> nodeAttributesHashMap = new HashMap<>();

    public NodeAttributeRepository(DataHolder options) {
    }

    public DataKey<NodeAttributeRepository> getDataKey() {
        return AttributesExtension.NODE_ATTRIBUTES;
    }

    public DataKey<KeepType> getKeepDataKey() {
        return AttributesExtension.ATTRIBUTES_KEEP;
    }

    @Override
    public int size() {
        return nodeAttributesHashMap.size();
    }

    @Override
    public boolean isEmpty() {
        return nodeAttributesHashMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return nodeAttributesHashMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return nodeAttributesHashMap.containsValue(value);
    }

    @Override
    public ArrayList<AttributesNode> get(Object key) {
        return nodeAttributesHashMap.get(key);
    }

    @Override
    public ArrayList<AttributesNode> put(Node key, ArrayList<AttributesNode> value) {
        return nodeAttributesHashMap.put(key, value);
    }

    public ArrayList<AttributesNode> put(Node key, AttributesNode value) {
        ArrayList<AttributesNode> another = nodeAttributesHashMap.get(key);
        if (another == null) {
            another = new ArrayList<>();
            nodeAttributesHashMap.put(key, another);
        }
        another.add(value);
        return another;
    }

    @Override
    public ArrayList<AttributesNode> remove(Object key) {
        return nodeAttributesHashMap.remove(key);
    }

    @Override
    public void putAll(@NotNull Map<? extends Node, ? extends ArrayList<AttributesNode>> m) {
        nodeAttributesHashMap.putAll(m);
    }

    @Override
    public void clear() {
        nodeAttributesHashMap.clear();
    }

    @NotNull
    @Override
    public Set<Node> keySet() {
        return nodeAttributesHashMap.keySet();
    }

    @NotNull
    @Override
    public Collection<ArrayList<AttributesNode>> values() {
        return nodeAttributesHashMap.values();
    }

    @NotNull
    @Override
    public Set<Entry<Node, ArrayList<AttributesNode>>> entrySet() {
        return nodeAttributesHashMap.entrySet();
    }
}
