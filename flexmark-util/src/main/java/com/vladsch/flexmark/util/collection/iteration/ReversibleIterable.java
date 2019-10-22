package com.vladsch.flexmark.util.collection.iteration;

import org.jetbrains.annotations.NotNull;

public interface ReversibleIterable<E> extends Iterable<E> {
    @Override
    @NotNull ReversibleIterator<E> iterator();
    @NotNull ReversibleIterable<E> reversed();
    boolean isReversed();
    @NotNull ReversibleIterator<E> reversedIterator();
}
