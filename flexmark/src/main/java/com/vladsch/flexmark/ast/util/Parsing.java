package com.vladsch.flexmark.ast.util;

import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.misc.CharPredicate;
import com.vladsch.flexmark.util.sequence.Escaping;
import com.vladsch.flexmark.util.sequence.SequenceUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;

public class Parsing {
  private static final String XML_NAMESPACE_START =
      "[_A-Za-z\u00C0-\u00D6\u00D8-\u00F6\u00F8-\u02FF\u0370-\u037D\u037F-\u1FFF\u200C-\u200D\u2070-\u218F\u2C00-\u2FEF\u3001-\uD7FF\uF900-\uFDCF\uFDF0-\uFFFD]"; // excluded  [#x10000-#xEFFFF]
  private static final String XML_NAMESPACE_CHAR =
      XML_NAMESPACE_START + "|[.0-9\u00B7\u0300-\u036F\u203F-\u2040-]";
  public static final String XML_NAMESPACE =
      "(?:(?:" + XML_NAMESPACE_START + ")(?:" + XML_NAMESPACE_CHAR + ")*:)?";

  // save options for others to use when only parsing instance is available
  private static final String ST_EOL = "(?:\r\n|\r|\n)";
  private static final String ST_ESCAPED_CHAR = "\\\\" + Escaping.ESCAPABLE;
  private static final Pattern ST_LINK_LABEL =
      Pattern.compile("^\\[(?:[^\\\\\\[\\]]|" + ST_ESCAPED_CHAR + "|\\\\){0,999}\\]");
  private static final String ST_LINK_TITLE_STRING =
      "(?:\"("
          + ST_ESCAPED_CHAR
          + "|[^\"\\x00])*\""
          + '|'
          + "'("
          + ST_ESCAPED_CHAR
          + "|[^'\\x00])*'"
          + '|'
          + "\\(("
          + ST_ESCAPED_CHAR
          + "|[^)\\x00])*\\))";
  private static final Pattern ST_LINK_TITLE = Pattern.compile("^" + ST_LINK_TITLE_STRING);
  private static final String ESCAPED_CHAR = ST_ESCAPED_CHAR;
  public static final Pattern LINK_LABEL = ST_LINK_LABEL;
  public static final Pattern linkTitle = ST_LINK_TITLE;

  private static final String ST_EXCLUDED_0_TO_SPACE_IDI = "\u0000-\u001e\u0020";
  private static final String ST_EXCLUDED_0_TO_SPACE_NO_IDI = "\u0000-\u0020";
  private static final String ST_ADDITIONAL_CHARS_IDI = "\u001f";
  private static final String ST_ADDITIONAL_CHARS_NO_IDI = "";

  private static final String ST_HTMLCOMMENT = "<!---->|<!--(?:-?[^>-])(?:-?[^-])*-->";
  private static final String ST_PROCESSINGINSTRUCTION = "[<][?].*?[?][>]";
  private static final String ST_CDATA = "<!\\[CDATA\\[[\\s\\S]*?\\]\\]>";
  private static final String ST_SINGLEQUOTEDVALUE = "'[^']*'";
  private static final String ST_DOUBLEQUOTEDVALUE = "\"[^\"]*\"";

