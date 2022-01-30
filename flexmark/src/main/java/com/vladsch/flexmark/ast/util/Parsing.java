package com.vladsch.flexmark.ast.util;

import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.format.TableFormatOptions;
import com.vladsch.flexmark.util.misc.CharPredicate;
import com.vladsch.flexmark.util.sequence.Escaping;
import com.vladsch.flexmark.util.sequence.SequenceUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.function.Function;
import java.util.regex.Pattern;

import static com.vladsch.flexmark.parser.Parser.*;

public class Parsing {
    final public static char INTELLIJ_DUMMY_IDENTIFIER_CHAR = TableFormatOptions.INTELLIJ_DUMMY_IDENTIFIER_CHAR;
    final public static String INTELLIJ_DUMMY_IDENTIFIER = TableFormatOptions.INTELLIJ_DUMMY_IDENTIFIER;

//    final public static String XML_NAMESPACE_START = "[_A-Za-z]";
//    final public static String XML_NAMESPACE_CHAR = XML_NAME_SPACE_START + "|-|.|[0-9]";
    final public static String XML_NAMESPACE_START = "[_A-Za-z\u00C0-\u00D6\u00D8-\u00F6\u00F8-\u02FF\u0370-\u037D\u037F-\u1FFF\u200C-\u200D\u2070-\u218F\u2C00-\u2FEF\u3001-\uD7FF\uF900-\uFDCF\uFDF0-\uFFFD]"; // excluded  [#x10000-#xEFFFF]
    final public static String XML_NAMESPACE_CHAR = XML_NAMESPACE_START + "|[.0-9\u00B7\u0300-\u036F\u203F-\u2040-]";
    final public static String XML_NAMESPACE = "(?:(?:" + XML_NAMESPACE_START + ")(?:" + XML_NAMESPACE_CHAR + ")*:)?";

    // save options for others to use when only parsing instance is available
    final public DataHolder options;

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
    final public Pattern LINK_DESTINATION;
    final public Pattern LINK_DESTINATION_MATCHED_PARENS;
    final public Pattern LINK_DESTINATION_MATCHED_PARENS_NOSP;

    final private static String ST_EXCLUDED_0_TO_SPACE_IDI = "\u0000-\u001e\u0020";
    final private static String ST_EXCLUDED_0_TO_SPACE_NO_IDI = "\u0000-\u0020";
    final private static String ST_ADDITIONAL_CHARS_IDI = "\u001f";
    final private static String ST_ADDITIONAL_CHARS_NO_IDI = "";
    final private static String ST_ADDITIONAL_CHARS_SET_IDI = "[\u001f]";
    final private static String ST_ADDITIONAL_CHARS_SET_NO_IDI = "";

    final public static String ST_HTMLCOMMENT = "<!---->|<!--(?:-?[^>-])(?:-?[^-])*-->";
    final public static String ST_PROCESSINGINSTRUCTION = "[<][?].*?[?][>]";
    final public static String ST_CDATA = "<!\\[CDATA\\[[\\s\\S]*?\\]\\]>";
    final public static String ST_SINGLEQUOTEDVALUE = "'[^']*'";
    final public static String ST_DOUBLEQUOTEDVALUE = "\"[^\"]*\"";

    final public String HTMLCOMMENT = ST_HTMLCOMMENT;
    final public String PROCESSINGINSTRUCTION = ST_PROCESSINGINSTRUCTION;
    final public String CDATA = ST_CDATA;
    final public String SINGLEQUOTEDVALUE = ST_SINGLEQUOTEDVALUE;
    final public String DOUBLEQUOTEDVALUE = ST_DOUBLEQUOTEDVALUE;

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

    // IntelliJDummyIdentifier dependent
    final private static String ST_TAGNAME_IDI = "[A-Za-z" + ST_ADDITIONAL_CHARS_IDI + "][A-Za-z0-9" + ST_ADDITIONAL_CHARS_IDI + "-]*";
    final private static String ST_TAGNAME_NO_IDI = "[A-Za-z" + ST_ADDITIONAL_CHARS_NO_IDI + "][A-Za-z0-9" + ST_ADDITIONAL_CHARS_NO_IDI + "-]*";

