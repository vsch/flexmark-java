package com.vladsch.flexmark.ext.tables.internal;

import com.vladsch.flexmark.ext.tables.*;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.internal.util.collection.DataHolder;
import com.vladsch.flexmark.node.Node;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TableNodeRenderer implements NodeRenderer {

    private final TableParserOptions options;

    public TableNodeRenderer(DataHolder options) {
        this.options = new TableParserOptions(options);
    }

    @Override
    public Set<Class<? extends Node>> getNodeTypes() {
        return new HashSet<Class<? extends Node>>(Arrays.asList(
                TableBlock.class,
                TableHead.class,
                TableSeparator.class,
                TableBody.class,
                TableRow.class,
                TableCell.class
        ));
    }

    @Override
    public void render(NodeRendererContext context, HtmlWriter html, Node node) {
        if (node instanceof TableBlock) {
            renderBlock(context, html, (TableBlock) node);
        } else if (node instanceof TableHead) {
            renderHead(context, html, (TableHead) node);
        } else if (node instanceof TableSeparator) {
            renderSeparator(context, html, (TableSeparator) node);
        } else if (node instanceof TableBody) {
            renderBody(context, html, (TableBody) node);
        } else if (node instanceof TableRow) {
            renderRow(context, html, (TableRow) node);
        } else if (node instanceof TableCell) {
            renderCell(context, html, (TableCell) node);
        }
    }

    private void renderBlock(NodeRendererContext context, HtmlWriter html, TableBlock node) {
        html.withAttr().tagIndent("table", () -> {
            context.renderChildren(node);
        });
    }

    private void renderHead(NodeRendererContext context, HtmlWriter html, TableHead node) {
        html.withAttr().withCondLine().tagIndent("thead", () -> {
            context.renderChildren(node);
        });
    }

    private void renderSeparator(NodeRendererContext context, HtmlWriter html, TableSeparator tableSeparator) {

    }

    private void renderBody(NodeRendererContext context, HtmlWriter html, TableBody node) {
        html.withAttr().withCondLine().tagIndent("tbody", () -> {
            context.renderChildren(node);
        });
    }

    private void renderRow(NodeRendererContext context, HtmlWriter html, TableRow node) {
        html.withAttr().tagLine("tr", () -> {
            context.renderChildren(node);
        });
    }

    private void renderCell(NodeRendererContext context, HtmlWriter html, TableCell node) {
        String tag = node.isHeader() ? "th" : "td";
        if (node.getAlignment() != null) {
            html.attr("align", getAlignValue(node.getAlignment()));
        }

        if (options.columnSpans && node.getSpan() > 1) {
            html.attr("colspan", String.valueOf(node.getSpan()));
        }

        html.withAttr().tag(tag);
        context.renderChildren(node);
        html.tag("/" + tag);
    }

    private static String getAlignValue(TableCell.Alignment alignment) {
        switch (alignment) {
            case LEFT:
                return "left";
            case CENTER:
                return "center";
            case RIGHT:
                return "right";
        }
        throw new IllegalStateException("Unknown alignment: " + alignment);
    }
}
