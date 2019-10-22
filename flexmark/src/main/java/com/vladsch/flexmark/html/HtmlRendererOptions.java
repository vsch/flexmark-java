package com.vladsch.flexmark.html;

import com.vladsch.flexmark.util.Utils;
import com.vladsch.flexmark.util.data.DataHolder;

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
        softBreak = HtmlRenderer.SOFT_BREAK.get(options);
        isSoftBreakAllSpaces = Utils.isWhiteSpaceNoEOL(softBreak);
        hardBreak = HtmlRenderer.HARD_BREAK.get(options);
        strongEmphasisStyleHtmlOpen = HtmlRenderer.STRONG_EMPHASIS_STYLE_HTML_OPEN.get(options);
        strongEmphasisStyleHtmlClose = HtmlRenderer.STRONG_EMPHASIS_STYLE_HTML_CLOSE.get(options);
        emphasisStyleHtmlOpen = HtmlRenderer.EMPHASIS_STYLE_HTML_OPEN.get(options);
        emphasisStyleHtmlClose = HtmlRenderer.EMPHASIS_STYLE_HTML_CLOSE.get(options);
        codeStyleHtmlOpen = HtmlRenderer.CODE_STYLE_HTML_OPEN.get(options);
        codeStyleHtmlClose = HtmlRenderer.CODE_STYLE_HTML_CLOSE.get(options);
        escapeHtmlBlocks = HtmlRenderer.ESCAPE_HTML_BLOCKS.get(options);
        escapeHtmlCommentBlocks = HtmlRenderer.ESCAPE_HTML_COMMENT_BLOCKS.get(options);
        escapeInlineHtml = HtmlRenderer.ESCAPE_INLINE_HTML.get(options);
        escapeInlineHtmlComments = HtmlRenderer.ESCAPE_INLINE_HTML_COMMENTS.get(options);
        percentEncodeUrls = HtmlRenderer.PERCENT_ENCODE_URLS.get(options);
        indentSize = HtmlRenderer.INDENT_SIZE.get(options);
        suppressHtmlBlocks = HtmlRenderer.SUPPRESS_HTML_BLOCKS.get(options);
        suppressHtmlCommentBlocks = HtmlRenderer.SUPPRESS_HTML_COMMENT_BLOCKS.get(options);
        suppressInlineHtml = HtmlRenderer.SUPPRESS_INLINE_HTML.get(options);
        suppressInlineHtmlComments = HtmlRenderer.SUPPRESS_INLINE_HTML_COMMENTS.get(options);
        doNotRenderLinksInDocument = HtmlRenderer.DO_NOT_RENDER_LINKS.get(options);
        renderHeaderId = HtmlRenderer.RENDER_HEADER_ID.get(options);
        generateHeaderIds = HtmlRenderer.GENERATE_HEADER_ID.get(options);
        languageClassPrefix = HtmlRenderer.FENCED_CODE_LANGUAGE_CLASS_PREFIX.get(options);
        noLanguageClass = HtmlRenderer.FENCED_CODE_NO_LANGUAGE_CLASS.get(options);
        sourcePositionAttribute = HtmlRenderer.SOURCE_POSITION_ATTRIBUTE.get(options);
        sourcePositionParagraphLines = !sourcePositionAttribute.isEmpty() && HtmlRenderer.SOURCE_POSITION_PARAGRAPH_LINES.get(options);
        sourceWrapHtmlBlocks = !sourcePositionAttribute.isEmpty() && HtmlRenderer.SOURCE_WRAP_HTML_BLOCKS.get(options);
        formatFlags = HtmlRenderer.FORMAT_FLAGS.get(options);
        maxTrailingBlankLines = HtmlRenderer.MAX_TRAILING_BLANK_LINES.get(options);
        htmlBlockOpenTagEol = HtmlRenderer.HTML_BLOCK_OPEN_TAG_EOL.get(options);
        htmlBlockCloseTagEol = HtmlRenderer.HTML_BLOCK_CLOSE_TAG_EOL.get(options);
        unescapeHtmlEntities = HtmlRenderer.UNESCAPE_HTML_ENTITIES.get(options);
        noPTagsUseBr = HtmlRenderer.NO_P_TAGS_USE_BR.get(options);
        inlineCodeSpliceClass = HtmlRenderer.INLINE_CODE_SPLICE_CLASS.get(options);
        autolinkWwwPrefix = HtmlRenderer.AUTOLINK_WWW_PREFIX.get(options);

        String ignoreLinks = HtmlRenderer.SUPPRESSED_LINKS.get(options);
        suppressedLinks = ignoreLinks.isEmpty() ? null : Pattern.compile(ignoreLinks);
        //wrapTightItemParagraphInSpan = HtmlRenderer.WRAP_TIGHT_ITEM_PARAGRAPH_IN_SPAN.getFrom(options);
    }
}
