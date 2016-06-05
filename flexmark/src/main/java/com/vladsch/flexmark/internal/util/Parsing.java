package com.vladsch.flexmark.internal.util;

public class Parsing {

    private static final String TAGNAME = "[A-Za-z][A-Za-z0-9-]*";
    private static final String ATTRIBUTENAME = "[a-zA-Z_:][a-zA-Z0-9:._-]*";
    private static final String UNQUOTEDVALUE = "[^\"'=<>`\\x00-\\x20]+";
    private static final String SINGLEQUOTEDVALUE = "'[^']*'";
    private static final String DOUBLEQUOTEDVALUE = "\"[^\"]*\"";
    private static final String ATTRIBUTEVALUE = "(?:" + UNQUOTEDVALUE + "|" + SINGLEQUOTEDVALUE
            + "|" + DOUBLEQUOTEDVALUE + ")";
    private static final String ATTRIBUTEVALUESPEC = "(?:" + "\\s*=" + "\\s*" + ATTRIBUTEVALUE
            + ")";
    private static final String ATTRIBUTE = "(?:" + "\\s+" + ATTRIBUTENAME + ATTRIBUTEVALUESPEC
            + "?)";

    public static final String OPENTAG = "<" + TAGNAME + ATTRIBUTE + "*" + "\\s*/?>";
    public static final String CLOSETAG = "</" + TAGNAME + "\\s*[>]";

    public static int CODE_BLOCK_INDENT = 4;

    public static int columnsToNextTabStop(int column) {
        // Tab stop is 4
        return 4 - (column % 4);
    }

    public static int findLineBreak(CharSequence s, int startIndex) {
        for (int i = startIndex; i < s.length(); i++) {
            switch (s.charAt(i)) {
                case '\n':
                case '\r':
                    return i;
            }
        }
        return -1;
    }

    public static boolean isBlank(CharSequence s) {
        return findNonSpace(s, 0) == -1;
    }

    public static boolean isLetter(CharSequence s, int index) {
        int codePoint = Character.codePointAt(s, index);
        return Character.isLetter(codePoint);
    }

    public static boolean isSpaceOrTab(CharSequence s, int index) {
        if (index < s.length()) {
            switch (s.charAt(index)) {
                case ' ':
                case '\t':
                    return true;
            }
        }
        return false;
    }

    private static int findNonSpace(CharSequence s, int startIndex) {
        for (int i = startIndex; i < s.length(); i++) {
            switch (s.charAt(i)) {
                case ' ':
                case '\t':
                case '\n':
                case '\u000B':
                case '\f':
                case '\r':
                    break;
                default:
                    return i;
            }
        }
        return -1;
    }
}
