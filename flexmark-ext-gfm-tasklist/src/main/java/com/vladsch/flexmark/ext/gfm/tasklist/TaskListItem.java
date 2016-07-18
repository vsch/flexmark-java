package com.vladsch.flexmark.ext.gfm.tasklist;

import com.vladsch.flexmark.internal.BlockContent;
import com.vladsch.flexmark.internal.util.sequence.BasedSequence;
import com.vladsch.flexmark.internal.util.sequence.SubSequence;
import com.vladsch.flexmark.node.BulletListItem;
import com.vladsch.flexmark.node.ListItem;

import java.util.List;

/**
 * A Task list item
 */
public class TaskListItem extends ListItem {
    protected BasedSequence taskOpeningMarker = SubSequence.NULL;

    @Override
    public void getAstExtra(StringBuilder out) {
        super.getAstExtra(out);
        segmentSpanChars(out, taskOpeningMarker, "taskOpen");
    }

    @Override
    public BasedSequence[] getSegments() {
        return new BasedSequence[] { openingMarker, taskOpeningMarker };
    }

    @Override
    public boolean isParagraphInTightListItem() {
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
        openingMarker = block.getOpeningMarker();

        takeChildren(block);
        setCharsFromContent();
    }

    public BasedSequence getTaskOpeningMarker() {
        return taskOpeningMarker;
    }

    public void setTaskOpeningMarker(BasedSequence taskOpeningMarker) {
        this.taskOpeningMarker = taskOpeningMarker;
    }

    @Override
    public void setOpeningMarker(BasedSequence openingMarker) {
        throw new IllegalStateException();
    }

    public boolean isItemDoneMarker() {
        return !taskOpeningMarker.matches("[ ]");
    }
}
