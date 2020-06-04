package com.vladsch.flexmark.ast.util;

import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.format.TableFormatOptions;
import com.vladsch.flexmark.util.misc.CharPredicate;
import com.vladsch.flexmark.util.sequence.Escaping;
import com.vladsch.flexmark.util.sequence.SequenceUtils;

import java.util.regex.Pattern;

import static com.vladsch.flexmark.parser.Parser.*;

public class Parsing {
    final public static char INTELLIJ_DUMMY_IDENTIFIER_CHAR = TableFormatOptions.INTELLIJ_DUMMY_IDENTIFIER_CHAR;
    final public static String INTELLIJ_DUMMY_IDENTIFIER = TableFormatOptions.INTELLIJ_DUMMY_IDENTIFIER;

    // save options for others to use when only parsing instance is available
    final public DataHolder options;

    final public String ADDITIONAL_CHARS;
    final public String EXCLUDED_0_TO_SPACE;

    final private static String ST_EOL = "(?:\r\n|\r|\n)";
    final private static String ST_ESCAPED_CHAR = "\\\\" + Escaping.ESCAPABLE;
    final private static Pattern ST_LINK_LABEL = Pattern.compile("^\\[(?:[^\\\\\\[\\]]|" + ST_ESCAPED_CHAR + "|\\\\){0,999}\\]");
    final private static String ST_LINK_TITLE_STRING = "(?:\"(" + ST_ESCAPED_CHAR + "|[^\"\\x00])*\"" +
            '|' +
            "'(" + ST_ESCAPED_CHAR + "|[^'\\x00])*'" +
            '|' +
            "\\((" + ST_ESCAPED_CHAR + "|[^)\\x00])*\\))";
    final private static Pattern ST_LINK_TITLE = Pattern.compile("^" + ST_LINK_TITLE_STRING);
    final public String EOL = ST_EOL;
    final public String ESCAPED_CHAR = ST_ESCAPED_CHAR;
    final public Pattern LINK_LABEL = ST_LINK_LABEL;
    final public Pattern LINK_DESTINATION_ANGLES;
    final public String LINK_TITLE_STRING = ST_LINK_TITLE_STRING;
    final public Pattern LINK_TITLE = ST_LINK_TITLE;
    final public String REG_CHAR;
    final public String REG_CHAR_SP;
    final public String IN_PARENS_NOSP;
    final public String IN_PARENS_W_SP;
    final public String IN_BRACES_W_SP;
    final public Pattern LINK_DESTINATION;
    final public Pattern LINK_DESTINATION_MATCHED_PARENS;
    final public Pattern LINK_DESTINATION_MATCHED_PARENS_NOSP;
    final public String HTMLCOMMENT;
    final public String PROCESSINGINSTRUCTION;
    final public String DECLARATION;
    final public String CDATA;
    final public String ENTITY;

    final private static String ST_ASCII_PUNCTUATION = "'!\"#\\$%&\\*\\+,\\-\\./:;=\\?@\\\\\\^_`\\|~";
    final private static String ST_ASCII_OPEN_PUNCTUATION = "\\(<\\[\\{";
    final private static String ST_ASCII_CLOSE_PUNCTUATION = "\\)>\\]\\}";
    final private static Pattern ST_PUNCTUATION = Pattern.compile(
            "^[" + ST_ASCII_PUNCTUATION + ST_ASCII_OPEN_PUNCTUATION + ST_ASCII_CLOSE_PUNCTUATION + "\\p{Pc}\\p{Pd}\\p{Pe}\\p{Pf}\\p{Pi}\\p{Po}\\p{Ps}]");
    final private static Pattern ST_PUNCTUATION_OPEN = Pattern.compile(
            "^[" + ST_ASCII_PUNCTUATION + ST_ASCII_OPEN_PUNCTUATION + "]|[\\p{Pc}\\p{Pd}\\p{Pe}\\p{Pf}\\p{Pi}\\p{Po}\\p{Ps}]&&[^" + ST_ASCII_CLOSE_PUNCTUATION + "]");
    final private static Pattern ST_PUNCTUATION_CLOSE = Pattern.compile(
            "^[" + ST_ASCII_PUNCTUATION + ST_ASCII_CLOSE_PUNCTUATION + "]|[\\p{Pc}\\p{Pd}\\p{Pe}\\p{Pf}\\p{Pi}\\p{Po}\\p{Ps}]&&[^" + ST_ASCII_OPEN_PUNCTUATION + "]");
    final private static Pattern ST_PUNCTUATION_ONLY = Pattern.compile(
            "^[" + ST_ASCII_PUNCTUATION + "\\p{Pc}\\p{Pd}\\p{Pe}\\p{Pf}\\p{Pi}\\p{Po}\\p{Ps}]&&[^" + ST_ASCII_OPEN_PUNCTUATION + ST_ASCII_CLOSE_PUNCTUATION + "]");
    final private static Pattern ST_PUNCTUATION_OPEN_ONLY = Pattern.compile(
            "^[" + ST_ASCII_OPEN_PUNCTUATION + "]");
    final private static Pattern ST_PUNCTUATION_CLOSE_ONLY = Pattern.compile(
            "^[" + ST_ASCII_CLOSE_PUNCTUATION + "]");

