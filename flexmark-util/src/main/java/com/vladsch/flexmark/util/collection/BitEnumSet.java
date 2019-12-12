package com.vladsch.flexmark.util.collection;

import com.vladsch.flexmark.util.DelimitedBuilder;
import org.jetbrains.annotations.NotNull;
import sun.misc.SharedSecrets;

import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Re-Implementation of RegularEnumSet class for EnumSet, for "regular sized" enum types
 * (i.e., those with 64 or fewer enum constants)
 * <p>
 * Modification allows access and manipulation of the bit mask for the elements so
 * this class can be easily converted between long/int and BitEnumSet to use as efficient
 * option flags in implementation but convenient enum sets for manipulation.
 *
 * @author Josh Bloch
 * @serial exclude
 * @since 1.5
 */
@SuppressWarnings({ "ManualArrayToCollectionCopy", "UseBulkOperation" })
public class BitEnumSet<E extends Enum<E>> extends AbstractSet<E> implements Cloneable, Serializable {
    private static final long serialVersionUID = 3411599620347842686L;

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
     * All of the values comprising T.  (Cached for performance.)
     */
    final E[] universe;

    final static Enum<?>[] ZERO_LENGTH_ENUM_ARRAY = new Enum<?>[0];

    BitEnumSet(Class<E> elementType, Enum<?>[] universe) {
        this.elementType = elementType;
        //noinspection unchecked
        this.universe = (E[]) universe;
    }

    void addRange(E from, E to) {
        elements = (-1L >>> (from.ordinal() - to.ordinal() - 1)) << from.ordinal();
    }

    void addAll() {
        if (universe.length != 0)
            elements = -1L >>> -universe.length;
    }

    public void complement() {
        if (universe.length != 0) {
            elements = ~elements;
            elements &= -1L >>> -universe.length;  // Mask unused bits
        }
    }

    public long toLong() {
        return elements;
    }

    public int toInt() {
        if (universe.length > 32) {
            throw new IndexOutOfBoundsException("Enum has more than 32 values");
        }
        return (int) elements;
    }

    public long allValues() {
        return -1L >>> -universe.length;
    }

    public boolean set(long mask) {
        if ((mask & ~(-1L >>> -universe.length)) != 0) {
            throw new IndexOutOfBoundsException("bitMask " + mask + " value contains elements outside the universe " + Long.toBinaryString(mask & ~allValues()));
        }

        long oldElements = elements;
        elements |= mask;
        return oldElements != elements;
    }

    public boolean replaceAll(long mask) {
        if ((mask & ~(-1L >>> -universe.length)) != 0) {
            throw new IndexOutOfBoundsException("bitMask " + mask + " value contains elements outside the universe " + Long.toBinaryString(mask & ~allValues()));
        }

        long oldElements = elements;
        elements = mask;
        return oldElements != elements;
    }

    @Override
    public String toString() {
        if (elements == 0) {
            return elementType.getSimpleName() + "{ }";
        } else {
            DelimitedBuilder out = new DelimitedBuilder(", ");

            out.append(elementType.getSimpleName()).append("{ ");
            for (E e : universe) {
                if (some(mask(e))) out.append(e.name()).mark();
            }
            out.unmark().append(" }");
            return out.toString();
        }
    }

    public boolean clear(long mask) {
        long oldElements = elements;
        elements &= ~mask;
        return oldElements != elements;
    }

    public boolean some(long mask) {
        return (elements & mask) != 0;
    }

    public boolean none(long mask) {
        return (elements & mask) == 0;
    }

    public boolean all(long mask) {
        if ((mask & ~(-1L >>> -universe.length)) != 0) {
            throw new IndexOutOfBoundsException("mask " + mask + " value contains elements outside the universe " + Long.toBinaryString(mask & ~allValues()));
        }
        return (elements & mask) == mask;
    }

    public static <E extends Enum<E>> long longMask(E e1) {
        if (getUniverse(e1.getDeclaringClass()).length > 64) {
            throw new IndexOutOfBoundsException("Enum has more than 64 values");
        }
        return 1L << e1.ordinal();
    }

    public static <E extends Enum<E>> int intMask(E e1) {
        if (getUniverse(e1.getDeclaringClass()).length > 32) {
            throw new IndexOutOfBoundsException("Enum has more than 32 values");
        }
        return 1 << e1.ordinal();
    }

