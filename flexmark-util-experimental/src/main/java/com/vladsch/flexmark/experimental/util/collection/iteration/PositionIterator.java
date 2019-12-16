package com.vladsch.flexmark.experimental.util.collection.iteration;

import com.vladsch.flexmark.util.sequence.PositionAnchor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static com.vladsch.flexmark.util.sequence.PositionAnchor.CURRENT;
import static com.vladsch.flexmark.util.sequence.PositionAnchor.PREVIOUS;

/**
 * Bidirectional iterator, direction depends on the position anchor NEXT is a forward iterator, PREVIOUS is a reverse iterator
 *
 * @param <T> type of element held by position
 * @param <P> type of position iterated over
 */
class PositionIterator<T, P extends IPositionHolder<T, P>> implements Iterator<P> {
    private @Nullable P myIndex;
    private @Nullable P myNext;

    public PositionIterator(@NotNull P index) {
        assert index.getAnchor() != CURRENT;
        myIndex = null;
        myNext = index;
    }

    @Override
    public boolean hasNext() {
        assert myNext == null || myNext.isValid() || myNext.getAnchor() == PREVIOUS;
        return myNext != null && myNext.isValidElement();
    }

    @Override
    public P next() {
        if (myNext == null || !myNext.isValidElement()) throw new NoSuchElementException();

        myIndex = myNext.withAnchor(PositionAnchor.CURRENT);

        P oldNext = myNext;
        if (myNext.getAnchor() == PREVIOUS) {
            myNext = myNext.previousOrNull();
        } else {
            myNext = myNext.nextOrNull();
        }
        oldNext.detachListener();

        assert myNext == null || myIndex.getIndex() != myNext.getIndex();
        return myIndex;
    }

    @Override
    public void remove() {
        if (myIndex == null)
            throw new IllegalStateException("next() has not been called");

        myIndex.remove();
    }
}
