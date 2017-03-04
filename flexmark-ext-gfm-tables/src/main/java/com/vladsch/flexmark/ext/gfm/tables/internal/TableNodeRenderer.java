package com.vladsch.flexmark.ext.gfm.tables.internal;

import com.vladsch.flexmark.ext.gfm.tables.*;
import com.vladsch.flexmark.html.CustomNodeRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.util.options.DataHolder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TableNodeRenderer implements NodeRenderer {
    public TableNodeRenderer(DataHolder options) {
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        return new HashSet<NodeRenderingHandler<? extends com.vladsch.flexmark.ast.Node>>(Arrays.asList(
                new NodeRenderingHandler<TableBlock>(TableBlock.class, new CustomNodeRenderer<TableBlock>() {
                    @Override
                    public void render(TableBlock tableCell, NodeRendererContext context, HtmlWriter html) {
                        TableNodeRenderer.this.render(tableCell, context, html);
                    }
                }),
                new NodeRenderingHandler<TableHead>(TableHead.class, new CustomNodeRenderer<TableHead>() {
                    @Override
                    public void render(TableHead tableCell, NodeRendererContext context, HtmlWriter html) {
                        TableNodeRenderer.this.render(tableCell, context, html);
                    }
                }),
                new NodeRenderingHandler<TableSeparator>(TableSeparator.class, new CustomNodeRenderer<TableSeparator>() {
                    @Override
                    public void render(TableSeparator tableCell, NodeRendererContext context, HtmlWriter html) {
                        TableNodeRenderer.this.render(tableCell, context, html);
                    }
                }),
                new NodeRenderingHandler<TableBody>(TableBody.class, new CustomNodeRenderer<TableBody>() {
                    @Override
                    public void render(TableBody tableCell, NodeRendererContext context, HtmlWriter html) {
                        TableNodeRenderer.this.render(tableCell, context, html);
                    }
                }),
                new NodeRenderingHandler<TableRow>(TableRow.class, new CustomNodeRenderer<TableRow>() {
                    @Override
                    public void render(TableRow tableCell, NodeRendererContext context, HtmlWriter html) {
                        TableNodeRenderer.this.render(tableCell, context, html);
                    }
                }),
                new NodeRenderingHandler<TableCell>(TableCell.class, new CustomNodeRenderer<TableCell>() {
                    @Override
                    public void render(TableCell tableCell, NodeRendererContext context, HtmlWriter html) {
                        TableNodeRenderer.this.render(tableCell, context, html);
                    }
                })
        ));
    }

    private void render(final TableBlock tableBlock, final NodeRendererContext context, HtmlWriter html) {
        html.withAttr().tagIndent("table", new Runnable() {
            @Override
            public void run() {
                context.renderChildren(tableBlock);
            }
        });
    }

    private void render(final TableHead tableHead, final NodeRendererContext context, HtmlWriter html) {
        html.withAttr().withCondLine().tagIndent("thead", new Runnable() {
            @Override
            public void run() {
                context.renderChildren(tableHead);
            }
        });
    }

    private void render(TableSeparator tableSeparator, NodeRendererContext context, HtmlWriter html) {

    }

    private void render(final TableBody tableBody, final NodeRendererContext context, HtmlWriter html) {
        html.withAttr().withCondLine().tagIndent("tbody", new Runnable() {
            @Override
            public void run() {
                context.renderChildren(tableBody);
            }
        });
    }

    private void render(final TableRow tableRow, final NodeRendererContext context, HtmlWriter html) {
        html.withAttr().tagLine("tr", new Runnable() {
            @Override
            public void run() {
                context.renderChildren(tableRow);
            }
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

    public static class Factory implements NodeRendererFactory {
        @Override
        public NodeRenderer create(final DataHolder options) {
            return new TableNodeRenderer(options);
        }
    }
}
