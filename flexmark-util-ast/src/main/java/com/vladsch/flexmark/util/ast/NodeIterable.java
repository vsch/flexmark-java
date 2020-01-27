package com.vladsch.flexmark.util.ast;

import com.vladsch.flexmark.util.collection.iteration.ReversibleIterator;
import com.vladsch.flexmark.util.collection.iteration.ReversiblePeekingIterable;
import com.vladsch.flexmark.util.collection.iteration.ReversiblePeekingIterator;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class NodeIterable implements ReversiblePeekingIterable<Node> {
    final Node firstNode;
    final Node lastNode;
    final boolean reversed;

    public NodeIterable(Node firstNode, Node lastNode, boolean reversed) {
        this.firstNode = firstNode;
        this.lastNode = lastNode;
        this.reversed = reversed;
    }

    @NotNull
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

    @NotNull
    @Override
    public ReversiblePeekingIterable<Node> reversed() {
        return new NodeIterable(firstNode, lastNode, !reversed);
    }

    @Override
    public boolean isReversed() {
        return reversed;
    }

    @NotNull
    @Override
    public ReversiblePeekingIterator<Node> reversedIterator() {
        return new NodeIterator(firstNode, lastNode, !reversed);
    }

    final public static ReversiblePeekingIterable<Node> EMPTY = new ReversiblePeekingIterable<Node>() {
        @NotNull
        @Override
        public ReversiblePeekingIterator<Node> iterator() {
            return NodeIterator.EMPTY;
        }

        @NotNull
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

        @NotNull
        @Override
        public ReversiblePeekingIterator<Node> reversedIterator() {
            return NodeIterator.EMPTY;
        }
    };
}
