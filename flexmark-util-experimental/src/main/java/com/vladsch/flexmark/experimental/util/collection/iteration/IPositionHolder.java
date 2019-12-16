package com.vladsch.flexmark.experimental.util.collection.iteration;

import com.vladsch.flexmark.util.sequence.PositionAnchor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IPositionHolder<T, P extends IPositionHolder<T, P>> extends /*ListIterator<P>,*/ IPositionListener {

    /**
     * iterable for this position and all following positions in increasing index order
     *
     * @return iterable whose first element is this position, iteration proceeds toward index list.size()
     */
    Iterable<P> forwards();

    /**
     * Iterator for all following positions, excluding this position in increasing index order
     *
     * @return iterable whose first element is one after this position, iteration proceeds toward index list.size()
     */
    Iterable<P> nextForwards();

    /**
     * Iterable for this position and all previous positions in decreasing index order
     *
     * @return iterable whose first element is this position, iteration proceeds toward index 0
     */
    Iterable<P> backwards();

    /**
     * iterable for all previous positions, excluding this position in decreasing index order
     *
     * @return iterable whose first element is previous to this position, iteration proceeds toward index 0
     */
    Iterable<P> previousBackwards();

    /**
     * FIX: allow getIndex(offset) to return -1
     *
     * @return absolute index in list, even when this position does not refer to a valid element it will always be [-1, list.size()]
     *         if it is referring to a valid element then the returned range will be [0, list.size()-1]
     */
    int getIndex();

    /**
     * Tells listener to remove itself from parent
     * NOTE: used as optimization in iterators which are guaranteed not to have any other references to their position element.
     * <p>
     * Do not otherwise use this because this position will stop having the list modification updates and will throw {@link IllegalStateException}
     * on any attempt to use any methods of this position other than {@link #isDetached()}
     */
    void detachListener();
    boolean isDetached();

    /**
     * Tells the listener it has been detached by its parent list
     * NOTE: used as optimization in position list frames to detach any positions which have not been unframed()
     */
    void setDetached();

    /**
     * Mark this position as used outside the iteration frame in which it was created. It allows positions to not be detached when the frame is closed.
     */
    void unframed();

    /**
     * Position Anchor for this position
     *
     * @return position anchor
     */
    @NotNull PositionAnchor getAnchor();

    /**
     * Get a new position with requested anchor from this position
     * <p>
     * CURRENT - position represents the element and if deleted will be invalidated,
     * if inserting at or before index then will advance to keep position of element
     * <p>
     * NEXT - position represents the next element
     * <p>
     * PREVIOUS - position represents the previous element
     *
     * @param anchor desired anchor position
     * @return new position with requested or this if there is no anchor change
     */
    // FIX: remove this, use previous() and next() to get the right anchor type and index, this should never be used.
    //   positions need to be created with the right anchor or need to add isValidElement to this method for completion
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
    boolean isValidElement();

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
