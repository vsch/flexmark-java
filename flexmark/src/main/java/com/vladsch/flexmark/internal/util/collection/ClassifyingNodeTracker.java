package com.vladsch.flexmark.internal.util.collection;

import com.vladsch.flexmark.internal.util.NodeTracker;
import com.vladsch.flexmark.internal.util.collection.iteration.ReversibleIterable;
import com.vladsch.flexmark.internal.util.collection.iteration.ReversiblePeekingIterable;
import com.vladsch.flexmark.internal.util.mappers.NodeClassifier;
import com.vladsch.flexmark.node.Node;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ClassifyingNodeTracker implements NodeTracker {
    final protected ClassificationBag<Class<?>, Node> myNodeClassifier;
    final private NodeTracker myHost;
    final private OrderedMap<Class<?>, Set<Class<?>>> myExclusionMap;
    final private OrderedSet<Class<?>> myExclusionSet;
    final private HashMap<Integer, BitSet> myNodeAncestryMap;

    public ClassifyingNodeTracker(NodeTracker host, Map<Class<? extends Node>, Set<Class<?>>> exclusionMap) {
        myHost = host;
        myNodeClassifier = new ClassificationBag<>(NodeClassifier.INSTANCE);
        myExclusionMap = new OrderedMap<>(exclusionMap.size());
        myExclusionMap.putAll(exclusionMap);
        
        // this maps the exclusion class to bits in the bit set 
        myExclusionSet = new OrderedSet<>();
        myExclusionMap.valueIterable().forEach(myExclusionSet::addAll);

        myNodeAncestryMap = new HashMap<>();
    }

    public OrderedMap<Class<?>, Set<Class<?>>> getExclusionMap() {
        return myExclusionMap;
    }

    public HashMap<Integer, BitSet> getNodeAncestryMap() {
        return myNodeAncestryMap;
    }

    public OrderedSet<Class<?>> getExclusionSet() {
        return myExclusionSet;
    }

    public ClassificationBag<Class<?>, Node> getNodeClassifier() {
        return myNodeClassifier;
    }

    private void validateLinked(Node node) {
        if (node.getNext() == null && node.getParent() == null) {
            throw new IllegalStateException("Added block " + node + " is not linked into the AST");
        }
    }

    @Override
    public void nodeAdded(Node node) {
        validateLinked(node);
        myNodeClassifier.add(node);
        if (myHost != null) myHost.nodeAdded(node);
    }

    @Override
    public void nodeAddedWithChildren(Node node) {
        validateLinked(node);
        myNodeClassifier.add(node);
        addNodes(node.getChildren());
        if (myHost != null) myHost.nodeAddedWithChildren(node);
    }

    @Override
    public void nodeAddedWithDescendants(Node node) {
        validateLinked(node);
        myNodeClassifier.add(node);
        addNodes(node.getDescendants());
        if (myHost != null) myHost.nodeAddedWithDescendants(node);
    }

    private void addNodes(ReversiblePeekingIterable<Node> nodes) {
        for (Node child : nodes) {
            myNodeClassifier.add(child);
        }
    }

    private void validateUnlinked(Node node) {
        if (!(node.getNext() == null && node.getParent() == null)) {
            throw new IllegalStateException("Removed block " + node + " is still linked in the AST");
        }
    }

    @Override
    public void nodeRemoved(Node node) {
        nodeRemovedWithDescendants(node);
        //validateUnlinked(node);
        //myNodeClassifier.remove(node);
        //if (myHost != null) myHost.nodeRemoved(node);
    }

    @Override
    public void nodeRemovedWithChildren(Node node) {
        nodeRemovedWithDescendants(node);
        //validateUnlinked(node);
        //myNodeClassifier.add(node);
        //removeNodes(node.getChildren());
        //if (myHost != null) myHost.nodeRemovedWithChildren(node);
    }

    @Override
    public void nodeRemovedWithDescendants(Node node) {
        validateUnlinked(node);
        myNodeClassifier.add(node);
        removeNodes(node.getDescendants());
        if (myHost != null) myHost.nodeRemovedWithDescendants(node);
    }

    private void removeNodes(ReversiblePeekingIterable<Node> nodes) {
        for (Node child : nodes) {
            myNodeClassifier.add(child);
        }
    }

    public OrderedSet<Node> getItems() {
        return myNodeClassifier.getItems();
    }

    public <X> ReversibleIterable<X> getCategoryItems(Class<? extends X> nodeClass, Set<? extends Class<?>> classes) {
        return myNodeClassifier.getCategoryItems(nodeClass, classes);
    }
}
