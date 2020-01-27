package com.vladsch.flexmark.ext.wikilink.internal;

import com.vladsch.flexmark.ext.wikilink.WikiLink;
import com.vladsch.flexmark.ext.wikilink.WikiLinkExtension;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.*;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class WikiLinkJiraRenderer implements NodeRenderer {
    final private WikiLinkOptions options;

    public WikiLinkJiraRenderer(DataHolder options) {
        this.options = new WikiLinkOptions(options);
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        HashSet<NodeRenderingHandler<?>> set = new HashSet<>();
        set.add(new NodeRenderingHandler<>(WikiLink.class, this::render));
        return set;
    }

    private void render(WikiLink node, NodeRendererContext context, HtmlWriter html) {
        if (options.disableRendering) {
            html.text(node.getChars().unescape());
        } else {
            ResolvedLink resolvedLink = context.resolveLink(WikiLinkExtension.WIKI_LINK, node.getLink().toString(), null);
            html.raw("[");
            html.raw(node.getText().isNotNull() ? node.getText().toString() : node.getPageRef().toString());
            html.raw("|");
            html.raw(resolvedLink.getUrl());
            html.raw("]");
        }
    }

    public static class Factory implements NodeRendererFactory {
        @NotNull
        @Override
        public NodeRenderer apply(@NotNull DataHolder options) {
            return new WikiLinkJiraRenderer(options);
        }
    }
}
