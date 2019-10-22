package com.vladsch.flexmark.parser;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.MutableDataSetter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

@SuppressWarnings("WeakerAccess")
public class ListOptions implements MutableDataSetter {
    protected @NotNull ParserEmulationProfile myParserEmulationProfile;
    protected @NotNull ItemInterrupt itemInterrupt;
    protected boolean autoLoose;
    protected boolean autoLooseOneLevelLists;
    protected boolean delimiterMismatchToNewList;
    protected boolean endOnDoubleBlank;
    protected boolean itemMarkerSpace;
    protected boolean itemTypeMismatchToNewList;
    protected boolean itemTypeMismatchToSubList;
    protected boolean looseWhenPrevHasTrailingBlankLine;
    protected boolean looseWhenLastItemPrevHasTrailingBlankLine;
    protected boolean looseWhenHasNonListChildren;
    protected boolean looseWhenBlankLineFollowsItemParagraph;
    protected boolean looseWhenHasLooseSubItem;
    protected boolean looseWhenHasTrailingBlankLine;
    protected boolean looseWhenContainsBlankLine;
    protected boolean numberedItemMarkerSuffixed;
    protected boolean orderedItemDotOnly;
    protected boolean orderedListManualStart;
    protected boolean itemContentAfterSuffix;
    protected @NotNull String itemPrefixChars;
    protected int codeIndent;
    protected int itemIndent;
    protected int newItemCodeIndent;
    protected @NotNull String[] itemMarkerSuffixes;

    public ListOptions() {
        this((DataHolder) null);
    }

    private ListOptions(@Nullable DataHolder options) {
        myParserEmulationProfile = Parser.PARSER_EMULATION_PROFILE.get(options);
        itemInterrupt = new ItemInterrupt(options);

        autoLoose = Parser.LISTS_AUTO_LOOSE.get(options);
        autoLooseOneLevelLists = Parser.LISTS_AUTO_LOOSE_ONE_LEVEL_LISTS.get(options);
        delimiterMismatchToNewList = Parser.LISTS_DELIMITER_MISMATCH_TO_NEW_LIST.get(options);
        endOnDoubleBlank = Parser.LISTS_END_ON_DOUBLE_BLANK.get(options);
        itemMarkerSpace = Parser.LISTS_ITEM_MARKER_SPACE.get(options);
        itemTypeMismatchToNewList = Parser.LISTS_ITEM_TYPE_MISMATCH_TO_NEW_LIST.get(options);
        itemTypeMismatchToSubList = Parser.LISTS_ITEM_TYPE_MISMATCH_TO_SUB_LIST.get(options);
        looseWhenPrevHasTrailingBlankLine = Parser.LISTS_LOOSE_WHEN_PREV_HAS_TRAILING_BLANK_LINE.get(options);
        looseWhenLastItemPrevHasTrailingBlankLine = Parser.LISTS_LOOSE_WHEN_LAST_ITEM_PREV_HAS_TRAILING_BLANK_LINE.get(options);
        looseWhenHasNonListChildren = Parser.LISTS_LOOSE_WHEN_HAS_NON_LIST_CHILDREN.get(options);
        looseWhenBlankLineFollowsItemParagraph = Parser.LISTS_LOOSE_WHEN_BLANK_LINE_FOLLOWS_ITEM_PARAGRAPH.get(options);
        looseWhenHasLooseSubItem = Parser.LISTS_LOOSE_WHEN_HAS_LOOSE_SUB_ITEM.get(options);
        looseWhenHasTrailingBlankLine = Parser.LISTS_LOOSE_WHEN_HAS_TRAILING_BLANK_LINE.get(options);
        looseWhenContainsBlankLine = Parser.LISTS_LOOSE_WHEN_CONTAINS_BLANK_LINE.get(options);
        numberedItemMarkerSuffixed = Parser.LISTS_NUMBERED_ITEM_MARKER_SUFFIXED.get(options);
        orderedItemDotOnly = Parser.LISTS_ORDERED_ITEM_DOT_ONLY.get(options);
        orderedListManualStart = Parser.LISTS_ORDERED_LIST_MANUAL_START.get(options);
        itemContentAfterSuffix = Parser.LISTS_ITEM_CONTENT_AFTER_SUFFIX.get(options);
        itemPrefixChars = Parser.LISTS_ITEM_PREFIX_CHARS.get(options);

        codeIndent = Parser.LISTS_CODE_INDENT.get(options);
        itemIndent = Parser.LISTS_ITEM_INDENT.get(options);
        newItemCodeIndent = Parser.LISTS_NEW_ITEM_CODE_INDENT.get(options);
        itemMarkerSuffixes = Parser.LISTS_ITEM_MARKER_SUFFIXES.get(options);
    }

