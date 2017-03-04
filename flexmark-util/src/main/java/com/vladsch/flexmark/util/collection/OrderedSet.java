package com.vladsch.flexmark.util.collection;

import com.vladsch.flexmark.util.collection.iteration.*;

import java.lang.reflect.Array;
import java.util.*;

public class OrderedSet<E> implements Set<E>, Iterable<E> {
    private final HashMap<E, Integer> myKeyMap;
    private final ArrayList<E> myValueList;
    private final CollectionHost<E> myHost;
    private Indexed<E> myIndexedProxy;
    private Indexed<E> myAllowConcurrentModsIndexedProxy;
    private BitSet myValidIndices;
    private int myModificationCount;

    public OrderedSet() {
        this(0);
    }

    public OrderedSet(int capacity) {
        this(capacity, null);
    }

    public OrderedSet(CollectionHost<E> host) {
        this(0, host);
    }

    public OrderedSet(int capacity, CollectionHost<E> host) {
        this.myKeyMap = new HashMap<E, Integer>(capacity);
        this.myValueList = new ArrayList<E>(capacity);
        this.myValidIndices = new BitSet();
        this.myHost = host;
        this.myModificationCount = Integer.MIN_VALUE;
        this.myIndexedProxy = null;
        this.myAllowConcurrentModsIndexedProxy = null;
    }

    public BitSet indexBitSet(Iterable<? extends E> items) {
        BitSet bitSet = new BitSet();
        for (E element : items) {
            int i = indexOf(element);
            if (i != -1) {
                bitSet.set(i);
            }
        }
        return bitSet;
    }

    public BitSet differenceBitSet(Iterable<? extends E> items) {
        return differenceBitSet(items.iterator());
    }

    public BitSet differenceBitSet(Iterator<? extends E> items) {
        BitSet bitSet = new BitSet();
        int j = 0;
        while (items.hasNext()) {
            E element = items.next();

            int i = indexOf(element);
            if (i != j) {
                bitSet.set(i);
            }
            j++;
        }
        return bitSet;
    }

    public BitSet keyDifferenceBitSet(Iterable<? extends Map.Entry<? extends E, ?>> items) {
        return keyDifferenceBitSet(items.iterator());
    }

    public BitSet keyDifferenceBitSet(Iterator<? extends Map.Entry<? extends E, ?>> items) {
        BitSet bitSet = new BitSet();
        int j = 0;
        while (items.hasNext()) {
            Map.Entry<? extends E, ?> entry = items.next();
            int i = indexOf(entry.getKey());
            if (i != j) {
                bitSet.set(i);
            }
            j++;
        }
        return bitSet;
    }

    public BitSet valueDifferenceBitSet(Iterable<? extends Map.Entry<?, ? extends E>> items) {
        return valueDifferenceBitSet(items.iterator());
    }

    public BitSet valueDifferenceBitSet(Iterator<? extends Map.Entry<?, ? extends E>> items) {
        BitSet bitSet = new BitSet();
        int j = 0;
        while (items.hasNext()) {
            Map.Entry<?, ? extends E> entry = items.next();
            int i = indexOf(entry.getValue());
            if (i != j) {
                bitSet.set(i);
            }
            j++;
        }
        return bitSet;
    }

    private class IndexedProxy implements Indexed<E> {
        private final boolean myAllowConcurrentMods;

        public IndexedProxy(boolean allowConcurrentMods) {
            myAllowConcurrentMods = allowConcurrentMods;
        }

        @Override
        public E get(int index) {
            return getValue(index);
        }

        @Override
        public void set(int index, E item) {
            setValueAt(index, item, null);
        }

        @Override
        public void removeAt(int index) {
            removeIndexHosted(index);
        }

        @Override
        public int size() {
            return myValueList.size();
        }

        @Override
        public int modificationCount() {
            return myAllowConcurrentMods ? 0 : getIteratorModificationCount();
        }
    }

    public Indexed<E> getIndexedProxy() {
        if (myIndexedProxy != null) return myIndexedProxy;
        myIndexedProxy = new IndexedProxy(false);
        return myIndexedProxy;
    }

    public Indexed<E> getConcurrentModsIndexedProxy() {
        if (myAllowConcurrentModsIndexedProxy != null) return myAllowConcurrentModsIndexedProxy;
        myAllowConcurrentModsIndexedProxy = new IndexedProxy(true);
        return myAllowConcurrentModsIndexedProxy;
    }

