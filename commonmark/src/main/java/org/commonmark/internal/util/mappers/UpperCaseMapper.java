package org.commonmark.internal.util.mappers;

import org.commonmark.internal.util.CharMapper;

import java.util.Locale;

public class UpperCaseMapper implements CharMapper {
    final public static UpperCaseMapper INSTANCE = new UpperCaseMapper();

    Locale locale = Locale.ROOT;

    public UpperCaseMapper() {
    }

    public UpperCaseMapper(Locale locale) {
        this.locale = locale;
    }

    @Override
    public char map(char c) {
        return c == '\0' ? '\uFFFD' : Character.toUpperCase(c);
    }
}
