package com.vladsch.flexmark.util.collection.iteration;

import org.jetbrains.annotations.NotNull;

public interface ReversiblePeekingIterable<E> extends ReversibleIterable<E> {
    @Override
    @NotNull ReversiblePeekingIterator<E> iterator();
    @Override
    @NotNull ReversiblePeekingIterable<E> reversed();
    @Override
    @NotNull ReversiblePeekingIterator<E> reversedIterator();
}
