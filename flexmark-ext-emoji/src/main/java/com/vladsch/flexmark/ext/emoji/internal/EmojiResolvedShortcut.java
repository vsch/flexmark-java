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
        EmojiReference.Emoji emoji = EmojiShortcuts.getEmojiFromShortcut(node.getText().toString());
        String emojiText = null;
        boolean isUnicode = false;
        String alt = null;

        if (emoji != null) {
            if (useImageType.isUnicode && emoji.unicodeChars != null) {
                emojiText = EmojiShortcuts.getUnicodeChars(emoji);
                isUnicode = true;
            }

            if (emojiText == null && useImageType.isImage) {
                String gitHubFile = null;
                String cheatSheetFile = null;
                if (useShortcutType.isGitHub && emoji.githubFile != null) {
                    gitHubFile = EmojiShortcuts.gitHubUrlPrefix + emoji.githubFile;
                }

                if (useShortcutType.isEmojiCheatSheet && emoji.emojiCheatSheetFile != null) {
                    cheatSheetFile = rootImagePath + emoji.emojiCheatSheetFile;
                }

                emojiText = useShortcutType.getPreferred(cheatSheetFile, gitHubFile);
            }

            alt = "emoji " + emoji.category + ":" + emoji.shortcut;
        }

        return new EmojiResolvedShortcut(emoji, emojiText, isUnicode, alt);
    }
}
