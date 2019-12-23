package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.ast.BlockContent;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.List;

public class OrderedListItem extends ListItem {
    public OrderedListItem() {
    }

    public OrderedListItem(BasedSequence chars) {
        super(chars);
    }

    public OrderedListItem(BasedSequence chars, List<BasedSequence> segments) {
        super(chars, segments);
    }

    public OrderedListItem(BlockContent blockContent) {
        super(blockContent);
    }

    @Override
    public boolean isOrderedItem() {
        return true;
    }
}