    public int getModificationCount() {
        return myModificationCount;
    }

    private int getIteratorModificationCount() {
        return myHost != null ? myHost.getIteratorModificationCount() : myModificationCount;
    }

    public static <T1> T1 ifNull(T1 o, T1 nullValue) {
        return o == null ? nullValue : o;
    }

    public boolean inHostUpdate() {
        return myHost != null && myHost.skipHostUpdate();
    }

    public int indexOf(Object element) {
        return ifNull(myKeyMap.get(element), -1);
    }

    public boolean isValidIndex(int index) {
        return index >= 0 && index < myValueList.size() && myValidIndices.get(index);
    }

    public void validateIndex(int index) {
        if (!isValidIndex(index)) {
            throw new IndexOutOfBoundsException("Index " + index + " is not valid, size=" + myValueList.size() + " validIndices[" + index + "]=" + myValidIndices.get(index));
        }
    }

    public E getValue(int index) {
        validateIndex(index);
        return myValueList.get(index);
    }

    public E getValueOrNull(int index) {
        return isValidIndex(index) ? myValueList.get(index) : null;
    }

    @Override
    public int size() {
        return myKeyMap.size();
    }

    @Override
    public boolean isEmpty() {
        return myKeyMap.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return myKeyMap.containsKey(o);
    }

    public List<E> getValueList() {
        return myValueList;
    }

    public List<E> values() {
        if (!isSparse()) return myValueList;
        List<E> list = new ArrayList<E>();
        for (E item : iterable()) list.add(item);
        return list;
    }

    public boolean setValueAt(int index, E value, Object o) {
        // if index is after end then we add nulls
        int indexOf = indexOf(value);
        if (indexOf != -1) {
            if (index != indexOf) {
                throw new IllegalStateException("Trying to add existing element " + value + "[" + indexOf + "] at index " + index);
            }
            // same index, same element, nothing to update
            return false;
        } else {
            if (index < myValueList.size()) {
                if (myValidIndices.get(index)) {
                    // already have another element at index
                    throw new IllegalStateException("Trying to add new element " + value + " at index " + index + ", already occupied by " + myValueList.get(index));
                }
                // old element was removed, just replace
            } else {
                if (index > myValueList.size()) addNulls(index - 1);
            }
        }

        if (myHost != null && !myHost.skipHostUpdate()) {
            myHost.adding(index, value, o);
        }

        myKeyMap.put(value, index);
        myValueList.set(index, value);
        myValidIndices.set(index);

        return true;
    }

    public boolean isSparse() {
        return myValidIndices.nextClearBit(0) < myValueList.size();
    }

    public void addNull() {
        addNulls(myValueList.size());
    }

    public void addNulls(int index) {
        assert index >= myValueList.size();

        if (myHost != null && !myHost.skipHostUpdate()) {
            myHost.addingNulls(index);
        }

        int start = myValueList.size();
        ++myModificationCount;

        // no need they are 0's by default
        //myValidIndices.set(start, index + 1);
        while (myValueList.size() <= index) myValueList.add(null);
    }

    public ReversibleIterator<Integer> indexIterator() {
        return new BitSetIterator(myValidIndices);
    }

    public ReversibleIterator<Integer> reversedIndexIterator() {
        return new BitSetIterator(myValidIndices, true);
    }

    public ReversibleIterable<Integer> indexIterable() {
        return new BitSetIterable(myValidIndices);
    }

    public ReversibleIterable<Integer> reversedIndexIterable() {
        return new BitSetIterable(myValidIndices, true);
    }

    @Override
    public ReversibleIndexedIterator<E> iterator() {
        return new IndexedIterator<E, E, ReversibleIterator<Integer>>(getIndexedProxy(), indexIterator());
    }

    public ReversibleIndexedIterator<E> reversedIterator() {
        return new IndexedIterator<E, E, ReversibleIterator<Integer>>(getIndexedProxy(), reversedIndexIterator());
    }

    public ReversibleIterable<E> iterable() {
        return new IndexedIterable<E, E, ReversibleIterable<Integer>>(getIndexedProxy(), indexIterable());
    }

    public ReversibleIterable<E> reversedIterable() {
        return new IndexedIterable<E, E, ReversibleIterable<Integer>>(getIndexedProxy(), reversedIndexIterable());
    }

    @Override
    public Object[] toArray() {
        Object[] objects = new Object[myKeyMap.size()];
        int index = -1;
        int i = -1;
        while (index + 1 < myValueList.size()) {
            if (!myValidIndices.get(++index)) continue;
            objects[++i] = myValueList.get(index);
        }
        return objects;
    }

