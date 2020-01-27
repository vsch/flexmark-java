package com.vladsch.flexmark.experimental.util.collection;

import com.vladsch.flexmark.util.collection.iteration.BitSetIterator;
import com.vladsch.flexmark.util.collection.iteration.ReversibleIterable;
import com.vladsch.flexmark.util.collection.iteration.ReversibleIterator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.LongBuffer;
import java.util.BitSet;
import java.util.Collection;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

public class BitIntegerSet implements Set<Integer>, ReversibleIterable<Integer> {
    final public static int[] EMPTY_INT = new int[0];

    final private @NotNull BitSet myBits;
    final private boolean myReversed;

    public BitIntegerSet() {
        this(0);
    }

    public BitIntegerSet(int i) {
        myBits = new BitSet(i);
        myReversed = false;
    }

    public BitIntegerSet(@NotNull BitSet other) {
        myBits = (BitSet) other.clone();
        myReversed = false;
    }

    public BitIntegerSet(@NotNull BitIntegerSet other) {
        this(other, other.isReversed());
    }

    private BitIntegerSet(@NotNull BitIntegerSet other, boolean reversed) {
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
    public boolean contains(@Nullable Object o) {
        return o instanceof Integer && myBits.get((Integer) o);
    }

    @NotNull
    @Override
    public Object[] toArray() {
        Object[] array = new Object[cardinality()];
        int i = 0;
        for (Integer bitIndex : this) {
            array[i++] = bitIndex;
        }
        return array;
    }

    @NotNull
    @Override
    public <T> T[] toArray(@NotNull T[] array) {
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
            //noinspection unchecked
            array[i++] = (T) bitIndex;
        }

        if (objects.length > ++i) {
            objects[i] = null;
        }
        //noinspection unchecked
        return (T[]) objects;
    }

    @Override
    public boolean add(@NotNull Integer item) {
        boolean old = myBits.get(item);
        myBits.set(item);
        return old;
    }

    @NotNull
    public int[] toArray(@Nullable int[] array) {
        return toArray(array, 0);
    }

    @NotNull
    public int[] toArray(@Nullable int[] array, int destinationIndex) {
        int arrayLength = array == null ? 0 : array.length;
        assert destinationIndex <= arrayLength;

        int[] useArray = array;
        int size = cardinality();

        if (size == 0) return useArray == null ? EMPTY_INT : useArray;

        if (array == null || arrayLength < destinationIndex + size) {
            // allocate a new array

            useArray = new int[destinationIndex + size];
            if (array != null) System.arraycopy(array, 0, useArray, 0, destinationIndex);
        }

        int[] i = new int[] { destinationIndex };
        int[] finalUseArray = useArray;
        forEach((IntConsumer) value -> finalUseArray[i[0]++] = value);
        return finalUseArray;
    }

