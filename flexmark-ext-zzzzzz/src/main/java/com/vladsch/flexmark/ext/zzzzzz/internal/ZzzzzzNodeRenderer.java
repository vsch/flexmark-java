package com.vladsch.flexmark.ext.zzzzzz.internal;

import com.vladsch.flexmark.ext.zzzzzz.Zzzzzz;
import com.vladsch.flexmark.ext.zzzzzz.ZzzzzzBlock;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.*;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ZzzzzzNodeRenderer implements NodeRenderer
        , PhasedNodeRenderer // zzzoptionszzz(PHASED_NODE_RENDERER)
{
    private static String fromChars = " +/<>";
    private static String toChars = "-----";

    final private ZzzzzzOptions options;// zzzoptionszzz(CUSTOM_PROPERTIES)

    public ZzzzzzNodeRenderer(DataHolder options) {
        this.options = new ZzzzzzOptions(options);// zzzoptionszzz(CUSTOM_PROPERTIES)
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        Set<NodeRenderingHandler<?>> set = new HashSet<>();
        // @formatter:off
        set.add(new NodeRenderingHandler<>(Zzzzzz.class, ZzzzzzNodeRenderer.this::render));// zzzoptionszzz(CUSTOM_NODE)
        set.add(new NodeRenderingHandler<>(ZzzzzzBlock.class, ZzzzzzNodeRenderer.this::render));// zzzoptionszzz(CUSTOM_BLOCK_NODE),// zzzoptionszzz(CUSTOM_NODE)
        // @formatter:on
        return set;
    }

    @Override//zzzoptionszzz(REMOVE, PHASED_NODE_RENDERER)
    public Set<RenderingPhase> getRenderingPhases() {//zzzoptionszzz(REMOVE, PHASED_NODE_RENDERER)
        return new HashSet<>(Collections.singletonList(RenderingPhase.BODY_BOTTOM));//zzzoptionszzz(REMOVE, PHASED_NODE_RENDERER)
    }//zzzoptionszzz(REMOVE, PHASED_NODE_RENDERER)

    @Override//zzzoptionszzz(REMOVE, PHASED_NODE_RENDERER)
    public void renderDocument(@NotNull NodeRendererContext context, @NotNull HtmlWriter html, @NotNull Document document, @NotNull RenderingPhase phase) {//zzzoptionszzz(REMOVE, PHASED_NODE_RENDERER)
    }//zzzoptionszzz(REMOVE, PHASED_NODE_RENDERER)

    private void render(Zzzzzz node, NodeRendererContext context, HtmlWriter html) {
        if (options.zzzzzzOption1) html.attr("href", context.encodeUrl(options.zzzzzzOption2));

        html.withAttr().tag("a");
        html.text(node.getText().unescape());
        html.tag("/a");
    }

    private void render(ZzzzzzBlock node, NodeRendererContext context, HtmlWriter html) {
        if (options.zzzzzzOption1) html.attr("href", context.encodeUrl(options.zzzzzzOption2));

        html.withAttr().tag("a");
        html.text(node.getText().unescape());
        html.tag("/a");
    }

    public static class Factory implements NodeRendererFactory {
        @NotNull
        @Override
        public NodeRenderer apply(@NotNull DataHolder options) {
            return new ZzzzzzNodeRenderer(options);
        }
    }
}
