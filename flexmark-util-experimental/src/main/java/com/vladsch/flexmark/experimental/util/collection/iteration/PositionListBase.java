package com.vladsch.flexmark.experimental.util.collection.iteration;

import com.vladsch.flexmark.util.misc.Utils;
import com.vladsch.flexmark.util.sequence.PositionAnchor;
import com.vladsch.flexmark.util.sequence.RepeatedSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.TestOnly;

import java.util.*;

/**
 * Iterator for list positions allowing to iterate over current elements while inserting and deleting elements relative to current position
 * <p>
 * elements inserted at current position or at current + 1 position will skipped in the iteration allowing adding new elements which will not be part of the iteration
 *
 * @param <T>  type of position
 * @param <P>  type of position holder
 *
 */
public abstract class PositionListBase<T, P extends IPositionHolder<T, P>> implements Iterable<P>, IPositionUpdater<T, P> {

    final private @NotNull List<T> myList;
    final private @NotNull WeakHashMap<IPositionListener, Integer> myListeners = new WeakHashMap<>(); // != -1 if listener is P and framed, -1 otherwise
    private @Nullable WeakHashMap<IPreviewPositionListener, Boolean> myPreviewListeners = null;        // used only for optimized setting notifications since most positions do nothing for this
    final private @NotNull PositionFactory<T, P> myFactory;
    final private @NotNull Stack<FrameLevel> myFrames = new Stack<>(); // open frames
    private int myLastFrameId; // starts at 0 and increments forever
    private int myMaxListeners;

    private static class FrameLevel {
        final @NotNull String parentList;
        final @Nullable FrameLevel parentFrame;
        final int frameLevel;
        final int frameId;
        final StackTraceElement[] openStackTrace;

        public FrameLevel(@NotNull String parentList, @Nullable FrameLevel parentFrame, int frameId, StackTraceElement[] openStackTrace) {
            this.parentList = parentList;
            this.parentFrame = parentFrame;
            this.frameLevel = parentFrame == null ? 0 : parentFrame.frameLevel + 1;
            this.frameId = frameId;
            this.openStackTrace = openStackTrace;
        }

        boolean isDescendantOf(@NotNull FrameLevel parentFrame) {
            return parentFrame == this.parentFrame || this.parentFrame != null && this.parentFrame.isDescendantOf(parentFrame);
        }

        void appendStackTrace(@NotNull StringBuilder out, @NotNull CharSequence indent, int stackOffset) {
            int iMax = openStackTrace.length;
            for (int i = stackOffset; i < iMax; i++) {
                StackTraceElement element = openStackTrace[i];
                out.append(indent).append(element.toString()).append("\n");
            }
        }
    }

    public PositionListBase(@NotNull List<T> list, @NotNull PositionFactory<T, P> factory) {
        myList = list;
        myFactory = factory;
    }

    @TestOnly
    public int getListeners() {
        int count = 0;
        for (IPositionListener position : myListeners.keySet()) {
            if (position != null) {
                count++;
            }
        }
        return count;
    }

    @TestOnly
    public int getMaxListeners() {
        return myMaxListeners;
    }

    @TestOnly
    public int getPreviewListeners() {
        if (myPreviewListeners != null) {
            int count = 0;
            for (IPreviewPositionListener position : myPreviewListeners.keySet()) {
                if (position != null) {
                    count++;
                }
            }

            if (count == 0) {
                myPreviewListeners = null;
            }

            return count;
        }
        return -1;
    }

    /**
     * Add list edit listener for notifications of mods to the list
     *
     * @param listener listener
     */
    @Override
    public void addPositionListener(@NotNull IPositionListener listener) {
        int frameId = myFrames.isEmpty() ? -1 : myFrames.peek().frameId;
        myListeners.put(listener, frameId);
        if (listener instanceof IPreviewPositionListener) {
            if (myPreviewListeners == null) myPreviewListeners = new WeakHashMap<>();
            myPreviewListeners.put((IPreviewPositionListener) listener, true);
        }
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
        if (myPreviewListeners != null && listener instanceof IPreviewPositionListener) {
            myPreviewListeners.remove(listener);
            if (myPreviewListeners.isEmpty()) {
                myPreviewListeners = null;
            }
        }
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
        assert position.getAnchor() != PositionAnchor.CURRENT;
        return new PositionIterator<T, P>(position);
    }

