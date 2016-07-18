package com.vladsch.flexmark.internal.util;

import java.util.regex.Pattern;

public class Parsing {

    public static final String ESCAPED_CHAR = "\\\\" + Escaping.ESCAPABLE;
    public static final Pattern LINK_LABEL = Pattern
            .compile("^\\[(?:[^\\\\\\[\\]]|" + ESCAPED_CHAR + "|\\\\){0,1000}\\]");
    public static final Pattern LINK_DESTINATION_BRACES = Pattern.compile(
                    "^(?:[<](?:[^<> \\t\\n\\\\\\x00]" + '|' + ESCAPED_CHAR + '|' + "\\\\)*[>])");
    public static final Pattern LINK_TITLE = Pattern.compile(
                            "^(?:\"(" + ESCAPED_CHAR + "|[^\"\\x00])*\"" +
                                    '|' +
                                    "'(" + ESCAPED_CHAR + "|[^'\\x00])*'" +
                                    '|' +
                                    "\\((" + ESCAPED_CHAR + "|[^)\\x00])*\\))");
    public static final String REG_CHAR = "[^\\\\()\\x00-\\x20]";
    public static final String IN_PARENS_NOSP = "\\((" + REG_CHAR + '|' + ESCAPED_CHAR + ")*\\)";
    public static final Pattern LINK_DESTINATION = Pattern.compile(
                                            "^(?:" + REG_CHAR + "+|" + ESCAPED_CHAR + "|\\\\|" + IN_PARENS_NOSP + ")*");
    public static final String HTMLCOMMENT = "<!---->|<!--(?:-?[^>-])(?:-?[^-])*-->";
    public static final String PROCESSINGINSTRUCTION = "[<][?].*?[?][>]";
    public static final String DECLARATION = "<![A-Z]+" + "\\s+[^>]*>";
    public static final String CDATA = "<!\\[CDATA\\[[\\s\\S]*?\\]\\]>";
    public static final String ENTITY = "&(?:#x[a-f0-9]{1,8}|#[0-9]{1,8}|[a-z][a-z0-9]{1,31});";
    
    public static final Pattern ENTITY_HERE = Pattern.compile('^' + ENTITY, Pattern.CASE_INSENSITIVE);
    public static final String ASCII_PUNCTUATION = "'!\"#\\$%&\\(\\)\\*\\+,\\-\\./:;<=>\\?@\\[\\\\\\]\\^_`\\{\\|\\}~";
    public static final Pattern PUNCTUATION = Pattern.compile(
                                                    "^[" + ASCII_PUNCTUATION + "\\p{Pc}\\p{Pd}\\p{Pe}\\p{Pf}\\p{Pi}\\p{Po}\\p{Ps}]");
    
    public static final Pattern HTML_COMMENT = Pattern.compile(HTMLCOMMENT);
    public static final Pattern ESCAPABLE = Pattern.compile('^' + Escaping.ESCAPABLE);
    public static final Pattern TICKS = Pattern.compile("`+");
    public static final Pattern TICKS_HERE = Pattern.compile("^`+");
    public static final Pattern EMAIL_AUTOLINK = Pattern.compile(
                                                            "^<([a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*)>");
    public static final Pattern AUTOLINK = Pattern.compile(
                                                                    "^<[a-zA-Z][a-zA-Z0-9.+-]{1,31}:[^<>\u0000-\u0020]*>");
    public static final Pattern SPNL = Pattern.compile("^ *(?:\n *)?");
    public static final Pattern UNICODE_WHITESPACE_CHAR = Pattern.compile("^[\\p{Zs}\t\r\n\f]");
    public static final Pattern WHITESPACE = Pattern.compile("\\s+");
    public static final Pattern FINAL_SPACE = Pattern.compile(" *$");
    public static final Pattern LINE_END = Pattern.compile("^ *(?:\n|$)");
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
    public static final String HTMLTAG = "(?:" + OPENTAG + "|" + CLOSETAG + "|" + HTMLCOMMENT
            + "|" + PROCESSINGINSTRUCTION + "|" + DECLARATION + "|" + CDATA + ")";
    public static final Pattern HTML_TAG = Pattern.compile('^' + HTMLTAG, Pattern.CASE_INSENSITIVE);

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
