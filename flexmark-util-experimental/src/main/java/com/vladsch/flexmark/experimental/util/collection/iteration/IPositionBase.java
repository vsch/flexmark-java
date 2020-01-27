package com.vladsch.flexmark.experimental.util.collection.iteration;

import com.vladsch.flexmark.util.misc.Utils;
import com.vladsch.flexmark.util.sequence.PositionAnchor;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import static com.vladsch.flexmark.util.sequence.PositionAnchor.*;

public class IPositionBase<T, P extends IPosition<T, P>> implements IPosition<T, P> {
    final private static int F_CURRENT = 0;
    final private static int F_NEXT = 1;
    final private static int F_PREVIOUS = 2;
    final private static int F_VALID = 4;
    final private static int F_DETACHED = 8;
    final private static int F_SETTING = 16;  // setting element, index will not be updated

    final private @NotNull IPositionUpdater<T, P> myParent;
    final private @NotNull List<T> myList;
    private int myIndex;
    private byte myFlags;

    public IPositionBase(@NotNull IPositionUpdater<T, P> parent, int index, @NotNull PositionAnchor anchor) {
        myParent = parent;
        myList = myParent.getList();
        myIndex = index;
        myFlags = (byte) (F_VALID | (anchor == PREVIOUS ? F_PREVIOUS : anchor == NEXT ? F_NEXT : F_CURRENT));
    }

