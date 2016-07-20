package com.vladsch.flexmark.internal.util;

import com.vladsch.flexmark.internal.util.options.DataHolder;
import com.vladsch.flexmark.parser.Parser;

import java.util.regex.Pattern;

public class Parsing {
    public final String ADDITIONAL_CHARS;
    public final String EXCLUDED_0_TO_SPACE;

    public final String ESCAPED_CHAR;
    public final Pattern LINK_LABEL;
    public final Pattern LINK_DESTINATION_BRACES;
    public final Pattern LINK_TITLE;
    public final String REG_CHAR;
    public final String IN_PARENS_NOSP;
    public final Pattern LINK_DESTINATION;
    public final String HTMLCOMMENT;
    public final String PROCESSINGINSTRUCTION;
    public final String DECLARATION;
    public final String CDATA;
    public final String ENTITY;

    public final Pattern ENTITY_HERE;
    public final String ASCII_PUNCTUATION;
    public final Pattern PUNCTUATION;

    public final Pattern HTML_COMMENT;
    public final Pattern ESCAPABLE;
    public final Pattern TICKS;
    public final Pattern TICKS_HERE;
    public final Pattern EMAIL_AUTOLINK;
    public final Pattern AUTOLINK;
    public final Pattern SPNL;
    public final Pattern UNICODE_WHITESPACE_CHAR;
    public final Pattern WHITESPACE;
    public final Pattern FINAL_SPACE;
    public final Pattern LINE_END;
    private final String TAGNAME;
    private final String ATTRIBUTENAME;
    private final String UNQUOTEDVALUE;
    private final String SINGLEQUOTEDVALUE;
    private final String DOUBLEQUOTEDVALUE;
    private final String ATTRIBUTEVALUE;
    private final String ATTRIBUTEVALUESPEC;
    private final String ATTRIBUTE;

    public final String OPENTAG;
    public final String CLOSETAG;
    public final String HTMLTAG;
    public final Pattern HTML_TAG;

    public int CODE_BLOCK_INDENT;
    public final boolean intellijDummyIdentifier;

