package com.vladsch.flexmark.convert.html;

import com.vladsch.flexmark.util.Utils;
import com.vladsch.flexmark.util.format.TableFormatOptions;
import com.vladsch.flexmark.util.html.CellAlignment;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.MutableDataHolder;
import com.vladsch.flexmark.util.options.MutableDataSetter;

import java.util.Map;
import java.util.regex.Pattern;

@SuppressWarnings("WeakerAccess")
public class HtmlParserOptions implements MutableDataSetter {
    public boolean listContentIndent;
    public boolean setextHeadings;
    public boolean outputUnknownTags;
    public boolean typographicQuotes;
    public boolean typographicSmarts;
    public boolean outputAttributesIdAttr;
    public boolean wrapAutoLinks;
    public boolean extractAutoLinks;
    public boolean renderComments;
    public boolean dotOnlyNumericLists;
    public boolean preCodePreserveEmphasis;
    public boolean listsEndOnDoubleBlank;
    public boolean divAsParagraph;
    public boolean brAsParaBreaks;
    public boolean brAsExtraBlankLines;
    public boolean ignoreTableHeadingAfterRows;
    public char orderedListDelimiter;
    public char unorderedListDelimiter;
    public int definitionMarkerSpaces;
    public int minSetextHeadingMarkerLength;
    public String codeIndent;
    public String eolInTitleAttribute;
    public String nbspText;
    public String thematicBreak;
    public String outputAttributesNamesRegex;
    public Pattern outputAttributesNamesRegexPattern;
    public Map<Object, CellAlignment> tableCellAlignmentMap;
    public TableFormatOptions tableOptions;

    HtmlParserOptions() {
       this((DataHolder) null);
    }

    HtmlParserOptions(HtmlParserOptions other) {
        listContentIndent = other.listContentIndent;
        setextHeadings = other.setextHeadings;
        outputUnknownTags = other.outputUnknownTags;
        typographicQuotes = other.typographicQuotes;
        typographicSmarts = other.typographicSmarts;
        outputAttributesIdAttr = other.outputAttributesIdAttr;
        wrapAutoLinks = other.wrapAutoLinks;
        extractAutoLinks = other.extractAutoLinks;
        renderComments = other.renderComments;
        dotOnlyNumericLists = other.dotOnlyNumericLists;
        preCodePreserveEmphasis = other.preCodePreserveEmphasis;
        listsEndOnDoubleBlank = other.listsEndOnDoubleBlank;
        divAsParagraph = other.divAsParagraph;
        brAsParaBreaks = other.brAsParaBreaks;
        brAsExtraBlankLines = other.brAsExtraBlankLines;
        ignoreTableHeadingAfterRows = other.ignoreTableHeadingAfterRows;
        orderedListDelimiter = other.orderedListDelimiter;
        unorderedListDelimiter = other.unorderedListDelimiter;
        definitionMarkerSpaces = other.definitionMarkerSpaces;
        minSetextHeadingMarkerLength = other.minSetextHeadingMarkerLength;
        codeIndent = other.codeIndent;
        eolInTitleAttribute = other.eolInTitleAttribute;
        nbspText = other.nbspText;
        thematicBreak = other.thematicBreak;
        outputAttributesNamesRegex = other.outputAttributesNamesRegex;
        outputAttributesNamesRegexPattern = other.outputAttributesNamesRegexPattern;
        tableCellAlignmentMap = other.tableCellAlignmentMap;
        tableOptions = other.tableOptions;
    }

    HtmlParserOptions(DataHolder options) {
        listContentIndent = FlexmarkHtmlParser.LIST_CONTENT_INDENT.getFrom(options);
        setextHeadings = FlexmarkHtmlParser.SETEXT_HEADINGS.getFrom(options);
        outputUnknownTags = FlexmarkHtmlParser.OUTPUT_UNKNOWN_TAGS.getFrom(options);
        typographicQuotes = FlexmarkHtmlParser.TYPOGRAPHIC_QUOTES.getFrom(options);
        typographicSmarts = FlexmarkHtmlParser.TYPOGRAPHIC_SMARTS.getFrom(options);
        outputAttributesIdAttr = FlexmarkHtmlParser.OUTPUT_ATTRIBUTES_ID.getFrom(options);
        wrapAutoLinks = FlexmarkHtmlParser.WRAP_AUTO_LINKS.getFrom(options);
        extractAutoLinks = FlexmarkHtmlParser.EXTRACT_AUTO_LINKS.getFrom(options);
        renderComments = FlexmarkHtmlParser.RENDER_COMMENTS.getFrom(options);
        dotOnlyNumericLists = FlexmarkHtmlParser.DOT_ONLY_NUMERIC_LISTS.getFrom(options);
        preCodePreserveEmphasis = FlexmarkHtmlParser.PRE_CODE_PRESERVE_EMPHASIS.getFrom(options);
        listsEndOnDoubleBlank = FlexmarkHtmlParser.LISTS_END_ON_DOUBLE_BLANK.getFrom(options);
        divAsParagraph = FlexmarkHtmlParser.DIV_AS_PARAGRAPH.getFrom(options);
        brAsParaBreaks = FlexmarkHtmlParser.BR_AS_PARA_BREAKS.getFrom(options);
        brAsExtraBlankLines = FlexmarkHtmlParser.BR_AS_EXTRA_BLANK_LINES.getFrom(options);
        ignoreTableHeadingAfterRows = FlexmarkHtmlParser.IGNORE_TABLE_HEADING_AFTER_ROWS.getFrom(options);
        orderedListDelimiter = FlexmarkHtmlParser.ORDERED_LIST_DELIMITER.getFrom(options);
        unorderedListDelimiter = FlexmarkHtmlParser.UNORDERED_LIST_DELIMITER.getFrom(options);
        definitionMarkerSpaces = FlexmarkHtmlParser.DEFINITION_MARKER_SPACES.getFrom(options);
        minSetextHeadingMarkerLength = Utils.minLimit(FlexmarkHtmlParser.MIN_SETEXT_HEADING_MARKER_LENGTH.getFrom(options), 3);
        codeIndent = FlexmarkHtmlParser.CODE_INDENT.getFrom(options);
        eolInTitleAttribute = FlexmarkHtmlParser.EOL_IN_TITLE_ATTRIBUTE.getFrom(options);
        nbspText = FlexmarkHtmlParser.NBSP_TEXT.getFrom(options);
        thematicBreak = FlexmarkHtmlParser.THEMATIC_BREAK.getFrom(options);
        outputAttributesNamesRegex = FlexmarkHtmlParser.OUTPUT_ATTRIBUTES_NAMES_REGEX.getFrom(options);
        outputAttributesNamesRegexPattern = Pattern.compile(outputAttributesNamesRegex);
        tableCellAlignmentMap = FlexmarkHtmlParser.TABLE_CELL_ALIGNMENT_MAP.getFrom(options);
        tableOptions =  new TableFormatOptions(options);
    }

