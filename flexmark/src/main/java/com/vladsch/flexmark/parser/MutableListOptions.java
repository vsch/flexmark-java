package com.vladsch.flexmark.parser;

import com.vladsch.flexmark.util.options.DataHolder;

@SuppressWarnings({ "WeakerAccess", "SameParameterValue" })
public class MutableListOptions extends ListOptions {
    public MutableListOptions() {
        super();
        itemInterrupt = new MutableItemInterrupt(super.getItemInterrupt());
    }

    public MutableListOptions(DataHolder options) {
        this(ListOptions.getFrom(options));
    }

    MutableListOptions(ListOptions other) {
        super(other);
        itemInterrupt = new MutableItemInterrupt(super.getItemInterrupt());
    }

    public MutableListOptions getMutable() {
        return new MutableListOptions(this);
    }

    // @formatter:off
    public MutableListOptions setParserEmulationFamily(ParserEmulationProfile parserEmulationProfile) { this.myParserEmulationProfile = parserEmulationProfile; return this; }
    public MutableListOptions setItemInterrupt(MutableItemInterrupt itemInterrupt) { this.itemInterrupt = itemInterrupt; return this; }
    // boolean setters
    public MutableListOptions setAutoLoose(boolean autoLoose) { this.autoLoose = autoLoose; return this; }
    public MutableListOptions setAutoLooseOneLevelLists(boolean autoLooseOneLevelLists) { this.autoLooseOneLevelLists = autoLooseOneLevelLists; return this; }
    public MutableListOptions setDelimiterMismatchToNewList(boolean bulletMismatchToNewList) { this.delimiterMismatchToNewList = bulletMismatchToNewList; return this; }
    public MutableListOptions setEndOnDoubleBlank(boolean endOnDoubleBlank) { this.endOnDoubleBlank = endOnDoubleBlank; return this; }
    public MutableListOptions setItemMarkerSpace(boolean itemMarkerSpace) { this.itemMarkerSpace = itemMarkerSpace; return this; }
    public MutableListOptions setItemTypeMismatchToNewList(boolean itemTypeMatchToNewList) { this.itemTypeMismatchToNewList = itemTypeMatchToNewList; return this; }
    public MutableListOptions setItemTypeMismatchToSubList(boolean itemTypeMismatchToSubList) { this.itemTypeMismatchToSubList = itemTypeMismatchToSubList; return this; }
    public MutableListOptions setLooseWhenPrevHasTrailingBlankLine(boolean looseWhenPrevHasTrailingBlankLine) { this.looseWhenPrevHasTrailingBlankLine = looseWhenPrevHasTrailingBlankLine; return this; }
    public MutableListOptions setLooseWhenLastItemPrevHasTrailingBlankLine(boolean looseWhenLastItemPrevHasTrailingBlankLine) { this.looseWhenLastItemPrevHasTrailingBlankLine = looseWhenLastItemPrevHasTrailingBlankLine; return this; }
    public MutableListOptions setLooseWhenHasNonListChildren(boolean looseWhenHasNonListChildren) { this.looseWhenHasNonListChildren = looseWhenHasNonListChildren; return this; }
    public MutableListOptions setLooseWhenBlankLineFollowsItemParagraph(boolean looseWhenLineBlankFollowsItemParagraph) { this.looseWhenBlankLineFollowsItemParagraph = looseWhenLineBlankFollowsItemParagraph; return this; }
    public MutableListOptions setLooseWhenHasLooseSubItem(boolean looseWhenHasLooseSubItem) { this.looseWhenHasLooseSubItem = looseWhenHasLooseSubItem; return this; }
    public MutableListOptions setLooseWhenHasTrailingBlankLine(boolean looseWhenHasTrailingBlankLine) { this.looseWhenHasTrailingBlankLine = looseWhenHasTrailingBlankLine; return this; }
    public MutableListOptions setLooseWhenContainsBlankLine(boolean looseWhenContainsBlankLine) { this.looseWhenContainsBlankLine = looseWhenContainsBlankLine; return this; }
    public MutableListOptions setNumberedItemMarkerSuffixed(boolean numberedItemMarkerSuffixed) { this.numberedItemMarkerSuffixed = numberedItemMarkerSuffixed; return this; }
    public MutableListOptions setOrderedItemDotOnly(boolean orderedItemDotOnly) { this.orderedItemDotOnly = orderedItemDotOnly; return this; }
    public MutableListOptions setOrderedListManualStart(boolean orderedListManualStart) { this.orderedListManualStart = orderedListManualStart; return this; }
    // int setters
    public MutableListOptions setCodeIndent(int codeIndent) { this.codeIndent = codeIndent; return this; }
    public MutableListOptions setItemIndent(int itemIndent) { this.itemIndent = itemIndent; return this; }
    public MutableListOptions setNewItemCodeIndent(int newItemCodeIndent) { this.newItemCodeIndent = newItemCodeIndent; return this; }
    public MutableListOptions setItemMarkerSuffixes(String[] itemMarkerSuffixes) { this.itemMarkerSuffixes = itemMarkerSuffixes; return this; }
    // @formatter:on
}

