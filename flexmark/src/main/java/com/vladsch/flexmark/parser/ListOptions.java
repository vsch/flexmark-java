package com.vladsch.flexmark.parser;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.MutableDataHolder;
import com.vladsch.flexmark.util.options.MutableDataSetter;

@SuppressWarnings("WeakerAccess")
public class ListOptions implements MutableDataSetter {
    protected ParserEmulationFamily parserEmulationFamily;
    protected ItemInterrupt itemInterrupt;
    protected boolean autoLoose;
    protected boolean autoLooseOneLevelLists;
    protected boolean delimiterMismatchToNewList;
    protected boolean endOnDoubleBlank;
    protected boolean itemMarkerSpace;
    protected boolean itemTypeMismatchToNewList;
    protected boolean itemTypeMismatchToSubList;
    protected boolean looseOnPrevLooseItem;
    protected boolean looseWhenHasLooseSubItem;
    protected boolean looseWhenHasTrailingBlankLine;
    protected boolean looseWhenBlankFollowsItemParagraph;
    protected boolean orderedItemDotOnly;
    protected boolean orderedListManualStart;
    protected int codeIndent;
    protected int itemIndent;
    protected int newItemCodeIndent;

    public ListOptions() {
        this((DataHolder) null);
    }

    public ListOptions(DataHolder options) {
        parserEmulationFamily = Parser.PARSER_EMULATION_FAMILY.getFrom(options);
        itemInterrupt = new ItemInterrupt(options);

        autoLoose = Parser.LISTS_AUTO_LOOSE.getFrom(options);
        autoLooseOneLevelLists = Parser.LISTS_AUTO_LOOSE_ONE_LEVEL_LISTS.getFrom(options);
        delimiterMismatchToNewList = Parser.LISTS_DELIMITER_MISMATCH_TO_NEW_LIST.getFrom(options);
        endOnDoubleBlank = Parser.LISTS_END_ON_DOUBLE_BLANK.getFrom(options);
        itemMarkerSpace = Parser.LISTS_ITEM_MARKER_SPACE.getFrom(options);
        itemTypeMismatchToNewList = Parser.LISTS_ITEM_TYPE_MISMATCH_TO_NEW_LIST.getFrom(options);
        itemTypeMismatchToSubList = Parser.LISTS_ITEM_TYPE_MISMATCH_TO_SUB_LIST.getFrom(options);
        looseOnPrevLooseItem = Parser.LISTS_LOOSE_ON_PREV_LOOSE_ITEM.getFrom(options);
        looseWhenBlankFollowsItemParagraph = Parser.LISTS_LOOSE_WHEN_BLANK_FOLLOWS_ITEM_PARAGRAPH.getFrom(options);
        looseWhenHasLooseSubItem = Parser.LISTS_LOOSE_WHEN_HAS_LOOSE_SUB_ITEM.getFrom(options);
        looseWhenHasTrailingBlankLine = Parser.LISTS_LOOSE_WHEN_HAS_TRAILING_BLANK_LINE.getFrom(options);
        orderedItemDotOnly = Parser.LISTS_ORDERED_ITEM_DOT_ONLY.getFrom(options);
        orderedListManualStart = Parser.LISTS_ORDERED_LIST_MANUAL_START.getFrom(options);

        codeIndent = Parser.LISTS_CODE_INDENT.getFrom(options);
        itemIndent = Parser.LISTS_ITEM_INDENT.getFrom(options);
        newItemCodeIndent = Parser.LISTS_NEW_ITEM_CODE_INDENT.getFrom(options);
    }

