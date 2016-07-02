package com.vladsch.flexmark.ext.gfm.tables.internal;

import com.vladsch.flexmark.ext.gfm.tables.*;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.internal.util.collection.DataHolder;
import com.vladsch.flexmark.node.Node;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TableNodeRenderer implements NodeRenderer {

    public TableNodeRenderer(DataHolder options) {
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

    private void renderBlock(NodeRendererContext context, HtmlWriter html, TableBlock tableBlock) {
        html.withAttr().tagIndent("table", () -> {
            context.renderChildren(tableBlock);
        });
    }

    private void renderHead(NodeRendererContext context, HtmlWriter html, TableHead tableHead) {
        html.withAttr().withCondLine().tagIndent("thead", () -> {
            context.renderChildren(tableHead);
        });
    }

    private void renderSeparator(NodeRendererContext context, HtmlWriter html, TableSeparator tableSeparator) {

    }

    private void renderBody(NodeRendererContext context, HtmlWriter html, TableBody tableBody) {
        html.withAttr().withCondLine().tagIndent("tbody", () -> {
            context.renderChildren(tableBody);
        });
    }

    private void renderRow(NodeRendererContext context, HtmlWriter html, TableRow tableRow) {
        html.withAttr().tagLine("tr", () -> {
            context.renderChildren(tableRow);
        });
    }

    private void renderCell(NodeRendererContext context, HtmlWriter html, TableCell tableCell) {
        String tag = tableCell.isHeader() ? "th" : "td";
        if (tableCell.getAlignment() != null) {
            html.attr("align", getAlignValue(tableCell.getAlignment()));
        }
        html.withAttr().tag(tag);
        context.renderChildren(tableCell);
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