    final private static String ST_EXCLUDED_0_TO_SPACE_IDI = "\u0000-\u001e\u0020";
    final private static String ST_EXCLUDED_0_TO_SPACE_NO_IDI = "\u0000-\u0020";
    final private static String ST_ADDITIONAL_CHARS_IDI = "\u001f";
    final private static String ST_ADDITIONAL_CHARS_NO_IDI = "";
    final private static String ST_ADDITIONAL_CHARS_SET_IDI = "[\u001f]";
    final private static String ST_ADDITIONAL_CHARS_SET_NO_IDI = "";

    final public Pattern ENTITY_HERE;
    final public String ASCII_PUNCTUATION = ST_ASCII_PUNCTUATION;
    final public String ASCII_OPEN_PUNCTUATION = ST_ASCII_OPEN_PUNCTUATION;
    final public String ASCII_CLOSE_PUNCTUATION = ST_ASCII_CLOSE_PUNCTUATION;

    final public Pattern PUNCTUATION = ST_PUNCTUATION;
    final public Pattern PUNCTUATION_OPEN = ST_PUNCTUATION_OPEN;
    final public Pattern PUNCTUATION_CLOSE = ST_PUNCTUATION_CLOSE;
    final public Pattern PUNCTUATION_ONLY = ST_PUNCTUATION_ONLY;
    final public Pattern PUNCTUATION_OPEN_ONLY = ST_PUNCTUATION_OPEN_ONLY;
    final public Pattern PUNCTUATION_CLOSE_ONLY = ST_PUNCTUATION_CLOSE_ONLY;

    final private static Pattern ST_ESCAPABLE = Pattern.compile('^' + Escaping.ESCAPABLE);
    final private static Pattern ST_TICKS = Pattern.compile("`+");
    final private static Pattern ST_TICKS_HERE = Pattern.compile("^`+");
    final private static Pattern ST_SPNL = Pattern.compile("^(?:[ \t])*(?:" + ST_EOL + "(?:[ \t])*)?");
    final private static Pattern ST_SPNL_URL = Pattern.compile("^(?:[ \t])*" + ST_EOL);
    final private static Pattern ST_SPNI = Pattern.compile("^ {0,3}");
    final private static Pattern ST_SP = Pattern.compile("^(?:[ \t])*");
    final private static Pattern ST_REST_OF_LINE = Pattern.compile("^.*" + ST_EOL);
    final private static Pattern ST_UNICODE_WHITESPACE_CHAR = Pattern.compile("^[\\p{Zs}\t\r\n\f]");
    final private static Pattern ST_WHITESPACE = Pattern.compile("\\s+");
    final private static Pattern ST_FINAL_SPACE = Pattern.compile(" *$");
    final private static Pattern ST_LINE_END = Pattern.compile("^[ \t]*(?:" + ST_EOL + "|$)");
    final private static Pattern ST_LINK_DESTINATION_ANGLES_SPC = Pattern.compile("^(?:[<](?:[^<> \\t\\n\\\\\\x00]" + '|' + ST_ESCAPED_CHAR + '|' + "\\\\| (?![\"']))*[>])");
    final private static Pattern ST_LINK_DESTINATION_ANGLES_NO_SPC = Pattern.compile("^(?:[<](?:[^<> \\t\\n\\\\\\x00]" + '|' + ST_ESCAPED_CHAR + '|' + "\\\\)*[>])");