    ListOptions(ListOptions other) {
        parserEmulationFamily = other.getParserEmulationFamily();
        itemInterrupt = new ItemInterrupt(other.getItemInterrupt());

        autoLoose = other.isAutoLoose();
        autoLooseOneLevelLists = other.isAutoLooseOneLevelLists();
        delimiterMismatchToNewList = other.isDelimiterMismatchToNewList();
        endOnDoubleBlank = other.isEndOnDoubleBlank();
        itemMarkerSpace = other.isItemMarkerSpace();
        itemTypeMismatchToNewList = other.isItemTypeMismatchToNewList();
        itemTypeMismatchToSubList = other.isItemTypeMismatchToSubList();
        looseOnPrevLooseItem = other.isLooseOnPrevLooseItem();
        looseWhenBlankFollowsItemParagraph = other.isLooseWhenBlankFollowsItemParagraph();
        looseWhenHasLooseSubItem = other.isLooseWhenHasLooseSubItem();
        looseWhenHasTrailingBlankLine = other.isLooseWhenHasTrailingBlankLine();
        orderedItemDotOnly = other.isOrderedItemDotOnly();
        orderedListManualStart = other.isOrderedListManualStart();

        codeIndent = other.getCodeIndent();
        itemIndent = other.getItemIndent();
        newItemCodeIndent = other.getNewItemCodeIndent();
    }

    public boolean isTightListItem(ListItem node) {
        if (isAutoLoose() && isAutoLooseOneLevelLists()) {
            boolean singleLevel = node.getAncestorOfType(ListItem.class) == null && node.getChildOfType(ListBlock.class) == null;
            return node.getFirstChild() == null || !singleLevel && node.isTight() || singleLevel && node.isInTightList();
        } else {
            return node.getFirstChild() == null || !isAutoLoose() && node.isTight() || isAutoLoose() && node.isInTightList();
        }
    }

    public boolean isInTightListItem(Paragraph node) {
        Block parent = node.getParent();
        if (isAutoLoose() && isAutoLooseOneLevelLists()) {
            return parent instanceof ListItem && isTightListItem((ListItem) parent);
        }
        return parent instanceof ListItem && (!isAutoLoose() && ((ListItem) parent).isParagraphInTightListItem(node) || isAutoLoose() && ((ListItem) parent).isInTightList());
    }

    public boolean canInterrupt(ListBlock a, boolean isEmptyItem, boolean isItemParagraph) {
        boolean isNumberedItem = a instanceof OrderedList;
        boolean isOneItem = isNumberedItem && (!isOrderedListManualStart() || ((OrderedList) a).getStartNumber() == 1);

        return getItemInterrupt().canInterrupt(isNumberedItem, isOneItem, isEmptyItem, isItemParagraph);
    }

    public boolean canStartSubList(ListBlock a, boolean isEmptyItem) {
        boolean isNumberedItem = a instanceof OrderedList;
        boolean isOneItem = isNumberedItem && (!isOrderedListManualStart() || ((OrderedList) a).getStartNumber() == 1);

        return getItemInterrupt().canStartSubList(isNumberedItem, isOneItem, isEmptyItem);
    }

    public boolean startNewList(ListBlock a, ListBlock b) {
        boolean isNumberedList = a instanceof OrderedList;
        boolean isNumberedItem = b instanceof OrderedList;

        if (isNumberedList == isNumberedItem) {
            if (isNumberedList) {
                return isDelimiterMismatchToNewList() && ((OrderedList) a).getDelimiter() != ((OrderedList) b).getDelimiter();
            } else {
                return isDelimiterMismatchToNewList() && ((BulletList) a).getOpeningMarker() != ((BulletList) b).getOpeningMarker();
            }
        } else {
            return isItemTypeMismatchToNewList();
        }
    }

    public boolean startSubList(ListBlock a, ListBlock b) {
        boolean isNumberedList = a instanceof OrderedList;
        boolean isNumberedItem = b instanceof OrderedList;

        return isNumberedList != isNumberedItem && isItemTypeMismatchToSubList();
    }

    public MutableListOptions getMutable() {
        return new MutableListOptions(this);
    }

