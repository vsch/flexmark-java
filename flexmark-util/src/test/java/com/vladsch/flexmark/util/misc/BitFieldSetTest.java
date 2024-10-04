package com.vladsch.flexmark.util.misc;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class BitFieldSetTest {
  @Test
  public void test_bitField() {
    assertEquals(
        0x0000_0000_0000_0001L, BitFieldSet.noneOf(BitFields.class).mask(BitFields.FIELD_0));
    assertEquals(
        0x0000_0000_0000_0002L, BitFieldSet.noneOf(BitFields.class).mask(BitFields.FIELD_1));
    assertEquals(
        0x0000_0000_0000_000cL, BitFieldSet.noneOf(BitFields.class).mask(BitFields.FIELD_2));
    assertEquals(
        0x0000_0000_0000_0070L, BitFieldSet.noneOf(BitFields.class).mask(BitFields.FIELD_3));
    assertEquals(
        0x0000_0000_0000_0780L, BitFieldSet.noneOf(BitFields.class).mask(BitFields.FIELD_4));
    assertEquals(
        0x0000_0000_0000_f800L, BitFieldSet.noneOf(BitFields.class).mask(BitFields.FIELD_5));
    assertEquals(
        0x0000_0000_003f_0000L, BitFieldSet.noneOf(BitFields.class).mask(BitFields.FIELD_6));
    assertEquals(
        0x0000_0000_1fc0_0000L, BitFieldSet.noneOf(BitFields.class).mask(BitFields.FIELD_7));
    assertEquals(
        0x0000_001f_e000_0000L, BitFieldSet.noneOf(BitFields.class).mask(BitFields.FIELD_8));
    assertEquals(
        0x0000_3fe0_0000_0000L, BitFieldSet.noneOf(BitFields.class).mask(BitFields.FIELD_9));
    assertEquals(
        0x00ff_c000_0000_0000L, BitFieldSet.noneOf(BitFields.class).mask(BitFields.FIELD_10));
    assertEquals(
        0x0f00_0000_0000_0000L, BitFieldSet.noneOf(BitFields.class).mask(BitFields.FIELD_11));
    assertEquals(
        0x3000_0000_0000_0000L, BitFieldSet.noneOf(BitFields.class).mask(BitFields.FIELD_12));
    assertEquals(
        0x4000_0000_0000_0000L, BitFieldSet.noneOf(BitFields.class).mask(BitFields.FIELD_13));
    assertEquals(
        0x8000_0000_0000_0000L, BitFieldSet.noneOf(BitFields.class).mask(BitFields.FIELD_14));

    IllegalArgumentException illegalArgumentException =
        Assert.assertThrows(
            IllegalArgumentException.class, () -> BitFieldSet.noneOf(BitFields2.class));
    Assert.assertEquals(
        "Enum bit field BitFields2.FIELD_15 bits exceed available 64 bits by 1",
        illegalArgumentException.getMessage());
  }

  @Test
  public void test_bitSetGet() {
    BitFieldSet<BitFields> bitFields = BitFieldSet.noneOf(BitFields.class);
    for (BitFields fields : BitFields.values()) {
      int mask = (1 << fields.getBits()) - 1;
      int iMin = -(1 << fields.getBits() / 2);
      int iMax = (1 << fields.getBits() / 2) - 1;
      for (int i = iMin; i < iMax; i++) {
        bitFields.setBitField(fields, i);
        assertEquals("field: " + fields.name() + " value: " + i, (i & mask), bitFields.get(fields));

        bitFields.setBitField(fields, i);
        assertEquals("field: " + fields.name() + " value: " + i, i, bitFields.getLong(fields));

        bitFields.setBitField(fields, i);
        assertEquals("field: " + fields.name() + " value: " + i, i, bitFields.getInt(fields));

        bitFields.setBitField(fields, (short) i);
        assertEquals("field: " + fields.name() + " value: " + i, i, bitFields.getShort(fields));

        if (fields.getBits() <= 8) {
          bitFields.setBitField(fields, (byte) i);
          assertEquals("field: " + fields.name() + " value: " + i, i, bitFields.getByte(fields));
        }
      }
    }
  }

  @Test
  public void test_BitFieldIterator() {
    BitFieldSet<BitFields> bitFields = BitFieldSet.noneOf(BitFields.class);
    bitFields.add(BitFields.FIELD_1);
    bitFields.setBitField(BitFields.FIELD_3, 2);
    bitFields.setBitField(BitFields.FIELD_5, -10);
    bitFields.setBitField(BitFields.FIELD_10, 381);
    bitFields.setBitField(BitFields.FIELD_11, -6);
    int[] expected = {-1, 2, -10, 381, -6};
    List<Integer> actualList = new ArrayList<>();

    for (BitFields fields : bitFields) {
      actualList.add(bitFields.getInt(fields));
    }

    int iMax = actualList.size();
    int[] actual = new int[iMax];
    for (int i = 0; i < iMax; i++) {
      actual[i] = actualList.get(i);
    }

    assertArrayEquals(expected, actual);
  }

  @Test
  public void test_BitFieldToString() {
    BitFieldSet<BitFields> bitFields = BitFieldSet.noneOf(BitFields.class);
    bitFields.add(BitFields.FIELD_1);
    bitFields.setBitField(BitFields.FIELD_3, 2);
    bitFields.setBitField(BitFields.FIELD_5, -10);
    bitFields.setBitField(BitFields.FIELD_10, 381);
    bitFields.setBitField(BitFields.FIELD_11, -6);
    assertEquals(
        "BitFields: { FIELD_1, FIELD_3(2), FIELD_5(-10), FIELD_10(381), FIELD_11(-6) }",
        bitFields.toString());
  }

  @Test
  public void test_BitSetIterator() {
    BitFieldSet<IntSet> bitFields =
        BitFieldSet.of(IntSet.VALUE_2, IntSet.VALUE_4, IntSet.VALUE_12, IntSet.VALUE_21);
    IntSet[] expected = {
      IntSet.VALUE_2, IntSet.VALUE_4, IntSet.VALUE_12, IntSet.VALUE_21,
    };
    List<IntSet> actualList = new ArrayList<>();

    for (IntSet fields : bitFields) {
      actualList.add(fields);
    }

    assertArrayEquals(expected, actualList.toArray());
  }

  @Test
  public void test_BitSetIteratorRemove() {
    BitFieldSet<IntSet> bitFields =
        BitFieldSet.of(
            IntSet.VALUE_2, IntSet.VALUE_4, IntSet.VALUE_12, IntSet.VALUE_21, IntSet.VALUE_17);
    IntSet[] expected = {
      IntSet.VALUE_2, IntSet.VALUE_4, IntSet.VALUE_12, IntSet.VALUE_21,
    };

    Iterator<IntSet> iterator = bitFields.iterator();

    while (iterator.hasNext()) {
      IntSet fields = iterator.next();
      if (fields == IntSet.VALUE_17) {
        iterator.remove();
      }
    }

    assertArrayEquals(expected, bitFields.toArray());
  }

  @Test
  public void test_BitFieldIteratorRemove() {
    BitFieldSet<BitFields> bitFields = BitFieldSet.noneOf(BitFields.class);
    bitFields.add(BitFields.FIELD_1);
    bitFields.setBitField(BitFields.FIELD_3, 2);
    bitFields.setBitField(BitFields.FIELD_5, -10);
    bitFields.setBitField(BitFields.FIELD_10, 381);
    bitFields.setBitField(BitFields.FIELD_11, -6);
    bitFields.setBitField(BitFields.FIELD_7, 57);

    int[] expected = {-1, 2, -10, 381, -6};
    List<Integer> actualList = new ArrayList<>();

    Iterator<BitFields> iterator = bitFields.iterator();

    while (iterator.hasNext()) {
      BitFields fields = iterator.next();
      if (fields == BitFields.FIELD_7) {
        iterator.remove();
      }
    }

    for (BitFields fields : bitFields) {
      actualList.add(bitFields.getInt(fields));
    }

    int iMax = actualList.size();
    int[] actual = new int[iMax];
    for (int i = 0; i < iMax; i++) {
      actual[i] = actualList.get(i);
    }

    assertArrayEquals(expected, actual);
  }

  @Test
  public void test_bitSetGetErr() {
    BitFieldSet<BitFields> bitFieldSet = BitFieldSet.noneOf(BitFields.class);
    IllegalArgumentException illegalArgumentException =
        Assert.assertThrows(
            IllegalArgumentException.class, () -> bitFieldSet.setBitField(BitFields.FIELD_11, 16));
    Assert.assertEquals(
        "Enum field BitFields.FIELD_11 is 4 bits, value range is [-8, 7], cannot be set to 16",
        illegalArgumentException.getMessage());
  }

  @Test
  public void test_bitSetGetErr2() {
    BitFieldSet<BitFields> bitFields = BitFieldSet.noneOf(BitFields.class);

    IllegalArgumentException illegalArgumentException =
        Assert.assertThrows(
            IllegalArgumentException.class, () -> bitFields.setBitField(BitFields.FIELD_11, -9));
    Assert.assertEquals(
        "Enum field BitFields.FIELD_11 is 4 bits, value range is [-8, 7], cannot be set to -9",
        illegalArgumentException.getMessage());
  }

  @Test
  public void test_bitSetGetErr3() {
    BitFieldSet<BitFields> bitFields = BitFieldSet.noneOf(BitFields.class);

    IllegalArgumentException illegalArgumentException =
        Assert.assertThrows(
            IllegalArgumentException.class, () -> bitFields.setBitField(BitFields.FIELD_1, -2));
    Assert.assertEquals(
        "Enum field BitFields.FIELD_1 is 1 bit, value range is [-1, 0], cannot be set to -2",
        illegalArgumentException.getMessage());
  }

  @Test
  public void test_bitSetGetErr4() {
    BitFieldSet<BitFields> bitFields = BitFieldSet.noneOf(BitFields.class);

    IllegalArgumentException illegalArgumentException =
        Assert.assertThrows(
            IllegalArgumentException.class, () -> bitFields.setBitField(BitFields.FIELD_1, 1));
    Assert.assertEquals(
        "Enum field BitFields.FIELD_1 is 1 bit, value range is [-1, 0], cannot be set to 1",
        illegalArgumentException.getMessage());
  }

  @Test
  public void test_toEnumSetLong() {
    assertEquals(BitFieldSet.noneOf(IntSet.class), BitFieldSet.of(IntSet.class, 0));
    for (int i = 0; i < IntSet.values().length; i++) {
      assertEquals(BitFieldSet.of(IntSet.values()[i]), BitFieldSet.of(IntSet.class, 1L << i));
    }

    assertEquals(BitFieldSet.noneOf(OverIntSet.class), BitFieldSet.of(OverIntSet.class, 0));
    for (int i = 0; i < OverIntSet.values().length; i++) {
      assertEquals(
          BitFieldSet.of(OverIntSet.values()[i]), BitFieldSet.of(OverIntSet.class, 1L << i));
    }

    assertEquals(BitFieldSet.noneOf(LongSet.class), BitFieldSet.of(LongSet.class, 0));
    for (int i = 0; i < LongSet.values().length; i++) {
      assertEquals(BitFieldSet.of(LongSet.values()[i]), BitFieldSet.of(LongSet.class, 1L << i));
    }

    IllegalArgumentException illegalArgumentException =
        Assert.assertThrows(
            IllegalArgumentException.class, () -> BitFieldSet.noneOf(OverLongSet.class));
    Assert.assertEquals(
        "Enums with more than 64 values are not supported", illegalArgumentException.getMessage());
  }

  @Test
  public void test_ToEnumSet() {
    assertEquals(BitFieldSet.noneOf(IntSet.class), BitFieldSet.of(IntSet.class, 0));
    for (int i = 0; i < IntSet.values().length; i++) {
      assertEquals(BitFieldSet.of(IntSet.values()[i]), BitFieldSet.of(IntSet.values()[i]));
    }

    assertEquals(BitFieldSet.noneOf(OverIntSet.class), BitFieldSet.of(OverIntSet.class, 0));
    for (int i = 0; i < OverIntSet.values().length; i++) {
      assertEquals(BitFieldSet.of(OverIntSet.values()[i]), BitFieldSet.of(OverIntSet.values()[i]));
    }

    assertEquals(BitFieldSet.noneOf(LongSet.class), BitFieldSet.of(LongSet.class, 0));
    for (int i = 0; i < LongSet.values().length; i++) {
      assertEquals(BitFieldSet.of(LongSet.values()[i]), BitFieldSet.of(LongSet.values()[i]));
    }

    IllegalArgumentException illegalArgumentException =
        Assert.assertThrows(
            IllegalArgumentException.class, () -> BitFieldSet.noneOf(OverLongSet.class));
    Assert.assertEquals(
        "Enums with more than 64 values are not supported", illegalArgumentException.getMessage());
  }

  @Test
  public void test_toLong() {
    assertEquals(BitFieldSet.noneOf(IntSet.class), BitFieldSet.of(IntSet.class, 0));
    for (int i = 0; i < IntSet.values().length; i++) {
      assertEquals(1L << i, BitFieldSet.of(IntSet.values()[i]).toLong());
    }

    assertEquals(BitFieldSet.noneOf(OverIntSet.class), BitFieldSet.of(OverIntSet.class, 0));
    for (int i = 0; i < OverIntSet.values().length; i++) {
      assertEquals(1L << i, BitFieldSet.of(OverIntSet.values()[i]).toLong());
    }

    assertEquals(BitFieldSet.noneOf(LongSet.class), BitFieldSet.of(LongSet.class, 0));
    for (int i = 0; i < LongSet.values().length; i++) {
      assertEquals(1L << i, BitFieldSet.of(LongSet.values()[i]).toLong());
    }

    IllegalArgumentException illegalArgumentException =
        Assert.assertThrows(
            IllegalArgumentException.class, () -> BitFieldSet.noneOf(OverLongSet.class));
    Assert.assertEquals(
        "Enums with more than 64 values are not supported", illegalArgumentException.getMessage());
  }

  @Test
  public void test_toInt() {
    assertEquals(BitFieldSet.noneOf(IntSet.class), BitFieldSet.of(IntSet.class, 0));
    for (int i = 0; i < IntSet.values().length; i++) {
      assertEquals((int) (1L << i), BitFieldSet.of(IntSet.values()[i]).toInt());
    }

    IllegalArgumentException illegalArgumentException =
        Assert.assertThrows(
            IllegalArgumentException.class, () -> BitFieldSet.of(OverIntSet.values()[0]).toInt());
    Assert.assertEquals(
        "Enum fields use 33 bits, which is more than 32 bits available in an int",
        illegalArgumentException.getMessage());

    assertEquals(BitFieldSet.noneOf(OverIntSet.class), BitFieldSet.of(OverIntSet.class, 0));
  }

  @Test
  public void someNoneAll() {
    assertEquals(BitFieldSet.noneOf(IntSet.class), BitFieldSet.of(IntSet.class, 0));
    for (int i = 0; i < IntSet.values().length; i++) {
      assertEquals(1L << i, BitFieldSet.of(IntSet.class, 1L << i).mask(IntSet.values()[i]));
      assertTrue(BitFieldSet.of(IntSet.class, 1L << i).any(1L << i));
      assertTrue(BitFieldSet.of(IntSet.class, 1L << i).any(IntSet.values()[i]));
      assertFalse(BitFieldSet.of(IntSet.class, 1L << i).none(1L << i));
      assertFalse(BitFieldSet.of(IntSet.class, 1L << i).none(IntSet.values()));
      assertTrue(BitFieldSet.of(IntSet.class, 1L << i).all(IntSet.values()[i]));
      assertFalse(BitFieldSet.of(IntSet.class, 1L << i).all(IntSet.values()));
    }

    assertEquals(BitFieldSet.noneOf(OverIntSet.class), BitFieldSet.of(OverIntSet.class, 0));
    for (int i = 0; i < OverIntSet.values().length; i++) {
      assertEquals(1L << i, BitFieldSet.of(OverIntSet.class, 1L << i).mask(OverIntSet.values()[i]));
      assertTrue(BitFieldSet.of(OverIntSet.class, 1L << i).any(1L << i));
      assertTrue(BitFieldSet.of(OverIntSet.class, 1L << i).any(OverIntSet.values()[i]));
      assertFalse(BitFieldSet.of(OverIntSet.class, 1L << i).none(1L << i));
      assertFalse(BitFieldSet.of(OverIntSet.class, 1L << i).none(OverIntSet.values()));
      assertTrue(BitFieldSet.of(OverIntSet.class, 1L << i).all(OverIntSet.values()[i]));
      assertFalse(BitFieldSet.of(OverIntSet.class, 1L << i).all(OverIntSet.values()));
    }

    assertEquals(BitFieldSet.noneOf(LongSet.class), BitFieldSet.of(LongSet.class, 0));
    for (int i = 0; i < LongSet.values().length; i++) {
      assertEquals(1L << i, BitFieldSet.of(LongSet.class, 1L << i).mask(LongSet.values()[i]));
      assertTrue(BitFieldSet.of(LongSet.class, 1L << i).any(1L << i));
      assertTrue(BitFieldSet.of(LongSet.class, 1L << i).any(LongSet.values()[i]));
      assertFalse(BitFieldSet.of(LongSet.class, 1L << i).none(1L << i));
      assertFalse(BitFieldSet.of(LongSet.class, 1L << i).none(LongSet.values()));
      assertTrue(BitFieldSet.of(LongSet.class, 1L << i).all(LongSet.values()[i]));
      assertFalse(BitFieldSet.of(LongSet.class, 1L << i).all(LongSet.values()));
    }
  }

  @Test
  public void test_outsideUniverseInt() {
    BitFieldSet<IntSet> bitFieldSet = BitFieldSet.of(IntSet.values()[31]);
    IllegalArgumentException illegalArgumentException =
        Assert.assertThrows(IllegalArgumentException.class, () -> bitFieldSet.all(1L << 32));
    Assert.assertEquals(
        "mask 4294967296(0b100000000000000000000000000000000) value contains elements outside the universe 0b100000000000000000000000000000000",
        illegalArgumentException.getMessage());
  }

  @Test
  public void test_outsideUniverseOverInt() {
    BitFieldSet<OverIntSet> bitFieldSet = BitFieldSet.of(OverIntSet.values()[32]);
    IllegalArgumentException illegalArgumentException =
        Assert.assertThrows(IllegalArgumentException.class, () -> bitFieldSet.all(1L << 33));
    Assert.assertEquals(
        "mask 8589934592(0b1000000000000000000000000000000000) value contains elements outside the universe 0b1000000000000000000000000000000000",
        illegalArgumentException.getMessage());
  }

  @Test
  public void test_toString() {
    assertEquals("IntSet: { }", BitFieldSet.noneOf(IntSet.class).toString());
    assertEquals("IntSet: { VALUE_0 }", BitFieldSet.of(IntSet.VALUE_0).toString());
    assertEquals(
        "IntSet: { VALUE_0, VALUE_2 }", BitFieldSet.of(IntSet.VALUE_0, IntSet.VALUE_2).toString());
    assertEquals(
        "IntSet: { VALUE_0, VALUE_31 }",
        BitFieldSet.of(IntSet.VALUE_0, IntSet.VALUE_31).toString());
  }
}
