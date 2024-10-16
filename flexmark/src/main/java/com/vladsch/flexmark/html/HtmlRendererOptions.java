package com.vladsch.flexmark.html;

import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.misc.CharPredicate;
import com.vladsch.flexmark.util.misc.Utils;
import java.util.Map;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HtmlRendererOptions {
  public final @NotNull String softBreak;
  public final boolean isSoftBreakAllSpaces;
  public final @NotNull String hardBreak;
  public final @Nullable String strongEmphasisStyleHtmlOpen;
  public final @Nullable String strongEmphasisStyleHtmlClose;
  public final @Nullable String emphasisStyleHtmlOpen;
  public final @Nullable String emphasisStyleHtmlClose;
  public final @Nullable String codeStyleHtmlOpen;
  public final @Nullable String codeStyleHtmlClose;
  public final boolean escapeHtmlBlocks;
  public final boolean escapeHtmlCommentBlocks;
  public final boolean escapeInlineHtml;
  public final boolean escapeInlineHtmlComments;
  final boolean percentEncodeUrls;
  final int indentSize;
  public final boolean suppressHtmlBlocks;
  public final boolean suppressHtmlCommentBlocks;
  public final boolean suppressInlineHtml;
  public final boolean suppressInlineHtmlComments;
  final boolean doNotRenderLinksInDocument;
  public final boolean renderHeaderId;
  final boolean generateHeaderIds;
  public final @NotNull String languageClassPrefix;
  public final @NotNull Map<String, String> languageClassMap;
  private final @NotNull String languageDelimiters;
  public final CharPredicate languageDelimiterSet;
  public final @NotNull String noLanguageClass;
  final @NotNull String sourcePositionAttribute;
  public final @Nullable String inlineCodeSpliceClass;
  public final boolean sourcePositionParagraphLines;
  public final boolean sourceWrapHtmlBlocks;
  final int formatFlags;
  final int maxTrailingBlankLines;
  final int maxBlankLines;
  final boolean htmlBlockOpenTagEol;
  public final boolean htmlBlockCloseTagEol;
  public final boolean unescapeHtmlEntities;
  public final boolean noPTagsUseBr;
  public final @NotNull String autolinkWwwPrefix;
  public final @Nullable Pattern suppressedLinks;

  HtmlRendererOptions(DataHolder options) {
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
    sourcePositionParagraphLines =
        !sourcePositionAttribute.isEmpty()
            && HtmlRenderer.SOURCE_POSITION_PARAGRAPH_LINES.get(options);
    sourceWrapHtmlBlocks =
        !sourcePositionAttribute.isEmpty() && HtmlRenderer.SOURCE_WRAP_HTML_BLOCKS.get(options);
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
  }
}