    public MutableDataHolder setIn(MutableDataHolder options) {
        options.set(Parser.PARSER_EMULATION_FAMILY, getParserEmulationFamily());
        getItemInterrupt().setIn(options);

        options.set(Parser.LISTS_AUTO_LOOSE, isAutoLoose());
        options.set(Parser.LISTS_AUTO_LOOSE_ONE_LEVEL_LISTS, isAutoLooseOneLevelLists());
        options.set(Parser.LISTS_DELIMITER_MISMATCH_TO_NEW_LIST, isDelimiterMismatchToNewList());
        options.set(Parser.LISTS_END_ON_DOUBLE_BLANK, isEndOnDoubleBlank());
        options.set(Parser.LISTS_ITEM_MARKER_SPACE, isItemMarkerSpace());
        options.set(Parser.LISTS_ITEM_TYPE_MISMATCH_TO_NEW_LIST, isItemTypeMismatchToNewList());
        options.set(Parser.LISTS_ITEM_TYPE_MISMATCH_TO_SUB_LIST, isItemTypeMismatchToSubList());
        options.set(Parser.LISTS_LOOSE_ON_PREV_LOOSE_ITEM, isLooseOnPrevLooseItem());
        options.set(Parser.LISTS_LOOSE_WHEN_BLANK_FOLLOWS_ITEM_PARAGRAPH, isLooseWhenBlankFollowsItemParagraph());
        options.set(Parser.LISTS_LOOSE_WHEN_HAS_LOOSE_SUB_ITEM, isLooseWhenHasLooseSubItem());
        options.set(Parser.LISTS_LOOSE_WHEN_HAS_TRAILING_BLANK_LINE, isLooseWhenHasTrailingBlankLine());
        options.set(Parser.LISTS_ORDERED_ITEM_DOT_ONLY, isOrderedItemDotOnly());
        options.set(Parser.LISTS_ORDERED_LIST_MANUAL_START, isOrderedListManualStart());

        options.set(Parser.LISTS_ITEM_INDENT, getItemIndent());
        options.set(Parser.LISTS_CODE_INDENT, getCodeIndent());
        options.set(Parser.LISTS_NEW_ITEM_CODE_INDENT, getNewItemCodeIndent());

        return options;
    }

    public ParserEmulationFamily getParserEmulationFamily() {
        return parserEmulationFamily;
    }

    public ItemInterrupt getItemInterrupt() {
        return itemInterrupt;
    }

    public boolean isAutoLoose() {
        return autoLoose;
    }

    public boolean isAutoLooseOneLevelLists() {
        return autoLooseOneLevelLists;
    }

    public boolean isDelimiterMismatchToNewList() {
        return delimiterMismatchToNewList;
    }

    public boolean isEndOnDoubleBlank() {
        return endOnDoubleBlank;
    }

    public boolean isItemMarkerSpace() {
        return itemMarkerSpace;
    }

    public boolean isItemTypeMismatchToNewList() {
        return itemTypeMismatchToNewList;
    }

    public boolean isItemTypeMismatchToSubList() {
        return itemTypeMismatchToSubList;
    }

    public boolean isLooseOnPrevLooseItem() {
        return looseOnPrevLooseItem;
    }

    public boolean isLooseWhenHasLooseSubItem() {
        return looseWhenHasLooseSubItem;
    }

    public boolean isLooseWhenHasTrailingBlankLine() {
        return looseWhenHasTrailingBlankLine;
    }

    public boolean isLooseWhenBlankFollowsItemParagraph() {
        return looseWhenBlankFollowsItemParagraph;
    }

    public boolean isOrderedItemDotOnly() {
        return orderedItemDotOnly;
    }

    public boolean isOrderedListManualStart() {
        return orderedListManualStart;
    }

    public int getCodeIndent() {
        return codeIndent;
    }

    public int getItemIndent() {
        return itemIndent;
    }

    public int getNewItemCodeIndent() {
        return newItemCodeIndent;
    }

    public static class ItemInterrupt {
        protected boolean bulletItemInterruptsParagraph;
        protected boolean orderedItemInterruptsParagraph;
        protected boolean orderedNonOneItemInterruptsParagraph;

        protected boolean emptyBulletItemInterruptsParagraph;
        protected boolean emptyOrderedItemInterruptsParagraph;
        protected boolean emptyOrderedNonOneItemInterruptsParagraph;

        protected boolean bulletItemInterruptsItemParagraph;
        protected boolean orderedItemInterruptsItemParagraph;
        protected boolean orderedNonOneItemInterruptsItemParagraph;

        protected boolean emptyBulletItemInterruptsItemParagraph;
        protected boolean emptyOrderedItemInterruptsItemParagraph;
        protected boolean emptyOrderedNonOneItemInterruptsItemParagraph;

        protected boolean emptyBulletSubItemInterruptsItemParagraph;
        protected boolean emptyOrderedSubItemInterruptsItemParagraph;
        protected boolean emptyOrderedNonOneSubItemInterruptsItemParagraph;

