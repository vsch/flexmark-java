package com.vladsch.flexmark.internal.util.collection;

import java.util.Iterator;

public interface ReversibleIterator<E> extends Iterator<E> {
    ReversibleIterator<E> reversed();
    boolean isReversed();
}
