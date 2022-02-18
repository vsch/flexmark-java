package com.vladsch.flexmark.util.misc;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Re-Implementation of RegularEnumSet class for EnumSet, for "regular sized" enum types
 * (i.e., those with 64 or fewer enum constants)
 * <p>
 * Modification allows access and manipulation of the bit mask for the elements so
 * this class can be easily converted between long/int and BitFieldSet to use as efficient
 * option flags in implementation but convenient enum sets for manipulation.
 * <p>
 * If the Enum implements {@link BitField} then each field can have 1..N bits up to a maximum total of 64 bits per enum.
 * The class provides methods for setting and getting values from these fields as long, int, short or byte values, either signed or unsigned.
 *
 * @author Vladimir Schneider
 * @author Josh Bloch
 * @serial exclude
 * @since 1.5
 */
@SuppressWarnings({ "ManualArrayToCollectionCopy", "UseBulkOperation" })
public class BitFieldSet<E extends Enum<E>> extends AbstractSet<E> implements Cloneable, Serializable {
    final private static long serialVersionUID = 3411599620347842686L;

    static class UniverseLoader {
        @SuppressWarnings("rawtypes") final static ConcurrentHashMap<Class, Enum[]> enumUniverseMap = new ConcurrentHashMap<>();
        @SuppressWarnings("rawtypes") final static ConcurrentHashMap<Class, long[]> enumBitMasksMap = new ConcurrentHashMap<>();

        @SuppressWarnings("rawtypes")
        @NotNull
        public static Enum[] getUniverseSlow(Class elementType) {
            assert (elementType.isEnum());
            Enum[] cachedUniverse = enumUniverseMap.get(elementType);
            if (cachedUniverse != null) return cachedUniverse;

            Field[] fields = elementType.getFields();
            int enums = 0;
            for (Field field : fields) {
                if (field.getType().isEnum()) enums++;
            }

            if (enums > 0) {
                cachedUniverse = new Enum[enums];

                enums = 0;
                for (Field field : fields) {
                    if (field.getType().isEnum()) {
                        //noinspection unchecked
                        cachedUniverse[enums++] = Enum.valueOf((Class<Enum>) field.getType(), field.getName());
                    }
                }
            } else {
                cachedUniverse = ZERO_LENGTH_ENUM_ARRAY;
            }

            enumUniverseMap.put(elementType, cachedUniverse);
            return cachedUniverse;
        }
    }

    public static long nextBitMask(int nextAvailableBit, int bits) {
        return (-1L >>> -bits) << (long) nextAvailableBit;
    }

    /**
     * Returns all of the values comprising E.
     * The result is cloned and slower than SharedSecrets use but works in Java 11 and Java 8 because SharedSecrets are not shared publicly
     * @param <E> type of enum
     * @param elementType class of enum
     * @return array of enum values
     */
    public static <E extends Enum<E>> E[] getUniverse(Class<E> elementType) {
        //noinspection unchecked
        return (E[]) UniverseLoader.getUniverseSlow(elementType);
    }

    /**
     * Returns all of the values comprising E.
     * The result is cloned and slower than SharedSecrets use but works in Java 11 and Java 8 because SharedSecrets are not shared publicly
     *
     * @param <E> type of enum
     * @param elementType class of enum
     * @return array of bit masks for enum values
     */
    public static <E extends Enum<E>> long[] getBitMasks(Class<E> elementType) {
        long[] bitMasks = UniverseLoader.enumBitMasksMap.get(elementType);
        if (bitMasks != null) return bitMasks;

        // compute the bit masks for the enum
        //noinspection unchecked
        E[] universe = (E[]) UniverseLoader.getUniverseSlow(elementType);
        if (BitField.class.isAssignableFrom(elementType)) {
            int bitCount = 0;
            bitMasks = new long[universe.length];

            for (E e : universe) {
                int bits = ((BitField) e).getBits();
                if (bits <= 0)
                    throw new IllegalArgumentException(String.format("Enum bit field %s.%s bits must be >= 1, got: %d", elementType.getSimpleName(), e.name(), bits));

                if (bitCount + bits > 64)
                    throw new IllegalArgumentException(String.format("Enum bit field %s.%s bits exceed available 64 bits by %d", elementType.getSimpleName(), e.name(), bitCount + bits - 64));

                bitMasks[e.ordinal()] = nextBitMask(bitCount, bits);

                bitCount += bits;
            }
        } else {
            if (universe.length <= 64) {
                bitMasks = new long[universe.length];
                for (E e : universe) {
                    bitMasks[e.ordinal()] = 1L << e.ordinal();
                }
            } else
                throw new IllegalArgumentException("Enums with more than 64 values are not supported");
        }

        UniverseLoader.enumBitMasksMap.put(elementType, bitMasks);
        return bitMasks;
    }

    /**
     * Bit vector representation of this set.  The 2^k bit indicates the
     * presence of universe[k] in this set.
     */
    long elements = 0L;

    /**
     * The class of all the elements of this set.
     */
    final Class<E> elementType;

    /**
     * All values comprising T
     */
    final E[] universe;

    /**
     * All bit masks for each field since some can span more than one
     */
    final long[] bitMasks;

    /**
     * total number of bits used by all fields
     */
    final int totalBits;     // total bits used by all fields