    @Override
    public <T> T[] toArray(T[] array) {
        Object[] objects = array;

        if (array.length < myKeyMap.size()) {
            objects = array.getClass() == Object[].class ? new Object[myKeyMap.size()] : (Object[]) Array.newInstance(array.getClass().getComponentType(), myKeyMap.size());
        }

        int index = -1;
        int i = -1;
        while (index + 1 < myValueList.size()) {
            if (!myValidIndices.get(++index)) continue;
            objects[++i] = myValueList.get(index);
        }

        if (objects.length > ++i) {
            objects[i] = null;
        }
        return (T[]) objects;
    }

    @Override
    public boolean add(E e) {
        return add(e, null);
    }

    public boolean add(E e, Object o) {
        if (myKeyMap.containsKey(e)) return false;

        int i = myValueList.size();

        if (myHost != null && !myHost.skipHostUpdate()) {
            myHost.adding(i, e, o);
        }

        ++myModificationCount;
        myKeyMap.put(e, i);
        myValueList.add(e);
        myValidIndices.set(i);
        return true;
    }

    public boolean removeIndex(int index) {
        return this.removeIndexHosted(index) != null;
    }

    public Object removeIndexHosted(int index) {
        validateIndex(index);

        E o = myValueList.get(index);
        Object r = null;
        if (myHost != null && !myHost.skipHostUpdate()) {
            r = myHost.removing(index, o);
        } else {
            r = o;
        }

        ++myModificationCount;
        myKeyMap.remove(o);

        if (myKeyMap.size() == 0) {
            if (myHost != null && !myHost.skipHostUpdate()) {
                myHost.clearing();
            }
            myValueList.clear();
            myValidIndices.clear();
        } else {
            if (myHost == null && index == myValueList.size() - 1) {
                myValueList.remove((int) index);
            }
            myValidIndices.clear(index);
        }

        return r;
    }

    @Override
    public boolean remove(Object o) {
        return removeHosted(o) != null;
    }

    public Object removeHosted(Object o) {
        Integer index = myKeyMap.get(o);
        if (index == null) return null;
        return removeIndexHosted((int) index);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        for (Object o : collection) {
            if (!myKeyMap.containsKey(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        final boolean[] changed = { false };
        for (E e : collection) {
            if (add(e)) changed[0] = true;
        }
        return changed[0];
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        BitSet removeSet = new BitSet(myValueList.size());
        removeSet.set(0, myValueList.size());
        removeSet.and(myValidIndices);

        for (Object o : collection) {
            int index = indexOf(o);
            if (index != -1) {
                removeSet.clear(index);
            }
        }

        // Java6
        //int index = myValueList.size();
        //if (index == 0) return false;
        //int[] indices = new int[removeSet.cardinality()];
        //index = -1;
        //int i = 0;
        //
        //while (true) {
        //    index = removeSet.nextSetBit(index + 1);
        //    if (index == -1) break;
        //    indices[i++] = index;
        //}
        //
        //boolean changed = false;
        //while (i-- > 0) {
        //    index = indices[i];
        //    remove(myValueList.get(index));
        //    changed = true;
        //}

        // Java7
        int index = myValueList.size();
        if (index == 0) return false;
        boolean changed = false;
        while (index-- > 0) {
            index = removeSet.previousSetBit(index);
            if (index == -1) break;
            remove(myValueList.get(index));
            changed = true;
        }

        return changed;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        boolean changed = false;
        for (Object o : collection) {
            if (myKeyMap.containsKey(o)) {
                if (remove(o)) changed = true;
            }
        }
        return changed;
    }

    @Override
    public void clear() {
        if (myHost != null && !myHost.skipHostUpdate()) {
            myHost.clearing();
        }

        ++myModificationCount;
        myKeyMap.clear();
        myValueList.clear();
        myValidIndices.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderedSet<?> set = (OrderedSet<?>) o;

        if (size() != set.size()) return false;
        Iterator setIterator = set.iterator();
        for (Object e : this) {
            Object eSet = setIterator.next();
            if (!e.equals(eSet)) return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = myKeyMap.hashCode();
        result = 31 * result + myValueList.hashCode();
        result = 31 * result + myValidIndices.hashCode();
        return result;
    }

    public BitSet getValidIndices() {
        return myValidIndices;
    }
}
