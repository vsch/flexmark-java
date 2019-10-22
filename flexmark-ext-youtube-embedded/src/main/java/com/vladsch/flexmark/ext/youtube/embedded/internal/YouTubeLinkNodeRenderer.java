package com.vladsch.flexmark.ext.youtube.embedded.internal;

import com.vladsch.flexmark.ext.youtube.embedded.YouTubeLink;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.*;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class YouTubeLinkNodeRenderer implements NodeRenderer {

    public YouTubeLinkNodeRenderer(DataHolder options) {
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        HashSet<NodeRenderingHandler<?>> set = new HashSet<>();
        set.add(new NodeRenderingHandler<>(YouTubeLink.class, this::render));
        return set;
    }

    private void render(YouTubeLink node, NodeRendererContext context, HtmlWriter html) {
        if (context.isDoNotRenderLinks()) {
            context.renderChildren(node);
        } else {
            // standard Link Rendering
            ResolvedLink resolvedLink = context.resolveLink(LinkType.LINK, node.getUrl().unescape(), null);

            URL url = null;
            try {
                url = new URL(resolvedLink.getUrl());
            } catch (MalformedURLException e) {
            }

            if (url != null && "youtu.be".equalsIgnoreCase(url.getHost())) {
                html.attr("src", "https://www.youtube-nocookie.com/embed" + url.getFile().replace("?t=", "?start="));
                html.attr("width", "560");
                html.attr("height", "315");
                html.attr("class", "youtube-embedded");
                html.attr("allowfullscreen", "true");
                html.attr("frameborder", "0");
                html.attr("allow", "accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture");
                html.srcPos(node.getChars()).withAttr(resolvedLink).tag("iframe");
                html.tag("/iframe");
            } else if (resolvedLink.getUrl().contains("www.youtube.com/watch")) {
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
        @NotNull
        @Override
        public NodeRenderer apply(@NotNull DataHolder options) {
            return new YouTubeLinkNodeRenderer(options);
        }
    }
}
