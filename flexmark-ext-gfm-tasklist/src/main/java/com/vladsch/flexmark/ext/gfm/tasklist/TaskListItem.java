package com.vladsch.flexmark.ext.gfm.tasklist;

import com.vladsch.flexmark.internal.BlockContent;
import com.vladsch.flexmark.internal.util.BasedSequence;
import com.vladsch.flexmark.node.BulletListItem;
import com.vladsch.flexmark.node.Node;

import java.util.List;

/**
 * A Task list item
 */
public class TaskListItem extends BulletListItem {
    @Override
    public boolean isParagraphInTightList() {
        // we handle our own paragraph wrapping
        return true;
    }

    public TaskListItem() {
    }

    public TaskListItem(BasedSequence chars) {
        super(chars);
    }

    public TaskListItem(BasedSequence chars, List<BasedSequence> segments) {
        super(chars, segments);
    }

    public TaskListItem(BlockContent blockContent) {
        super(blockContent);
    }

    public TaskListItem(BulletListItem block) {
        super(block.getChars(), block.getContentLines());

        takeChildren(block);
        setCharsFromContent();
    }

    public boolean isItemDone() {
        Node firstChild = getFirstChild();
        return firstChild instanceof TaskListItemMarker && ((TaskListItemMarker) firstChild).isItemDoneMarker();
    }
}