    @NotNull
    public List<T> getList() {
        return myList;
    }

    public boolean isEmpty() {
        return myList.isEmpty();
    }

    public boolean isNotEmpty() {
        return !myList.isEmpty();
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

    public Object openFrame() {
        int frameId = ++myLastFrameId;
        FrameLevel frameLevel;
        if (myFrames.isEmpty()) {
            frameLevel = new FrameLevel(getListParentId(), null, frameId, Thread.currentThread().getStackTrace());
        } else {
            FrameLevel parentFrame = myFrames.peek();
            frameLevel = new FrameLevel(getListParentId(), parentFrame, frameId, Thread.currentThread().getStackTrace());
        }
        myFrames.push(frameLevel);
        return frameLevel;
    }

    @NotNull
    public String getListParentId() {
        return getClass().getSimpleName() + "@" + Integer.toString(super.hashCode(), 16);
    }

    public void closeFrame(Object frameId) {
        if (!(frameId instanceof FrameLevel)) throw new IllegalStateException("Invalid frame id: " + frameId);
        FrameLevel frameLevel = (FrameLevel) frameId;
        if (!frameLevel.parentList.equals(getListParentId())) throw new IllegalStateException("FrameId created by: " + frameLevel.parentList + ", not by this list: " + getListParentId());

        if (myFrames.isEmpty()) {
            throw new IllegalStateException("No frames open");
        }

        FrameLevel openFrame = myFrames.peek();
        int openFrameId = openFrame.frameId;
        if (openFrameId != frameLevel.frameId) {
            StringBuilder out = new StringBuilder();
            out.append("closeFrame(")
                    //.append(openFrame.getClass().getSimpleName()).append("@").append(Integer.toString(openFrame.hashCode(), 16))
                    .append(") open nested frames, "); //.append(openFrameId).append(" ");
            throwIllegalStateWithOpenFrameTrace(out, frameLevel);
        }

        myFrames.pop();

        int[] listenerCount = { 0 };
        myListeners.entrySet().removeIf(entry -> {
            listenerCount[0]++;
            if (entry.getKey() != null) {
                if (entry.getValue() != openFrameId) return false;

                if (entry.getKey() instanceof IPositionHolder<?, ?>) {
                    ((IPositionHolder<?, ?>) entry.getKey()).setDetached();
                }

                if (myPreviewListeners != null && entry.getKey() instanceof IPreviewPositionListener) {
                    myPreviewListeners.remove(entry.getKey());
                }
            }
            return true;
        });

        myMaxListeners = Math.max(myMaxListeners, listenerCount[0]);

        if (myPreviewListeners != null && myPreviewListeners.isEmpty()) {
            myPreviewListeners = null;
        }
    }

    private void throwIllegalStateWithOpenFrameTrace(@Nullable StringBuilder out, FrameLevel frame) {
        if (!myFrames.isEmpty()) {
            if (out == null) out = new StringBuilder();

            out.append("openFrame trace:\n");

            int indent = 4;
            for (FrameLevel openFrame : myFrames) {
                if (openFrame.isDescendantOf(frame)) {
                    openFrame.appendStackTrace(out, RepeatedSequence.ofSpaces(indent + 2 * (openFrame.frameLevel - frame.frameLevel)), 2);
                }
            }

            throw new IllegalStateException(out.toString());
        }
    }

    @Override
    public P getPosition(int index, @NotNull PositionAnchor anchor) {
        if (index < 0 || index > myList.size())
            throw new IndexOutOfBoundsException("ListPosition.get(" + index + "), index out valid range [0, " + myList.size() + "]");

        P listPosition = myFactory.create(this, index, anchor);
        addPositionListener(listPosition);
        return listPosition;
    }

    @Override
    public void unframe(P position) {
        if (myListeners.containsKey(position)) {
            myListeners.put(position, -1);
        }
    }

    public P getFirst() {
        return getPosition(0, PositionAnchor.CURRENT);
    }

    public P getLast() {
        return myList.isEmpty() ? getPosition(0, PositionAnchor.CURRENT) : getPosition(myList.size() - 1, PositionAnchor.CURRENT);
    }

    public P getEnd() {
        return getPosition(myList.size(), PositionAnchor.CURRENT);
    }

    @Override
    public void clear() {
        if (!myList.isEmpty()) {
            myList.clear();
            deleted(0, Integer.MAX_VALUE);

            int[] listenerCount = { 0 };
            myListeners.entrySet().removeIf(entry -> {
                listenerCount[0]++;
                if (entry.getKey() instanceof IPositionHolder<?, ?>) {
                    ((IPositionHolder<?, ?>) entry.getKey()).setDetached();
                }

                if (myPreviewListeners != null && entry.getKey() instanceof IPreviewPositionListener) {
                    myPreviewListeners.remove(entry.getKey());
                }
                return true;
            });

            myMaxListeners = Math.max(myMaxListeners, listenerCount[0]);

            myListeners.clear();
            myPreviewListeners = null;
        }
    }

    @Override
    public void inserted(int index, int count) {
        assert count >= 0;
        assert index >= 0 && index <= myList.size() - count;

        int[] listenerCount = { 0 };
        myListeners.entrySet().removeIf(entry -> {
            listenerCount[0]++;
            if (entry.getKey() == null) return true;
            entry.getKey().inserted(index, count);
            return false;
        });
        myMaxListeners = Math.max(myMaxListeners, listenerCount[0]);
    }

    @Override
    public void deleted(int index, int count) {
        assert count >= 0;
        assert index >= 0 && index + count <= myList.size() + count;
        int[] listenerCount = { 0 };
        myListeners.entrySet().removeIf(entry -> {
            listenerCount[0]++;
            if (entry.getKey() == null) return true;
            entry.getKey().deleted(index, count);
            return false;
        });
        myMaxListeners = Math.max(myMaxListeners, listenerCount[0]);
    }

    @Override
    public void deleting(int index, int count) {
        if (myPreviewListeners != null) {
            myPreviewListeners.entrySet().removeIf(entry -> {
                if (entry.getKey() == null) return true;
                entry.getKey().deleting(index, count);
                return false;
            });
        }
    }

    @Override
    public Object changing(int index, Object value) {
        //noinspection unchecked
        return set(index, (T) value);
    }

    @Override
    public void changed(int index, Object oldValue, Object newValue) {
        throw new IllegalStateException("Should not be called. Use set() or changing(index, value) to change list value");
    }

    public T set(int index, T value) {
        if (index == myList.size()) {
            add(value);
            return null;
        } else {
            if (myPreviewListeners != null) {
                Object[] useValue = { value };

                myPreviewListeners.entrySet().removeIf(entry -> {
                    if (entry.getKey() == null) return true;
                    Object wasValue = useValue[0];
                    useValue[0] = entry.getKey().changing(index, wasValue);

                    // make sure replacement is compatible
                    assert useValue[0] == wasValue || value.getClass().isInstance(useValue[0]);
                    return false;
                });

                T oldValue = myList.get(index);
                //noinspection unchecked
                T newValue = (T) useValue[0];

                T result = myList.set(index, newValue);

                myPreviewListeners.entrySet().removeIf(entry -> {
                    if (entry.getKey() == null) return true;
                    entry.getKey().changed(index, oldValue, newValue);
                    return false;
                });

                return result;
            } else {
                return myList.set(index, value);
            }
        }
    }

    public T remove(int index) {
        assert index >= 0 && index < myList.size();
        deleting(index, 1);
        T value = myList.remove(index);
        deleted(index, 1);
        return value;
    }

    public void remove(int startIndex, int endIndex) {
        if (startIndex < endIndex) {
            assert startIndex >= 0 && endIndex <= myList.size();
            deleting(startIndex, endIndex - startIndex);
            myList.subList(startIndex, endIndex).clear();
            deleted(startIndex, endIndex - startIndex);
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
