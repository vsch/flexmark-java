package com.vladsch.flexmark.util.mappers;

import java.util.Locale;

public class LowerCaseMapper implements CharMapper {
    public static final LowerCaseMapper INSTANCE = new LowerCaseMapper();

    Locale locale = Locale.ROOT;

    public LowerCaseMapper() {
    }

    public LowerCaseMapper(Locale locale) {
        this.locale = locale;
    }

    @Override
    public char map(char c) {
        return c == '\0' ? '\uFFFD' : Character.toLowerCase(c);
    }

}
