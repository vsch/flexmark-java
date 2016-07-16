package com.vladsch.flexmark.node;

import com.vladsch.flexmark.internal.BlockContent;
import com.vladsch.flexmark.internal.util.sequence.BasedSequence;

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

}
