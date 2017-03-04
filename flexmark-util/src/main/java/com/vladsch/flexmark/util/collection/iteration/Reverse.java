package com.vladsch.flexmark.util.collection.iteration;

import java.util.List;

@SuppressWarnings("WeakerAccess")
public class Reverse<T> implements ReversibleIterable<T> {
    private final List<T> myList;
    private final boolean myIsReversed;

    public Reverse(List<T> list) {
        this(list, true);
    }

    public Reverse(List<T> list, boolean isReversed) {
        myList = list;
        myIsReversed = isReversed;
    }

    static class ReversedListIterator<T> implements ReversibleIterator<T> {
        private final List<T> myList;
        private final boolean myIsReversed;
        private int index;

        public ReversedListIterator(List<T> list) {
            this(list, true);
        }

        ReversedListIterator(List<T> list, boolean isReversed) {
            myList = list;
            myIsReversed = isReversed;
            if (isReversed) {
                this.index = list.size() == 0 ? -1 : list.size() - 1;
            } else {
                this.index = list.size() == 0 ? -1 : 0;
            }
        }

        @Override
        public boolean isReversed() {
            return myIsReversed;
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
            T t = myList.get(index);
            if (index != -1) {
                if (myIsReversed) {
                    index--;
                } else {
                    if (index == myList.size() - 1) {
                        index = -1;
                    } else {
                        index++;
                    }
                }
            }

            return t;
        }
    }

    @Override
    public ReversibleIterator<T> iterator() {
        return new ReversedListIterator<T>(myList, myIsReversed);
    }

    @Override
    public ReversibleIterable<T> reversed() {
        return new Reverse<T>(myList, !myIsReversed);
    }

    @Override
    public boolean isReversed() {
        return myIsReversed;
    }

    @Override
    public ReversibleIterator<T> reversedIterator() {
        return new ReversedListIterator<T>(myList, !myIsReversed);
    }
}
