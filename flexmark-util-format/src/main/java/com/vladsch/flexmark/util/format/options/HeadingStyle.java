package com.vladsch.flexmark.util.format.options;

// IMPORTANT: implement this format option
public enum HeadingStyle {
    AS_IS,
    ATX_PREFERRED,
    SETEXT_PREFERRED,
    ;

    public boolean isNoChange() {
        return this == AS_IS;
    }

    public boolean isNoChange(boolean isSetext, int level) {
        return this == AS_IS || this == SETEXT_PREFERRED && (isSetext || level > 2) || this == ATX_PREFERRED && !isSetext;
    }

    public boolean isAtx() {
        return this == ATX_PREFERRED;
    }

    public boolean isSetext() {
        return this == SETEXT_PREFERRED;
    }
}
