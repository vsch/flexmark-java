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
    final public int fixedIndent;
    final public boolean listInterruptsParagraph;
    final public boolean orderedListDotOnly;
    final public boolean orderedListInterruptsParagraph;
    final public boolean orderedSubItemInterruptsParentItem;

    public ListOptions(DataHolder options) {
        this.endOnDoubleBlank = options.get(Parser.LISTS_END_ON_DOUBLE_BLANK);
        this.autoLoose = options.get(Parser.LISTS_AUTO_LOOSE);
        this.orderedStart = options.get(Parser.ORDERED_LIST_START);
        this.fixedIndent = options.get(Parser.LISTS_FIXED_INDENT);
        this.bulletMatch = options.get(Parser.LISTS_BULLET_MATCH);
        this.listInterruptsParagraph = options.get(Parser.LIST_INTERRUPTS_PARAGRAPH);
        this.orderedListDotOnly = options.get(Parser.ORDERED_LIST_DOT_ONLY);
        this.orderedListInterruptsParagraph = options.get(Parser.ORDERED_LIST_INTERRUPTS_PARAGRAPH);
        this.orderedSubItemInterruptsParentItem = options.get(Parser.ORDERED_SUBITEM_INTERRUPTS_PARENT_ITEM);
    }
    
    public boolean isTightListItem(ListItem node) {
        return node.getFirstChild() == null || node.isTight();
    }
    
    public boolean isInTightListItem(Paragraph node) {
        Block parent = node.getParent();
        return parent instanceof ListItem && ((ListItem) parent).isParagraphInTightListItem();
    }
}
