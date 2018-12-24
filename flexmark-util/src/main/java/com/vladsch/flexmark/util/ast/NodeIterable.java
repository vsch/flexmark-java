package com.vladsch.flexmark.util.ast;

import com.vladsch.flexmark.util.collection.Consumer;
import com.vladsch.flexmark.util.collection.iteration.ReversibleIterator;
import com.vladsch.flexmark.util.collection.iteration.ReversiblePeekingIterable;
import com.vladsch.flexmark.util.collection.iteration.ReversiblePeekingIterator;

public class NodeIterable implements ReversiblePeekingIterable<Node> {
    final Node firstNode;
    final Node lastNode;
    final boolean reversed;

    public NodeIterable(Node firstNode, Node lastNode, boolean reversed) {
        this.firstNode = firstNode;
        this.lastNode = lastNode;
        this.reversed = reversed;
    }


    @Override
    public ReversiblePeekingIterator<Node> iterator() {
        return new NodeIterator(firstNode, lastNode, reversed);
    }

    public void forEach(Consumer<? super Node> consumer) {
        ReversibleIterator<Node> iterator = iterator();
        while (iterator.hasNext()) {
            consumer.accept(iterator.next());
        }
    }

    @Override
    public ReversiblePeekingIterable<Node> reversed() {
        return new NodeIterable(firstNode, lastNode, !reversed);
    }

    @Override
    public boolean isReversed() {
        return reversed;
    }

    @Override
    public ReversiblePeekingIterator<Node> reversedIterator() {
        return new NodeIterator(firstNode, lastNode, !reversed);
    }

    public static final ReversiblePeekingIterable<Node> EMPTY = new ReversiblePeekingIterable<Node>() {
        @Override
        public ReversiblePeekingIterator<Node> iterator() {
            return NodeIterator.EMPTY;
        }

        @Override
        public ReversiblePeekingIterable<Node> reversed() {
            return this;
        }

        public void forEach(Consumer<? super Node> consumer) {

        }

        @Override
        public boolean isReversed() {
            return false;
        }

        @Override
        public ReversiblePeekingIterator<Node> reversedIterator() {
            return NodeIterator.EMPTY;
        }
    };
}
