package com.vladsch.flexmark.ext.anchorlink.internal;

import com.vladsch.flexmark.ext.anchorlink.AnchorLink;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.internal.util.collection.DataHolder;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class AnchorLinkNodeRenderer implements NodeRenderer {
    private final AnchorLinkOptions options;

    public AnchorLinkNodeRenderer(DataHolder options) {
        this.options = new AnchorLinkOptions(options);
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        return new HashSet<>(Collections.singletonList(
                new NodeRenderingHandler<>(AnchorLink.class, this::render)
        ));
    }

    private void render(AnchorLink node, NodeRendererContext context, HtmlWriter html) {
        if (context.isDoNotRenderLinks()) {
            if (!options.noWrap) {
                context.renderChildren(node);
            }
        } else {
            String id = context.getNodeId(node.getParent());
            if (id != null) {
                html.attr("href", "#" + id);
                if (options.setId) html.attr("id", id);
                if (options.setName) html.attr("name", id);
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
            } else {
                if (!options.noWrap) {
                    context.renderChildren(node);
                }
            }
        }
    }
}
