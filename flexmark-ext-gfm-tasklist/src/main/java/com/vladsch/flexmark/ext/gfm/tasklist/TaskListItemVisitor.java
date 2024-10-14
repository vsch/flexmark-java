package com.vladsch.flexmark.ext.gfm.tasklist;

interface TaskListItemVisitor {
  void visit(TaskListItem node);
}
