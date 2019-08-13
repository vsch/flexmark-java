package com.vladsch.flexmark.ext.typographic.internal;

import com.vladsch.flexmark.ext.typographic.TypographicQuotes;
import com.vladsch.flexmark.ext.typographic.TypographicSmarts;
import com.vladsch.flexmark.html.CustomNodeRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.util.data.DataHolder;

import java.util.HashSet;
import java.util.Set;

public class TypographicNodeRenderer implements NodeRenderer {
    private final TypographicOptions options;

    public TypographicNodeRenderer(DataHolder options) {
        this.options = new TypographicOptions(options);
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        HashSet<NodeRenderingHandler<?>> set = new HashSet<NodeRenderingHandler<?>>();
        set.add(new NodeRenderingHandler<TypographicSmarts>(TypographicSmarts.class, new CustomNodeRenderer<TypographicSmarts>() {
            @Override
            public void render(TypographicSmarts node, NodeRendererContext context, HtmlWriter html) {
                TypographicNodeRenderer.this.render(node, context, html);
            }
        }));
        set.add(new NodeRenderingHandler<TypographicQuotes>(TypographicQuotes.class, new CustomNodeRenderer<TypographicQuotes>() {
            @Override
            public void render(TypographicQuotes node, NodeRendererContext context, HtmlWriter html) {
                TypographicNodeRenderer.this.render(node, context, html);
            }
        }));
        return set;
    }

    private void render(TypographicQuotes node, NodeRendererContext context, HtmlWriter html) {
        if (node.getTypographicOpening() != null && !node.getTypographicOpening().isEmpty()) html.raw(node.getTypographicOpening());
        context.renderChildren(node);
        if (node.getTypographicClosing() != null && !node.getTypographicClosing().isEmpty()) html.raw(node.getTypographicClosing());
    }

    private void render(TypographicSmarts node, NodeRendererContext context, HtmlWriter html) {
        html.raw(node.getTypographicText());
    }

    public static class Factory implements NodeRendererFactory {
        @Override
        public NodeRenderer apply(DataHolder options) {
            return new TypographicNodeRenderer(options);
        }
    }
}
