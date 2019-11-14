package com.vladsch.flexmark.util.collection.iteration;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

class ListPositionBase<T, P extends IPosition<T, P>> implements IPosition<T, P> {
    final private @NotNull PositionListBase<T, P> myParent;
    final private @NotNull List<T> myList;
    private int myIndex;
    private boolean myIsValid;

    public ListPositionBase(@NotNull PositionListBase<T, P> parent, int index, boolean isValid) {
        myParent = parent;
        myList = parent.getList();
        myIndex = index;
        myIsValid = isValid;
    }

    @Override
    public int getIndex() {
        return myIndex;
    }

    @Override
    public void setIndex(int i, boolean isValid) {
        // can only invalidate this position
        if (myIsValid) myIsValid = isValid;
        myIndex = i;
    }

    /**
     * throw if this position is not valid
     */
    private void validate() {
        if (!myIsValid) throw new IllegalStateException("ListPosition is not valid");
    }

    /**
     * throw if index is out list index range
     */
    private void validateIndex(int index) {
        if (myIndex + index < 0 || myIndex + index > myParent.getList().size())
            throw new IndexOutOfBoundsException("ListPosition at " + myIndex + " index: " + index + " is out of range [-" + myIndex + ", " + (myParent.getList().size() - myIndex) + "]");
    }

    /**
     * throw if not valid or index is out list index range
     */
    private void validateWithIndex(int index) {
        validate();
        validateIndex(index);
    }

    /**
     * throw if not valid or index is out list index range of element indices
     */
    private void validateWithElementIndex(int index) {
        validate();
        if (myIndex + index < 0 || myIndex + index >= myParent.getList().size())
            throw new IndexOutOfBoundsException("ListIndex at " + myIndex + " index: " + index + " is out of range [-" + myIndex + ", " + (myParent.getList().size() - myIndex) + ")");
    }

    public P getPosition(int index) {
        validateWithIndex(index);
        return myParent.get(myIndex + index);
    }

    @Override
    public P getPrevious() {
        validateIndex(-1);
        return myParent.get(myIndex - 1);
    }

    @Override
    public P getNext() {
        if (myIsValid) {
            validateIndex(1);
            return myParent.get(myIndex + 1);
        } else {
            validateIndex(0);
            return myParent.get(myIndex);
        }
    }

    @Override
    public boolean isValid() {
        return myIsValid;
    }

    @Override
    public boolean isValidIndex() {
        return myIndex <= myList.size();
    }

    @Override
    public boolean isValidPosition() {
        return myIsValid && myIndex < myList.size();
    }

    @Override
    public boolean isPreviousValid() {
        return myIndex > 0;
    }

    @Override
    public boolean isNextValid() {
        return myIndex + (myIsValid ? 1 : 0) < myList.size();
    }

    @Override
    public T get() {
        return get(0);
    }

    @Override
    public <S extends T> S getOrNull(Class<S> elementClass) {
        return getOrNull(0, elementClass);
    }

    @Override
    public void set(T element) {
        set(0, element);
    }

    @Override
    public T remove() {
        return remove(0);
    }

    @Override
    public boolean add(T element) {
        return add(0, element);
    }

    @Override
    public boolean addAll(@NotNull Collection<T> elements) {
        return addAll(0, elements);
    }

    @Override
    public boolean addAll(int index, @NotNull Collection<T> elements) {
        validateWithIndex(index);
        return myParent.addAllItems(myIndex + index, elements);
    }

    @Override
    public int size() {
        return myList.size();
    }

    @Override
    public boolean isEmpty() {
        return myList.isEmpty();
    }

    @Override
    public boolean append(T element) {
        return myParent.addItem(element);
    }

    @Override
    public void clear() {
        myParent.clear();
    }

    @Override
    public T get(int index) {
        validateWithElementIndex(index);
        return myList.get(myIndex + index);
    }

    @Override
    public <S extends T> S getOrNull(int index, Class<S> elementClass) {
        validateWithIndex(index);
        T element = myList.get(myIndex + index);
        //noinspection unchecked
        return elementClass.isInstance(element) ? (S) element : null;
    }

    @Override
    public T set(int index, T element) {
        validateWithIndex(index);
        if (myIndex + index == myList.size()) {
            myParent.addItem(element);
            return null;
        } else {
            return myList.set(myIndex + index, element);
        }
    }

    @Override
    public boolean add(int index, T element) {
        validateWithIndex(index);
        myParent.addItem(myIndex + index, element);
        return true;
    }

    @Override
    public T remove(int index) {
        validateWithElementIndex(index);
        return myParent.removeItem(myIndex + index);
    }

    @Override
    public void remove(int startIndex, int endIndex) {
        validateWithIndex(startIndex);
        validateWithIndex(endIndex);
        if (startIndex > endIndex)
            throw new IllegalArgumentException("startIndex: " + startIndex + " must be less than endIndex: " + endIndex);
        myParent.removeItems(myIndex + startIndex, myIndex + endIndex);
    }

    @Override
    public P indexOf(T o) {
        return indexOf(0, o);
    }

    @Override
    public P indexOf(int index, T o) {
        validateWithIndex(index);
        //noinspection SuspiciousMethodCalls
        int itemIndex = myList.subList(myIndex + index, myList.size()).indexOf(o);
        return itemIndex == -1 ? myParent.getPosition(myList.size(), false) : myParent.get(itemIndex + myIndex + index);
    }

    @Override
    public P indexOf(@NotNull Predicate<P> predicate) {
        return indexOf(0, predicate);
    }

    @Override
    public P indexOf(int index, @NotNull Predicate<P> predicate) {
        validateWithIndex(index);
        int iMax = myList.size();
        for (int i = myIndex + index; i < iMax; i++) {
            P pos = myParent.get(i);
            if (predicate.test(pos)) {
                return pos;
            }
        }
        return myParent.getPosition(myList.size(), false);
    }

    @Override
    public P lastIndexOf(T o) {
        return lastIndexOf(0, o);
    }

    @Override
    public P lastIndexOf(int index, T o) {
        validateWithIndex(index);
        //noinspection SuspiciousMethodCalls
        int itemIndex = myList.subList(0, myIndex + index).lastIndexOf(o);
        return itemIndex == -1 ? myParent.getPosition(myList.size(), false) : myParent.get(itemIndex);
    }

    @Override
    public P lastIndexOf(@NotNull Predicate<P> predicate) {
        return lastIndexOf(0, predicate);
    }

    @Override
    public P lastIndexOf(int index, @NotNull Predicate<P> predicate) {
        validateWithIndex(index);
        int iMax = myIndex + index;
        for (int i = iMax; i-- > 0; ) {
            P pos = myParent.get(i);
            if (predicate.test(pos)) {
                return pos;
            }
        }
        return myParent.getPosition(myList.size(), false);
    }
}
