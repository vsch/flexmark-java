package com.vladsch.flexmark.ext.jekyll.front.matter.internal;

import com.vladsch.flexmark.ext.jekyll.front.matter.JekyllFrontMatterBlock;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class JekyllFrontMatterNodeRenderer implements NodeRenderer {
    public JekyllFrontMatterNodeRenderer(DataHolder options) {

    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        HashSet<NodeRenderingHandler<?>> set = new HashSet<>();
        set.add(new NodeRenderingHandler<>(JekyllFrontMatterBlock.class, this::render));

        return set;
    }

    private void render(JekyllFrontMatterBlock node, NodeRendererContext context, HtmlWriter html) {

    }

    public static class Factory implements NodeRendererFactory {
        @NotNull
        @Override
        public NodeRenderer apply(@NotNull DataHolder options) {
            return new JekyllFrontMatterNodeRenderer(options);
        }
    }
}
