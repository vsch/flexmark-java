package com.vladsch.flexmark.parser;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.MutableDataHolder;
import com.vladsch.flexmark.util.options.MutableDataSetter;

@SuppressWarnings("WeakerAccess")
public class ListOptions implements MutableDataSetter {
    public final ParserEmulationFamily parserEmulationFamily;
    public final boolean autoLoose;
    public final boolean looseOnPrevLooseItem;
    public final boolean orderedStart;
    public final boolean delimiterMismatchToNewList;
    public final boolean itemTypeMismatchToNewList;
    public final boolean itemTypeMismatchToSubList;
    public final boolean endOnDoubleBlank;
    public final ItemInterrupt itemInterrupt;
    public final int itemIndent;
    public final int codeIndent;

    public ListOptions(DataHolder options) {
        parserEmulationFamily = Parser.PARSER_EMULATION_FAMILY.getFrom(options);
        endOnDoubleBlank = Parser.LISTS_END_ON_DOUBLE_BLANK.getFrom(options);
        autoLoose = Parser.LISTS_AUTO_LOOSE.getFrom(options);
        looseOnPrevLooseItem = Parser.LISTS_LOOSE_ON_PREV_LOOSE_ITEM.getFrom(options);
        orderedStart = Parser.LISTS_ORDERED_LIST_MANUAL_START.getFrom(options);
        delimiterMismatchToNewList = Parser.LISTS_DELIMITER_MISMATCH_TO_NEW_LIST.getFrom(options);
        itemTypeMismatchToNewList = Parser.LISTS_ITEM_TYPE_MISMATCH_TO_NEW_LIST.getFrom(options);
        itemTypeMismatchToSubList = Parser.LISTS_ITEM_TYPE_MISMATCH_TO_SUB_LIST.getFrom(options);
        itemIndent = Parser.LISTS_ITEM_INDENT.getFrom(options);
        codeIndent = Parser.LISTS_CODE_INDENT.getFrom(options);
        itemInterrupt = new ItemInterrupt(options);
    }

    ListOptions(Mutable other) {
        parserEmulationFamily = other.parserEmulationFamily;
        endOnDoubleBlank = other.endOnDoubleBlank;
        autoLoose = other.autoLoose;
        looseOnPrevLooseItem = other.looseOnPrevLooseItem;
        orderedStart = other.orderedStart;
        delimiterMismatchToNewList = other.bulletMismatchToNewList;
        itemTypeMismatchToNewList = other.itemTypeMismatchToNewList;
        itemTypeMismatchToSubList = other.itemTypeMismatchToSubList;
        itemIndent = other.itemIndent;
        codeIndent = other.codeIndent;
        itemInterrupt = new ItemInterrupt(other.itemInterrupt);
    }

    public boolean isTightListItem(ListItem node) {
        return node.getFirstChild() == null || !autoLoose && node.isTight() || autoLoose && node.isInTightList();
    }

    public boolean isInTightListItem(Paragraph node) {
        Block parent = node.getParent();
        return parent instanceof ListItem && (!autoLoose && ((ListItem) parent).isParagraphInTightListItem(node) || autoLoose && ((ListItem) parent).isInTightList());
    }

    public boolean canInterrupt(ListBlock a, boolean isEmptyItem, boolean isItemParagraph) {
        boolean isNumberedItem = a instanceof OrderedList;
        boolean isOneItem = isNumberedItem && (!orderedStart || ((OrderedList) a).getStartNumber() == 1);

        return itemInterrupt.canInterrupt(isNumberedItem, isOneItem, isEmptyItem, isItemParagraph);
    }

    public boolean canStartSubList(ListBlock a, boolean isEmptyItem) {
        boolean isNumberedItem = a instanceof OrderedList;
        boolean isOneItem = isNumberedItem && (!orderedStart || ((OrderedList) a).getStartNumber() == 1);

        return itemInterrupt.canStartSubList(isNumberedItem, isOneItem, isEmptyItem);
    }