  private static final String HTMLCOMMENT = ST_HTMLCOMMENT;
  private static final String PROCESSINGINSTRUCTION = ST_PROCESSINGINSTRUCTION;
  private static final String CDATA = ST_CDATA;
  private static final String ST_ASCII_PUNCTUATION = "'!\"#\\$%&\\*\\+,\\-\\./:;=\\?@\\\\\\^_`\\|~";
  private static final String ST_ASCII_OPEN_PUNCTUATION = "\\(<\\[\\{";
  private static final String ST_ASCII_CLOSE_PUNCTUATION = "\\)>\\]\\}";
  private static final Pattern ST_PUNCTUATION =
      Pattern.compile(
          "^["
              + ST_ASCII_PUNCTUATION
              + ST_ASCII_OPEN_PUNCTUATION
              + ST_ASCII_CLOSE_PUNCTUATION
              + "\\p{Pc}\\p{Pd}\\p{Pe}\\p{Pf}\\p{Pi}\\p{Po}\\p{Ps}]");
  private static final Pattern ST_PUNCTUATION_OPEN =
      Pattern.compile(
          "^["
              + ST_ASCII_PUNCTUATION
              + ST_ASCII_OPEN_PUNCTUATION
              + "]|[\\p{Pc}\\p{Pd}\\p{Pe}\\p{Pf}\\p{Pi}\\p{Po}\\p{Ps}]&&[^"
              + ST_ASCII_CLOSE_PUNCTUATION
              + "]");
  private static final Pattern ST_PUNCTUATION_CLOSE =
      Pattern.compile(
          "^["
              + ST_ASCII_PUNCTUATION
              + ST_ASCII_CLOSE_PUNCTUATION
              + "]|[\\p{Pc}\\p{Pd}\\p{Pe}\\p{Pf}\\p{Pi}\\p{Po}\\p{Ps}]&&[^"
              + ST_ASCII_OPEN_PUNCTUATION
              + "]");
  public static final Pattern PUNCTUATION = ST_PUNCTUATION;
  public static final Pattern PUNCTUATION_OPEN = ST_PUNCTUATION_OPEN;
  public static final Pattern PUNCTUATION_CLOSE = ST_PUNCTUATION_CLOSE;

  private static final Pattern ST_ESCAPABLE = Pattern.compile('^' + Escaping.ESCAPABLE);
  private static final Pattern ST_TICKS = Pattern.compile("`+");
  private static final Pattern ST_TICKS_HERE = Pattern.compile("^`+");
  private static final Pattern ST_SPNL =
      Pattern.compile("^(?:[ \t])*(?:" + ST_EOL + "(?:[ \t])*)?");
  private static final Pattern ST_SPNL_URL = Pattern.compile("^(?:[ \t])*" + ST_EOL);
  private static final Pattern ST_SPNI = Pattern.compile("^ {0,3}");
  private static final Pattern ST_SP = Pattern.compile("^(?:[ \t])*");
  private static final Pattern ST_REST_OF_LINE = Pattern.compile("^.*" + ST_EOL);
  private static final Pattern ST_UNICODE_WHITESPACE_CHAR = Pattern.compile("^[\\p{Zs}\t\r\n\f]");
  private static final Pattern ST_WHITESPACE = Pattern.compile("\\s+");
  private static final Pattern ST_FINAL_SPACE = Pattern.compile(" *$");
  private static final Pattern ST_LINE_END = Pattern.compile("^[ \t]*(?:" + ST_EOL + "|$)");
  private static final Pattern ST_LINK_DESTINATION_ANGLES_SPC =
      Pattern.compile(
          "^(?:[<](?:[^<> \\t\\n\\\\\\x00]"
              + '|'
              + ST_ESCAPED_CHAR
              + '|'
              + "\\\\| (?![\"']))*[>])");
  private static final Pattern ST_LINK_DESTINATION_ANGLES_NO_SPC =
      Pattern.compile(
          "^(?:[<](?:[^<> \\t\\n\\\\\\x00]" + '|' + ST_ESCAPED_CHAR + '|' + "\\\\)*[>])");

  public static final Pattern ESCAPABLE = ST_ESCAPABLE;
  public static final Pattern TICKS = ST_TICKS;
  public static final Pattern TICKS_HERE = ST_TICKS_HERE;

  public static final Pattern SPNL = ST_SPNL;
  public static final Pattern SPNL_URL = ST_SPNL_URL;
  public static final Pattern SPNI = ST_SPNI;
  public static final Pattern SP = ST_SP;
  public static final Pattern REST_OF_LINE = ST_REST_OF_LINE;
  public static final Pattern UNICODE_WHITESPACE_CHAR = ST_UNICODE_WHITESPACE_CHAR;
  public static final Pattern WHITESPACE = ST_WHITESPACE;
  public static final Pattern FINAL_SPACE = ST_FINAL_SPACE;
  public static final Pattern LINE_END = ST_LINE_END;

