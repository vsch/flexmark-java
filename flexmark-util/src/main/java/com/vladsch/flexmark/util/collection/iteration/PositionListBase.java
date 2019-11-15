package com.vladsch.flexmark.util.collection.iteration;

import com.vladsch.flexmark.util.Utils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.TestOnly;

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
public abstract class PositionListBase<T, P extends IPositionHolder<T, P>> implements Iterable<P>, IPositionUpdater<T, P> {
    final private @NotNull List<T> myList;
    final private @NotNull WeakHashMap<IPositionListener, Boolean> myListeners = new WeakHashMap<>(); // true if listener is P, false otherwise
    final private @NotNull PositionFactory<T, P> myFactory;

    public PositionListBase(@NotNull List<T> list, @NotNull PositionFactory<T, P> factory) {
        myList = list;
        myFactory = factory;
    }

    @TestOnly
    public int trackedPositions() {
        int count = 0;
        for (IPositionListener position : myListeners.keySet()) {
            if (position != null) {
                count++;
            }
        }

        if (count != myListeners.size()) {
            int tmp = 0;
        }
        return count;
    }

    /**
     * Add list edit listener for notifications of mods to the list
     *
     * @param listener listener
     */
    @Override
    public void addPositionListener(@NotNull IPositionListener listener) {
        myListeners.put(listener, false);
    }

    /**
     * Remove list edit listener
     * <p>
     * NOTE: removal is optional. Only weak refs are kept for the listener
     *
     * @param listener listener
     */
    @Override
    public void removePositionListener(@NotNull IPositionListener listener) {
        myListeners.remove(listener);
    }

    @NotNull
    public Iterator<P> iterator() {
        return iterator(getPosition(0, PositionAnchor.NEXT));
    }

    @NotNull
    public Iterator<P> reversedIterator() {
        return iterator(getPosition(Math.max(0, myList.size() - 1), PositionAnchor.PREVIOUS));
    }

    @NotNull
    public Iterable<P> reversed() {
        return this::reversedIterator;
    }

    @NotNull
    public Iterator<P> iterator(@NotNull P position) {
        assert position.getAnchor() != PositionAnchor.NONE;
        return new PositionIterator<T, P>(position);
    }

    @NotNull
    public List<T> getList() {
        return myList;
    }

    public T get(int index) {
        return myList.get(index);
    }

    public T getOrNull(int index) {
        return Utils.getOrNull(myList, index);
    }

    public <S extends T> S getOrNull(int index, Class<S> elementClass) {
        return Utils.getOrNull(myList, index, elementClass);
    }

    public T set(int index, T value) {
        return Utils.setOrAdd(myList, index, value);
    }

    @Override
    public P getPosition(int index, @NotNull PositionAnchor anchor) {
        if (index < 0 || index > myList.size())
            throw new IndexOutOfBoundsException("ListPosition.get(" + index + "), index out valid range [0, " + myList.size() + "]");

        P listPosition = myFactory.create(this, index, anchor);
        myListeners.put(listPosition, true);
        return listPosition;
    }

    public P getFirst() {
        return getPosition(0, PositionAnchor.NONE);
    }

    public P getLast() {
        return myList.isEmpty() ? getPosition(0, PositionAnchor.NONE) : getPosition(myList.size() - 1, PositionAnchor.NONE);
    }

    public P getEnd() {
        return getPosition(myList.size(), PositionAnchor.NONE);
    }

    @Override
    public void clear() {
        if (!myList.isEmpty()) {
            myList.clear();
            deleted(0, Integer.MAX_VALUE);

            myListeners.clear();
        }
    }

    @Override
    public void inserted(int index, int count) {
        assert count >= 0;
        assert index >= 0 && index <= myList.size() - count;

        for (IPositionListener position : myListeners.keySet()) {
            if (position != null) {
                position.inserted(index, count);
            }
        }
    }

    @Override
    public void deleted(int index, int count) {
        assert count >= 0;
        assert index >= 0 && index + count <= myList.size() + count;

        for (IPositionListener position : myListeners.keySet()) {
            if (position != null) {
                position.deleted(index, count);
            }
        }
    }

    public boolean add(T element) {
        int index = myList.size();
        myList.add(element);
        inserted(index, 1);
        return true;
    }

    @SuppressWarnings("UnusedReturnValue")
    public boolean add(int index, T element) {
        assert index >= 0 && index <= myList.size();
        myList.add(index, element);
        inserted(index, 1);
        return true;
    }

    public boolean addAll(@NotNull PositionListBase<T, P> other) {
        return addAll(myList.size(), other.myList);
    }

    public boolean addAll(int index, @NotNull PositionListBase<T, P> other) {
        return addAll(index, other.myList);
    }

    public boolean addAll(@NotNull Collection<? extends T> elements) {
        return addAll(myList.size(), elements);
    }

    public boolean addAll(int index, @NotNull Collection<? extends T> elements) {
        assert index >= 0 && index <= myList.size();
        myList.addAll(index, elements);
        inserted(index, elements.size());
        return true;
    }

    public T remove(int index) {
        assert index >= 0 && index < myList.size();
        T value = myList.remove(index);
        deleted(index, 1);
        return value;
    }

    public void remove(int startIndex, int endIndex) {
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