    @Override
    public boolean remove(@Nullable Object o) {
        if (!(o instanceof Integer) || !myBits.get((Integer) o)) return false;
        myBits.clear((Integer) o);
        return true;
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> collection) {
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

    public boolean addAll(int... collection) {
        return addAll(collection, 0, Integer.MAX_VALUE);
    }

    public boolean addAll(@NotNull int[] collection, int startIndex) {
        return addAll(collection, startIndex, Integer.MAX_VALUE);
    }

    public boolean addAll(@NotNull int[] collection, int startIndex, int endIndex) {
        assert startIndex <= endIndex;

        boolean changed = false;

        int iMax = Math.min(collection.length, endIndex);
        for (int i = startIndex; i < iMax; i++) {
            if (add(collection[i])) changed = true;
        }
        return changed;
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends Integer> collection) {
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
    public boolean retainAll(@NotNull Collection<?> collection) {
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
    public boolean removeAll(@NotNull Collection<?> collection) {
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

    public void forEach(@NotNull Consumer<? super Integer> consumer) {
        int index = myBits.nextSetBit(0);
        while (index >= 0) {
            consumer.accept(index);
            index = myBits.nextSetBit(index + 1);
        }
    }

    public void forEach(@NotNull IntConsumer consumer) {
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
    public static @NotNull BitIntegerSet valueOf(@NotNull long[] longs) {return new BitIntegerSet(BitSet.valueOf(longs));}
    public static @NotNull BitIntegerSet valueOf(@NotNull LongBuffer buffer) {return new BitIntegerSet(BitSet.valueOf(buffer));}
    public static @NotNull BitIntegerSet valueOf(@NotNull byte[] bytes) {return new BitIntegerSet(BitSet.valueOf(bytes));}
    public static @NotNull BitIntegerSet valueOf(@NotNull ByteBuffer buffer) {return new BitIntegerSet(BitSet.valueOf(buffer));}

    public @NotNull byte[] toByteArray() {return myBits.toByteArray();}
    public @NotNull long[] toLongArray() {return myBits.toLongArray();}

    public @NotNull BitIntegerSet flip(int i) {myBits.flip(i); return this;}
    public @NotNull BitIntegerSet flip(int i, int i1) {myBits.flip(i, i1); return this;}
    public @NotNull BitIntegerSet set(int i) {myBits.set(i); return this;}
    public @NotNull BitIntegerSet set(int i, boolean b) {myBits.set(i, b); return this;}
    public @NotNull BitIntegerSet set(int i, int i1) {myBits.set(i, i1); return this;}
    public @NotNull BitIntegerSet set(int i, int i1, boolean b) {myBits.set(i, i1, b); return this;}
    public @NotNull BitIntegerSet clear(int i) {myBits.clear(i); return this;}
    public @NotNull BitIntegerSet clear(int i, int i1) {myBits.clear(i, i1); return this;}

    public @NotNull BitIntegerSet and(@NotNull BitSet set) {myBits.and(set); return this;}
    public @NotNull BitIntegerSet or(@NotNull BitSet set) {myBits.or(set); return this;}
    public @NotNull BitIntegerSet xor(@NotNull BitSet set) {myBits.xor(set); return this;}
    public @NotNull BitIntegerSet andNot(@NotNull BitSet set) {myBits.andNot(set); return this;}
    public @NotNull BitIntegerSet and(@NotNull BitIntegerSet set) {myBits.and(set.myBits); return this;}
    public @NotNull BitIntegerSet or(@NotNull BitIntegerSet set) {myBits.or(set.myBits); return this;}
    public @NotNull BitIntegerSet xor(@NotNull BitIntegerSet set) {myBits.xor(set.myBits); return this;}
    public @NotNull BitIntegerSet andNot(@NotNull BitIntegerSet set) {myBits.andNot(set.myBits); return this;}

    public boolean get(int i) {return myBits.get(i);}

    public @NotNull BitIntegerSet get(int i, int i1) {return new BitIntegerSet(myBits.get(i, i1));}

    public int nextSetBit(int i) {return myBits.nextSetBit(i);}
    public int nextClearBit(int i) {return myBits.nextClearBit(i);}
    public int previousSetBit(int i) {return myBits.previousSetBit(i);}
    public int previousClearBit(int i) {return myBits.previousClearBit(i);}
    public boolean intersects(BitSet set) {return myBits.intersects(set);}

    public @NotNull BitSet bitSet() { return myBits; }
    // @formatter:on

    @Override
    public @NotNull ReversibleIterator<Integer> iterator() {
        return new BitSetIterator(myBits, myReversed);
    }

    @Override
    public @NotNull ReversibleIterable<Integer> reversed() {
        return new BitIntegerSet(this, !myReversed);
    }

    @Override
    public boolean isReversed() {
        return myReversed;
    }

    @Override
    public @NotNull ReversibleIterator<Integer> reversedIterator() {
        return new BitSetIterator(myBits, !myReversed);
    }
}