    final public Pattern ESCAPABLE = ST_ESCAPABLE;
    final public Pattern TICKS = ST_TICKS;
    final public Pattern TICKS_HERE = ST_TICKS_HERE;
    final public Pattern EMAIL_AUTOLINK;
    final public Pattern AUTOLINK;
    final public Pattern WWW_AUTOLINK;
    final public Pattern SPNL = ST_SPNL;
    final public Pattern SPNL_URL = ST_SPNL_URL;
    final public Pattern SPNI = ST_SPNI;
    final public Pattern SP = ST_SP;
    final public Pattern REST_OF_LINE = ST_REST_OF_LINE;
    final public Pattern UNICODE_WHITESPACE_CHAR = ST_UNICODE_WHITESPACE_CHAR;
    final public Pattern WHITESPACE = ST_WHITESPACE;
    final public Pattern FINAL_SPACE = ST_FINAL_SPACE;
    final public Pattern LINE_END = ST_LINE_END;
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
    final public boolean htmlForTranslator;
    final public String translationHtmlInlineTagPattern;
    final public String translationAutolinkTagPattern;

    final public String IN_MATCHED_PARENS_NOSP;
    final public String IN_MATCHED_PARENS_W_SP;
    final public String REG_CHAR_PARENS;
    final public String REG_CHAR_SP_PARENS;

    final public boolean spaceInLinkUrl;
    final public boolean parseJekyllMacroInLinkUrl;
    final String itemPrefixChars;

