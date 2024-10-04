package com.vladsch.flexmark.util.misc;

enum BitFields implements BitField {
  FIELD_0,
  FIELD_1(1),
  FIELD_2(2),
  FIELD_3(3),
  FIELD_4(4),
  FIELD_5(5),
  FIELD_6(6),
  FIELD_7(7),
  FIELD_8(8),
  FIELD_9(9),
  FIELD_10(10),
  FIELD_11(4),
  FIELD_12(2),
  FIELD_13(1),
  FIELD_14;

  private final int bits;

  BitFields() {
    this(1);
  }

  BitFields(int bits) {
    this.bits = Math.max(1, bits);
  }

  @Override
  public int getBits() {
    return bits;
  }
}
