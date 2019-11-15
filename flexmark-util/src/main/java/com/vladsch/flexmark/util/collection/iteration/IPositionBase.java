package com.vladsch.flexmark.util.collection.iteration;

import com.vladsch.flexmark.util.Utils;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import static com.vladsch.flexmark.util.collection.iteration.PositionAnchor.*;

public class IPositionBase<T, P extends IPosition<T, P>> implements IPosition<T, P> {
    final private @NotNull IPositionUpdater<T, P> myParent;
    final private @NotNull List<T> myList;
    final private @NotNull PositionAnchor myAnchor;
    private int myIndex;
    private boolean myIsValid;
    private boolean myIsDetached;

    public IPositionBase(@NotNull IPositionUpdater<T, P> parent, int index, @NotNull PositionAnchor anchor) {
        myParent = parent;
        myList = parent.getList();
        myIndex = index;
        myAnchor = anchor;
        myIsValid = true;
        myIsDetached = false;
    }

    /**
     * Anchor position effect on insert/delete and isValid()
     * <p>
     * NONE - position represents the element and if deleted will be invalidated,
     * if inserting at or before index then will advance to keep position of element
     * <p>
     * NEXT - position represents the next element
     * if inserting at or before index then will advance to keep position of element
     * when deleted will move the index to the next element after deleted region or end of list and remain valid
     * <p>
     * PREVIOUS - position represents the previous element
     * when deleted will keep index at the start of the deleted region and remain valid
     * when inserting at the index the position index does not change,
     * when inserting at position index-1 then will move index to start of insert region so previous element remains unchanged
     * when inserting before position index-1 then will advance position so previous element remains unchanged
     */
    @Override
    public void inserted(int index, int count) {
        int positionIndex = myIndex;
        assert positionIndex + count <= myList.size();

        if (myAnchor == PREVIOUS) {
            if (index <= positionIndex) {
                if (index == positionIndex - 1) {
                    // inserted at our position, move to start of inserted region
                    setIndex(index, true);
                } else if (index != positionIndex) {
                    // inserting before our previous
                    setIndex(positionIndex + count, true);
                }
            }
        } else {
            if (index <= positionIndex) {
                assert positionIndex + count <= myList.size();
                setIndex(positionIndex + count, true);
            }
        }
    }

    @Override
    public void deleted(int index, int count) {
        int positionIndex = myIndex;
        if (myAnchor == PREVIOUS) {
            if (index <= positionIndex) {
                if (index + count <= positionIndex) {
                    // deleting before position index, still valid
                    setIndex(positionIndex - count, true);
                } else {
                    // deleted element, position on previous element
                    if (index > 0) {
                        setIndex(index - 1, true);
                    } else {
                        setIndex(index, false);
                    }
                }
            }
        } else {
            if (index <= positionIndex) {
                if (index + count <= positionIndex) {
                    // deleting before position index, still valid
                    setIndex(positionIndex - count, true);
                } else {
                    // deleted element, position on next element and invalidate
                    if (myAnchor == NONE) {
                        setIndex(index, false);
                    } else {
                        setIndex(index, true);
                    }
                }
            }
        }
    }

    private void setIndex(int i, boolean isValid) {
        // can only invalidate this position
        if (myIsValid) myIsValid = isValid;
        myIndex = i;
    }

    @Override
    public boolean isValid() {
        return myIsValid || myAnchor == NEXT; // Next is always valid
    }

    @Override
    public void invalidate() {
        myIsValid = false;
    }

    @Override
    public void detachListener() {
        myIsDetached = true;
        myParent.removePositionListener(this);
    }

    @Override
    public @NotNull PositionAnchor getAnchor() {
        return myAnchor;
    }

    @NotNull
    @Override
    public P withAnchor(@NotNull PositionAnchor anchor) {
        if (myAnchor != anchor) {
            return myParent.getPosition(myIndex, anchor);
        } else {
            //noinspection unchecked
            return (P) this;
        }
    }

    @Override
    public int getIndex() {
        return myIndex;
    }

    @Override
    public int getIndex(int offset) {
        if (myIsValid) {
            return myIndex + offset;
        } else {
            // our index is already next, because position at 0 was invalidated
            return offset > 0 ? myIndex + offset - 1 : myIndex + offset;
        }
    }

    private void validateDetached() {
        if (myIsDetached)
            throw new IllegalStateException("Position is detached from list");
    }

    private void validateOffset(int offset) {
        if (!myIsValid && offset == 0)
            throw new IllegalStateException("Position is not valid");
    }

