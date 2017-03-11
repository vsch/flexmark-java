package com.vladsch.flexmark.convert.html;

import com.vladsch.flexmark.util.Utils;
import com.vladsch.flexmark.util.format.TableFormatOptions;
import com.vladsch.flexmark.util.html.CellAlignment;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.MutableDataHolder;
import com.vladsch.flexmark.util.options.MutableDataSetter;

import java.util.Map;

@SuppressWarnings("WeakerAccess")
public class HtmlParserOptions implements MutableDataSetter {
    public boolean listContentIndent;
    public boolean setextHeadings;
    public boolean outputUnknownTags;
    public boolean typographicQuotes;
    public boolean typographicSmarts;
    public boolean wrapAutoLinks;
    public boolean extractAutoLinks;
    public boolean renderComments;
    public boolean dotOnlyNumericLists;
    public char orderedListDelimiter;
    public char unorderedListDelimiter;
    public int definitionMarkerSpaces;
    public int minSetextHeadingMarkerLength;
    public String codeIndent;
    public String eolInTitleAttribute;
    public String nbspText;
    public String thematicBreak;
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
        wrapAutoLinks = other.wrapAutoLinks;
        extractAutoLinks = other.extractAutoLinks;
        renderComments = other.renderComments;
        dotOnlyNumericLists = other.dotOnlyNumericLists;
        orderedListDelimiter = other.orderedListDelimiter;
        unorderedListDelimiter = other.unorderedListDelimiter;
        definitionMarkerSpaces = other.definitionMarkerSpaces;
        minSetextHeadingMarkerLength = other.minSetextHeadingMarkerLength;
        codeIndent = other.codeIndent;
        eolInTitleAttribute = other.eolInTitleAttribute;
        nbspText = other.nbspText;
        thematicBreak = other.thematicBreak;
        tableCellAlignmentMap = other.tableCellAlignmentMap;
        tableOptions = other.tableOptions;
    }

    HtmlParserOptions(DataHolder options) {
        listContentIndent = FlexmarkHtmlParser.LIST_CONTENT_INDENT.getFrom(options);
        setextHeadings = FlexmarkHtmlParser.SETEXT_HEADINGS.getFrom(options);
        outputUnknownTags = FlexmarkHtmlParser.OUTPUT_UNKNOWN_TAGS.getFrom(options);
        typographicQuotes = FlexmarkHtmlParser.TYPOGRAPHIC_QUOTES.getFrom(options);
        typographicSmarts = FlexmarkHtmlParser.TYPOGRAPHIC_SMARTS.getFrom(options);
        wrapAutoLinks = FlexmarkHtmlParser.WRAP_AUTO_LINKS.getFrom(options);
        extractAutoLinks = FlexmarkHtmlParser.EXTRACT_AUTO_LINKS.getFrom(options);
        renderComments = FlexmarkHtmlParser.RENDER_COMMENTS.getFrom(options);
        dotOnlyNumericLists = FlexmarkHtmlParser.DOT_ONLY_NUMERIC_LISTS.getFrom(options);
        orderedListDelimiter = FlexmarkHtmlParser.ORDERED_LIST_DELIMITER.getFrom(options);
        unorderedListDelimiter = FlexmarkHtmlParser.UNORDERED_LIST_DELIMITER.getFrom(options);
        definitionMarkerSpaces = FlexmarkHtmlParser.DEFINITION_MARKER_SPACES.getFrom(options);
        minSetextHeadingMarkerLength = Utils.minLimit(FlexmarkHtmlParser.MIN_SETEXT_HEADING_MARKER_LENGTH.getFrom(options), 3);
        codeIndent = FlexmarkHtmlParser.CODE_INDENT.getFrom(options);
        eolInTitleAttribute = FlexmarkHtmlParser.EOL_IN_TITLE_ATTRIBUTE.getFrom(options);
        nbspText = FlexmarkHtmlParser.NBSP_TEXT.getFrom(options);
        thematicBreak = FlexmarkHtmlParser.THEMATIC_BREAK.getFrom(options);
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
        dataHolder.set(FlexmarkHtmlParser.WRAP_AUTO_LINKS, wrapAutoLinks);
        dataHolder.set(FlexmarkHtmlParser.EXTRACT_AUTO_LINKS, extractAutoLinks);
        dataHolder.set(FlexmarkHtmlParser.RENDER_COMMENTS, renderComments);
        dataHolder.set(FlexmarkHtmlParser.DOT_ONLY_NUMERIC_LISTS, dotOnlyNumericLists);
        dataHolder.set(FlexmarkHtmlParser.ORDERED_LIST_DELIMITER, orderedListDelimiter);
        dataHolder.set(FlexmarkHtmlParser.UNORDERED_LIST_DELIMITER, unorderedListDelimiter);
        dataHolder.set(FlexmarkHtmlParser.DEFINITION_MARKER_SPACES, definitionMarkerSpaces);
        dataHolder.set(FlexmarkHtmlParser.MIN_SETEXT_HEADING_MARKER_LENGTH, minSetextHeadingMarkerLength);
        dataHolder.set(FlexmarkHtmlParser.CODE_INDENT, codeIndent);
        dataHolder.set(FlexmarkHtmlParser.EOL_IN_TITLE_ATTRIBUTE, eolInTitleAttribute);
        dataHolder.set(FlexmarkHtmlParser.NBSP_TEXT, nbspText);
        dataHolder.set(FlexmarkHtmlParser.THEMATIC_BREAK, thematicBreak);
        dataHolder.set(FlexmarkHtmlParser.TABLE_CELL_ALIGNMENT_MAP, tableCellAlignmentMap);
        dataHolder.setFrom(tableOptions);
        return dataHolder;
    }
}