    final static Enum<?>[] ZERO_LENGTH_ENUM_ARRAY = new Enum<?>[0];

    BitFieldSet(Class<E> elementType, Enum<?>[] universe, long[] bitMasks) {
        this.elementType = elementType;
        //noinspection unchecked
        this.universe = (E[]) universe;
        this.bitMasks = bitMasks;
        this.totalBits = getTotalBits(bitMasks);
    }

    public static int getTotalBits(long[] bitMasks) {
        return bitMasks.length == 0 ? 0 : 64 - Long.numberOfLeadingZeros(bitMasks[bitMasks.length - 1]);
    }

    // FIX: this for bit fields of more than 1 bit
    void addRange(E from, E to) {
        elements = (-1L >>> (from.ordinal() - to.ordinal() - 1)) << from.ordinal();
    }

    void addAll() {
        if (totalBits != 0)
            elements = -1L >>> -totalBits;
    }

    public void complement() {
        if (totalBits != 0) {
            elements = ~elements;
            elements &= -1L >>> -totalBits;  // Mask unused bits
        }
    }

    public long toLong() {
        return elements;
    }

    public int toInt() {
        if (totalBits > 32)
            throw new IllegalArgumentException(String.format("Enum fields use %d bits, which is more than 32 bits available in an int", totalBits));
        return (int) elements;
    }

    public short toShort() {
        if (totalBits > 16)
            throw new IllegalArgumentException(String.format("Enum fields use %d bits, which is more than 16 bits available in a short", totalBits));
        return (short) elements;
    }

    public byte toByte() {
        if (totalBits > 8)
            throw new IllegalArgumentException(String.format("Enum fields use %d bits, which is more than 8 bits available in a byte", totalBits));
        return (byte) elements;
    }

    public long allBitsMask() {
        return -1L >>> -totalBits;
    }

    public boolean orMask(long mask) {
        long allValues = -1L >>> -totalBits;
        if ((mask & ~allValues) != 0) {
            throw new IllegalArgumentException(String.format("bitMask %d value contains elements outside the universe %s", mask, Long.toBinaryString(mask & ~allValues)));
        }

        long oldElements = elements;
        elements |= mask;
        return oldElements != elements;
    }

    /**
     * Set all bit fields to values in mask
     *
     * @param mask bit fields values
     * @return true if any field values were modified
     * @deprecated use {@link #setAll(long)}
     */
    @SuppressWarnings("UnusedReturnValue")
    @Deprecated
    public boolean replaceAll(long mask) {
        return setAll(mask);
    }

    @SuppressWarnings("UnusedReturnValue")
    public boolean setAll(long mask) {
        long allValues = -1L >>> -totalBits;
        if ((mask & ~allValues) != 0) {
            throw new IllegalArgumentException(String.format("mask %d(0b%s) value contains elements outside the universe 0b%s", mask, Long.toBinaryString(mask), Long.toBinaryString(mask & ~allValues)));
        }

        long oldElements = elements;
        elements = mask;
        return oldElements != elements;
    }

    @Override
    public String toString() {
        if (elements == 0) {
            return elementType.getSimpleName() + ": { }";
        } else {
            DelimitedBuilder out = new DelimitedBuilder(", ");

            out.append(elementType.getSimpleName()).append(": { ");
            for (E e : universe) {
                if (any(mask(e))) {
                    out.append(e.name());
                    if (e instanceof BitField && ((BitField) e).getBits() > 1) {
                        out.append("(").append(getLong(e)).append(")");
                    }
                    out.mark();
                }
            }
            out.unmark().append(" }");
            return out.toString();
        }
    }

    public boolean andNotMask(long mask) {
        long oldElements = elements;
        elements &= ~mask;
        return oldElements != elements;
    }

    public boolean any(long mask) {
        return (elements & mask) != 0;
    }

    public boolean none(long mask) {
        return (elements & mask) == 0;
    }

    public boolean all(long mask) {
        long allValues = -1L >>> -totalBits;
        if ((mask & ~allValues) != 0) {
            throw new IllegalArgumentException(String.format("mask %d(0b%s) value contains elements outside the universe 0b%s", mask, Long.toBinaryString(mask), Long.toBinaryString(mask & ~allValues)));
        }
        return (elements & mask) == mask;
    }

    public static <E extends Enum<E>> long longMask(E e1) {
        long[] bitMasks = getBitMasks(e1.getDeclaringClass());
        // if we are here then there is no overflow
        return bitMasks[e1.ordinal()];
    }

    public static <E extends Enum<E>> int intMask(E e1) {
        long[] bitMasks = getBitMasks(e1.getDeclaringClass());
        int totalBits = getTotalBits(bitMasks);
        if (totalBits > 32)
            throw new IllegalArgumentException(String.format("Enum fields use %d, which is more than 32 available in int", totalBits));
        return (int) bitMasks[e1.ordinal()];
    }

    /**
     * Returns unsigned value for the field, except if the field is 64 bits
     *
     * @param e1 field to get
     * @return unsigned value
     */
    public long get(E e1) {
        long bitMask = bitMasks[e1.ordinal()];
        return (elements & bitMask) >>> Long.numberOfTrailingZeros(bitMask);
    }