    @Override
    public MutableDataHolder setIn(final MutableDataHolder dataHolder) {
        dataHolder.set(FlexmarkHtmlParser.LIST_CONTENT_INDENT, listContentIndent);
        dataHolder.set(FlexmarkHtmlParser.SETEXT_HEADINGS, setextHeadings);
        dataHolder.set(FlexmarkHtmlParser.OUTPUT_UNKNOWN_TAGS, outputUnknownTags);
        dataHolder.set(FlexmarkHtmlParser.TYPOGRAPHIC_QUOTES, typographicQuotes);
        dataHolder.set(FlexmarkHtmlParser.TYPOGRAPHIC_SMARTS, typographicSmarts);
        dataHolder.set(FlexmarkHtmlParser.OUTPUT_ATTRIBUTES_ID, outputAttributesIdAttr);
        dataHolder.set(FlexmarkHtmlParser.WRAP_AUTO_LINKS, wrapAutoLinks);
        dataHolder.set(FlexmarkHtmlParser.EXTRACT_AUTO_LINKS, extractAutoLinks);
        dataHolder.set(FlexmarkHtmlParser.RENDER_COMMENTS, renderComments);
        dataHolder.set(FlexmarkHtmlParser.DOT_ONLY_NUMERIC_LISTS, dotOnlyNumericLists);
        dataHolder.set(FlexmarkHtmlParser.PRE_CODE_PRESERVE_EMPHASIS, preCodePreserveEmphasis);
        dataHolder.set(FlexmarkHtmlParser.LISTS_END_ON_DOUBLE_BLANK, listsEndOnDoubleBlank);
        dataHolder.set(FlexmarkHtmlParser.DIV_AS_PARAGRAPH, divAsParagraph);
        dataHolder.set(FlexmarkHtmlParser.BR_AS_PARA_BREAKS, brAsParaBreaks);
        dataHolder.set(FlexmarkHtmlParser.BR_AS_EXTRA_BLANK_LINES, brAsExtraBlankLines);
        dataHolder.set(FlexmarkHtmlParser.IGNORE_TABLE_HEADING_AFTER_ROWS, ignoreTableHeadingAfterRows);
        dataHolder.set(FlexmarkHtmlParser.ORDERED_LIST_DELIMITER, orderedListDelimiter);
        dataHolder.set(FlexmarkHtmlParser.UNORDERED_LIST_DELIMITER, unorderedListDelimiter);
        dataHolder.set(FlexmarkHtmlParser.DEFINITION_MARKER_SPACES, definitionMarkerSpaces);
        dataHolder.set(FlexmarkHtmlParser.MIN_SETEXT_HEADING_MARKER_LENGTH, minSetextHeadingMarkerLength);
        dataHolder.set(FlexmarkHtmlParser.CODE_INDENT, codeIndent);
        dataHolder.set(FlexmarkHtmlParser.EOL_IN_TITLE_ATTRIBUTE, eolInTitleAttribute);
        dataHolder.set(FlexmarkHtmlParser.NBSP_TEXT, nbspText);
        dataHolder.set(FlexmarkHtmlParser.THEMATIC_BREAK, thematicBreak);
        dataHolder.set(FlexmarkHtmlParser.OUTPUT_ATTRIBUTES_NAMES_REGEX, outputAttributesNamesRegex);
        dataHolder.set(FlexmarkHtmlParser.TABLE_CELL_ALIGNMENT_MAP, tableCellAlignmentMap);
        dataHolder.setFrom(tableOptions);
        return dataHolder;
    }
}