        public ItemInterrupt() {
            bulletItemInterruptsParagraph = false;
            orderedItemInterruptsParagraph = false;
            orderedNonOneItemInterruptsParagraph = false;

            emptyBulletItemInterruptsParagraph = false;
            emptyOrderedItemInterruptsParagraph = false;
            emptyOrderedNonOneItemInterruptsParagraph = false;

            bulletItemInterruptsItemParagraph = false;
            orderedItemInterruptsItemParagraph = false;
            orderedNonOneItemInterruptsItemParagraph = false;

            emptyBulletItemInterruptsItemParagraph = false;
            emptyOrderedItemInterruptsItemParagraph = false;
            emptyOrderedNonOneItemInterruptsItemParagraph = false;

            emptyBulletSubItemInterruptsItemParagraph = false;
            emptyOrderedSubItemInterruptsItemParagraph = false;
            emptyOrderedNonOneSubItemInterruptsItemParagraph = false;
        }

        public ItemInterrupt(DataHolder options) {
            bulletItemInterruptsParagraph = Parser.LISTS_BULLET_ITEM_INTERRUPTS_PARAGRAPH.getFrom(options);
            orderedItemInterruptsParagraph = Parser.LISTS_ORDERED_ITEM_INTERRUPTS_PARAGRAPH.getFrom(options);
            orderedNonOneItemInterruptsParagraph = Parser.LISTS_ORDERED_NON_ONE_ITEM_INTERRUPTS_PARAGRAPH.getFrom(options);

            emptyBulletItemInterruptsParagraph = Parser.LISTS_EMPTY_BULLET_ITEM_INTERRUPTS_PARAGRAPH.getFrom(options);
            emptyOrderedItemInterruptsParagraph = Parser.LISTS_EMPTY_ORDERED_ITEM_INTERRUPTS_PARAGRAPH.getFrom(options);
            emptyOrderedNonOneItemInterruptsParagraph = Parser.LISTS_EMPTY_ORDERED_NON_ONE_ITEM_INTERRUPTS_PARAGRAPH.getFrom(options);

            bulletItemInterruptsItemParagraph = Parser.LISTS_BULLET_ITEM_INTERRUPTS_ITEM_PARAGRAPH.getFrom(options);
            orderedItemInterruptsItemParagraph = Parser.LISTS_ORDERED_ITEM_INTERRUPTS_ITEM_PARAGRAPH.getFrom(options);
            orderedNonOneItemInterruptsItemParagraph = Parser.LISTS_ORDERED_NON_ONE_ITEM_INTERRUPTS_ITEM_PARAGRAPH.getFrom(options);

            emptyBulletItemInterruptsItemParagraph = Parser.LISTS_EMPTY_BULLET_ITEM_INTERRUPTS_ITEM_PARAGRAPH.getFrom(options);
            emptyOrderedItemInterruptsItemParagraph = Parser.LISTS_EMPTY_ORDERED_ITEM_INTERRUPTS_ITEM_PARAGRAPH.getFrom(options);
            emptyOrderedNonOneItemInterruptsItemParagraph = Parser.LISTS_EMPTY_ORDERED_NON_ONE_ITEM_INTERRUPTS_ITEM_PARAGRAPH.getFrom(options);

            emptyBulletSubItemInterruptsItemParagraph = Parser.LISTS_EMPTY_BULLET_SUB_ITEM_INTERRUPTS_ITEM_PARAGRAPH.getFrom(options);
            emptyOrderedSubItemInterruptsItemParagraph = Parser.LISTS_EMPTY_ORDERED_SUB_ITEM_INTERRUPTS_ITEM_PARAGRAPH.getFrom(options);
            emptyOrderedNonOneSubItemInterruptsItemParagraph = Parser.LISTS_EMPTY_ORDERED_NON_ONE_SUB_ITEM_INTERRUPTS_ITEM_PARAGRAPH.getFrom(options);
        }

