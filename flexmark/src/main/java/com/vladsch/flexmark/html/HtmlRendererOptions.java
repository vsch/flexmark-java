package com.vladsch.flexmark.html;

import com.vladsch.flexmark.util.Utils;
import com.vladsch.flexmark.util.options.DataHolder;

import java.util.regex.Pattern;

public class HtmlRendererOptions {
    public final String softBreak;
    public final boolean isSoftBreakAllSpaces;
    public final String hardBreak;
    public final String strongEmphasisStyleHtmlOpen;
    public final String strongEmphasisStyleHtmlClose;
    public final String emphasisStyleHtmlOpen;
    public final String emphasisStyleHtmlClose;
    public final String codeStyleHtmlOpen;
    public final String codeStyleHtmlClose;
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
    public final String noLanguageClass;
    public final String sourcePositionAttribute;
    public final String inlineCodeSpliceClass;
    public final boolean sourcePositionParagraphLines;
    public final boolean sourceWrapHtmlBlocks;
    public final int formatFlags;
    public final int maxTrailingBlankLines;
    public final boolean htmlBlockOpenTagEol;
    public final boolean htmlBlockCloseTagEol;
    public final boolean unescapeHtmlEntities;
    public final boolean noPTagsUseBr;
    //public final boolean wrapTightItemParagraphInSpan;
    public final String autolinkWwwPrefix;
    public final Pattern suppressedLinks;

    public HtmlRendererOptions(DataHolder options) {
        softBreak = HtmlRenderer.SOFT_BREAK.getFrom(options);
        isSoftBreakAllSpaces = Utils.isWhiteSpaceNoEOL(softBreak);
        hardBreak = HtmlRenderer.HARD_BREAK.getFrom(options);
        strongEmphasisStyleHtmlOpen = HtmlRenderer.STRONG_EMPHASIS_STYLE_HTML_OPEN.getFrom(options);
        strongEmphasisStyleHtmlClose = HtmlRenderer.STRONG_EMPHASIS_STYLE_HTML_CLOSE.getFrom(options);
        emphasisStyleHtmlOpen = HtmlRenderer.EMPHASIS_STYLE_HTML_OPEN.getFrom(options);
        emphasisStyleHtmlClose = HtmlRenderer.EMPHASIS_STYLE_HTML_CLOSE.getFrom(options);
        codeStyleHtmlOpen = HtmlRenderer.CODE_STYLE_HTML_OPEN.getFrom(options);
        codeStyleHtmlClose = HtmlRenderer.CODE_STYLE_HTML_CLOSE.getFrom(options);
        escapeHtmlBlocks = HtmlRenderer.ESCAPE_HTML_BLOCKS.getFrom(options);
        escapeHtmlCommentBlocks = HtmlRenderer.ESCAPE_HTML_COMMENT_BLOCKS.getFrom(options);
        escapeInlineHtml = HtmlRenderer.ESCAPE_INLINE_HTML.getFrom(options);
        escapeInlineHtmlComments = HtmlRenderer.ESCAPE_INLINE_HTML_COMMENTS.getFrom(options);
        percentEncodeUrls = HtmlRenderer.PERCENT_ENCODE_URLS.getFrom(options);
        indentSize = HtmlRenderer.INDENT_SIZE.getFrom(options);
        suppressHtmlBlocks = HtmlRenderer.SUPPRESS_HTML_BLOCKS.getFrom(options);
        suppressHtmlCommentBlocks = HtmlRenderer.SUPPRESS_HTML_COMMENT_BLOCKS.getFrom(options);
        suppressInlineHtml = HtmlRenderer.SUPPRESS_INLINE_HTML.getFrom(options);
        suppressInlineHtmlComments = HtmlRenderer.SUPPRESS_INLINE_HTML_COMMENTS.getFrom(options);
        doNotRenderLinksInDocument = HtmlRenderer.DO_NOT_RENDER_LINKS.getFrom(options);
        renderHeaderId = HtmlRenderer.RENDER_HEADER_ID.getFrom(options);
        generateHeaderIds = HtmlRenderer.GENERATE_HEADER_ID.getFrom(options);
        languageClassPrefix = HtmlRenderer.FENCED_CODE_LANGUAGE_CLASS_PREFIX.getFrom(options);
        noLanguageClass = HtmlRenderer.FENCED_CODE_NO_LANGUAGE_CLASS.getFrom(options);
        sourcePositionAttribute = HtmlRenderer.SOURCE_POSITION_ATTRIBUTE.getFrom(options);
        sourcePositionParagraphLines = !sourcePositionAttribute.isEmpty() && HtmlRenderer.SOURCE_POSITION_PARAGRAPH_LINES.getFrom(options);
        sourceWrapHtmlBlocks = !sourcePositionAttribute.isEmpty() && HtmlRenderer.SOURCE_WRAP_HTML_BLOCKS.getFrom(options);
        formatFlags = HtmlRenderer.FORMAT_FLAGS.getFrom(options);
        maxTrailingBlankLines = HtmlRenderer.MAX_TRAILING_BLANK_LINES.getFrom(options);
        htmlBlockOpenTagEol = HtmlRenderer.HTML_BLOCK_OPEN_TAG_EOL.getFrom(options);
        htmlBlockCloseTagEol = HtmlRenderer.HTML_BLOCK_CLOSE_TAG_EOL.getFrom(options);
        unescapeHtmlEntities = HtmlRenderer.UNESCAPE_HTML_ENTITIES.getFrom(options);
        noPTagsUseBr = HtmlRenderer.NO_P_TAGS_USE_BR.getFrom(options);
        inlineCodeSpliceClass = HtmlRenderer.INLINE_CODE_SPLICE_CLASS.getFrom(options);
        autolinkWwwPrefix = HtmlRenderer.AUTOLINK_WWW_PREFIX.getFrom(options);

        String ignoreLinks = HtmlRenderer.SUPPRESSED_LINKS.getFrom(options);
        suppressedLinks = ignoreLinks.isEmpty() ? null : Pattern.compile(ignoreLinks);
        //wrapTightItemParagraphInSpan = HtmlRenderer.WRAP_TIGHT_ITEM_PARAGRAPH_IN_SPAN.getFrom(options);
    }
}
