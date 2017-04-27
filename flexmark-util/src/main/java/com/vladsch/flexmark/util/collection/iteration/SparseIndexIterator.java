package com.vladsch.flexmark.util.collection.iteration;

import com.vladsch.flexmark.util.collection.Consumer;

import java.util.NoSuchElementException;

/**
 * @deprecated not tested
 */
public class SparseIndexIterator implements ReversibleIterator<Integer> {
    private final int[] myStarts;
    private final int[] myEnds;
    private final boolean myIsReversed;
    private int myIndex;
    private int myNext;
    private int myLast;

    public SparseIndexIterator(int[] starts, int[] ends, boolean reversed) {
        myStarts = starts;
        myEnds = ends;
        myIsReversed = reversed;
        myIndex = reversed ? myEnds.length - 1 : 0;
        myNext = myIndex < 0 && myIndex >= myEnds.length ? -1 : myIsReversed ? myEnds[myIndex] : myStarts[myIndex];
        myLast = -1;
    }

    @Override
    public boolean isReversed() {
        return myIsReversed;
    }

    @Override
    public boolean hasNext() {
        return myNext != -1;
    }

    @Override
    public Integer next() {
        if (myNext == -1) {
            throw new NoSuchElementException();
        }

        myLast = myNext;
        if (myIsReversed) {
            if (myNext == myStarts[myIndex]) {
                // previous range
                myIndex--;
                myNext = myIndex >= 0 ? myEnds[myIndex] : -1;
            } else {
                myNext--;
            }
        } else {
            if (myNext == myEnds[myIndex]) {
                // previous range
                myIndex++;
                myNext = myIndex < myStarts.length ? myStarts[myIndex] : -1;
            } else {
                myNext++;
            }
        }
        return myLast;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    public void forEachRemaining(Consumer<? super Integer> consumer) {
        while (hasNext()) {
            consumer.accept(next());
        }
    }
}
