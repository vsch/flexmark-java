package com.vladsch.flexmark.formatter.internal;

import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.util.format.options.*;
import com.vladsch.flexmark.util.mappers.CharWidthProvider;
import com.vladsch.flexmark.util.options.DataHolder;

public class FormatterOptions {
    public final boolean itemContentIndent;

    public final ParserEmulationProfile emulationProfile;
    public final boolean setextHeaderEqualizeMarker;
    public final int formatFlags;
    public final int maxBlankLines;
    public final int maxTrailingBlankLines;
    public final int minSetextMarkerLength;
    public final DiscretionaryText spaceAfterAtxMarker;
    public final EqualizeTrailingMarker atxHeaderTrailingMarker;
    public final boolean blockQuoteBlankLines;
    public final BlockQuoteMarker blockQuoteMarkers;
    public final String thematicBreak;
    public final boolean indentedCodeMinimizeIndent;
    public final boolean fencedCodeMinimizeIndent;
    public final boolean fencedCodeMatchClosingMarker;
    public final boolean fencedCodeSpaceBeforeInfo;
    public final int fencedCodeMarkerLength;
    public final CodeFenceMarker fencedCodeMarkerType;
    public final boolean listAddBlankLineBefore;
    //public final boolean listAlignFirstLineText;
    //public final boolean listAlignChildBlocks;
    public final boolean listRenumberItems;
    public final ListBulletMarker listBulletMarker;
    public final ListNumberedMarker listNumberedMarker;
    public final ListSpacing listSpacing;
    public final ElementPlacement referencePlacement;
    public final ElementPlacementSort referenceSort;
    public final boolean keepImageLinksAtStart;
    public final boolean keepExplicitLinksAtStart;
    public final CharWidthProvider charWidthProvider;
    //public final TrailingSpaces keepTrailingSpaces;
    //public final TrailingSpaces codeKeepTrailingSpaces;

    public FormatterOptions(DataHolder options) {
        emulationProfile = Formatter.FORMATTER_EMULATION_PROFILE.getFrom(options);
        itemContentIndent = emulationProfile.family != ParserEmulationProfile.FIXED_INDENT;

        setextHeaderEqualizeMarker = Formatter.SETEXT_HEADER_EQUALIZE_MARKER.getFrom(options);
        formatFlags = Formatter.FORMAT_FLAGS.getFrom(options);
        maxBlankLines = Formatter.MAX_BLANK_LINES.getFrom(options);
        maxTrailingBlankLines = Formatter.MAX_TRAILING_BLANK_LINES.getFrom(options);
        spaceAfterAtxMarker = Formatter.SPACE_AFTER_ATX_MARKER.getFrom(options);
        atxHeaderTrailingMarker = Formatter.ATX_HEADER_TRAILING_MARKER.getFrom(options);
        minSetextMarkerLength = Parser.HEADING_SETEXT_MARKER_LENGTH.getFrom(options);
        thematicBreak = Formatter.THEMATIC_BREAK.getFrom(options);
        blockQuoteBlankLines = Formatter.BLOCK_QUOTE_BLANK_LINES.getFrom(options);
        blockQuoteMarkers = Formatter.BLOCK_QUOTE_MARKERS.getFrom(options);
        indentedCodeMinimizeIndent = Formatter.INDENTED_CODE_MINIMIZE_INDENT.getFrom(options);
        fencedCodeMinimizeIndent = Formatter.FENCED_CODE_MINIMIZE_INDENT.getFrom(options);
        fencedCodeMatchClosingMarker = Formatter.FENCED_CODE_MATCH_CLOSING_MARKER.getFrom(options);
        fencedCodeSpaceBeforeInfo = Formatter.FENCED_CODE_SPACE_BEFORE_INFO.getFrom(options);
        fencedCodeMarkerLength = Formatter.FENCED_CODE_MARKER_LENGTH.getFrom(options);
        fencedCodeMarkerType = Formatter.FENCED_CODE_MARKER_TYPE.getFrom(options);
        listAddBlankLineBefore = Formatter.LIST_ADD_BLANK_LINE_BEFORE.getFrom(options);
        //listAlignFirstLineText = Formatter.LIST_ALIGN_FIRST_LINE_TEXT.getFrom(options);
        //listAlignChildBlocks = Formatter.LIST_ALIGN_CHILD_BLOCKS.getFrom(options);
        listRenumberItems = Formatter.LIST_RENUMBER_ITEMS.getFrom(options);
        listBulletMarker = Formatter.LIST_BULLET_MARKER.getFrom(options);
        listNumberedMarker = Formatter.LIST_NUMBERED_MARKER.getFrom(options);
        listSpacing = Formatter.LIST_SPACING.getFrom(options);
        referencePlacement = Formatter.REFERENCE_PLACEMENT.getFrom(options);
        referenceSort = Formatter.REFERENCE_SORT.getFrom(options);
        keepImageLinksAtStart = Formatter.KEEP_IMAGE_LINKS_AT_START.getFrom(options);
        keepExplicitLinksAtStart = Formatter.KEEP_EXPLICIT_LINKS_AT_START.getFrom(options);
        charWidthProvider = Formatter.CHAR_WIDTH_PROVIDER.getFrom(options);
        //keepTrailingSpaces = Formatter.KEEP_TRAILING_SPACES.getFrom(options);
        //codeKeepTrailingSpaces = Formatter.CODE_KEEP_TRAILING_SPACES.getFrom(options);
    }
}