    final private static String ST_UNQUOTEDVALUE_IDI = "[^\"'=<>{}`" + ST_EXCLUDED_0_TO_SPACE_IDI + "]+";
    final private static String ST_UNQUOTEDVALUE_NO_IDI = "[^\"'=<>{}`" + ST_EXCLUDED_0_TO_SPACE_NO_IDI + "]+";

    final private static String ST_ATTRIBUTENAME_IDI = "[a-zA-Z" + ST_ADDITIONAL_CHARS_IDI + "_:][a-zA-Z0-9" + ST_ADDITIONAL_CHARS_IDI + ":._-]*";
    final private static String ST_ATTRIBUTENAME_NO_IDI = "[a-zA-Z" + ST_ADDITIONAL_CHARS_NO_IDI + "_:][a-zA-Z0-9" + ST_ADDITIONAL_CHARS_NO_IDI + ":._-]*";

    final private static String ST_ATTRIBUTEVALUE_IDI = "(?:" + ST_UNQUOTEDVALUE_IDI + "|" + ST_SINGLEQUOTEDVALUE + "|" + ST_DOUBLEQUOTEDVALUE + ")";
    final private static String ST_ATTRIBUTEVALUE_NO_IDI = "(?:" + ST_UNQUOTEDVALUE_NO_IDI + "|" + ST_SINGLEQUOTEDVALUE + "|" + ST_DOUBLEQUOTEDVALUE + ")";

    final private static String ST_ATTRIBUTEVALUESPEC_IDI = "(?:" + "\\s*=" + "\\s*" + ST_ATTRIBUTEVALUE_IDI + ")";
    final private static String ST_ATTRIBUTEVALUESPEC_NO_IDI = "(?:" + "\\s*=" + "\\s*" + ST_ATTRIBUTEVALUE_NO_IDI + ")";

    final private static String ST_CLOSETAG_IDI = "</" + ST_TAGNAME_IDI + "\\s*[>]";
    final private static String ST_CLOSETAG_NO_IDI = "</" + ST_TAGNAME_NO_IDI + "\\s*[>]";
    final private static String ST_NS_CLOSETAG_IDI = "</" + XML_NAMESPACE + ST_TAGNAME_IDI + "\\s*[>]";
    final private static String ST_NS_CLOSETAG_NO_IDI = "</" + XML_NAMESPACE + ST_TAGNAME_NO_IDI + "\\s*[>]";

    final private static String ST_ATTRIBUTE_IDI = "(?:" + "\\s+" + ST_ATTRIBUTENAME_IDI + ST_ATTRIBUTEVALUESPEC_IDI + "?)";
    final private static String ST_ATTRIBUTE_NO_IDI = "(?:" + "\\s+" + ST_ATTRIBUTENAME_NO_IDI + ST_ATTRIBUTEVALUESPEC_NO_IDI + "?)";

    final private static String ST_DECLARATION_IDI = "<![A-Z" + ST_ADDITIONAL_CHARS_IDI + "]+\\s+[^>]*>";
    final private static String ST_DECLARATION_NO_IDI = "<![A-Z" + ST_ADDITIONAL_CHARS_NO_IDI + "]+\\s+[^>]*>";

    final private static String ST_ENTITY_IDI = "&(?:#x[a-f0-9" + ST_ADDITIONAL_CHARS_IDI + "]{1,8}|#[0-9]{1,8}|[a-z" + ST_ADDITIONAL_CHARS_IDI + "][a-z0-9" + ST_ADDITIONAL_CHARS_IDI + "]{1,31});";
    final private static String ST_ENTITY_NO_IDI = "&(?:#x[a-f0-9" + ST_ADDITIONAL_CHARS_NO_IDI + "]{1,8}|#[0-9]{1,8}|[a-z" + ST_ADDITIONAL_CHARS_NO_IDI + "][a-z0-9" + ST_ADDITIONAL_CHARS_NO_IDI + "]{1,31});";

    final private static String ST_IN_BRACES_W_SP_IDI = "\\{\\{(?:[^{}\\\\" + ST_EXCLUDED_0_TO_SPACE_IDI + "]| |\t)*\\}\\}";
    final private static String ST_IN_BRACES_W_SP_NO_IDI = "\\{\\{(?:[^{}\\\\" + ST_EXCLUDED_0_TO_SPACE_NO_IDI + "]| |\t)*\\}\\}";

