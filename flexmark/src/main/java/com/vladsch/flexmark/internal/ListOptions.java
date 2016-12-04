package com.vladsch.flexmark.internal;

import com.vladsch.flexmark.ast.Block;
import com.vladsch.flexmark.ast.ListItem;
import com.vladsch.flexmark.ast.Paragraph;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.DataHolder;

public class ListOptions {

    public enum ListType {
        FIXED_INDENT,
        LIST_RELATIVE,
        LIST_ITEM_RELATIVE;

        public static final ListType DEFAULT = LIST_ITEM_RELATIVE;
    }

    public enum ListRelativeSubType {
        NONE,
        FIRST_LIST_FIXED_INDENT,
        FIRST_LIST_FIRST_ITEM_RELATIVE;

        public static final ListRelativeSubType DEFAULT = NONE;
    }

    public enum RelativeType {
        MARKER_RELATIVE,
        CONTENT_RELATIVE;

        public static final RelativeType DEFAULT = CONTENT_RELATIVE;
    }

    final public boolean endOnDoubleBlank;
    final public boolean autoLoose;
    final public boolean looseOnPrevLooseItem;
    final public boolean orderedStart;
    final public boolean bulletMatch;
    final public boolean itemTypeMatch;
    final public boolean itemMismatchToSubItem;
    final public boolean firstItemIndentBasedLimit;
    final public int firstItemIndentBasedLimitOffset;
    final public boolean listContentIndentOverridesCodeIndent;
    final public int listContentIndentOverridesCodeIndentOffset;
    final public boolean itemIndentOverMarkerToList;
    final public boolean itemIndentOverMarkerToSubItem;
    final public int fixedIndent;
    final public boolean bulletItemInterruptsParagraph;
    final public boolean bulletItemInterruptsItemParagraph;
    final public boolean emptyBulletItemInterruptsItemParagraph;
    final public boolean orderedListItemDotOnly;
    final public boolean orderedItemInterruptsParagraph;
    final public boolean orderedItemInterruptsItemParagraph;
    final public boolean orderedNonOneItemInterruptsParagraph;
    final public boolean orderedNonOneItemInterruptsParentItemParagraph;
    final public boolean itemIndentRelativeToLastItem;

    public ListOptions(DataHolder options) {
        this.endOnDoubleBlank = options.get(Parser.LISTS_END_ON_DOUBLE_BLANK);
        this.autoLoose = options.get(Parser.LISTS_AUTO_LOOSE);
        this.looseOnPrevLooseItem = options.get(Parser.LISTS_LOOSE_ON_PREV_LOOSE_ITEM);
        this.orderedStart = options.get(Parser.LISTS_ORDERED_LIST_MANUAL_START);
        this.bulletMatch = options.get(Parser.LISTS_BULLET_MATCH);
        this.itemTypeMatch = options.get(Parser.LISTS_ITEM_TYPE_MATCH);
        this.itemMismatchToSubItem = options.get(Parser.LISTS_ITEM_MISMATCH_TO_SUB_ITEM) & this.itemTypeMatch;
        this.bulletItemInterruptsParagraph = options.get(Parser.LISTS_BULLET_ITEM_INTERRUPTS_PARAGRAPH);
        this.bulletItemInterruptsItemParagraph = options.get(Parser.LISTS_BULLET_ITEM_INTERRUPTS_ITEM_PARAGRAPH);
        this.emptyBulletItemInterruptsItemParagraph = options.get(Parser.LISTS_EMPTY_BULLET_ITEM_INTERRUPTS_ITEM_PARAGRAPH);
        this.orderedListItemDotOnly = options.get(Parser.LISTS_ORDERED_ITEM_DOT_ONLY);
        this.orderedItemInterruptsParagraph = options.get(Parser.LISTS_ORDERED_ITEM_INTERRUPTS_PARAGRAPH);
        this.orderedItemInterruptsItemParagraph = options.get(Parser.LISTS_ORDERED_ITEM_INTERRUPTS_ITEM_PARAGRAPH);
        this.orderedNonOneItemInterruptsParagraph = options.get(Parser.LISTS_ORDERED_NON_ONE_ITEM_INTERRUPTS_PARAGRAPH);
        this.orderedNonOneItemInterruptsParentItemParagraph = options.get(Parser.LISTS_ORDERED_NON_ONE_ITEM_INTERRUPTS_PARENT_ITEM_PARAGRAPH);
        this.fixedIndent = options.get(Parser.LISTS_FIXED_INDENT);
        if (fixedIndent <= 0) {
            this.firstItemIndentBasedLimit = options.get(Parser.LISTS_FIRST_ITEM_INDENT_BASED_LIMIT);
            this.firstItemIndentBasedLimitOffset = options.get(Parser.LISTS_FIRST_ITEM_INDENT_BASED_LIMIT_OFFSET);
            this.listContentIndentOverridesCodeIndent = options.get(Parser.LISTS_CONTENT_INDENT_OVERRIDES_CODE_INDENT);
            this.listContentIndentOverridesCodeIndentOffset = options.get(Parser.LISTS_CONTENT_INDENT_OVERRIDES_CODE_INDENT_OFFSET);
            this.itemIndentOverMarkerToList = options.get(Parser.LISTS_ITEM_INDENT_OVER_MARKER_TO_LIST);
            this.itemIndentOverMarkerToSubItem = options.get(Parser.LISTS_ITEM_INDENT_OVER_MARKER_TO_SUB_ITEM);
            this.itemIndentRelativeToLastItem = options.get(Parser.LISTS_ITEM_INDENT_RELATIVE_TO_LAST_ITEM);
        } else {
            this.firstItemIndentBasedLimitOffset = options.get(Parser.LISTS_FIRST_ITEM_INDENT_BASED_LIMIT_OFFSET);
            this.listContentIndentOverridesCodeIndentOffset = options.get(Parser.LISTS_CONTENT_INDENT_OVERRIDES_CODE_INDENT_OFFSET);

            // turn all of these off, they are not fixed indent
            this.firstItemIndentBasedLimit = false;
            this.listContentIndentOverridesCodeIndent = false;
            this.itemIndentOverMarkerToList = false;
            this.itemIndentOverMarkerToSubItem = false;
            this.itemIndentRelativeToLastItem = false;
        }
    }

    public boolean isTightListItem(ListItem node) {
        return node.getFirstChild() == null || !autoLoose && node.isTight() || autoLoose && node.isInTightList();
    }

    public boolean isInTightListItem(Paragraph node) {
        Block parent = node.getParent();
        return parent instanceof ListItem && (!autoLoose && ((ListItem) parent).isParagraphInTightListItem(node) || autoLoose && ((ListItem) parent).isInTightList());
    }
}
