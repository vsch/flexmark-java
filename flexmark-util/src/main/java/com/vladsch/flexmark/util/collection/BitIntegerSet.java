package com.vladsch.flexmark.util.collection;

import com.vladsch.flexmark.util.IntConsumer;
import com.vladsch.flexmark.util.collection.iteration.BitSetIterator;
import com.vladsch.flexmark.util.collection.iteration.ReversibleIterable;
import com.vladsch.flexmark.util.collection.iteration.ReversibleIterator;

import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.LongBuffer;
import java.util.BitSet;
import java.util.Collection;
import java.util.Set;

public class BitIntegerSet implements Set<Integer>, ReversibleIterable<Integer> {
    private final BitSet myBits;
    private final boolean myReversed;

    public BitIntegerSet() {
        this(0);
    }

    public BitIntegerSet(int i) {
        myBits = new BitSet(i);
        myReversed = false;
    }

    public BitIntegerSet(BitSet other) {
        myBits = (BitSet) other.clone();
        myReversed = false;
    }

    public BitIntegerSet(BitIntegerSet other) {
        this(other, other.isReversed());
    }

    private BitIntegerSet(BitIntegerSet other, boolean reversed) {
        myBits = (BitSet) other.myBits.clone();
        myReversed = reversed;
    }

    public int cardinality() {
        return myBits.cardinality();
    }

    public int cardinality(int start) {
        return cardinality(start, myBits.length());
    }

    public int cardinality(int start, int end) {
        int count = 0;
        if (start >= 0 && end > 0 && start < end) {
            int firstBit = myBits.nextSetBit(0);
            int lastBit = myBits.previousSetBit(myBits.length()) + 1;
            if (start < firstBit) start = firstBit;
            if (end > lastBit) end = lastBit;

            if (start <= end && myBits.length() > 0) {
                int startIndex = start >> 6;
                int endIndex = end >> 6;
                long startMask = -1L << (start & 63); // 0-FF, 1-FE, 2-FC, 3-FE, 4-F0....
                long endMask = ~(-1L << (end & 63)); // 0-0, 1-01, 2-03, 3-07

                if (endMask == 0) {
                    endIndex--;
                    endMask = -1L;
                }

                long[] words = myBits.toLongArray();
                for (int i = startIndex; i <= endIndex; i++) {
                    long word = words[i];

                    if (i == startIndex) word &= startMask;
                    if (i == endIndex) word &= endMask;
                    count += Long.bitCount(word);
                }
            }
        }

        return count;
    }

    @Override
    public boolean contains(Object o) {
        return o instanceof Integer && myBits.get((Integer) o);
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[cardinality()];
        int i = 0;
        for (Integer bitIndex : this) {
            array[i++] = bitIndex;
        }
        return array;
    }

    @Override
    public <T> T[] toArray(T[] array) {
        Object[] objects = array;
        int count = cardinality();

        if (!array.getClass().getComponentType().isAssignableFrom(Integer.class)) {
            throw new ArrayStoreException("Cannot store Integer in array of " + array.getClass().getName());
        }

        if (array.length < count) {
            objects = array.getClass() == Object[].class ? new Object[count] : (Object[]) Array.newInstance(array.getClass().getComponentType(), count);
        }

        int i = 0;
        for (Integer bitIndex : this) {
            array[i++] = (T) bitIndex;
        }

        if (objects.length > ++i) {
            objects[i] = null;
        }
        return (T[]) objects;
    }

    @Override
    public boolean add(Integer item) {
        boolean old = myBits.get(item);
        myBits.set(item);
        return old;
    }