  // IntelliJDummyIdentifier dependent
  private static final String ST_TAGNAME_IDI =
      "[A-Za-z" + ST_ADDITIONAL_CHARS_IDI + "][A-Za-z0-9" + ST_ADDITIONAL_CHARS_IDI + "-]*";
  private static final String ST_TAGNAME_NO_IDI =
      "[A-Za-z" + ST_ADDITIONAL_CHARS_NO_IDI + "][A-Za-z0-9" + ST_ADDITIONAL_CHARS_NO_IDI + "-]*";

  private static final String ST_UNQUOTEDVALUE_IDI =
      "[^\"'=<>{}`" + ST_EXCLUDED_0_TO_SPACE_IDI + "]+";
  private static final String ST_UNQUOTEDVALUE_NO_IDI =
      "[^\"'=<>{}`" + ST_EXCLUDED_0_TO_SPACE_NO_IDI + "]+";

  private static final String ST_ATTRIBUTENAME_IDI =
      "[a-zA-Z" + ST_ADDITIONAL_CHARS_IDI + "_:][a-zA-Z0-9" + ST_ADDITIONAL_CHARS_IDI + ":._-]*";
  private static final String ST_ATTRIBUTENAME_NO_IDI =
      "[a-zA-Z"
          + ST_ADDITIONAL_CHARS_NO_IDI
          + "_:][a-zA-Z0-9"
          + ST_ADDITIONAL_CHARS_NO_IDI
          + ":._-]*";

  private static final String ST_ATTRIBUTEVALUE_IDI =
      "(?:" + ST_UNQUOTEDVALUE_IDI + "|" + ST_SINGLEQUOTEDVALUE + "|" + ST_DOUBLEQUOTEDVALUE + ")";
  private static final String ST_ATTRIBUTEVALUE_NO_IDI =
      "(?:"
          + ST_UNQUOTEDVALUE_NO_IDI
          + "|"
          + ST_SINGLEQUOTEDVALUE
          + "|"
          + ST_DOUBLEQUOTEDVALUE
          + ")";

  private static final String ST_ATTRIBUTEVALUESPEC_IDI =
      "(?:" + "\\s*=" + "\\s*" + ST_ATTRIBUTEVALUE_IDI + ")";
  private static final String ST_ATTRIBUTEVALUESPEC_NO_IDI =
      "(?:" + "\\s*=" + "\\s*" + ST_ATTRIBUTEVALUE_NO_IDI + ")";

  private static final String ST_CLOSETAG_IDI = "</" + ST_TAGNAME_IDI + "\\s*[>]";
  private static final String ST_CLOSETAG_NO_IDI = "</" + ST_TAGNAME_NO_IDI + "\\s*[>]";
  private static final String ST_NS_CLOSETAG_IDI =
      "</" + XML_NAMESPACE + ST_TAGNAME_IDI + "\\s*[>]";
  private static final String ST_NS_CLOSETAG_NO_IDI =
      "</" + XML_NAMESPACE + ST_TAGNAME_NO_IDI + "\\s*[>]";

  private static final String ST_ATTRIBUTE_IDI =
      "(?:" + "\\s+" + ST_ATTRIBUTENAME_IDI + ST_ATTRIBUTEVALUESPEC_IDI + "?)";
  private static final String ST_ATTRIBUTE_NO_IDI =
      "(?:" + "\\s+" + ST_ATTRIBUTENAME_NO_IDI + ST_ATTRIBUTEVALUESPEC_NO_IDI + "?)";

  private static final String ST_DECLARATION_IDI =
      "<![A-Z" + ST_ADDITIONAL_CHARS_IDI + "]+\\s+[^>]*>";
  private static final String ST_DECLARATION_NO_IDI =
      "<![A-Z" + ST_ADDITIONAL_CHARS_NO_IDI + "]+\\s+[^>]*>";

