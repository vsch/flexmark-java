package com.vladsch.flexmark.node;

import com.vladsch.flexmark.internal.BlockContent;
import com.vladsch.flexmark.internal.util.BasedSequence;

import java.util.List;

public class BulletListItem extends ListItem {

    public BulletListItem() {
    }

    public BulletListItem(BasedSequence chars) {
        super(chars);
    }

    public BulletListItem(BasedSequence chars, List<BasedSequence> segments) {
        super(chars, segments);
    }

    public BulletListItem(BlockContent blockContent) {
        super(blockContent);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
