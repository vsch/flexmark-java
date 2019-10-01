package com.vladsch.flexmark.ext.aside.internal;

import com.vladsch.flexmark.ext.aside.AsideBlock;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.util.data.DataHolder;

import java.util.HashSet;
import java.util.Set;

public class AsideNodeRenderer implements NodeRenderer {
    private final AsideOptions options;

    public AsideNodeRenderer(DataHolder options) {
        this.options = new AsideOptions(options);
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        HashSet<NodeRenderingHandler<?>> set = new HashSet<>();
        set.add(new NodeRenderingHandler<>(AsideBlock.class, this::render));
        return set;
    }

    private void render(AsideBlock node, NodeRendererContext context, HtmlWriter html) {
        html.withAttr().withCondIndent().tagLine("aside", () -> context.renderChildren(node));
    }

    public static class Factory implements NodeRendererFactory {
        @Override
        public NodeRenderer apply(DataHolder options) {
            return new AsideNodeRenderer(options);
        }
    }
}
