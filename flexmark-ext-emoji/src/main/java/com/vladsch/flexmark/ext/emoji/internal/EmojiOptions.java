package com.vladsch.flexmark.ext.emoji.internal;

import com.vladsch.flexmark.ext.emoji.EmojiExtension;
import com.vladsch.flexmark.ext.emoji.EmojiImageType;
import com.vladsch.flexmark.ext.emoji.EmojiShortcutType;
import com.vladsch.flexmark.util.data.DataHolder;

public class EmojiOptions {
    final public String rootImagePath;
    final public EmojiShortcutType useShortcutType;
    final public EmojiImageType useImageType;
    final public String attrImageSize;
    final public String attrAlign;
    final public String attrImageClass;
    final public boolean useUnicodeFileNames;

    public EmojiOptions(DataHolder options) {
        this.useShortcutType = EmojiExtension.USE_SHORTCUT_TYPE.get(options);
        this.attrAlign = EmojiExtension.ATTR_ALIGN.get(options);
        this.attrImageSize = EmojiExtension.ATTR_IMAGE_SIZE.get(options);
        this.rootImagePath = EmojiExtension.ROOT_IMAGE_PATH.get(options);
        this.useImageType = EmojiExtension.USE_IMAGE_TYPE.get(options);
        this.attrImageClass = EmojiExtension.ATTR_IMAGE_CLASS.get(options);
        this.useUnicodeFileNames = EmojiExtension.USE_UNICODE_FILE_NAMES.get(options);
    }
}
