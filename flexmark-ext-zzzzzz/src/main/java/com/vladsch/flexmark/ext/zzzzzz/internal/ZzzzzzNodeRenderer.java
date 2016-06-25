package com.vladsch.flexmark.ext.zzzzzz.internal;

import com.vladsch.flexmark.ext.zzzzzz.Zzzzzz;
import com.vladsch.flexmark.ext.zzzzzz.ZzzzzzBlock;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.node.Node;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ZzzzzzNodeRenderer implements NodeRenderer {
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
        return new HashSet<>(Collections.singletonList(Zzzzzz.class));
    }

    @Override
    public void render(Node node) {
        if (node instanceof Zzzzzz) {
            renderZzzzzz((Zzzzzz) node);
        }
        if (node instanceof ZzzzzzBlock) {
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
        // definition only, no rendered HTML
    }
}
