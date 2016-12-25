package com.vladsch.flexmark.superscript.internal;

import com.vladsch.flexmark.html.CustomNodeRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.superscript.Superscript;
import com.vladsch.flexmark.util.options.DataHolder;

import java.util.HashSet;
import java.util.Set;

public class SuperscriptNodeRenderer implements NodeRenderer
{
    public SuperscriptNodeRenderer(DataHolder options) {
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        Set<NodeRenderingHandler<?>> set = new HashSet<>();
        // @formatter:off
        set.add(new NodeRenderingHandler<>(Superscript.class, new CustomNodeRenderer<Superscript>() { @Override public void render(Superscript node, NodeRendererContext context, HtmlWriter html) { SuperscriptNodeRenderer.this.render(node, context, html); } }));
        // @formatter:on
        return set;
    }

    private void render(Superscript node, NodeRendererContext context, HtmlWriter html) {
        html.withAttr().tag("sup");
        context.renderChildren(node);
        html.tag("/sup");
    }
}
