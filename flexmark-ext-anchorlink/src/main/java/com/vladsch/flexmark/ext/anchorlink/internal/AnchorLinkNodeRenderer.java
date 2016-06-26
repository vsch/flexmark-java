package com.vladsch.flexmark.ext.anchorlink.internal;

import com.vladsch.flexmark.ext.anchorlink.AnchorLink;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.node.Node;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class AnchorLinkNodeRenderer implements NodeRenderer {
    private final NodeRendererContext context;
    private final HtmlWriter html;
    private final AnchorLinkOptions options;

    public AnchorLinkNodeRenderer(NodeRendererContext context) {
        this.context = context;
        this.html = context.getHtmlWriter();
        this.options = new AnchorLinkOptions(context.getOptions());
    }

    @Override
    public Set<Class<? extends Node>> getNodeTypes() {
        return new HashSet<>(Collections.singletonList(AnchorLink.class));
    }

    @Override
    public void render(Node node) {
        if (node instanceof AnchorLink) {
            renderAnchorLinks((AnchorLink) node);
        }
    }

    private void renderAnchorLinks(AnchorLink node) {
        html.attr("href", "#" + node.getHeaderId());
        if (options.setId) html.attr("id", node.getHeaderId());
        if (options.setName) html.attr("name", node.getHeaderId());
        if (!options.anchorClass.isEmpty()) html.attr("class", options.anchorClass);
        
        if (options.noWrap) {
            html.withAttr().tag("a");
            if (!options.textPrefix.isEmpty()) html.raw(options.textPrefix);
            if (!options.textSuffix.isEmpty()) html.raw(options.textSuffix);
            html.tag("/a");
        } else {
            html.withAttr().tag("a", () -> {
                if (!options.textPrefix.isEmpty()) html.raw(options.textPrefix);
                context.renderChildren(node);
                if (!options.textSuffix.isEmpty()) html.raw(options.textSuffix);
            });
        }
    }
}
