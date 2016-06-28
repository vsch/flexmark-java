package com.vladsch.flexmark.internal.util.collection;

import java.util.NoSuchElementException;
import java.util.function.Consumer;

public abstract class AbstractSparseIterator<E> implements SparseIterator<E> {
    final private boolean reversed;
    private int index;
    private int lastIndex;

    public abstract SparseIterator<E> reversed();
    protected abstract int nextValidIndex(int index);
    protected abstract int previousValidIndex(int index);
    protected abstract E getValueAt(int index);
    protected abstract int maxSize();
    protected abstract void remove(int index);
    protected abstract void checkConcurrentMods();

    public AbstractSparseIterator(boolean reversed) {
        this.reversed = reversed;
        this.index = Integer.MIN_VALUE;
        this.lastIndex = -1;
    }

    public boolean isReversed() {
        return reversed;
    }

    public int getIndex() {
        if (lastIndex == -1) throw new IllegalStateException("Either next() was not called yet or the element was removeIndex()");
        return lastIndex;
    }

    private void skipUnwanted() {
        index = skipUnwanted(index, maxSize());
    }

    private int skipUnwanted(int index, int maxSize) {
        if (index >= 0) {
            if (reversed) {
                index = previousValidIndex(index);
            } else {
                index = nextValidIndex(index);
            }

            if (index < 0 || index >= maxSize) {
                index = -1;
            }
        }
        return index;
    }

    @Override
    public boolean hasNext() {
        int maxSize = maxSize();
        if (index == Integer.MIN_VALUE) {
            index = skipUnwanted(reversed ? maxSize -1 : 0, maxSize);
        }

        int nextIndex = skipUnwanted(index, maxSize);
        return nextIndex >= 0;
    }

    @Override
    public E next() {
        checkConcurrentMods();
        int maxSize = maxSize();
        
        if (index == Integer.MIN_VALUE) {
            index = skipUnwanted(reversed ? maxSize -1 : 0, maxSize);
        }
        
        if (index < 0 || index >= maxSize) throw new NoSuchElementException();
        if (reversed) {
            lastIndex = index--;
        } else {
            lastIndex = index++;
        }
        skipUnwanted();
        return getValueAt(lastIndex);
    }

    @Override
    public void remove() {
        if (lastIndex == -1) throw new IllegalStateException();
        checkConcurrentMods();
        remove(lastIndex);
    }

    @Override
    public void forEachRemaining(Consumer<? super E> consumer) {
        checkConcurrentMods();
        while (hasNext()) {
            consumer.accept(next());
        }
        checkConcurrentMods();
    }
}
