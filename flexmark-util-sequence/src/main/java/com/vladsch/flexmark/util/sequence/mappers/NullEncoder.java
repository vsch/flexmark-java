package com.vladsch.flexmark.util.sequence.mappers;

import com.vladsch.flexmark.util.sequence.SequenceUtils;

public class NullEncoder {
    final public static CharMapper encodeNull = new EncodeNull();
    final public static CharMapper decodeNull = new DecodeNull();

    private static class DecodeNull implements CharMapper {
        DecodeNull() {}

        @Override
        public char map(char c) {
            return c == SequenceUtils.ENC_NUL ? SequenceUtils.NUL : c;
        }
    }

    private static class EncodeNull implements CharMapper {
        EncodeNull() {}

        @Override
        public char map(char c) {
            return c == SequenceUtils.NUL ? SequenceUtils.ENC_NUL : c;
        }
    }
}