    final private static String ST_REG_CHAR_IDI = "[^\\\\()" + ST_EXCLUDED_0_TO_SPACE_IDI + "]";
    final private static String ST_REG_CHAR_NO_IDI = "[^\\\\()" + ST_EXCLUDED_0_TO_SPACE_NO_IDI + "]";

    final private static String ST_IN_MATCHED_PARENS_NOSP_IDI = "\\((" + ST_REG_CHAR_IDI + '|' + ST_ESCAPED_CHAR + ")*\\)";
    final private static String ST_IN_MATCHED_PARENS_NOSP_NO_IDI = "\\((" + ST_REG_CHAR_NO_IDI + '|' + ST_ESCAPED_CHAR + ")*\\)";

    final private static String ST_REG_CHAR_SP_IDI = "[^\\\\()" + ST_EXCLUDED_0_TO_SPACE_IDI + "]| (?![\"'])";
    final private static String ST_REG_CHAR_SP_NO_IDI = "[^\\\\()" + ST_EXCLUDED_0_TO_SPACE_NO_IDI + "]| (?![\"'])";

    final private static String ST_IN_MATCHED_PARENS_W_SP_IDI = "\\((" + ST_REG_CHAR_SP_IDI + '|' + ST_ESCAPED_CHAR + ")*\\)";
    final private static String ST_IN_MATCHED_PARENS_W_SP_NO_IDI = "\\((" + ST_REG_CHAR_SP_NO_IDI + '|' + ST_ESCAPED_CHAR + ")*\\)";

    final private static String ST_IN_PARENS_NOSP_IDI = "\\((" + ST_REG_CHAR_IDI + '|' + ST_ESCAPED_CHAR + ")*\\)";
    final private static String ST_IN_PARENS_NOSP_NO_IDI = "\\((" + ST_REG_CHAR_NO_IDI + '|' + ST_ESCAPED_CHAR + ")*\\)";

    final private static String ST_IN_PARENS_W_SP_IDI = "\\((" + ST_REG_CHAR_SP_IDI + '|' + ST_ESCAPED_CHAR + ")*\\)";
    final private static String ST_IN_PARENS_W_SP_NO_IDI = "\\((" + ST_REG_CHAR_SP_NO_IDI + '|' + ST_ESCAPED_CHAR + ")*\\)";

    final private static String ST_OPENTAG_IDI = "<" + ST_TAGNAME_IDI + ST_ATTRIBUTE_IDI + "*" + "\\s*/?>";
    final private static String ST_OPENTAG_NO_IDI = "<" + ST_TAGNAME_NO_IDI + ST_ATTRIBUTE_NO_IDI + "*" + "\\s*/?>";
    final private static String ST_NS_OPENTAG_IDI = "<" + XML_NAMESPACE + ST_TAGNAME_IDI + ST_ATTRIBUTE_IDI + "*" + "\\s*/?>";
    final private static String ST_NS_OPENTAG_NO_IDI = "<" + XML_NAMESPACE + ST_TAGNAME_NO_IDI + ST_ATTRIBUTE_NO_IDI + "*" + "\\s*/?>";

    final private static String ST_REG_CHAR_PARENS_IDI = "[^\\\\" + ST_EXCLUDED_0_TO_SPACE_IDI + "]";
    final private static String ST_REG_CHAR_PARENS_NO_IDI = "[^\\\\" + ST_EXCLUDED_0_TO_SPACE_NO_IDI + "]";

    final private static String ST_REG_CHAR_SP_PARENS_IDI = "[^\\\\" + ST_EXCLUDED_0_TO_SPACE_IDI + "]| (?![\"'])";
    final private static String ST_REG_CHAR_SP_PARENS_NO_IDI = "[^\\\\" + ST_EXCLUDED_0_TO_SPACE_NO_IDI + "]| (?![\"'])";

    final private static Pattern ST_ENTITY_HERE_IDI = Pattern.compile('^' + ST_ENTITY_IDI, Pattern.CASE_INSENSITIVE);
    final private static Pattern ST_ENTITY_HERE_NO_IDI = Pattern.compile('^' + ST_ENTITY_NO_IDI, Pattern.CASE_INSENSITIVE);

