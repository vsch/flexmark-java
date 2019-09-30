package com.vladsch.flexmark.ext.tables;

import com.vladsch.flexmark.util.ast.VisitHandler;

public class TableVisitorExt {
    public static <V extends TableVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<>(TableBlock.class, visitor::visit),
                new VisitHandler<>(TableHead.class, visitor::visit),
                new VisitHandler<>(TableSeparator.class, visitor::visit),
                new VisitHandler<>(TableBody.class, visitor::visit),
                new VisitHandler<>(TableRow.class, visitor::visit),
                new VisitHandler<>(TableCell.class, visitor::visit),
                new VisitHandler<>(TableCaption.class, visitor::visit),
        };
    }
}
