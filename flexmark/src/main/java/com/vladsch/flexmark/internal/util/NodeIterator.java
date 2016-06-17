package com.vladsch.flexmark.internal.util;

import com.vladsch.flexmark.node.Node;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;

public class NodeIterator implements Iterator<Node> {
    final Node firstNode;
    final Node lastNode;
    Node node;
    Node next;

    /**
     * stream nodes until null
     *
     * @param firstNode
     */
    public NodeIterator(Node firstNode) {
        this(firstNode, null);
    }

    /**
     * stream nodes until null
     *
     * @param firstNode
     */
    public NodeIterator(Node firstNode, Node lastNode) {
        Objects.requireNonNull(firstNode);

        this.firstNode = firstNode;
        this.lastNode = lastNode;
        this.node = firstNode;
        this.next = firstNode.getNext();
    }

    @Override
    public boolean hasNext() {
        return node != null;
    }

    @Override
    public Node next() {
        if (node != null) {
            Node result = node;
            node = next;
            if (node == null || node == lastNode) next = null;
            else next = node.getNext();
            return result;
        }
        return null;
    }

    public Node peek() {
        if (node != null) {
            return node;
        }
        return null;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("remove");
    }

    @Override
    public void forEachRemaining(Consumer<? super Node> consumer) {
        Objects.requireNonNull(consumer);

        while (hasNext()) {
            consumer.accept(next());
        }
    }
}
