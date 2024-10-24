package com.vladsch.flexmark.util.ast;

import com.vladsch.flexmark.util.collection.ClassificationBag;
import com.vladsch.flexmark.util.collection.OrderedMap;
import com.vladsch.flexmark.util.collection.OrderedSet;
import com.vladsch.flexmark.util.collection.iteration.ReversibleIterable;
import com.vladsch.flexmark.util.collection.iteration.ReversibleIterator;
import com.vladsch.flexmark.util.collection.iteration.ReversiblePeekingIterable;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ClassifyingNodeTracker implements NodeTracker {
  private final ClassificationBag<Class<?>, Node> nodeClassifier;
  private final NodeTracker host;
  private final OrderedMap<Class<?>, Set<Class<?>>> exclusionMap;
  private final OrderedSet<Class<?>> exclusionSet;
  private final HashMap<Integer, BitSet> nodeAncestryMap;

  ClassifyingNodeTracker(NodeTracker host, Map<Class<? extends Node>, Set<Class<?>>> exclusionMap) {
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

  public OrderedMap<Class<?>, Set<Class<?>>> getExclusionMap() {
    return exclusionMap;
  }

  public Map<Integer, BitSet> getNodeAncestryMap() {
    return nodeAncestryMap;
  }

  public OrderedSet<Class<?>> getExclusionSet() {
    return exclusionSet;
  }

  public ClassificationBag<Class<?>, Node> getNodeClassifier() {
    return nodeClassifier;
  }

  private static void validateLinked(Node node) {
    if (node.getNext() == null && node.getParent() == null) {
      throw new IllegalStateException("Added block " + node + " is not linked into the AST");
    }
  }

  @Override
  public void nodeAdded(Node node) {
    validateLinked(node);
    nodeClassifier.add(node);
    if (host != null) host.nodeAdded(node);
  }

  @Override
  public void nodeAddedWithChildren(Node node) {
    validateLinked(node);
    nodeClassifier.add(node);
    addNodes(node.getChildren());
    if (host != null) host.nodeAddedWithChildren(node);
  }

  @Override
  public void nodeAddedWithDescendants(Node node) {
    validateLinked(node);
    nodeClassifier.add(node);
    addNodes(node.getDescendants());
    if (host != null) host.nodeAddedWithDescendants(node);
  }

  private void addNodes(ReversiblePeekingIterable<Node> nodes) {
    for (Node child : nodes) {
      nodeClassifier.add(child);
    }
  }

  private static void validateUnlinked(Node node) {
    if (!(node.getNext() == null && node.getParent() == null)) {
      throw new IllegalStateException("Removed block " + node + " is still linked in the AST");
    }
  }

  @Override
  public void nodeRemoved(Node node) {
    nodeRemovedWithDescendants(node);
  }

  @Override
  public void nodeRemovedWithChildren(Node node) {
    nodeRemovedWithDescendants(node);
  }

  @Override
  public void nodeRemovedWithDescendants(Node node) {
    validateUnlinked(node);
    nodeClassifier.add(node);
    removeNodes(node.getDescendants());
    if (host != null) host.nodeRemovedWithDescendants(node);
  }

  private void removeNodes(ReversiblePeekingIterable<Node> nodes) {
    for (Node child : nodes) {
      nodeClassifier.add(child);
    }
  }

  public OrderedSet<Node> getItems() {
    return nodeClassifier.getItems();
  }

  public <X> ReversibleIterable<X> getCategoryItems(Set<Class<?>> classes) {
    return nodeClassifier.getCategoryItems(classes);
  }
}