        public void setIn(MutableDataHolder options) {
            options.set(Parser.LISTS_BULLET_ITEM_INTERRUPTS_PARAGRAPH, bulletItemInterruptsParagraph);
            options.set(Parser.LISTS_ORDERED_ITEM_INTERRUPTS_PARAGRAPH, orderedItemInterruptsParagraph);
            options.set(Parser.LISTS_ORDERED_NON_ONE_ITEM_INTERRUPTS_PARAGRAPH, orderedNonOneItemInterruptsParagraph);

            options.set(Parser.LISTS_EMPTY_BULLET_ITEM_INTERRUPTS_PARAGRAPH, emptyBulletItemInterruptsParagraph);
            options.set(Parser.LISTS_EMPTY_ORDERED_ITEM_INTERRUPTS_PARAGRAPH, emptyOrderedItemInterruptsParagraph);
            options.set(Parser.LISTS_EMPTY_ORDERED_NON_ONE_ITEM_INTERRUPTS_PARAGRAPH, emptyOrderedNonOneItemInterruptsParagraph);

            options.set(Parser.LISTS_BULLET_ITEM_INTERRUPTS_ITEM_PARAGRAPH, bulletItemInterruptsItemParagraph);
            options.set(Parser.LISTS_ORDERED_ITEM_INTERRUPTS_ITEM_PARAGRAPH, orderedItemInterruptsItemParagraph);
            options.set(Parser.LISTS_ORDERED_NON_ONE_ITEM_INTERRUPTS_ITEM_PARAGRAPH, orderedNonOneItemInterruptsItemParagraph);

            options.set(Parser.LISTS_EMPTY_BULLET_ITEM_INTERRUPTS_ITEM_PARAGRAPH, emptyBulletItemInterruptsItemParagraph);
            options.set(Parser.LISTS_EMPTY_ORDERED_ITEM_INTERRUPTS_ITEM_PARAGRAPH, emptyOrderedItemInterruptsItemParagraph);
            options.set(Parser.LISTS_EMPTY_ORDERED_NON_ONE_ITEM_INTERRUPTS_ITEM_PARAGRAPH, emptyOrderedNonOneItemInterruptsItemParagraph);

            options.set(Parser.LISTS_EMPTY_BULLET_SUB_ITEM_INTERRUPTS_ITEM_PARAGRAPH, emptyBulletSubItemInterruptsItemParagraph);
            options.set(Parser.LISTS_EMPTY_ORDERED_SUB_ITEM_INTERRUPTS_ITEM_PARAGRAPH, emptyOrderedSubItemInterruptsItemParagraph);
            options.set(Parser.LISTS_EMPTY_ORDERED_NON_ONE_SUB_ITEM_INTERRUPTS_ITEM_PARAGRAPH, emptyOrderedNonOneSubItemInterruptsItemParagraph);
        }

        public ItemInterrupt(ItemInterrupt other) {
            bulletItemInterruptsParagraph = other.bulletItemInterruptsParagraph;
            orderedItemInterruptsParagraph = other.orderedItemInterruptsParagraph;
            orderedNonOneItemInterruptsParagraph = other.orderedNonOneItemInterruptsParagraph;

            emptyBulletItemInterruptsParagraph = other.emptyBulletItemInterruptsParagraph;
            emptyOrderedItemInterruptsParagraph = other.emptyOrderedItemInterruptsParagraph;
            emptyOrderedNonOneItemInterruptsParagraph = other.emptyOrderedNonOneItemInterruptsParagraph;

            bulletItemInterruptsItemParagraph = other.bulletItemInterruptsItemParagraph;
            orderedItemInterruptsItemParagraph = other.orderedItemInterruptsItemParagraph;
            orderedNonOneItemInterruptsItemParagraph = other.orderedNonOneItemInterruptsItemParagraph;

            emptyBulletItemInterruptsItemParagraph = other.emptyBulletItemInterruptsItemParagraph;
            emptyOrderedItemInterruptsItemParagraph = other.emptyOrderedItemInterruptsItemParagraph;
            emptyOrderedNonOneItemInterruptsItemParagraph = other.emptyOrderedNonOneItemInterruptsItemParagraph;

            emptyBulletSubItemInterruptsItemParagraph = other.emptyBulletSubItemInterruptsItemParagraph;
            emptyOrderedSubItemInterruptsItemParagraph = other.emptyOrderedSubItemInterruptsItemParagraph;
            emptyOrderedNonOneSubItemInterruptsItemParagraph = other.emptyOrderedNonOneSubItemInterruptsItemParagraph;
        }