    ListOptions(@NotNull ListOptions other) {
        myParserEmulationProfile = other.getParserEmulationProfile();
        itemInterrupt = new ItemInterrupt(other.getItemInterrupt());

        autoLoose = other.isAutoLoose();
        autoLooseOneLevelLists = other.isAutoLooseOneLevelLists();
        delimiterMismatchToNewList = other.isDelimiterMismatchToNewList();
        endOnDoubleBlank = other.isEndOnDoubleBlank();
        itemMarkerSpace = other.isItemMarkerSpace();
        itemTypeMismatchToNewList = other.isItemTypeMismatchToNewList();
        itemTypeMismatchToSubList = other.isItemTypeMismatchToSubList();
        looseWhenPrevHasTrailingBlankLine = other.isLooseWhenPrevHasTrailingBlankLine();
        looseWhenLastItemPrevHasTrailingBlankLine = other.isLooseWhenLastItemPrevHasTrailingBlankLine();
        looseWhenHasNonListChildren = other.isLooseWhenHasNonListChildren();
        looseWhenBlankLineFollowsItemParagraph = other.isLooseWhenBlankLineFollowsItemParagraph();
        looseWhenHasLooseSubItem = other.isLooseWhenHasLooseSubItem();
        looseWhenHasTrailingBlankLine = other.isLooseWhenHasTrailingBlankLine();
        looseWhenContainsBlankLine = other.isLooseWhenContainsBlankLine();
        numberedItemMarkerSuffixed = other.isNumberedItemMarkerSuffixed();
        orderedItemDotOnly = other.isOrderedItemDotOnly();
        orderedListManualStart = other.isOrderedListManualStart();
        itemContentAfterSuffix = other.isItemContentAfterSuffix();
        itemPrefixChars = other.getItemPrefixChars();

        codeIndent = other.getCodeIndent();
        itemIndent = other.getItemIndent();
        newItemCodeIndent = other.getNewItemCodeIndent();
        itemMarkerSuffixes = other.getItemMarkerSuffixes();
    }

    /**
     * @param options options
     * @return new list options
     * @deprecated use get()
     */
    @Deprecated
    public static @NotNull ListOptions getFrom(DataHolder options) {
        return get(options);
    }

    public static @NotNull ListOptions get(DataHolder options) {
        return new ListOptions(options);
    }

    public boolean isTightListItem(@NotNull ListItem node) {
        if (node.isLoose()) {
            return false;
        }

        boolean autoLoose = isAutoLoose();
        if (autoLoose && isAutoLooseOneLevelLists()) {
            boolean singleLevel = node.getAncestorOfType(ListItem.class) == null && node.getChildOfType(ListBlock.class) == null;
            return node.getFirstChild() == null || !singleLevel && node.isTight() || singleLevel && node.isInTightList();
        } else {
            return node.getFirstChild() == null || !autoLoose && node.isTight() || autoLoose && node.isInTightList();
        }
    }

