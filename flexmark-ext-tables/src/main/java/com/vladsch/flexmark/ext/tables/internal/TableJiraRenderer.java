package com.vladsch.flexmark.ext.tables.internal;

import com.vladsch.flexmark.ext.tables.*;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.util.options.DataHolder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TableJiraRenderer implements NodeRenderer {

    //private final TableParserOptions options;

    public TableJiraRenderer(DataHolder options) {
        //this.options = new TableParserOptions(options);
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
        context.renderChildren(node);
    }

    private void render(TableHead node, NodeRendererContext context, HtmlWriter html) {
        context.renderChildren(node);
    }

    private void render(TableSeparator tableSeparator, NodeRendererContext context, HtmlWriter html) {

    }

    private void render(TableBody node, NodeRendererContext context, HtmlWriter html) {
        context.renderChildren(node);
    }

    private void render(TableRow node, NodeRendererContext context, HtmlWriter html) {
        if (node.getParent() instanceof TableHead) {
            html.line().raw("||");
        } else if (node.getParent() instanceof TableBody) {
            html.line().raw("|");
        }
        context.renderChildren(node);
        html.line();
    }

    private void render(TableCell node, NodeRendererContext context, HtmlWriter html) {
        context.renderChildren(node);
        if (node.getParent().getParent() instanceof TableHead) {
            html.raw("||");
        } else if (node.getParent().getParent() instanceof TableBody) {
            html.raw("|");
        }
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
