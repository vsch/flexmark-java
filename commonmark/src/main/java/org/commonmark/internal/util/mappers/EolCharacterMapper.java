package org.commonmark.internal.util.mappers;

import org.commonmark.internal.util.CharMapper;

public class EolCharacterMapper implements CharMapper {
    final public static EolCharacterMapper INSTANCE = new EolCharacterMapper();

    @Override
    public char map(char c) {
        return '\n';
    }

}
