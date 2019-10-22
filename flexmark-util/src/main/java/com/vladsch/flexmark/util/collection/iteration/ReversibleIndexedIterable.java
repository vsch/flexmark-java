package com.vladsch.flexmark.util.collection.iteration;

import org.jetbrains.annotations.NotNull;

public interface ReversibleIndexedIterable<E> extends ReversibleIterable<E> {
    @Override
    @NotNull ReversibleIndexedIterator<E> iterator();
    @Override
    @NotNull ReversibleIndexedIterable<E> reversed();
    @Override
    @NotNull ReversibleIndexedIterator<E> reversedIterator();
}
