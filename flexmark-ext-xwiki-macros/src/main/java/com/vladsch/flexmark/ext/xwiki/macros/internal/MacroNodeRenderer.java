package com.vladsch.flexmark.ext.xwiki.macros.internal;

import com.vladsch.flexmark.ext.xwiki.macros.Macro;
import com.vladsch.flexmark.ext.xwiki.macros.MacroBlock;
import com.vladsch.flexmark.html.CustomNodeRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.util.options.DataHolder;

import java.util.HashSet;
import java.util.Set;

public class MacroNodeRenderer implements NodeRenderer {
    public MacroNodeRenderer(DataHolder options) {
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        HashSet<NodeRenderingHandler<?>> set = new HashSet<>();
        set.add(new NodeRenderingHandler<>(Macro.class, new CustomNodeRenderer<Macro>() {
            @Override
            public void render(Macro node, NodeRendererContext context, HtmlWriter html) { MacroNodeRenderer.this.render(node, context, html); }
        }));
        set.add(new NodeRenderingHandler<>(MacroBlock.class, new CustomNodeRenderer<MacroBlock>() {
            @Override
            public void render(MacroBlock node, NodeRendererContext context, HtmlWriter html) { MacroNodeRenderer.this.render(node, context, html); }
        }));
        return set;
    }

    private void render(Macro node, NodeRendererContext context, HtmlWriter html) {
    }

    private void render(MacroBlock node, NodeRendererContext context, HtmlWriter html) {
    }
}
