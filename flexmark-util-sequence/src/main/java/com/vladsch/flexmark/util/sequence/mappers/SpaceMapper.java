package com.vladsch.flexmark.util.sequence.mappers;

import com.vladsch.flexmark.util.sequence.SequenceUtils;

public class SpaceMapper {
  public static final CharMapper toNonBreakSpace = new ToNonBreakSpace();
  public static final CharMapper fromNonBreakSpace = new FromNonBreakSpace();

  private static class FromNonBreakSpace implements CharMapper {
    FromNonBreakSpace() {}

    @Override
    public char map(char c) {
      return c == SequenceUtils.NBSP ? SequenceUtils.SPC : c;
    }
  }

  private static class ToNonBreakSpace implements CharMapper {
    ToNonBreakSpace() {}

    @Override
    public char map(char c) {
      return c == SequenceUtils.SPC ? SequenceUtils.NBSP : c;
    }
  }
}
