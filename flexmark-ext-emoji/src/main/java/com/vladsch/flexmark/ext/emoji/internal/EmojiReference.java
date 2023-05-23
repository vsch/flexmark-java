package com.vladsch.flexmark.ext.emoji.internal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class EmojiReference {
    public static final String EMOJI_REFERENCE_TXT = "/EmojiReference.txt"; // resource path to text data file
    public static final String githubUrl = "https://github.githubassets.com/images/icons/emoji/";
    private static final String[] EMPTY_ARRAY = new String[0];

    /**
     * Browser types and their subdirectory names
     */
    public enum EmojiBrowserType {
        APPL("appl"),
        DCM("dcm"),
        EMOJI_CHEAT_SHEET("emojis"),
        FB("fb"),
        GITHUB("github"),
        GMAIL("gmail"),
        GOOG("goog"),
        JOY("joy"),
        KDDI("kddi"),
        SAMS("sams"),
        SB("sb"),
        TWTR("twtr"),
        WIND("wind"),
        ;

        public final String subdirectory;

        EmojiBrowserType(String subdirectory) {
            this.subdirectory = subdirectory;
        }
    }

    public static class Emoji {
        public final String shortcut;
        public String[] aliasShortcuts;
        public final String category;
        public String subcategory;
        public final String emojiCheatSheetFile; // name part of the file no extension
        public final String githubFile; // name part of the file no extension
        public final String unicodeChars; // unicode char codes space separated list
        public final String unicodeSampleFile; // name part of the file no extension
        public final String unicodeCldr;
        public String[] browserTypes;

        public Emoji(String shortcut
                , String[] aliasShortcuts
                , String category
                , String subcategory
                , String emojiCheatSheetFile
                , String githubFile
                , String unicodeChars
                , String unicodeSampleFile
                , String unicodeCldr
                , String[] browserTypes
        ) {
            this.shortcut = shortcut;
            this.aliasShortcuts = aliasShortcuts;
            this.category = category;
            this.subcategory = subcategory;
            this.emojiCheatSheetFile = emojiCheatSheetFile;
            this.githubFile = githubFile;
            this.unicodeChars = unicodeChars;
            this.unicodeSampleFile = unicodeSampleFile;
            this.unicodeCldr = unicodeCldr;
            this.browserTypes = browserTypes;
        }
    }


    private static ArrayList<Emoji> emojiList = null;

    public static List<Emoji> getEmojiList() {
        if (emojiList == null) {
            // read it in
            emojiList = new ArrayList<>(3000);

            final String emojiReference = EMOJI_REFERENCE_TXT;
            InputStream stream = EmojiReference.class.getResourceAsStream(emojiReference);

            if (stream == null) {
                throw new IllegalStateException("Could not load " + emojiReference + " classpath resource");
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
            String line;
            try {
                // skip first line, it is column names
                line = reader.readLine();
                while ((line = reader.readLine()) != null) {
                    String[] fields = line.split("\t");
                    try {

                        String shortcut = fields[0].charAt(0) == ' ' ? null : fields[0];
                        String category = fields[1].charAt(0) == ' ' ? null : fields[1];
                        String emojiCheatSheetFile = fields[2].charAt(0) == ' ' ? null : fields[2];
                        String githubFile = fields[3].charAt(0) == ' ' ? null : fields[3];
                        String unicodeChars = fields[4].charAt(0) == ' ' ? null : fields[4];
                        String unicodeSampleFile = fields[5].charAt(0) == ' ' ? null : fields[5];
                        String unicodeCldr = fields[6].charAt(0) == ' ' ? null : fields[6];
                        String subcategory = fields[7].charAt(0) == ' ' ? null : fields[7];
                        String[] aliasShortcuts = fields[8].charAt(0) == ' ' ? EMPTY_ARRAY : fields[8].split(",");
                        String[] browserTypes = fields[9].charAt(0) == ' ' ? EMPTY_ARRAY : fields[9].split(",");

                        final Emoji emoji = new Emoji(
                                shortcut
                                , aliasShortcuts
                                , category
                                , subcategory
                                , emojiCheatSheetFile
                                , githubFile
                                , unicodeChars
                                , unicodeSampleFile
                                , unicodeCldr
                                , browserTypes
                        );
                        emojiList.add(emoji);

                        //if (emoji.shortcut != null && emoji.unicodeChars == null) {
                        //    String type = emoji.githubFile == null ? "cheatSheet " : (emoji.emojiCheatSheetFile == null ? "gitHub " : "");
                        //    System.out.printf("Non unicode %sshortcut %s\n",type, emoji.shortcut);
                        //}
                    } catch (ArrayIndexOutOfBoundsException e) {
                        //e.printStackTrace();
                        throw new IllegalStateException("Error processing EmojiReference.txt", e);
                    }
                }
            } catch (IOException e) {
                //e.printStackTrace();
                throw new IllegalStateException("Error processing EmojiReference.txt", e);
            }
        }

        return emojiList;
    }
}
