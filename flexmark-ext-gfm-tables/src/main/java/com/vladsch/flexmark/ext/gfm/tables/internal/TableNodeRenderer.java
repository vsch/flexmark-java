package com.vladsch.flexmark.ext.gfm.tables.internal;

import com.vladsch.flexmark.ext.gfm.tables.*;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.internal.util.collection.DataHolder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TableNodeRenderer implements NodeRenderer {

    public TableNodeRenderer(DataHolder options) {
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderers() {
        return new HashSet<>(Arrays.asList(
                new NodeRenderingHandler<>(TableBlock.class, this::render),
                new NodeRenderingHandler<>(TableHead.class, this::render),
                new NodeRenderingHandler<>(TableSeparator.class, this::render),
                new NodeRenderingHandler<>(TableBody.class, this::render),
                new NodeRenderingHandler<>(TableRow.class, this::render),
                new NodeRenderingHandler<>(TableCell.class, this::render)
        ));
    }


    private void render(TableBlock tableBlock, NodeRendererContext context, HtmlWriter html) {
        html.withAttr().tagIndent("table", () -> {
            context.renderChildren(tableBlock);
        });
    }

    private void render(TableHead tableHead, NodeRendererContext context, HtmlWriter html) {
        html.withAttr().withCondLine().tagIndent("thead", () -> {
            context.renderChildren(tableHead);
        });
    }

    private void render(TableSeparator tableSeparator, NodeRendererContext context, HtmlWriter html) {

    }

    private void render(TableBody tableBody, NodeRendererContext context, HtmlWriter html) {
        html.withAttr().withCondLine().tagIndent("tbody", () -> {
            context.renderChildren(tableBody);
        });
    }

    private void render(TableRow tableRow, NodeRendererContext context, HtmlWriter html) {
        html.withAttr().tagLine("tr", () -> {
            context.renderChildren(tableRow);
        });
    }

    private void render(TableCell tableCell, NodeRendererContext context, HtmlWriter html) {
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
