package com.vladsch.flexmark.parser;

import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

class MutableListOptions extends ListOptions {
  MutableListOptions() {
    super();
    itemInterrupt = new MutableItemInterrupt(super.getItemInterrupt());
  }

  MutableListOptions(@Nullable DataHolder options) {
    this(ListOptions.get(options));
  }

  MutableListOptions(@NotNull ListOptions other) {
    super(other);
    itemInterrupt = new MutableItemInterrupt(super.getItemInterrupt());
  }

  @Override
  public MutableListOptions getMutable() {
    return new MutableListOptions(this);
  }

  @NotNull
  MutableListOptions setParserEmulationFamily(
      @NotNull ParserEmulationProfile parserEmulationProfile) {
    this.myParserEmulationProfile = parserEmulationProfile;
    return this;
  }

  @NotNull
  MutableListOptions setItemInterrupt(@NotNull MutableItemInterrupt itemInterrupt) {
    this.itemInterrupt = itemInterrupt;
    return this;
  }

  @NotNull
  MutableListOptions setAutoLoose(boolean autoLoose) {
    this.autoLoose = autoLoose;
    return this;
  }

  @NotNull
  MutableListOptions setAutoLooseOneLevelLists(boolean autoLooseOneLevelLists) {
    this.autoLooseOneLevelLists = autoLooseOneLevelLists;
    return this;
  }

  @NotNull
  MutableListOptions setDelimiterMismatchToNewList(boolean bulletMismatchToNewList) {
    this.delimiterMismatchToNewList = bulletMismatchToNewList;
    return this;
  }

  @NotNull
  MutableListOptions setEndOnDoubleBlank(boolean endOnDoubleBlank) {
    this.endOnDoubleBlank = endOnDoubleBlank;
    return this;
  }

  @NotNull
  MutableListOptions setItemMarkerSpace(boolean itemMarkerSpace) {
    this.itemMarkerSpace = itemMarkerSpace;
    return this;
  }

  @NotNull
  MutableListOptions setItemTypeMismatchToNewList(boolean itemTypeMatchToNewList) {
    this.itemTypeMismatchToNewList = itemTypeMatchToNewList;
    return this;
  }

  @NotNull
  MutableListOptions setItemTypeMismatchToSubList(boolean itemTypeMismatchToSubList) {
    this.itemTypeMismatchToSubList = itemTypeMismatchToSubList;
    return this;
  }

  @NotNull
  MutableListOptions setLooseWhenPrevHasTrailingBlankLine(
      boolean looseWhenPrevHasTrailingBlankLine) {
    this.looseWhenPrevHasTrailingBlankLine = looseWhenPrevHasTrailingBlankLine;
    return this;
  }

  @NotNull
  MutableListOptions setLooseWhenLastItemPrevHasTrailingBlankLine(
      boolean looseWhenLastItemPrevHasTrailingBlankLine) {
    this.looseWhenLastItemPrevHasTrailingBlankLine = looseWhenLastItemPrevHasTrailingBlankLine;
    return this;
  }

  @NotNull
  MutableListOptions setLooseWhenHasNonListChildren(boolean looseWhenHasNonListChildren) {
    this.looseWhenHasNonListChildren = looseWhenHasNonListChildren;
    return this;
  }

  @NotNull
  MutableListOptions setLooseWhenBlankLineFollowsItemParagraph(
      boolean looseWhenLineBlankFollowsItemParagraph) {
    this.looseWhenBlankLineFollowsItemParagraph = looseWhenLineBlankFollowsItemParagraph;
    return this;
  }

  @NotNull
  MutableListOptions setLooseWhenHasLooseSubItem(boolean looseWhenHasLooseSubItem) {
    this.looseWhenHasLooseSubItem = looseWhenHasLooseSubItem;
    return this;
  }

  @NotNull
  MutableListOptions setLooseWhenHasTrailingBlankLine(boolean looseWhenHasTrailingBlankLine) {
    this.looseWhenHasTrailingBlankLine = looseWhenHasTrailingBlankLine;
    return this;
  }

  @NotNull
  MutableListOptions setLooseWhenContainsBlankLine(boolean looseWhenContainsBlankLine) {
    this.looseWhenContainsBlankLine = looseWhenContainsBlankLine;
    return this;
  }

  @NotNull
  MutableListOptions setOrderedItemDotOnly(boolean orderedItemDotOnly) {
    this.orderedItemDotOnly = orderedItemDotOnly;
    return this;
  }

  @NotNull
  MutableListOptions setOrderedListManualStart(boolean orderedListManualStart) {
    this.orderedListManualStart = orderedListManualStart;
    return this;
  }

  @NotNull
  MutableListOptions setCodeIndent(int codeIndent) {
    this.codeIndent = codeIndent;
    return this;
  }

  @NotNull
  MutableListOptions setItemIndent(int itemIndent) {
    this.itemIndent = itemIndent;
    return this;
  }

  @NotNull
  MutableListOptions setNewItemCodeIndent(int newItemCodeIndent) {
    this.newItemCodeIndent = newItemCodeIndent;
    return this;
  }
}
