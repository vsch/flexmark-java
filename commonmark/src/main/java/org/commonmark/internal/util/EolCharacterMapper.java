package org.commonmark.internal.util;

public class EolCharacterMapper implements CharMapper {
    final public static EolCharacterMapper INSTANCE = new EolCharacterMapper();

    @Override
    public char map(char c, CharSequence charSequence, int index) {
        return '\n';
    }

}