        public boolean canInterrupt(boolean isNumberedItem, boolean isOneItem, boolean isEmptyItem, boolean isItemParagraph) {
            if (isNumberedItem) {
                if (isOneItem) {
                    if (isItemParagraph) {
                        return orderedItemInterruptsItemParagraph && (!isEmptyItem || emptyOrderedItemInterruptsItemParagraph);
                    } else {
                        return orderedItemInterruptsParagraph && (!isEmptyItem || emptyOrderedItemInterruptsParagraph);
                    }
                } else {
                    if (isItemParagraph) {
                        return orderedNonOneItemInterruptsItemParagraph && (!isEmptyItem || emptyOrderedNonOneItemInterruptsItemParagraph);
                    } else {
                        return orderedNonOneItemInterruptsParagraph && (!isEmptyItem || emptyOrderedNonOneItemInterruptsParagraph);
                    }
                }
            } else {
                if (isItemParagraph) {
                    return bulletItemInterruptsItemParagraph && (!isEmptyItem || emptyBulletItemInterruptsItemParagraph);
                } else {
                    return bulletItemInterruptsParagraph && (!isEmptyItem || emptyBulletItemInterruptsParagraph);
                }
            }
        }

        public boolean canStartSubList(boolean isNumberedItem, boolean isOneItem, boolean isEmptyItem) {
            if (isNumberedItem) {
                return orderedItemInterruptsItemParagraph && (!isEmptyItem || emptyOrderedSubItemInterruptsItemParagraph && emptyOrderedItemInterruptsItemParagraph)
                        && (isOneItem
                        || (orderedNonOneItemInterruptsItemParagraph && (!isEmptyItem || emptyOrderedNonOneSubItemInterruptsItemParagraph && emptyOrderedNonOneItemInterruptsItemParagraph)));
            } else {
                return bulletItemInterruptsItemParagraph && (!isEmptyItem || emptyBulletSubItemInterruptsItemParagraph && emptyBulletItemInterruptsItemParagraph);
            }
        }
    }

    @SuppressWarnings("SameParameterValue")
    public static class MutableItemInterrupt extends ItemInterrupt {
        public MutableItemInterrupt() { }

        public MutableItemInterrupt(DataHolder options) {
            super(options);
        }

        public MutableItemInterrupt(ItemInterrupt other) {
            super(other);
        }

        // @formatter:off
        public boolean isBulletItemInterruptsParagraph() { return bulletItemInterruptsParagraph; }
        public boolean isOrderedItemInterruptsParagraph() { return orderedItemInterruptsParagraph; }
        public boolean isOrderedNonOneItemInterruptsParagraph() { return orderedNonOneItemInterruptsParagraph; }

        public boolean isEmptyBulletItemInterruptsParagraph() { return emptyBulletItemInterruptsParagraph; }
        public boolean isEmptyOrderedItemInterruptsParagraph() { return emptyOrderedItemInterruptsParagraph; }
        public boolean isEmptyOrderedNonOneItemInterruptsParagraph() { return emptyOrderedNonOneItemInterruptsParagraph; }

        public boolean isBulletItemInterruptsItemParagraph() { return bulletItemInterruptsItemParagraph; }
        public boolean isOrderedItemInterruptsItemParagraph() { return orderedItemInterruptsItemParagraph; }
        public boolean isOrderedNonOneItemInterruptsItemParagraph() { return orderedNonOneItemInterruptsItemParagraph; }

        public boolean isEmptyBulletItemInterruptsItemParagraph() { return emptyBulletItemInterruptsItemParagraph; }
        public boolean isEmptyOrderedItemInterruptsItemParagraph() { return emptyOrderedItemInterruptsItemParagraph; }
        public boolean isEmptyOrderedNonOneItemInterruptsItemParagraph() { return emptyOrderedNonOneItemInterruptsItemParagraph; }

