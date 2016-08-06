
package com.vladsch.flexmark.ext.jekyll.front.matter.internal;

import com.vladsch.flexmark.ext.jekyll.front.matter.JekyllFrontMatterBlock;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.util.options.DataHolder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class JekyllFrontMatterNodeRenderer implements NodeRenderer {
    private final JekyllFrontMatterOptions options;

    public JekyllFrontMatterNodeRenderer(DataHolder options) {
        this.options = new JekyllFrontMatterOptions(options);
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        return new HashSet<>(Arrays.asList(
                // new NodeRenderingHandler<>(JekyllFrontMatter.class, this::render),
                new NodeRenderingHandler<>(JekyllFrontMatterBlock.class, this::render)
        ));
    }

    private void render(JekyllFrontMatterBlock node, NodeRendererContext context, HtmlWriter html) {

    }
}
