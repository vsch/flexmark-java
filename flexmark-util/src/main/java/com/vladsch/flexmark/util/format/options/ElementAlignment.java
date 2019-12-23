package com.vladsch.flexmark.util.format.options;

public enum ElementAlignment {
    NONE,
    LEFT_ALIGN,
    RIGHT_ALIGN,
    ;

    public boolean isNoChange() {
        return this == NONE;
    }

    public boolean isRight() {
        return this == RIGHT_ALIGN;
    }

    public boolean isLeft() {
        return this == LEFT_ALIGN;
    }
}