    public boolean isInTightListItem(@NotNull Paragraph node) {
        Block parent = node.getParent();
        if (!(parent instanceof ListItem)) return false;

        ListItem listItem = (ListItem) parent;
        if (!listItem.isItemParagraph(node)) return false;

        boolean autoLoose = isAutoLoose();
        if (autoLoose && isAutoLooseOneLevelLists()) {
            return isTightListItem(listItem);
        }
        return !autoLoose && listItem.isParagraphInTightListItem(node) || autoLoose && listItem.isInTightList();
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean canInterrupt(@NotNull ListBlock a, boolean isEmptyItem, boolean isItemParagraph) {
        boolean isNumberedItem = a instanceof OrderedList;
        boolean isOneItem = isNumberedItem && (!isOrderedListManualStart() || ((OrderedList) a).getStartNumber() == 1);

        return getItemInterrupt().canInterrupt(isNumberedItem, isOneItem, isEmptyItem, isItemParagraph);
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean canStartSubList(@NotNull ListBlock a, boolean isEmptyItem) {
        boolean isNumberedItem = a instanceof OrderedList;
        boolean isOneItem = isNumberedItem && (!isOrderedListManualStart() || ((OrderedList) a).getStartNumber() == 1);

        return getItemInterrupt().canStartSubList(isNumberedItem, isOneItem, isEmptyItem);
    }

    public boolean startNewList(@NotNull ListBlock a, @NotNull ListBlock b) {
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

    public boolean startSubList(@NotNull ListBlock a, @NotNull ListBlock b) {
        boolean isNumberedList = a instanceof OrderedList;
        boolean isNumberedItem = b instanceof OrderedList;

        return isNumberedList != isNumberedItem && isItemTypeMismatchToSubList();
    }

    public MutableListOptions getMutable() {
        return new MutableListOptions(this);
    }

    @NotNull
    public MutableDataHolder setIn(@NotNull MutableDataHolder options) {
        options.set(Parser.PARSER_EMULATION_PROFILE, getParserEmulationProfile());
        getItemInterrupt().setIn(options);

        options.set(Parser.LISTS_AUTO_LOOSE, autoLoose);
        options.set(Parser.LISTS_AUTO_LOOSE_ONE_LEVEL_LISTS, autoLooseOneLevelLists);
        options.set(Parser.LISTS_DELIMITER_MISMATCH_TO_NEW_LIST, delimiterMismatchToNewList);
        options.set(Parser.LISTS_END_ON_DOUBLE_BLANK, endOnDoubleBlank);
        options.set(Parser.LISTS_ITEM_MARKER_SPACE, itemMarkerSpace);
        options.set(Parser.LISTS_ITEM_TYPE_MISMATCH_TO_NEW_LIST, itemTypeMismatchToNewList);
        options.set(Parser.LISTS_ITEM_TYPE_MISMATCH_TO_SUB_LIST, itemTypeMismatchToSubList);
        options.set(Parser.LISTS_LOOSE_WHEN_PREV_HAS_TRAILING_BLANK_LINE, looseWhenPrevHasTrailingBlankLine);
        options.set(Parser.LISTS_LOOSE_WHEN_LAST_ITEM_PREV_HAS_TRAILING_BLANK_LINE, looseWhenLastItemPrevHasTrailingBlankLine);
        options.set(Parser.LISTS_LOOSE_WHEN_HAS_NON_LIST_CHILDREN, looseWhenHasNonListChildren);
        options.set(Parser.LISTS_LOOSE_WHEN_BLANK_LINE_FOLLOWS_ITEM_PARAGRAPH, looseWhenBlankLineFollowsItemParagraph);
        options.set(Parser.LISTS_LOOSE_WHEN_HAS_LOOSE_SUB_ITEM, looseWhenHasLooseSubItem);
        options.set(Parser.LISTS_LOOSE_WHEN_HAS_TRAILING_BLANK_LINE, looseWhenHasTrailingBlankLine);
        options.set(Parser.LISTS_LOOSE_WHEN_CONTAINS_BLANK_LINE, looseWhenContainsBlankLine);
        options.set(Parser.LISTS_NUMBERED_ITEM_MARKER_SUFFIXED, numberedItemMarkerSuffixed);
        options.set(Parser.LISTS_ORDERED_ITEM_DOT_ONLY, orderedItemDotOnly);
        options.set(Parser.LISTS_ORDERED_LIST_MANUAL_START, orderedListManualStart);

        options.set(Parser.LISTS_CODE_INDENT, codeIndent);
        options.set(Parser.LISTS_ITEM_INDENT, itemIndent);
        options.set(Parser.LISTS_NEW_ITEM_CODE_INDENT, newItemCodeIndent);
        options.set(Parser.LISTS_ITEM_MARKER_SUFFIXES, itemMarkerSuffixes);
        options.set(Parser.LISTS_ITEM_CONTENT_AFTER_SUFFIX, itemContentAfterSuffix);
        options.set(Parser.LISTS_ITEM_PREFIX_CHARS, itemPrefixChars);

        return options;
    }

    public static void addItemMarkerSuffixes(@NotNull MutableDataHolder options, String... itemMarkerSuffixes) {
        String[] suffixes = Parser.LISTS_ITEM_MARKER_SUFFIXES.get(options);
        int addSuffixCount = itemMarkerSuffixes.length;

        int iMax = itemMarkerSuffixes.length;
        for (String suffix : suffixes) {
            for (int i = 0; i < iMax; i++) {
                String addSuffix = itemMarkerSuffixes[i];
                if (addSuffix != null && addSuffix.equals(suffix)) {
                    addSuffixCount--;
                    itemMarkerSuffixes[i] = null;
                    break;
                }
            }

            if (addSuffixCount == 0) break;
        }

        if (addSuffixCount > 0) {
            String[] newSuffixes = new String[suffixes.length + addSuffixCount];
            System.arraycopy(suffixes, 0, newSuffixes, 0, suffixes.length);

            int suffixIndex = suffixes.length;
            for (String addSuffix : itemMarkerSuffixes) {
                if (addSuffix != null) {
                    newSuffixes[suffixIndex++] = addSuffix;
                }
            }
            options.set(Parser.LISTS_ITEM_MARKER_SUFFIXES, newSuffixes);
        }
    }

    public @NotNull ParserEmulationProfile getParserEmulationProfile() {
        return myParserEmulationProfile;
    }

    public @NotNull ItemInterrupt getItemInterrupt() {
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

    public boolean isItemContentAfterSuffix() {
        return itemContentAfterSuffix;
    }

    @NotNull
    public String getItemPrefixChars() {
        return itemPrefixChars;
    }

    public boolean isItemTypeMismatchToSubList() {
        return itemTypeMismatchToSubList;
    }

    public boolean isLooseWhenPrevHasTrailingBlankLine() {
        return looseWhenPrevHasTrailingBlankLine;
    }

    public boolean isLooseWhenLastItemPrevHasTrailingBlankLine() {
        return looseWhenLastItemPrevHasTrailingBlankLine;
    }

    public boolean isLooseWhenHasNonListChildren() {
        return looseWhenHasNonListChildren;
    }

    public boolean isLooseWhenHasLooseSubItem() {
        return looseWhenHasLooseSubItem;
    }

    public boolean isLooseWhenHasTrailingBlankLine() {
        return looseWhenHasTrailingBlankLine;
    }

    public boolean isLooseWhenContainsBlankLine() {
        return looseWhenContainsBlankLine;
    }

    public boolean isLooseWhenBlankLineFollowsItemParagraph() {
        return looseWhenBlankLineFollowsItemParagraph;
    }

    public boolean isOrderedItemDotOnly() {
        return orderedItemDotOnly;
    }

    public boolean isOrderedListManualStart() {
        return orderedListManualStart;
    }

    public boolean isNumberedItemMarkerSuffixed() {
        return numberedItemMarkerSuffixed;
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

    public @NotNull String[] getItemMarkerSuffixes() {
        return itemMarkerSuffixes;
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

        public ItemInterrupt(@Nullable DataHolder options) {
            bulletItemInterruptsParagraph = Parser.LISTS_BULLET_ITEM_INTERRUPTS_PARAGRAPH.get(options);
            orderedItemInterruptsParagraph = Parser.LISTS_ORDERED_ITEM_INTERRUPTS_PARAGRAPH.get(options);
            orderedNonOneItemInterruptsParagraph = Parser.LISTS_ORDERED_NON_ONE_ITEM_INTERRUPTS_PARAGRAPH.get(options);

            emptyBulletItemInterruptsParagraph = Parser.LISTS_EMPTY_BULLET_ITEM_INTERRUPTS_PARAGRAPH.get(options);
            emptyOrderedItemInterruptsParagraph = Parser.LISTS_EMPTY_ORDERED_ITEM_INTERRUPTS_PARAGRAPH.get(options);
            emptyOrderedNonOneItemInterruptsParagraph = Parser.LISTS_EMPTY_ORDERED_NON_ONE_ITEM_INTERRUPTS_PARAGRAPH.get(options);

            bulletItemInterruptsItemParagraph = Parser.LISTS_BULLET_ITEM_INTERRUPTS_ITEM_PARAGRAPH.get(options);
            orderedItemInterruptsItemParagraph = Parser.LISTS_ORDERED_ITEM_INTERRUPTS_ITEM_PARAGRAPH.get(options);
            orderedNonOneItemInterruptsItemParagraph = Parser.LISTS_ORDERED_NON_ONE_ITEM_INTERRUPTS_ITEM_PARAGRAPH.get(options);

            emptyBulletItemInterruptsItemParagraph = Parser.LISTS_EMPTY_BULLET_ITEM_INTERRUPTS_ITEM_PARAGRAPH.get(options);
            emptyOrderedItemInterruptsItemParagraph = Parser.LISTS_EMPTY_ORDERED_ITEM_INTERRUPTS_ITEM_PARAGRAPH.get(options);
            emptyOrderedNonOneItemInterruptsItemParagraph = Parser.LISTS_EMPTY_ORDERED_NON_ONE_ITEM_INTERRUPTS_ITEM_PARAGRAPH.get(options);

            emptyBulletSubItemInterruptsItemParagraph = Parser.LISTS_EMPTY_BULLET_SUB_ITEM_INTERRUPTS_ITEM_PARAGRAPH.get(options);
            emptyOrderedSubItemInterruptsItemParagraph = Parser.LISTS_EMPTY_ORDERED_SUB_ITEM_INTERRUPTS_ITEM_PARAGRAPH.get(options);
            emptyOrderedNonOneSubItemInterruptsItemParagraph = Parser.LISTS_EMPTY_ORDERED_NON_ONE_SUB_ITEM_INTERRUPTS_ITEM_PARAGRAPH.get(options);
        }

        public void setIn(@NotNull MutableDataHolder options) {
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

        public ItemInterrupt(@NotNull ItemInterrupt other) {
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ItemInterrupt)) return false;

            ItemInterrupt interrupt = (ItemInterrupt) o;

            if (bulletItemInterruptsParagraph != interrupt.bulletItemInterruptsParagraph) return false;
            if (orderedItemInterruptsParagraph != interrupt.orderedItemInterruptsParagraph) return false;
            if (orderedNonOneItemInterruptsParagraph != interrupt.orderedNonOneItemInterruptsParagraph) return false;
            if (emptyBulletItemInterruptsParagraph != interrupt.emptyBulletItemInterruptsParagraph) return false;
            if (emptyOrderedItemInterruptsParagraph != interrupt.emptyOrderedItemInterruptsParagraph) return false;
            if (emptyOrderedNonOneItemInterruptsParagraph != interrupt.emptyOrderedNonOneItemInterruptsParagraph) return false;
            if (bulletItemInterruptsItemParagraph != interrupt.bulletItemInterruptsItemParagraph) return false;
            if (orderedItemInterruptsItemParagraph != interrupt.orderedItemInterruptsItemParagraph) return false;
            if (orderedNonOneItemInterruptsItemParagraph != interrupt.orderedNonOneItemInterruptsItemParagraph) return false;
            if (emptyBulletItemInterruptsItemParagraph != interrupt.emptyBulletItemInterruptsItemParagraph) return false;
            if (emptyOrderedItemInterruptsItemParagraph != interrupt.emptyOrderedItemInterruptsItemParagraph) return false;
            if (emptyOrderedNonOneItemInterruptsItemParagraph != interrupt.emptyOrderedNonOneItemInterruptsItemParagraph) return false;
            if (emptyBulletSubItemInterruptsItemParagraph != interrupt.emptyBulletSubItemInterruptsItemParagraph) return false;
            if (emptyOrderedSubItemInterruptsItemParagraph != interrupt.emptyOrderedSubItemInterruptsItemParagraph) return false;
            return emptyOrderedNonOneSubItemInterruptsItemParagraph == interrupt.emptyOrderedNonOneSubItemInterruptsItemParagraph;
        }

        @Override
        public int hashCode() {
            int result = (bulletItemInterruptsParagraph ? 1 : 0);
            result = 31 * result + (orderedItemInterruptsParagraph ? 1 : 0);
            result = 31 * result + (orderedNonOneItemInterruptsParagraph ? 1 : 0);
            result = 31 * result + (emptyBulletItemInterruptsParagraph ? 1 : 0);
            result = 31 * result + (emptyOrderedItemInterruptsParagraph ? 1 : 0);
            result = 31 * result + (emptyOrderedNonOneItemInterruptsParagraph ? 1 : 0);
            result = 31 * result + (bulletItemInterruptsItemParagraph ? 1 : 0);
            result = 31 * result + (orderedItemInterruptsItemParagraph ? 1 : 0);
            result = 31 * result + (orderedNonOneItemInterruptsItemParagraph ? 1 : 0);
            result = 31 * result + (emptyBulletItemInterruptsItemParagraph ? 1 : 0);
            result = 31 * result + (emptyOrderedItemInterruptsItemParagraph ? 1 : 0);
            result = 31 * result + (emptyOrderedNonOneItemInterruptsItemParagraph ? 1 : 0);
            result = 31 * result + (emptyBulletSubItemInterruptsItemParagraph ? 1 : 0);
            result = 31 * result + (emptyOrderedSubItemInterruptsItemParagraph ? 1 : 0);
            result = 31 * result + (emptyOrderedNonOneSubItemInterruptsItemParagraph ? 1 : 0);
            return result;
        }
    }

    @SuppressWarnings("SameParameterValue")
    public static class MutableItemInterrupt extends ItemInterrupt {
        public MutableItemInterrupt() { }

        public MutableItemInterrupt(@Nullable DataHolder options) {
            super(options);
        }

        public MutableItemInterrupt(@NotNull ItemInterrupt other) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListOptions)) return false;

        ListOptions that = (ListOptions) o;

        if (myParserEmulationProfile != that.myParserEmulationProfile) return false;
        if (autoLoose != that.autoLoose) return false;
        if (autoLooseOneLevelLists != that.autoLooseOneLevelLists) return false;
        if (delimiterMismatchToNewList != that.delimiterMismatchToNewList) return false;
        if (endOnDoubleBlank != that.endOnDoubleBlank) return false;
        if (itemMarkerSpace != that.itemMarkerSpace) return false;
        if (itemTypeMismatchToNewList != that.itemTypeMismatchToNewList) return false;
        if (itemTypeMismatchToSubList != that.itemTypeMismatchToSubList) return false;
        if (looseWhenPrevHasTrailingBlankLine != that.looseWhenPrevHasTrailingBlankLine) return false;
        if (looseWhenLastItemPrevHasTrailingBlankLine != that.looseWhenLastItemPrevHasTrailingBlankLine) return false;
        if (looseWhenHasNonListChildren != that.looseWhenHasNonListChildren) return false;
        if (looseWhenBlankLineFollowsItemParagraph != that.looseWhenBlankLineFollowsItemParagraph) return false;
        if (looseWhenHasLooseSubItem != that.looseWhenHasLooseSubItem) return false;
        if (looseWhenHasTrailingBlankLine != that.looseWhenHasTrailingBlankLine) return false;
        if (looseWhenContainsBlankLine != that.looseWhenContainsBlankLine) return false;
        if (numberedItemMarkerSuffixed != that.numberedItemMarkerSuffixed) return false;
        if (orderedItemDotOnly != that.orderedItemDotOnly) return false;
        if (orderedListManualStart != that.orderedListManualStart) return false;
        if (codeIndent != that.codeIndent) return false;
        if (itemIndent != that.itemIndent) return false;
        if (newItemCodeIndent != that.newItemCodeIndent) return false;
        if (itemMarkerSuffixes != that.itemMarkerSuffixes) return false;
        if (itemContentAfterSuffix != that.itemContentAfterSuffix) return false;
        if (!itemPrefixChars.equals(that.itemPrefixChars)) return false;
        return itemInterrupt.equals(that.itemInterrupt);
    }

    @Override
    public int hashCode() {
        int result = myParserEmulationProfile.hashCode();
        result = 31 * result + itemInterrupt.hashCode();
        result = 31 * result + (autoLoose ? 1 : 0);
        result = 31 * result + (autoLooseOneLevelLists ? 1 : 0);
        result = 31 * result + (delimiterMismatchToNewList ? 1 : 0);
        result = 31 * result + (endOnDoubleBlank ? 1 : 0);
        result = 31 * result + (itemMarkerSpace ? 1 : 0);
        result = 31 * result + (itemTypeMismatchToNewList ? 1 : 0);
        result = 31 * result + (itemTypeMismatchToSubList ? 1 : 0);
        result = 31 * result + (looseWhenPrevHasTrailingBlankLine ? 1 : 0);
        result = 31 * result + (looseWhenLastItemPrevHasTrailingBlankLine ? 1 : 0);
        result = 31 * result + (looseWhenHasNonListChildren ? 1 : 0);
        result = 31 * result + (looseWhenBlankLineFollowsItemParagraph ? 1 : 0);
        result = 31 * result + (looseWhenHasLooseSubItem ? 1 : 0);
        result = 31 * result + (looseWhenHasTrailingBlankLine ? 1 : 0);
        result = 31 * result + (looseWhenContainsBlankLine ? 1 : 0);
        result = 31 * result + (numberedItemMarkerSuffixed ? 1 : 0);
        result = 31 * result + (orderedItemDotOnly ? 1 : 0);
        result = 31 * result + (orderedListManualStart ? 1 : 0);
        result = 31 * result + (itemContentAfterSuffix ? 1 : 0);
        result = 31 * result + itemPrefixChars.hashCode();
        result = 31 * result + codeIndent;
        result = 31 * result + itemIndent;
        result = 31 * result + newItemCodeIndent;
        result = 31 * result + Arrays.hashCode(itemMarkerSuffixes);
        return result;
    }
}