    /**
     * Set a signed value for the field
     *
     * @param e1    field
     * @param value value to set
     * @return true if elements changed by operation
     */
    @SuppressWarnings("UnusedReturnValue")
    public boolean setUnsigned(E e1, long value) {
        long oldElements = elements;
        elements = setUnsigned(elementType, bitMasks, elements, e1, value);
        return oldElements != elements;
    }

    /**
     * Set a signed value for the field
     *
     * @param e1    field
     * @param value value to set
     * @return true if elements changed by operation
     */
    @SuppressWarnings("UnusedReturnValue")
    public boolean setSigned(E e1, long value) {
        long oldElements = elements;
        elements = setSigned(elementType, bitMasks, elements, e1, value);
        return oldElements != elements;
    }

    public void setBitField(E e1, long value) {
        setSigned(e1, (long) value);
    }

    public void setBitField(E e1, int value) {
        setSigned(e1, (long) value);
    }

    public void setBitField(E e1, short value) {
        setSigned(e1, (long) value);
    }

    public void setBitField(E e1, byte value) {
        setSigned(e1, (long) value);
    }

    public void setUnsignedField(E e1, long value) {
        setUnsigned(e1, (long) value);
    }

    public void setUnsignedField(E e1, int value) {
        setUnsigned(e1, (long) value);
    }

    public void setUnsignedField(E e1, short value) {
        setUnsigned(e1, (long) value);
    }

    public void setUnsignedField(E e1, byte value) {
        setUnsigned(e1, (long) value);
    }

    public long getUnsigned(E e1, int maxBits, String typeName) {
        return getUnsignedBitField(elements, e1, maxBits, typeName);
    }

    public long getSigned(E e1, int maxBits, String typeName) {
        return getSignedBitField(elements, e1, maxBits, typeName);
    }

    /**
     * Returns signed value for the field, except if the field is 64 bits
     *
     * @param e1 field to get
     * @return unsigned value
     */
    public long getLong(E e1) {
        return getSigned(e1, 64, "long");
    }

    public int getInt(E e1) {
        return (int) getSigned(e1, 32, "int");
    }

    public short getShort(E e1) {
        return (short) getSigned(e1, 16, "short");
    }

    public byte getByte(E e1) {
        return (byte) getSigned(e1, 8, "byte");
    }

    public int getUInt(E e1) {
        return (int) getSigned(e1, 32, "int");
    }

    public short getUShort(E e1) {
        return (short) getSigned(e1, 16, "short");
    }

    public byte getUByte(E e1) {
        return (byte) getSigned(e1, 8, "byte");
    }

    public static long orMask(long flags, long mask) {
        return flags | mask;
    }

    public static long andNotMask(long flags, long mask) {
        return flags & ~mask;
    }

    public static boolean any(long flags, long mask) {
        return (flags & mask) != 0;
    }

    public static boolean all(long flags, long mask) {
        return (flags & mask) == mask;
    }

    public static boolean none(long flags, long mask) {
        return (flags & mask) == 0;
    }

    public long mask(E e1) { return bitMasks[e1.ordinal()]; }

    public long mask(E e1, E e2) { return bitMasks[e1.ordinal()] | bitMasks[e2.ordinal()];}

    public long mask(E e1, E e2, E e3) { return bitMasks[e1.ordinal()] | bitMasks[e2.ordinal()] | bitMasks[e3.ordinal()];}

    public long mask(E e1, E e2, E e3, E e4) { return bitMasks[e1.ordinal()] | bitMasks[e2.ordinal()] | bitMasks[e3.ordinal()] | bitMasks[e4.ordinal()];}

    public long mask(E e1, E e2, E e3, E e4, E e5) { return bitMasks[e1.ordinal()] | bitMasks[e2.ordinal()] | bitMasks[e3.ordinal()] | bitMasks[e4.ordinal()] | bitMasks[e5.ordinal()];}

    @SafeVarargs
    final public long mask(E... rest) {
        long mask = 0;
        for (E e : rest) {
            mask |= bitMasks[e.ordinal()];
        }
        return mask;
    }

    public boolean add(E e1, E e2) { return orMask(mask(e1, e2));}

    public boolean add(E e1, E e2, E e3) { return orMask(mask(e1, e2, e3));}

    public boolean add(E e1, E e2, E e3, E e4) { return orMask(mask(e1, e2, e3, e4));}

    public boolean add(E e1, E e2, E e3, E e4, E e5) { return orMask(mask(e1, e2, e3, e4, e5));}

    @SafeVarargs
    final public boolean add(E... rest) { return orMask(mask(rest));}

    public boolean remove(E e1, E e2) { return andNotMask(mask(e1, e2));}

    public boolean remove(E e1, E e2, E e3) { return andNotMask(mask(e1, e2, e3));}

    public boolean remove(E e1, E e2, E e3, E e4) { return andNotMask(mask(e1, e2, e3, e4));}

    public boolean remove(E e1, E e2, E e3, E e4, E e5) { return andNotMask(mask(e1, e2, e3, e4, e5));}

    @SafeVarargs
    final public boolean remove(E... rest) { return andNotMask(mask(rest));}

    public boolean any(E e1) { return any(mask(e1)); }

    public boolean any(E e1, E e2) { return any(mask(e1, e2));}

    public boolean any(E e1, E e2, E e3) { return any(mask(e1, e2, e3));}

    public boolean any(E e1, E e2, E e3, E e4) { return any(mask(e1, e2, e3, e4));}

