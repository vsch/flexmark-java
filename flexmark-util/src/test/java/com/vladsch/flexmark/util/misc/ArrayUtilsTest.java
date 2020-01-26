package com.vladsch.flexmark.util.misc;

import org.junit.Test;

import java.util.BitSet;
import java.util.Objects;

import static com.vladsch.flexmark.util.misc.ArrayUtils.*;
import static org.junit.Assert.*;

public class ArrayUtilsTest {

    @Test
    public void test_contained() {
        assertTrue(ArrayUtils.contained(-339_763_186, new Object[] { -339_763_186 }));
        assertTrue(ArrayUtils.contained(0, new int[] { 0 }));
        assertFalse(ArrayUtils.contained(-2_147_483_647, new Object[] { }));
        assertFalse(ArrayUtils.contained(-1_547_722_752, new Object[] { -339_763_186 }));
        assertFalse(ArrayUtils.contained(0, new int[] { }));
        assertFalse(ArrayUtils.contained(0, new int[] { 1 }));
    }

    @Test
    public void test_indexOf() {
        Integer[] ints = new Integer[] {
                1,     // 0
                4,     // 1
                1,     // 2
                null,  // 3
                3,     // 4
                5,     // 5
                null,  // 6
                1,     // 7
                2,     // 8
                4,     // 9
                3,     // 10
                2,     // 11
                0      // 12
        };

        assertEquals(-1, indexOf(ints, i -> i != null && i == 6));
        assertEquals(0, indexOf(ints, i -> i != null && i == 1));
        assertEquals(0, indexOf(ints, 0, i -> i != null && i == 1));
        assertEquals(2, indexOf(ints, 1, i -> i != null && i == 1));
        assertEquals(2, indexOf(ints, 2, i -> i != null && i == 1));
        assertEquals(2, indexOf(ints, 2, i -> i != null && i == 1));
        assertEquals(3, indexOf(ints, 0, Objects::isNull));
        assertEquals(5, indexOf(ints, 0, i -> i != null && i == 5));
        assertEquals(12, indexOf(ints, 0, i -> i != null && i == 0));
    }

    @Test
    public void test_firstOf() {
        Integer[] ints = new Integer[] {
                1,     // 0
                4,     // 1
                1,     // 2
                null,  // 3
                3,     // 4
                5,     // 5
                null,  // 6
                1,     // 7
                2,     // 8
                4,     // 9
                3,     // 10
                2,     // 11
                0      // 12
        };

        assertEquals((Integer) null, firstOf(ints, i -> i != null && i == 6));
        assertEquals(Integer.valueOf(1), firstOf(ints, i -> i != null && i == 1));
        assertEquals(Integer.valueOf(1), firstOf(ints, 0, i -> i != null && i == 1));
        assertEquals(Integer.valueOf(1), firstOf(ints, 1, i -> i != null && i == 1));
        assertEquals(Integer.valueOf(1), firstOf(ints, 2, i -> i != null && i == 1));
        assertEquals(Integer.valueOf(1), firstOf(ints, 2, i -> i != null && i == 1));
        assertEquals((Integer) null, firstOf(ints, 0, Objects::isNull));
        assertEquals(Integer.valueOf(5), firstOf(ints, 0, i -> i != null && i == 5));
        assertEquals(Integer.valueOf(0), firstOf(ints, 0, i -> i != null && i == 0));
    }

