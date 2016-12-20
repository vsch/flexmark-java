package com.vladsch.flexmark.ext.gfm.strikethrough.internal;

import com.vladsch.flexmark.ext.gfm.strikethrough.Strikethrough;
import com.vladsch.flexmark.html.CustomNodeRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.internal.BlockQuoteParser;
import com.vladsch.flexmark.parser.block.CustomBlockParserFactory;
import com.vladsch.flexmark.util.options.DataHolder;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class StrikethroughJiraRenderer implements NodeRenderer {

    public StrikethroughJiraRenderer(DataHolder options) {
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        HashSet<NodeRenderingHandler<?>> set = new HashSet<>();
        set.add(new NodeRenderingHandler<>(Strikethrough.class, new CustomNodeRenderer<Strikethrough>() {
            @Override
            public void render(Strikethrough node, NodeRendererContext context, HtmlWriter html) {
                StrikethroughJiraRenderer.this.render(node, context, html);
            }
        }));

        return set;
    }

    private void render(Strikethrough node, NodeRendererContext context, HtmlWriter html) {
        html.raw("-");
        context.renderChildren(node);
        html.raw("-");
    }
}
