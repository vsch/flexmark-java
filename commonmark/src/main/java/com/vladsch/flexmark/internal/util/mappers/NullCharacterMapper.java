package com.vladsch.flexmark.internal.util.mappers;

public class NullCharacterMapper implements CharMapper {
    final public static NullCharacterMapper INSTANCE = new NullCharacterMapper();

    @Override
    public char map(char c) {
        return c == '\0' ? '\uFFFD' : c;
    }

}
