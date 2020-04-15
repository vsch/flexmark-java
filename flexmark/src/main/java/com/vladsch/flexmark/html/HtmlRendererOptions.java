package com.vladsch.flexmark.html;

import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.misc.CharPredicate;
import com.vladsch.flexmark.util.misc.Utils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.regex.Pattern;

public class HtmlRendererOptions {
    final public @NotNull String softBreak;
    final public boolean isSoftBreakAllSpaces;
    final public @NotNull String hardBreak;
    final public @Nullable String strongEmphasisStyleHtmlOpen;
    final public @Nullable String strongEmphasisStyleHtmlClose;
    final public @Nullable String emphasisStyleHtmlOpen;
    final public @Nullable String emphasisStyleHtmlClose;
    final public @Nullable String codeStyleHtmlOpen;
    final public @Nullable String codeStyleHtmlClose;
    final public boolean escapeHtmlBlocks;
    final public boolean escapeHtmlCommentBlocks;
    final public boolean escapeInlineHtml;
    final public boolean escapeInlineHtmlComments;
    final public boolean percentEncodeUrls;
    final public int indentSize;
    final public boolean suppressHtmlBlocks;
    final public boolean suppressHtmlCommentBlocks;
    final public boolean suppressInlineHtml;
    final public boolean suppressInlineHtmlComments;
    final public boolean doNotRenderLinksInDocument;
    final public boolean renderHeaderId;
    final public boolean generateHeaderIds;
    final public @NotNull String languageClassPrefix;
    final public @NotNull HashMap<String, String> languageClassMap;
    final public @NotNull String languageDelimiters;
    final public CharPredicate languageDelimiterSet;
    final public @NotNull String noLanguageClass;
    final public @NotNull String sourcePositionAttribute;
    final public @Nullable String inlineCodeSpliceClass;
    final public boolean sourcePositionParagraphLines;
    final public boolean sourceWrapHtmlBlocks;
    final public int formatFlags;
    final public int maxTrailingBlankLines;
    final public int maxBlankLines;
    final public boolean htmlBlockOpenTagEol;
    final public boolean htmlBlockCloseTagEol;
    final public boolean unescapeHtmlEntities;
    final public boolean noPTagsUseBr;
    final public @NotNull String autolinkWwwPrefix;
    final public @Nullable Pattern suppressedLinks;

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
        languageClassMap = HtmlRenderer.FENCED_CODE_LANGUAGE_CLASS_MAP.get(options);
        languageDelimiters = HtmlRenderer.FENCED_CODE_LANGUAGE_DELIMITERS.get(options);
        languageDelimiterSet = CharPredicate.anyOf(languageDelimiters);
        noLanguageClass = HtmlRenderer.FENCED_CODE_NO_LANGUAGE_CLASS.get(options);
        sourcePositionAttribute = HtmlRenderer.SOURCE_POSITION_ATTRIBUTE.get(options);
        sourcePositionParagraphLines = !sourcePositionAttribute.isEmpty() && HtmlRenderer.SOURCE_POSITION_PARAGRAPH_LINES.get(options);
        sourceWrapHtmlBlocks = !sourcePositionAttribute.isEmpty() && HtmlRenderer.SOURCE_WRAP_HTML_BLOCKS.get(options);
        formatFlags = HtmlRenderer.FORMAT_FLAGS.get(options);
        maxTrailingBlankLines = HtmlRenderer.MAX_TRAILING_BLANK_LINES.get(options);
        maxBlankLines = HtmlRenderer.MAX_BLANK_LINES.get(options);
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
