package com.vladsch.flexmark.util.collection.iteration;

import org.jetbrains.annotations.Nullable;

public interface ReversiblePeekingIterator<E> extends ReversibleIterator<E> {
    @Nullable E peek();
}
