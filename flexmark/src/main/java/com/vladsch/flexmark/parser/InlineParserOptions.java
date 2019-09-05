package com.vladsch.flexmark.parser;

import com.vladsch.flexmark.util.data.DataHolder;

public class InlineParserOptions {
    public final boolean matchLookaheadFirst;
    public final boolean parseMultiLineImageUrls;
    public final boolean hardLineBreakLimit;
    public final boolean spaceInLinkUrls;
    public final boolean spaceInLinkElements;
    public final boolean codeSoftLineBreaks;
    public final boolean inlineDelimiterDirectionalPunctuations;
    public final boolean linksAllowMatchedParentheses;
    public final boolean wwwAutoLinkElement;
    public final boolean intellijDummyIdentifier;
    public final boolean parseJekyllMacrosInUrls;
    public final boolean useHardcodedLinkAddressParser;

    public InlineParserOptions(DataHolder options) {
        matchLookaheadFirst = Parser.MATCH_NESTED_LINK_REFS_FIRST.getFrom(options);
        parseMultiLineImageUrls = Parser.PARSE_MULTI_LINE_IMAGE_URLS.getFrom(options);
        hardLineBreakLimit = Parser.HARD_LINE_BREAK_LIMIT.getFrom(options);
        spaceInLinkUrls = Parser.SPACE_IN_LINK_URLS.getFrom(options);
        spaceInLinkElements = Parser.SPACE_IN_LINK_ELEMENTS.getFrom(options);
        wwwAutoLinkElement = Parser.WWW_AUTO_LINK_ELEMENT.getFrom(options);
        intellijDummyIdentifier = Parser.INTELLIJ_DUMMY_IDENTIFIER.getFrom(options);
        parseJekyllMacrosInUrls = Parser.PARSE_JEKYLL_MACROS_IN_URLS.getFrom(options);
        useHardcodedLinkAddressParser = Parser.USE_HARDCODED_LINK_ADDRESS_PARSER.getFrom(options);
        codeSoftLineBreaks = Parser.CODE_SOFT_LINE_BREAKS.getFrom(options);
        inlineDelimiterDirectionalPunctuations = Parser.INLINE_DELIMITER_DIRECTIONAL_PUNCTUATIONS.getFrom(options);
        linksAllowMatchedParentheses = Parser.LINKS_ALLOW_MATCHED_PARENTHESES.getFrom(options);
    }
}
