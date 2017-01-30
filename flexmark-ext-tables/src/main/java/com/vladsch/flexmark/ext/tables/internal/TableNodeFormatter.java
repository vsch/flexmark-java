package com.vladsch.flexmark.ext.tables.internal;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ast.Paragraph;
import com.vladsch.flexmark.ast.Text;
import com.vladsch.flexmark.ext.tables.*;
import com.vladsch.flexmark.formatter.CustomNodeFormatter;
import com.vladsch.flexmark.formatter.internal.*;
import com.vladsch.flexmark.util.format.Table;
import com.vladsch.flexmark.util.format.TableFormatOptions;
import com.vladsch.flexmark.util.html.CellAlignment;
import com.vladsch.flexmark.util.html.FormattingAppendable;
import com.vladsch.flexmark.util.options.DataHolder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TableNodeFormatter implements NodeFormatter {
    private final TableFormatOptions options;

    private Table myTable;

    public TableNodeFormatter(DataHolder options) {
        this.options = new TableFormatOptions(options);
    }

    @Override
    public Set<Class<?>> getNodeClasses() {
        return null;
    }

    @Override
    public Set<NodeFormattingHandler<?>> getNodeFormattingHandlers() {
        return new HashSet<>(Arrays.asList(
                new NodeFormattingHandler<>(TableBlock.class, new CustomNodeFormatter<TableBlock>() {
                    @Override
                    public void render(TableBlock node, NodeFormatterContext context, MarkdownWriter markdown) {
                        TableNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<>(TableHead.class, new CustomNodeFormatter<TableHead>() {
                    @Override
                    public void render(TableHead node, NodeFormatterContext context, MarkdownWriter markdown) {
                        TableNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<>(TableSeparator.class, new CustomNodeFormatter<TableSeparator>() {
                    @Override
                    public void render(TableSeparator node, NodeFormatterContext context, MarkdownWriter markdown) {
                        TableNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<>(TableBody.class, new CustomNodeFormatter<TableBody>() {
                    @Override
                    public void render(TableBody node, NodeFormatterContext context, MarkdownWriter markdown) {
                        TableNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<>(TableRow.class, new CustomNodeFormatter<TableRow>() {
                    @Override
                    public void render(TableRow node, NodeFormatterContext context, MarkdownWriter markdown) {
                        TableNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<>(TableCell.class, new CustomNodeFormatter<TableCell>() {
                    @Override
                    public void render(TableCell node, NodeFormatterContext context, MarkdownWriter markdown) {
                        TableNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<>(TableCaption.class, new CustomNodeFormatter<TableCaption>() {
                    @Override
                    public void render(TableCaption node, NodeFormatterContext context, MarkdownWriter markdown) {
                        TableNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<>(Text.class, new CustomNodeFormatter<Text>() {
                    @Override
                    public void render(Text node, NodeFormatterContext context, MarkdownWriter markdown) {
                        TableNodeFormatter.this.render(node, context, markdown);
                    }
                })
        ));
    }

    private void render(final TableBlock node, final NodeFormatterContext context, MarkdownWriter markdown) {
        myTable = new Table(options);
        context.renderChildren(node);

        // output table
        myTable.finalizeTable();
        if (myTable.getMaxColumns() > 0) {
            markdown.blankLine();
            myTable.appendTable(markdown);
            markdown.blankLine();
        }
    }

    private void render(final TableHead node, final NodeFormatterContext context, MarkdownWriter markdown) {
        myTable.setSeparator(false);
        myTable.setHeading(true);
        context.renderChildren(node);
    }

    private void render(TableSeparator node, NodeFormatterContext context, MarkdownWriter markdown) {
        myTable.setSeparator(true);
        context.renderChildren(node);
    }

    private void render(final TableBody node, final NodeFormatterContext context, MarkdownWriter markdown) {
        myTable.setSeparator(false);
        myTable.setHeading(false);
        context.renderChildren(node);
    }

    private void render(final TableRow node, final NodeFormatterContext context, MarkdownWriter markdown) {
        context.renderChildren(node);
        if (!myTable.isSeparator()) myTable.nextRow();
    }

    private void render(final TableCaption node, final NodeFormatterContext context, MarkdownWriter markdown) {
        myTable.setCaption(node.getOpeningMarker(), node.getText(), node.getClosingMarker());
        //if (!options.removeCaption) {
        //    markdown.line().append(node.getOpeningMarker());
        //    context.renderChildren(node);
        //    markdown.append(node.getClosingMarker()).line();
        //}
    }

    private void render(TableCell node, NodeFormatterContext context, MarkdownWriter markdown) {
        myTable.addCell(new Table.TableCell(node.getOpeningMarker(), node.getText(), node.getClosingMarker(), 1, node.getSpan(), node.getAlignment() == null ? CellAlignment.NONE : node.getAlignment().cellAlignment()));
        //if (node.getPrevious() == null) {
        //    if (options.leadTrailPipes && node.getOpeningMarker().isEmpty()) markdown.append('|');
        //    else markdown.append(node.getOpeningMarker());
        //} else {
        //    markdown.append(node.getOpeningMarker());
        //}
        //if (!isSeparator && options.spaceAroundPipes && !node.getText().startsWith(" ")) markdown.append(' ');
        //context.renderChildren(node);
        //if (!isSeparator && options.spaceAroundPipes && !node.getText().endsWith(" ")) markdown.append(' ');
        //if (node.getNext() == null) {
        //    if (options.leadTrailPipes && node.getClosingMarker().isEmpty()) markdown.append('|');
        //    else markdown.append(node.getClosingMarker());
        //} else {
        //    markdown.append(node.getClosingMarker());
        //}
    }

    private void render(Text node, NodeFormatterContext context, MarkdownWriter markdown) {
        if (TableParagraphPreProcessor.TABLE_HEADER_SEPARATOR.matcher(node.getChars()).matches()) {
            Node parent = node.getAncestorOfType(Paragraph.class);
            if (parent instanceof Paragraph && ((Paragraph) parent).hasTableSeparator()) {
                markdown.pushPrefix().addPrefix(" ").append(node.getChars()).popPrefix();
            } else {
                markdown.append(node.getChars());
            }
        } else {
            markdown.append(node.getChars());
        }
    }

    public static class Factory implements NodeFormatterFactory {
        @Override
        public NodeFormatter create(final DataHolder options) {
            return new TableNodeFormatter(options);
        }
    }
}
