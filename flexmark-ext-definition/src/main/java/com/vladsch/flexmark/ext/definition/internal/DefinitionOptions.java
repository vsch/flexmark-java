package com.vladsch.flexmark.ext.definition.internal;

import com.vladsch.flexmark.ext.definition.DefinitionExtension;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.util.data.DataHolder;

@SuppressWarnings("WeakerAccess")
class DefinitionOptions {
    public final int markerSpaces;
    public final boolean tildeMarker;
    public final boolean colonMarker;
    public final ParserEmulationProfile myParserEmulationProfile;
    public final boolean autoLoose;
    public final boolean autoLooseOneLevelLists;
    public final boolean looseOnPrevLooseItem;
    public final boolean looseWhenHasLooseSubItem;
    public final boolean looseWhenHasTrailingBlankLine;
    public final boolean looseWhenBlankFollowsItemParagraph;
    public final boolean doubleBlankLineBreaksList;
    public final int codeIndent;
    public final int itemIndent;
    public final int newItemCodeIndent;

    public DefinitionOptions(DataHolder options) {
        markerSpaces = DefinitionExtension.MARKER_SPACES.getFrom(options);
        tildeMarker = DefinitionExtension.TILDE_MARKER.getFrom(options);
        colonMarker = DefinitionExtension.COLON_MARKER.getFrom(options);
        myParserEmulationProfile = Parser.PARSER_EMULATION_PROFILE.getFrom(options);
        autoLoose = Parser.LISTS_AUTO_LOOSE.getFrom(options);
        autoLooseOneLevelLists = Parser.LISTS_AUTO_LOOSE_ONE_LEVEL_LISTS.getFrom(options);
        looseOnPrevLooseItem = Parser.LISTS_LOOSE_WHEN_PREV_HAS_TRAILING_BLANK_LINE.getFrom(options);
        looseWhenBlankFollowsItemParagraph = Parser.LISTS_LOOSE_WHEN_BLANK_LINE_FOLLOWS_ITEM_PARAGRAPH.getFrom(options);
        looseWhenHasLooseSubItem = Parser.LISTS_LOOSE_WHEN_HAS_LOOSE_SUB_ITEM.getFrom(options);
        looseWhenHasTrailingBlankLine = Parser.LISTS_LOOSE_WHEN_HAS_TRAILING_BLANK_LINE.getFrom(options);
        codeIndent = Parser.LISTS_CODE_INDENT.getFrom(options);
        itemIndent = Parser.LISTS_ITEM_INDENT.getFrom(options);
        newItemCodeIndent = Parser.LISTS_NEW_ITEM_CODE_INDENT.getFrom(options);
        doubleBlankLineBreaksList = DefinitionExtension.DOUBLE_BLANK_LINE_BREAKS_LIST.getFrom(options);
    }
}
