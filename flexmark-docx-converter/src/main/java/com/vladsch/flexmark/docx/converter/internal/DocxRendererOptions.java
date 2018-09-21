package com.vladsch.flexmark.docx.converter.internal;

import com.vladsch.flexmark.util.options.DataHolder;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.StyleDefinitionsPart;
import org.docx4j.wml.Style;
import org.docx4j.wml.Styles;

import java.util.HashMap;
import java.util.List;

public class DocxRendererOptions {
    static private final HashMap<String, String> standardStyleNames = new HashMap<>();
    static {
        standardStyleNames.put("AsideBlock", "AsideBlock");
        standardStyleNames.put("BodyText", "Body Text");
        standardStyleNames.put("Emphasis", "Emphasis");
        standardStyleNames.put("EndnoteReference", "endnote reference");
        standardStyleNames.put("Footer", "footer");
        standardStyleNames.put("Footnote", "Footnote");
        standardStyleNames.put("FootnoteReference", "footnote reference");
        standardStyleNames.put("FootnoteText", "footnote text");
        standardStyleNames.put("Header", "header");
        standardStyleNames.put("Heading1", "heading 1");
        standardStyleNames.put("Heading2", "heading 2");
        standardStyleNames.put("Heading3", "heading 3");
        standardStyleNames.put("Heading4", "heading 4");
        standardStyleNames.put("Heading5", "heading 5");
        standardStyleNames.put("Heading6", "heading 6");
        standardStyleNames.put("HorizontalLine", "Horizontal Line");
        standardStyleNames.put("Hyperlink", "Hyperlink");
        standardStyleNames.put("Normal", "Normal");
        standardStyleNames.put("ParagraphTextBody", "Paragraph Text Body");
        standardStyleNames.put("PreformattedText", "Preformatted Text");
        standardStyleNames.put("Quotations", "Quotations");
        standardStyleNames.put("SourceText", "SourceText");
        standardStyleNames.put("Strikethrough", "Strikethrough");
        standardStyleNames.put("StrongEmphasis", "Strong Emphasis");
        standardStyleNames.put("Subscript", "Subscript");
        standardStyleNames.put("Superscript", "Superscript");
        standardStyleNames.put("TableCaption", "Table Caption");
        standardStyleNames.put("TableContents", "Table Contents");
        standardStyleNames.put("TableGrid", "Table Grid");
        standardStyleNames.put("TableHeading", "Table Heading");
        standardStyleNames.put("Underlined", "Underlined");
    }

    public final boolean escapeHtmlBlocks;
    public final boolean escapeHtmlCommentBlocks;
    public final boolean escapeInlineHtml;
    public final boolean escapeInlineHtmlComments;
    public final boolean percentEncodeUrls;
    public final boolean suppressHtmlBlocks;
    public final boolean suppressHtmlCommentBlocks;
    public final boolean suppressInlineHtml;
    public final boolean suppressInlineHtmlComments;
    public final boolean tocGenerate;
    public final boolean logImageProcessing;
    public final boolean noCharacterStyles;
    public final boolean prefixWwwLinks;
    public final int maxImageWidth;
    public final String tocInstruction;
    public final String codeHighlightShading;
    public final String localHyperlinkSuffix;
    public final String localHyperlinkMissingHighlight;
    public final String localHyperlinkMissingFormat;
    public final boolean errorsToStdErr;
    public final double docEmojiImageVertOffset;
    public final double docEmojiImageVertSize;
    public final String errorSourceFile;

    public final String ASIDE_BLOCK_STYLE;
    public final String BLOCK_QUOTE_STYLE;
    public final String BOLD_STYLE;
    public final String DEFAULT_STYLE;
    public final String ENDNOTE_ANCHOR_STYLE;
    public final String FOOTER;
    public final String FOOTNOTE_ANCHOR_STYLE;
    public final String FOOTNOTE_STYLE;
    public final String FOOTNOTE_TEXT;
    public final String HEADER;
    public final String HEADING_1;
    public final String HEADING_2;
    public final String HEADING_3;
    public final String HEADING_4;
    public final String HEADING_5;
    public final String HEADING_6;
    public final String HORIZONTAL_LINE_STYLE;
    public final String HYPERLINK_STYLE;
    public final String INLINE_CODE_STYLE;
    public final String INS_STYLE;
    public final String ITALIC_STYLE;
    public final String LOOSE_PARAGRAPH_STYLE;
    public final String PREFORMATTED_TEXT_STYLE;
    public final String STRIKE_THROUGH_STYLE;
    public final String SUBSCRIPT_STYLE;
    public final String SUPERSCRIPT_STYLE;
    public final String TABLE_CAPTION;
    public final String TABLE_CONTENTS;
    public final String TABLE_GRID;
    public final String TABLE_HEADING;
    public final String TIGHT_PARAGRAPH_STYLE;
    public final String[] HEADINGS;

