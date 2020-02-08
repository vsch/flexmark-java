package com.vladsch.flexmark.experimental.util.collection.iteration;

import com.vladsch.flexmark.util.sequence.PositionAnchor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.function.Predicate;

/**
 * Represents a position in the list at the given index and with a span of 0 or 1 elements.
 * <p>
 * Add/remove operations affect the index and span of this position depending on the anchor type of the position.
 * <p>
 * Once the span becomes 0, no modifications to the list can change it to span 1 element.
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
 * or was removed later.
 * <p>
 * The index of this position reflects the position in the list previous to the position from which
 * it was instantiated and the index is valid if span is 1 or index is &gt;0. Relative offset &lt;0 will be increased
 * by 1 if span is 0 to reflect removal of previous element.
 * <p>
 * This anchor position type is used for iterating positions in reverse since it ignores
 * insertions into the list immediately before the current position.
 * <p>
 * {@link PositionAnchor#NEXT} - tracks the next element to the position from which it was instantiated.
 * The span will be 0 if no next element existed when it was instantiated (ie. at end of the list)
 * or was removed later.
 * <p>
 * The index of this position reflects the position in the list next to the position from which
 * it was instantiated and the index is valid if span is 1 or index is &lt;list.size().
 * Relative offset &gt;0 will be reduced by 1 if span is 0 to reflect removal of next element.
 * <p>
 * This anchor position type is used for iterating positions since it ignores
 * insertions into the list immediately after the current position.
 * <p>
 * All offset based operations on this position are operations on the list at index relative to this position's index and span,
 * so offsets may be -ve or +ve as long as absolute index is &gt;=0 and &lt;list.size()
 * <p>
 * NOTE: Offsets are affected by span being 0 or 1 and type of anchor for the position:
 * Offset of 0 refers to the element of this position and is only available if this position span is 1.
 * <p>
 * Offset &lt;0 refer to the elements preceding the element at this position. For {@link PositionAnchor#PREVIOUS} If span is 0 then the offset is
 * increased by 1 to reflect that there is no current element at this position.
 * <p>
 * Offset &gt;0 refer to the elements following the element at this position. If span is 0 then offset is reduced by 1
 * to reflect that there is no current element.
 * <p>
 * PositionList iterator will iterate over list elements while elements
 * are added or removed from the list without affecting count of the iteration as long
 * as elements are only added immediately before or after the current position.
 * <p>
 * This is not limited to adding 1 element. Each added element before or after the current
 * position moves the previous/next element position in the list. The first addition is limited to relative offset of -1, 0 or 1
 * from current index if the addition is not to affect iteration.
 * <p>
 * The following operations can insert anywhere in the previously inserted range.
 * <p>
 * Deletions have no limitations. They can never cause new elements to be inserted after
 * the next element position of the iterator so cannot cause iteration to continue on a newly added element.
 * <p>
 * All this makes list manipulation while traversing it easier to write, debug and understand because
 * all operations are relative to current position instead of a numeric index. In the code you
 * see -1, 0, +1, for previous, current and next element references taking removal and addition of elements into account.
 * <p>
 * PositionList adjusts all outstanding positions into its instance. Allowing getting multiple
 * positions in the list before making modification, in order to track index of elements at those positions.
 * If after modifications the position has 0 span, as reflected by its {@link IPosition#isValidElement()} being
 * false, then the element at that position was removed. Otherwise, getIndex() will return the current
 * index of that element in the list. If there is no valid element will return the index of the position, which
 * will be the next available element in the list.
 * <p>
 * Index computations can be a real bug generator, throw inserting, deleting and traversing a list at the
 * same time and you have a beast to debug and maintenance nightmare to look forward to.
 * PositionList and Position instances make all that go away. That's why I wrote these classes
 * so I don't have to write and debug the same error prone code for manipulating lists.
 *
 * @param <T> type of element held in the list
 */
public interface IPosition<T, P extends IPosition<T, P>> extends IPositionHolder<T, P> {
    /**
     * @param offset offset to current position
     *               <p>
     *               FIX: allow getIndex(offset) to return -1
     * @return absolute index in list, even if this position is not valid, it will always be [-1, list.size()]
     */
    int getIndex(int offset);

    /**
     * @param offset index relative to current position, &lt;0 previous elements, 0 means current, &gt;0 following elements
     * @return Position representing the index relative to current position,
     *         throws {@link IllegalStateException} if current position is not valid and given index == 0
     *         throws {@link IndexOutOfBoundsException} if requested index results in absolute index &lt;0 or &gt;size() of the list
     *         <p>
     *         NOTE: to avoid exceptions test if position has a valid index with isValidIndex()
     */
    P getPosition(int offset);

