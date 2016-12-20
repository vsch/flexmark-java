package com.vladsch.flexmark.ext.gfm.strikethrough.internal;

import com.vladsch.flexmark.ext.gfm.strikethrough.Strikethrough;
import com.vladsch.flexmark.html.CustomNodeRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.util.options.DataHolder;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class StrikethroughNodeRenderer implements NodeRenderer {

    public StrikethroughNodeRenderer(DataHolder options) {
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        HashSet<NodeRenderingHandler<?>> set = new HashSet<>();
        set.add(new NodeRenderingHandler<>(Strikethrough.class, new CustomNodeRenderer<Strikethrough>() {
            @Override
            public void render(Strikethrough node, NodeRendererContext context, HtmlWriter html) {
                StrikethroughNodeRenderer.this.render(node, context, html);
            }
        }));

        return set;
    }

    private void render(Strikethrough node, NodeRendererContext context, HtmlWriter html) {
        html.srcPos(node.getText()).withAttr().tag("del");
        context.renderChildren(node);
        html.tag("/del");
    }
}
