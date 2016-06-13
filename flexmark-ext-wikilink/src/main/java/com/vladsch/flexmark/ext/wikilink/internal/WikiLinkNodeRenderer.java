package com.vladsch.flexmark.ext.wikilink.internal;

import com.vladsch.flexmark.ext.wikilink.WikiLink;
import com.vladsch.flexmark.ext.wikilink.WikiLinkExtension;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.internal.util.BasedSequence;
import com.vladsch.flexmark.node.Node;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class WikiLinkNodeRenderer implements NodeRenderer {
    private static String fromChars = " +/<>";
    private static String toChars = "-----";

    private final NodeRendererContext context;
    private final HtmlWriter html;
    private final boolean isLinkFirst;

    public WikiLinkNodeRenderer(NodeRendererContext context) {
        this.context = context;
        this.html = context.getHtmlWriter();
        this.isLinkFirst = context.getOptions().get(WikiLinkExtension.LINK_FIRST_SYNTAX);
    }

    @Override
    public Set<Class<? extends Node>> getNodeTypes() {
        return new HashSet<>(Collections.singletonList(WikiLink.class));
    }

    @Override
    public void render(Node node) {
        if (node instanceof WikiLink) {
            renderWikiLink((WikiLink) node);
        }
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

        return sb.toString();
    }

    private void renderWikiLink(WikiLink node) {
        String url = wikiLinkToUrl(node.getLink());
        html.attr("href", context.encodeUrl(url));
        html.withAttr().tag("a");
        html.text(node.getText().isNotNull() ? node.getText().toString() : node.getLink().toString());
        html.tag("/a");
    }
}
