package com.vladsch.flexmark.ext.wikilink.internal;

import com.vladsch.flexmark.ext.wikilink.WikiImage;
import com.vladsch.flexmark.ext.wikilink.WikiLink;
import com.vladsch.flexmark.ext.wikilink.WikiLinkExtension;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.*;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class WikiLinkNodeRenderer implements NodeRenderer {
    final private WikiLinkOptions options;

    public WikiLinkNodeRenderer(DataHolder options) {
        this.options = new WikiLinkOptions(options);
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        HashSet<NodeRenderingHandler<?>> set = new HashSet<>();
        set.add(new NodeRenderingHandler<>(WikiLink.class, this::render));
        set.add(new NodeRenderingHandler<>(WikiImage.class, this::render));
        return set;
    }

    private void render(WikiLink node, NodeRendererContext context, HtmlWriter html) {
        if (!context.isDoNotRenderLinks()) {
            if (options.disableRendering) {
                html.text(node.getChars().unescape());
            } else {
                ResolvedLink resolvedLink = context.resolveLink(WikiLinkExtension.WIKI_LINK, node.getLink(), null);
                html.attr("href", resolvedLink.getUrl());
                html.srcPos(node.getChars()).withAttr(resolvedLink).tag("a");
                context.renderChildren(node);//html.text(node.getText().isNotNull() ? node.getText().toString() : node.getPageRef().toString());
                html.tag("/a");
            }
        }
    }

    private void render(WikiImage node, NodeRendererContext context, HtmlWriter html) {
        if (!context.isDoNotRenderLinks()) {
            if (options.disableRendering) {
                html.text(node.getChars().unescape());
            } else {
                String altText = node.getText().isNotNull() ? node.getText().toString() : node.getLink().unescape();

                ResolvedLink resolvedLink = context.resolveLink(WikiLinkExtension.WIKI_LINK, node.getLink(), null);
                String url = resolvedLink.getUrl();

                html.attr("src", url);
                html.attr("alt", altText);
                html.srcPos(node.getChars()).withAttr(resolvedLink).tagVoid("img");
            }
        }
    }

    public static class Factory implements NodeRendererFactory {
        @NotNull
        @Override
        public NodeRenderer apply(@NotNull DataHolder options) {
            return new WikiLinkNodeRenderer(options);
        }
    }
}
