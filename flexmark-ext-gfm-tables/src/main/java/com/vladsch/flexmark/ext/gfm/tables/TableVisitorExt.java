package com.vladsch.flexmark.ext.gfm.tables;

import com.vladsch.flexmark.ast.VisitHandler;
import com.vladsch.flexmark.ast.Visitor;

public class TableVisitorExt {
    public static <V extends TableVisitor> VisitHandler<?>[] VISIT_HANDLERS(final V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<TableCell>(TableCell.class, new Visitor<TableCell>() {
                    @Override
                    public void visit(TableCell node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<TableBlock>(TableBlock.class, new Visitor<TableBlock>() {
                    @Override
                    public void visit(TableBlock node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<TableHead>(TableHead.class, new Visitor<TableHead>() {
                    @Override
                    public void visit(TableHead node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<TableSeparator>(TableSeparator.class, new Visitor<TableSeparator>() {
                    @Override
                    public void visit(TableSeparator node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<TableBody>(TableBody.class, new Visitor<TableBody>() {
                    @Override
                    public void visit(TableBody node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<TableRow>(TableRow.class, new Visitor<TableRow>() {
                    @Override
                    public void visit(TableRow node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<TableCell>(TableCell.class, new Visitor<TableCell>() {
                    @Override
                    public void visit(TableCell node) {
                        visitor.visit(node);
                    }
                })
        };
    }
}