    final HashMap<String, String> styleIdToStyleName;
    final HashMap<String, String> nameToStyleStyleId;
    public boolean isResolved;

    public DocxRendererOptions(DataHolder options) {
        this(options, null);
    }

    public DocxRendererOptions(DataHolder options, WordprocessingMLPackage out) {
        if (out == null) {
            styleIdToStyleName = null;
            nameToStyleStyleId = null;
            isResolved = false;
        } else {
            styleIdToStyleName = new HashMap<>();
            nameToStyleStyleId = new HashMap<>();
            isResolved = true;
            initializeStyleNames(out);
        }

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
        tocGenerate = DocxRenderer.TOC_GENERATE.getFrom(options);
        logImageProcessing = DocxRenderer.LOG_IMAGE_PROCESSING.getFrom(options);
        noCharacterStyles = DocxRenderer.NO_CHARACTER_STYLES.getFrom(options);
        codeHighlightShading = DocxRenderer.CODE_HIGHLIGHT_SHADING.getFrom(options);
        localHyperlinkSuffix = DocxRenderer.LOCAL_HYPERLINK_SUFFIX.getFrom(options);
        localHyperlinkMissingHighlight = DocxRenderer.LOCAL_HYPERLINK_MISSING_HIGHLIGHT.getFrom(options);
        localHyperlinkMissingFormat = DocxRenderer.LOCAL_HYPERLINK_MISSING_FORMAT.getFrom(options);
        errorsToStdErr = DocxRenderer.ERRORS_TO_STDERR.getFrom(options);
        docEmojiImageVertOffset = DocxRenderer.DOC_EMOJI_IMAGE_VERT_OFFSET.getFrom(options);
        docEmojiImageVertSize = DocxRenderer.DOC_EMOJI_IMAGE_VERT_SIZE.getFrom(options);
        errorSourceFile = DocxRenderer.ERROR_SOURCE_FILE.getFrom(options);
        prefixWwwLinks = DocxRenderer.PREFIX_WWW_LINKS.getFrom(options);

        ASIDE_BLOCK_STYLE = resolveStyleId(DocxRenderer.ASIDE_BLOCK_STYLE.getFrom(options), !isResolved);
        BLOCK_QUOTE_STYLE = resolveStyleId(DocxRenderer.BLOCK_QUOTE_STYLE.getFrom(options), !isResolved);
        BOLD_STYLE = resolveStyleId(DocxRenderer.BOLD_STYLE.getFrom(options), !isResolved);
        DEFAULT_STYLE = resolveStyleId(DocxRenderer.DEFAULT_STYLE.getFrom(options), !isResolved);
        ENDNOTE_ANCHOR_STYLE = resolveStyleId(DocxRenderer.ENDNOTE_ANCHOR_STYLE.getFrom(options), !isResolved);
        FOOTER = resolveStyleId(DocxRenderer.FOOTER.getFrom(options), !isResolved);
        FOOTNOTE_ANCHOR_STYLE = resolveStyleId(DocxRenderer.FOOTNOTE_ANCHOR_STYLE.getFrom(options), !isResolved);
        FOOTNOTE_STYLE = resolveStyleId(DocxRenderer.FOOTNOTE_STYLE.getFrom(options), !isResolved);
        FOOTNOTE_TEXT = resolveStyleId(DocxRenderer.FOOTNOTE_TEXT.getFrom(options), !isResolved);
        HEADER = resolveStyleId(DocxRenderer.HEADER.getFrom(options), !isResolved);
        HEADING_1 = resolveStyleId(DocxRenderer.HEADING_1.getFrom(options), !isResolved);
        HEADING_2 = resolveStyleId(DocxRenderer.HEADING_2.getFrom(options), !isResolved);
        HEADING_3 = resolveStyleId(DocxRenderer.HEADING_3.getFrom(options), !isResolved);
        HEADING_4 = resolveStyleId(DocxRenderer.HEADING_4.getFrom(options), !isResolved);
        HEADING_5 = resolveStyleId(DocxRenderer.HEADING_5.getFrom(options), !isResolved);
        HEADING_6 = resolveStyleId(DocxRenderer.HEADING_6.getFrom(options), !isResolved);
        HORIZONTAL_LINE_STYLE = resolveStyleId(DocxRenderer.HORIZONTAL_LINE_STYLE.getFrom(options), !isResolved);
        HYPERLINK_STYLE = resolveStyleId(DocxRenderer.HYPERLINK_STYLE.getFrom(options), !isResolved);
        INLINE_CODE_STYLE = resolveStyleId(DocxRenderer.INLINE_CODE_STYLE.getFrom(options), !isResolved);
        INS_STYLE = resolveStyleId(DocxRenderer.INS_STYLE.getFrom(options), !isResolved);
        ITALIC_STYLE = resolveStyleId(DocxRenderer.ITALIC_STYLE.getFrom(options), !isResolved);
        LOOSE_PARAGRAPH_STYLE = resolveStyleId(DocxRenderer.LOOSE_PARAGRAPH_STYLE.getFrom(options), !isResolved);
        PREFORMATTED_TEXT_STYLE = resolveStyleId(DocxRenderer.PREFORMATTED_TEXT_STYLE.getFrom(options), !isResolved);
        STRIKE_THROUGH_STYLE = resolveStyleId(DocxRenderer.STRIKE_THROUGH_STYLE.getFrom(options), !isResolved);
        SUBSCRIPT_STYLE = resolveStyleId(DocxRenderer.SUBSCRIPT_STYLE.getFrom(options), !isResolved);
        SUPERSCRIPT_STYLE = resolveStyleId(DocxRenderer.SUPERSCRIPT_STYLE.getFrom(options), !isResolved);
        TABLE_CAPTION = resolveStyleId(DocxRenderer.TABLE_CAPTION.getFrom(options), !isResolved);
        TABLE_CONTENTS = resolveStyleId(DocxRenderer.TABLE_CONTENTS.getFrom(options), !isResolved);
        TABLE_GRID = resolveStyleId(DocxRenderer.TABLE_GRID.getFrom(options), !isResolved);
        TABLE_HEADING = resolveStyleId(DocxRenderer.TABLE_HEADING.getFrom(options), !isResolved);
        TIGHT_PARAGRAPH_STYLE = resolveStyleId(DocxRenderer.TIGHT_PARAGRAPH_STYLE.getFrom(options), !isResolved);

        HEADINGS = new String[] {
                HEADING_1,
                HEADING_2,
                HEADING_3,
                HEADING_4,
                HEADING_5,
                HEADING_6,
        };
    }

