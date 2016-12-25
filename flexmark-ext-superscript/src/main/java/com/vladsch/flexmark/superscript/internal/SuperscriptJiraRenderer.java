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

public class SuperscriptJiraRenderer implements NodeRenderer {
    public SuperscriptJiraRenderer(DataHolder options) {

    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        Set<NodeRenderingHandler<?>> set = new HashSet<>();
        // @formatter:off
        set.add(new NodeRenderingHandler<>(Superscript.class, new CustomNodeRenderer<Superscript>() { @Override public void render(Superscript node, NodeRendererContext context, HtmlWriter html) { SuperscriptJiraRenderer.this.render(node, context, html); } }));
        // @formatter:on
        return set;
    }

    private void render(Superscript node, NodeRendererContext context, HtmlWriter html) {
        html.raw("^");
        context.renderChildren(node);
        html.raw("^");
    }
}
