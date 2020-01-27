/**
 * Shortcuts and images by http://www.emoji-cheat-sheet.com/
 * from https://github.com/WebpageFX/emoji-cheat-sheet.com
 * <p>
 * Updated from https://api.github.com/emojis
 */
package com.vladsch.flexmark.ext.emoji.internal;

import com.vladsch.flexmark.ext.emoji.internal.EmojiReference.Emoji;

import java.io.File;
import java.util.HashMap;

public class EmojiShortcuts {
    final public static String gitHubUrlPrefix = EmojiReference.githubUrl;

    final private static HashMap<String, Emoji> emojiShortcuts = new HashMap<>();
    final private static HashMap<String, Emoji> emojiURIs = new HashMap<>();
    final private static HashMap<Emoji, String> emojiUnicodeChars = new HashMap<>();

    synchronized public static String getUnicodeChars(Emoji emoji) {
        if (emoji == null || emoji.unicodeChars == null) {
            return null;
        }

        String value = emojiUnicodeChars.get(emoji);
        if (value == null) {
            String[] unicodePoints = emoji.unicodeChars.replace("U+", "").split(" ");
            StringBuilder sb = new StringBuilder(16);
            for (String unicodePoint : unicodePoints) {
                sb.appendCodePoint(Integer.parseInt(unicodePoint, 16));
            }
            value = sb.toString();
            emojiUnicodeChars.put(emoji, value);
        }
        return value;
    }

    public static String extractFileName(String emojiURI) {
        String fileName = new File(emojiURI).getName();
        int pos = fileName.indexOf(".png");
        if (pos >= 0) {
            fileName = fileName.substring(0, pos);
        }
        return fileName;
    }

    public static HashMap<String, Emoji> getEmojiShortcuts() {
        updateEmojiShortcuts();
        return emojiShortcuts;
    }

    public static HashMap<String, Emoji> getEmojiURIs() {
        updateEmojiShortcuts();
        return emojiURIs;
    }

    public static Emoji getEmojiFromShortcut(String shortcut) {
        updateEmojiShortcuts();
        return emojiShortcuts.get(shortcut);
    }

    public static Emoji getEmojiFromURI(String imageURI) {
        updateEmojiURIs();
        return emojiURIs.get(extractFileName(imageURI));
    }

    synchronized private static void updateEmojiShortcuts() {
        if (emojiShortcuts.isEmpty()) {
            for (Emoji emoji : EmojiReference.getEmojiList()) {
                if (emoji.shortcut != null) {
                    emojiShortcuts.put(emoji.shortcut, emoji);
                }
            }
        }
    }

    synchronized private static void updateEmojiURIs() {
        if (emojiURIs.isEmpty()) {
            // create it
            for (Emoji emoji : EmojiReference.getEmojiList()) {
                if (emoji.emojiCheatSheetFile != null) {
                    emojiURIs.put(extractFileName(emoji.emojiCheatSheetFile), emoji);
                }
                if (emoji.githubFile != null) {
                    emojiURIs.put(extractFileName(emoji.githubFile), emoji);
                }
                if (emoji.unicodeSampleFile != null) {
                    emojiURIs.put(extractFileName(emoji.unicodeSampleFile), emoji);
                }
            }
        }
    }
}