    public long mask(E e1) { return 1L << e1.ordinal(); }

    public long mask(E e1, E e2) { return 1L << e1.ordinal() | 1L << e2.ordinal();}

    public long mask(E e1, E e2, E e3) { return 1L << e1.ordinal() | 1L << e2.ordinal() | 1L << e3.ordinal();}

    public long mask(E e1, E e2, E e3, E e4) { return 1L << e1.ordinal() | 1L << e2.ordinal() | 1L << e3.ordinal() | 1L << e4.ordinal();}

    public long mask(E e1, E e2, E e3, E e4, E e5) { return 1L << e1.ordinal() | 1L << e2.ordinal() | 1L << e3.ordinal() | 1L << e4.ordinal() | 1L << e5.ordinal();}

    @SafeVarargs
    final public long mask(E... rest) { return of(elementType, rest).toLong();}

    public boolean set(E e1) { return set(mask(e1)); }

    public boolean set(E e1, E e2) { return set(mask(e1, e2));}

    public boolean set(E e1, E e2, E e3) { return set(mask(e1, e2, e3));}

    public boolean set(E e1, E e2, E e3, E e4) { return set(mask(e1, e2, e3, e4));}

    public boolean set(E e1, E e2, E e3, E e4, E e5) { return set(mask(e1, e2, e3, e4, e5));}

    @SafeVarargs
    final public boolean set(E... rest) { return set(mask(rest));}

    public boolean clear(E e1) { return clear(mask(e1)); }

    public boolean clear(E e1, E e2) { return clear(mask(e1, e2));}

    public boolean clear(E e1, E e2, E e3) { return clear(mask(e1, e2, e3));}

    public boolean clear(E e1, E e2, E e3, E e4) { return clear(mask(e1, e2, e3, e4));}

    public boolean clear(E e1, E e2, E e3, E e4, E e5) { return clear(mask(e1, e2, e3, e4, e5));}

    @SafeVarargs
    final public boolean clear(E... rest) { return clear(mask(rest));}

    public boolean some(E e1) { return some(mask(e1)); }

    public boolean some(E e1, E e2) { return some(mask(e1, e2));}

    public boolean some(E e1, E e2, E e3) { return some(mask(e1, e2, e3));}

    public boolean some(E e1, E e2, E e3, E e4) { return some(mask(e1, e2, e3, e4));}

    public boolean some(E e1, E e2, E e3, E e4, E e5) { return some(mask(e1, e2, e3, e4, e5));}

    @SafeVarargs
    final public boolean some(E... rest) { return some(mask(rest));}

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
     *
     * @return an iterator over the elements contained in this set
     */
    @NotNull
    public Iterator<E> iterator() {
        //noinspection ReturnOfInnerClass
        return new EnumSetIterator<>();
    }

    private class EnumSetIterator<E extends Enum<E>> implements Iterator<E> {
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