    public boolean startNewList(ListBlock a, ListBlock b) {
        boolean isNumberedList = a instanceof OrderedList;
        boolean isNumberedItem = b instanceof OrderedList;

        if (isNumberedList == isNumberedItem) {
            if (isNumberedList) {
                return delimiterMismatchToNewList && ((OrderedList) a).getDelimiter() != ((OrderedList) b).getDelimiter();
            } else {
                return delimiterMismatchToNewList && ((BulletList) a).getOpeningMarker() != ((BulletList) b).getOpeningMarker();
            }
        } else {
            return itemTypeMismatchToNewList;
        }
    }

    public boolean startSubList(ListBlock a, ListBlock b) {
        boolean isNumberedList = a instanceof OrderedList;
        boolean isNumberedItem = b instanceof OrderedList;

        return isNumberedList != isNumberedItem && itemTypeMismatchToSubList;
    }

    public Mutable getMutable() {
        return new Mutable(this);
    }

    public MutableDataHolder setIn(MutableDataHolder options) {
        options.set(Parser.LISTS_END_ON_DOUBLE_BLANK, endOnDoubleBlank);
        options.set(Parser.LISTS_AUTO_LOOSE, autoLoose);
        options.set(Parser.LISTS_LOOSE_ON_PREV_LOOSE_ITEM, looseOnPrevLooseItem);
        options.set(Parser.LISTS_ORDERED_LIST_MANUAL_START, orderedStart);
        options.set(Parser.LISTS_DELIMITER_MISMATCH_TO_NEW_LIST, delimiterMismatchToNewList);
        options.set(Parser.LISTS_ITEM_TYPE_MISMATCH_TO_NEW_LIST, itemTypeMismatchToNewList);
        options.set(Parser.LISTS_ITEM_TYPE_MISMATCH_TO_SUB_LIST, itemTypeMismatchToSubList);
        options.set(Parser.LISTS_ITEM_INDENT, itemIndent);
        options.set(Parser.LISTS_CODE_INDENT, codeIndent);

        itemInterrupt.setIn(options);
        return options;
    }

    @SuppressWarnings("SameParameterValue")
    public static class Mutable {
        private ParserEmulationFamily parserEmulationFamily;
        private boolean autoLoose;
        private boolean looseOnPrevLooseItem;
        private boolean orderedStart;
        private boolean bulletMismatchToNewList;
        private boolean itemTypeMismatchToNewList;
        private boolean itemTypeMismatchToSubList;
        private boolean endOnDoubleBlank;
        private int itemIndent;
        private int codeIndent;
        private MutableItemInterrupt itemInterrupt;

        public Mutable() {
            this(new ListOptions((DataHolder) null));
        }

        public Mutable(ParserEmulationFamily parserEmulationFamily) {
            this(parserEmulationFamily.getListOptions());
        }

        public Mutable(ListOptions other) {
            parserEmulationFamily = other.parserEmulationFamily;
            endOnDoubleBlank = other.endOnDoubleBlank;
            autoLoose = other.autoLoose;
            looseOnPrevLooseItem = other.looseOnPrevLooseItem;
            orderedStart = other.orderedStart;
            bulletMismatchToNewList = other.delimiterMismatchToNewList;
            itemTypeMismatchToNewList = other.itemTypeMismatchToNewList;
            itemTypeMismatchToSubList = other.itemTypeMismatchToSubList;
            itemIndent = other.itemIndent;
            codeIndent = other.codeIndent;
            itemInterrupt = new MutableItemInterrupt(other.itemInterrupt);
        }

        public Mutable(Mutable other) {
            parserEmulationFamily = other.parserEmulationFamily;
            endOnDoubleBlank = other.endOnDoubleBlank;
            autoLoose = other.autoLoose;
            looseOnPrevLooseItem = other.looseOnPrevLooseItem;
            orderedStart = other.orderedStart;
            bulletMismatchToNewList = other.bulletMismatchToNewList;
            itemTypeMismatchToNewList = other.itemTypeMismatchToNewList;
            itemTypeMismatchToSubList = other.itemTypeMismatchToSubList;
            itemIndent = other.itemIndent;
            codeIndent = other.codeIndent;
            itemInterrupt = new MutableItemInterrupt(other.itemInterrupt);
        }

        public ListOptions getListOptions() {
            return new ListOptions(this);
        }

