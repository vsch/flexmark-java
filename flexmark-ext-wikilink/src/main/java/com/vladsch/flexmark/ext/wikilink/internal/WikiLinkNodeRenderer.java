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
        if (options.disableRendering) {
           html.text(node.getChars().unescape());
        } else {
            ResolvedLink resolvedLink = context.resolveLink(WikiLinkExtension.WIKI_LINK, node.getPageRef().toString(), null);
            String anchorRef = node.getAnchorMarker().isNull() ? "" : node.getAnchorMarker().toString() + node.getAnchorRef().toString();
            html.attr("href", resolvedLink.getUrl() + anchorRef);
            html.withAttr(resolvedLink).tag("a");
            html.text(node.getText().isNotNull() ? node.getText().toString() : node.getPageRef().toString());
            html.tag("/a");
        }
    }
}
