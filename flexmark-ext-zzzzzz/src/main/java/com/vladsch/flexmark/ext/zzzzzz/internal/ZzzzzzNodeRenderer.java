package com.vladsch.flexmark.ext.zzzzzz.internal;

import com.vladsch.flexmark.ext.zzzzzz.Zzzzzz;
import com.vladsch.flexmark.ext.zzzzzz.ZzzzzzBlock;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.PhasedNodeRenderer;
import com.vladsch.flexmark.html.renderer.RenderingPhase;
import com.vladsch.flexmark.internal.util.collection.DataHolder;
import com.vladsch.flexmark.node.Document;
import com.vladsch.flexmark.node.Node;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ZzzzzzNodeRenderer implements NodeRenderer
    , PhasedNodeRenderer // zzzoptionszzz(PHASED_NODE_RENDERER)
{
    private static String fromChars = " +/<>";
    private static String toChars = "-----";

    private final ZzzzzzOptions options;

    public ZzzzzzNodeRenderer(DataHolder options) {
        this.options = new ZzzzzzOptions(options);
    }

    @Override
    public Set<Class<? extends Node>> getNodeTypes() {
        return new HashSet<>(Arrays.asList(
                Zzzzzz.class,
                ZzzzzzBlock.class
        ));
    }

    @Override//zzzoptionszzz(REMOVE, PHASED_NODE_RENDERER)
    public Set<RenderingPhase> getRenderingPhases() {//zzzoptionszzz(REMOVE, PHASED_NODE_RENDERER)
        return new HashSet<>(Collections.singletonList(RenderingPhase.BODY_BOTTOM));//zzzoptionszzz(REMOVE, PHASED_NODE_RENDERER)
    }//zzzoptionszzz(REMOVE, PHASED_NODE_RENDERER)
    
    @Override//zzzoptionszzz(REMOVE, PHASED_NODE_RENDERER)
    public void renderDocument(NodeRendererContext context, HtmlWriter html, Document document, RenderingPhase phase) {//zzzoptionszzz(REMOVE, PHASED_NODE_RENDERER)
    }//zzzoptionszzz(REMOVE, PHASED_NODE_RENDERER)
    
    @Override
    public void render(NodeRendererContext context, HtmlWriter html, Node node) {
        if (node instanceof Zzzzzz) {
            renderZzzzzz(context, html, (Zzzzzz) node);
        } else if (node instanceof ZzzzzzBlock) {
            renderZzzzzzBlock(context, html, (ZzzzzzBlock) node);
        }
    }

    private void renderZzzzzz(NodeRendererContext context, HtmlWriter html, Zzzzzz node) {
        if (options.zzzzzzOption1) html.attr("href", context.encodeUrl(options.zzzzzzOption2));

        html.withAttr().tag("a");
        html.text(node.getText().unescape());
        html.tag("/a");
    }

    private void renderZzzzzzBlock(NodeRendererContext context, HtmlWriter html, ZzzzzzBlock node) {
        if (options.zzzzzzOption1) html.attr("href", context.encodeUrl(options.zzzzzzOption2));

        html.withAttr().tag("a");
        html.text(node.getText().unescape());
        html.tag("/a");
    }
}
