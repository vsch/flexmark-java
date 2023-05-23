package com.vladsch.flexmark.ext.emoji.internal;

import com.vladsch.flexmark.ext.emoji.Emoji;
import com.vladsch.flexmark.ext.emoji.EmojiImageType;
import com.vladsch.flexmark.ext.emoji.EmojiShortcutType;

public class EmojiResolvedShortcut {
    final public EmojiReference.Emoji emoji;
    final public String emojiText;
    final public boolean isUnicode;
    final public String alt;

    public EmojiResolvedShortcut(EmojiReference.Emoji emoji, String emojiText, boolean isUnicode, String alt) {
        this.emoji = emoji;
        this.emojiText = emojiText;
        this.isUnicode = isUnicode;
        this.alt = alt;
    }

    public static EmojiResolvedShortcut getEmojiText(Emoji node, EmojiShortcutType useShortcutType, EmojiImageType useImageType, String rootImagePath) {
        return getEmojiText(node.getText().toString(), useShortcutType, useImageType, rootImagePath);
    }

    public static EmojiResolvedShortcut getEmojiText(Emoji node, EmojiShortcutType useShortcutType, EmojiImageType useImageType, String rootImagePath, boolean useUnicodeFileName) {
        return getEmojiText(node.getText().toString(), useShortcutType, useImageType, rootImagePath, useUnicodeFileName);
    }

    public static EmojiResolvedShortcut getEmojiText(String emojiId, EmojiShortcutType useShortcutType, EmojiImageType useImageType, String rootImagePath) {
        return getEmojiText(emojiId, useShortcutType, useImageType, rootImagePath, false);
    }

    public static EmojiResolvedShortcut getEmojiText(String emojiId, EmojiShortcutType useShortcutType, EmojiImageType useImageType, String rootImagePath, boolean useUnicodeFileName) {
        EmojiReference.Emoji emoji = EmojiShortcuts.getEmojiFromShortcut(emojiId);
        String emojiText = null;
        boolean isUnicode = false;
        String alt = null;

        if (emoji != null) {
            String unicodeText = null;
            String imageText = null;
            if (useImageType.isUnicode && emoji.unicodeChars != null) {
                unicodeText = EmojiShortcuts.getUnicodeChars(emoji);
            }

            if (useImageType.isImage) {
                String gitHubFile = null;
                String cheatSheetFile = null;
                if (useShortcutType.isGitHub && emoji.githubFile != null) {
                    gitHubFile = EmojiShortcuts.gitHubUrlPrefix + emoji.githubFile;
                }

                if (useShortcutType.isEmojiCheatSheet && emoji.emojiCheatSheetFile != null) {
                    if (useUnicodeFileName && emoji.unicodeSampleFile != null) {
                        cheatSheetFile = rootImagePath + emoji.unicodeSampleFile;
                    } else {
                        cheatSheetFile = rootImagePath + emoji.emojiCheatSheetFile;
                    }
                }

                imageText = useShortcutType.getPreferred(cheatSheetFile, gitHubFile);
            }

            if (imageText != null || unicodeText != null) {
                if (unicodeText != null) {
                    emojiText = unicodeText;
                    isUnicode = true;
                } else {
                    emojiText = imageText;
                }

                // CAUTION: this exact string is used by html parser to convert emoji from Apple Mail HTML
                alt = "emoji " + emoji.category + ":" + emoji.shortcut;
            }
        }

        return new EmojiResolvedShortcut(emoji, emojiText, isUnicode, alt);
    }
}
