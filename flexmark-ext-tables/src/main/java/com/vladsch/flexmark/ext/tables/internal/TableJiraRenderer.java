package com.vladsch.flexmark.ext.tables.internal;

import com.vladsch.flexmark.ast.BlockQuote;
import com.vladsch.flexmark.ext.tables.*;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TableJiraRenderer implements NodeRenderer {

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
        if (node.getGrandParent() instanceof TableHead) {
            html.raw("||");
        } else if (node.getGrandParent() instanceof TableBody) {
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
        @NotNull
        @Override
        public NodeRenderer apply(@NotNull DataHolder options) {
            return new TableJiraRenderer(options);
        }
    }
}
