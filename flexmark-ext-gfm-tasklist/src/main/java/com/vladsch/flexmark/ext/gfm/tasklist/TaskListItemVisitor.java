package com.vladsch.flexmark.ext.gfm.tasklist;

public interface TaskListItemVisitor {
    void visit(final TaskListItem node);
}
