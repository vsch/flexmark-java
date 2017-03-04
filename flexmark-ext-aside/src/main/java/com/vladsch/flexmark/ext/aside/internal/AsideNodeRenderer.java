package com.vladsch.flexmark.ext.aside.internal;

import com.vladsch.flexmark.ext.aside.AsideBlock;
import com.vladsch.flexmark.html.CustomNodeRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.util.options.DataHolder;

import java.util.HashSet;
import java.util.Set;

public class AsideNodeRenderer implements NodeRenderer {
    private final AsideOptions options;

    public AsideNodeRenderer(DataHolder options) {
        this.options = new AsideOptions(options);
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        HashSet<NodeRenderingHandler<?>> set = new HashSet<NodeRenderingHandler<?>>();
        set.add(new NodeRenderingHandler<AsideBlock>(AsideBlock.class, new CustomNodeRenderer<AsideBlock>() {
            @Override
            public void render(AsideBlock node, NodeRendererContext context, HtmlWriter html) {
                AsideNodeRenderer.this.render(node, context, html);
            }
        }));
        return set;
    }

    private void render(final AsideBlock node, final NodeRendererContext context, HtmlWriter html) {
        html.withAttr().tagIndent("aside", new Runnable() {
            @Override
            public void run() {
                context.renderChildren(node);
            }
        });
    }

    public static class Factory implements NodeRendererFactory {
        @Override
        public NodeRenderer create(final DataHolder options) {
            return new AsideNodeRenderer(options);
        }
    }
}
