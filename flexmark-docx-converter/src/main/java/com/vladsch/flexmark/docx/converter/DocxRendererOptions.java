package com.vladsch.flexmark.docx.converter;

import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.SharedDataKeys;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.StyleDefinitionsPart;
import org.docx4j.wml.Style;
import org.docx4j.wml.Styles;

import java.util.HashMap;
import java.util.List;

public class DocxRendererOptions {
    final private static HashMap<String, String> standardStyleNames = new HashMap<>();
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

    final public boolean escapeHtmlBlocks;
    final public boolean escapeHtmlCommentBlocks;
    final public boolean escapeInlineHtml;
    final public boolean escapeInlineHtmlComments;
    final public boolean percentEncodeUrls;
    final public boolean suppressHtmlBlocks;
    final public boolean suppressHtmlCommentBlocks;
    final public boolean suppressInlineHtml;
    final public boolean suppressInlineHtmlComments;
    final public boolean tocGenerate;
    final public boolean logImageProcessing;
    final public boolean noCharacterStyles;
    final public boolean prefixWwwLinks;
    final public int maxImageWidth;
    final public String tocInstruction;
    final public String codeHighlightShading;
    final public String localHyperlinkSuffix;
    final public String localHyperlinkMissingHighlight;
    final public String localHyperlinkMissingFormat;
    final public boolean errorsToStdErr;
    final public double docEmojiImageVertOffset;
    final public double docEmojiImageVertSize;
    final public String errorSourceFile;
    final public String formControls;
    final public boolean runningTests;

    final public String ASIDE_BLOCK_STYLE;
    final public String BLOCK_QUOTE_STYLE;
    final public String BOLD_STYLE;
    final public String DEFAULT_STYLE;
    final public String ENDNOTE_ANCHOR_STYLE;
    final public String FOOTER;
    final public String FOOTNOTE_ANCHOR_STYLE;
    final public String FOOTNOTE_STYLE;
    final public String FOOTNOTE_TEXT;
    final public String HEADER;
    final public String HEADING_1;
    final public String HEADING_2;
    final public String HEADING_3;
    final public String HEADING_4;
    final public String HEADING_5;
    final public String HEADING_6;
    final public String HORIZONTAL_LINE_STYLE;
    final public String HYPERLINK_STYLE;
    final public String INLINE_CODE_STYLE;
    final public String INS_STYLE;
    final public String ITALIC_STYLE;
    final public String LOOSE_PARAGRAPH_STYLE;
    final public String PREFORMATTED_TEXT_STYLE;
    final public String STRIKE_THROUGH_STYLE;
    final public String SUBSCRIPT_STYLE;
    final public String SUPERSCRIPT_STYLE;
    final public String TABLE_CAPTION;
    final public String TABLE_CONTENTS;
    final public String TABLE_GRID;
    final public String TABLE_HEADING;
    final public String TIGHT_PARAGRAPH_STYLE;
    final public String BULLET_LIST_STYLE;
    final public String NUMBERED_LIST_STYLE;
    final public String PARAGRAPH_BULLET_LIST_STYLE;
    final public String PARAGRAPH_NUMBERED_LIST_STYLE;
    final public String[] HEADINGS;

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

        escapeHtmlBlocks = DocxRenderer.ESCAPE_HTML_BLOCKS.get(options);
        escapeHtmlCommentBlocks = DocxRenderer.ESCAPE_HTML_COMMENT_BLOCKS.get(options);
        escapeInlineHtml = DocxRenderer.ESCAPE_INLINE_HTML.get(options);
        escapeInlineHtmlComments = DocxRenderer.ESCAPE_INLINE_HTML_COMMENTS.get(options);
        percentEncodeUrls = DocxRenderer.PERCENT_ENCODE_URLS.get(options);
        suppressHtmlBlocks = DocxRenderer.SUPPRESS_HTML_BLOCKS.get(options);
        suppressHtmlCommentBlocks = DocxRenderer.SUPPRESS_HTML_COMMENT_BLOCKS.get(options);
        suppressInlineHtml = DocxRenderer.SUPPRESS_INLINE_HTML.get(options);
        suppressInlineHtmlComments = DocxRenderer.SUPPRESS_INLINE_HTML_COMMENTS.get(options);
        maxImageWidth = DocxRenderer.MAX_IMAGE_WIDTH.get(options);
        tocInstruction = DocxRenderer.TOC_INSTRUCTION.get(options);
        tocGenerate = DocxRenderer.TOC_GENERATE.get(options);
        logImageProcessing = DocxRenderer.LOG_IMAGE_PROCESSING.get(options);
        noCharacterStyles = DocxRenderer.NO_CHARACTER_STYLES.get(options);
        formControls = DocxRenderer.FORM_CONTROLS.get(options).trim();
        runningTests = SharedDataKeys.RUNNING_TESTS.get(options);
        codeHighlightShading = DocxRenderer.CODE_HIGHLIGHT_SHADING.get(options);
        localHyperlinkSuffix = DocxRenderer.LOCAL_HYPERLINK_SUFFIX.get(options);
        localHyperlinkMissingHighlight = DocxRenderer.LOCAL_HYPERLINK_MISSING_HIGHLIGHT.get(options);
        localHyperlinkMissingFormat = DocxRenderer.LOCAL_HYPERLINK_MISSING_FORMAT.get(options);
        errorsToStdErr = DocxRenderer.ERRORS_TO_STDERR.get(options);
        docEmojiImageVertOffset = DocxRenderer.DOC_EMOJI_IMAGE_VERT_OFFSET.get(options);
        docEmojiImageVertSize = DocxRenderer.DOC_EMOJI_IMAGE_VERT_SIZE.get(options);
        errorSourceFile = DocxRenderer.ERROR_SOURCE_FILE.get(options);
        prefixWwwLinks = DocxRenderer.PREFIX_WWW_LINKS.get(options);