    public Parsing(DataHolder options) {
        this.options = options;
        this.intellijDummyIdentifier = Parser.INTELLIJ_DUMMY_IDENTIFIER.get(options);
        this.htmlForTranslator = Parser.HTML_FOR_TRANSLATOR.get(options);
        this.translationHtmlInlineTagPattern = Parser.TRANSLATION_HTML_INLINE_TAG_PATTERN.get(options);
        this.translationAutolinkTagPattern = Parser.TRANSLATION_AUTOLINK_TAG_PATTERN.get(options);
        this.spaceInLinkUrl = Parser.SPACE_IN_LINK_URLS.get(options);
        this.parseJekyllMacroInLinkUrl = Parser.PARSE_JEKYLL_MACROS_IN_URLS.get(options);
        this.itemPrefixChars = LISTS_ITEM_PREFIX_CHARS.get(options);
        this.CODE_BLOCK_INDENT = Parser.CODE_BLOCK_INDENT.get(options); // make sure this is consistent with lists settings

        this.ADDITIONAL_CHARS = ADDITIONAL_CHARS(intellijDummyIdentifier);
        this.EXCLUDED_0_TO_SPACE = EXCLUDED_0_TO_SPACE(intellijDummyIdentifier);

        this.LINK_DESTINATION_ANGLES = spaceInLinkUrl ? ST_LINK_DESTINATION_ANGLES_SPC : ST_LINK_DESTINATION_ANGLES_NO_SPC;

        this.REG_CHAR = "[^\\\\()" + EXCLUDED_0_TO_SPACE + "]";
        this.REG_CHAR_PARENS = "[^\\\\" + EXCLUDED_0_TO_SPACE + "]";
        this.REG_CHAR_SP = "[^\\\\()" + EXCLUDED_0_TO_SPACE + "]| (?![\"'])";
        this.REG_CHAR_SP_PARENS = "[^\\\\" + EXCLUDED_0_TO_SPACE + "]| (?![\"'])";
        this.IN_PARENS_NOSP = "\\((" + REG_CHAR + '|' + ESCAPED_CHAR + ")*\\)";
        this.IN_PARENS_W_SP = "\\((" + REG_CHAR_SP + '|' + ESCAPED_CHAR + ")*\\)";
        this.IN_MATCHED_PARENS_NOSP = "\\((" + REG_CHAR + '|' + ESCAPED_CHAR + ")*\\)";
        this.IN_MATCHED_PARENS_W_SP = "\\((" + REG_CHAR_SP + '|' + ESCAPED_CHAR + ")*\\)";
        this.IN_BRACES_W_SP = "\\{\\{(?:[^{}\\\\" + EXCLUDED_0_TO_SPACE + "]| |\t)*\\}\\}";
        this.HTMLCOMMENT = "<!---->|<!--(?:-?[^>-])(?:-?[^-])*-->";
        this.PROCESSINGINSTRUCTION = "[<][?].*?[?][>]";
        this.DECLARATION = "<![A-Z" + ADDITIONAL_CHARS + "]+\\s+[^>]*>";
        this.CDATA = "<!\\[CDATA\\[[\\s\\S]*?\\]\\]>";
        this.ENTITY = "&(?:#x[a-f0-9" + ADDITIONAL_CHARS + "]{1,8}|#[0-9]{1,8}|[a-z" + ADDITIONAL_CHARS + "][a-z0-9" + ADDITIONAL_CHARS + "]{1,31});";

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

        // init patterns
        this.LINK_DESTINATION = Pattern.compile(
                "^(?:" + (parseJekyllMacroInLinkUrl ? IN_BRACES_W_SP + "|" : "") +
                        (spaceInLinkUrl ? "(?:" + REG_CHAR_SP + ")|" : REG_CHAR + "|") +
                        ESCAPED_CHAR + "|\\\\|" + (spaceInLinkUrl ? IN_PARENS_W_SP : IN_PARENS_NOSP) + ")*");

        this.LINK_DESTINATION_MATCHED_PARENS = Pattern.compile(
                "^(?:" + (parseJekyllMacroInLinkUrl ? IN_BRACES_W_SP + "|" : "")
                        + (spaceInLinkUrl ? "(?:" + REG_CHAR_SP + ")|" : REG_CHAR + "|") +
                        ESCAPED_CHAR + "|\\\\|\\(|\\))*");

        this.LINK_DESTINATION_MATCHED_PARENS_NOSP = Pattern.compile(
                "^(?:" + (parseJekyllMacroInLinkUrl ? IN_BRACES_W_SP + "|" : "")
                        + (REG_CHAR + "|") +
                        ESCAPED_CHAR + "|\\\\|\\(|\\))*");
        
        this.ENTITY_HERE = Pattern.compile('^' + ENTITY, Pattern.CASE_INSENSITIVE);

        this.EMAIL_AUTOLINK = Pattern.compile(
                "^<(" +
                        "(?:[a-zA-Z0-9" + ADDITIONAL_CHARS + ".!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9" + ADDITIONAL_CHARS + "](?:[a-zA-Z0-9" + ADDITIONAL_CHARS + "-]{0,61}[a-zA-Z0-9" + ADDITIONAL_CHARS + "])?(?:\\.[a-zA-Z0-9" + ADDITIONAL_CHARS + "](?:[a-zA-Z0-9" + ADDITIONAL_CHARS + "-]{0,61}[a-zA-Z0-9" + ADDITIONAL_CHARS + "])?)*)" +
                        (htmlForTranslator ? "|(?:" + translationAutolinkTagPattern + ")" : "") +
                        ")>");

        this.AUTOLINK = Pattern.compile(
                "^<(" +
                        "(?:[a-zA-Z][a-zA-Z0-9" + ADDITIONAL_CHARS + ".+-]{1,31}:[^<>" + EXCLUDED_0_TO_SPACE + "]*)" +
                        (htmlForTranslator ? "|(?:" + translationAutolinkTagPattern + ")" : "") +
                        ")>");

        this.WWW_AUTOLINK = Pattern.compile(
                "^<(" +
                        "(?:w" + ADDITIONAL_CHARS + "?){3,3}\\.[^<>" + EXCLUDED_0_TO_SPACE + "]*" +
                        (htmlForTranslator ? "|(?:" + translationAutolinkTagPattern + ")" : "") +
                        ")>");

        this.HTML_TAG = Pattern.compile('^' + HTMLTAG, Pattern.CASE_INSENSITIVE);

        if (LISTS_ITEM_MARKER_SPACE.get(options)) {
            if (LISTS_ORDERED_ITEM_DOT_ONLY.get(options)) {
                this.LIST_ITEM_MARKER = Pattern.compile("^([\\Q" + itemPrefixChars + "\\E])(?=[ \t])|^(\\d{1,9})([.])(?=[ \t])");
            } else {
                this.LIST_ITEM_MARKER = Pattern.compile("^([\\Q" + itemPrefixChars + "\\E])(?=[ \t])|^(\\d{1,9})([.)])(?=[ \t])");
            }
        } else {
            if (LISTS_ORDERED_ITEM_DOT_ONLY.get(options)) {
                this.LIST_ITEM_MARKER = Pattern.compile("^([\\Q" + itemPrefixChars + "\\E])(?= |\t|$)|^(\\d{1,9})([.])(?= |\t|$)");
            } else {
                this.LIST_ITEM_MARKER = Pattern.compile("^([\\Q" + itemPrefixChars + "\\E])(?= |\t|$)|^(\\d{1,9})([.)])(?= |\t|$)");
            }
        }
    }

