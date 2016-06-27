package com.vladsch.flexmark.internal.util;

import com.vladsch.flexmark.internal.util.collection.ReversibleIterator;
import com.vladsch.flexmark.node.Node;

import java.util.Objects;
import java.util.function.Consumer;

public class NodeIterator implements ReversibleIterator<Node> {
    final boolean reversed;
    final Node firstNode;
    final Node lastNode;
    Node node;
    Node result;

    /**
     * iterate nodes until last node is iterated over or null
     *
     * @param firstNode
     */
    public NodeIterator(Node firstNode) {
        this(firstNode, null, false);
    }

    /**
     * iterate nodes until last node is iterated over or null
     *
     * @param firstNode
     */
    public NodeIterator(Node firstNode, boolean reversed) {
        this(firstNode, null, reversed);
    }

    /**
     * iterate nodes until last node is iterated over or null
     *
     * @param firstNode
     */
    public NodeIterator(Node firstNode, Node lastNode) {
        this(firstNode, lastNode, false);
    }

    /**
     * iterate nodes until null or last node is iterated over
     *
     * @param firstNode
     */
    public NodeIterator(Node firstNode, Node lastNode, boolean reversed) {
        Objects.requireNonNull(firstNode);

        this.reversed = reversed;
        this.firstNode = firstNode;
        this.lastNode = lastNode;
        this.node = reversed ? lastNode : firstNode;
    }

    @Override
    public NodeIterator reversed() {
        return new NodeIterator(firstNode, lastNode, !reversed);
    }

    @Override
    public boolean isReversed() {
        return reversed;
    }

    @Override
    public boolean hasNext() {
        return node != null;
    }

    @Override
    public Node next() {
        result = null;
        if (node != null) {
            result = node;
            node = reversed ? node.getPrevious() : node.getNext();
            if (node == null || result == (reversed ? firstNode : lastNode)) node = null;
        }
        return result;
    }

    public Node peek() {
        if (node != null) {
            return node;
        }
        return null;
    }

    @Override
    public void remove() {
        if (result == null) {
            throw new IllegalStateException("Either next() was not called yet or the element was remove()");
        }
        result.unlink();
        result = null;
    }

    @Override
    public void forEachRemaining(Consumer<? super Node> consumer) {
        Objects.requireNonNull(consumer);

        while (hasNext()) {
            consumer.accept(next());
        }
    }
}
