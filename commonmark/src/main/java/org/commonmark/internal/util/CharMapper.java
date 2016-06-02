package org.commonmark.internal.util;

public interface CharMapper {
    char map(char c, CharSequence charSequence, int index);
}
