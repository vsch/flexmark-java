package com.vladsch.flexmark.ext.zzzzzz.internal;

import com.vladsch.flexmark.ext.zzzzzz.Zzzzzz;
import com.vladsch.flexmark.ext.zzzzzz.ZzzzzzBlock;
import com.vladsch.flexmark.html.CustomNodeRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.util.options.DataHolder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ZzzzzzJiraRenderer implements NodeRenderer {
    public ZzzzzzJiraRenderer(DataHolder options) {

    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        return new HashSet<NodeRenderingHandler<? extends com.vladsch.flexmark.ast.Node>>(Arrays.asList(
                // @formatter:off
                new NodeRenderingHandler<Zzzzzz>(Zzzzzz.class, new CustomNodeRenderer<Zzzzzz>() { @Override public void render(Zzzzzz node, NodeRendererContext context, HtmlWriter html) { ZzzzzzJiraRenderer.this.render(node, context, html); } }),// zzzoptionszzz(CUSTOM_NODE)
                new NodeRenderingHandler<ZzzzzzBlock>(ZzzzzzBlock.class, new CustomNodeRenderer<ZzzzzzBlock>() { @Override public void render(ZzzzzzBlock node, NodeRendererContext context, HtmlWriter html) { ZzzzzzJiraRenderer.this.render(node, context, html); } })// zzzoptionszzz(CUSTOM_BLOCK_NODE)
                // @formatter:on
        ));
    }

    private void render(Zzzzzz node, NodeRendererContext context, HtmlWriter html) {
        html.raw("");
    }

    private void render(ZzzzzzBlock node, NodeRendererContext context, HtmlWriter html) {
        html.raw("{}");
        html.raw(node.getText().unescape());
        html.raw("{}");
    }

    public static class Factory implements NodeRendererFactory {
        @Override
        public NodeRenderer create(final DataHolder options) {
            return new ZzzzzzJiraRenderer(options);
        }
    }
}
