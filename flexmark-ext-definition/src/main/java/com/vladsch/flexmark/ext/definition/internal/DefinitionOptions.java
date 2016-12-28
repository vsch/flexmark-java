package com.vladsch.flexmark.ext.definition.internal;

import com.vladsch.flexmark.ext.definition.DefinitionExtension;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationFamily;
import com.vladsch.flexmark.util.options.DataHolder;

@SuppressWarnings("WeakerAccess")
class DefinitionOptions {
    public final int markerSpaces;
    public final ParserEmulationFamily parserEmulationFamily;
    public final boolean autoLoose;
    public final boolean autoLooseOneLevelLists;
    public final boolean delimiterMismatchToNewList;
    public final boolean looseOnPrevLooseItem;
    public final boolean looseWhenHasLooseSubItem;
    public final boolean looseWhenHasTrailingBlankLine;
    public final boolean looseWhenBlankFollowsItemParagraph;
    public final int codeIndent;
    public final int itemIndent;
    public final int newItemCodeIndent;

    public DefinitionOptions(DataHolder options) {
        markerSpaces = options.get(DefinitionExtension.MARKER_SPACES);
        parserEmulationFamily = Parser.PARSER_EMULATION_FAMILY.getFrom(options);
        autoLoose = Parser.LISTS_AUTO_LOOSE.getFrom(options);
        autoLooseOneLevelLists = Parser.LISTS_AUTO_LOOSE_ONE_LEVEL_LISTS.getFrom(options);
        delimiterMismatchToNewList = Parser.LISTS_DELIMITER_MISMATCH_TO_NEW_LIST.getFrom(options);
        looseOnPrevLooseItem = Parser.LISTS_LOOSE_ON_PREV_LOOSE_ITEM.getFrom(options);
        looseWhenBlankFollowsItemParagraph = Parser.LISTS_LOOSE_WHEN_BLANK_FOLLOWS_ITEM_PARAGRAPH.getFrom(options);
        looseWhenHasLooseSubItem = Parser.LISTS_LOOSE_WHEN_HAS_LOOSE_SUB_ITEM.getFrom(options);
        looseWhenHasTrailingBlankLine = Parser.LISTS_LOOSE_WHEN_HAS_TRAILING_BLANK_LINE.getFrom(options);
        codeIndent = Parser.LISTS_CODE_INDENT.getFrom(options);
        itemIndent = Parser.LISTS_ITEM_INDENT.getFrom(options);
        newItemCodeIndent = Parser.LISTS_NEW_ITEM_CODE_INDENT.getFrom(options);
    }
}
