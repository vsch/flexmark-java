package com.vladsch.flexmark.ext.aside.internal;

import com.vladsch.flexmark.ext.aside.AsideBlock;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.util.options.DataHolder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AsideNodeRenderer implements NodeRenderer {
    private final AsideOptions options;

    public AsideNodeRenderer(DataHolder options) {
        this.options = new AsideOptions(options);
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        return new HashSet<>(Arrays.asList(
                // new NodeRenderingHandler<>(ExtAside.class, this::render),
                new NodeRenderingHandler<>(AsideBlock.class, this::render)
        ));
    }

    private void render(AsideBlock node, NodeRendererContext context, HtmlWriter html) {
        html.withAttr().tagIndent("aside", () -> {
            context.renderChildren(node);
        });
    }
}