    /**
     * @deprecated use static version (0.62.2)
     */
    @Deprecated
    public String EXCLUDED_0_TO_SPACE() {
        // NOTE: hardcoded because of dependency on it being '\u001f'
        return EXCLUDED_0_TO_SPACE(intellijDummyIdentifier);
    }

    /**
     * @deprecated use static version (0.62.2)
     */
    @Deprecated
    public String ADDITIONAL_CHARS() {
        // NOTE: hardcoded because of dependency on it being '\u001f'
        return ADDITIONAL_CHARS(intellijDummyIdentifier);
    }

    /**
     * @deprecated use static version (0.62.2)
     */
    @Deprecated
    public String ADDITIONAL_CHARS_SET(String quantifier) {
        // NOTE: hardcoded because of dependency on it being '\u001f'
        return ADDITIONAL_CHARS_SET(intellijDummyIdentifier, quantifier);
    }

    public static String EXCLUDED_0_TO_SPACE(boolean intellijDummyIdentifier) {
        // NOTE: hardcoded because of dependency on it being '\u001f'
        return intellijDummyIdentifier ? ST_EXCLUDED_0_TO_SPACE_IDI : ST_EXCLUDED_0_TO_SPACE_NO_IDI;
    }

    public static String ADDITIONAL_CHARS(boolean intellijDummyIdentifier) {
        // NOTE: hardcoded because of dependency on it being '\u001f'
        return intellijDummyIdentifier ? ST_ADDITIONAL_CHARS_IDI : ST_ADDITIONAL_CHARS_NO_IDI;
    }

    public static String ADDITIONAL_CHARS_SET(boolean intellijDummyIdentifier, String quantifier) {
        // NOTE: hardcoded because of dependency on it being '\u001f'
        return intellijDummyIdentifier ? ST_ADDITIONAL_CHARS_SET_IDI + quantifier : ST_ADDITIONAL_CHARS_SET_NO_IDI;
    }

    public static int columnsToNextTabStop(int column) {
        // Tab stop is 4
        return 4 - (column % 4);
    }

    public static int findLineBreak(CharSequence s, int startIndex) {
        return SequenceUtils.indexOfAny(s, CharPredicate.ANY_EOL, startIndex);
    }

    public static boolean isBlank(CharSequence s) {
        return SequenceUtils.indexOfAnyNot(s, CharPredicate.BLANKSPACE) == -1;
    }

    public static boolean isLetter(CharSequence s, int index) {
        int codePoint = Character.codePointAt(s, index);
        return Character.isLetter(codePoint);
    }

    public static boolean isSpaceOrTab(CharSequence s, int index) {
        return CharPredicate.SPACE_TAB.test(SequenceUtils.safeCharAt(s, index));
    }
}