    @Test
    public void test_lastIndexOf() {
        Integer[] ints = new Integer[] {
                1,     // 0
                4,     // 1
                1,     // 2
                null,  // 3
                3,     // 4
                5,     // 5
                null,  // 6
                1,     // 7
                2,     // 8
                4,     // 9
                3,     // 10
                2,     // 11
                0      // 12
        };

        assertEquals(-1, lastIndexOf(ints, i -> i != null && i == 6));
        assertEquals(7, lastIndexOf(ints, i -> i != null && i == 1));
        assertEquals(7, lastIndexOf(ints, 7, i -> i != null && i == 1));
        assertEquals(2, lastIndexOf(ints, 6, i -> i != null && i == 1));
        assertEquals(2, lastIndexOf(ints, 3, i -> i != null && i == 1));
        assertEquals(2, lastIndexOf(ints, 2, i -> i != null && i == 1));
        assertEquals(0, lastIndexOf(ints, 1, i -> i != null && i == 1));
        assertEquals(-1, lastIndexOf(ints, 1, 1, i -> i != null && i == 1));
        assertEquals(7, lastIndexOf(ints, 10, i -> i != null && i == 1));
        assertEquals(-1, lastIndexOf(ints, 0, Objects::isNull));
        assertEquals(3, lastIndexOf(ints, 5, Objects::isNull));
        assertEquals(3, lastIndexOf(ints, 3, 5, Objects::isNull));
        assertEquals(-1, lastIndexOf(ints, 4, 5, Objects::isNull));
        assertEquals(5, lastIndexOf(ints, 20, i -> i != null && i == 5));
        assertEquals(5, lastIndexOf(ints, 5, i -> i != null && i == 5));
        assertEquals(-1, lastIndexOf(ints, 4, i -> i != null && i == 5));
        assertEquals(12, lastIndexOf(ints, 15, i -> i != null && i == 0));
        assertEquals(12, lastIndexOf(ints, 12, i -> i != null && i == 0));
        assertEquals(-1, lastIndexOf(ints, 11, i -> i != null && i == 0));
    }

    @Test
    public void test_lastOf() {
        Integer[] ints = new Integer[] {
                1,     // 0
                4,     // 1
                1,     // 2
                null,  // 3
                3,     // 4
                5,     // 5
                null,  // 6
                1,     // 7
                2,     // 8
                4,     // 9
                3,     // 10
                2,     // 11
                0      // 12
        };

        assertEquals((Integer) null, lastOf(ints, i -> i != null && i == 6));
        assertEquals(Integer.valueOf(1), lastOf(ints, i -> i != null && i == 1));
        assertEquals(Integer.valueOf(1), lastOf(ints, 7, i -> i != null && i == 1));
        assertEquals(Integer.valueOf(1), lastOf(ints, 6, i -> i != null && i == 1));
        assertEquals(Integer.valueOf(1), lastOf(ints, 3, i -> i != null && i == 1));
        assertEquals(Integer.valueOf(1), lastOf(ints, 2, i -> i != null && i == 1));
        assertEquals(Integer.valueOf(1), lastOf(ints, 1, i -> i != null && i == 1));
        assertEquals((Integer) null, lastOf(ints, 1, 1, i -> i != null && i == 1));
        assertEquals(Integer.valueOf(1), lastOf(ints, 10, i -> i != null && i == 1));
        assertEquals((Integer) null, lastOf(ints, 0, Objects::isNull));
        assertEquals((Integer) null, lastOf(ints, 5, Objects::isNull));
        assertEquals((Integer) null, lastOf(ints, 3, 5, Objects::isNull));
        assertEquals((Integer) null, lastOf(ints, 4, 5, Objects::isNull));
        assertEquals(Integer.valueOf(5), lastOf(ints, 20, i -> i != null && i == 5));
        assertEquals(Integer.valueOf(5), lastOf(ints, 5, i -> i != null && i == 5));
        assertEquals((Integer) null, lastOf(ints, 4, i -> i != null && i == 5));
        assertEquals(Integer.valueOf(0), lastOf(ints, 15, i -> i != null && i == 0));
        assertEquals(Integer.valueOf(0), lastOf(ints, 12, i -> i != null && i == 0));
        assertEquals((Integer) null, lastOf(ints, 11, i -> i != null && i == 0));
    }

    @Test
    public void test_toArrayBitSet() {
        BitSet bitSet = new BitSet();

        assertArrayEquals(new int[] { }, toArray(bitSet));
        bitSet.set(0);
        assertArrayEquals(new int[] { 0 }, toArray(bitSet));
        bitSet.set(5);
        assertArrayEquals(new int[] { 0, 5 }, toArray(bitSet));
        bitSet.set(3);
        assertArrayEquals(new int[] { 0, 3, 5 }, toArray(bitSet));
        bitSet.set(100);
        assertArrayEquals(new int[] { 0, 3, 5, 100 }, toArray(bitSet));
    }
}
