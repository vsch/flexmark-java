package com.vladsch.flexmark.ext.tables.internal;

import com.vladsch.flexmark.ast.BlockQuote;
import com.vladsch.flexmark.ext.tables.*;
import com.vladsch.flexmark.html.CustomNodeRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;

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
        return new HashSet<NodeRenderingHandler<? extends Node>>(Arrays.asList(
                new NodeRenderingHandler<TableBlock>(TableBlock.class, new CustomNodeRenderer<TableBlock>() {
                    @Override
                    public void render(TableBlock node, NodeRendererContext context, HtmlWriter html) {
                        TableJiraRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<TableHead>(TableHead.class, new CustomNodeRenderer<TableHead>() {
                    @Override
                    public void render(TableHead node, NodeRendererContext context, HtmlWriter html) {
                        TableJiraRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<TableSeparator>(TableSeparator.class, new CustomNodeRenderer<TableSeparator>() {
                    @Override
                    public void render(TableSeparator node, NodeRendererContext context, HtmlWriter html) {
                        TableJiraRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<TableBody>(TableBody.class, new CustomNodeRenderer<TableBody>() {
                    @Override
                    public void render(TableBody node, NodeRendererContext context, HtmlWriter html) {
                        TableJiraRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<TableRow>(TableRow.class, new CustomNodeRenderer<TableRow>() {
                    @Override
                    public void render(TableRow node, NodeRendererContext context, HtmlWriter html) {
                        TableJiraRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<TableCell>(TableCell.class, new CustomNodeRenderer<TableCell>() {
                    @Override
                    public void render(TableCell node, NodeRendererContext context, HtmlWriter html) {
                        TableJiraRenderer.this.render(node, context, html);
                    }
                })
        ));
    }

    private HtmlWriter tailBlankLine(Node node, HtmlWriter html) {
        return tailBlankLine(node, 1, html);
    }

    public boolean isLastBlockQuoteChild(Node node) {
        Node parent = node.getParent();
        return parent instanceof BlockQuote && parent.getLastChild() == node;
    }

    public HtmlWriter tailBlankLine(Node node, int count, HtmlWriter html) {
        if (isLastBlockQuoteChild(node)) {
            // Needed to not add block quote prefix to trailing blank lines
            //if (getPushedPrefixCount() > 0) {
            //    flush(-1); // clear pending lines so pop prefix is not delayed, if PREFIX_AFTER_PENDING_EOL is enabled
            //    popPrefix();
            //    pushPrefix();
            //}
            CharSequence prefix = html.getPrefix();
            html.popPrefix();
            html.blankLine(count);
            html.pushPrefix();
            html.setPrefix(prefix, false);
        } else {
            html.blankLine(count);
        }
        return html;
    }

    private void render(TableBlock node, NodeRendererContext context, HtmlWriter html) {
        context.renderChildren(node);
        tailBlankLine(node, html);
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

    public static class Factory implements NodeRendererFactory {
        @Override
        public NodeRenderer apply(final DataHolder options) {
            return new TableJiraRenderer(options);
        }
    }
}