    public boolean any(E e1, E e2, E e3, E e4, E e5) { return any(mask(e1, e2, e3, e4, e5));}

    @SafeVarargs
    final public boolean any(E... rest) { return any(mask(rest));}

    public boolean all(E e1) { return all(mask(e1)); }

    public boolean all(E e1, E e2) { return all(mask(e1, e2));}

    public boolean all(E e1, E e2, E e3) { return all(mask(e1, e2, e3));}

    public boolean all(E e1, E e2, E e3, E e4) { return all(mask(e1, e2, e3, e4));}

    public boolean all(E e1, E e2, E e3, E e4, E e5) { return all(mask(e1, e2, e3, e4, e5));}

    @SafeVarargs
    final public boolean all(E... rest) { return all(mask(rest));}

    public boolean none(E e1) { return none(mask(e1)); }

    public boolean none(E e1, E e2) { return none(mask(e1, e2));}

    public boolean none(E e1, E e2, E e3) { return none(mask(e1, e2, e3));}

    public boolean none(E e1, E e2, E e3, E e4) { return none(mask(e1, e2, e3, e4));}

    public boolean none(E e1, E e2, E e3, E e4, E e5) { return none(mask(e1, e2, e3, e4, e5));}

    @SafeVarargs
    final public boolean none(E... rest) { return none(mask(rest));}

    /**
     * Returns an iterator over the elements contained in this set.  The
     * iterator traverses the elements in their <i>natural order</i> (which is
     * the order in which the enum constants are declared). The returned
     * Iterator is a "snapshot" iterator that will never throw {@link
     * java.util.ConcurrentModificationException}; the elements are traversed as they
     * existed when this call was invoked.
     * <p>
     * NOTE: bit field iteration requires skipping fields whose bits are all 0 so constant time is violated
     *
     * @return an iterator over the elements contained in this set
     */
    @NotNull
    public Iterator<E> iterator() {
        return bitMasks.length == totalBits ? new EnumBitSetIterator<>() : new EnumBitFieldIterator<>();
    }

    private class EnumBitSetIterator<E extends Enum<E>> implements Iterator<E> {
        /**
         * A bit vector representing the elements in the set not yet
         * returned by this iterator.
         */
        long unseen;

        /**
         * The bit representing the last element returned by this iterator
         * but not removed, or zero if no such element exists.
         */
        long lastReturned = 0;

        EnumBitSetIterator() {
            unseen = elements;
        }

        public boolean hasNext() {
            return unseen != 0;
        }

        @SuppressWarnings("unchecked")
        public E next() {
            if (unseen == 0)
                throw new NoSuchElementException();
            lastReturned = unseen & -unseen;
            unseen -= lastReturned;
            return (E) universe[Long.numberOfTrailingZeros(lastReturned)];
        }

        public void remove() {
            if (lastReturned == 0)
                throw new IllegalStateException();
            elements &= ~lastReturned;
            lastReturned = 0;
        }
    }

    private class EnumBitFieldIterator<E extends Enum<E>> implements Iterator<E> {
        int nextIndex;
        int lastReturnedIndex = -1;

        EnumBitFieldIterator() {
            nextIndex = -1;
            findNext();
        }

        public boolean hasNext() {
            return nextIndex < universe.length;
        }

        @SuppressWarnings("unchecked")
        public E next() {
            if (nextIndex >= universe.length)
                throw new NoSuchElementException();

            lastReturnedIndex = nextIndex;
            findNext();

            return (E) universe[lastReturnedIndex];
        }

        void findNext() {
            do {
                nextIndex++;
                if (nextIndex >= universe.length) break;
            } while ((elements & bitMasks[nextIndex]) == 0);
        }

        public void remove() {
            if (lastReturnedIndex == -1)
                throw new IllegalStateException();
            elements &= ~bitMasks[lastReturnedIndex];
            lastReturnedIndex = -1;
        }
    }

    /**
     * Returns the number of elements in this set.
     *
     * @return the number of elements in this set
     */
    public int size() {
        return totalBits;
    }

    /**
     * @return true if this set contains no elements
     */
    public boolean isEmpty() {
        return elements == 0;
    }

    /**
     * Returns true if this set contains the specified element.
     *
     * @param e element to be checked for containment in this collection
     * @return true if this set contains the specified element
     */
    public boolean contains(Object e) {
        if (e == null)
            return false;
        Class<?> eClass = e.getClass();
        if (eClass != elementType && eClass.getSuperclass() != elementType)
            return false;

        return (elements & bitMasks[((Enum<?>) e).ordinal()]) != 0;
    }

    // Modification Operations

    /**
     * Adds the specified element to this set if it is not already present.
     *
     * @param e element to be added to this set
     * @return true if the set changed as a result of the call
     * @throws NullPointerException if e is null
     */
    public boolean add(E e) {
        typeCheck(e);

        long oldElements = elements;
        elements |= bitMasks[e.ordinal()];
        return elements != oldElements;
    }

    /**
     * Removes the specified element from this set if it is present.
     *
     * @param e element to be removed from this set, if present
     * @return true if the set contained the specified element
     */
    public boolean remove(Object e) {
        if (e == null)
            return false;
        Class<?> eClass = e.getClass();
        if (eClass != elementType && eClass.getSuperclass() != elementType)
            return false;

        long oldElements = elements;
        elements &= ~bitMasks[((Enum<?>) e).ordinal()];
        return elements != oldElements;
    }

