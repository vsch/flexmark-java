package com.vladsch.flexmark.util.collection.iteration;

import org.jetbrains.annotations.NotNull;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class IndexedIterator<R, S, I extends ReversibleIterator<Integer>> implements ReversibleIndexedIterator<R> {
    private final I myIterator;
    private final Indexed<S> myItems;
    private int myLastIndex;
    private int myModificationCount;

    public IndexedIterator(@NotNull Indexed<S> items, @NotNull I iterator) {
        this.myItems = items;
        this.myIterator = iterator;
        this.myLastIndex = -1;
        this.myModificationCount = items.modificationCount();
    }

    @Override
    public boolean isReversed() {
        return myIterator.isReversed();
    }

    @Override
    public boolean hasNext() {
        return myIterator.hasNext();
    }

    @Override
    public @NotNull R next() {
        if (myModificationCount != myItems.modificationCount()) {
            throw new ConcurrentModificationException();
        }

        myLastIndex = myIterator.next();
        //noinspection unchecked
        return (R) myItems.get(myLastIndex);
    }

    @Override
    public void remove() {
        if (myLastIndex == -1) {
            throw new NoSuchElementException();
        }

        if (myModificationCount != myItems.modificationCount()) {
            throw new ConcurrentModificationException();
        }

        myItems.removeAt(myLastIndex);
        myLastIndex = -1;
        myModificationCount = myItems.modificationCount();
    }

    @Override
    public int getIndex() {
        if (myLastIndex < 0) {
            throw new NoSuchElementException();
        }
        return myLastIndex;
    }

    public void forEachRemaining(@NotNull Consumer<? super R> consumer) {
        while (hasNext()) {
            consumer.accept(next());
        }
    }
}
