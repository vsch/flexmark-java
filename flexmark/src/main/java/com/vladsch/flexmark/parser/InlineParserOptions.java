package com.vladsch.flexmark.parser;

import com.vladsch.flexmark.util.data.DataHolder;

public class InlineParserOptions {
    final public boolean matchLookaheadFirst;
    final public boolean parseMultiLineImageUrls;
    final public boolean hardLineBreakLimit;
    final public boolean spaceInLinkUrls;
    final public boolean spaceInLinkElements;
    final public boolean codeSoftLineBreaks;
    final public boolean inlineDelimiterDirectionalPunctuations;
    final public boolean linksAllowMatchedParentheses;
    final public boolean wwwAutoLinkElement;
    final public boolean intellijDummyIdentifier;
    final public boolean parseJekyllMacrosInUrls;
    final public boolean useHardcodedLinkAddressParser;
    final public boolean linkTextPriorityOverLinkRef;

    public InlineParserOptions(DataHolder options) {
        matchLookaheadFirst = Parser.MATCH_NESTED_LINK_REFS_FIRST.get(options);
        parseMultiLineImageUrls = Parser.PARSE_MULTI_LINE_IMAGE_URLS.get(options);
        hardLineBreakLimit = Parser.HARD_LINE_BREAK_LIMIT.get(options);
        spaceInLinkUrls = Parser.SPACE_IN_LINK_URLS.get(options);
        spaceInLinkElements = Parser.SPACE_IN_LINK_ELEMENTS.get(options);
        wwwAutoLinkElement = Parser.WWW_AUTO_LINK_ELEMENT.get(options);
        intellijDummyIdentifier = Parser.INTELLIJ_DUMMY_IDENTIFIER.get(options);
        parseJekyllMacrosInUrls = Parser.PARSE_JEKYLL_MACROS_IN_URLS.get(options);
        useHardcodedLinkAddressParser = Parser.USE_HARDCODED_LINK_ADDRESS_PARSER.get(options);
        codeSoftLineBreaks = Parser.CODE_SOFT_LINE_BREAKS.get(options);
        inlineDelimiterDirectionalPunctuations = Parser.INLINE_DELIMITER_DIRECTIONAL_PUNCTUATIONS.get(options);
        linksAllowMatchedParentheses = Parser.LINKS_ALLOW_MATCHED_PARENTHESES.get(options);
        linkTextPriorityOverLinkRef = Parser.LINK_TEXT_PRIORITY_OVER_LINK_REF.get(options);
    }
}
