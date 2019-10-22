package com.vladsch.flexmark.util.ast;

import com.vladsch.flexmark.util.collection.ClassificationBag;
import com.vladsch.flexmark.util.collection.OrderedMap;
import com.vladsch.flexmark.util.collection.OrderedSet;
import com.vladsch.flexmark.util.collection.iteration.ReversibleIterable;
import com.vladsch.flexmark.util.collection.iteration.ReversibleIterator;
import com.vladsch.flexmark.util.collection.iteration.ReversiblePeekingIterable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ClassifyingNodeTracker implements NodeTracker {
    protected final @NotNull ClassificationBag<Class<?>, Node> myNodeClassifier;
    private final @Nullable NodeTracker myHost;
    private final @NotNull OrderedMap<Class<?>, Set<Class<?>>> myExclusionMap;
    private final @NotNull OrderedSet<Class<?>> myExclusionSet;
    private final @NotNull HashMap<Integer, BitSet> myNodeAncestryMap;

    public ClassifyingNodeTracker(@Nullable NodeTracker host, @NotNull Map<Class<? extends Node>, Set<Class<?>>> exclusionMap) {
        myHost = host;
        myNodeClassifier = new ClassificationBag<>(NodeClassifier.INSTANCE);
        myExclusionMap = new OrderedMap<>(exclusionMap.size());
        myExclusionMap.putAll(exclusionMap);

        // this maps the exclusion class to bits in the bit set
        myExclusionSet = new OrderedSet<>();

        ReversibleIterator<Set<Class<?>>> iterator = myExclusionMap.valueIterable().iterator();
        while (iterator.hasNext()) {
            myExclusionSet.addAll(iterator.next());
        }
        myNodeAncestryMap = new HashMap<>();
    }

    @NotNull
    public OrderedMap<Class<?>, Set<Class<?>>> getExclusionMap() {
        return myExclusionMap;
    }

    @NotNull
    public HashMap<Integer, BitSet> getNodeAncestryMap() {
        return myNodeAncestryMap;
    }

    @NotNull
    public OrderedSet<Class<?>> getExclusionSet() {
        return myExclusionSet;
    }

    @NotNull
    public ClassificationBag<Class<?>, Node> getNodeClassifier() {
        return myNodeClassifier;
    }

    private void validateLinked(Node node) {
        if (node.getNext() == null && node.getParent() == null) {
            throw new IllegalStateException("Added block " + node + " is not linked into the AST");
        }
    }

    @Override
    public void nodeAdded(@NotNull Node node) {
        validateLinked(node);
        myNodeClassifier.add(node);
        if (myHost != null) myHost.nodeAdded(node);
    }

    @Override
    public void nodeAddedWithChildren(@NotNull Node node) {
        validateLinked(node);
        myNodeClassifier.add(node);
        addNodes(node.getChildren());
        if (myHost != null) myHost.nodeAddedWithChildren(node);
    }

    @Override
    public void nodeAddedWithDescendants(@NotNull Node node) {
        validateLinked(node);
        myNodeClassifier.add(node);
        addNodes(node.getDescendants());
        if (myHost != null) myHost.nodeAddedWithDescendants(node);
    }

    private void addNodes(@NotNull ReversiblePeekingIterable<Node> nodes) {
        for (Node child : nodes) {
            myNodeClassifier.add(child);
        }
    }

    private void validateUnlinked(@NotNull Node node) {
        if (!(node.getNext() == null && node.getParent() == null)) {
            throw new IllegalStateException("Removed block " + node + " is still linked in the AST");
        }
    }

    @Override
    public void nodeRemoved(@NotNull Node node) {
        nodeRemovedWithDescendants(node);
    }

    @Override
    public void nodeRemovedWithChildren(@NotNull Node node) {
        nodeRemovedWithDescendants(node);
    }

    @Override
    public void nodeRemovedWithDescendants(@NotNull Node node) {
        validateUnlinked(node);
        myNodeClassifier.add(node);
        removeNodes(node.getDescendants());
        if (myHost != null) myHost.nodeRemovedWithDescendants(node);
    }

    private void removeNodes(@NotNull ReversiblePeekingIterable<Node> nodes) {
        for (Node child : nodes) {
            myNodeClassifier.add(child);
        }
    }

    public @NotNull OrderedSet<Node> getItems() {
        return myNodeClassifier.getItems();
    }

    public <X> @NotNull ReversibleIterable<X> getCategoryItems(@NotNull Class<? extends X> nodeClass, @NotNull Set<Class<?>> classes) {
        return myNodeClassifier.getCategoryItems(nodeClass, classes);
    }
}
