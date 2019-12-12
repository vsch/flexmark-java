package com.vladsch.flexmark.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.EnumSet;

import static com.vladsch.flexmark.util.EnumSetUtils.*;
import static org.junit.Assert.assertEquals;

public class EnumSetUtilsTest {
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
        assertEquals(EnumSet.noneOf(IntSet.class), toEnumSet(IntSet.class, 0));
        for (int i = 0; i < IntSet.values().length; i++) {
            assertEquals(EnumSet.of(IntSet.values()[i]), toEnumSet(IntSet.class, 1L << i));
        }

        assertEquals(EnumSet.noneOf(OverIntSet.class), toEnumSet(OverIntSet.class, 0));
        for (int i = 0; i < OverIntSet.values().length; i++) {
            assertEquals(EnumSet.of(OverIntSet.values()[i]), toEnumSet(OverIntSet.class, 1L << i));
        }

        assertEquals(EnumSet.noneOf(LongSet.class), toEnumSet(LongSet.class, 0));
        for (int i = 0; i < LongSet.values().length; i++) {
            assertEquals(EnumSet.of(LongSet.values()[i]), toEnumSet(LongSet.class, 1L << i));
        }

        assertEquals(EnumSet.noneOf(OverLongSet.class), toEnumSet(OverLongSet.class, 0));
        for (int i = 0; i < Math.min(64, OverLongSet.values().length); i++) {
            assertEquals(EnumSet.of(OverLongSet.values()[i]), toEnumSet(OverLongSet.class, 1L << i));
        }
    }

    @Test
    public void test_ToEnumSet() {
        assertEquals(EnumSet.noneOf(IntSet.class), toEnumSet(IntSet.class, 0));
        for (int i = 0; i < IntSet.values().length; i++) {
            assertEquals(EnumSet.of(IntSet.values()[i]), toEnumSet(IntSet.class, IntSet.values()[i]));
        }

        assertEquals(EnumSet.noneOf(OverIntSet.class), toEnumSet(OverIntSet.class, 0));
        for (int i = 0; i < OverIntSet.values().length; i++) {
            assertEquals(EnumSet.of(OverIntSet.values()[i]), toEnumSet(OverIntSet.class, OverIntSet.values()[i]));
        }

        assertEquals(EnumSet.noneOf(LongSet.class), toEnumSet(LongSet.class, 0));
        for (int i = 0; i < LongSet.values().length; i++) {
            assertEquals(EnumSet.of(LongSet.values()[i]), toEnumSet(LongSet.class, LongSet.values()[i]));
        }

        assertEquals(EnumSet.noneOf(OverLongSet.class), toEnumSet(OverLongSet.class, 0));
        for (int i = 0; i < Math.min(64, OverLongSet.values().length); i++) {
            assertEquals(EnumSet.of(OverLongSet.values()[i]), toEnumSet(OverLongSet.class, OverLongSet.values()[i]));
        }
    }

    @Test
    public void test_fullSet() {
        assertEquals(EnumSet.allOf(IntSet.class), fullSet(EnumSet.noneOf(IntSet.class)));
        assertEquals(EnumSet.allOf(OverIntSet.class), fullSet(EnumSet.noneOf(OverIntSet.class)));
        assertEquals(EnumSet.allOf(LongSet.class), fullSet(EnumSet.noneOf(LongSet.class)));
        assertEquals(EnumSet.allOf(OverLongSet.class), fullSet(EnumSet.noneOf(OverLongSet.class)));
    }

    @Test
    public void test_toLong() {
        assertEquals(EnumSet.noneOf(IntSet.class), toEnumSet(IntSet.class, 0));
        for (int i = 0; i < IntSet.values().length; i++) {
            assertEquals(1L << i, toLong(toEnumSet(IntSet.class, IntSet.values()[i])));
        }

        assertEquals(EnumSet.noneOf(OverIntSet.class), toEnumSet(OverIntSet.class, 0));
        for (int i = 0; i < OverIntSet.values().length; i++) {
            assertEquals(1L << i, toLong(toEnumSet(OverIntSet.class, OverIntSet.values()[i])));
        }

        assertEquals(EnumSet.noneOf(LongSet.class), toEnumSet(LongSet.class, 0));
        for (int i = 0; i < LongSet.values().length; i++) {
            assertEquals(1L << i, toLong(toEnumSet(LongSet.class, LongSet.values()[i])));
        }

        assertEquals(EnumSet.noneOf(OverLongSet.class), toEnumSet(OverLongSet.class, 0));
        for (int i = 0; i < Math.min(64, OverLongSet.values().length); i++) {
            assertEquals(1L << i, toLong(toEnumSet(OverLongSet.class, OverLongSet.values()[i])));
        }
    }

    @Test
    public void test_toInt() {
        assertEquals(EnumSet.noneOf(IntSet.class), toEnumSet(IntSet.class, 0));
        for (int i = 0; i < IntSet.values().length; i++) {
            assertEquals((int)(1L << i), toInt(toEnumSet(IntSet.class, IntSet.values()[i])));
        }

        assertEquals(EnumSet.noneOf(OverIntSet.class), toEnumSet(OverIntSet.class, 0));
        for (int i = 0; i < OverIntSet.values().length; i++) {
            assertEquals((int)(1L << i), toInt(toEnumSet(OverIntSet.class, OverIntSet.values()[i])));
        }

        assertEquals(EnumSet.noneOf(LongSet.class), toEnumSet(LongSet.class, 0));
        for (int i = 0; i < LongSet.values().length; i++) {
            assertEquals((int)(1L << i), toInt(toEnumSet(LongSet.class, LongSet.values()[i])));
        }

        assertEquals(EnumSet.noneOf(OverLongSet.class), toEnumSet(OverLongSet.class, 0));
        for (int i = 0; i < Math.min(64, OverLongSet.values().length); i++) {
            assertEquals((int)(1L << i), toInt(toEnumSet(OverLongSet.class, OverLongSet.values()[i])));
        }
    }
}
