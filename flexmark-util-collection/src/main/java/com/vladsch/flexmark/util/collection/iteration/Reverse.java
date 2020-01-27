package com.vladsch.flexmark.util.collection.iteration;

import org.jetbrains.annotations.NotNull;

import java.util.List;

@SuppressWarnings("WeakerAccess")
public class Reverse<T> implements ReversibleIterable<T> {
    final private @NotNull List<T> list;
    final private boolean isReversed;

    public Reverse(@NotNull List<T> list) {
        this(list, true);
    }

    public Reverse(@NotNull List<T> list, boolean isReversed) {
        this.list = list;
        this.isReversed = isReversed;
    }

    static class ReversedListIterator<T> implements ReversibleIterator<T> {
        final private @NotNull List<T> list;
        final private boolean isReversed;
        private int index;

        ReversedListIterator(@NotNull List<T> list, boolean isReversed) {
            this.list = list;
            this.isReversed = isReversed;
            if (isReversed) {
                this.index = list.size() == 0 ? -1 : list.size() - 1;
            } else {
                this.index = list.size() == 0 ? -1 : 0;
            }
        }

        @Override
        public boolean isReversed() {
            return isReversed;
        }

        @Override
        public void remove() {

        }

        @Override
        public boolean hasNext() {
            return index != -1;
        }

        @Override
        public T next() {
            T t = list.get(index);
            if (index != -1) {
                if (isReversed) {
                    index--;
                } else {
                    if (index == list.size() - 1) {
                        index = -1;
                    } else {
                        index++;
                    }
                }
            }

            return t;
        }
    }

    @NotNull
    @Override
    public ReversibleIterator<T> iterator() {
        return new ReversedListIterator<>(list, isReversed);
    }

    @NotNull
    @Override
    public ReversibleIterable<T> reversed() {
        return new Reverse<>(list, !isReversed);
    }

    @Override
    public boolean isReversed() {
        return isReversed;
    }

    @NotNull
    @Override
    public ReversibleIterator<T> reversedIterator() {
        return new ReversedListIterator<>(list, !isReversed);
    }
}
