package com.vladsch.flexmark.parser;

import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings({ "WeakerAccess", "SameParameterValue" })
public class MutableListOptions extends ListOptions {
    public MutableListOptions() {
        super();
        itemInterrupt = new MutableItemInterrupt(super.getItemInterrupt());
    }

    public MutableListOptions(@Nullable DataHolder options) {
        this(ListOptions.get(options));
    }

    MutableListOptions(@NotNull ListOptions other) {
        super(other);
        itemInterrupt = new MutableItemInterrupt(super.getItemInterrupt());
    }

    public MutableListOptions getMutable() {
        return new MutableListOptions(this);
    }

    // @formatter:off
    public @NotNull MutableListOptions setParserEmulationFamily(@NotNull ParserEmulationProfile parserEmulationProfile) { this.myParserEmulationProfile = parserEmulationProfile; return this; }
    public @NotNull MutableListOptions setItemInterrupt(@NotNull MutableItemInterrupt itemInterrupt) { this.itemInterrupt = itemInterrupt; return this; }
    // boolean setters
    public @NotNull MutableListOptions setAutoLoose(boolean autoLoose) { this.autoLoose = autoLoose; return this; }
    public @NotNull MutableListOptions setAutoLooseOneLevelLists(boolean autoLooseOneLevelLists) { this.autoLooseOneLevelLists = autoLooseOneLevelLists; return this; }
    public @NotNull MutableListOptions setDelimiterMismatchToNewList(boolean bulletMismatchToNewList) { this.delimiterMismatchToNewList = bulletMismatchToNewList; return this; }
    public @NotNull MutableListOptions setEndOnDoubleBlank(boolean endOnDoubleBlank) { this.endOnDoubleBlank = endOnDoubleBlank; return this; }
    public @NotNull MutableListOptions setItemMarkerSpace(boolean itemMarkerSpace) { this.itemMarkerSpace = itemMarkerSpace; return this; }
    public @NotNull MutableListOptions setItemTypeMismatchToNewList(boolean itemTypeMatchToNewList) { this.itemTypeMismatchToNewList = itemTypeMatchToNewList; return this; }
    public @NotNull MutableListOptions setItemTypeMismatchToSubList(boolean itemTypeMismatchToSubList) { this.itemTypeMismatchToSubList = itemTypeMismatchToSubList; return this; }
    public @NotNull MutableListOptions setLooseWhenPrevHasTrailingBlankLine(boolean looseWhenPrevHasTrailingBlankLine) { this.looseWhenPrevHasTrailingBlankLine = looseWhenPrevHasTrailingBlankLine; return this; }
    public @NotNull MutableListOptions setLooseWhenLastItemPrevHasTrailingBlankLine(boolean looseWhenLastItemPrevHasTrailingBlankLine) { this.looseWhenLastItemPrevHasTrailingBlankLine = looseWhenLastItemPrevHasTrailingBlankLine; return this; }
    public @NotNull MutableListOptions setLooseWhenHasNonListChildren(boolean looseWhenHasNonListChildren) { this.looseWhenHasNonListChildren = looseWhenHasNonListChildren; return this; }
    public @NotNull MutableListOptions setLooseWhenBlankLineFollowsItemParagraph(boolean looseWhenLineBlankFollowsItemParagraph) { this.looseWhenBlankLineFollowsItemParagraph = looseWhenLineBlankFollowsItemParagraph; return this; }
    public @NotNull MutableListOptions setLooseWhenHasLooseSubItem(boolean looseWhenHasLooseSubItem) { this.looseWhenHasLooseSubItem = looseWhenHasLooseSubItem; return this; }
    public @NotNull MutableListOptions setLooseWhenHasTrailingBlankLine(boolean looseWhenHasTrailingBlankLine) { this.looseWhenHasTrailingBlankLine = looseWhenHasTrailingBlankLine; return this; }
    public @NotNull MutableListOptions setLooseWhenContainsBlankLine(boolean looseWhenContainsBlankLine) { this.looseWhenContainsBlankLine = looseWhenContainsBlankLine; return this; }
    public @NotNull MutableListOptions setNumberedItemMarkerSuffixed(boolean numberedItemMarkerSuffixed) { this.numberedItemMarkerSuffixed = numberedItemMarkerSuffixed; return this; }
    public @NotNull MutableListOptions setOrderedItemDotOnly(boolean orderedItemDotOnly) { this.orderedItemDotOnly = orderedItemDotOnly; return this; }
    public @NotNull MutableListOptions setOrderedListManualStart(boolean orderedListManualStart) { this.orderedListManualStart = orderedListManualStart; return this; }
    // int setters
    public @NotNull MutableListOptions setCodeIndent(int codeIndent) { this.codeIndent = codeIndent; return this; }
    public @NotNull MutableListOptions setItemIndent(int itemIndent) { this.itemIndent = itemIndent; return this; }
    public @NotNull MutableListOptions setNewItemCodeIndent(int newItemCodeIndent) { this.newItemCodeIndent = newItemCodeIndent; return this; }
    public @NotNull MutableListOptions setItemMarkerSuffixes(@NotNull String[] itemMarkerSuffixes) { this.itemMarkerSuffixes = itemMarkerSuffixes; return this; }
    // @formatter:on
}

