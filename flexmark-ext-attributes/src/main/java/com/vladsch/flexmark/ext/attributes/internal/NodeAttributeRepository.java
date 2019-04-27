package com.vladsch.flexmark.ext.attributes.internal;

import com.vladsch.flexmark.ext.attributes.AttributesExtension;
import com.vladsch.flexmark.ext.attributes.AttributesNode;
import com.vladsch.flexmark.util.ast.KeepType;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataKey;

import java.util.*;

@SuppressWarnings("WeakerAccess")
public class NodeAttributeRepository implements Map<Node, ArrayList<AttributesNode>> {
    protected final HashMap<Node, ArrayList<AttributesNode>> nodeAttributesHashMap = new HashMap<Node, ArrayList<AttributesNode>>();

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
    public boolean containsKey(final Object key) {
        return nodeAttributesHashMap.containsKey(key);
    }

    @Override
    public boolean containsValue(final Object value) {
        return nodeAttributesHashMap.containsValue(value);
    }

    @Override
    public ArrayList<AttributesNode> get(final Object key) {
        return nodeAttributesHashMap.get(key);
    }

    @Override
    public ArrayList<AttributesNode> put(final Node key, final ArrayList<AttributesNode> value) {
        return nodeAttributesHashMap.put(key, value);
    }

    public ArrayList<AttributesNode> put(final Node key, final AttributesNode value) {
        ArrayList<AttributesNode> another = nodeAttributesHashMap.get(key);
        if (another == null) {
            another = new ArrayList<AttributesNode>();
            nodeAttributesHashMap.put(key, another);
        }
        another.add(value);
        return another;
    }

    @Override
    public ArrayList<AttributesNode> remove(final Object key) {
        return nodeAttributesHashMap.remove(key);
    }

    @Override
    public void putAll(final Map<? extends Node, ? extends ArrayList<AttributesNode>> m) {
        nodeAttributesHashMap.putAll(m);
    }

    @Override
    public void clear() {
        nodeAttributesHashMap.clear();
    }

    @Override
    public Set<Node> keySet() {
        return nodeAttributesHashMap.keySet();
    }

    @Override
    public Collection<ArrayList<AttributesNode>> values() {
        return nodeAttributesHashMap.values();
    }

    @Override
    public Set<Entry<Node, ArrayList<AttributesNode>>> entrySet() {
        return nodeAttributesHashMap.entrySet();
    }
}
