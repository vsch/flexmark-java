package com.vladsch.flexmark.ext.youtube.embedded.internal;

import com.vladsch.flexmark.ast.Link;
import com.vladsch.flexmark.ext.youtube.embedded.YouTubeLink;
import com.vladsch.flexmark.html.CustomNodeRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.*;
import com.vladsch.flexmark.util.options.DataHolder;

import java.util.HashSet;
import java.util.Set;

public class YouTubeLinkNodeRenderer implements NodeRenderer {

    public YouTubeLinkNodeRenderer(DataHolder options) {
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        final YouTubeLinkNodeRenderer self = this;

        HashSet<NodeRenderingHandler<?>> set = new HashSet<NodeRenderingHandler<?>>();
        set.add(new NodeRenderingHandler<YouTubeLink>(YouTubeLink.class, new CustomNodeRenderer<YouTubeLink>() {
            @Override
            public void render(YouTubeLink node, NodeRendererContext context, HtmlWriter html) {
                self.render(node, context, html);
            }
        }));
        return set;
    }

    private void render(final YouTubeLink node, final NodeRendererContext context, final HtmlWriter html) {
        if (context.isDoNotRenderLinks()) {
            context.renderChildren(node);
        } else {
            // standard Link Rendering
            ResolvedLink resolvedLink = context.resolveLink(LinkType.LINK, node.getUrl().unescape(), null);

            if (resolvedLink.getUrl().contains("www.youtube.com/watch")) {
                html.attr("src", resolvedLink.getUrl().replace("watch?v=".toLowerCase(), "embed/"));
                html.attr("width", "420");
                html.attr("height", "315");
                html.attr("class", "youtube-embedded");
                html.attr("allowfullscreen", "true");
                html.attr("frameborder", "0");
                html.srcPos(node.getChars()).withAttr(resolvedLink).tag("iframe");
                //context.renderChildren(node);
                html.tag("/iframe");
            } else {
                html.attr("href", resolvedLink.getUrl());
                if (node.getTitle().isNotNull()) {
                    html.attr("title", node.getTitle().unescape());
                }
                html.srcPos(node.getChars()).withAttr(resolvedLink).tag("a");
                context.renderChildren(node);
                html.tag("/a");
            }
        }
    }

    public static class Factory implements NodeRendererFactory {
        @Override
        public NodeRenderer create(final DataHolder options) {
            return new YouTubeLinkNodeRenderer(options);
        }
    }
}
