package com.vladsch.flexmark.ext.tables.internal;

import com.vladsch.flexmark.ext.tables.*;
import com.vladsch.flexmark.html.CustomNodeRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.util.options.DataHolder;

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
                new NodeRenderingHandler<>(TableBlock.class, new CustomNodeRenderer<TableBlock>() {
                    @Override
                    public void render(TableBlock node, NodeRendererContext context, HtmlWriter html) {
                        TableNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<>(TableHead.class, new CustomNodeRenderer<TableHead>() {
                    @Override
                    public void render(TableHead node, NodeRendererContext context, HtmlWriter html) {
                        TableNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<>(TableSeparator.class, new CustomNodeRenderer<TableSeparator>() {
                    @Override
                    public void render(TableSeparator node, NodeRendererContext context, HtmlWriter html) {
                        TableNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<>(TableBody.class, new CustomNodeRenderer<TableBody>() {
                    @Override
                    public void render(TableBody node, NodeRendererContext context, HtmlWriter html) {
                        TableNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<>(TableRow.class, new CustomNodeRenderer<TableRow>() {
                    @Override
                    public void render(TableRow node, NodeRendererContext context, HtmlWriter html) {
                        TableNodeRenderer.this.render(node, context, html);
                    }
                }),
                new NodeRenderingHandler<>(TableCell.class, new CustomNodeRenderer<TableCell>() {
                    @Override
                    public void render(TableCell node, NodeRendererContext context, HtmlWriter html) {
                        TableNodeRenderer.this.render(node, context, html);
                    }
                })
        ));
    }

    private void render(final TableBlock node, final NodeRendererContext context, HtmlWriter html) {
        if (!options.className.isEmpty()) {
            html.attr("class", options.className);
        }

        html.srcPosWithEOL(node.getChars()).withAttr().tagLineIndent("table", new Runnable() {
            @Override
            public void run() {
                context.renderChildren(node);
            }
        });
    }

    private void render(final TableHead node, final NodeRendererContext context, HtmlWriter html) {
        html.withAttr().withCondLine().tagIndent("thead", new Runnable() {
            @Override
            public void run() {
                context.renderChildren(node);
            }
        });
    }

    private void render(TableSeparator tableSeparator, NodeRendererContext context, HtmlWriter html) {

    }

    private void render(final TableBody node, final NodeRendererContext context, HtmlWriter html) {
        html.withAttr().withCondLine().tagIndent("tbody", new Runnable() {
            @Override
            public void run() {
                context.renderChildren(node);
            }
        });
    }

    private void render(final TableRow node, final NodeRendererContext context, HtmlWriter html) {
        html.srcPos(node.getChars()).withAttr().tagLine("tr", new Runnable() {
            @Override
            public void run() {
                context.renderChildren(node);
            }
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

        html.srcPos(node.getText()).withAttr().tag(tag);
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
