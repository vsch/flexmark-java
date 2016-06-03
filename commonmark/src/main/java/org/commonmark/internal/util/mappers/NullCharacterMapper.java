package org.commonmark.internal.util.mappers;

import org.commonmark.internal.util.CharMapper;

public class NullCharacterMapper implements CharMapper {
    final public static NullCharacterMapper INSTANCE = new NullCharacterMapper();

    @Override
    public char map(char c) {
        return c == '\0' ? '\uFFFD' : c;
    }

}
