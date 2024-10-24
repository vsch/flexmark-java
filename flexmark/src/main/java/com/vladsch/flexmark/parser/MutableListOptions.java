package com.vladsch.flexmark.parser;

import com.vladsch.flexmark.util.data.DataHolder;

class MutableListOptions extends ListOptions {
  MutableListOptions() {
    super();
    itemInterrupt = new MutableItemInterrupt(super.getItemInterrupt());
  }

  MutableListOptions(DataHolder options) {
    this(ListOptions.get(options));
  }

  MutableListOptions(ListOptions other) {
    super(other);
    itemInterrupt = new MutableItemInterrupt(super.getItemInterrupt());
  }

  @Override
  public MutableListOptions getMutable() {
    return new MutableListOptions(this);
  }

  MutableListOptions setParserEmulationFamily(ParserEmulationProfile parserEmulationProfile) {
    this.myParserEmulationProfile = parserEmulationProfile;
    return this;
  }

  MutableListOptions setItemInterrupt(MutableItemInterrupt itemInterrupt) {
    this.itemInterrupt = itemInterrupt;
    return this;
  }

  MutableListOptions setAutoLoose(boolean autoLoose) {
    this.autoLoose = autoLoose;
    return this;
  }

  MutableListOptions setAutoLooseOneLevelLists(boolean autoLooseOneLevelLists) {
    this.autoLooseOneLevelLists = autoLooseOneLevelLists;
    return this;
  }

  MutableListOptions setDelimiterMismatchToNewList(boolean bulletMismatchToNewList) {
    this.delimiterMismatchToNewList = bulletMismatchToNewList;
    return this;
  }

  MutableListOptions setEndOnDoubleBlank(boolean endOnDoubleBlank) {
    this.endOnDoubleBlank = endOnDoubleBlank;
    return this;
  }

  MutableListOptions setItemMarkerSpace(boolean itemMarkerSpace) {
    this.itemMarkerSpace = itemMarkerSpace;
    return this;
  }

  MutableListOptions setItemTypeMismatchToNewList(boolean itemTypeMatchToNewList) {
    this.itemTypeMismatchToNewList = itemTypeMatchToNewList;
    return this;
  }

  MutableListOptions setItemTypeMismatchToSubList(boolean itemTypeMismatchToSubList) {
    this.itemTypeMismatchToSubList = itemTypeMismatchToSubList;
    return this;
  }

  MutableListOptions setLooseWhenPrevHasTrailingBlankLine(
      boolean looseWhenPrevHasTrailingBlankLine) {
    this.looseWhenPrevHasTrailingBlankLine = looseWhenPrevHasTrailingBlankLine;
    return this;
  }

  MutableListOptions setLooseWhenLastItemPrevHasTrailingBlankLine(
      boolean looseWhenLastItemPrevHasTrailingBlankLine) {
    this.looseWhenLastItemPrevHasTrailingBlankLine = looseWhenLastItemPrevHasTrailingBlankLine;
    return this;
  }

  MutableListOptions setLooseWhenHasNonListChildren(boolean looseWhenHasNonListChildren) {
    this.looseWhenHasNonListChildren = looseWhenHasNonListChildren;
    return this;
  }

  MutableListOptions setLooseWhenBlankLineFollowsItemParagraph(
      boolean looseWhenLineBlankFollowsItemParagraph) {
    this.looseWhenBlankLineFollowsItemParagraph = looseWhenLineBlankFollowsItemParagraph;
    return this;
  }

  MutableListOptions setLooseWhenHasLooseSubItem(boolean looseWhenHasLooseSubItem) {
    this.looseWhenHasLooseSubItem = looseWhenHasLooseSubItem;
    return this;
  }

  MutableListOptions setLooseWhenHasTrailingBlankLine(boolean looseWhenHasTrailingBlankLine) {
    this.looseWhenHasTrailingBlankLine = looseWhenHasTrailingBlankLine;
    return this;
  }

  MutableListOptions setLooseWhenContainsBlankLine(boolean looseWhenContainsBlankLine) {
    this.looseWhenContainsBlankLine = looseWhenContainsBlankLine;
    return this;
  }

  MutableListOptions setOrderedItemDotOnly(boolean orderedItemDotOnly) {
    this.orderedItemDotOnly = orderedItemDotOnly;
    return this;
  }

  MutableListOptions setOrderedListManualStart(boolean orderedListManualStart) {
    this.orderedListManualStart = orderedListManualStart;
    return this;
  }

  MutableListOptions setCodeIndent(int codeIndent) {
    this.codeIndent = codeIndent;
    return this;
  }

  MutableListOptions setItemIndent(int itemIndent) {
    this.itemIndent = itemIndent;
    return this;
  }

  MutableListOptions setNewItemCodeIndent(int newItemCodeIndent) {
    this.newItemCodeIndent = newItemCodeIndent;
    return this;
  }
}
