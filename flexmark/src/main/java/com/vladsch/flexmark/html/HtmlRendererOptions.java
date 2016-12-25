package com.vladsch.flexmark.html;

import com.vladsch.flexmark.util.options.DataHolder;

public class HtmlRendererOptions {
    public final String softBreak;
    public final String hardBreak;
    public final boolean escapeHtmlBlocks;
    public final boolean escapeHtmlCommentBlocks;
    public final boolean escapeInlineHtml;
    public final boolean escapeInlineHtmlComments;
    public final boolean percentEncodeUrls;
    public final int indentSize;
    public final boolean suppressHtmlBlocks;
    public final boolean suppressHtmlCommentBlocks;
    public final boolean suppressInlineHtml;
    public final boolean suppressInlineHtmlComments;
    public final boolean doNotRenderLinksInDocument;
    public final boolean renderHeaderId;
    public final boolean generateHeaderIds;
    public final String languageClassPrefix;
    public final String sourcePositionAttribute;
    public final boolean sourcePositionParagraphLines;
    public final boolean sourceWrapHtmlBlocks;
    public final int formatFlags;
    public final int maxTrailingBlankLines;
    //public final boolean sourceWrapInlineHtml;

    public HtmlRendererOptions(DataHolder options) {
        softBreak = options.get(HtmlRenderer.SOFT_BREAK);
        hardBreak = options.get(HtmlRenderer.HARD_BREAK);
        escapeHtmlBlocks = options.get(HtmlRenderer.ESCAPE_HTML_BLOCKS);
        escapeHtmlCommentBlocks = options.get(HtmlRenderer.ESCAPE_HTML_COMMENT_BLOCKS);
        escapeInlineHtml = options.get(HtmlRenderer.ESCAPE_INLINE_HTML);
        escapeInlineHtmlComments = options.get(HtmlRenderer.ESCAPE_INLINE_HTML_COMMENTS);
        percentEncodeUrls = options.get(HtmlRenderer.PERCENT_ENCODE_URLS);
        indentSize = options.get(HtmlRenderer.INDENT_SIZE);
        suppressHtmlBlocks = options.get(HtmlRenderer.SUPPRESS_HTML_BLOCKS);
        suppressHtmlCommentBlocks = options.get(HtmlRenderer.SUPPRESS_HTML_COMMENT_BLOCKS);
        suppressInlineHtml = options.get(HtmlRenderer.SUPPRESS_INLINE_HTML);
        suppressInlineHtmlComments = options.get(HtmlRenderer.SUPPRESS_INLINE_HTML_COMMENTS);
        doNotRenderLinksInDocument = options.get(HtmlRenderer.DO_NOT_RENDER_LINKS);
        renderHeaderId = options.get(HtmlRenderer.RENDER_HEADER_ID);
        generateHeaderIds = options.get(HtmlRenderer.GENERATE_HEADER_ID);
        languageClassPrefix = options.get(HtmlRenderer.FENCED_CODE_LANGUAGE_CLASS_PREFIX);
        sourcePositionAttribute = options.get(HtmlRenderer.SOURCE_POSITION_ATTRIBUTE);
        sourcePositionParagraphLines = !sourcePositionAttribute.isEmpty() && options.get(HtmlRenderer.SOURCE_POSITION_PARAGRAPH_LINES);
        sourceWrapHtmlBlocks = !sourcePositionAttribute.isEmpty() && options.get(HtmlRenderer.SOURCE_WRAP_HTML_BLOCKS);
        //sourceWrapInlineHtml = !sourcePositionAttribute.isEmpty() && options.get(HtmlRenderer.SOURCE_WRAP_INLINE_HTML);
        formatFlags = options.get(HtmlRenderer.FORMAT_FLAGS);
        maxTrailingBlankLines = options.get(HtmlRenderer.MAX_TRAILING_BLANK_LINES);
    }
}
