package com.atlassian.plugins.confluence.markdown.ext.DevOpsResizableImage.internal;

import com.atlassian.plugins.confluence.markdown.ext.DevOpsResizableImage.ResizableImage;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.LinkType;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.html.renderer.ResolvedLink;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class ResizableImageNodeRenderer implements NodeRenderer
{
    public ResizableImageNodeRenderer(DataHolder options) {
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        Set<NodeRenderingHandler<?>> set = new HashSet<>();
        // @formatter:off
        set.add(new NodeRenderingHandler<>(ResizableImage.class, ResizableImageNodeRenderer.this::render));
        // @formatter:on
        return set;
    }

    public void render(ResizableImage node, NodeRendererContext context, HtmlWriter html) {
        if (context.isDoNotRenderLinks()) {
            context.renderChildren(node);
        } else {
            ResolvedLink link = context.resolveLink(LinkType.IMAGE, node.getSource(), true);
            html.srcPos(node.getChars())
                .attr("src", link.getUrl());
            if (node.getText().isNotEmpty()){
                html.attr("alt", node.getText());
            }
            if (node.getWidth().isNotEmpty()){
                html.attr("width", node.getWidth() + "px");
            }
            if (node.getHeight().isNotEmpty()){
                html.attr("height", node.getHeight() + "px");
            }               
            html.withAttr().tag("img");
            html.tag("/img");
        }
    }

    public static class Factory implements NodeRendererFactory {
        @NotNull
        @Override
        public NodeRenderer apply(@NotNull DataHolder options) {
            return new ResizableImageNodeRenderer(options);
        }
    }
}
