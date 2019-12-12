package com.vladsch.flexmark.util.collection;

import com.vladsch.flexmark.util.ExceptionMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class BitEnumSetTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
//        thrown.expect(ExceptionMatcher.match(IllegalArgumentException.class, "Position is not valid"));
//        thrown.expect(IndexOutOfBoundsException.class);

    enum IntSet {
        VALUE_0, VALUE_1, VALUE_2, VALUE_3, VALUE_4, VALUE_5, VALUE_6, VALUE_7, VALUE_8, VALUE_9, VALUE_10, VALUE_11, VALUE_12, VALUE_13, VALUE_14, VALUE_15,
        VALUE_16, VALUE_17, VALUE_18, VALUE_19, VALUE_20, VALUE_21, VALUE_22, VALUE_23, VALUE_24, VALUE_25, VALUE_26, VALUE_27, VALUE_28, VALUE_29, VALUE_30, VALUE_31
    }

    enum OverIntSet {
        VALUE_0, VALUE_1, VALUE_2, VALUE_3, VALUE_4, VALUE_5, VALUE_6, VALUE_7, VALUE_8, VALUE_9, VALUE_10, VALUE_11, VALUE_12, VALUE_13, VALUE_14, VALUE_15,
        VALUE_16, VALUE_17, VALUE_18, VALUE_19, VALUE_20, VALUE_21, VALUE_22, VALUE_23, VALUE_24, VALUE_25, VALUE_26, VALUE_27, VALUE_28, VALUE_29, VALUE_30, VALUE_31,
        VALUE_32,
    }

    enum LongSet {
        VALUE_0, VALUE_1, VALUE_2, VALUE_3, VALUE_4, VALUE_5, VALUE_6, VALUE_7, VALUE_8, VALUE_9, VALUE_10, VALUE_11, VALUE_12, VALUE_13, VALUE_14, VALUE_15,
        VALUE_16, VALUE_17, VALUE_18, VALUE_19, VALUE_20, VALUE_21, VALUE_22, VALUE_23, VALUE_24, VALUE_25, VALUE_26, VALUE_27, VALUE_28, VALUE_29, VALUE_30, VALUE_31,
        VALUE_32, VALUE_33, VALUE_34, VALUE_35, VALUE_36, VALUE_37, VALUE_38, VALUE_39, VALUE_40, VALUE_41, VALUE_42, VALUE_43, VALUE_44, VALUE_45, VALUE_46, VALUE_47,
        VALUE_48, VALUE_49, VALUE_50, VALUE_51, VALUE_52, VALUE_53, VALUE_54, VALUE_55, VALUE_56, VALUE_57, VALUE_58, VALUE_59, VALUE_60, VALUE_61, VALUE_62, VALUE_63,
    }

    enum OverLongSet {
        VALUE_0, VALUE_1, VALUE_2, VALUE_3, VALUE_4, VALUE_5, VALUE_6, VALUE_7, VALUE_8, VALUE_9, VALUE_10, VALUE_11, VALUE_12, VALUE_13, VALUE_14, VALUE_15,
        VALUE_16, VALUE_17, VALUE_18, VALUE_19, VALUE_20, VALUE_21, VALUE_22, VALUE_23, VALUE_24, VALUE_25, VALUE_26, VALUE_27, VALUE_28, VALUE_29, VALUE_30, VALUE_31,
        VALUE_32, VALUE_33, VALUE_34, VALUE_35, VALUE_36, VALUE_37, VALUE_38, VALUE_39, VALUE_40, VALUE_41, VALUE_42, VALUE_43, VALUE_44, VALUE_45, VALUE_46, VALUE_47,
        VALUE_48, VALUE_49, VALUE_50, VALUE_51, VALUE_52, VALUE_53, VALUE_54, VALUE_55, VALUE_56, VALUE_57, VALUE_58, VALUE_59, VALUE_60, VALUE_61, VALUE_62, VALUE_63,
        VALUE_64
    }

    @Test
    public void test_toEnumSetLong() {
        assertEquals(BitEnumSet.noneOf(IntSet.class), BitEnumSet.of(IntSet.class, 0));
        for (int i = 0; i < IntSet.values().length; i++) {
            assertEquals(BitEnumSet.of(IntSet.values()[i]), BitEnumSet.of(IntSet.class, 1L << i));
        }

        assertEquals(BitEnumSet.noneOf(OverIntSet.class), BitEnumSet.of(OverIntSet.class, 0));
        for (int i = 0; i < OverIntSet.values().length; i++) {
            assertEquals(BitEnumSet.of(OverIntSet.values()[i]), BitEnumSet.of(OverIntSet.class, 1L << i));
        }

        assertEquals(BitEnumSet.noneOf(LongSet.class), BitEnumSet.of(LongSet.class, 0));
        for (int i = 0; i < LongSet.values().length; i++) {
            assertEquals(BitEnumSet.of(LongSet.values()[i]), BitEnumSet.of(LongSet.class, 1L << i));
        }

        thrown.expect(ExceptionMatcher.match(IllegalArgumentException.class, "Enums with more than 64 values are not supported"));
        assertEquals(BitEnumSet.noneOf(OverLongSet.class), BitEnumSet.of(OverLongSet.class, 0));
        for (int i = 0; i < Math.min(64, OverLongSet.values().length); i++) {
            assertEquals(BitEnumSet.of(OverLongSet.values()[i]), BitEnumSet.of(OverLongSet.class, 1L << i));
        }
    }

    @Test
    public void test_ToEnumSet() {
        assertEquals(BitEnumSet.noneOf(IntSet.class), BitEnumSet.of(IntSet.class, 0));
        for (int i = 0; i < IntSet.values().length; i++) {
            assertEquals(BitEnumSet.of(IntSet.values()[i]), BitEnumSet.of(IntSet.values()[i]));
        }

        assertEquals(BitEnumSet.noneOf(OverIntSet.class), BitEnumSet.of(OverIntSet.class, 0));
        for (int i = 0; i < OverIntSet.values().length; i++) {
            assertEquals(BitEnumSet.of(OverIntSet.values()[i]), BitEnumSet.of(OverIntSet.values()[i]));
        }

        assertEquals(BitEnumSet.noneOf(LongSet.class), BitEnumSet.of(LongSet.class, 0));
        for (int i = 0; i < LongSet.values().length; i++) {
            assertEquals(BitEnumSet.of(LongSet.values()[i]), BitEnumSet.of(LongSet.values()[i]));
        }

        thrown.expect(ExceptionMatcher.match(IllegalArgumentException.class, "Enums with more than 64 values are not supported"));
        assertEquals(BitEnumSet.noneOf(OverLongSet.class), BitEnumSet.of(OverLongSet.class, 0));
        for (int i = 0; i < Math.min(64, OverLongSet.values().length); i++) {
            assertEquals(BitEnumSet.of(OverLongSet.values()[i]), BitEnumSet.of(OverLongSet.values()[i]));
        }
    }

    @Test
    public void test_toLong() {
        assertEquals(BitEnumSet.noneOf(IntSet.class), BitEnumSet.of(IntSet.class, 0));
        for (int i = 0; i < IntSet.values().length; i++) {
            assertEquals(1L << i, BitEnumSet.of(IntSet.values()[i]).toLong());
        }

        assertEquals(BitEnumSet.noneOf(OverIntSet.class), BitEnumSet.of(OverIntSet.class, 0));
        for (int i = 0; i < OverIntSet.values().length; i++) {
            assertEquals(1L << i, BitEnumSet.of(OverIntSet.values()[i]).toLong());
        }

        assertEquals(BitEnumSet.noneOf(LongSet.class), BitEnumSet.of(LongSet.class, 0));
        for (int i = 0; i < LongSet.values().length; i++) {
            assertEquals(1L << i, BitEnumSet.of(LongSet.values()[i]).toLong());
        }

        thrown.expect(ExceptionMatcher.match(IllegalArgumentException.class, "Enums with more than 64 values are not supported"));
        assertEquals(BitEnumSet.noneOf(OverLongSet.class), BitEnumSet.of(OverLongSet.class, 0));
        for (int i = 0; i < Math.min(64, OverLongSet.values().length); i++) {
            assertEquals(1L << i, BitEnumSet.of(OverLongSet.values()[i]).toLong());
        }
    }

    @Test
    public void test_toInt() {
        assertEquals(BitEnumSet.noneOf(IntSet.class), BitEnumSet.of(IntSet.class, 0));
        for (int i = 0; i < IntSet.values().length; i++) {
            assertEquals((int) (1L << i), BitEnumSet.of(IntSet.values()[i]).toInt());
        }

        assertEquals(BitEnumSet.noneOf(OverIntSet.class), BitEnumSet.of(OverIntSet.class, 0));
        for (int i = 0; i < OverIntSet.values().length; i++) {
            thrown.expect(ExceptionMatcher.match(IndexOutOfBoundsException.class, "Enum has more than 32 values"));
            assertEquals((int) (1L << i), BitEnumSet.of(OverIntSet.values()[i]).toInt());
        }
    }

    @Test
    public void someNoneAll() {
        assertEquals(BitEnumSet.noneOf(IntSet.class), BitEnumSet.of(IntSet.class, 0));
        for (int i = 0; i < IntSet.values().length; i++) {
            assertEquals(1L << i, BitEnumSet.of(IntSet.class, 1L << i).mask(IntSet.values()[i]));
            assertTrue(BitEnumSet.of(IntSet.class, 1L << i).any(1L << i));
            assertTrue(BitEnumSet.of(IntSet.class, 1L << i).any(IntSet.values()[i]));
            assertFalse(BitEnumSet.of(IntSet.class, 1L << i).none(1L << i));
            assertFalse(BitEnumSet.of(IntSet.class, 1L << i).none(IntSet.values()));
            assertTrue(BitEnumSet.of(IntSet.class, 1L << i).all(IntSet.values()[i]));
            assertFalse(BitEnumSet.of(IntSet.class, 1L << i).all(IntSet.values()));
        }

        assertEquals(BitEnumSet.noneOf(OverIntSet.class), BitEnumSet.of(OverIntSet.class, 0));
        for (int i = 0; i < OverIntSet.values().length; i++) {
            assertEquals(1L << i, BitEnumSet.of(OverIntSet.class, 1L << i).mask(OverIntSet.values()[i]));
            assertTrue(BitEnumSet.of(OverIntSet.class, 1L << i).any(1L << i));
            assertTrue(BitEnumSet.of(OverIntSet.class, 1L << i).any(OverIntSet.values()[i]));
            assertFalse(BitEnumSet.of(OverIntSet.class, 1L << i).none(1L << i));
            assertFalse(BitEnumSet.of(OverIntSet.class, 1L << i).none(OverIntSet.values()));
            assertTrue(BitEnumSet.of(OverIntSet.class, 1L << i).all(OverIntSet.values()[i]));
            assertFalse(BitEnumSet.of(OverIntSet.class, 1L << i).all(OverIntSet.values()));
        }

        assertEquals(BitEnumSet.noneOf(LongSet.class), BitEnumSet.of(LongSet.class, 0));
        for (int i = 0; i < LongSet.values().length; i++) {
            assertEquals(1L << i, BitEnumSet.of(LongSet.class, 1L << i).mask(LongSet.values()[i]));
            assertTrue(BitEnumSet.of(LongSet.class, 1L << i).any(1L << i));
            assertTrue(BitEnumSet.of(LongSet.class, 1L << i).any(LongSet.values()[i]));
            assertFalse(BitEnumSet.of(LongSet.class, 1L << i).none(1L << i));
            assertFalse(BitEnumSet.of(LongSet.class, 1L << i).none(LongSet.values()));
            assertTrue(BitEnumSet.of(LongSet.class, 1L << i).all(LongSet.values()[i]));
            assertFalse(BitEnumSet.of(LongSet.class, 1L << i).all(LongSet.values()));
        }
    }

    @Test
    public void test_outsideUniverseInt() {
        thrown.expect(ExceptionMatcher.match(IndexOutOfBoundsException.class, "mask 4294967296 value contains elements outside the universe 100000000000000000000000000000000"));
        assertFalse(BitEnumSet.of(IntSet.values()[31]).all(1L << 32));
    }

    @Test
    public void test_outsideUniverseOverInt() {
        thrown.expect(ExceptionMatcher.match(IndexOutOfBoundsException.class, "mask 8589934592 value contains elements outside the universe 1000000000000000000000000000000000"));
        assertFalse(BitEnumSet.of(OverIntSet.values()[32]).all(1L << 33));
    }

    @Test
    public void test_toString() {
        assertEquals("IntSet{ }", BitEnumSet.noneOf(IntSet.class).toString());
        assertEquals("IntSet{ VALUE_0 }", BitEnumSet.of(IntSet.VALUE_0).toString());
        assertEquals("IntSet{ VALUE_0, VALUE_2 }", BitEnumSet.of(IntSet.VALUE_0, IntSet.VALUE_2).toString());
        assertEquals("IntSet{ VALUE_0, VALUE_31 }", BitEnumSet.of(IntSet.VALUE_0, IntSet.VALUE_31).toString());
    }
}
