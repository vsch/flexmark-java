package com.vladsch.flexmark.ext.tables.internal;

import com.vladsch.flexmark.ext.tables.*;
import com.vladsch.flexmark.formatter.CustomNodeFormatter;
import com.vladsch.flexmark.formatter.internal.*;
import com.vladsch.flexmark.util.options.DataHolder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TableNodeFormatter implements NodeFormatter {
    private final TableFormatOptions options;

    private int rowNumber;
    private int columnNumber;
    private boolean isSeparator;

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
                })
        ));
    }

    private void render(final TableBlock node, final NodeFormatterContext context, MarkdownWriter markdown) {
        rowNumber = 0;
        context.renderChildren(node);
    }

    private void render(final TableHead node, final NodeFormatterContext context, MarkdownWriter markdown) {
        rowNumber = 0;
        isSeparator = false;
        context.renderChildren(node);
    }

    private void render(TableSeparator node, NodeFormatterContext context, MarkdownWriter markdown) {
        rowNumber = 0;
        isSeparator = true;
        context.renderChildren(node);
    }

    private void render(final TableBody node, final NodeFormatterContext context, MarkdownWriter markdown) {
        rowNumber = 0;
        isSeparator = false;
        context.renderChildren(node);
    }

    private void render(final TableRow node, final NodeFormatterContext context, MarkdownWriter markdown) {
        rowNumber++;
        columnNumber = 0;
        context.renderChildren(node);
        markdown.line();
    }

    private void render(final TableCaption node, final NodeFormatterContext context, MarkdownWriter markdown) {
        if (!options.formatRemoveCaption) {
            markdown.line().append(node.getOpeningMarker());
            context.renderChildren(node);
            markdown.append(node.getClosingMarker()).line();
        }
    }

    private void render(TableCell node, NodeFormatterContext context, MarkdownWriter markdown) {
        columnNumber++;
        if (node.getPrevious() == null) {
            if (options.leadTrailPipes && node.getOpeningMarker().isEmpty()) markdown.append('|');
            else markdown.append(node.getOpeningMarker());
        } else {
            markdown.append(node.getOpeningMarker());
        }
        if (!isSeparator && options.spaceAroundPipe && !node.getText().startsWith(" ")) markdown.append(' ');
        context.renderChildren(node);
        if (!isSeparator && options.spaceAroundPipe && !node.getText().endsWith(" ")) markdown.append(' ');
        if (node.getNext() == null) {
            if (options.leadTrailPipes && node.getClosingMarker().isEmpty()) markdown.append('|');
            else markdown.append(node.getClosingMarker());
        } else {
            markdown.append(node.getClosingMarker());
        }
    }

    public static class Factory implements NodeFormatterFactory {
        @Override
        public NodeFormatter create(final DataHolder options) {
            return new TableNodeFormatter(options);
        }
    }
}
