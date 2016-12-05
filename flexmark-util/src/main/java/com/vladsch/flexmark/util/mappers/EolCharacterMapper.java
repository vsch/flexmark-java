package com.vladsch.flexmark.util.mappers;

public class EolCharacterMapper implements CharMapper {
    public static final EolCharacterMapper INSTANCE = new EolCharacterMapper();

    @Override
    public char map(char c) {
        return '\n';
    }

}
