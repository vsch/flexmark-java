package com.vladsch.flexmark.ext.wikilink.internal;

import com.vladsch.flexmark.ext.wikilink.WikiLink;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.*;
import com.vladsch.flexmark.internal.util.options.DataHolder;
import com.vladsch.flexmark.internal.util.sequence.BasedSequence;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class WikiLinkNodeRenderer implements NodeRenderer {
    private static String fromChars = " +/<>";
    private static String toChars = "-----";

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
        LinkRendering rendering = context.getLinkRendering(LinkType.Wiki, wikiLinkToUrl(node.getLink()), node.getText().isNotNull() ? node.getText().toString() : node.getLink().toString(), null, node);
        html.attr("href", rendering.getUrl());
        html.withAttr(rendering.getAttributes()).tag("a");
        html.text(rendering.getText());
        html.tag("/a");
    }

    private String wikiLinkToUrl(BasedSequence wikiLink) {
        StringBuilder sb = new StringBuilder();
        int iMax = wikiLink.length();
        for (int i = 0; i < iMax; i++) {
            char c = wikiLink.charAt(i);
            int pos = fromChars.indexOf(c);
            if (pos < 0) {
                sb.append(c);
            } else {
                sb.append(toChars.charAt(pos));
            }
        }

        sb.append(options.linkFileExtension);
        return sb.toString();
    }
}
