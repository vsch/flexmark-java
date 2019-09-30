package com.vladsch.flexmark.ext.gfm.tasklist;

import com.vladsch.flexmark.util.ast.VisitHandler;

public class TaskListItemVisitorExt {
    public static <V extends TaskListItemVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<>(TaskListItem.class, visitor::visit),
        };
    }
}
