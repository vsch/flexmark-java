package com.vladsch.flexmark.internal.util;

import com.vladsch.flexmark.internal.util.collection.ReversiblePeekingIterable;
import com.vladsch.flexmark.internal.util.collection.ReversiblePeekingIterator;
import com.vladsch.flexmark.node.Node;

public class DescendantNodeIterable implements ReversiblePeekingIterable<Node> {
    private ReversiblePeekingIterable<Node> iterable;

    /**
     * iterate nodes, with descendants, depth first until all are done
     *
     * @param iterable
     */
    public DescendantNodeIterable(ReversiblePeekingIterable<Node> iterable) {
        if (iterable instanceof DescendantNodeIterable) {
            this.iterable = ((DescendantNodeIterable) iterable).iterable;
        } else {
            this.iterable = iterable;
        }
    }

    @Override
    public ReversiblePeekingIterator<Node> iterator() {
        return new DescendantNodeIterator(iterable.iterator());
    }

    @Override
    public ReversiblePeekingIterable<Node> reversed() {
        return new DescendantNodeIterable(iterable.reversed());
    }

    @Override
    public ReversiblePeekingIterator<Node> reversedIterator() {
        return new DescendantNodeIterator(iterable.reversedIterator());
    }

    @Override
    public boolean isReversed() {
        return iterable.isReversed();
    }
}