    // Bulk Operations

    /**
     * Returns true if this set contains all of the elements
     * in the specified collection.
     *
     * @param c collection to be checked for containment in this set
     * @return true if this set contains all of the elements
     *         in the specified collection
     * @throws NullPointerException if the specified collection is null
     */
    public boolean containsAll(Collection<?> c) {
        if (!(c instanceof BitFieldSet))
            return super.containsAll(c);

        BitFieldSet<?> es = (BitFieldSet<?>) c;
        if (es.elementType != elementType)
            return es.isEmpty();

        return (es.elements & ~elements) == 0;
    }

    /**
     * Adds all of the elements in the specified collection to this set.
     *
     * @param c collection whose elements are to be added to this set
     * @return true if this set changed as a result of the call
     * @throws NullPointerException if the specified collection or any
     *                              of its elements are null
     */
    public boolean addAll(Collection<? extends E> c) {
        if (!(c instanceof BitFieldSet))
            return super.addAll(c);

        BitFieldSet<?> es = (BitFieldSet<?>) c;
        if (es.elementType != elementType) {
            if (es.isEmpty())
                return false;
            else
                throw new ClassCastException(
                        es.elementType + " != " + elementType);
        }

        long oldElements = elements;
        elements |= es.elements;
        return elements != oldElements;
    }

    /**
     * Removes from this set all of its elements that are contained in
     * the specified collection.
     *
     * @param c elements to be removed from this set
     * @return true if this set changed as a result of the call
     * @throws NullPointerException if the specified collection is null
     */
    public boolean removeAll(Collection<?> c) {
        if (!(c instanceof BitFieldSet))
            return super.removeAll(c);

        BitFieldSet<?> es = (BitFieldSet<?>) c;
        if (es.elementType != elementType)
            return false;

        long oldElements = elements;
        elements &= ~es.elements;
        return elements != oldElements;
    }

    /**
     * Retains only the elements in this set that are contained in the
     * specified collection.
     *
     * @param c elements to be retained in this set
     * @return true if this set changed as a result of the call
     * @throws NullPointerException if the specified collection is null
     */
    public boolean retainAll(Collection<?> c) {
        if (!(c instanceof BitFieldSet))
            return super.retainAll(c);

        BitFieldSet<?> es = (BitFieldSet<?>) c;
        if (es.elementType != elementType) {
            boolean changed = (elements != 0);
            elements = 0;
            return changed;
        }

        long oldElements = elements;
        elements &= es.elements;
        return elements != oldElements;
    }

    /**
     * Removes all of the elements from this set.
     */
    public void clear() {
        elements = 0;
    }

    /**
     * Create a bit enum set from a bit mask
     *
     * @param enumClass class of the enum
     * @param mask      bit mask for items
     * @param <T>       enum type
     * @return bit enum set
     */
    public static <T extends Enum<T>> BitFieldSet<T> of(@NotNull Class<T> enumClass, long mask) {
        BitFieldSet<T> optionSet = BitFieldSet.noneOf(enumClass);
        optionSet.orMask(mask);
        return optionSet;
    }

