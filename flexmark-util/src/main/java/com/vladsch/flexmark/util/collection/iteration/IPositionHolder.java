package com.vladsch.flexmark.util.collection.iteration;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IPositionHolder<T, P extends IPositionHolder<T, P>> extends /*ListIterator<P>,*/ IPositionListener {

    /**
     * iterable for all next elements
     *
     * @return iterable whose first element is this position, iteration proceeds toward index list.size()
     */
    Iterable<P> forwards();

    /**
     * Iterator for all next elements
     *
     * @return iterable whose first element is one after this position, iteration proceeds toward index list.size()
     */
    Iterable<P> nextForwards();

    /**
     * Iterable for all previous elements
     *
     * @return iterable whose first element is this position, iteration proceeds toward index 0
     */
    Iterable<P> backwards();

    /**
     * iterable for all previous elements
     *
     * @return iterable whose first element is previous to this position, iteration proceeds toward index 0
     */
    Iterable<P> previousBackwards();

    /**
     * @return absolute index in list, even if this position is not valid, it will always be [0, list.size()]
     */
    int getIndex();

    /**
     * Tells listener to remove itself from parent
     * used only in iterator that never returns its position element so it is guaranteed to be the only
     * holder of the position. Do not otherwise use this because other holders of this position will stop
     * having the position updated when list is modified.
     */
    void detachListener();

    /**
     * Position Anchor for this position
     *
     * @return position anchor
     */
    @NotNull PositionAnchor getAnchor();

    /**
     * Set the position Anchor for this position
     * <p>
     * NONE - position represents the element and if deleted will be invalidated,
     * if inserting at or before index then will advance to keep position of element
     * <p>
     * NEXT - position represents the next element
     * if inserting at or before index then will advance to keep position of element
     * when deleted will move the index to the next element after deleted region and remain valid unless region length is Integer.MAX_VALUE meaning all deleted
     * <p>
     * PREVIOUS - position represents the previous element
     * when deleted will keep index at the start of the deleted region-1 and remain valid unless resulting previous index is &lt;0
     * when inserting at the index the position index does not change,
     * when inserting at position index-1 then will move index to start of insert region so previous element remains unchanged
     * when inserting before position index-1 then will advance position so previous element remains unchanged
     *
     * @param anchor desired anchor position
     * @return new position with  anchor or this if no anchor change
     */
    @NotNull P withAnchor(@NotNull PositionAnchor anchor);

    /**
     * Get previous index position relative to current,
     * returns previous index if it exists even when this position is not valid
     *
     * @return previous valid position relative to this position, with the same position anchor as this position
     *         throws {@link IndexOutOfBoundsException} if there is no elements before current position
     */
    P previous();

    /**
     * Return valid previous position, with the same position anchor as this position, or null if does not exist. Does not throw exceptions
     *
     * @return previous position or null
     */
    @Nullable
    P previousOrNull();

    /**
     * Get next index position relative to current,
     * returns next position if it exists even when this position is not valid
     *
     * @return previous valid position relative to this position, with the same position anchor as this position
     *         throws {@link IndexOutOfBoundsException} if there is no elements before current position
     */
    P next();

    /**
     * Return valid next position, with the same position anchor as this position, or null if does not exist. Does not throw exceptions
     *
     * @return next position or null
     */
    @Nullable
    P nextOrNull();

    /**
     * Returns the index of the element that would be returned by a
     * subsequent call to {@link #next}. (Returns list size if the list
     * iterator is at the end of the list.)
     *
     * @return the index of the element that would be returned by a
     *         subsequent call to {@code next}, or list size if the list
     *         iterator is at the end of the list
     */
    int nextIndex();

    /**
     * Returns the index of the element that would be returned by a
     * subsequent call to {@link #previous}. (Returns -1 if the list
     * iterator is at the beginning of the list.)
     *
     * @return the index of the element that would be returned by a
     *         subsequent call to {@code previous}, or -1 if the list
     *         iterator is at the beginning of the list
     */
    int previousIndex();

    /**
     * @return true if this position was not invalidated by deleting the element at index
     */
    boolean isValid();

    /**
     * @return true if getPreviousIndex() will return a value, false if {@link #previous()} will throw an exception
     */
    boolean hasPrevious();

    /**
     * @return true if getNextIndex() will return a value, false if {@link #next()} will throw an exception
     */
    boolean hasNext();

    /**
     * @return true if this position represents a valid element in the list, ie. isValid() is true and index is at or before last element in list
     */
    boolean isValidPosition();

    /**
     * @return removed current element, throws IllegalStateException if current index is not valid or after last element of list
     */
    T remove();

    /**
     * Listeners should not change hashCode() or equals() from their default implementation where the instance is only equal to itself.
     * Otherwise, a new instance can remove an old one from the weak hash map used to hold listener instances.
     *
     * @return hash
     */
    int hashCode();

    /**
     * Listeners should not change hashCode() or equals() from their default implementation where the instance is only equal to itself.
     * Otherwise, a new instance can remove an old one from the weak hash map used to hold listener instances.
     *
     * @return hash
     */
    boolean equals(Object other);
}
