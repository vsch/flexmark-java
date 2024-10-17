package com.vladsch.flexmark.util.ast;

import com.vladsch.flexmark.util.misc.BitField;

enum Flags implements BitField {
  LINK_TEXT_TYPE(3),
  NODE_TEXT, // not unescaped and not percent decoded
  FOR_HEADING_ID, // text for heading ID
  NO_TRIM_REF_TEXT_START, // don't trim ref text start
  NO_TRIM_REF_TEXT_END, // don't trim ref text end
  ADD_SPACES_BETWEEN_NODES; // when appending text from different nodes, ensure there is at least
  // one space

  final int bits;

  Flags() {
    this(1);
  }

  Flags(int bits) {
    this.bits = bits;
  }

  @Override
  public int getBits() {
    return bits;
  }
}