  private static final String ST_ENTITY_IDI =
      "&(?:#x[a-f0-9"
          + ST_ADDITIONAL_CHARS_IDI
          + "]{1,8}|#[0-9]{1,8}|[a-z"
          + ST_ADDITIONAL_CHARS_IDI
          + "][a-z0-9"
          + ST_ADDITIONAL_CHARS_IDI
          + "]{1,31});";
  private static final String ST_ENTITY_NO_IDI =
      "&(?:#x[a-f0-9"
          + ST_ADDITIONAL_CHARS_NO_IDI
          + "]{1,8}|#[0-9]{1,8}|[a-z"
          + ST_ADDITIONAL_CHARS_NO_IDI
          + "][a-z0-9"
          + ST_ADDITIONAL_CHARS_NO_IDI
          + "]{1,31});";

  private static final String ST_IN_BRACES_W_SP_IDI =
      "\\{\\{(?:[^{}\\\\" + ST_EXCLUDED_0_TO_SPACE_IDI + "]| |\t)*\\}\\}";
  private static final String ST_IN_BRACES_W_SP_NO_IDI =
      "\\{\\{(?:[^{}\\\\" + ST_EXCLUDED_0_TO_SPACE_NO_IDI + "]| |\t)*\\}\\}";

  private static final String ST_REG_CHAR_IDI = "[^\\\\()" + ST_EXCLUDED_0_TO_SPACE_IDI + "]";
  private static final String ST_REG_CHAR_NO_IDI = "[^\\\\()" + ST_EXCLUDED_0_TO_SPACE_NO_IDI + "]";

  private static final String ST_REG_CHAR_SP_IDI =
      "[^\\\\()" + ST_EXCLUDED_0_TO_SPACE_IDI + "]| (?![\"'])";
  private static final String ST_REG_CHAR_SP_NO_IDI =
      "[^\\\\()" + ST_EXCLUDED_0_TO_SPACE_NO_IDI + "]| (?![\"'])";

  private static final String ST_IN_PARENS_NOSP_IDI =
      "\\((" + ST_REG_CHAR_IDI + '|' + ST_ESCAPED_CHAR + ")*\\)";
  private static final String ST_IN_PARENS_NOSP_NO_IDI =
      "\\((" + ST_REG_CHAR_NO_IDI + '|' + ST_ESCAPED_CHAR + ")*\\)";

  private static final String ST_IN_PARENS_W_SP_IDI =
      "\\((" + ST_REG_CHAR_SP_IDI + '|' + ST_ESCAPED_CHAR + ")*\\)";
  private static final String ST_IN_PARENS_W_SP_NO_IDI =
      "\\((" + ST_REG_CHAR_SP_NO_IDI + '|' + ST_ESCAPED_CHAR + ")*\\)";

  private static final String ST_OPENTAG_IDI =
      "<" + ST_TAGNAME_IDI + ST_ATTRIBUTE_IDI + "*" + "\\s*/?>";
  private static final String ST_OPENTAG_NO_IDI =
      "<" + ST_TAGNAME_NO_IDI + ST_ATTRIBUTE_NO_IDI + "*" + "\\s*/?>";
  private static final String ST_NS_OPENTAG_IDI =
      "<" + XML_NAMESPACE + ST_TAGNAME_IDI + ST_ATTRIBUTE_IDI + "*" + "\\s*/?>";
  private static final String ST_NS_OPENTAG_NO_IDI =
      "<" + XML_NAMESPACE + ST_TAGNAME_NO_IDI + ST_ATTRIBUTE_NO_IDI + "*" + "\\s*/?>";

  private static final Pattern ST_ENTITY_HERE_IDI =
      Pattern.compile('^' + ST_ENTITY_IDI, Pattern.CASE_INSENSITIVE);
  private static final Pattern ST_ENTITY_HERE_NO_IDI =
      Pattern.compile('^' + ST_ENTITY_NO_IDI, Pattern.CASE_INSENSITIVE);

  public final Pattern linkDestinationAngles;
  public final Pattern linkDestination;
  public final Pattern linkDestinationMatchedParens;
  public final Pattern emailAutolink;
  public final Pattern autolink;
  public final Pattern wwwAutolink;

  private final String additionalChars;
  private final String excluded0ToSpace;
  private final String regChar;
  private final String regCharSp;
  private final String inParensNosp;
  private final String inParensWSp;
  private final String inBracesWSp;
  private final String declaration;
  public final String opentag;
  public final String closetag;

