package com.vladsch.flexmark.ast.util;

import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.format.TableFormatOptions;
import com.vladsch.flexmark.util.html.Escaping;
import com.vladsch.flexmark.util.options.DataHolder;

import java.util.regex.Pattern;

import static com.vladsch.flexmark.parser.Parser.*;

public class Parsing {
    public static final char INTELLIJ_DUMMY_IDENTIFIER_CHAR = TableFormatOptions.INTELLIJ_DUMMY_IDENTIFIER_CHAR;
    public static final String INTELLIJ_DUMMY_IDENTIFIER = TableFormatOptions.INTELLIJ_DUMMY_IDENTIFIER;

    // save options for others to use when only parsing instance is available
    public final DataHolder options;
    
    public final String ADDITIONAL_CHARS;
    public final String EXCLUDED_0_TO_SPACE;

    public final String EOL;
    public final String ESCAPED_CHAR;
    public final Pattern LINK_LABEL;
    public final Pattern LINK_DESTINATION_ANGLES;
    public final String LINK_TITLE_STRING;
    public final Pattern LINK_TITLE;
    public final String REG_CHAR;
    public final String REG_CHAR_SP;
    public final String IN_PARENS_NOSP;
    public final String IN_PARENS_W_SP;
    public final String IN_BRACES_W_SP;
    public final Pattern LINK_DESTINATION;
    public final Pattern LINK_DESTINATION_MATCHED_PARENS;
    public final String HTMLCOMMENT;
    public final String PROCESSINGINSTRUCTION;
    public final String DECLARATION;
    public final String CDATA;
    public final String ENTITY;

    public final Pattern ENTITY_HERE;
    public final String ASCII_PUNCTUATION;
    public final String ASCII_OPEN_PUNCTUATION;
    public final String ASCII_CLOSE_PUNCTUATION;
    public final Pattern PUNCTUATION;
    public final Pattern PUNCTUATION_OPEN;
    public final Pattern PUNCTUATION_CLOSE;
    public final Pattern PUNCTUATION_ONLY;
    public final Pattern PUNCTUATION_OPEN_ONLY;
    public final Pattern PUNCTUATION_CLOSE_ONLY;

    //public final Pattern HTML_COMMENT;
    public final Pattern ESCAPABLE;
    public final Pattern TICKS;
    public final Pattern TICKS_HERE;
    public final Pattern EMAIL_AUTOLINK;
    public final Pattern AUTOLINK;
    public final Pattern WWW_AUTOLINK;
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
    public final boolean htmlForTranslator;
    public final String translationHtmlInlineTagPattern;
    public final String INVALID_LINK_CHARS;
    public final String IN_MATCHED_PARENS_NOSP;
    public final String IN_MATCHED_PARENS_W_SP;
    public final String REG_CHAR_PARENS;
    public final String REG_CHAR_SP_PARENS;

