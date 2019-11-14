package com.vladsch.flexmark.util.collection.iteration;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.NoSuchElementException;

class PositionIterator<V> implements Iterator<Position<V>> {
    private @Nullable Position<V> myIndex;
    private @Nullable Position<V> myNext;

    public PositionIterator(@NotNull Position<V> index) {
        myIndex = null;
        myNext = index;
    }

    @Override
    public boolean hasNext() {
        if (myNext != null && !myNext.isValid() && myNext.isNextValid()) {
            // refresh to valid next position
            myNext = myNext.getNext();
        }

        return myNext != null && myNext.isValidPosition();
    }

    @Override
    public Position<V> next() {
//            if (myNext != null && !myNext.isValid() && myNext.isNextValid()) {
//                // refresh to valid next position
//                myNext = myNext.getNext();
//            }

        if (myNext == null || !myNext.isValidPosition()) throw new NoSuchElementException();
        myIndex = myNext;
        myNext = myNext.isNextValid() ? myNext.getNext() : null;
        return myIndex;
    }

    @Override
    public void remove() {
        if (myIndex == null) {
            throw new IllegalStateException("next() has not been called yet.");
        }
        myIndex.remove();
    }

}
