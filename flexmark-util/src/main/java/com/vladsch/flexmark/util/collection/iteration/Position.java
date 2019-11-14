package com.vladsch.flexmark.util.collection.iteration;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.function.Predicate;

/**
 * Represents an index position in a list and behaves similar to the list.
 * <p>
 * All indexed operations on the list are relative to this index position, so may be -ve as long as absolute index is &gt;>0
 * <p>
 * Any insertions/deletions before current index will adjust the current to reflect the same element. If element at current index
 * is removed then the current index will be invalidated and trying to get the element will throw IllegalStateException.
 * <p>
 * This means that you can get instances to the list at different indices before making modifications and then modify the list
 * by adding/removing elements and all instances will be invalidated if their element was removed
 * or shifted to point to the same index if the operation was performed before their current index.
 *
 * @param <T> type of element held in the list
 */
public interface Position<T, P extends Position<T, P>> {
    /**
     * @return absolute index in list, even if this position is not valid, the index will always be [0, size()]
     */
    int getIndex();

    /**
     * Internal for changing indices and invalidating positions
     * @param index new index
     * @param isValid false if should be invalidated, if already invalid should ignore true
     */
    void setIndex(int index, boolean isValid);

    /**
     * @param index index relative to current position
     * @return ListIndex representing the index relative to current position,
     *         throws {@link IllegalStateException} if current position is not valid
     *         throws {@link IndexOutOfBoundsException} if requested index results in absolute index &lt;0 or &gt;size() of the list
     */
    P getPosition(int index);

    /**
     * Get previous index position relative to current,
     * returns previous index if it exists even when this position is not valid
     *
     * @return previous valid position relative to this position,
     *         throws {@link IndexOutOfBoundsException} if there is no elements before current position
     */
    P getPrevious();
    /**
     * Get next index position relative to current,
     * returns previous index if it exists even when this position is not valid
     *
     * @return previous valid position relative to this position,
     *         throws {@link IndexOutOfBoundsException} if there is no elements before current position
     */
    P getNext();

    /**
     * @return true if this position was not invalidated by deleting the element at index
     */
    boolean isValid();

    /**
     * @return true if index of this position is between 0 and size() of the list
     */
    boolean isValidIndex();

    /**
     * @return true if this position represents a valid element in the list, ie. isValid() is true and index is at or before last element in list
     */
    boolean isValidPosition();

    /**
     * @return true if getPreviousIndex() will return a value, false if {@link #getPrevious()} will throw an exception
     */
    boolean isPreviousValid();
    /**
     * @return true if getNextIndex() will return a value, false if {@link #getNext()} will throw an exception
     */
    boolean isNextValid();

    /**
     * @return get element at this position, throws {@link IllegalStateException} if current position is not valid or after last element of list
     */
    T get();

    /**
     * Get the requested class or null if element at position cannot be cast to this class
     *
     * @param elementClass class of element desired
     * @param <S>          type of element
     * @return element of type or null
     */
    <S extends T> S getOrNull(Class<S> elementClass);

    /**
     * @param element to which to set the current element in the list
     *                throws IllegalStateException if current index is not valid
     *                <p>
     *                If the current index is after the last element this will add the value to the end of the list without changing any positions at the end of the list
     *                they will now point to a valid element.
     */
    void set(T element);

    /**
     * @return removed current element, throws IllegalStateException if current index is not valid or after last element of list
     */
    T remove();

    /**
     * @param element to insert before current position in the list
     *                throws IllegalStateException if current index is not valid
     * @return true
     */
    boolean add(T element);

    // these are delegated to the original list
    int size();
    boolean isEmpty();
    boolean append(T element);
    void clear(); // will invalidate all indices of the list

    // these add current index to given indices before passing them to original list
    /**
     * Insert element at index
     *
     * @param index relative to this position, absolute index [0, size()], if absolute index == size() then element is added at the end of the list. The latter is also considered an insert at size() index.
     * @return element
     */
    T get(int index);

    /**
     * Get the requested class or null if element at position cannot be cast to this class
     *
     * @param index        relative to this position, absolute index [0, size()], if absolute index == size() then element is added at the end of the list. The latter is also considered an insert at size() index.
     * @param elementClass class of element desired
     * @param <S>          type of element
     * @return element of type or null
     */
    <S extends T> S getOrNull(int index, Class<S> elementClass);

    /**
     * Set element at given index
     *
     * @param index relative to this position, absolute index [0, size()], if absolute index == size() then element is added at the end of the list. The latter is considered an insert at the index.
     * @return element value at that position before or null if adding at end of list
     */
    T set(int index, T element);
    boolean add(int index, T element);
    boolean addAll(@NotNull Collection<T> elements);
    boolean addAll(int index, @NotNull Collection<T> elements);
    T remove(int index);
    void remove(int startIndex, int endIndex);

    // these return index relative to current index and operate on elements at or after the current index
    // if not found returned position will point to index after the last element. ie. getIndex() == size()
    // test isValidPosition() to see if actual element was found
    P indexOf(T o);
    P indexOf(int index, T o);
    P indexOf(@NotNull Predicate<P> predicate);
    P indexOf(int index, @NotNull Predicate<P> predicate);

    // these return position relative to current index and operate on elements before the current index
    // if not found returned position will point to index after the last element. ie. getIndex() == size()
    // test isValidPosition() to see if actual element was found
    P lastIndexOf(T o);
    P lastIndexOf(@NotNull Predicate<P> predicate);
    P lastIndexOf(int index, T o);
    P lastIndexOf(int index, @NotNull Predicate<P> predicate);
}
