package com.vladsch.flexmark.ext.typographic.internal;

import com.vladsch.flexmark.ext.typographic.TypographicQuotes;
import com.vladsch.flexmark.ext.typographic.TypographicSmarts;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class TypographicNodeRenderer implements NodeRenderer {
    public TypographicNodeRenderer(DataHolder options) {

    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        HashSet<NodeRenderingHandler<?>> set = new HashSet<>();
        set.add(new NodeRenderingHandler<>(TypographicSmarts.class, this::render));
        set.add(new NodeRenderingHandler<>(TypographicQuotes.class, this::render));
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
        @NotNull
        @Override
        public NodeRenderer apply(@NotNull DataHolder options) {
            return new TypographicNodeRenderer(options);
        }
    }
}
