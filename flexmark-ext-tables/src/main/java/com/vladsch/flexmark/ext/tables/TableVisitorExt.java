package com.vladsch.flexmark.ext.tables;

import com.vladsch.flexmark.util.ast.VisitHandler;

public class TableVisitorExt {
    public static <V extends TableVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<TableBlock>(TableBlock.class, visitor::visit),
                new VisitHandler<TableHead>(TableHead.class, visitor::visit),
                new VisitHandler<TableSeparator>(TableSeparator.class, visitor::visit),
                new VisitHandler<TableBody>(TableBody.class, visitor::visit),
                new VisitHandler<TableRow>(TableRow.class, visitor::visit),
                new VisitHandler<TableCell>(TableCell.class, visitor::visit),
                new VisitHandler<TableCaption>(TableCaption.class, visitor::visit),
        };
    }
}
