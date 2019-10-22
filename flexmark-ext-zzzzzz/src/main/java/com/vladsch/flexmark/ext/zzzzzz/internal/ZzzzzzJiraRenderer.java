package com.vladsch.flexmark.ext.zzzzzz.internal;

import com.vladsch.flexmark.ext.zzzzzz.Zzzzzz;
import com.vladsch.flexmark.ext.zzzzzz.ZzzzzzBlock;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ZzzzzzJiraRenderer implements NodeRenderer {
    public ZzzzzzJiraRenderer(DataHolder options) {

    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        return new HashSet<>(Arrays.asList(
                new NodeRenderingHandler<>(Zzzzzz.class, this::render),// zzzoptionszzz(CUSTOM_NODE)
                new NodeRenderingHandler<>(ZzzzzzBlock.class, this::render)// zzzoptionszzz(CUSTOM_BLOCK_NODE)
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
        @NotNull
        @Override
        public NodeRenderer apply(@NotNull DataHolder options) {
            return new ZzzzzzJiraRenderer(options);
        }
    }
}
