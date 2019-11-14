package com.vladsch.flexmark.util.collection.iteration;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.WeakHashMap;

/**
 * Iterator for list positions allowing to iterate over current elements while inserting and deleting elements relative to current position
 * <p>
 * elements inserted at current position or at current + 1 position will skipped in the iteration allowing adding new elements which will not be part of the iteration
 *
 * @param <T>
 */
public abstract class PositionListBase<T, P extends Position<T, P>> implements Iterable<P> {
    final private @NotNull List<T> myList;
    final private @NotNull WeakHashMap<P, Boolean> myIndices = new WeakHashMap<>();
    final private @NotNull ListPositionFactory<T, P> myFactory;

    public PositionListBase(@NotNull List<T> list) {
        this(list, (parent, index, isValid) -> {
            //noinspection unchecked
            return (P) new ListPosition<T, P>(parent, index, isValid);
        });
    }

    public PositionListBase(@NotNull List<T> list, @NotNull ListPositionFactory<T, P> factory) {
        myList = list;
        myFactory = factory;
    }

    @NotNull
    public Iterator<P> iterator() {
        return new PositionIterator<T, P>(get(0));
    }

    @NotNull
    public List<T> getList() {
        return myList;
    }

    public P get(int index) {
        return getPosition(index, true);
    }

    public P getPosition(int index, boolean isValid) {
        if (index < 0 || index > myList.size())
            throw new IndexOutOfBoundsException("ListPosition.get(" + index + ", " + isValid + "), index out valid range [0, " + myList.size() + "]");

        P listPosition = myFactory.create(this, index, isValid);
        myIndices.put(listPosition, true);
        return listPosition;
    }

    public P getFirst() {
        return get(0);
    }

    public P getLast() {
        return myList.isEmpty() ? get(0) : get(myList.size() - 1);
    }

    public P getEnd() {
        return get(myList.size());
    }

    public void clear() {
        for (P position : myIndices.keySet()) {
            if (position != null) {
                position.setIndex(0, false);
            }
        }

        myList.clear();
        myIndices.clear();
    }

    void inserted(int index, int count) {
        assert count >= 0;
        assert index >= 0 && index <= myList.size() - count;

        for (P position : myIndices.keySet()) {
            if (position != null) {
                int positionIndex = position.getIndex();
                if (positionIndex >= index) {
                    assert positionIndex + count <= myList.size();
                    position.setIndex(positionIndex + count, true);
                }
            }
        }
    }

    void deleted(int index, int count) {
        assert count >= 0;
        assert index >= 0 && index + count <= myList.size() + count;

        for (P position : myIndices.keySet()) {
            if (position != null) {
                int positionIndex = position.getIndex();
                if (positionIndex >= index) {
                    if (positionIndex >= index + count) {
                        // still valid
                        position.setIndex(positionIndex - count, true);
                    } else {
                        // invalidated
                        position.setIndex(index, false);
                    }
                }
            }
        }
    }

    boolean addItem(T value) {
        int index = myList.size();
        myList.add(value);
        inserted(index, 1);
        return true;
    }

    @SuppressWarnings("UnusedReturnValue")
    boolean addItem(int index, T value) {
        assert index >= 0 && index <= myList.size();
        myList.add(index, value);
        inserted(index, 1);
        return true;
    }

    boolean addAllItems(int index, Collection<? extends T> values) {
        assert index >= 0 && index <= myList.size();
        myList.addAll(index, values);
        inserted(index, values.size());
        return true;
    }

    T removeItem(int index) {
        assert index >= 0 && index < myList.size();
        T value = myList.remove(index);
        deleted(index, 1);
        return value;
    }

    void removeItems(int startIndex, int endIndex) {
        if (startIndex < endIndex) {
            assert startIndex >= 0 && endIndex <= myList.size();
            myList.subList(startIndex, endIndex).clear();
            deleted(startIndex, endIndex - startIndex);
        }
    }

    public int size() {
        return myList.size();
    }

//    @Override
//    public int size() {
//        return myList.size();
//    }
//
//    @Override
//    public boolean isEmpty() {
//        return myList.isEmpty();
//    }
//
//    @Override
//    public boolean contains(Object o) {
//        return myList.contains(o);
//    }
//
//    @NotNull
//    @Override
//    public Object[] toArray() {
//        return new Object[0];
//    }
//
//    @NotNull
//    @Override
//    public Iterator<T> iterator() {
//        return myList.iterator();
//    }
//
//    @NotNull
//    @Override
//    public <T1> T1[] toArray(@NotNull T1[] a) {
//        return myList.toArray(a);
//    }
//
//    @Override
//    public boolean add(T t) {
//        return addItem(t);
//    }
//
//    @Override
//    public boolean remove(Object o) {
//        int index = indexOf(o);
//        if (myList.remove(o)) {
//            deleted(index, 1);
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    public boolean containsAll(@NotNull Collection<?> c) {
//        return myList.containsAll(c);
//    }
//
//    @Override
//    public boolean addAll(@NotNull Collection<? extends T> c) {
//        return addAllItems(myList.size(), c);
//    }
//
//    @Override
//    public boolean addAll(int index, @NotNull Collection<? extends T> c) {
//        return addAllItems(index, c);
//    }
//
//    @Override
//    public boolean removeAll(@NotNull Collection<?> c) {
//        boolean changed = false;
//        for (Object item : c) {
//            if (remove(item)) changed = true;
//        }
//        return changed;
//    }
//
//    @Override
//    public boolean retainAll(@NotNull Collection<?> c) {
//        int[] toRemove = new int[myList.size()];
//        int iMax = 0;
//        int index = 0;
//        for (T item : myList) {
//            if (!c.contains(item)) {
//                toRemove[iMax++] = index;
//            }
//            index++;
//        }
//
//        if (iMax > 0) {
//            for (int i = iMax; i-- > 0; ) {
//                removeItem(toRemove[i]);
//            }
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    public T get(int index) {
//        return myList.get(index);
//    }
//
//    @Override
//    public T set(int index, T element) {
//        return myList.set(index, element);
//    }
//
//    @Override
//    public void add(int index, T element) {
//        addItem(index, element);
//    }
//
//    @Override
//    public T remove(int index) {
//        return removeItem(index);
//    }
//
//    @Override
//    public int indexOf(Object o) {
//        return myList.indexOf(o);
//    }
//
//    @Override
//    public int lastIndexOf(Object o) {
//        return myList.lastIndexOf(o);
//    }
//
//    @NotNull
//    @Override
//    public ListIterator<T> listIterator() {
//        throw new IllegalStateException("Not supported");
//    }
//
//    @NotNull
//    @Override
//    public ListIterator<T> listIterator(int index) {
//        throw new IllegalStateException("Not supported");
//    }
//
//    @NotNull
//    @Override
//    public List<T> subList(int fromIndex, int toIndex) {
//        throw new IllegalStateException("Not supported");
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PositionListBase)) return false;

        PositionListBase<?, ?> list = (PositionListBase<?, ?>) o;

        return myList.equals(list.myList);
    }

    @Override
    public int hashCode() {
        return myList.hashCode();
    }
}
