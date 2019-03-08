package com.vladsch.flexmark.parser;

import com.vladsch.flexmark.util.options.DataHolder;

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

    public InlineParserOptions(DataHolder options) {
        matchLookaheadFirst = Parser.MATCH_NESTED_LINK_REFS_FIRST.getFrom(options);
        parseMultiLineImageUrls = Parser.PARSE_MULTI_LINE_IMAGE_URLS.getFrom(options);
        hardLineBreakLimit = Parser.HARD_LINE_BREAK_LIMIT.getFrom(options);
        spaceInLinkUrls = Parser.SPACE_IN_LINK_URLS.getFrom(options);
        spaceInLinkElements = Parser.SPACE_IN_LINK_ELEMENTS.getFrom(options);
        wwwAutoLinkElement = Parser.WWW_AUTO_LINK_ELEMENT.getFrom(options);
        codeSoftLineBreaks = Parser.CODE_SOFT_LINE_BREAKS.getFrom(options);
        inlineDelimiterDirectionalPunctuations = Parser.INLINE_DELIMITER_DIRECTIONAL_PUNCTUATIONS.getFrom(options);
        linksAllowMatchedParentheses = Parser.LINKS_ALLOW_MATCHED_PARENTHESES.getFrom(options);
    }
}