    @Override
    public boolean remove(Object o) {
        if (!(o instanceof Integer) || !myBits.get((Integer) o)) return false;
        myBits.clear((Integer) o);
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        if (collection instanceof BitIntegerSet) {
            BitSet other = ((BitIntegerSet) collection).myBits;
            BitSet bitSet = (BitSet) this.myBits.clone();
            bitSet.xor(other);
            bitSet.and(other);
            return bitSet.isEmpty();
        }

        for (Object o : collection) {
            if (!contains(o)) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends Integer> collection) {
        if (collection instanceof BitIntegerSet) {
            BitSet other = ((BitIntegerSet) collection).myBits;
            BitSet bitSet = (BitSet) this.myBits.clone();
            myBits.or(other);
            bitSet.xor(bitSet);
            return !bitSet.isEmpty();
        }

        boolean changed = false;
        for (Object o : collection) {
            if (add((Integer) o)) changed = true;
        }
        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        BitSet other;
        if (!(collection instanceof BitSet)) {
            other = new BitSet();
            for (Object o : collection) {
                if (contains(o)) {
                    other.set((Integer) o);
                }
            }
        } else {
            other = (BitSet) collection;
        }

        BitSet bitSet = (BitSet) this.myBits.clone();
        myBits.and(other);
        bitSet.xor(bitSet);
        return !bitSet.isEmpty();
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        if (collection instanceof BitIntegerSet) {
            BitSet other = ((BitIntegerSet) collection).myBits;
            BitSet bitSet = (BitSet) this.myBits.clone();
            myBits.andNot(other);
            bitSet.xor(bitSet);
            return !bitSet.isEmpty();
        }

        boolean changed = false;
        for (Object o : collection) {
            if (contains(o)) {
                myBits.clear((Integer) o);
                changed = true;
            }
        }
        return changed;
    }

    public void forEach(Consumer<? super Integer> consumer) {
        int index = myBits.nextSetBit(0);
        while (index >= 0) {
            consumer.accept(index);
            index = myBits.nextSetBit(index + 1);
        }
    }

    public void forEach(IntConsumer consumer) {
        int index = myBits.nextSetBit(0);
        while (index >= 0) {
            consumer.accept(index);
            index = myBits.nextSetBit(index + 1);
        }
    }

    @Override
    public int size() {
        return myBits.length();
    }

    @Override
    public boolean isEmpty() {
        return myBits.isEmpty();
    }

    @Override
    public void clear() {
        myBits.clear();
    }

    // @formatter:off
    public static BitIntegerSet valueOf(long[] longs) {return new BitIntegerSet(BitSet.valueOf(longs));}
    public static BitIntegerSet valueOf(LongBuffer buffer) {return new BitIntegerSet(BitSet.valueOf(buffer));}
    public static BitIntegerSet valueOf(byte[] bytes) {return new BitIntegerSet(BitSet.valueOf(bytes));}
    public static BitIntegerSet valueOf(ByteBuffer buffer) {return new BitIntegerSet(BitSet.valueOf(buffer));}

    public byte[] toByteArray() {return myBits.toByteArray();}
    public long[] toLongArray() {return myBits.toLongArray();}

    public BitIntegerSet flip(int i) {myBits.flip(i); return this;}
    public BitIntegerSet flip(int i, int i1) {myBits.flip(i, i1); return this;}
    public BitIntegerSet set(int i) {myBits.set(i); return this;}
    public BitIntegerSet set(int i, boolean b) {myBits.set(i, b); return this;}
    public BitIntegerSet set(int i, int i1) {myBits.set(i, i1); return this;}
    public BitIntegerSet set(int i, int i1, boolean b) {myBits.set(i, i1, b); return this;}
    public BitIntegerSet clear(int i) {myBits.clear(i); return this;}
    public BitIntegerSet clear(int i, int i1) {myBits.clear(i, i1); return this;}

    public BitIntegerSet and(BitSet set) {myBits.and(set); return this;}
    public BitIntegerSet or(BitSet set) {myBits.or(set); return this;}
    public BitIntegerSet xor(BitSet set) {myBits.xor(set); return this;}
    public BitIntegerSet andNot(BitSet set) {myBits.andNot(set); return this;}
    public BitIntegerSet and(BitIntegerSet set) {myBits.and(set.myBits); return this;}
    public BitIntegerSet or(BitIntegerSet set) {myBits.or(set.myBits); return this;}
    public BitIntegerSet xor(BitIntegerSet set) {myBits.xor(set.myBits); return this;}
    public BitIntegerSet andNot(BitIntegerSet set) {myBits.andNot(set.myBits); return this;}

    public boolean get(int i) {return myBits.get(i);}

    public BitIntegerSet get(int i, int i1) {return new BitIntegerSet(myBits.get(i, i1));}

    public int nextSetBit(int i) {return myBits.nextSetBit(i);}
    public int nextClearBit(int i) {return myBits.nextClearBit(i);}
    public int previousSetBit(int i) {return myBits.previousSetBit(i);}
    public int previousClearBit(int i) {return myBits.previousClearBit(i);}
    public boolean intersects(BitSet set) {return myBits.intersects(set);}

    public BitSet bitSet() { return myBits; }
    // @formatter:on

    @Override
    public ReversibleIterator<Integer> iterator() {
        return new BitSetIterator(myBits, myReversed);
    }

    @Override
    public ReversibleIterable<Integer> reversed() {
        return new BitIntegerSet(this, !myReversed);
    }

    @Override
    public boolean isReversed() {
        return myReversed;
    }

    @Override
    public ReversibleIterator<Integer> reversedIterator() {
        return new BitSetIterator(myBits, !myReversed);
    }
}
