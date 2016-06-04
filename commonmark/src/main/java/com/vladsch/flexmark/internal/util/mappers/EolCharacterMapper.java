package com.vladsch.flexmark.internal.util.mappers;

public class EolCharacterMapper implements CharMapper {
    final public static EolCharacterMapper INSTANCE = new EolCharacterMapper();

    @Override
    public char map(char c) {
        return '\n';
    }

}
