package com.vladsch.flexmark.ext.gfm.strikethrough.internal;

import com.vladsch.flexmark.ext.gfm.strikethrough.Strikethrough;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.node.Node;

import java.util.Collections;
import java.util.Set;

public class StrikethroughNodeRenderer implements NodeRenderer {

    private final NodeRendererContext context;
    private final HtmlWriter html;

    public StrikethroughNodeRenderer(NodeRendererContext context) {
        this.context = context;
        this.html = context.getHtmlWriter();
    }

    @Override
    public Set<Class<? extends Node>> getNodeTypes() {
        return Collections.<Class<? extends Node>>singleton(Strikethrough.class);
    }

    @Override
    public void render(Node node) {
        html.withAttr().tag("del");
        context.renderChildren(node);
        html.tag("/del");
    }
}
