package com.vladsch.flexmark.internal.util.collection;

import java.lang.reflect.Array;
import java.util.*;

public class OrderedSet<E> implements Set<E> {
    final private HashMap<E, Integer> keyMap;
    final private ArrayList<E> valueList;
    final private OrderedSetHost<E> host;
    private BitSet removedIndices;
    private int inHostUpdate;

    public OrderedSet() {
        this(0);
    }

    public OrderedSet(int capacity) {
        this(capacity, null);
    }

    public OrderedSet(OrderedSetHost<E> host) {
        this(0, host);
    }

    public OrderedSet(int capacity, OrderedSetHost<E> host) {
        this(capacity, false, host);
    }

    public OrderedSet(int capacity, boolean inHostUpdate, OrderedSetHost<E> host) {
        this.keyMap = new HashMap<E, Integer>(capacity);
        this.valueList = new ArrayList<E>(capacity);
        this.removedIndices = new BitSet();
        this.host = host;
        this.inHostUpdate = inHostUpdate && host != null ? 1 : 0;
    }

    public boolean inHostUpdate() {
        return inHostUpdate != 0;
    }

    public void enterHostUpdate() {
        if (host == null) throw new IllegalStateException("Cannot enter host update when unhosted");
        inHostUpdate++;
    }

    public void leaveHostUpdate() {
        if (host == null) throw new IllegalStateException("Cannot leave host update when un-hosted");
        if (inHostUpdate <= 0) throw new IllegalStateException("Cannot leave host update when not entered one");
        inHostUpdate--;
    }

    protected void enteringHostUpdate() {
        if (host == null) throw new IllegalStateException("Entering host update when un-hosted");
        if (inHostUpdate > 1) throw new IllegalStateException("Entering host update when already entered " + inHostUpdate + " times");
        enterHostUpdate();
    }

    public static <T1> T1 ifNull(T1 o, T1 nullValue) {
        return o == null ? nullValue : o;
    }

    public int indexOf(Object element) {
        return ifNull(keyMap.get(element), -1);
    }

    public boolean isValidIndex(int index) {
        return index >= 0 && index < valueList.size() && !removedIndices.get(index);
    }

    public E getValue(int index) {
        if (!isValidIndex(index)) {
            throw new IndexOutOfBoundsException("Index " + index + " is not valid, size=" + valueList.size() + " removedIndices[" + index + "]=" + removedIndices.get(index));
        }
        return valueList.get(index);
    }

    @Override
    public int size() {
        return keyMap.size();
    }

    @Override
    public boolean isEmpty() {
        return keyMap.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return keyMap.containsKey(o);
    }

    public List<E> getValueList() {
        return valueList;
    }

    public boolean isSparse() {
        return removedIndices.nextSetBit(0) != -1;
    }

    public void addNull() {
        addNull(valueList.size());
    }

    public void addNull(int index) {
        assert index >= valueList.size();

        if (host != null && !host.hostInUpdate()) {
            enteringHostUpdate();
            if (inHostUpdate == 1) {
                host.addingNull(index);
            }
            leaveHostUpdate();
        }
        int start = valueList.size();
        removedIndices.set(start, index + 1);
        while (valueList.size() <= index) valueList.add(null);
    }

    public abstract static class AbstractIndexedIterator<T, V> extends AbstractBitSetIterator<V> {
        final protected OrderedSet<T> orderedSet;

        public AbstractIndexedIterator(OrderedSet<T> orderedSet) {
            this(orderedSet, false);
        }

        public AbstractIndexedIterator(OrderedSet<T> orderedSet, boolean reversed) {
            super(orderedSet.removedIndices, false, reversed);
            this.orderedSet = orderedSet;
        }

        @Override
        protected int maxSize() {
            return orderedSet.valueList.size();
        }

        @Override
        protected void remove(int index) {
            orderedSet.remove(index);
        }
    }

    public static class IndexIterator<T> extends AbstractIndexedIterator<T, Integer> {
        public IndexIterator(OrderedSet<T> orderedSet) {
            this(orderedSet, false);
        }

        public IndexIterator(OrderedSet<T> orderedSet, boolean reversed) {
            super(orderedSet, reversed);
        }

        @Override
        public SparseIterator<Integer> reversed() {
            return new IndexIterator<T>(orderedSet, !isReversed());
        }

        @Override
        protected Integer getValueAt(int index) {
            return index;
        }
    }

