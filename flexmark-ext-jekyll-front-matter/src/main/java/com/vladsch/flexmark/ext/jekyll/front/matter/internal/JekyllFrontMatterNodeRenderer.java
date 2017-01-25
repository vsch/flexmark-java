
package com.vladsch.flexmark.ext.jekyll.front.matter.internal;

import com.vladsch.flexmark.ext.jekyll.front.matter.JekyllFrontMatterBlock;
import com.vladsch.flexmark.html.CustomNodeRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.util.options.DataHolder;

import java.util.HashSet;
import java.util.Set;

public class JekyllFrontMatterNodeRenderer implements NodeRenderer {
    private final JekyllFrontMatterOptions options;

    public JekyllFrontMatterNodeRenderer(DataHolder options) {
        this.options = new JekyllFrontMatterOptions(options);
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        HashSet<NodeRenderingHandler<?>> set = new HashSet<>();
        set.add(new NodeRenderingHandler<>(JekyllFrontMatterBlock.class, new CustomNodeRenderer<JekyllFrontMatterBlock>() {
            @Override
            public void render(JekyllFrontMatterBlock node, NodeRendererContext context, HtmlWriter html) {
                JekyllFrontMatterNodeRenderer.this.render(node, context, html);
            }
        }));

        return set;
    }

    private void render(JekyllFrontMatterBlock node, NodeRendererContext context, HtmlWriter html) {

    }

    public static class Factory implements NodeRendererFactory {
        @Override
        public NodeRenderer create(final DataHolder options) {
            return new JekyllFrontMatterNodeRenderer(options);
        }
    }
}
