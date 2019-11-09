package com.vladsch.flexmark.util.format;

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

    default int getStringWidth(CharSequence chars) {
        int iMax = chars.length();
        int width = 0;

        for (int i = 0; i < iMax; i++) {
            width += charWidth(chars.charAt(i));
        }
        return width;
    }
}