    /**
     * throw if offset is out list index range
     */
    private void validateIndex(int index, int offset) {
        validateDetached();

        if (index < 0 || index > myList.size())
            throw new IndexOutOfBoundsException("ListPosition at " + myIndex + " offset: " + offset + " is out of range [" + (-myIndex) + ", " + (myList.size() - myIndex) + "]");
    }

    /**
     * throw if not valid or index is out list index range
     */
    private void validateWithIndex(int index, int offset) {
        validateDetached();
        validateOffset(offset);

        if (index < 0 || index > myList.size())
            throw new IndexOutOfBoundsException("ListPosition at " + myIndex + " offset: " + offset + " is out of range [" + (-myIndex) + ", " + (myList.size() - myIndex) + "]");
    }

    /**
     * throw if not valid or index is out list index range of element indices
     */
    private void validateWithElementIndex(int index, int offset) {
        validateDetached();
        validateOffset(offset);

        if (index < 0 || index >= myList.size())
            throw new IndexOutOfBoundsException("ListIndex at " + myIndex + " offset: " + offset + " is out of range [" + (-myIndex) + ", " + (myList.size() - myIndex) + ")");
    }

    public P getPosition(int offset) {
        int index = getIndex(offset);
        validateWithIndex(index, offset);
        return myParent.getPosition(index, myAnchor);
    }

    @Override
    public P previous() {
        int index = getIndex(-1);
        validateIndex(index, -1);

        return myParent.getPosition(index, myAnchor);
    }

    @Override
    public P previousOrNull() {
        int index = getIndex(-1);
        return (index < 0 || index > myList.size()) ? null : myParent.getPosition(index, myAnchor);
    }

    @Override
    public P next() {
        int index = getIndex(1);
        validateIndex(index, 1);

        return myParent.getPosition(index, myAnchor);
    }

    @Override
    public int nextIndex() {
        validateDetached();

        int index = getIndex(1);
        validateDetached();
        return index;
    }

    @Override
    public int previousIndex() {
        validateDetached();

        int index = getIndex(-1);
        validateDetached();
        return index;
    }

    @Override
    public P nextOrNull() {
        validateDetached();

        int index = getIndex(1);
        return (index < 0 || index > myList.size()) ? null : myParent.getPosition(index, myAnchor);
    }

    @Override
    public Iterable<P> forwards() {
        validateDetached();
        return () -> myParent.iterator(withAnchor(NEXT));
    }

    @Override
    public Iterable<P> nextForwards() {
        validateDetached();
        return () -> myParent.iterator(myParent.getPosition(getIndex(1), NEXT));
    }

    @Override
    public Iterable<P> backwards() {
        validateDetached();
        int index = !isValidPosition() ? getIndex(-1) : getIndex();
        int useIndex = Math.max(0, index);
        P position = myParent.getPosition(useIndex, PREVIOUS);
        if (useIndex > index) position.invalidate(); // nothing in the iterator
        return () -> myParent.iterator(position);
    }

    @Override
    public Iterable<P> previousBackwards() {
        validateDetached();
        int index = !isValidPosition() ? getIndex(-2) : getIndex(-1);
        int useIndex = Math.max(0, index);
        P position = myParent.getPosition(useIndex, PREVIOUS);
        if (useIndex > index) position.invalidate(); // nothing in the iterator
        return () -> myParent.iterator(position);
    }

    @Override
    public boolean isValidIndex() {
        validateDetached();

        return myIndex <= myList.size();
    }

    @Override
    public boolean isValidPosition() {
        validateDetached();

        return myIsValid && myIndex < myList.size();
    }

    @Override
    public boolean hasPrevious() {
        validateDetached();

        return getIndex(-1) >= 0;
    }

    @Override
    public boolean hasNext() {
        validateDetached();

        return getIndex(1) < myList.size();
    }

    @Override
    public T get() {
        return get(0);
    }

    @Override
    public T get(int offset) {
        int index = getIndex(offset);
        validateWithElementIndex(index, offset);

        return myList.get(index);
    }

    @Override
    public T getOrNull() {
        return getOrNull(0);
    }

    @Override
    public T getOrNull(int offset) {
        validateDetached();
        return Utils.getOrNull(myList, getIndex(offset));
    }

    @Override
    public <S extends T> S getOrNull(Class<S> elementClass) {
        validateDetached();
        return getOrNull(0, elementClass);
    }

    @Override
    public <S extends T> S getOrNull(int offset, Class<S> elementClass) {
        validateDetached();
        return Utils.getOrNull(myList, getIndex(offset), elementClass);
    }