    public Parsing(DataHolder options) {
        this.intellijDummyIdentifier = Parser.INTELLIJ_DUMMY_IDENTIFIER.getFrom(options);

        this.ADDITIONAL_CHARS = ADDITIONAL_CHARS();
        this.EXCLUDED_0_TO_SPACE = EXCLUDED_0_TO_SPACE();

        this.ESCAPED_CHAR = "\\\\" + Escaping.ESCAPABLE;
        this.LINK_LABEL = Pattern
                .compile("^\\[(?:[^\\\\\\[\\]]|" + ESCAPED_CHAR + "|\\\\){0,1000}\\]");
        this.LINK_DESTINATION_BRACES = Pattern.compile(
                "^(?:[<](?:[^<> \\t\\n\\\\\\x00]" + '|' + ESCAPED_CHAR + '|' + "\\\\)*[>])");
        this.LINK_TITLE = Pattern.compile(
                "^(?:\"(" + ESCAPED_CHAR + "|[^\"\\x00])*\"" +
                        '|' +
                        "'(" + ESCAPED_CHAR + "|[^'\\x00])*'" +
                        '|' +
                        "\\((" + ESCAPED_CHAR + "|[^)\\x00])*\\))");
        this.REG_CHAR = "[^\\\\()" + EXCLUDED_0_TO_SPACE + "]";
        this.IN_PARENS_NOSP = "\\((" + REG_CHAR + '|' + ESCAPED_CHAR + ")*\\)";
        this.LINK_DESTINATION = Pattern.compile(
                "^(?:" + REG_CHAR + "+|" + ESCAPED_CHAR + "|\\\\|" + IN_PARENS_NOSP + ")*");
        this.HTMLCOMMENT = "<!---->|<!--(?:-?[^>-])(?:-?[^-])*-->";
        this.PROCESSINGINSTRUCTION = "[<][?].*?[?][>]";
        this.DECLARATION = "<![A-Z" + ADDITIONAL_CHARS + "]+" + "\\s+[^>]*>";
        this.CDATA = "<!\\[CDATA\\[[\\s\\S]*?\\]\\]>";
        this.ENTITY = "&(?:#x[a-f0-9" + ADDITIONAL_CHARS + "]{1,8}|#[0-9]{1,8}|[a-z" + ADDITIONAL_CHARS + "][a-z0-9" + ADDITIONAL_CHARS + "]{1,31});";

        this.ENTITY_HERE = Pattern.compile('^' + ENTITY, Pattern.CASE_INSENSITIVE);
        this.ASCII_PUNCTUATION = "'!\"#\\$%&\\(\\)\\*\\+,\\-\\./:;<=>\\?@\\[\\\\\\]\\^_`\\{\\|\\}~";
        this.PUNCTUATION = Pattern.compile(
                "^[" + ASCII_PUNCTUATION + "\\p{Pc}\\p{Pd}\\p{Pe}\\p{Pf}\\p{Pi}\\p{Po}\\p{Ps}]");

        this.HTML_COMMENT = Pattern.compile(HTMLCOMMENT);
        this.ESCAPABLE = Pattern.compile('^' + Escaping.ESCAPABLE);
        this.TICKS = Pattern.compile("`+");
        this.TICKS_HERE = Pattern.compile("^`+");
        this.EMAIL_AUTOLINK = Pattern.compile(
                "^<([a-zA-Z0-9" + ADDITIONAL_CHARS + ".!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9" + ADDITIONAL_CHARS + "](?:[a-zA-Z0-9" + ADDITIONAL_CHARS + "-]{0,61}[a-zA-Z0-9" + ADDITIONAL_CHARS + "])?(?:\\.[a-zA-Z0-9" + ADDITIONAL_CHARS + "](?:[a-zA-Z0-9" + ADDITIONAL_CHARS + "-]{0,61}[a-zA-Z0-9" + ADDITIONAL_CHARS + "])?)*)>");
        this.AUTOLINK = Pattern.compile(
                "^<[a-zA-Z][a-zA-Z0-9" + ADDITIONAL_CHARS + ".+-]{1,31}:[^<>" + EXCLUDED_0_TO_SPACE + "]*>");
        this.SPNL = Pattern.compile("^ *(?:\n *)?");
        this.UNICODE_WHITESPACE_CHAR = Pattern.compile("^[\\p{Zs}\t\r\n\f]");
        this.WHITESPACE = Pattern.compile("\\s+");
        this.FINAL_SPACE = Pattern.compile(" *$");
        this.LINE_END = Pattern.compile("^ *(?:\n|$)");
        this.TAGNAME = "[A-Za-z" + ADDITIONAL_CHARS + "][A-Za-z0-9" + ADDITIONAL_CHARS + "-]*";
        this.ATTRIBUTENAME = "[a-zA-Z" + ADDITIONAL_CHARS + "_:][a-zA-Z0-9" + ADDITIONAL_CHARS + ":._-]*";
        this.UNQUOTEDVALUE = "[^\"'=<>`" + EXCLUDED_0_TO_SPACE + "]+";
        this.SINGLEQUOTEDVALUE = "'[^']*'";
        this.DOUBLEQUOTEDVALUE = "\"[^\"]*\"";
        this.ATTRIBUTEVALUE = "(?:" + UNQUOTEDVALUE + "|" + SINGLEQUOTEDVALUE
                + "|" + DOUBLEQUOTEDVALUE + ")";
        this.ATTRIBUTEVALUESPEC = "(?:" + "\\s*=" + "\\s*" + ATTRIBUTEVALUE
                + ")";
        this.ATTRIBUTE = "(?:" + "\\s+" + ATTRIBUTENAME + ATTRIBUTEVALUESPEC
                + "?)";

        this.OPENTAG = "<" + TAGNAME + ATTRIBUTE + "*" + "\\s*/?>";
        this.CLOSETAG = "</" + TAGNAME + "\\s*[>]";
        this.HTMLTAG = "(?:" + OPENTAG + "|" + CLOSETAG + "|" + HTMLCOMMENT
                + "|" + PROCESSINGINSTRUCTION + "|" + DECLARATION + "|" + CDATA + ")";
        this.HTML_TAG = Pattern.compile('^' + HTMLTAG, Pattern.CASE_INSENSITIVE);

        this.CODE_BLOCK_INDENT = 4;
    }

    public String EXCLUDED_0_TO_SPACE() {
        return intellijDummyIdentifier ? "\u0000-\u001e\u0020":"\u0000-\u0020";
    }

    public String ADDITIONAL_CHARS() {
        return intellijDummyIdentifier ? "\u001f" : "";
    }

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