        public boolean isEmptyBulletSubItemInterruptsItemParagraph() { return emptyBulletSubItemInterruptsItemParagraph; }
        public boolean isEmptyOrderedSubItemInterruptsItemParagraph() { return emptyOrderedSubItemInterruptsItemParagraph; }
        public boolean isEmptyOrderedNonOneSubItemInterruptsItemParagraph() { return emptyOrderedNonOneSubItemInterruptsItemParagraph; }

        public MutableItemInterrupt setBulletItemInterruptsParagraph(boolean bulletItemInterruptsParagraph) { this.bulletItemInterruptsParagraph = bulletItemInterruptsParagraph; return this; }
        public MutableItemInterrupt setOrderedItemInterruptsParagraph(boolean orderedItemInterruptsParagraph) { this.orderedItemInterruptsParagraph = orderedItemInterruptsParagraph; return this; }
        public MutableItemInterrupt setOrderedNonOneItemInterruptsParagraph(boolean orderedNonOneItemInterruptsParagraph) { this.orderedNonOneItemInterruptsParagraph = orderedNonOneItemInterruptsParagraph; return this; }

        public MutableItemInterrupt setEmptyBulletItemInterruptsParagraph(boolean emptyBulletItemInterruptsParagraph) { this.emptyBulletItemInterruptsParagraph = emptyBulletItemInterruptsParagraph; return this; }
        public MutableItemInterrupt setEmptyOrderedItemInterruptsParagraph(boolean emptyOrderedItemInterruptsParagraph) { this.emptyOrderedItemInterruptsParagraph = emptyOrderedItemInterruptsParagraph; return this; }
        public MutableItemInterrupt setEmptyOrderedNonOneItemInterruptsParagraph(boolean emptyOrderedNonOneItemInterruptsParagraph) { this.emptyOrderedNonOneItemInterruptsParagraph = emptyOrderedNonOneItemInterruptsParagraph; return this; }

        public MutableItemInterrupt setBulletItemInterruptsItemParagraph(boolean bulletItemInterruptsItemParagraph) { this.bulletItemInterruptsItemParagraph = bulletItemInterruptsItemParagraph; return this; }
        public MutableItemInterrupt setOrderedItemInterruptsItemParagraph(boolean orderedItemInterruptsItemParagraph) { this.orderedItemInterruptsItemParagraph = orderedItemInterruptsItemParagraph; return this; }
        public MutableItemInterrupt setOrderedNonOneItemInterruptsItemParagraph(boolean orderedNonOneItemInterruptsItemParagraph) { this.orderedNonOneItemInterruptsItemParagraph = orderedNonOneItemInterruptsItemParagraph; return this; }

        public MutableItemInterrupt setEmptyBulletItemInterruptsItemParagraph(boolean emptyBulletItemInterruptsItemParagraph) { this.emptyBulletItemInterruptsItemParagraph = emptyBulletItemInterruptsItemParagraph; return this; }
        public MutableItemInterrupt setEmptyOrderedItemInterruptsItemParagraph(boolean emptyOrderedItemInterruptsItemParagraph) { this.emptyOrderedItemInterruptsItemParagraph = emptyOrderedItemInterruptsItemParagraph; return this; }
        public MutableItemInterrupt setEmptyOrderedNonOneItemInterruptsItemParagraph(boolean emptyOrderedNonOneItemInterruptsItemParagraph) { this.emptyOrderedNonOneItemInterruptsItemParagraph = emptyOrderedNonOneItemInterruptsItemParagraph; return this; }

        public MutableItemInterrupt setEmptyBulletSubItemInterruptsItemParagraph(boolean emptyBulletItemStartsSubList) { this.emptyBulletSubItemInterruptsItemParagraph = emptyBulletItemStartsSubList; return this; }
        public MutableItemInterrupt setEmptyOrderedSubItemInterruptsItemParagraph(boolean emptyOrderedItemStartsSubList) { this.emptyOrderedSubItemInterruptsItemParagraph = emptyOrderedItemStartsSubList; return this; }
        public MutableItemInterrupt setEmptyOrderedNonOneSubItemInterruptsItemParagraph(boolean emptyOrderedNonOneItemStartsSubList) { this.emptyOrderedNonOneSubItemInterruptsItemParagraph = emptyOrderedNonOneItemStartsSubList; return this; }

        // @formatter:on
    }
}