    final public String ADDITIONAL_CHARS;
    final public String EXCLUDED_0_TO_SPACE;
    final public String REG_CHAR;
    final public String REG_CHAR_PARENS;
    final public String REG_CHAR_SP;
    final public String REG_CHAR_SP_PARENS;
    final public String IN_PARENS_NOSP;
    final public String IN_PARENS_W_SP;
    final public String IN_MATCHED_PARENS_NOSP;
    final public String IN_MATCHED_PARENS_W_SP;
    final public String IN_BRACES_W_SP;
    final public String DECLARATION;
    final public String ENTITY;
    final public String TAGNAME;
    final public String ATTRIBUTENAME;
    final public String UNQUOTEDVALUE;
    final public String ATTRIBUTEVALUE;
    final public String ATTRIBUTEVALUESPEC;
    final public String ATTRIBUTE;
    final public String OPENTAG;
    final public String CLOSETAG;

    final public String HTMLTAG;

    final public Pattern ENTITY_HERE;
    final public Pattern HTML_TAG;
    final public Pattern LIST_ITEM_MARKER;

    final public int CODE_BLOCK_INDENT;

    final public boolean intellijDummyIdentifier;
    final public boolean htmlForTranslator;
    final public String translationHtmlInlineTagPattern;
    final public String translationAutolinkTagPattern;
    final public boolean spaceInLinkUrl;
    final public boolean parseJekyllMacroInLinkUrl;
    final public String itemPrefixChars;
    final public boolean listsItemMarkerSpace;
    final public boolean listsOrderedItemDotOnly;
    final public boolean allowNameSpace;

    private static class PatternTypeFlags {
        final @Nullable Boolean intellijDummyIdentifier;
        final @Nullable Boolean htmlForTranslator;
        final @Nullable String translationHtmlInlineTagPattern;
        final @Nullable String translationAutolinkTagPattern;
        final @Nullable Boolean spaceInLinkUrl;
        final @Nullable Boolean parseJekyllMacroInLinkUrl;
        final @Nullable String itemPrefixChars;
        final @Nullable Boolean listsItemMarkerSpace;
        final @Nullable Boolean listsOrderedItemDotOnly;
        final @Nullable Boolean allowNameSpace;

        PatternTypeFlags(DataHolder options) {
            this.intellijDummyIdentifier = Parser.INTELLIJ_DUMMY_IDENTIFIER.get(options);
            this.htmlForTranslator = Parser.HTML_FOR_TRANSLATOR.get(options);
            this.translationHtmlInlineTagPattern = Parser.TRANSLATION_HTML_INLINE_TAG_PATTERN.get(options);
            this.translationAutolinkTagPattern = Parser.TRANSLATION_AUTOLINK_TAG_PATTERN.get(options);
            this.spaceInLinkUrl = Parser.SPACE_IN_LINK_URLS.get(options);
            this.parseJekyllMacroInLinkUrl = Parser.PARSE_JEKYLL_MACROS_IN_URLS.get(options);
            this.itemPrefixChars = LISTS_ITEM_PREFIX_CHARS.get(options);
            this.listsItemMarkerSpace = LISTS_ITEM_MARKER_SPACE.get(options);
            this.listsOrderedItemDotOnly = LISTS_ORDERED_ITEM_DOT_ONLY.get(options);
            this.allowNameSpace = HTML_ALLOW_NAME_SPACE.get(options);
        }

        public PatternTypeFlags(
                @Nullable Boolean intellijDummyIdentifier
                , @Nullable Boolean htmlForTranslator
                , @Nullable String translationHtmlInlineTagPattern
                , @Nullable String translationAutolinkTagPattern
                , @Nullable Boolean spaceInLinkUrl
                , @Nullable Boolean parseJekyllMacroInLinkUrl
                , @Nullable String itemPrefixChars
                , @Nullable Boolean listsItemMarkerSpace
                , @Nullable Boolean listsOrderedItemDotOnly
                , @Nullable Boolean allowNameSpace
        ) {
            this.intellijDummyIdentifier = intellijDummyIdentifier;
            this.htmlForTranslator = htmlForTranslator;
            this.translationHtmlInlineTagPattern = translationHtmlInlineTagPattern;
            this.translationAutolinkTagPattern = translationAutolinkTagPattern;
            this.spaceInLinkUrl = spaceInLinkUrl;
            this.parseJekyllMacroInLinkUrl = parseJekyllMacroInLinkUrl;
            this.itemPrefixChars = itemPrefixChars;
            this.listsItemMarkerSpace = listsItemMarkerSpace;
            this.listsOrderedItemDotOnly = listsOrderedItemDotOnly;
            this.allowNameSpace = allowNameSpace;
        }