    /**
     * Mark position as not valid
     * may be useful for something. Invalidates current element with all side-effects of this state
     */
    void invalidate();

    /**
     * @return true if index of this position is between 0 and size() of the list
     */

    boolean isValidIndex();

    /**
     * @return get element at this position, throws {@link IllegalStateException} if current position is not valid or after last element of list
     */
    T get();

    /**
     * Insert element at index
     *
     * @param offset relative to this position, absolute index [0, size()], if absolute index == size() then element is added at the end of the list. The latter is also considered an insert at size() index.
     * @return element
     */
    T get(int offset);

    /**
     * @return get element at this position or null if no such thing
     */
    T getOrNull();

    /**
     * Insert element at index
     *
     * @param offset relative to this position, absolute index [0, size()], if absolute index == size() then element is added at the end of the list. The latter is also considered an insert at size() index.
     * @return element or null if for some reason the index or position are not valid
     */

    @Nullable
    T getOrNull(int offset);

    /**
     * Get the requested class or null if element at position cannot be cast to this class
     *
     * @param elementClass class of element desired
     * @param <S>          type of element
     * @return element of type or null
     */
    @Nullable <S extends T> S getOrNull(Class<S> elementClass);

    /**
     * Get the requested class or null if element at position cannot be cast to this class
     *
     * @param offset       relative to this position, absolute index [0, size()], if absolute index == size() then element is added at the end of the list. The latter is also considered an insert at size() index.
     * @param elementClass class of element desired
     * @param <S>          type of element
     * @return element of type or null
     */
    @Nullable <S extends T> S getOrNull(int offset, Class<S> elementClass);

    /**
     * @param element to which to set the current element in the list
     *                throws IllegalStateException if current index is not valid
     *                <p>
     *                If the current index is after the last element this will add the value to the end of the list treating as an insert, with corresponding updates to any positions that this would affect.
     */
    void set(T element);

    /**
     * Set element at given index
     *
     * @param offset relative to this position, absolute index [0, size()], if absolute index == size() then element is added at the end of the list. The latter is considered an insert at the index.
     * @param element value to set at offset
     * @return element value at that position before. If adding at end of list then null is always returned.
     */
    T set(int offset, T element);

    /**
     * @param element to insert after the current position in the list, not at the end of the list as a regular List does.
     *                throws IllegalStateException if current index is not valid
     * @return true
     */
    boolean add(T element);

    /**
     * Add element at position given by relative offset to current position.
     * <p>
     * NOTE: The position of insert is changed, depending on what has happened to the elements around the current position since it was instantiated:
     * if element at position was deleted then offset 0 and 1 have the same effect, insert element before next.
     * <p>
     * 0 will always insert before the current position, so add(0, item1) will insert before current position, which advances the position to keep up with the current element. Next add(0, item2) will
     * insert item2 after item1.
     *
     * @param offset  offset, can be negative. 0 means insert before current, 1 means after current, -1 means before previous
     * @param element to insert
     * @return true
     */
    boolean add(int offset, T element);
    boolean addAll(@NotNull Collection<T> elements);
    boolean addAll(int offset, @NotNull Collection<T> elements);

    /**
     * @return removed current element, throws IllegalStateException if current index is not valid or after last element of list
     */
    @Override
    T remove();
    T remove(int offset);
    void remove(int startOffset, int endOffset);

    /**
     * @return max offset from current position
     */
    int maxOffset();
    /**
     * @return min offset from current position to 0
     */
    int minOffset();

    // these are delegated to the original list
    int size();
    boolean isEmpty();
    boolean append(T element);

    // these return index relative to current index and operate on elements at or after the current index
    // if not found returned position will point to index after the last element. ie. getIndex() == size()
    // test isValidPosition() to see if actual element was found
    P indexOf(T o);
    P indexOf(int offset, T o);
    P indexOf(@NotNull Predicate<P> predicate);
    P indexOf(int offset, @NotNull Predicate<P> predicate);

    // these return position relative to current index and operate on elements before the current index
    // if not found returned position will point to index after the last element. ie. getIndex() == size()
    // test isValidPosition() to see if actual element was found
    P lastIndexOf(T o);
    P lastIndexOf(@NotNull Predicate<P> predicate);
    P lastIndexOf(int offset, T o);
    P lastIndexOf(int offset, @NotNull Predicate<P> predicate);
}