    /**
     * Returns a copy of this set.
     *
     * @return a copy of this set
     */
    @SuppressWarnings("unchecked")
    public BitFieldSet<E> clone() {
        try {
            return (BitFieldSet<E>) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    /**
     * Throws an exception if e is not of the correct type for this enum set.
     */
    final void typeCheck(E e) {
        Class<?> eClass = e.getClass();
        if (eClass != elementType && eClass.getSuperclass() != elementType)
            throw new ClassCastException(eClass + " != " + elementType);
    }

    /**
     * This class is used to serialize all EnumSet instances, regardless of
     * implementation type.  It captures their "logical contents" and they
     * are reconstructed using public static factories.  This is necessary
     * to ensure that the existence of a particular implementation type is
     * an implementation detail.
     *
     * @serial include
     */
    private static class SerializationProxy<E extends Enum<E>>
            implements java.io.Serializable
    {
        /**
         * The element type of this enum set.
         *
         * @serial
         */
        final private Class<E> elementType;

        /**
         * The bit mask for elements contained in this enum set.
         *
         * @serial
         */
        final private long bits;

        SerializationProxy(BitFieldSet<E> set) {
            elementType = set.elementType;
            bits = set.elements;
        }

        private Object readResolve() {
            BitFieldSet<E> result = BitFieldSet.noneOf(elementType);
            result.orMask(bits);
            return result;
        }

        final private static long serialVersionUID = 362491234563181265L;
    }

    Object writeReplace() {
        return new SerializationProxy<>(this);
    }

    // readObject method for the serialization proxy pattern
    // See Effective Java, Second Ed., Item 78.
    private void readObject(java.io.ObjectInputStream stream)
            throws java.io.InvalidObjectException {
        throw new java.io.InvalidObjectException("Proxy required");
    }

    /**
     * Compares the specified object with this set for equality.  Returns
     * true if the given object is also a set, the two sets have
     * the same size, and every member of the given set is contained in
     * this set.
     *
     * @param o object to be compared for equality with this set
     * @return true if the specified object is equal to this set
     */
    public boolean equals(Object o) {
        if (!(o instanceof BitFieldSet))
            return super.equals(o);

        BitFieldSet<?> es = (BitFieldSet<?>) o;
        if (es.elementType != elementType)
            return elements == 0 && es.elements == 0;
        return es.elements == elements;
    }

    /**
     * Creates an empty enum set with the specified element type.
     *
     * @param <E>         The class of the elements in the set
     * @param elementType the class object of the element type for this enum
     *                    set
     * @return An empty enum set of the specified type.
     * @throws NullPointerException if elementType is null
     */
    public static <E extends Enum<E>> BitFieldSet<E> noneOf(Class<E> elementType) {
        if (!elementType.isEnum())
            throw new ClassCastException(elementType + " not an enum");

        Enum<?>[] universe = getUniverse(elementType);

        return new BitFieldSet<>(elementType, universe, getBitMasks(elementType));
    }

    /**
     * Set a signed value for the field
     *
     * @param e1    field
     * @param value value to set
     */
    static <E extends Enum<E>> long setSigned(long elements, E e1, long value) {
        Class<E> elementType = e1.getDeclaringClass();
        long[] bitMasks = getBitMasks(elementType);
        return setSigned(elementType, bitMasks, elements, e1, value);
    }

    /**
     * Set a signed value for the field
     *
     * @param e1    field
     * @param value value to set
     */
    static <E extends Enum<E>> long setSigned(Class<E> elementType, long[] bitMasks, long elements, E e1, long value) {
        long bitMask = bitMasks[e1.ordinal()];

        int bitCount = Long.bitCount(bitMask);
        long halfValue = 1L << bitCount - 1;

        if (bitCount < 64) {
            if (value < -halfValue || value > halfValue - 1)
                throw new IllegalArgumentException(String.format("Enum field %s.%s is %d bit%s, value range is [%d, %d], cannot be set to %d", elementType.getSimpleName(), e1.name(), bitCount, bitCount > 1 ? "s" : "", -halfValue, halfValue - 1, value));
        }

        long shiftedValue = value << Long.numberOfTrailingZeros(bitMask);
        return elements ^ ((elements ^ shiftedValue) & bitMask);
    }

    /**
     * Set an unsigned value for the field
     *
     * @param e1    field
     * @param value value to set
     */
    static <E extends Enum<E>> long setUnsigned(long elements, E e1, long value) {
        Class<E> elementType = e1.getDeclaringClass();
        long[] bitMasks = getBitMasks(elementType);
        return setUnsigned(elementType, bitMasks, elements, e1, value);
    }

    /**
     * Set an unsigned value for the field
     *
     * @param e1    field
     * @param value value to set
     */
    static <E extends Enum<E>> long setUnsigned(Class<E> elementType, long[] bitMasks, long elements, E e1, long value) {
        long bitMask = bitMasks[e1.ordinal()];

        int bitCount = Long.bitCount(bitMask);
        long maxValue = 1L << bitCount;

        if (bitCount < 64) {
            if (!(value >= 0 && value < maxValue))
                throw new IllegalArgumentException(String.format("Enum field %s.%s is %d bit%s, value range is [0, %d), cannot be set to %d", elementType.getSimpleName(), e1.name(), bitCount, bitCount > 1 ? "s" : "", maxValue - 1, value));
        }

        long shiftedValue = value << Long.numberOfTrailingZeros(bitMask);
        return elements ^ ((elements ^ shiftedValue) & bitMask);
    }

    public static <E extends Enum<E>> long setBitField(long elements, E e1, int value) {
        return setUnsigned(elements, e1, value);
    }

    public static <E extends Enum<E>> int setBitField(int elements, E e1, int value) {
        return (int) setUnsigned(elements, e1, value);
    }

    public static <E extends Enum<E>> short setBitField(short elements, E e1, short value) {
        return (short) setUnsigned(elements, e1, value);
    }

    public static <E extends Enum<E>> byte setBitField(byte elements, E e1, byte value) {
        return (byte) setUnsigned(elements, e1, value);
    }

    /**
     * Returns unsigned value for the field, except if the field is 64 bits
     *
     * @param <E> type of enum
     * @param elements bit mask for elements
     * @param e1 field to get
     * @param maxBits maximum bits for type
     * @param typeName name of type
     * @return unsigned value
     */
    public static <E extends Enum<E>> long getUnsignedBitField(long elements, E e1, int maxBits, String typeName) {
        Class<E> elementType = e1.getDeclaringClass();
        long[] bitMasks = getBitMasks(elementType);
        long bitMask = bitMasks[e1.ordinal()];
        int bitCount = Long.bitCount(bitMask);

        if (bitCount > maxBits)
            throw new IllegalArgumentException(String.format("Enum field %s.%s uses %d, which is more than %d available in %s", elementType.getSimpleName(), e1.name(), bitCount, maxBits, typeName));

        return (elements & bitMask) >>> Long.numberOfTrailingZeros(bitMask);
    }

    static <E extends Enum<E>> long getSignedBitField(long elements, E e1, int maxBits, String typeName) {
        Class<E> elementType = e1.getDeclaringClass();
        long[] bitMasks = getBitMasks(elementType);
        long bitMask = bitMasks[e1.ordinal()];
        int bitCount = Long.bitCount(bitMask);

        if (bitCount > maxBits)
            throw new IllegalArgumentException(String.format("Enum field %s.%s uses %d, which is more than %d available in %s", elementType.getSimpleName(), e1.name(), bitCount, maxBits, typeName));

        return elements << Long.numberOfLeadingZeros(bitMask) >> 64 - bitCount;
    }

    /**
     * Returns signed value for the field, except if the field is 64 bits
     *
     * @param <E> type of enum
     * @param elements bit mask for elements
     * @param e1 field to get
     * @return unsigned value
     */
    public static <E extends Enum<E>> long getBitField(long elements, E e1) {
        return getUnsignedBitField(elements, e1, 64, "long");
    }

    public static <E extends Enum<E>> int getBitField(int elements, E e1) {
        return (int) getUnsignedBitField(elements, e1, 32, "int");
    }

    public static <E extends Enum<E>> short getBitField(short elements, E e1) {
        return (short) getUnsignedBitField(elements, e1, 16, "short");
    }

    public static <E extends Enum<E>> byte getBitField(byte elements, E e1) {
        return (byte) getUnsignedBitField(elements, e1, 8, "byte");
    }

    /**
     * Creates an enum set containing all of the elements in the specified
     * element type.
     *
     * @param <E>         The class of the elements in the set
     * @param elementType the class object of the element type for this enum
     *                    set
     * @return An enum set containing all the elements in the specified type.
     * @throws NullPointerException if elementType is null
     */
    public static <E extends Enum<E>> BitFieldSet<E> allOf(Class<E> elementType) {
        BitFieldSet<E> result = noneOf(elementType);
        result.addAll();
        return result;
    }

    /**
     * Creates an enum set with the same element type as the specified enum
     * set, initially containing the same elements (if any).
     *
     * @param <E> The class of the elements in the set
     * @param s   the enum set from which to initialize this enum set
     * @return A copy of the specified enum set.
     * @throws NullPointerException if s is null
     */
    public static <E extends Enum<E>> BitFieldSet<E> copyOf(BitFieldSet<E> s) {
        return s.clone();
    }

    /**
     * Creates an enum set initialized from the specified collection.  If
     * the specified collection is an BitFieldSet instance, this static
     * factory method behaves identically to {@link #copyOf(BitFieldSet)}.
     * Otherwise, the specified collection must contain at least one element
     * (in order to determine the new enum set's element type).
     *
     * @param <E> The class of the elements in the collection
     * @param c   the collection from which to initialize this enum set
     * @return An enum set initialized from the given collection.
     * @throws IllegalArgumentException if c is not an
     *                                  BitFieldSet instance and contains no elements
     * @throws NullPointerException     if c is null
     */
    public static <E extends Enum<E>> BitFieldSet<E> copyOf(Collection<E> c) {
        if (c instanceof BitFieldSet) {
            return ((BitFieldSet<E>) c).clone();
        } else {
            if (c.isEmpty())
                throw new IllegalArgumentException("Collection is empty");
            Iterator<E> i = c.iterator();
            E first = i.next();
            BitFieldSet<E> result = BitFieldSet.of(first);
            while (i.hasNext()) { result.add(i.next()); }
            return result;
        }
    }

    /**
     * Creates an enum set with the same element type as the specified enum
     * set, initially containing all the elements of this type that are
     * <i>not</i> contained in the specified set.
     *
     * @param <E> The class of the elements in the enum set
     * @param s   the enum set from whose complement to initialize this enum set
     * @return The complement of the specified set in this set
     * @throws NullPointerException if s is null
     */
    public static <E extends Enum<E>> BitFieldSet<E> complementOf(BitFieldSet<E> s) {
        BitFieldSet<E> result = copyOf(s);
        result.complement();
        return result;
    }

    /**
     * Creates an enum set initially containing the specified element.
     * <p>
     * Overloads of this method exist to initialize an enum set with
     * one through five elements.  A sixth overloading is provided that
     * uses the varargs feature.  This overloading may be used to create
     * an enum set initially containing an arbitrary number of elements, but
     * is likely to run slower than the overloads that do not use varargs.
     *
     * @param <E> The class of the specified element and of the set
     * @param e   the element that this set is to contain initially
     * @return an enum set initially containing the specified element
     * @throws NullPointerException if e is null
     */
    public static <E extends Enum<E>> BitFieldSet<E> of(E e) {
        BitFieldSet<E> result = noneOf(e.getDeclaringClass());
        result.add(e);
        return result;
    }

    /**
     * Creates an enum set initially containing the specified elements.
     * <p>
     * Overloads of this method exist to initialize an enum set with
     * one through five elements.  A sixth overloading is provided that
     * uses the varargs feature.  This overloading may be used to create
     * an enum set initially containing an arbitrary number of elements, but
     * is likely to run slower than the overloads that do not use varargs.
     *
     * @param <E> The class of the parameter elements and of the set
     * @param e1  an element that this set is to contain initially
     * @param e2  another element that this set is to contain initially
     * @return an enum set initially containing the specified elements
     * @throws NullPointerException if any parameters are null
     */
    public static <E extends Enum<E>> BitFieldSet<E> of(E e1, E e2) {
        BitFieldSet<E> result = noneOf(e1.getDeclaringClass());
        result.add(e1);
        result.add(e2);
        return result;
    }

    /**
     * Creates an enum set initially containing the specified elements.
     * <p>
     * Overloads of this method exist to initialize an enum set with
     * one through five elements.  A sixth overloading is provided that
     * uses the varargs feature.  This overloading may be used to create
     * an enum set initially containing an arbitrary number of elements, but
     * is likely to run slower than the overloads that do not use varargs.
     *
     * @param <E> The class of the parameter elements and of the set
     * @param e1  an element that this set is to contain initially
     * @param e2  another element that this set is to contain initially
     * @param e3  another element that this set is to contain initially
     * @return an enum set initially containing the specified elements
     * @throws NullPointerException if any parameters are null
     */
    public static <E extends Enum<E>> BitFieldSet<E> of(E e1, E e2, E e3) {
        BitFieldSet<E> result = noneOf(e1.getDeclaringClass());
        result.add(e1);
        result.add(e2);
        result.add(e3);
        return result;
    }

    /**
     * Creates an enum set initially containing the specified elements.
     * <p>
     * Overloads of this method exist to initialize an enum set with
     * one through five elements.  A sixth overloading is provided that
     * uses the varargs feature.  This overloading may be used to create
     * an enum set initially containing an arbitrary number of elements, but
     * is likely to run slower than the overloads that do not use varargs.
     *
     * @param <E> The class of the parameter elements and of the set
     * @param e1  an element that this set is to contain initially
     * @param e2  another element that this set is to contain initially
     * @param e3  another element that this set is to contain initially
     * @param e4  another element that this set is to contain initially
     * @return an enum set initially containing the specified elements
     * @throws NullPointerException if any parameters are null
     */
    public static <E extends Enum<E>> BitFieldSet<E> of(E e1, E e2, E e3, E e4) {
        BitFieldSet<E> result = noneOf(e1.getDeclaringClass());
        result.add(e1);
        result.add(e2);
        result.add(e3);
        result.add(e4);
        return result;
    }

    /**
     * Creates an enum set initially containing the specified elements.
     * <p>
     * Overloads of this method exist to initialize an enum set with
     * one through five elements.  A sixth overloading is provided that
     * uses the varargs feature.  This overloading may be used to create
     * an enum set initially containing an arbitrary number of elements, but
     * is likely to run slower than the overloads that do not use varargs.
     *
     * @param <E> The class of the parameter elements and of the set
     * @param e1  an element that this set is to contain initially
     * @param e2  another element that this set is to contain initially
     * @param e3  another element that this set is to contain initially
     * @param e4  another element that this set is to contain initially
     * @param e5  another element that this set is to contain initially
     * @return an enum set initially containing the specified elements
     * @throws NullPointerException if any parameters are null
     */
    public static <E extends Enum<E>> BitFieldSet<E> of(
            E e1, E e2, E e3, E e4,
            E e5
    ) {
        BitFieldSet<E> result = noneOf(e1.getDeclaringClass());
        result.add(e1);
        result.add(e2);
        result.add(e3);
        result.add(e4);
        result.add(e5);
        return result;
    }

    /**
     * Creates an enum set initially containing the specified elements.
     * This factory, whose parameter list uses the varargs feature, may
     * be used to create an enum set initially containing an arbitrary
     * number of elements, but it is likely to run slower than the overloads
     * that do not use varargs.
     *
     * @param <E>   The class of the parameter elements and of the set
     * @param first an element that the set is to contain initially
     * @param rest  the remaining elements the set is to contain initially
     * @return an enum set initially containing the specified elements
     * @throws NullPointerException if any of the specified elements are null,
     *                              or if rest is null
     */
    @SafeVarargs
    public static <E extends Enum<E>> BitFieldSet<E> of(E first, E... rest) {
        BitFieldSet<E> result = noneOf(first.getDeclaringClass());
        result.add(first);
        for (E e : rest) { result.add(e); }
        return result;
    }

    /**
     * Creates an enum set initially containing the specified elements.
     * This factory, whose parameter list uses the varargs feature, may
     * be used to create an enum set initially containing an arbitrary
     * number of elements, but it is likely to run slower than the overloads
     * that do not use varargs.
     *
     * @param <E>  The class of the parameter elements and of the set
     * @param declaringClass declaring class of enum
     * @param rest the remaining elements the set is to contain initially
     * @return an enum set initially containing the specified elements
     * @throws NullPointerException if any of the specified elements are null,
     *                              or if rest is null
     */
    public static <E extends Enum<E>> BitFieldSet<E> of(@NotNull Class<E> declaringClass, E[] rest) {
        BitFieldSet<E> result = noneOf(declaringClass);
        for (E e : rest) { result.add(e); }
        return result;
    }

    /**
     * Creates an enum set initially containing all of the elements in the
     * range defined by the two specified endpoints.  The returned set will
     * contain the endpoints themselves, which may be identical but must not
     * be out of order.
     *
     * @param <E>  The class of the parameter elements and of the set
     * @param from the first element in the range
     * @param to   the last element in the range
     * @return an enum set initially containing all of the elements in the
     *         range defined by the two specified endpoints
     * @throws NullPointerException     if {@code from} or {@code to} are null
     * @throws IllegalArgumentException if {@code from.compareTo(to) > 0}
     */
    public static <E extends Enum<E>> BitFieldSet<E> range(E from, E to) {
        if (from.compareTo(to) > 0)
            throw new IllegalArgumentException(from + " > " + to);
        BitFieldSet<E> result = noneOf(from.getDeclaringClass());
        result.addRange(from, to);
        return result;
    }
}