        ASIDE_BLOCK_STYLE = resolveStyleId(DocxRenderer.ASIDE_BLOCK_STYLE.get(options), !isResolved);
        BLOCK_QUOTE_STYLE = resolveStyleId(DocxRenderer.BLOCK_QUOTE_STYLE.get(options), !isResolved);
        BOLD_STYLE = resolveStyleId(DocxRenderer.BOLD_STYLE.get(options), !isResolved);
        DEFAULT_STYLE = resolveStyleId(DocxRenderer.DEFAULT_STYLE.get(options), !isResolved);
        ENDNOTE_ANCHOR_STYLE = resolveStyleId(DocxRenderer.ENDNOTE_ANCHOR_STYLE.get(options), !isResolved);
        FOOTER = resolveStyleId(DocxRenderer.FOOTER.get(options), !isResolved);
        FOOTNOTE_ANCHOR_STYLE = resolveStyleId(DocxRenderer.FOOTNOTE_ANCHOR_STYLE.get(options), !isResolved);
        FOOTNOTE_STYLE = resolveStyleId(DocxRenderer.FOOTNOTE_STYLE.get(options), !isResolved);
        FOOTNOTE_TEXT = resolveStyleId(DocxRenderer.FOOTNOTE_TEXT.get(options), !isResolved);
        HEADER = resolveStyleId(DocxRenderer.HEADER.get(options), !isResolved);
        HEADING_1 = resolveStyleId(DocxRenderer.HEADING_1.get(options), !isResolved);
        HEADING_2 = resolveStyleId(DocxRenderer.HEADING_2.get(options), !isResolved);
        HEADING_3 = resolveStyleId(DocxRenderer.HEADING_3.get(options), !isResolved);
        HEADING_4 = resolveStyleId(DocxRenderer.HEADING_4.get(options), !isResolved);
        HEADING_5 = resolveStyleId(DocxRenderer.HEADING_5.get(options), !isResolved);
        HEADING_6 = resolveStyleId(DocxRenderer.HEADING_6.get(options), !isResolved);
        HORIZONTAL_LINE_STYLE = resolveStyleId(DocxRenderer.HORIZONTAL_LINE_STYLE.get(options), !isResolved);
        HYPERLINK_STYLE = resolveStyleId(DocxRenderer.HYPERLINK_STYLE.get(options), !isResolved);
        INLINE_CODE_STYLE = resolveStyleId(DocxRenderer.INLINE_CODE_STYLE.get(options), !isResolved);
        INS_STYLE = resolveStyleId(DocxRenderer.INS_STYLE.get(options), !isResolved);
        ITALIC_STYLE = resolveStyleId(DocxRenderer.ITALIC_STYLE.get(options), !isResolved);
        LOOSE_PARAGRAPH_STYLE = resolveStyleId(DocxRenderer.LOOSE_PARAGRAPH_STYLE.get(options), !isResolved);
        PREFORMATTED_TEXT_STYLE = resolveStyleId(DocxRenderer.PREFORMATTED_TEXT_STYLE.get(options), !isResolved);
        STRIKE_THROUGH_STYLE = resolveStyleId(DocxRenderer.STRIKE_THROUGH_STYLE.get(options), !isResolved);
        SUBSCRIPT_STYLE = resolveStyleId(DocxRenderer.SUBSCRIPT_STYLE.get(options), !isResolved);
        SUPERSCRIPT_STYLE = resolveStyleId(DocxRenderer.SUPERSCRIPT_STYLE.get(options), !isResolved);
        TABLE_CAPTION = resolveStyleId(DocxRenderer.TABLE_CAPTION.get(options), !isResolved);
        TABLE_CONTENTS = resolveStyleId(DocxRenderer.TABLE_CONTENTS.get(options), !isResolved);
        TABLE_GRID = resolveStyleId(DocxRenderer.TABLE_GRID.get(options), !isResolved);
        TABLE_HEADING = resolveStyleId(DocxRenderer.TABLE_HEADING.get(options), !isResolved);
        TIGHT_PARAGRAPH_STYLE = resolveStyleId(DocxRenderer.TIGHT_PARAGRAPH_STYLE.get(options), !isResolved);
        BULLET_LIST_STYLE = resolveStyleId(DocxRenderer.BULLET_LIST_STYLE.get(options), !isResolved);
        NUMBERED_LIST_STYLE = resolveStyleId(DocxRenderer.NUMBERED_LIST_STYLE.get(options), !isResolved);
        PARAGRAPH_BULLET_LIST_STYLE = resolveStyleId(DocxRenderer.PARAGRAPH_BULLET_LIST_STYLE.get(options), !isResolved);
        PARAGRAPH_NUMBERED_LIST_STYLE = resolveStyleId(DocxRenderer.PARAGRAPH_NUMBERED_LIST_STYLE.get(options), !isResolved);

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
        formControls = other.formControls;
        runningTests = other.runningTests;

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
        BULLET_LIST_STYLE = resolveStyleId(other.BULLET_LIST_STYLE, other.isResolved);
        NUMBERED_LIST_STYLE = resolveStyleId(other.NUMBERED_LIST_STYLE, other.isResolved);
        PARAGRAPH_BULLET_LIST_STYLE = resolveStyleId(other.PARAGRAPH_BULLET_LIST_STYLE, other.isResolved);
        PARAGRAPH_NUMBERED_LIST_STYLE = resolveStyleId(other.PARAGRAPH_NUMBERED_LIST_STYLE, other.isResolved);

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
