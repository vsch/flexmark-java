package com.vladsch.flexmark.ext.gfm.tables.internal;

import com.vladsch.flexmark.ext.gfm.tables.*;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.node.Node;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TableNodeRenderer implements NodeRenderer {

    private final HtmlWriter html;
    private final NodeRendererContext context;

    public TableNodeRenderer(NodeRendererContext context) {
        this.html = context.getHtmlWriter();
        this.context = context;
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

    private void renderBlock(TableBlock tableBlock) {
        html.withAttr().tagIndent("table", () -> {
            context.renderChildren(tableBlock);
        });
    }

    private void renderHead(TableHead tableHead) {
        html.withAttr().withCondLine().tagIndent("thead", () -> {
            context.renderChildren(tableHead);
        });
    }

    private void renderSeparator(TableSeparator tableSeparator) {

    }

    private void renderBody(TableBody tableBody) {
        html.withAttr().withCondLine().tagIndent("tbody", () -> {
            context.renderChildren(tableBody);
        });
    }

    private void renderRow(TableRow tableRow) {
        html.withAttr().tagLine("tr", () -> {
            context.renderChildren(tableRow);
        });
    }

    private void renderCell(TableCell tableCell) {
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
