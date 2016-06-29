package com.vladsch.flexmark.internal.util.collection;

import com.vladsch.flexmark.internal.util.NodeTracker;
import com.vladsch.flexmark.internal.util.mappers.NodeClassifier;
import com.vladsch.flexmark.node.Node;

public class ClassifiedNodeTracker implements NodeTracker {
    final protected ClassifiedBag<Class<? extends Node>, Node> classifiedNodeBag = new ClassifiedBag<Class<? extends Node>, Node>(NodeClassifier.INSTANCE);

    public ClassifiedBag<Class<? extends Node>, Node> getClassifiedNodeBag() {
        return classifiedNodeBag;
    }

    @Override
    public void nodeAdded(Node node) {
        classifiedNodeBag.add(node);
    }

    @Override
    public void nodeAddedWithChildren(Node node) {
        classifiedNodeBag.add(node);
        addNodes(node.getChildren());
    }

    @Override
    public void nodeAddedWithDescendants(Node node) {
        classifiedNodeBag.add(node);
        addNodes(node.getDescendants());
    }

    private void addNodes(ReversiblePeekingIterable<Node> nodes) {
        for (Node child : nodes) {
            classifiedNodeBag.add(child);
        }
    }

    @Override
    public void nodeRemoved(Node node) {
        classifiedNodeBag.remove(node);
    }

    @Override
    public void nodeRemovedWithChildren(Node node) {
        classifiedNodeBag.add(node);
        removeNodes(node.getChildren());
    }

    @Override
    public void nodeRemovedWithDescendants(Node node) {
        classifiedNodeBag.add(node);
        removeNodes(node.getDescendants());
    }

    private void removeNodes(ReversiblePeekingIterable<Node> nodes) {
        for (Node child : nodes) {
            classifiedNodeBag.add(child);
        }
    }
}