    public static class ValueIterator<T> extends AbstractIndexedIterator<T, T> {
        public ValueIterator(OrderedSet<T> orderedSet) {
            this(orderedSet, false);
        }

        public ValueIterator(OrderedSet<T> orderedSet, boolean reversed) {
            super(orderedSet, reversed);
        }

        @Override
        public SparseIterator<T> reversed() {
            return new ValueIterator<T>(orderedSet, !isReversed());
        }

        @Override
        protected T getValueAt(int index) {
            return orderedSet.getValueList().get(index);
        }
    }

    public SparseIterator<Integer> indexIterator() {
        return new IndexIterator<E>(this);
    }

    public SparseIterator<Integer> reversedIndexIterator() {
        return new IndexIterator<E>(this, true);
    }

    @Override
    public SparseIterator<E> iterator() {
        return new ValueIterator<E>(this);
    }

    public SparseIterator<E> reversedIterator() {
        return new ValueIterator<E>(this, true);
    }

    @Override
    public Object[] toArray() {
        Object[] objects = new Object[keyMap.size()];
        int index = -1;
        int i = -1;
        while (index + 1 < valueList.size()) {
            if (removedIndices.get(++index)) continue;
            objects[++i] = valueList.get(index);
        }
        return objects;
    }

    @Override
    public <T> T[] toArray(T[] array) {
        Object[] objects = array;

        if (array.length < keyMap.size()) {
            objects = array.getClass() == Object[].class ? new Object[keyMap.size()] : (Object[]) Array.newInstance(array.getClass().getComponentType(), keyMap.size());
        }

        int index = -1;
        int i = -1;
        while (index + 1 < valueList.size()) {
            if (removedIndices.get(++index)) continue;
            objects[++i] = valueList.get(index);
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
        if (keyMap.containsKey(e)) return false;

        int i = valueList.size();
        if (host != null && !host.hostInUpdate()) {
            enteringHostUpdate();
            if (inHostUpdate == 1) {
                host.adding(i, e, o);
            }
            leaveHostUpdate();
        }

        keyMap.put(e, i);
        valueList.add(e);
        return true;
    }

    public boolean remove(int index) {
        return this.removeHosted(index) != null;
    }

    public Object removeHosted(int index) {
        if (!isValidIndex(index)) return null;
        Object r = null;
        E o = valueList.get(index);
        if (host != null && !host.hostInUpdate()) {
            enteringHostUpdate();
            if (inHostUpdate == 1) {
                r = host.removing(index, o);
            }
            leaveHostUpdate();
        } else r = o;

        keyMap.remove(o);

        if (keyMap.size() == 0) {
            if (host != null && !host.hostInUpdate()) {
                enteringHostUpdate();
                if (inHostUpdate == 1) {
                    host.clearing();
                }
                leaveHostUpdate();
            }
            valueList.clear();
            removedIndices.clear();
        } else {
            if (host == null && index == valueList.size() - 1) {
                valueList.remove((int) index);
                removedIndices.clear(index);
            } else {
                removedIndices.set(index);
            }
        }
        return r;
    }

    @Override
    public boolean remove(Object o) {
        return removeHosted(o) != null;
    }

    public Object removeHosted(Object o) {
        Integer index = keyMap.get(o);
        if (index == null) return null;
        return removeHosted((int) index);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        for (Object o : collection) {
            if (!keyMap.containsKey(o)) {
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
        BitSet removeSet = new BitSet(valueList.size());
        removeSet.set(0, valueList.size());
        removeSet.andNot(removedIndices);

        for (Object o : collection) {
            int index = indexOf(o);
            if (index != -1) {
                removeSet.clear(index);
            }
        }

        int index = valueList.size();
        if (index == 0) return false;
        boolean changed = false;
        while (index-- > 0) {
            index = removeSet.previousSetBit(index);
            if (index == -1) break;
            remove(valueList.get(index));
            changed = true;
        }

        return changed;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        boolean changed = false;
        for (Object o : collection) {
            if (keyMap.containsKey(o)) {
                if (remove(0)) changed = true;
            }
        }
        return changed;
    }

    @Override
    public void clear() {
        if (host != null && !host.hostInUpdate()) {
            enteringHostUpdate();
            if (inHostUpdate == 1) {
                host.clearing();
            }
            leaveHostUpdate();
        }
        keyMap.clear();
        valueList.clear();
        removedIndices.clear();
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
        int result = keyMap.hashCode();
        result = 31 * result + valueList.hashCode();
        result = 31 * result + removedIndices.hashCode();
        return result;
    }
}
