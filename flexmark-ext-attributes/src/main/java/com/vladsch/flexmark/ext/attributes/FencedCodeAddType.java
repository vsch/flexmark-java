package com.vladsch.flexmark.ext.attributes;

public enum FencedCodeAddType {
    ADD_TO_PRE_CODE(true, true),
    ADD_TO_PRE(true, false),
    ADD_TO_CODE(false, true),
    ;

    public final boolean addToPre;
    public final boolean addToCode;

    FencedCodeAddType(boolean isEmojiCheatSheet, boolean addToCode) {
        this.addToPre = isEmojiCheatSheet;
        this.addToCode = addToCode;
    }
}