    public Parsing(DataHolder options) {
        this.options = options;
        this.intellijDummyIdentifier = Parser.INTELLIJ_DUMMY_IDENTIFIER.getFrom(options);
        this.htmlForTranslator = Parser.HTML_FOR_TRANSLATOR.getFrom(options);
        this.translationHtmlInlineTagPattern = Parser.TRANSLATION_HTML_INLINE_TAG_PATTERN.getFrom(options);

        this.EOL = "(?:\r\n|\r|\n)";
        this.ADDITIONAL_CHARS = ADDITIONAL_CHARS();
        this.EXCLUDED_0_TO_SPACE = EXCLUDED_0_TO_SPACE();

        this.ESCAPED_CHAR = "\\\\" + Escaping.ESCAPABLE;
        this.LINK_LABEL = Pattern
                .compile("^\\[(?:[^\\\\\\[\\]]|" + ESCAPED_CHAR + "|\\\\){0,999}\\]");
        this.LINK_DESTINATION_ANGLES = Parser.SPACE_IN_LINK_URLS.getFrom(options)
                ? Pattern.compile("^(?:[<](?:[^<> \\t\\n\\\\\\x00]" + '|' + ESCAPED_CHAR + '|' + "\\\\| (?![\"]))*[>])")
                : Pattern.compile("^(?:[<](?:[^<> \\t\\n\\\\\\x00]" + '|' + ESCAPED_CHAR + '|' + "\\\\)*[>])");
        this.LINK_TITLE_STRING = "(?:\"(" + ESCAPED_CHAR + "|[^\"\\x00])*\"" +
                '|' +
                "'(" + ESCAPED_CHAR + "|[^'\\x00])*'" +
                '|' +
                "\\((" + ESCAPED_CHAR + "|[^)\\x00])*\\))";
        this.LINK_TITLE = Pattern.compile("^" + LINK_TITLE_STRING);
        this.REG_CHAR = "[^\\\\()" + EXCLUDED_0_TO_SPACE + "]";
        this.REG_CHAR_PARENS = "[^\\\\" + EXCLUDED_0_TO_SPACE + "]";
        this.REG_CHAR_SP = "[^\\\\()" + EXCLUDED_0_TO_SPACE + "]| (?!\")";
        this.REG_CHAR_SP_PARENS = "[^\\\\" + EXCLUDED_0_TO_SPACE + "]| (?!\")";
        this.IN_PARENS_NOSP = "\\((" + REG_CHAR + '|' + ESCAPED_CHAR + ")*\\)";
        this.IN_PARENS_W_SP = "\\((" + REG_CHAR_SP + '|' + ESCAPED_CHAR + ")*\\)";
        this.IN_MATCHED_PARENS_NOSP = "\\((" + REG_CHAR + '|' + ESCAPED_CHAR + ")*\\)";
        this.IN_MATCHED_PARENS_W_SP = "\\((" + REG_CHAR_SP + '|' + ESCAPED_CHAR + ")*\\)";
        this.IN_BRACES_W_SP = "\\{\\{(?:[^{}\\\\" + EXCLUDED_0_TO_SPACE + "]| |\t)*\\}\\}";
        this.LINK_DESTINATION = Pattern.compile(
                "^(?:" + (Parser.PARSE_JEKYLL_MACROS_IN_URLS.getFrom(options) ? IN_BRACES_W_SP + "|" : "") +
                        (Parser.SPACE_IN_LINK_URLS.getFrom(options) ? "(?:" + REG_CHAR_SP + ")+|" : REG_CHAR + "+|") +
                        ESCAPED_CHAR + "|\\\\|" + (Parser.SPACE_IN_LINK_URLS.getFrom(options) ? IN_PARENS_W_SP : IN_PARENS_NOSP) + ")*");
        this.LINK_DESTINATION_MATCHED_PARENS = Pattern.compile(
                "^(?:" + (Parser.PARSE_JEKYLL_MACROS_IN_URLS.getFrom(options) ? IN_BRACES_W_SP + "|" : "")
                        + (Parser.SPACE_IN_LINK_URLS.getFrom(options) ? "(?:" + REG_CHAR_SP + ")+|" : REG_CHAR + "+|") +
                        ESCAPED_CHAR + "|\\\\|\\(|\\))*");
        this.HTMLCOMMENT = "<!---->|<!--(?:-?[^>-])(?:-?[^-])*-->";
        this.PROCESSINGINSTRUCTION = "[<][?].*?[?][>]";
        this.DECLARATION = "<![A-Z" + ADDITIONAL_CHARS + "]+\\s+[^>]*>";
        this.CDATA = "<!\\[CDATA\\[[\\s\\S]*?\\]\\]>";
        this.ENTITY = "&(?:#x[a-f0-9" + ADDITIONAL_CHARS + "]{1,8}|#[0-9]{1,8}|[a-z" + ADDITIONAL_CHARS + "][a-z0-9" + ADDITIONAL_CHARS + "]{1,31});";

        this.ENTITY_HERE = Pattern.compile('^' + ENTITY, Pattern.CASE_INSENSITIVE);
        this.ASCII_PUNCTUATION = "'!\"#\\$%&\\*\\+,\\-\\./:;=\\?@\\\\\\^_`\\|~";
        this.ASCII_OPEN_PUNCTUATION = "\\(<\\[\\{";
        this.ASCII_CLOSE_PUNCTUATION = "\\)>\\]\\}";
        this.PUNCTUATION = Pattern.compile(
                "^[" + ASCII_PUNCTUATION + ASCII_OPEN_PUNCTUATION + ASCII_CLOSE_PUNCTUATION + "\\p{Pc}\\p{Pd}\\p{Pe}\\p{Pf}\\p{Pi}\\p{Po}\\p{Ps}]");
        this.PUNCTUATION_OPEN = Pattern.compile(
                "^[" + ASCII_PUNCTUATION + ASCII_OPEN_PUNCTUATION + "]|[\\p{Pc}\\p{Pd}\\p{Pe}\\p{Pf}\\p{Pi}\\p{Po}\\p{Ps}]&&[^" + ASCII_CLOSE_PUNCTUATION + "]");
        this.PUNCTUATION_CLOSE = Pattern.compile(
                "^[" + ASCII_PUNCTUATION + ASCII_CLOSE_PUNCTUATION + "]|[\\p{Pc}\\p{Pd}\\p{Pe}\\p{Pf}\\p{Pi}\\p{Po}\\p{Ps}]&&[^" + ASCII_OPEN_PUNCTUATION + "]");
        this.PUNCTUATION_ONLY = Pattern.compile(
                "^[" + ASCII_PUNCTUATION + "\\p{Pc}\\p{Pd}\\p{Pe}\\p{Pf}\\p{Pi}\\p{Po}\\p{Ps}]&&[^" + ASCII_OPEN_PUNCTUATION + ASCII_CLOSE_PUNCTUATION + "]");
        this.PUNCTUATION_OPEN_ONLY = Pattern.compile(
                "^[" + ASCII_OPEN_PUNCTUATION + "]");
        this.PUNCTUATION_CLOSE_ONLY = Pattern.compile(
                "^[" + ASCII_CLOSE_PUNCTUATION + "]");

        //this.HTML_COMMENT = Pattern.compile(HTMLCOMMENT);
        this.ESCAPABLE = Pattern.compile('^' + Escaping.ESCAPABLE);
        this.TICKS = Pattern.compile("`+");
        this.TICKS_HERE = Pattern.compile("^`+");
        this.EMAIL_AUTOLINK = Pattern.compile(
                "^<([a-zA-Z0-9" + ADDITIONAL_CHARS + ".!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9" + ADDITIONAL_CHARS + "](?:[a-zA-Z0-9" + ADDITIONAL_CHARS + "-]{0,61}[a-zA-Z0-9" + ADDITIONAL_CHARS + "])?(?:\\.[a-zA-Z0-9" + ADDITIONAL_CHARS + "](?:[a-zA-Z0-9" + ADDITIONAL_CHARS + "-]{0,61}[a-zA-Z0-9" + ADDITIONAL_CHARS + "])?)*)>");
        this.AUTOLINK = Pattern.compile(
                "^<[a-zA-Z][a-zA-Z0-9" + ADDITIONAL_CHARS + ".+-]{1,31}:[^<>" + EXCLUDED_0_TO_SPACE + "]*>");
        
        this.WWW_AUTOLINK = Pattern.compile(
                "^<(?:w" + ADDITIONAL_CHARS + "?){3,3}\\.[^<>" + EXCLUDED_0_TO_SPACE + "]*>");
        
        this.SPNL = Pattern.compile("^(?:[ \t])*(?:" + EOL + "(?:[ \t])*)?");
        this.SPNL_URL = Pattern.compile("^(?:[ \t])*" + EOL);
        this.SPNI = Pattern.compile("^ {0,3}");
        this.SP = Pattern.compile("^(?:[ \t])*");
        this.REST_OF_LINE = Pattern.compile("^.*" + EOL);
        this.UNICODE_WHITESPACE_CHAR = Pattern.compile("^[\\p{Zs}\t\r\n\f]");
        this.WHITESPACE = Pattern.compile("\\s+");
        this.FINAL_SPACE = Pattern.compile(" *$");
        this.LINE_END = Pattern.compile("^[ \t]*(?:" + EOL + "|$)");
        this.TAGNAME = "[A-Za-z" + ADDITIONAL_CHARS + "][A-Za-z0-9" + ADDITIONAL_CHARS + "-]*";
        this.ATTRIBUTENAME = "[a-zA-Z" + ADDITIONAL_CHARS + "_:][a-zA-Z0-9" + ADDITIONAL_CHARS + ":._-]*";
        this.UNQUOTEDVALUE = "[^\"'=<>{}`" + EXCLUDED_0_TO_SPACE + "]+";
        this.SINGLEQUOTEDVALUE = "'[^']*'";
        this.DOUBLEQUOTEDVALUE = "\"[^\"]*\"";
        this.ATTRIBUTEVALUE = "(?:" + UNQUOTEDVALUE + "|" + SINGLEQUOTEDVALUE
                + "|" + DOUBLEQUOTEDVALUE + ")";
        this.ATTRIBUTEVALUESPEC = "(?:" + "\\s*=" + "\\s*" + ATTRIBUTEVALUE + ")";
        this.ATTRIBUTE = "(?:" + "\\s+" + ATTRIBUTENAME + ATTRIBUTEVALUESPEC + "?)";

        this.OPENTAG = "<" + TAGNAME + ATTRIBUTE + "*" + "\\s*/?>";
        this.CLOSETAG = "</" + TAGNAME + "\\s*[>]";
        this.HTMLTAG = "(?:" + OPENTAG + "|" + CLOSETAG + "|" + HTMLCOMMENT
                + "|" + PROCESSINGINSTRUCTION + "|" + DECLARATION + "|" + CDATA +
                (htmlForTranslator ? "|<(?:" + translationHtmlInlineTagPattern + ")>|</(?:" + translationHtmlInlineTagPattern + ")>" : "") + ")";
        this.HTML_TAG = Pattern.compile('^' + HTMLTAG, Pattern.CASE_INSENSITIVE);

        final String itemPrefixChars = LISTS_ITEM_PREFIX_CHARS.getFrom(options);
        if (LISTS_ITEM_MARKER_SPACE.getFrom(options)) {
            if (LISTS_ORDERED_ITEM_DOT_ONLY.getFrom(options)) {
                this.LIST_ITEM_MARKER = Pattern.compile("^([\\Q" + itemPrefixChars + "\\E])(?=[ \t])|^(\\d{1,9})([.])(?=[ \t])");
            } else {
                this.LIST_ITEM_MARKER = Pattern.compile("^([\\Q" + itemPrefixChars + "\\E])(?=[ \t])|^(\\d{1,9})([.)])(?=[ \t])");
            }
        } else {
            if (LISTS_ORDERED_ITEM_DOT_ONLY.getFrom(options)) {
                this.LIST_ITEM_MARKER = Pattern.compile("^([\\Q" + itemPrefixChars + "\\E])(?= |\t|$)|^(\\d{1,9})([.])(?= |\t|$)");
            } else {
                this.LIST_ITEM_MARKER = Pattern.compile("^([\\Q" + itemPrefixChars + "\\E])(?= |\t|$)|^(\\d{1,9})([.)])(?= |\t|$)");
            }
        }

        // make sure this is consistent with lists settings
        this.CODE_BLOCK_INDENT = Parser.CODE_BLOCK_INDENT.getFrom(options);

        // list of characters not allowed in link URL
        this.INVALID_LINK_CHARS = " \t";
    }

    public String EXCLUDED_0_TO_SPACE() {
        return intellijDummyIdentifier ? "\u0000-\u001e\u0020" : "\u0000-\u0020";
    }

    public String ADDITIONAL_CHARS() {
        return intellijDummyIdentifier ? INTELLIJ_DUMMY_IDENTIFIER : "";
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
