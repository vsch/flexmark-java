package com.vladsch.flexmark.ext.gfm.strikethrough.internal;

import com.vladsch.flexmark.ext.gfm.strikethrough.Strikethrough;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderHandler;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.internal.util.collection.DataHolder;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class StrikethroughNodeRenderer implements NodeRenderer {

    public StrikethroughNodeRenderer(DataHolder options) {
    }

    @Override
    public Set<NodeRenderHandler<?>> getNodeRenderers() {
        return new HashSet<>(Collections.singletonList(
                new NodeRenderHandler<>(Strikethrough.class, this::render)
        ));
    }

    private void render(Strikethrough node, NodeRendererContext context, HtmlWriter html) {
        html.withAttr().tag("del");
        context.renderChildren(node);
        html.tag("/del");
    }
}
