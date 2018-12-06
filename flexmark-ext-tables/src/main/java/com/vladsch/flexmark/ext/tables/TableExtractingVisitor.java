package com.vladsch.flexmark.ext.tables;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.ast.util.Parsing;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.format.Table;
import com.vladsch.flexmark.util.format.TableFormatOptions;
import com.vladsch.flexmark.util.html.CellAlignment;
import com.vladsch.flexmark.util.options.DataHolder;

public class TableExtractingVisitor {
    private final TableFormatOptions options;
    private final boolean isIntellijDummyIdentifier;
    private final String intellijDummyIdentifier;
    private final String linePrefix;

    private Table myTable;

    private NodeVisitor myVisitor = new NodeVisitor(
            new VisitHandler<>(TableBlock.class, new Visitor<TableBlock>() {
                @Override
                public void visit(TableBlock node) {
                    TableExtractingVisitor.this.visit(node);
                }
            }),
            new VisitHandler<>(TableHead.class, new Visitor<TableHead>() {
                @Override
                public void visit(TableHead node) {
                    TableExtractingVisitor.this.visit(node);
                }
            }),
            new VisitHandler<>(TableSeparator.class, new Visitor<TableSeparator>() {
                @Override
                public void visit(TableSeparator node) {
                    TableExtractingVisitor.this.visit(node);
                }
            }),
            new VisitHandler<>(TableBody.class, new Visitor<TableBody>() {
                @Override
                public void visit(TableBody node) {
                    TableExtractingVisitor.this.visit(node);
                }
            }),
            new VisitHandler<>(TableRow.class, new Visitor<TableRow>() {
                @Override
                public void visit(TableRow node) {
                    TableExtractingVisitor.this.visit(node);
                }
            }),
            new VisitHandler<>(TableCell.class, new Visitor<TableCell>() {
                @Override
                public void visit(TableCell node) {
                    TableExtractingVisitor.this.visit(node);
                }
            }),
            new VisitHandler<>(TableCaption.class, new Visitor<TableCaption>() {
                @Override
                public void visit(TableCaption node) {
                    TableExtractingVisitor.this.visit(node);
                }
            })
    );

    public TableExtractingVisitor(DataHolder options) {
        this.options = new TableFormatOptions(options);
        linePrefix = TablesExtension.FORMAT_TABLE_INDENT_PREFIX.getFrom(options);
        isIntellijDummyIdentifier = Parser.INTELLIJ_DUMMY_IDENTIFIER.getFrom(options);
        intellijDummyIdentifier = isIntellijDummyIdentifier ? Parsing.INTELLIJ_DUMMY_IDENTIFIER : "";
    }

    private void visit(final TableBlock node) {
        myTable = new Table(options);
        myVisitor.visitChildren(node);
    }

    private void visit(final TableHead node) {
        myTable.setSeparator(false);
        myTable.setHeading(true);
        myVisitor.visitChildren(node);
    }

    private void visit(TableSeparator node) {
        myTable.setSeparator(true);
        myVisitor.visitChildren(node);
    }

    private void visit(final TableBody node) {
        myTable.setSeparator(false);
        myTable.setHeading(false);
        myVisitor.visitChildren(node);
    }

    private void visit(final TableRow node) {
        myVisitor.visitChildren(node);
        if (!myTable.isSeparator()) myTable.nextRow();
    }

    private void visit(final TableCaption node) {
        myTable.setCaption(node.getOpeningMarker(), node.getText(), node.getClosingMarker());
    }

    private void visit(final TableCell node) {
        myTable.addCell(new Table.TableCell(node.getOpeningMarker(), options.trimCellWhitespace ? node.getText().trim() : node.getText(), node.getClosingMarker(), 1, node.getSpan(), node.getAlignment() == null ? CellAlignment.NONE : node.getAlignment().cellAlignment()));
    }
}