        EnumSetIterator() {
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

    /**
     * Returns the number of elements in this set.
     *
     * @return the number of elements in this set
     */
    public int size() {
        return Long.bitCount(elements);
    }

    /**
     * Returns <tt>true</tt> if this set contains no elements.
     *
     * @return <tt>true</tt> if this set contains no elements
     */
    public boolean isEmpty() {
        return elements == 0;
    }

    /**
     * Returns <tt>true</tt> if this set contains the specified element.
     *
     * @param e element to be checked for containment in this collection
     * @return <tt>true</tt> if this set contains the specified element
     */
    public boolean contains(Object e) {
        if (e == null)
            return false;
        Class<?> eClass = e.getClass();
        if (eClass != elementType && eClass.getSuperclass() != elementType)
            return false;

        return (elements & (1L << ((Enum<?>) e).ordinal())) != 0;
    }

    // Modification Operations

    /**
     * Adds the specified element to this set if it is not already present.
     *
     * @param e element to be added to this set
     * @return <tt>true</tt> if the set changed as a result of the call
     * @throws NullPointerException if <tt>e</tt> is null
     */
    public boolean add(E e) {
        typeCheck(e);

        long oldElements = elements;
        elements |= (1L << e.ordinal());
        return elements != oldElements;
    }

    /**
     * Removes the specified element from this set if it is present.
     *
     * @param e element to be removed from this set, if present
     * @return <tt>true</tt> if the set contained the specified element
     */
    public boolean remove(Object e) {
        if (e == null)
            return false;
        Class<?> eClass = e.getClass();
        if (eClass != elementType && eClass.getSuperclass() != elementType)
            return false;

        long oldElements = elements;
        elements &= ~(1L << ((Enum<?>) e).ordinal());
        return elements != oldElements;
    }

    // Bulk Operations

    /**
     * Returns <tt>true</tt> if this set contains all of the elements
     * in the specified collection.
     *
     * @param c collection to be checked for containment in this set
     * @return <tt>true</tt> if this set contains all of the elements
     *         in the specified collection
     * @throws NullPointerException if the specified collection is null
     */
    public boolean containsAll(Collection<?> c) {
        if (!(c instanceof BitEnumSet))
            return super.containsAll(c);

        BitEnumSet<?> es = (BitEnumSet<?>) c;
        if (es.elementType != elementType)
            return es.isEmpty();

        return (es.elements & ~elements) == 0;
    }

    /**
     * Adds all of the elements in the specified collection to this set.
     *
     * @param c collection whose elements are to be added to this set
     * @return <tt>true</tt> if this set changed as a result of the call
     * @throws NullPointerException if the specified collection or any
     *                              of its elements are null
     */
    public boolean addAll(Collection<? extends E> c) {
        if (!(c instanceof BitEnumSet))
            return super.addAll(c);

        BitEnumSet<?> es = (BitEnumSet<?>) c;
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
     * @return <tt>true</tt> if this set changed as a result of the call
     * @throws NullPointerException if the specified collection is null
     */
    public boolean removeAll(Collection<?> c) {
        if (!(c instanceof BitEnumSet))
            return super.removeAll(c);

        BitEnumSet<?> es = (BitEnumSet<?>) c;
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
     * @return <tt>true</tt> if this set changed as a result of the call
     * @throws NullPointerException if the specified collection is null
     */
    public boolean retainAll(Collection<?> c) {
        if (!(c instanceof BitEnumSet))
            return super.retainAll(c);

        BitEnumSet<?> es = (BitEnumSet<?>) c;
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
    public static <T extends Enum<T>> BitEnumSet<T> of(@NotNull Class<T> enumClass, long mask) {
        BitEnumSet<T> optionSet = BitEnumSet.noneOf(enumClass);
        optionSet.set(mask);
        return optionSet;
    }

    /**
     * Returns a copy of this set.
     *
     * @return a copy of this set
     */
    @SuppressWarnings("unchecked")
    public BitEnumSet<E> clone() {
        try {
            return (BitEnumSet<E>) super.clone();
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
        private final Class<E> elementType;

        /**
         * The elements contained in this enum set.
         *
         * @serial
         */
        private final Enum<?>[] elements;

        SerializationProxy(BitEnumSet<E> set) {
            elementType = set.elementType;
            elements = set.toArray(ZERO_LENGTH_ENUM_ARRAY);
        }

        // instead of cast to E, we should perhaps use elementType.cast()
        // to avoid injection of forged stream, but it will slow the implementation
        @SuppressWarnings("unchecked")
        private Object readResolve() {
            BitEnumSet<E> result = BitEnumSet.noneOf(elementType);
            for (Enum<?> e : elements)
                result.add((E) e);
            return result;
        }

        private static final long serialVersionUID = 362491234563181265L;
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
     * <tt>true</tt> if the given object is also a set, the two sets have
     * the same size, and every member of the given set is contained in
     * this set.
     *
     * @param o object to be compared for equality with this set
     * @return <tt>true</tt> if the specified object is equal to this set
     */
    public boolean equals(Object o) {
        if (!(o instanceof BitEnumSet))
            return super.equals(o);

        BitEnumSet<?> es = (BitEnumSet<?>) o;
        if (es.elementType != elementType)
            return elements == 0 && es.elements == 0;
        return es.elements == elements;
    }

    /**
     * Returns all of the values comprising E.
     * The result is uncloned, cached, and shared by all callers.
     */
    private static <E extends Enum<E>> E[] getUniverse(Class<E> elementType) {
        return SharedSecrets.getJavaLangAccess()
                .getEnumConstantsShared(elementType);
    }

    /**
     * Creates an empty enum set with the specified element type.
     *
     * @param <E>         The class of the elements in the set
     * @param elementType the class object of the element type for this enum
     *                    set
     * @return An empty enum set of the specified type.
     * @throws NullPointerException if <tt>elementType</tt> is null
     */
    public static <E extends Enum<E>> BitEnumSet<E> noneOf(Class<E> elementType) {
        Enum<?>[] universe = getUniverse(elementType);
        if (universe == null)
            throw new ClassCastException(elementType + " not an enum");

        if (universe.length <= 64)
            return new BitEnumSet<>(elementType, universe);
        else
            throw new IllegalArgumentException("Enums with more than 64 values are not supported");
    }

    /**
     * Creates an enum set containing all of the elements in the specified
     * element type.
     *
     * @param <E>         The class of the elements in the set
     * @param elementType the class object of the element type for this enum
     *                    set
     * @return An enum set containing all the elements in the specified type.
     * @throws NullPointerException if <tt>elementType</tt> is null
     */
    public static <E extends Enum<E>> BitEnumSet<E> allOf(Class<E> elementType) {
        BitEnumSet<E> result = noneOf(elementType);
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
     * @throws NullPointerException if <tt>s</tt> is null
     */
    public static <E extends Enum<E>> BitEnumSet<E> copyOf(BitEnumSet<E> s) {
        return s.clone();
    }

    /**
     * Creates an enum set initialized from the specified collection.  If
     * the specified collection is an <tt>BitEnumSet</tt> instance, this static
     * factory method behaves identically to {@link #copyOf(BitEnumSet)}.
     * Otherwise, the specified collection must contain at least one element
     * (in order to determine the new enum set's element type).
     *
     * @param <E> The class of the elements in the collection
     * @param c   the collection from which to initialize this enum set
     * @return An enum set initialized from the given collection.
     * @throws IllegalArgumentException if <tt>c</tt> is not an
     *                                  <tt>BitEnumSet</tt> instance and contains no elements
     * @throws NullPointerException     if <tt>c</tt> is null
     */
    public static <E extends Enum<E>> BitEnumSet<E> copyOf(Collection<E> c) {
        if (c instanceof BitEnumSet) {
            return ((BitEnumSet<E>) c).clone();
        } else {
            if (c.isEmpty())
                throw new IllegalArgumentException("Collection is empty");
            Iterator<E> i = c.iterator();
            E first = i.next();
            BitEnumSet<E> result = BitEnumSet.of(first);
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
     * @throws NullPointerException if <tt>s</tt> is null
     */
    public static <E extends Enum<E>> BitEnumSet<E> complementOf(BitEnumSet<E> s) {
        BitEnumSet<E> result = copyOf(s);
        result.complement();
        return result;
    }

    /**
     * Creates an enum set initially containing the specified element.
     * <p>
     * Overloadings of this method exist to initialize an enum set with
     * one through five elements.  A sixth overloading is provided that
     * uses the varargs feature.  This overloading may be used to create
     * an enum set initially containing an arbitrary number of elements, but
     * is likely to run slower than the overloadings that do not use varargs.
     *
     * @param <E> The class of the specified element and of the set
     * @param e   the element that this set is to contain initially
     * @return an enum set initially containing the specified element
     * @throws NullPointerException if <tt>e</tt> is null
     */
    public static <E extends Enum<E>> BitEnumSet<E> of(E e) {
        BitEnumSet<E> result = noneOf(e.getDeclaringClass());
        result.add(e);
        return result;
    }

    /**
     * Creates an enum set initially containing the specified elements.
     * <p>
     * Overloadings of this method exist to initialize an enum set with
     * one through five elements.  A sixth overloading is provided that
     * uses the varargs feature.  This overloading may be used to create
     * an enum set initially containing an arbitrary number of elements, but
     * is likely to run slower than the overloadings that do not use varargs.
     *
     * @param <E> The class of the parameter elements and of the set
     * @param e1  an element that this set is to contain initially
     * @param e2  another element that this set is to contain initially
     * @return an enum set initially containing the specified elements
     * @throws NullPointerException if any parameters are null
     */
    public static <E extends Enum<E>> BitEnumSet<E> of(E e1, E e2) {
        BitEnumSet<E> result = noneOf(e1.getDeclaringClass());
        result.add(e1);
        result.add(e2);
        return result;
    }

    /**
     * Creates an enum set initially containing the specified elements.
     * <p>
     * Overloadings of this method exist to initialize an enum set with
     * one through five elements.  A sixth overloading is provided that
     * uses the varargs feature.  This overloading may be used to create
     * an enum set initially containing an arbitrary number of elements, but
     * is likely to run slower than the overloadings that do not use varargs.
     *
     * @param <E> The class of the parameter elements and of the set
     * @param e1  an element that this set is to contain initially
     * @param e2  another element that this set is to contain initially
     * @param e3  another element that this set is to contain initially
     * @return an enum set initially containing the specified elements
     * @throws NullPointerException if any parameters are null
     */
    public static <E extends Enum<E>> BitEnumSet<E> of(E e1, E e2, E e3) {
        BitEnumSet<E> result = noneOf(e1.getDeclaringClass());
        result.add(e1);
        result.add(e2);
        result.add(e3);
        return result;
    }

    /**
     * Creates an enum set initially containing the specified elements.
     * <p>
     * Overloadings of this method exist to initialize an enum set with
     * one through five elements.  A sixth overloading is provided that
     * uses the varargs feature.  This overloading may be used to create
     * an enum set initially containing an arbitrary number of elements, but
     * is likely to run slower than the overloadings that do not use varargs.
     *
     * @param <E> The class of the parameter elements and of the set
     * @param e1  an element that this set is to contain initially
     * @param e2  another element that this set is to contain initially
     * @param e3  another element that this set is to contain initially
     * @param e4  another element that this set is to contain initially
     * @return an enum set initially containing the specified elements
     * @throws NullPointerException if any parameters are null
     */
    public static <E extends Enum<E>> BitEnumSet<E> of(E e1, E e2, E e3, E e4) {
        BitEnumSet<E> result = noneOf(e1.getDeclaringClass());
        result.add(e1);
        result.add(e2);
        result.add(e3);
        result.add(e4);
        return result;
    }

    /**
     * Creates an enum set initially containing the specified elements.
     * <p>
     * Overloadings of this method exist to initialize an enum set with
     * one through five elements.  A sixth overloading is provided that
     * uses the varargs feature.  This overloading may be used to create
     * an enum set initially containing an arbitrary number of elements, but
     * is likely to run slower than the overloadings that do not use varargs.
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
    public static <E extends Enum<E>> BitEnumSet<E> of(
            E e1, E e2, E e3, E e4,
            E e5
    ) {
        BitEnumSet<E> result = noneOf(e1.getDeclaringClass());
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
     * number of elements, but it is likely to run slower than the overloadings
     * that do not use varargs.
     *
     * @param <E>   The class of the parameter elements and of the set
     * @param first an element that the set is to contain initially
     * @param rest  the remaining elements the set is to contain initially
     * @return an enum set initially containing the specified elements
     * @throws NullPointerException if any of the specified elements are null,
     *                              or if <tt>rest</tt> is null
     */
    @SafeVarargs
    public static <E extends Enum<E>> BitEnumSet<E> of(E first, E... rest) {
        BitEnumSet<E> result = noneOf(first.getDeclaringClass());
        result.add(first);
        for (E e : rest)
            result.add(e);
        return result;
    }

    /**
     * Creates an enum set initially containing the specified elements.
     * This factory, whose parameter list uses the varargs feature, may
     * be used to create an enum set initially containing an arbitrary
     * number of elements, but it is likely to run slower than the overloadings
     * that do not use varargs.
     *
     * @param <E>  The class of the parameter elements and of the set
     * @param rest the remaining elements the set is to contain initially
     * @return an enum set initially containing the specified elements
     * @throws NullPointerException if any of the specified elements are null,
     *                              or if <tt>rest</tt> is null
     */
    public static <E extends Enum<E>> BitEnumSet<E> of(@NotNull Class<E> declaringClass, E[] rest) {
        BitEnumSet<E> result = noneOf(declaringClass);
        for (E e : rest)
            result.add(e);
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
    public static <E extends Enum<E>> BitEnumSet<E> range(E from, E to) {
        if (from.compareTo(to) > 0)
            throw new IllegalArgumentException(from + " > " + to);
        BitEnumSet<E> result = noneOf(from.getDeclaringClass());
        result.addRange(from, to);
        return result;
    }
}
