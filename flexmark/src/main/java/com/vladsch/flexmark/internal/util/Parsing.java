package com.vladsch.flexmark.internal.util;

import com.vladsch.flexmark.internal.util.options.DataHolder;
import com.vladsch.flexmark.parser.Parser;

import java.util.regex.Pattern;

import static com.vladsch.flexmark.parser.Parser.LISTS_ORDERED_ITEM_DOT_ONLY;

public class Parsing {
    final public String ADDITIONAL_CHARS;
    final public String EXCLUDED_0_TO_SPACE;

    final public String ESCAPED_CHAR;
    final public Pattern LINK_LABEL;
    final public Pattern LINK_DESTINATION_BRACES;
    final public String LINK_TITLE_STRING;
    final public Pattern LINK_TITLE;
    final public String REG_CHAR;
    final public String IN_PARENS_NOSP;
    final public Pattern LINK_DESTINATION;
    final public String HTMLCOMMENT;
    final public String PROCESSINGINSTRUCTION;
    final public String DECLARATION;
    final public String CDATA;
    final public String ENTITY;

    final public Pattern ENTITY_HERE;
    final public String ASCII_PUNCTUATION;
    final public Pattern PUNCTUATION;

    final public Pattern HTML_COMMENT;
    final public Pattern ESCAPABLE;
    final public Pattern TICKS;
    final public Pattern TICKS_HERE;
    final public Pattern EMAIL_AUTOLINK;
    final public Pattern AUTOLINK;
    final public Pattern SPNL;
    final public Pattern UNICODE_WHITESPACE_CHAR;
    final public Pattern WHITESPACE;
    final public Pattern FINAL_SPACE;
    final public Pattern LINE_END;
    final public String TAGNAME;
    final public String ATTRIBUTENAME;
    final public String UNQUOTEDVALUE;
    final public String SINGLEQUOTEDVALUE;
    final public String DOUBLEQUOTEDVALUE;
    final public String ATTRIBUTEVALUE;
    final public String ATTRIBUTEVALUESPEC;
    final public String ATTRIBUTE;

    final public String OPENTAG;
    final public String CLOSETAG;
    final public String HTMLTAG;
    final public Pattern HTML_TAG;

    final public Pattern LIST_ITEM_MARKER;

    final public int CODE_BLOCK_INDENT;
    final public boolean intellijDummyIdentifier;

    public Parsing(DataHolder options) {
        this.intellijDummyIdentifier = Parser.INTELLIJ_DUMMY_IDENTIFIER.getFrom(options);

        this.ADDITIONAL_CHARS = ADDITIONAL_CHARS();
        this.EXCLUDED_0_TO_SPACE = EXCLUDED_0_TO_SPACE();

        this.ESCAPED_CHAR = "\\\\" + Escaping.ESCAPABLE;
        this.LINK_LABEL = Pattern
                .compile("^\\[(?:[^\\\\\\[\\]]|" + ESCAPED_CHAR + "|\\\\){0,999}\\]");
        this.LINK_DESTINATION_BRACES = Pattern.compile(
                "^(?:[<](?:[^<> \\t\\n\\\\\\x00]" + '|' + ESCAPED_CHAR + '|' + "\\\\)*[>])");
        this.LINK_TITLE_STRING = "(?:\"(" + ESCAPED_CHAR + "|[^\"\\x00])*\"" +
                '|' +
                "'(" + ESCAPED_CHAR + "|[^'\\x00])*'" +
                '|' +
                "\\((" + ESCAPED_CHAR + "|[^)\\x00])*\\))";
        this.LINK_TITLE = Pattern.compile("^" + LINK_TITLE_STRING);
        this.REG_CHAR = "[^\\\\()" + EXCLUDED_0_TO_SPACE + "]";
        this.IN_PARENS_NOSP = "\\((" + REG_CHAR + '|' + ESCAPED_CHAR + ")*\\)";
        this.LINK_DESTINATION = Pattern.compile(
                "^(?:" + REG_CHAR + "+|" + ESCAPED_CHAR + "|\\\\|" + IN_PARENS_NOSP + ")*");
        this.HTMLCOMMENT = "<!---->|<!--(?:-?[^>-])(?:-?[^-])*-->";
        this.PROCESSINGINSTRUCTION = "[<][?].*?[?][>]";
        this.DECLARATION = "<![A-Z" + ADDITIONAL_CHARS + "]+\\s+[^>]*>";
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

        if (options.get(LISTS_ORDERED_ITEM_DOT_ONLY)) {
            this.LIST_ITEM_MARKER = Pattern.compile("^([*+-])(?= |\t|$)|^(\\d{1,9})([.])(?= |\t|$)");
        } else {
            this.LIST_ITEM_MARKER = Pattern.compile("^([*+-])(?= |\t|$)|^(\\d{1,9})([.)])(?= |\t|$)");
        }

        this.CODE_BLOCK_INDENT = 4;
    }

    public String EXCLUDED_0_TO_SPACE() {
        return intellijDummyIdentifier ? "\u0000-\u001e\u0020" : "\u0000-\u0020";
    }

    public String ADDITIONAL_CHARS() {
        return intellijDummyIdentifier ? "\u001f" : "";
    }

    public String ADDITIONAL_CHARS_SET(String quantifier) {
        return intellijDummyIdentifier ? "[\u001f]" + quantifier : "";
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
