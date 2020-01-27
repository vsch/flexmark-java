package com.vladsch.flexmark.experimental.util.collection;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class BitIntegerSetTest {
    final private static int[] EMPTY = new int[0];

    @Test
    public void test_toArrayNull0() {
        BitIntegerSet intSet = new BitIntegerSet();

        int[] result = intSet.toArray((int[]) null);
        assertArrayEquals(EMPTY, result);
    }

    @Test
    public void test_toArrayNull2() {
        BitIntegerSet intSet = new BitIntegerSet();
        int[] expected = { 2, 20 };
        intSet.addAll(expected);

        int[] result = intSet.toArray((int[]) null);
        assertArrayEquals(expected, result);
    }

    @Test
    public void test_toArrayEmpty0() {
        BitIntegerSet intSet = new BitIntegerSet();

        int[] result = intSet.toArray(EMPTY);
        assertEquals(EMPTY, result);
    }

    @Test
    public void test_toArrayEmpty2() {
        BitIntegerSet intSet = new BitIntegerSet();
        int[] expected = { 2, 20 };
        intSet.addAll(expected);

        int[] result = intSet.toArray(EMPTY);
        assertArrayEquals(expected, result);
    }

    @Test
    public void test_toArrayAppend0() {
        BitIntegerSet intSet = new BitIntegerSet();
        int[] existing = { 3, 30 };

        int[] result = intSet.toArray(existing, 2);

        assertEquals(existing, result);
    }

    @Test
    public void test_toArrayOverwrite2() {
        BitIntegerSet intSet = new BitIntegerSet();
        int[] existing = { 3, 30 };
        int[] expected = { 2, 20 };
        intSet.addAll(expected);

        int[] result = intSet.toArray(existing);
        assertArrayEquals(expected, result);
    }

    @Test
    public void test_toArrayOverwrite2_0() {
        BitIntegerSet intSet = new BitIntegerSet();
        int[] existing = { 3, 30 };
        int[] expected = { 2, 20 };
        intSet.addAll(expected);

        int[] result = intSet.toArray(existing);
        assertArrayEquals(expected, result);

        int[] result1 = intSet.toArray(existing, 0);
        assertArrayEquals(expected, result1);
    }

    @Test
    public void test_toArrayOverwrite1_1() {
        BitIntegerSet intSet = new BitIntegerSet();
        int[] existing = { 3, 30 };
        int[] values = { 2, 20 };
        intSet.addAll(values);

        int[] expected = { 3, 2, 20 };
        int[] result = intSet.toArray(existing, 1);
        assertArrayEquals(expected, result);
    }

    @Test
    public void test_toArrayOverwrite0_2() {
        BitIntegerSet intSet = new BitIntegerSet();
        int[] existing = { 3, 30 };
        int[] values = { 2, 20 };
        intSet.addAll(values);

        int[] expected = { 3, 30, 2, 20 };
        int[] result = intSet.toArray(existing, 2);
        assertArrayEquals(expected, result);
    }
}
