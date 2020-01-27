package com.vladsch.flexmark.ext.emoji;

public enum EmojiImageType {
    IMAGE_ONLY(false, true),
    UNICODE_FALLBACK_TO_IMAGE(true, true),
    UNICODE_ONLY(true, false),
    ;

    final public boolean isUnicode;
    final public boolean isImage;

    EmojiImageType(boolean isUnicode, boolean isImage) {
        this.isUnicode = isUnicode;
        this.isImage = isImage;
    }
}