  public final Pattern entityHere;
  public final Pattern htmlTag;
  public final Pattern listItemMarker;

  public final int codeBlockIndent;

  private final boolean intellijDummyIdentifier;
  private final boolean htmlForTranslator;
  private final String translationHtmlInlineTagPattern;
  private final String translationAutolinkTagPattern;
  private final boolean spaceInLinkUrl;
  private final boolean parseJekyllMacroInLinkUrl;
  private final String itemPrefixChars;
  private final boolean listsItemMarkerSpace;
  private final boolean listsOrderedItemDotOnly;
  private final boolean allowNameSpace;

  private static class PatternTypeFlags {
    final Boolean intellijDummyIdentifier;
    final Boolean htmlForTranslator;
    final String translationHtmlInlineTagPattern;
    final String translationAutolinkTagPattern;
    final Boolean spaceInLinkUrl;
    final Boolean parseJekyllMacroInLinkUrl;
    final String itemPrefixChars;
    final Boolean listsItemMarkerSpace;
    final Boolean listsOrderedItemDotOnly;
    final Boolean allowNameSpace;

    PatternTypeFlags(DataHolder options) {
      this.intellijDummyIdentifier = Parser.INTELLIJ_DUMMY_IDENTIFIER.get(options);
      this.htmlForTranslator = Parser.HTML_FOR_TRANSLATOR.get(options);
      this.translationHtmlInlineTagPattern =
          Parser.TRANSLATION_HTML_INLINE_TAG_PATTERN.get(options);
      this.translationAutolinkTagPattern = Parser.TRANSLATION_AUTOLINK_TAG_PATTERN.get(options);
      this.spaceInLinkUrl = Parser.SPACE_IN_LINK_URLS.get(options);
      this.parseJekyllMacroInLinkUrl = Parser.PARSE_JEKYLL_MACROS_IN_URLS.get(options);
      this.itemPrefixChars = Parser.LISTS_ITEM_PREFIX_CHARS.get(options);
      this.listsItemMarkerSpace = Parser.LISTS_ITEM_MARKER_SPACE.get(options);
      this.listsOrderedItemDotOnly = Parser.LISTS_ORDERED_ITEM_DOT_ONLY.get(options);
      this.allowNameSpace = Parser.HTML_ALLOW_NAME_SPACE.get(options);
    }