    /**
     * Anchor position effect on insert/delete, {@link IPosition#isValid()},
     * {@link IPosition#isValidIndex()} and {@link IPosition#isValidElement()}
     * <p>
     * {@link PositionAnchor#CURRENT} - tracks a specific element at index when the position is instantiated.
     * Adding elements before the position will shift the range down in the list.
     * Removing elements before the position will shift the range up in the list.
     * Removing elements which include the position's index will set its span to 0.
     * The position is always available for adding/removing next/previous elements. The
     * current element is only available when span is 1.
     * <p>
     * {@link PositionAnchor#PREVIOUS} - tracks the previous element to the position from which it was instantiated.
     * The span will be 0 if no previous element existed when it was instantiated (ie. at position 0)
     * or was removed later. The index reflects the position in the list previous to the position at time it was instantiated.
     * Adding elements before this position does not affect the position or span.
     * Adding elements before the previous position shifts the range down in the list.
     * Removing elements before the position shifts the range up in the list.
     * If the element previous to this position is removed, then the span is set to 0.
     * <p>
     * This anchor position type is used for iterating positions in reverse since it ignores
     * insertions into the list immediately before the current position.
     * <p>
     * {@link PositionAnchor#NEXT} - tracks the next element to the position from which it was instantiated.
     * The span will be 0 if no next element existed when it was instantiated (ie. at end of the list)
     * or was removed later.
     * Adding elements before this position will shift the range down in the list.
     * Removing elements before this position will shift the range up in the list.
     * Removing element which include the next element of this position will set its span to 0.
     * <p>
     * This anchor position type is used for iterating positions since it ignores
     * insertions into the list immediately after the current position.
     *
     * @param index position of insert
     * @param count number of elements inserted
     */
    @Override
    public void inserted(int index, int count) {
        if ((myFlags & F_DETACHED) != 0) {
            throw new IllegalStateException("Position is detached but still receiving notifications");
        }

        int positionIndex = myIndex;
        assert positionIndex + count <= myList.size();

        if ((myFlags & F_SETTING) == 0) {
            if ((myFlags & F_PREVIOUS) != 0) {
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
    }

    @Override
    public void deleted(int index, int count) {
        if ((myFlags & F_DETACHED) != 0) {
            throw new IllegalStateException("Position is detached but still receiving notifications");
        }

        if ((myFlags & F_SETTING) == 0) {
            int positionIndex = myIndex;

            if ((myFlags & F_PREVIOUS) != 0) {
                if (index <= positionIndex) {
                    if (index + count <= positionIndex) {
                        // deleting before position index, still valid
                        setIndex(positionIndex - count, true);
                    } else {
                        // FIX: should invalidate if previous element was removed, not only if at index 0
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
                        setIndex(index, (myFlags & F_NEXT) != 0);
                    }
                }
            }
        }
    }

    private void setIndex(int i, boolean isValid) {
        // can only invalidate this position
        if (isValid() && !isValid) myFlags &= ~F_VALID;
        myIndex = i;
    }

    @Override
    public boolean isValid() {
        // FIX: previous is valid if index > 0 or isValid, not
        // NOTE: PREVIOUS is valid if index > 0 or F_VALID
        //  CURRENT is valid if F_VALID
        //  NEXT is valid if index < size() or F_VALID
        return (myFlags & F_DETACHED) == 0 && ((myFlags & (F_VALID | F_NEXT)) != 0); // Next is always valid
    }

    @Override
    public void invalidate() {
        myFlags &= ~F_VALID;
    }

    @Override
    public boolean isDetached() {
        return (myFlags & F_DETACHED) != 0;
    }

    @Override
    public void detachListener() {
        //myDetachedTrace = Thread.currentThread().getStackTrace();
        myFlags |= F_DETACHED;
        myParent.removePositionListener(this);
    }

    @Override
    public void setDetached() {
        //myDetachedTrace = Thread.currentThread().getStackTrace();
        myFlags |= F_DETACHED;
    }

    @Override
    public void unframed() {
        validateDetached();
        //noinspection unchecked
        myParent.unframe((P) this);
    }

    @Override
    public @NotNull PositionAnchor getAnchor() {
        return ((myFlags & F_NEXT) != 0) ? NEXT : ((myFlags & F_PREVIOUS) != 0) ? PREVIOUS : CURRENT;
    }

    @NotNull
    @Override
    public P withAnchor(@NotNull PositionAnchor anchor) {
        if (getAnchor() != anchor) {
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
        if (isValid()) {
            return myIndex + offset;
        } else {
            // our index is already next, because position at 0 was invalidated
            return offset > 0 ? myIndex + offset - 1 : myIndex + offset;
        }
    }

    private void validateDetached() {
        if (isDetached())
            throw new IllegalStateException("Position is detached from its list");
    }

    private void validateOffset(int offset) {
        if (!isValid() && offset == 0)
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
        return myParent.getPosition(index, getAnchor());
    }

    @Override
    public P previous() {
        int index = getIndex(-1);
        validateIndex(index, -1);

        return myParent.getPosition(index, getAnchor());
    }

    @Override
    public P previousOrNull() {
        int index = getIndex(-1);
        return (index < 0 || index > myList.size()) ? null : myParent.getPosition(index, getAnchor());
    }

    @Override
    public P next() {
        int index = getIndex(1);
        validateIndex(index, 1);

        return myParent.getPosition(index, getAnchor());
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
        return (index < 0 || index > myList.size()) ? null : myParent.getPosition(index, getAnchor());
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
        int index = !isValidElement() ? getIndex(-1) : getIndex();
        int useIndex = Math.max(0, index);
        P position = myParent.getPosition(useIndex, PREVIOUS);
        if (useIndex > index) position.invalidate(); // nothing in the iterator
        return () -> myParent.iterator(position);
    }

    @Override
    public Iterable<P> previousBackwards() {
        validateDetached();
        int index = !isValidElement() ? getIndex(-2) : getIndex(-1);
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
    public boolean isValidElement() {
        validateDetached();

        return isValid() && myIndex < myList.size();
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
        myFlags |= F_SETTING;
        //noinspection unchecked
        T result = (T) myParent.changing(index, element);
        myFlags &= ~F_SETTING;
        return result;
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

        myParent.deleting(index, 1);
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
            myParent.deleting(startIndex, endIndex - startIndex);
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
        return itemIndex == -1 ? myParent.getPosition(myList.size(), getAnchor()) : myParent.getPosition(itemIndex + index, getAnchor());
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
        PositionAnchor anchor = getAnchor();

        for (int i = index; i < iMax; i++) {
            P pos = myParent.getPosition(i, anchor);
            if (predicate.test(pos)) {
                return pos;
            }
        }
        return myParent.getPosition(myList.size(), anchor);
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
        return itemIndex == -1 ? myParent.getPosition(myList.size(), getAnchor()) : myParent.getPosition(itemIndex, getAnchor());
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
        PositionAnchor anchor = getAnchor();

        for (int i = iMax; i-- > 0; ) {
            P pos = myParent.getPosition(i, anchor);
            if (predicate.test(pos)) {
                return pos;
            }
        }
        return myParent.getPosition(myList.size(), anchor);
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
        return "Position{anchor=" + getAnchor() + ", index=" + myIndex + ", valid=" + isValid() + '}';
    }
}
