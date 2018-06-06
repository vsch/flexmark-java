package com.vladsch.flexmark.ext.emoji.internal;

import com.vladsch.flexmark.ext.emoji.Emoji;
import com.vladsch.flexmark.ext.emoji.EmojiImageType;
import com.vladsch.flexmark.ext.emoji.EmojiShortcutType;

public class EmojiResolvedShortcut {
    public final EmojiReference.Emoji emoji;
    public final String emojiText;
    public final boolean isUnicode;
    public final String alt;

    public EmojiResolvedShortcut(final EmojiReference.Emoji emoji, final String emojiText, final boolean isUnicode, final String alt) {
        this.emoji = emoji;
        this.emojiText = emojiText;
        this.isUnicode = isUnicode;
        this.alt = alt;
    }

    public static EmojiResolvedShortcut getEmojiText(Emoji node, EmojiShortcutType useShortcutType, EmojiImageType useImageType, String rootImagePath) {
        return getEmojiText(node.getText().toString(), useShortcutType, useImageType, rootImagePath);
    }

    public static EmojiResolvedShortcut getEmojiText(String emojiId, EmojiShortcutType useShortcutType, EmojiImageType useImageType, String rootImagePath) {
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
                    cheatSheetFile = rootImagePath + emoji.emojiCheatSheetFile;
                }

                imageText = useShortcutType.getPreferred(cheatSheetFile, gitHubFile);
            }

            if (imageText != null) {
                if (unicodeText != null) {
                    emojiText = unicodeText;
                    isUnicode = true;
                } else {
                    emojiText = imageText;
                }

                // IMPORTANT: this exact string is used by html parser to convert emoji from Apple Mail HTML
                alt = "emoji " + emoji.category + ":" + emoji.shortcut;
            }
        }

        return new EmojiResolvedShortcut(emoji, emojiText, isUnicode, alt);
    }
}
