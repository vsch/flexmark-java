package com.vladsch.flexmark.util.ast;

import com.vladsch.flexmark.util.collection.iteration.ReversiblePeekingIterable;
import com.vladsch.flexmark.util.collection.iteration.ReversiblePeekingIterator;
import org.jetbrains.annotations.NotNull;

public class DescendantNodeIterable implements ReversiblePeekingIterable<Node> {
    private ReversiblePeekingIterable<Node> iterable;

    /**
     * iterate nodes, with descendants, depth first until all are done
     *
     * @param iterable node iterable to use for iterating nodes and their descendants
     */
    public DescendantNodeIterable(@NotNull ReversiblePeekingIterable<Node> iterable) {
        if (iterable instanceof DescendantNodeIterable) {
            this.iterable = ((DescendantNodeIterable) iterable).iterable;
        } else {
            this.iterable = iterable;
        }
    }

    @NotNull
    @Override
    public ReversiblePeekingIterator<Node> iterator() {
        return new DescendantNodeIterator(iterable.iterator());
    }

    @NotNull
    @Override
    public ReversiblePeekingIterable<Node> reversed() {
        return new DescendantNodeIterable(iterable.reversed());
    }

    @NotNull
    @Override
    public ReversiblePeekingIterator<Node> reversedIterator() {
        return new DescendantNodeIterator(iterable.reversedIterator());
    }

    @Override
    public boolean isReversed() {
        return iterable.isReversed();
    }
}
