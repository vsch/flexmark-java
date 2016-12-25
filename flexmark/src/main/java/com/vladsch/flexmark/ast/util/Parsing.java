package com.vladsch.flexmark.ast.util;

import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.html.Escaping;
import com.vladsch.flexmark.util.options.DataHolder;

import java.util.regex.Pattern;

import static com.vladsch.flexmark.parser.Parser.LISTS_ITEM_MARKER_SPACE;
import static com.vladsch.flexmark.parser.Parser.LISTS_ORDERED_ITEM_DOT_ONLY;

public class Parsing {
    public final String ADDITIONAL_CHARS;
    public final String EXCLUDED_0_TO_SPACE;

    public final String ESCAPED_CHAR;
    public final Pattern LINK_LABEL;
    public final Pattern LINK_DESTINATION_ANGLES;
    public final String LINK_TITLE_STRING;
    public final Pattern LINK_TITLE;
    public final String REG_CHAR;
    public final String IN_PARENS_NOSP;
    public final String IN_BRACES_W_SP;
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
    public final Pattern SPNL_URL;
    public final Pattern SPNI;
    public final Pattern SP;
    public final Pattern REST_OF_LINE;
    public final Pattern UNICODE_WHITESPACE_CHAR;
    public final Pattern WHITESPACE;
    public final Pattern FINAL_SPACE;
    public final Pattern LINE_END;
    public final String TAGNAME;
    public final String ATTRIBUTENAME;
    public final String UNQUOTEDVALUE;
    public final String SINGLEQUOTEDVALUE;
    public final String DOUBLEQUOTEDVALUE;
    public final String ATTRIBUTEVALUE;
    public final String ATTRIBUTEVALUESPEC;
    public final String ATTRIBUTE;

    public final String OPENTAG;
    public final String CLOSETAG;
    public final String HTMLTAG;
    public final Pattern HTML_TAG;

    public final Pattern LIST_ITEM_MARKER;

    public final int CODE_BLOCK_INDENT;
    public final boolean intellijDummyIdentifier;
    public final String INVALID_LINK_CHARS;

    public Parsing(DataHolder options) {
        this.intellijDummyIdentifier = Parser.INTELLIJ_DUMMY_IDENTIFIER.getFrom(options);

        this.ADDITIONAL_CHARS = ADDITIONAL_CHARS();
        this.EXCLUDED_0_TO_SPACE = EXCLUDED_0_TO_SPACE();

        this.ESCAPED_CHAR = "\\\\" + Escaping.ESCAPABLE;
        this.LINK_LABEL = Pattern
                .compile("^\\[(?:[^\\\\\\[\\]]|" + ESCAPED_CHAR + "|\\\\){0,999}\\]");
        this.LINK_DESTINATION_ANGLES = Pattern.compile(
                "^(?:[<](?:[^<> \\t\\n\\\\\\x00]" + '|' + ESCAPED_CHAR + '|' + "\\\\)*[>])");
        this.LINK_TITLE_STRING = "(?:\"(" + ESCAPED_CHAR + "|[^\"\\x00])*\"" +
                '|' +
                "'(" + ESCAPED_CHAR + "|[^'\\x00])*'" +
                '|' +
                "\\((" + ESCAPED_CHAR + "|[^)\\x00])*\\))";
        this.LINK_TITLE = Pattern.compile("^" + LINK_TITLE_STRING);
        this.REG_CHAR = "[^\\\\()" + EXCLUDED_0_TO_SPACE + "]";
        this.IN_PARENS_NOSP = "\\((" + REG_CHAR + '|' + ESCAPED_CHAR + ")*\\)";
        this.IN_BRACES_W_SP = "\\{\\{(?:[^{}\\\\" + EXCLUDED_0_TO_SPACE + "]| |\t)*\\}\\}";
        this.LINK_DESTINATION = Pattern.compile(
                "^(?:" + (Parser.PARSE_JEKYLL_MACROS_IN_URLS.getFrom(options) ? IN_BRACES_W_SP + "|" : "") + REG_CHAR + "+|" + ESCAPED_CHAR + "|\\\\|" + IN_PARENS_NOSP + ")*");
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
        this.SPNL = Pattern.compile("^(?: |\t)*(?:\n(?: |\t)*)?");
        this.SPNL_URL = Pattern.compile("^(?: |\t)*\n");
        this.SPNI = Pattern.compile("^ {0,3}");
        this.SP = Pattern.compile("^(?: |\t)*");
        this.REST_OF_LINE = Pattern.compile("^.*\n");
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

        if (LISTS_ITEM_MARKER_SPACE.getFrom(options)) {
            if (LISTS_ORDERED_ITEM_DOT_ONLY.getFrom(options)) {
                this.LIST_ITEM_MARKER = Pattern.compile("^([*+-])(?= |\t)|^(\\d{1,9})([.])(?= |\t)");
            } else {
                this.LIST_ITEM_MARKER = Pattern.compile("^([*+-])(?= |\t)|^(\\d{1,9})([.)])(?= |\t)");
            }
        } else {
            if (LISTS_ORDERED_ITEM_DOT_ONLY.getFrom(options)) {
                this.LIST_ITEM_MARKER = Pattern.compile("^([*+-])(?= |\t|$)|^(\\d{1,9})([.])(?= |\t|$)");
            } else {
                this.LIST_ITEM_MARKER = Pattern.compile("^([*+-])(?= |\t|$)|^(\\d{1,9})([.)])(?= |\t|$)");
            }
        }

        // make sure this is consistent with lists settings
        this.CODE_BLOCK_INDENT = Parser.LISTS_ITEM_INDENT.getFrom(options);

        // list of characters not allowed in link URL
        this.INVALID_LINK_CHARS = " \t";
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
