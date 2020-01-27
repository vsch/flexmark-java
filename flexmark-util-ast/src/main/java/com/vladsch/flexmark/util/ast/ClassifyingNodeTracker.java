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
    protected final @NotNull ClassificationBag<Class<?>, Node> nodeClassifier;
    final private @Nullable NodeTracker host;
    final private @NotNull OrderedMap<Class<?>, Set<Class<?>>> exclusionMap;
    final private @NotNull OrderedSet<Class<?>> exclusionSet;
    final private @NotNull HashMap<Integer, BitSet> nodeAncestryMap;

    public ClassifyingNodeTracker(@Nullable NodeTracker host, @NotNull Map<Class<? extends Node>, Set<Class<?>>> exclusionMap) {
        this.host = host;
        nodeClassifier = new ClassificationBag<>(NodeClassifier.INSTANCE);
        this.exclusionMap = new OrderedMap<>(exclusionMap.size());
        this.exclusionMap.putAll(exclusionMap);

        // this maps the exclusion class to bits in the bit set
        exclusionSet = new OrderedSet<>();

        ReversibleIterator<Set<Class<?>>> iterator = this.exclusionMap.valueIterable().iterator();
        while (iterator.hasNext()) {
            exclusionSet.addAll(iterator.next());
        }
        nodeAncestryMap = new HashMap<>();
    }

    @NotNull
    public OrderedMap<Class<?>, Set<Class<?>>> getExclusionMap() {
        return exclusionMap;
    }

    @NotNull
    public HashMap<Integer, BitSet> getNodeAncestryMap() {
        return nodeAncestryMap;
    }

    @NotNull
    public OrderedSet<Class<?>> getExclusionSet() {
        return exclusionSet;
    }

    @NotNull
    public ClassificationBag<Class<?>, Node> getNodeClassifier() {
        return nodeClassifier;
    }

    private void validateLinked(Node node) {
        if (node.getNext() == null && node.getParent() == null) {
            throw new IllegalStateException("Added block " + node + " is not linked into the AST");
        }
    }

    @Override
    public void nodeAdded(@NotNull Node node) {
        validateLinked(node);
        nodeClassifier.add(node);
        if (host != null) host.nodeAdded(node);
    }

    @Override
    public void nodeAddedWithChildren(@NotNull Node node) {
        validateLinked(node);
        nodeClassifier.add(node);
        addNodes(node.getChildren());
        if (host != null) host.nodeAddedWithChildren(node);
    }

    @Override
    public void nodeAddedWithDescendants(@NotNull Node node) {
        validateLinked(node);
        nodeClassifier.add(node);
        addNodes(node.getDescendants());
        if (host != null) host.nodeAddedWithDescendants(node);
    }

    private void addNodes(@NotNull ReversiblePeekingIterable<Node> nodes) {
        for (Node child : nodes) {
            nodeClassifier.add(child);
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
        nodeClassifier.add(node);
        removeNodes(node.getDescendants());
        if (host != null) host.nodeRemovedWithDescendants(node);
    }

    private void removeNodes(@NotNull ReversiblePeekingIterable<Node> nodes) {
        for (Node child : nodes) {
            nodeClassifier.add(child);
        }
    }

    public @NotNull OrderedSet<Node> getItems() {
        return nodeClassifier.getItems();
    }

    public <X> @NotNull ReversibleIterable<X> getCategoryItems(@NotNull Class<? extends X> nodeClass, @NotNull Set<Class<?>> classes) {
        return nodeClassifier.getCategoryItems(nodeClass, classes);
    }
}
