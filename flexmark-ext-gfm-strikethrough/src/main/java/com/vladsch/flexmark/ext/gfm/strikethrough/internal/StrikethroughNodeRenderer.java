package com.vladsch.flexmark.ext.gfm.strikethrough.internal;

import com.vladsch.flexmark.ext.gfm.strikethrough.Strikethrough;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.internal.util.collection.DataHolder;
import com.vladsch.flexmark.node.Node;

import java.util.Collections;
import java.util.Set;

public class StrikethroughNodeRenderer implements NodeRenderer {

    public StrikethroughNodeRenderer(DataHolder options) {
    }

    @Override
    public Set<Class<? extends Node>> getNodeTypes() {
        return Collections.<Class<? extends Node>>singleton(Strikethrough.class);
    }

    @Override
    public void render(NodeRendererContext context, HtmlWriter html, Node node) {
        html.withAttr().tag("del");
        context.renderChildren(node);
        html.tag("/del");
    }
}
