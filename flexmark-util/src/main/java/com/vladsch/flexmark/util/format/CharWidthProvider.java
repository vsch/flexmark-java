package com.vladsch.flexmark.util.format;

import com.vladsch.flexmark.util.sequence.IRichSequenceBase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface CharWidthProvider {
    int spaceWidth();
    int charWidth(char c);
    int charWidth(@NotNull CharSequence s);

    default int getStringWidth(@NotNull CharSequence chars) {
        return getStringWidth(chars, null);
    }

    default int getStringWidth(@NotNull CharSequence chars, @Nullable CharSequence zeroWidth) {
        int iMax = chars.length();
        int width = 0;

        for (int i = 0; i < iMax; i++) {
            char c = chars.charAt(i);
            if (zeroWidth == null || IRichSequenceBase.indexOf(zeroWidth, c) == -1) {
                width += charWidth(c);
            }
        }
        return width;
    }

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
        public int charWidth(@NotNull CharSequence s) {
            return s.length();
        }
    };
}