    public DocxRendererOptions(DocxRendererOptions other, WordprocessingMLPackage out) {
        styleIdToStyleName = new HashMap<>();
        nameToStyleStyleId = new HashMap<>();
        isResolved = true;
        initializeStyleNames(out);

        escapeHtmlBlocks = other.escapeHtmlBlocks;
        escapeHtmlCommentBlocks = other.escapeHtmlCommentBlocks;
        escapeInlineHtml = other.escapeInlineHtml;
        escapeInlineHtmlComments = other.escapeInlineHtmlComments;
        percentEncodeUrls = other.percentEncodeUrls;
        suppressHtmlBlocks = other.suppressHtmlBlocks;
        suppressHtmlCommentBlocks = other.suppressHtmlCommentBlocks;
        suppressInlineHtml = other.suppressInlineHtml;
        suppressInlineHtmlComments = other.suppressInlineHtmlComments;
        maxImageWidth = other.maxImageWidth;
        tocInstruction = other.tocInstruction;
        tocGenerate = other.tocGenerate;
        logImageProcessing = other.logImageProcessing;
        noCharacterStyles = other.noCharacterStyles;
        codeHighlightShading = other.codeHighlightShading;
        localHyperlinkSuffix = other.localHyperlinkSuffix;
        localHyperlinkMissingHighlight = other.localHyperlinkMissingHighlight;
        localHyperlinkMissingFormat = other.localHyperlinkMissingFormat;
        errorsToStdErr = other.errorsToStdErr;
        docEmojiImageVertOffset = other.docEmojiImageVertOffset;
        docEmojiImageVertSize = other.docEmojiImageVertSize;
        errorSourceFile = other.errorSourceFile;
        prefixWwwLinks = other.prefixWwwLinks;

        ASIDE_BLOCK_STYLE = resolveStyleId(other.ASIDE_BLOCK_STYLE, other.isResolved);
        BLOCK_QUOTE_STYLE = resolveStyleId(other.BLOCK_QUOTE_STYLE, other.isResolved);
        BOLD_STYLE = resolveStyleId(other.BOLD_STYLE, other.isResolved);
        DEFAULT_STYLE = resolveStyleId(other.DEFAULT_STYLE, other.isResolved);
        ENDNOTE_ANCHOR_STYLE = resolveStyleId(other.ENDNOTE_ANCHOR_STYLE, other.isResolved);
        FOOTER = resolveStyleId(other.FOOTER, other.isResolved);
        FOOTNOTE_ANCHOR_STYLE = resolveStyleId(other.FOOTNOTE_ANCHOR_STYLE, other.isResolved);
        FOOTNOTE_STYLE = resolveStyleId(other.FOOTNOTE_STYLE, other.isResolved);
        FOOTNOTE_TEXT = resolveStyleId(other.FOOTNOTE_TEXT, other.isResolved);
        HEADER = resolveStyleId(other.HEADER, other.isResolved);
        HEADING_1 = resolveStyleId(other.HEADING_1, other.isResolved);
        HEADING_2 = resolveStyleId(other.HEADING_2, other.isResolved);
        HEADING_3 = resolveStyleId(other.HEADING_3, other.isResolved);
        HEADING_4 = resolveStyleId(other.HEADING_4, other.isResolved);
        HEADING_5 = resolveStyleId(other.HEADING_5, other.isResolved);
        HEADING_6 = resolveStyleId(other.HEADING_6, other.isResolved);
        HORIZONTAL_LINE_STYLE = resolveStyleId(other.HORIZONTAL_LINE_STYLE, other.isResolved);
        HYPERLINK_STYLE = resolveStyleId(other.HYPERLINK_STYLE, other.isResolved);
        INLINE_CODE_STYLE = resolveStyleId(other.INLINE_CODE_STYLE, other.isResolved);
        INS_STYLE = resolveStyleId(other.INS_STYLE, other.isResolved);
        ITALIC_STYLE = resolveStyleId(other.ITALIC_STYLE, other.isResolved);
        LOOSE_PARAGRAPH_STYLE = resolveStyleId(other.LOOSE_PARAGRAPH_STYLE, other.isResolved);
        PREFORMATTED_TEXT_STYLE = resolveStyleId(other.PREFORMATTED_TEXT_STYLE, other.isResolved);
        STRIKE_THROUGH_STYLE = resolveStyleId(other.STRIKE_THROUGH_STYLE, other.isResolved);
        SUBSCRIPT_STYLE = resolveStyleId(other.SUBSCRIPT_STYLE, other.isResolved);
        SUPERSCRIPT_STYLE = resolveStyleId(other.SUPERSCRIPT_STYLE, other.isResolved);
        TABLE_CAPTION = resolveStyleId(other.TABLE_CAPTION, other.isResolved);
        TABLE_CONTENTS = resolveStyleId(other.TABLE_CONTENTS, other.isResolved);
        TABLE_GRID = resolveStyleId(other.TABLE_GRID, other.isResolved);
        TABLE_HEADING = resolveStyleId(other.TABLE_HEADING, other.isResolved);
        TIGHT_PARAGRAPH_STYLE = resolveStyleId(other.TIGHT_PARAGRAPH_STYLE, other.isResolved);

        HEADINGS = other.isResolved ? other.HEADINGS :
                new String[] {
                        HEADING_1,
                        HEADING_2,
                        HEADING_3,
                        HEADING_4,
                        HEADING_5,
                        HEADING_6,
                };
    }

    private String resolveStyleId(String styleId, boolean isResolved) {
        if (!isResolved && !styleIdToStyleName.containsKey(styleId)) {
            String standardStyleName = standardStyleNames.get(styleId);
            if (standardStyleName != null) {
                String mappedStyleId = nameToStyleStyleId.get(standardStyleName);
                if (mappedStyleId != null) {
                    return mappedStyleId;
                }
            }
        }
        return styleId;
    }

    private void initializeStyleNames(WordprocessingMLPackage out) {
        final StyleDefinitionsPart styleDefinitionsPart = out.getMainDocumentPart().getStyleDefinitionsPart();
        try {
            final Styles contents = styleDefinitionsPart.getContents();
            final List<Style> styles = contents.getStyle();
            for (Style style : styles) {
                final String styleId = style.getStyleId();
                final String styleName = style.getName().getVal();
                styleIdToStyleName.put(styleId, styleName);
                nameToStyleStyleId.put(styleName, styleId);
            }
        } catch (Docx4JException e) {
            e.printStackTrace();
        }
    }
}