    public PatternTypeFlags(
        Boolean intellijDummyIdentifier,
        Boolean htmlForTranslator,
        String translationHtmlInlineTagPattern,
        String translationAutolinkTagPattern,
        Boolean spaceInLinkUrl,
        Boolean parseJekyllMacroInLinkUrl,
        String itemPrefixChars,
        Boolean listsItemMarkerSpace,
        Boolean listsOrderedItemDotOnly,
        Boolean allowNameSpace) {
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

    PatternTypeFlags withJekyllMacroSpaceInLinkUrl() {
      return new PatternTypeFlags(
          intellijDummyIdentifier,
          null,
          null,
          null,
          spaceInLinkUrl,
          parseJekyllMacroInLinkUrl,
          null,
          null,
          null,
          null);
    }

    PatternTypeFlags withHtmlTranslator() {
      return new PatternTypeFlags(
          intellijDummyIdentifier,
          htmlForTranslator,
          translationHtmlInlineTagPattern,
          translationAutolinkTagPattern,
          null,
          null,
          null,
          null,
          null,
          null);
    }

    PatternTypeFlags withItemPrefixChars() {
      return new PatternTypeFlags(
          null,
          null,
          null,
          null,
          null,
          null,
          itemPrefixChars,
          listsItemMarkerSpace,
          listsOrderedItemDotOnly,
          null);
    }

    /**
     * Compare where null entry equals any other value
     *
     * @param object other
     * @return true if equal
     */
    @Override
    public boolean equals(Object object) {
      if (this == object) {
        return true;
      }
      if (object == null || getClass() != object.getClass()) {
        return false;
      }

      PatternTypeFlags that = (PatternTypeFlags) object;

      if (intellijDummyIdentifier != null
          && !intellijDummyIdentifier.equals(that.intellijDummyIdentifier)) {
        return false;
      }
      if (htmlForTranslator != null && !htmlForTranslator.equals(that.htmlForTranslator)) {
        return false;
      }
      if (translationHtmlInlineTagPattern != null
          && !translationHtmlInlineTagPattern.equals(that.translationHtmlInlineTagPattern)) {
        return false;
      }
      if (translationAutolinkTagPattern != null
          && !translationAutolinkTagPattern.equals(that.translationAutolinkTagPattern)) {
        return false;
      }
      if (spaceInLinkUrl != null && !spaceInLinkUrl.equals(that.spaceInLinkUrl)) {
        return false;
      }
      if (parseJekyllMacroInLinkUrl != null
          && !parseJekyllMacroInLinkUrl.equals(that.parseJekyllMacroInLinkUrl)) {
        return false;
      }
      if (itemPrefixChars != null && !itemPrefixChars.equals(that.itemPrefixChars)) {
        return false;
      }
      if (listsItemMarkerSpace != null && !listsItemMarkerSpace.equals(that.listsItemMarkerSpace)) {
        return false;
      }
      if (allowNameSpace != null && !allowNameSpace.equals(that.allowNameSpace)) {
        return false;
      }

      return listsOrderedItemDotOnly == null
          || listsOrderedItemDotOnly.equals(that.listsOrderedItemDotOnly);
    }

    @Override
    public int hashCode() {
      int result = intellijDummyIdentifier != null ? intellijDummyIdentifier.hashCode() : 0;
      result = 31 * result + (htmlForTranslator != null ? htmlForTranslator.hashCode() : 0);
      result =
          31 * result
              + (translationHtmlInlineTagPattern != null
                  ? translationHtmlInlineTagPattern.hashCode()
                  : 0);
      result =
          31 * result
              + (translationAutolinkTagPattern != null
                  ? translationAutolinkTagPattern.hashCode()
                  : 0);
      result = 31 * result + (spaceInLinkUrl != null ? spaceInLinkUrl.hashCode() : 0);
      result =
          31 * result
              + (parseJekyllMacroInLinkUrl != null ? parseJekyllMacroInLinkUrl.hashCode() : 0);
      result = 31 * result + (itemPrefixChars != null ? itemPrefixChars.hashCode() : 0);
      result = 31 * result + (listsItemMarkerSpace != null ? listsItemMarkerSpace.hashCode() : 0);
      result =
          31 * result + (listsOrderedItemDotOnly != null ? listsOrderedItemDotOnly.hashCode() : 0);
      result = 31 * result + (allowNameSpace != null ? allowNameSpace.hashCode() : 0);
      return result;
    }
  }

  private static final Map<String, HashMap<PatternTypeFlags, Pattern>> cachedPatterns =
      new HashMap<>();

  private static Pattern getCachedPattern(
      String patternName,
      PatternTypeFlags cachedTypeFlags,
      Function<PatternTypeFlags, Pattern> factory) {
    Map<PatternTypeFlags, Pattern> patternMap =
        cachedPatterns.computeIfAbsent(patternName, key -> new HashMap<>());
    return patternMap.computeIfAbsent(cachedTypeFlags, factory);
  }

