package com.vladsch.flexmark.util.mappers;

public interface CharWidthProvider {
    int spaceWidth();
    int charWidth(char c);
    int charWidth(CharSequence s);

    CharWidthProvider NULL = new CharWidthProvider() {
        @Override
        public int spaceWidth() {
            return 1;
        }

        @Override
        public int charWidth(char c) {
            return 1;
        }

        @Override
        public int charWidth(CharSequence s) {
            return s.length();
        }
    };
}
