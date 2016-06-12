package com.vladsch.flexmark.ext.tables.internal;

import com.vladsch.flexmark.ext.tables.*;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.internal.util.Options;
import com.vladsch.flexmark.node.Node;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TableNodeRenderer implements NodeRenderer {

    private final HtmlWriter html;
    private final NodeRendererContext context;
    private final TableParserOptions options;

    public TableNodeRenderer(NodeRendererContext context, Options options) {
        this.html = context.getHtmlWriter();
        this.context = context;
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
    public void render(Node node) {
        if (node instanceof TableBlock) {
            renderBlock((TableBlock) node);
        } else if (node instanceof TableHead) {
            renderHead((TableHead) node);
        } else if (node instanceof TableSeparator) {
            renderSeparator((TableSeparator) node);
        } else if (node instanceof TableBody) {
            renderBody((TableBody) node);
        } else if (node instanceof TableRow) {
            renderRow((TableRow) node);
        } else if (node instanceof TableCell) {
            renderCell((TableCell) node);
        }
    }

    private void renderBlock(TableBlock node) {
        html.withAttr().tagIndent("table", () -> {
            context.renderChildren(node);
        });
    }

    private void renderHead(TableHead node) {
        html.withAttr().tagIndent("thead", () -> {
            context.renderChildren(node);
        });
    }

    private void renderSeparator(TableSeparator tableSeparator) {

    }

    private void renderBody(TableBody node) {
        html.withAttr().tagIndent("tbody", () -> {
            context.renderChildren(node);
        });
    }

    private void renderRow(TableRow node) {
        html.withAttr().tagLine("tr", () -> {
            context.renderChildren(node);
        });
    }

    private void renderCell(TableCell node) {
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