    @Override
    public void set(T element) {
        set(0, element);
    }

    @Override
    public T set(int offset, T element) {
        int index = getIndex(offset);
        validateWithIndex(index, offset);
        if (index == myList.size()) {
            // set will do an insert, need to inform position holders otherwise infinite loops occur
            T value = Utils.setOrAdd(myList, index, element);
            myParent.inserted(index, 1);
            return value;
        } else {
            return Utils.setOrAdd(myList, index, element);
        }
    }

    @Override
    public boolean add(T element) {
        return add(0, element);
    }

    @Override
    public boolean add(int offset, T element) {
        int index = getIndex(offset);
        validateWithIndex(index, offset);

        myList.add(index, element);
        myParent.inserted(index, 1);
        return true;
    }

    @Override
    public boolean addAll(@NotNull Collection<T> elements) {
        return addAll(0, elements);
    }

    @Override
    public boolean addAll(int offset, @NotNull Collection<T> elements) {
        int index = getIndex(offset);
        validateWithIndex(index, offset);

        boolean result = myList.addAll(index, elements);
        myParent.inserted(index, elements.size());
        return result;
    }

    @Override
    public T remove() {
        return remove(0);
    }

    @Override
    public T remove(int offset) {
        int index = getIndex(offset);
        validateWithElementIndex(index, offset);

        T value = myList.remove(index);
        myParent.deleted(index, 1);
        return value;
    }

    @Override
    public void remove(int startOffset, int endOffset) {
        int startIndex = getIndex(startOffset);
        int endIndex = getIndex(endOffset);
        validateWithElementIndex(startIndex, startOffset);
        validateWithIndex(endIndex, endOffset);

        if (startOffset > endOffset)
            throw new IllegalArgumentException("startOffset: " + startOffset + " must be less than endOffset: " + endOffset);

        if (startIndex < endIndex) {
            myList.subList(startIndex, endIndex).clear();
            myParent.deleted(startIndex, endIndex - startIndex);
        }
    }

    @Override
    public int maxOffset() {
        return myList.size() - myIndex;
    }

    @Override
    public int minOffset() {
        return -myIndex;
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
        validateDetached();

        int index = myList.size();
        myList.add(element);
        myParent.inserted(index, 1);
        return true;
    }

    @Override
    public P indexOf(T o) {
        return indexOf(0, o);
    }

    @Override
    public P indexOf(int offset, T o) {
        int index = getIndex(offset);
        validateWithIndex(index, offset);

        int itemIndex = myList.subList(index, myList.size()).indexOf(o);
        return itemIndex == -1 ? myParent.getPosition(myList.size(), myAnchor) : myParent.getPosition(itemIndex + index, myAnchor);
    }

    @Override
    public P indexOf(@NotNull Predicate<P> predicate) {
        return indexOf(0, predicate);
    }

    @Override
    public P indexOf(int offset, @NotNull Predicate<P> predicate) {
        int index = getIndex(offset);
        validateWithIndex(index, offset);

        int iMax = myList.size();
        for (int i = index; i < iMax; i++) {
            P pos = myParent.getPosition(i, myAnchor);
            if (predicate.test(pos)) {
                return pos;
            }
        }
        return myParent.getPosition(myList.size(), myAnchor);
    }

    @Override
    public P lastIndexOf(T o) {
        return lastIndexOf(0, o);
    }

    @Override
    public P lastIndexOf(int offset, T o) {
        int index = getIndex(offset);
        validateWithIndex(index, offset);

        int itemIndex = myList.subList(0, index).lastIndexOf(o);
        return itemIndex == -1 ? myParent.getPosition(myList.size(), myAnchor) : myParent.getPosition(itemIndex, myAnchor);
    }

    @Override
    public P lastIndexOf(@NotNull Predicate<P> predicate) {
        return lastIndexOf(0, predicate);
    }

    @Override
    public P lastIndexOf(int offset, @NotNull Predicate<P> predicate) {
        int index = getIndex(offset);
        validateWithIndex(index, offset);

        //noinspection UnnecessaryLocalVariable
        int iMax = index;
        for (int i = iMax; i-- > 0; ) {
            P pos = myParent.getPosition(i, myAnchor);
            if (predicate.test(pos)) {
                return pos;
            }
        }
        return myParent.getPosition(myList.size(), myAnchor);
    }

    @Override
    final public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    final public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "Position{anchor=" + myAnchor + ", index=" + myIndex + ", valid=" + myIsValid + '}';
    }
}
