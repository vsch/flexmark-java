package com.vladsch.flexmark.internal;

import com.vladsch.flexmark.internal.util.options.DataHolder;
import com.vladsch.flexmark.node.Block;
import com.vladsch.flexmark.node.ListItem;
import com.vladsch.flexmark.node.Paragraph;
import com.vladsch.flexmark.parser.Parser;

public class ListOptions {
    final public boolean endOnDoubleBlank;
    final public boolean autoLoose;
    final public boolean orderedStart;
    final public boolean bulletMatch;
    final public boolean itemTypeMatch;
    final public boolean itemMismatchToSubitem;
    final public int fixedIndent;
    final public boolean bulletItemInterruptsParagraph;
    final public boolean bulletItemInterruptsItemParagraph;
    final public boolean orderedListItemDotOnly;
    final public boolean orderedItemInterruptsParagraph;
    final public boolean orderedItemInterruptsItemParagraph;
    final public boolean orderedNonOneItemInterruptsParagraph;
    final public boolean orderedNonOneItemInterruptsParentItemParagraph;

    public ListOptions(DataHolder options) {
        this.endOnDoubleBlank = options.get(Parser.LISTS_END_ON_DOUBLE_BLANK);
        this.autoLoose = options.get(Parser.LISTS_AUTO_LOOSE);
        this.orderedStart = options.get(Parser.LISTS_ORDERED_LIST_MANUAL_START);
        this.fixedIndent = options.get(Parser.LISTS_FIXED_INDENT);
        this.bulletMatch = options.get(Parser.LISTS_BULLET_MATCH);
        this.itemTypeMatch = options.get(Parser.LISTS_ITEM_TYPE_MATCH);
        this.bulletItemInterruptsParagraph = options.get(Parser.LISTS_BULLET_ITEM_INTERRUPTS_PARAGRAPH);
        this.bulletItemInterruptsItemParagraph = options.get(Parser.LISTS_BULLET_ITEM_INTERRUPTS_ITEM_PARAGRAPH);
        this.orderedListItemDotOnly = options.get(Parser.LISTS_ORDERED_ITEM_DOT_ONLY);
        this.orderedItemInterruptsParagraph = options.get(Parser.LISTS_ORDERED_ITEM_INTERRUPTS_PARAGRAPH);
        this.orderedItemInterruptsItemParagraph = options.get(Parser.LISTS_ORDERED_ITEM_INTERRUPTS_ITEM_PARAGRAPH);
        this.orderedNonOneItemInterruptsParagraph = options.get(Parser.LISTS_ORDERED_NON_ONE_ITEM_INTERRUPTS_PARAGRAPH);
        this.orderedNonOneItemInterruptsParentItemParagraph = options.get(Parser.LISTS_ORDERED_NON_ONE_ITEM_INTERRUPTS_PARENT_ITEM_PARAGRAPH);
        this.itemMismatchToSubitem = options.get(Parser.LISTS_ITEM_MISMATCH_TO_SUBITEM);
    }
    
    public boolean isTightListItem(ListItem node) {
        return node.getFirstChild() == null || node.isTight();
    }
    
    public boolean isInTightListItem(Paragraph node) {
        Block parent = node.getParent();
        return parent instanceof ListItem && ((ListItem) parent).isParagraphInTightListItem();
    }
}
