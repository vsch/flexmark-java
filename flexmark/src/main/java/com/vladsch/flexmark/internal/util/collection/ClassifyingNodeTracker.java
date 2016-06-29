package com.vladsch.flexmark.internal.util.collection;

import com.vladsch.flexmark.internal.util.NodeTracker;
import com.vladsch.flexmark.internal.util.mappers.NodeClassifier;
import com.vladsch.flexmark.node.Node;

public class ClassifyingNodeTracker implements NodeTracker {
    final protected ClassificationBag<Class<? extends Node>, Node> nodeClassifier = new ClassificationBag<Class<? extends Node>, Node>(NodeClassifier.INSTANCE);

    public ClassificationBag<Class<? extends Node>, Node> getNodeClassifier() {
        return nodeClassifier;
    }

    @Override
    public void nodeAdded(Node node) {
        nodeClassifier.add(node);
    }

    @Override
    public void nodeAddedWithChildren(Node node) {
        nodeClassifier.add(node);
        addNodes(node.getChildren());
    }

    @Override
    public void nodeAddedWithDescendants(Node node) {
        nodeClassifier.add(node);
        addNodes(node.getDescendants());
    }

    private void addNodes(ReversiblePeekingIterable<Node> nodes) {
        for (Node child : nodes) {
            nodeClassifier.add(child);
        }
    }

    @Override
    public void nodeRemoved(Node node) {
        nodeClassifier.remove(node);
    }

    @Override
    public void nodeRemovedWithChildren(Node node) {
        nodeClassifier.add(node);
        removeNodes(node.getChildren());
    }

    @Override
    public void nodeRemovedWithDescendants(Node node) {
        nodeClassifier.add(node);
        removeNodes(node.getDescendants());
    }

    private void removeNodes(ReversiblePeekingIterable<Node> nodes) {
        for (Node child : nodes) {
            nodeClassifier.add(child);
        }
    }
}
