package com.vladsch.flexmark.ext.wikilink.internal;

import com.vladsch.flexmark.ext.wikilink.WikiLink;
import com.vladsch.flexmark.ext.wikilink.WikiLinkExtension;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.html.renderer.ResolvedLink;
import com.vladsch.flexmark.internal.util.options.DataHolder;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class WikiLinkNodeRenderer implements NodeRenderer {
    private final WikiLinkOptions options;

    public WikiLinkNodeRenderer(DataHolder options) {
        this.options = new WikiLinkOptions(options);
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        return new HashSet<>(Collections.singletonList(
                new NodeRenderingHandler<>(WikiLink.class, this::render)
        ));
    }

    private void render(WikiLink node, NodeRendererContext context, HtmlWriter html) {
        ResolvedLink resolvedLink = context.resolveLink(WikiLinkExtension.WIKI_LINK, node.getLink().toString());
        html.attr("href", resolvedLink.getUrl());
        html.withAttr(resolvedLink).tag("a");
        html.text(node.getText().isNotNull() ? node.getText().toString() : node.getLink().toString());
        html.tag("/a");
    }
}
