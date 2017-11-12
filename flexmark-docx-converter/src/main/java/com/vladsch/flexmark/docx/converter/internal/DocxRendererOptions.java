package com.vladsch.flexmark.docx.converter.internal;

import com.vladsch.flexmark.util.options.DataHolder;

public class DocxRendererOptions {
    public final boolean escapeHtmlBlocks;
    public final boolean escapeHtmlCommentBlocks;
    public final boolean escapeInlineHtml;
    public final boolean escapeInlineHtmlComments;
    public final boolean percentEncodeUrls;
    public final boolean suppressHtmlBlocks;
    public final boolean suppressHtmlCommentBlocks;
    public final boolean suppressInlineHtml;
    public final boolean suppressInlineHtmlComments;
    public final int maxImageWidth;
    public final String tocInstruction;

    public DocxRendererOptions(DataHolder options) {
        escapeHtmlBlocks = DocxRenderer.ESCAPE_HTML_BLOCKS.getFrom(options);
        escapeHtmlCommentBlocks = DocxRenderer.ESCAPE_HTML_COMMENT_BLOCKS.getFrom(options);
        escapeInlineHtml = DocxRenderer.ESCAPE_INLINE_HTML.getFrom(options);
        escapeInlineHtmlComments = DocxRenderer.ESCAPE_INLINE_HTML_COMMENTS.getFrom(options);
        percentEncodeUrls = DocxRenderer.PERCENT_ENCODE_URLS.getFrom(options);
        suppressHtmlBlocks = DocxRenderer.SUPPRESS_HTML_BLOCKS.getFrom(options);
        suppressHtmlCommentBlocks = DocxRenderer.SUPPRESS_HTML_COMMENT_BLOCKS.getFrom(options);
        suppressInlineHtml = DocxRenderer.SUPPRESS_INLINE_HTML.getFrom(options);
        suppressInlineHtmlComments = DocxRenderer.SUPPRESS_INLINE_HTML_COMMENTS.getFrom(options);
        maxImageWidth = DocxRenderer.MAX_IMAGE_WIDTH.getFrom(options);
        tocInstruction = DocxRenderer.TOC_INSTRUCTION.getFrom(options);
    }
}
