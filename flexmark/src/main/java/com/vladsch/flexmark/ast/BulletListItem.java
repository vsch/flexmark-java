package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.ast.BlockContent;
import com.vladsch.flexmark.util.sequence.BasedSequence;

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
}
