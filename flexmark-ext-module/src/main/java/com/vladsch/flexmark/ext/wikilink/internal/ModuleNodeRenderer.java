package com.vladsch.flexmark.ext.wikilink.internal;

import com.vladsch.flexmark.ext.wikilink.Module;
import com.vladsch.flexmark.ext.wikilink.ModuleBlock;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.node.Node;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ModuleNodeRenderer implements NodeRenderer {
    private static String fromChars = " +/<>";
    private static String toChars = "-----";

    private final NodeRendererContext context;
    private final HtmlWriter html;
    private final ModuleOptions options;

    public ModuleNodeRenderer(NodeRendererContext context) {
        this.context = context;
        this.html = context.getHtmlWriter();
        this.options = new ModuleOptions(context.getOptions());
    }

    @Override
    public Set<Class<? extends Node>> getNodeTypes() {
        return new HashSet<>(Collections.singletonList(Module.class));
    }

    @Override
    public void render(Node node) {
        if (node instanceof Module) {
            renderModule((Module) node);
        }
        if (node instanceof ModuleBlock) {
            renderModuleBlock((ModuleBlock) node);
        }
    }

    private void renderModule(Module node) {
        if (options.moduleOption1) html.attr("href", context.encodeUrl(options.moduleOption2));

        html.withAttr().tag("a");
        html.text(node.getText().unescape());
        html.tag("/a");
    }

    private void renderModuleBlock(ModuleBlock node) {
        // definition only, no rendered HTML
    }
}
