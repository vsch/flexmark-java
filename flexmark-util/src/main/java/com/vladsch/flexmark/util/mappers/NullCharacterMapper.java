package com.vladsch.flexmark.util.mappers;

public class NullCharacterMapper implements CharMapper {
    public static final NullCharacterMapper INSTANCE = new NullCharacterMapper();

    @Override
    public char map(char c) {
        return c == '\0' ? '\uFFFD' : c;
    }

}
