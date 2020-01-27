package com.vladsch.flexmark.ext.emoji;

public enum EmojiShortcutType {
    EMOJI_CHEAT_SHEET(true, false),
    GITHUB(false, true),
    ANY_EMOJI_CHEAT_SHEET_PREFERRED(true, true),
    ANY_GITHUB_PREFERRED(true, true),
    ;

    final public boolean isEmojiCheatSheet;
    final public boolean isGitHub;

    EmojiShortcutType(boolean isEmojiCheatSheet, boolean isGitHub) {
        this.isEmojiCheatSheet = isEmojiCheatSheet;
        this.isGitHub = isGitHub;
    }

    public String getPreferred(String emojiCheatSheet, String gitHub) {
        switch (this) {
            case EMOJI_CHEAT_SHEET:
                return emojiCheatSheet;
            case GITHUB:
                return gitHub;
            case ANY_EMOJI_CHEAT_SHEET_PREFERRED:
                return emojiCheatSheet != null ? emojiCheatSheet : gitHub;
            case ANY_GITHUB_PREFERRED:
                return gitHub != null ? gitHub : emojiCheatSheet;
        }
        assert false : "Missing Case Statement";
        return null;
    }
}
