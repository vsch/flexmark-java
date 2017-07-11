package com.vladsch.flexmark.parser;

import com.vladsch.flexmark.util.options.DataHolder;

public class InlineParserOptions {
    public final boolean matchLookaheadFirst;
    //public final boolean parseInlineAnchorLinks;
    public final boolean parseMultiLineImageUrls;
    //public final boolean parseGitHubIssueMarker;
    public final boolean hardLineBreakLimit;
    public final boolean spaceInLinkUrls;
    public final boolean spaceInLinkElements;
    public final boolean codeSoftLineBreaks;

    public InlineParserOptions(DataHolder options) {
        matchLookaheadFirst = Parser.MATCH_NESTED_LINK_REFS_FIRST.getFrom(options);
        //parseInlineAnchorLinks = Parser.PARSE_INLINE_ANCHOR_LINKS.getFrom(options);
        parseMultiLineImageUrls = Parser.PARSE_MULTI_LINE_IMAGE_URLS.getFrom(options);
        hardLineBreakLimit = Parser.HARD_LINE_BREAK_LIMIT.getFrom(options);
        spaceInLinkUrls = Parser.SPACE_IN_LINK_URLS.getFrom(options);
        spaceInLinkElements = Parser.SPACE_IN_LINK_ELEMENTS.getFrom(options);
        codeSoftLineBreaks = Parser.CODE_SOFT_LINE_BREAKS.getFrom(options);
        //parseGitHubIssueMarker = PARSE_GITHUB_ISSUE_MARKER.getFrom(options);
    }
}
