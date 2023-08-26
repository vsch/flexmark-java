package com.vladsch.flexmark.ext.d2.internal;

import com.vladsch.flexmark.ext.d2.D2Node;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class D2NodeRenderer implements NodeRenderer {
    public D2NodeRenderer(DataHolder options) {

    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        HashSet<NodeRenderingHandler<?>> set = new HashSet<>();
        set.add(new NodeRenderingHandler<>(D2Node.class, this::render));
        return set;
    }

    private void render(D2Node node, NodeRendererContext context, HtmlWriter html) {
        html.tag("p");
        html.text("rendering d2 node");
        html.tag("/p");
    }

    public static class Factory implements NodeRendererFactory {
        @NotNull
        @Override
        public NodeRenderer apply(@NotNull DataHolder options) {
            return new D2NodeRenderer(options);
        }
    }
}
