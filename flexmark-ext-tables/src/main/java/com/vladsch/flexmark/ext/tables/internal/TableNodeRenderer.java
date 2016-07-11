package com.vladsch.flexmark.ext.tables.internal;

import com.vladsch.flexmark.ext.tables.*;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.internal.util.collection.DataHolder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TableNodeRenderer implements NodeRenderer {

    private final TableParserOptions options;

    public TableNodeRenderer(DataHolder options) {
        this.options = new TableParserOptions(options);
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        return new HashSet<>(Arrays.asList(
                new NodeRenderingHandler<>(TableBlock.class, this::render),
                new NodeRenderingHandler<>(TableHead.class, this::render),
                new NodeRenderingHandler<>(TableSeparator.class, this::render),
                new NodeRenderingHandler<>(TableBody.class, this::render),
                new NodeRenderingHandler<>(TableRow.class, this::render),
                new NodeRenderingHandler<>(TableCell.class, this::render)
        ));
    }

    private void render(TableBlock node, NodeRendererContext context, HtmlWriter html) {
        html.withAttr().tagIndent("table", () -> {
            context.renderChildren(node);
        });
    }

    private void render(TableHead node, NodeRendererContext context, HtmlWriter html) {
        html.withAttr().withCondLine().tagIndent("thead", () -> {
            context.renderChildren(node);
        });
    }

    private void render(TableSeparator tableSeparator, NodeRendererContext context, HtmlWriter html) {

    }

    private void render(TableBody node, NodeRendererContext context, HtmlWriter html) {
        html.withAttr().withCondLine().tagIndent("tbody", () -> {
            context.renderChildren(node);
        });
    }

    private void render(TableRow node, NodeRendererContext context, HtmlWriter html) {
        html.withAttr().tagLine("tr", () -> {
            context.renderChildren(node);
        });
    }

    private void render(TableCell node, NodeRendererContext context, HtmlWriter html) {
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
