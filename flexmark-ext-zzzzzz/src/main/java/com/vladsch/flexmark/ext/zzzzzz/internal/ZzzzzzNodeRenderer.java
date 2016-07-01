package com.vladsch.flexmark.ext.zzzzzz.internal;

import com.vladsch.flexmark.ext.zzzzzz.Zzzzzz;
import com.vladsch.flexmark.ext.zzzzzz.ZzzzzzBlock;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.PhasedNodeRenderer;
import com.vladsch.flexmark.html.renderer.RenderingPhase;
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

    private final NodeRendererContext context;
    private final HtmlWriter html;
    private final ZzzzzzOptions options;

    public ZzzzzzNodeRenderer(NodeRendererContext context) {
        this.context = context;
        this.html = context.getHtmlWriter();
        this.options = new ZzzzzzOptions(context.getOptions());
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
    public void render(Document document, RenderingPhase phase) {//zzzoptionszzz(REMOVE, PHASED_NODE_RENDERER)
    }//zzzoptionszzz(REMOVE, PHASED_NODE_RENDERER)
    
    @Override
    public void render(Node node) {
        if (node instanceof Zzzzzz) {
            renderZzzzzz((Zzzzzz) node);
        } else if (node instanceof ZzzzzzBlock) {
            renderZzzzzzBlock((ZzzzzzBlock) node);
        }
    }

    private void renderZzzzzz(Zzzzzz node) {
        if (options.zzzzzzOption1) html.attr("href", context.encodeUrl(options.zzzzzzOption2));

        html.withAttr().tag("a");
        html.text(node.getText().unescape());
        html.tag("/a");
    }

    private void renderZzzzzzBlock(ZzzzzzBlock node) {
        if (options.zzzzzzOption1) html.attr("href", context.encodeUrl(options.zzzzzzOption2));

        html.withAttr().tag("a");
        html.text(node.getText().unescape());
        html.tag("/a");
    }
}