        public MutableDataHolder setIn(MutableDataHolder options) {
            getListOptions().setIn(options);
            return options;
        }

        // @formatter:off
        public boolean isAutoLoose() { return autoLoose; }
        public boolean isBulletMismatchToNewList() { return bulletMismatchToNewList; }
        public boolean isEndOnDoubleBlank() { return endOnDoubleBlank; }
        public boolean isItemTypeMismatchToNewList() { return itemTypeMismatchToNewList; }
        public boolean isItemTypeMismatchToSubList() { return itemTypeMismatchToSubList; }
        public boolean isLooseOnPrevLooseItem() { return looseOnPrevLooseItem; }
        public boolean isOrderedStart() { return orderedStart; }
        public int getCodeIndent() { return codeIndent; }
        public int getItemIndent() { return itemIndent; }
        public MutableItemInterrupt getItemInterrupt() { return itemInterrupt; }
        public ParserEmulationFamily getParserEmulationFamily() { return parserEmulationFamily; }
        public Mutable setAutoLoose(boolean autoLoose) { this.autoLoose = autoLoose; return this; }
        public Mutable setBulletMismatchToNewList(boolean bulletMismatchToNewList) { this.bulletMismatchToNewList = bulletMismatchToNewList; return this; }
        public Mutable setCodeIndent(int codeIndent) { this.codeIndent = codeIndent; return this; }
        public Mutable setEndOnDoubleBlank(boolean endOnDoubleBlank) { this.endOnDoubleBlank = endOnDoubleBlank; return this; }
        public Mutable setItemIndent(int itemIndent) { this.itemIndent = itemIndent; return this; }
        public Mutable setItemInterrupt(MutableItemInterrupt itemInterrupt) { this.itemInterrupt = itemInterrupt; return this; }
        public Mutable setItemTypeMismatchToNewList(boolean itemTypeMatchToNewList) { this.itemTypeMismatchToNewList = itemTypeMatchToNewList; return this; }
        public Mutable setItemTypeMismatchToSubList(boolean itemTypeMismatchToSubList) { this.itemTypeMismatchToSubList = itemTypeMismatchToSubList; return this; }
        public Mutable setLooseOnPrevLooseItem(boolean looseOnPrevLooseItem) { this.looseOnPrevLooseItem = looseOnPrevLooseItem; return this; }
        public Mutable setOrderedStart(boolean orderedStart) { this.orderedStart = orderedStart; return this; }
        public Mutable setParserEmulationFamily(ParserEmulationFamily parserEmulationFamily) { this.parserEmulationFamily = parserEmulationFamily; return this; }
        // @formatter:on
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
                    }
                    else {
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
        boolean isBulletItemInterruptsParagraph() { return bulletItemInterruptsParagraph; }
        boolean isOrderedItemInterruptsParagraph() { return orderedItemInterruptsParagraph; }
        boolean isOrderedNonOneItemInterruptsParagraph() { return orderedNonOneItemInterruptsParagraph; }

        boolean isEmptyBulletItemInterruptsParagraph() { return emptyBulletItemInterruptsParagraph; }
        boolean isEmptyOrderedItemInterruptsParagraph() { return emptyOrderedItemInterruptsParagraph; }
        boolean isEmptyOrderedNonOneItemInterruptsParagraph() { return emptyOrderedNonOneItemInterruptsParagraph; }

        boolean isBulletItemInterruptsItemParagraph() { return bulletItemInterruptsItemParagraph; }
        boolean isOrderedItemInterruptsItemParagraph() { return orderedItemInterruptsItemParagraph; }
        boolean isOrderedNonOneItemInterruptsItemParagraph() { return orderedNonOneItemInterruptsItemParagraph; }

        boolean isEmptyBulletItemInterruptsItemParagraph() { return emptyBulletItemInterruptsItemParagraph; }
        boolean isEmptyOrderedItemInterruptsItemParagraph() { return emptyOrderedItemInterruptsItemParagraph; }
        boolean isEmptyOrderedNonOneItemInterruptsItemParagraph() { return emptyOrderedNonOneItemInterruptsItemParagraph; }

        boolean isEmptyBulletSubItemInterruptsItemParagraph() { return emptyBulletSubItemInterruptsItemParagraph; }
        boolean isEmptyOrderedSubItemInterruptsItemParagraph() { return emptyOrderedSubItemInterruptsItemParagraph; }
        boolean isEmptyOrderedNonOneSubItemInterruptsItemParagraph() { return emptyOrderedNonOneSubItemInterruptsItemParagraph; }

        MutableItemInterrupt setBulletItemInterruptsParagraph(boolean bulletItemInterruptsParagraph) { this.bulletItemInterruptsParagraph = bulletItemInterruptsParagraph; return this; }
        MutableItemInterrupt setOrderedItemInterruptsParagraph(boolean orderedItemInterruptsParagraph) { this.orderedItemInterruptsParagraph = orderedItemInterruptsParagraph; return this; }
        MutableItemInterrupt setOrderedNonOneItemInterruptsParagraph(boolean orderedNonOneItemInterruptsParagraph) { this.orderedNonOneItemInterruptsParagraph = orderedNonOneItemInterruptsParagraph; return this; }

        MutableItemInterrupt setEmptyBulletItemInterruptsParagraph(boolean emptyBulletItemInterruptsParagraph) { this.emptyBulletItemInterruptsParagraph = emptyBulletItemInterruptsParagraph; return this; }
        MutableItemInterrupt setEmptyOrderedItemInterruptsParagraph(boolean emptyOrderedItemInterruptsParagraph) { this.emptyOrderedItemInterruptsParagraph = emptyOrderedItemInterruptsParagraph; return this; }
        MutableItemInterrupt setEmptyOrderedNonOneItemInterruptsParagraph(boolean emptyOrderedNonOneItemInterruptsParagraph) { this.emptyOrderedNonOneItemInterruptsParagraph = emptyOrderedNonOneItemInterruptsParagraph; return this; }

        MutableItemInterrupt setBulletItemInterruptsItemParagraph(boolean bulletItemInterruptsItemParagraph) { this.bulletItemInterruptsItemParagraph = bulletItemInterruptsItemParagraph; return this; }
        MutableItemInterrupt setOrderedItemInterruptsItemParagraph(boolean orderedItemInterruptsItemParagraph) { this.orderedItemInterruptsItemParagraph = orderedItemInterruptsItemParagraph; return this; }
        MutableItemInterrupt setOrderedNonOneItemInterruptsItemParagraph(boolean orderedNonOneItemInterruptsItemParagraph) { this.orderedNonOneItemInterruptsItemParagraph = orderedNonOneItemInterruptsItemParagraph; return this; }

        MutableItemInterrupt setEmptyBulletItemInterruptsItemParagraph(boolean emptyBulletItemInterruptsItemParagraph) { this.emptyBulletItemInterruptsItemParagraph = emptyBulletItemInterruptsItemParagraph; return this; }
        MutableItemInterrupt setEmptyOrderedItemInterruptsItemParagraph(boolean emptyOrderedItemInterruptsItemParagraph) { this.emptyOrderedItemInterruptsItemParagraph = emptyOrderedItemInterruptsItemParagraph; return this; }
        MutableItemInterrupt setEmptyOrderedNonOneItemInterruptsItemParagraph(boolean emptyOrderedNonOneItemInterruptsItemParagraph) { this.emptyOrderedNonOneItemInterruptsItemParagraph = emptyOrderedNonOneItemInterruptsItemParagraph; return this; }

        MutableItemInterrupt setEmptyBulletSubItemInterruptsItemParagraph(boolean emptyBulletItemStartsSubList) { this.emptyBulletSubItemInterruptsItemParagraph = emptyBulletItemStartsSubList; return this; }
        MutableItemInterrupt setEmptyOrderedSubItemInterruptsItemParagraph(boolean emptyOrderedItemStartsSubList) { this.emptyOrderedSubItemInterruptsItemParagraph = emptyOrderedItemStartsSubList; return this; }
        MutableItemInterrupt setEmptyOrderedNonOneSubItemInterruptsItemParagraph(boolean emptyOrderedNonOneItemStartsSubList) { this.emptyOrderedNonOneSubItemInterruptsItemParagraph = emptyOrderedNonOneItemStartsSubList; return this; }

        // @formatter:on
    }
}