        PatternTypeFlags withJekyllMacroInLinkUrl() { return new PatternTypeFlags(intellijDummyIdentifier, null, null, null, null, parseJekyllMacroInLinkUrl, null, null, null, null); }

        PatternTypeFlags withJekyllMacroSpaceInLinkUrl() { return new PatternTypeFlags(intellijDummyIdentifier, null, null, null, spaceInLinkUrl, parseJekyllMacroInLinkUrl, null, null, null, null); }

        PatternTypeFlags withHtmlTranslator() { return new PatternTypeFlags(intellijDummyIdentifier, htmlForTranslator, translationHtmlInlineTagPattern, translationAutolinkTagPattern, null, null, null, null, null, null); }

        PatternTypeFlags withItemPrefixChars() { return new PatternTypeFlags(null, null, null, null, null, null, itemPrefixChars, listsItemMarkerSpace, listsOrderedItemDotOnly, null); }

        PatternTypeFlags withAllowNameSpace() { return new PatternTypeFlags(null, null, null, null, null, null, null, null, null, allowNameSpace); }

        /**
         * Compare where null entry equals any other value
         *
         * @param o other
         * @return true if equal
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            PatternTypeFlags that = (PatternTypeFlags) o;

            if (intellijDummyIdentifier != null && !intellijDummyIdentifier.equals(that.intellijDummyIdentifier)) return false;
            if (htmlForTranslator != null && !htmlForTranslator.equals(that.htmlForTranslator)) return false;
            if (translationHtmlInlineTagPattern != null && !translationHtmlInlineTagPattern.equals(that.translationHtmlInlineTagPattern)) return false;
            if (translationAutolinkTagPattern != null && !translationAutolinkTagPattern.equals(that.translationAutolinkTagPattern)) return false;
            if (spaceInLinkUrl != null && !spaceInLinkUrl.equals(that.spaceInLinkUrl)) return false;
            if (parseJekyllMacroInLinkUrl != null && !parseJekyllMacroInLinkUrl.equals(that.parseJekyllMacroInLinkUrl)) return false;
            if (itemPrefixChars != null && !itemPrefixChars.equals(that.itemPrefixChars)) return false;
            if (listsItemMarkerSpace != null && !listsItemMarkerSpace.equals(that.listsItemMarkerSpace)) return false;
            if (allowNameSpace != null && !allowNameSpace.equals(that.allowNameSpace)) return false;

            return listsOrderedItemDotOnly == null || listsOrderedItemDotOnly.equals(that.listsOrderedItemDotOnly);
        }

        @Override
        public int hashCode() {
            int result = intellijDummyIdentifier != null ? intellijDummyIdentifier.hashCode() : 0;
            result = 31 * result + (htmlForTranslator != null ? htmlForTranslator.hashCode() : 0);
            result = 31 * result + (translationHtmlInlineTagPattern != null ? translationHtmlInlineTagPattern.hashCode() : 0);
            result = 31 * result + (translationAutolinkTagPattern != null ? translationAutolinkTagPattern.hashCode() : 0);
            result = 31 * result + (spaceInLinkUrl != null ? spaceInLinkUrl.hashCode() : 0);
            result = 31 * result + (parseJekyllMacroInLinkUrl != null ? parseJekyllMacroInLinkUrl.hashCode() : 0);
            result = 31 * result + (itemPrefixChars != null ? itemPrefixChars.hashCode() : 0);
            result = 31 * result + (listsItemMarkerSpace != null ? listsItemMarkerSpace.hashCode() : 0);
            result = 31 * result + (listsOrderedItemDotOnly != null ? listsOrderedItemDotOnly.hashCode() : 0);
            result = 31 * result + (allowNameSpace != null ? allowNameSpace.hashCode() : 0);
            return result;
        }
    }

    final static HashMap<String, HashMap<PatternTypeFlags, Pattern>> cachedPatterns = new HashMap<>();

    static Pattern getCachedPattern(@NotNull String patternName, @NotNull PatternTypeFlags cachedTypeFlags, @NotNull Function<PatternTypeFlags, Pattern> factory) {
        HashMap<PatternTypeFlags, Pattern> patternMap = cachedPatterns.computeIfAbsent(patternName, (key) -> new HashMap<>());
        return patternMap.computeIfAbsent(cachedTypeFlags, factory);
    }

    public Parsing(DataHolder options) {
        this.options = options;
        this.CODE_BLOCK_INDENT = Parser.CODE_BLOCK_INDENT.get(options); // make sure this is consistent with lists settings
        PatternTypeFlags patternTypeFlags = new PatternTypeFlags(options);
        this.intellijDummyIdentifier = Boolean.TRUE.equals(patternTypeFlags.intellijDummyIdentifier);
        this.htmlForTranslator = Boolean.TRUE.equals(patternTypeFlags.htmlForTranslator);
        this.translationHtmlInlineTagPattern = patternTypeFlags.translationHtmlInlineTagPattern;
        this.translationAutolinkTagPattern = patternTypeFlags.translationAutolinkTagPattern;
        this.spaceInLinkUrl = Boolean.TRUE.equals(patternTypeFlags.spaceInLinkUrl);
        this.parseJekyllMacroInLinkUrl = Boolean.TRUE.equals(patternTypeFlags.parseJekyllMacroInLinkUrl);
        this.itemPrefixChars = patternTypeFlags.itemPrefixChars;
        this.listsItemMarkerSpace = Boolean.TRUE.equals(patternTypeFlags.listsItemMarkerSpace);
        this.listsOrderedItemDotOnly = Boolean.TRUE.equals(patternTypeFlags.listsOrderedItemDotOnly);
        this.allowNameSpace = Boolean.TRUE.equals(patternTypeFlags.allowNameSpace);

        if (intellijDummyIdentifier) {
            this.ADDITIONAL_CHARS = ST_ADDITIONAL_CHARS_IDI;
            this.EXCLUDED_0_TO_SPACE = ST_EXCLUDED_0_TO_SPACE_IDI;
            this.REG_CHAR = ST_REG_CHAR_IDI;
            this.REG_CHAR_PARENS = ST_REG_CHAR_PARENS_IDI;
            this.REG_CHAR_SP = ST_REG_CHAR_SP_IDI;
            this.REG_CHAR_SP_PARENS = ST_REG_CHAR_SP_PARENS_IDI;
            this.IN_PARENS_NOSP = ST_IN_PARENS_NOSP_IDI;
            this.IN_PARENS_W_SP = ST_IN_PARENS_W_SP_IDI;
            this.IN_MATCHED_PARENS_NOSP = ST_IN_MATCHED_PARENS_NOSP_IDI;
            this.IN_MATCHED_PARENS_W_SP = ST_IN_MATCHED_PARENS_W_SP_IDI;
            this.IN_BRACES_W_SP = ST_IN_BRACES_W_SP_IDI;
            this.DECLARATION = ST_DECLARATION_IDI;
            this.ENTITY = ST_ENTITY_IDI;
            this.TAGNAME = ST_TAGNAME_IDI;
            this.ATTRIBUTENAME = ST_ATTRIBUTENAME_IDI;
            this.UNQUOTEDVALUE = ST_UNQUOTEDVALUE_IDI;
            this.ATTRIBUTEVALUE = ST_ATTRIBUTEVALUE_IDI;
            this.ATTRIBUTEVALUESPEC = ST_ATTRIBUTEVALUESPEC_IDI;
            this.ATTRIBUTE = ST_ATTRIBUTE_IDI;
            this.OPENTAG = allowNameSpace ? ST_NS_OPENTAG_IDI : ST_OPENTAG_IDI;
            this.CLOSETAG = allowNameSpace ? ST_NS_CLOSETAG_IDI : ST_CLOSETAG_IDI;
        } else {
            this.ADDITIONAL_CHARS = ST_ADDITIONAL_CHARS_NO_IDI;
            this.EXCLUDED_0_TO_SPACE = ST_EXCLUDED_0_TO_SPACE_NO_IDI;
            this.REG_CHAR = ST_REG_CHAR_NO_IDI;
            this.REG_CHAR_PARENS = ST_REG_CHAR_PARENS_NO_IDI;
            this.REG_CHAR_SP = ST_REG_CHAR_SP_NO_IDI;
            this.REG_CHAR_SP_PARENS = ST_REG_CHAR_SP_PARENS_NO_IDI;
            this.IN_PARENS_NOSP = ST_IN_PARENS_NOSP_NO_IDI;
            this.IN_PARENS_W_SP = ST_IN_PARENS_W_SP_NO_IDI;
            this.IN_MATCHED_PARENS_NOSP = ST_IN_MATCHED_PARENS_NOSP_NO_IDI;
            this.IN_MATCHED_PARENS_W_SP = ST_IN_MATCHED_PARENS_W_SP_NO_IDI;
            this.IN_BRACES_W_SP = ST_IN_BRACES_W_SP_NO_IDI;
            this.DECLARATION = ST_DECLARATION_NO_IDI;
            this.ENTITY = ST_ENTITY_NO_IDI;
            this.TAGNAME = ST_TAGNAME_NO_IDI;
            this.ATTRIBUTENAME = ST_ATTRIBUTENAME_NO_IDI;
            this.UNQUOTEDVALUE = ST_UNQUOTEDVALUE_NO_IDI;
            this.ATTRIBUTEVALUE = ST_ATTRIBUTEVALUE_NO_IDI;
            this.ATTRIBUTEVALUESPEC = ST_ATTRIBUTEVALUESPEC_NO_IDI;
            this.ATTRIBUTE = ST_ATTRIBUTE_NO_IDI;
            this.OPENTAG = allowNameSpace ? ST_NS_OPENTAG_NO_IDI : ST_OPENTAG_NO_IDI;
            this.CLOSETAG = allowNameSpace ? ST_NS_CLOSETAG_NO_IDI : ST_CLOSETAG_NO_IDI;
        }

        // init flag based patterns
        this.LINK_DESTINATION_ANGLES = spaceInLinkUrl ? ST_LINK_DESTINATION_ANGLES_SPC : ST_LINK_DESTINATION_ANGLES_NO_SPC;
        this.ENTITY_HERE = intellijDummyIdentifier ? ST_ENTITY_HERE_IDI : ST_ENTITY_HERE_NO_IDI;

        // init dynamic patterns
        synchronized (cachedPatterns) {
            this.LINK_DESTINATION_MATCHED_PARENS_NOSP = getCachedPattern("LINK_DESTINATION_MATCHED_PARENS_NOSP", patternTypeFlags.withJekyllMacroInLinkUrl(), entry -> Pattern.compile(
                    "^(?:" + (parseJekyllMacroInLinkUrl ? IN_BRACES_W_SP + "|" : "")
                            + (REG_CHAR + "|") +
                            ESCAPED_CHAR + "|\\\\|\\(|\\))*"));

            this.LINK_DESTINATION = getCachedPattern("LINK_DESTINATION", patternTypeFlags.withJekyllMacroSpaceInLinkUrl(), entry -> Pattern.compile(
                    "^(?:" + (parseJekyllMacroInLinkUrl ? IN_BRACES_W_SP + "|" : "") +
                            (spaceInLinkUrl ? "(?:" + REG_CHAR_SP + ")|" : REG_CHAR + "|") +
                            ESCAPED_CHAR + "|\\\\|" + (spaceInLinkUrl ? IN_PARENS_W_SP : IN_PARENS_NOSP) + ")*"));

            this.LINK_DESTINATION_MATCHED_PARENS = getCachedPattern("LINK_DESTINATION_MATCHED_PARENS", patternTypeFlags.withJekyllMacroSpaceInLinkUrl(), entry -> Pattern.compile(
                    "^(?:" + (parseJekyllMacroInLinkUrl ? IN_BRACES_W_SP + "|" : "")
                            + (spaceInLinkUrl ? "(?:" + REG_CHAR_SP + ")|" : REG_CHAR + "|") +
                            ESCAPED_CHAR + "|\\\\|\\(|\\))*"));

            this.EMAIL_AUTOLINK = getCachedPattern("EMAIL_AUTOLINK", patternTypeFlags.withHtmlTranslator(), entry -> Pattern.compile(
                    "^<(" +
                            "(?:[a-zA-Z0-9" + ADDITIONAL_CHARS + ".!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9" + ADDITIONAL_CHARS + "](?:[a-zA-Z0-9" + ADDITIONAL_CHARS + "-]{0,61}[a-zA-Z0-9" + ADDITIONAL_CHARS + "])?(?:\\.[a-zA-Z0-9" + ADDITIONAL_CHARS + "](?:[a-zA-Z0-9" + ADDITIONAL_CHARS + "-]{0,61}[a-zA-Z0-9" + ADDITIONAL_CHARS + "])?)*)" +
                            (htmlForTranslator ? "|(?:" + translationAutolinkTagPattern + ")" : "") +
                            ")>"));

            this.AUTOLINK = getCachedPattern("AUTOLINK", patternTypeFlags.withHtmlTranslator(), entry -> Pattern.compile(
                    "^<(" +
                            "(?:[a-zA-Z][a-zA-Z0-9" + ADDITIONAL_CHARS + ".+-]{1,31}:[^<>" + EXCLUDED_0_TO_SPACE + "]*)" +
                            (htmlForTranslator ? "|(?:" + translationAutolinkTagPattern + ")" : "") +
                            ")>"));

            this.WWW_AUTOLINK = getCachedPattern("WWW_AUTOLINK", patternTypeFlags.withHtmlTranslator(), entry -> Pattern.compile(
                    "^<(" +
                            "(?:w" + ADDITIONAL_CHARS + "?){3,3}\\.[^<>" + EXCLUDED_0_TO_SPACE + "]*" +
                            (htmlForTranslator ? "|(?:" + translationAutolinkTagPattern + ")" : "") +
                            ")>"));

            this.HTML_TAG = getCachedPattern("HTML_TAG", patternTypeFlags.withHtmlTranslator(), entry -> Pattern.compile('^' + ("(?:" + OPENTAG + "|" + CLOSETAG + "|" + HTMLCOMMENT
                    + "|" + PROCESSINGINSTRUCTION + "|" + DECLARATION + "|" + CDATA +
                    (htmlForTranslator ? "|<(?:" + translationHtmlInlineTagPattern + ")>|</(?:" + translationHtmlInlineTagPattern + ")>" : "") + ")"), Pattern.CASE_INSENSITIVE));

            this.LIST_ITEM_MARKER = getCachedPattern("LIST_ITEM_MARKER", patternTypeFlags.withItemPrefixChars(), entry -> {
                if (listsItemMarkerSpace) {
                    if (listsOrderedItemDotOnly) {
                        return Pattern.compile("^([\\Q" + itemPrefixChars + "\\E])(?=[ \t])|^(\\d{1,9})([.])(?=[ \t])");
                    } else {
                        return Pattern.compile("^([\\Q" + itemPrefixChars + "\\E])(?=[ \t])|^(\\d{1,9})([.)])(?=[ \t])");
                    }
                } else {
                    if (listsOrderedItemDotOnly) {
                        return Pattern.compile("^([\\Q" + itemPrefixChars + "\\E])(?= |\t|$)|^(\\d{1,9})([.])(?= |\t|$)");
                    } else {
                        return Pattern.compile("^([\\Q" + itemPrefixChars + "\\E])(?= |\t|$)|^(\\d{1,9})([.)])(?= |\t|$)");
                    }
                }
            });
        }

        this.HTMLTAG = this.HTML_TAG.pattern();
    }

    /**
     * @deprecated in version (0.62.2), to be removed
     */
    @Deprecated
    public String EXCLUDED_0_TO_SPACE() {
        return intellijDummyIdentifier ? ST_EXCLUDED_0_TO_SPACE_IDI : ST_EXCLUDED_0_TO_SPACE_NO_IDI;
    }

    /**
     * @deprecated in version (0.62.2), to be removed
     */
    @Deprecated
    public String ADDITIONAL_CHARS() {
        return intellijDummyIdentifier ? ST_ADDITIONAL_CHARS_IDI : ST_ADDITIONAL_CHARS_NO_IDI;
    }

    /**
     * @deprecated in version (0.62.2), to be removed
     */
    @Deprecated
    public String ADDITIONAL_CHARS_SET(String quantifier) {
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