  public Parsing(DataHolder options) {
    this.codeBlockIndent =
        Parser.CODE_BLOCK_INDENT.get(options); // make sure this is consistent with lists settings
    PatternTypeFlags patternTypeFlags = new PatternTypeFlags(options);
    this.intellijDummyIdentifier = Boolean.TRUE.equals(patternTypeFlags.intellijDummyIdentifier);
    this.htmlForTranslator = Boolean.TRUE.equals(patternTypeFlags.htmlForTranslator);
    this.translationHtmlInlineTagPattern = patternTypeFlags.translationHtmlInlineTagPattern;
    this.translationAutolinkTagPattern = patternTypeFlags.translationAutolinkTagPattern;
    this.spaceInLinkUrl = Boolean.TRUE.equals(patternTypeFlags.spaceInLinkUrl);
    this.parseJekyllMacroInLinkUrl =
        Boolean.TRUE.equals(patternTypeFlags.parseJekyllMacroInLinkUrl);
    this.itemPrefixChars = patternTypeFlags.itemPrefixChars;
    this.listsItemMarkerSpace = Boolean.TRUE.equals(patternTypeFlags.listsItemMarkerSpace);
    this.listsOrderedItemDotOnly = Boolean.TRUE.equals(patternTypeFlags.listsOrderedItemDotOnly);
    this.allowNameSpace = Boolean.TRUE.equals(patternTypeFlags.allowNameSpace);

    if (intellijDummyIdentifier) {
      this.additionalChars = ST_ADDITIONAL_CHARS_IDI;
      this.excluded0ToSpace = ST_EXCLUDED_0_TO_SPACE_IDI;
      this.regChar = ST_REG_CHAR_IDI;
      this.regCharSp = ST_REG_CHAR_SP_IDI;
      this.inParensNosp = ST_IN_PARENS_NOSP_IDI;
      this.inParensWSp = ST_IN_PARENS_W_SP_IDI;
      this.inBracesWSp = ST_IN_BRACES_W_SP_IDI;
      this.declaration = ST_DECLARATION_IDI;
      this.opentag = allowNameSpace ? ST_NS_OPENTAG_IDI : ST_OPENTAG_IDI;
      this.closetag = allowNameSpace ? ST_NS_CLOSETAG_IDI : ST_CLOSETAG_IDI;
    } else {
      this.additionalChars = ST_ADDITIONAL_CHARS_NO_IDI;
      this.excluded0ToSpace = ST_EXCLUDED_0_TO_SPACE_NO_IDI;
      this.regChar = ST_REG_CHAR_NO_IDI;
      this.regCharSp = ST_REG_CHAR_SP_NO_IDI;
      this.inParensNosp = ST_IN_PARENS_NOSP_NO_IDI;
      this.inParensWSp = ST_IN_PARENS_W_SP_NO_IDI;
      this.inBracesWSp = ST_IN_BRACES_W_SP_NO_IDI;
      this.declaration = ST_DECLARATION_NO_IDI;
      this.opentag = allowNameSpace ? ST_NS_OPENTAG_NO_IDI : ST_OPENTAG_NO_IDI;
      this.closetag = allowNameSpace ? ST_NS_CLOSETAG_NO_IDI : ST_CLOSETAG_NO_IDI;
    }

    // init flag based patterns
    this.linkDestinationAngles =
        spaceInLinkUrl ? ST_LINK_DESTINATION_ANGLES_SPC : ST_LINK_DESTINATION_ANGLES_NO_SPC;
    this.entityHere = intellijDummyIdentifier ? ST_ENTITY_HERE_IDI : ST_ENTITY_HERE_NO_IDI;

    // init dynamic patterns
    synchronized (cachedPatterns) {
      this.linkDestination =
          getCachedPattern(
              "LINK_DESTINATION",
              patternTypeFlags.withJekyllMacroSpaceInLinkUrl(),
              entry ->
                  Pattern.compile(
                      "^(?:"
                          + (parseJekyllMacroInLinkUrl ? inBracesWSp + "|" : "")
                          + (spaceInLinkUrl ? "(?:" + regCharSp + ")|" : regChar + "|")
                          + ESCAPED_CHAR
                          + "|\\\\|"
                          + (spaceInLinkUrl ? inParensWSp : inParensNosp)
                          + ")*"));

      this.linkDestinationMatchedParens =
          getCachedPattern(
              "LINK_DESTINATION_MATCHED_PARENS",
              patternTypeFlags.withJekyllMacroSpaceInLinkUrl(),
              entry ->
                  Pattern.compile(
                      "^(?:"
                          + (parseJekyllMacroInLinkUrl ? inBracesWSp + "|" : "")
                          + (spaceInLinkUrl ? "(?:" + regCharSp + ")|" : regChar + "|")
                          + ESCAPED_CHAR
                          + "|\\\\|\\(|\\))*"));

      this.emailAutolink =
          getCachedPattern(
              "EMAIL_AUTOLINK",
              patternTypeFlags.withHtmlTranslator(),
              entry ->
                  Pattern.compile(
                      "^<("
                          + "(?:[a-zA-Z0-9"
                          + additionalChars
                          + ".!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9"
                          + additionalChars
                          + "](?:[a-zA-Z0-9"
                          + additionalChars
                          + "-]{0,61}[a-zA-Z0-9"
                          + additionalChars
                          + "])?(?:\\.[a-zA-Z0-9"
                          + additionalChars
                          + "](?:[a-zA-Z0-9"
                          + additionalChars
                          + "-]{0,61}[a-zA-Z0-9"
                          + additionalChars
                          + "])?)*)"
                          + (htmlForTranslator ? "|(?:" + translationAutolinkTagPattern + ")" : "")
                          + ")>"));

      this.autolink =
          getCachedPattern(
              "AUTOLINK",
              patternTypeFlags.withHtmlTranslator(),
              entry ->
                  Pattern.compile(
                      "^<("
                          + "(?:[a-zA-Z][a-zA-Z0-9"
                          + additionalChars
                          + ".+-]{1,31}:[^<>"
                          + excluded0ToSpace
                          + "]*)"
                          + (htmlForTranslator ? "|(?:" + translationAutolinkTagPattern + ")" : "")
                          + ")>"));

      this.wwwAutolink =
          getCachedPattern(
              "WWW_AUTOLINK",
              patternTypeFlags.withHtmlTranslator(),
              entry ->
                  Pattern.compile(
                      "^<("
                          + "(?:w"
                          + additionalChars
                          + "?){3,3}\\.[^<>"
                          + excluded0ToSpace
                          + "]*"
                          + (htmlForTranslator ? "|(?:" + translationAutolinkTagPattern + ")" : "")
                          + ")>"));

      this.htmlTag =
          getCachedPattern(
              "HTML_TAG",
              patternTypeFlags.withHtmlTranslator(),
              entry ->
                  Pattern.compile(
                      '^'
                          + ("(?:"
                              + opentag
                              + "|"
                              + closetag
                              + "|"
                              + HTMLCOMMENT
                              + "|"
                              + PROCESSINGINSTRUCTION
                              + "|"
                              + declaration
                              + "|"
                              + CDATA
                              + (htmlForTranslator
                                  ? "|<(?:"
                                      + translationHtmlInlineTagPattern
                                      + ")>|</(?:"
                                      + translationHtmlInlineTagPattern
                                      + ")>"
                                  : "")
                              + ")"),
                      Pattern.CASE_INSENSITIVE));

      this.listItemMarker =
          getCachedPattern(
              "LIST_ITEM_MARKER",
              patternTypeFlags.withItemPrefixChars(),
              entry -> {
                if (listsItemMarkerSpace) {
                  if (listsOrderedItemDotOnly) {
                    return Pattern.compile(
                        "^([\\Q" + itemPrefixChars + "\\E])(?=[ \t])|^(\\d{1,9})([.])(?=[ \t])");
                  }

                  return Pattern.compile(
                      "^([\\Q" + itemPrefixChars + "\\E])(?=[ \t])|^(\\d{1,9})([.)])(?=[ \t])");
                }

                if (listsOrderedItemDotOnly) {
                  return Pattern.compile(
                      "^([\\Q" + itemPrefixChars + "\\E])(?= |\t|$)|^(\\d{1,9})([.])(?= |\t|$)");
                }

                return Pattern.compile(
                    "^([\\Q" + itemPrefixChars + "\\E])(?= |\t|$)|^(\\d{1,9})([.)])(?= |\t|$)");
              });
    }
  }

  public static int columnsToNextTabStop(int column) {
    // Tab stop is 4
    return 4 - (column % 4);
  }

  public static int findLineBreak(CharSequence s, int startIndex) {
    return SequenceUtils.indexOfAny(s, CharPredicate.ANY_EOL, startIndex);
  }

  public static boolean isLetter(CharSequence s, int index) {
    int codePoint = Character.codePointAt(s, index);
    return Character.isLetter(codePoint);
  }

  public static boolean isSpaceOrTab(CharSequence s, int index) {
    return CharPredicate.SPACE_TAB.test(SequenceUtils.safeCharAt(s, index));
  }
}
