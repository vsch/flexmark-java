package com.vladsch.flexmark.util.mappers;

import com.vladsch.flexmark.util.sequence.IRichSequence;

public class SpaceMapper {
    public static final CharMapper toNonBreakSpace = new ToNonBreakSpace();
    public static final CharMapper fromNonBreakSpace = new FromNonBreakSpace();

    private static class FromNonBreakSpace implements CharMapper {
        FromNonBreakSpace() {}

        @Override
        public char map(char c) {
            return c == IRichSequence.NBSP ? IRichSequence.SPC : c;
        }
    }

    private static class ToNonBreakSpace implements CharMapper {
        ToNonBreakSpace() {}

        @Override
        public char map(char c) {
            return c == IRichSequence.SPC ? IRichSequence.NBSP : c;
        }
    }
}
