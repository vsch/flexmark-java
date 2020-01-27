package com.vladsch.flexmark.util.ast;

import com.vladsch.flexmark.util.collection.iteration.ReversiblePeekingIterator;
import org.jetbrains.annotations.Nullable;

import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class NodeIterator implements ReversiblePeekingIterator<Node> {
    final Node firstNode;
    final Node lastNode;
    final boolean reversed;
    Node node;
    Node result;

    /**
     * @param firstNode node from which to start the iteration and continue until all sibling nodes have been traversed
     */
    public NodeIterator(Node firstNode) {
        this(firstNode, null, false);
    }

    /**
     * @param firstNode node from which to start the iteration and continue until all sibling nodes have been traversed
     * @param reversed  true/false if the nodes are to be traversed in reverse order. If true the nodes previous sibling will be used instead of next sibling
     */
    public NodeIterator(Node firstNode, boolean reversed) {
        this(firstNode, null, reversed);
    }

    /**
     * @param firstNode node from which to start the iteration and continue until all sibling nodes have been traversed or lastNode has been traversed
     * @param lastNode  the last node to be traversed
     */
    public NodeIterator(Node firstNode, Node lastNode) {
        this(firstNode, lastNode, false);
    }

    /**
     * iterate nodes until null or last node is iterated over
     *
     * @param firstNode node from which to start the iteration and continue until all sibling nodes have been traversed or lastNode has been traversed
     * @param lastNode  the last node to be traversed
     * @param reversed  true/false if the nodes are to be traversed in reverse order. If true the nodes previous sibling will be used instead of next sibling
     */
    public NodeIterator(Node firstNode, Node lastNode, boolean reversed) {
        if (firstNode == null)
            throw new NullPointerException();

        this.firstNode = firstNode;
        this.lastNode = lastNode;
        this.reversed = reversed;
        this.node = reversed ? lastNode : firstNode;
    }

    /**
     * @return true if the iterator is a reversed iterator
     */
    @Override
    public boolean isReversed() {
        return reversed;
    }

    /**
     * @return true if there is a next node
     */
    @Override
    public boolean hasNext() {
        return node != null;
    }

    /**
     * @return the next node for the iterator. If the iterator is not reversed then the previous node's next sibling is returned. If it is reversed the the previous node's previous sibling is returned.
     */
    @Override
    public Node next() {
        result = null;

        if (node == null) {
            throw new NoSuchElementException();
        }

        result = node;
        node = reversed ? node.getPrevious() : node.getNext();
        if (node == null || result == (reversed ? firstNode : lastNode)) node = null;
        return result;
    }

    /**
     * @return the node which would be returned by a call to {@link #next()} or null if there is no next node.
     */
    @Nullable
    public Node peek() {
        if (node != null) {
            return node;
        }
        return null;
    }

    /**
     * Remove the last node returned by {@link #next()}
     */
    @Override
    public void remove() {
        if (result == null) {
            throw new IllegalStateException("Either next() was not called yet or the node was removed");
        }
        result.unlink();
        result = null;
    }

    public void forEachRemaining(Consumer<? super Node> consumer) {
        if (consumer == null)
            throw new NullPointerException();

        while (hasNext()) {
            consumer.accept(next());
        }
    }

    final public static ReversiblePeekingIterator<Node> EMPTY = new ReversiblePeekingIterator<Node>() {
        @Override
        public void remove() {

        }

        @Override
        public boolean isReversed() {
            return false;
        }

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public Node next() {
            throw new NoSuchElementException();
        }

        @Nullable
        @Override
        public Node peek() {
            return null;
        }
    };
}
