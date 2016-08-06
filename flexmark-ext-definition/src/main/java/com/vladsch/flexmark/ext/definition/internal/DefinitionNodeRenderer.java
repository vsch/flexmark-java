package com.vladsch.flexmark.ext.definition.internal;

import com.vladsch.flexmark.ext.definition.DefinitionItem;
import com.vladsch.flexmark.ext.definition.DefinitionList;
import com.vladsch.flexmark.ext.definition.DefinitionTerm;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.util.options.DataHolder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DefinitionNodeRenderer implements NodeRenderer {
    private final DefinitionOptions options;

    public DefinitionNodeRenderer(DataHolder options) {
        this.options = new DefinitionOptions(options);
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        return new HashSet<>(Arrays.asList(
                new NodeRenderingHandler<>(DefinitionList.class, this::render),
                new NodeRenderingHandler<>(DefinitionTerm.class, this::render),
                new NodeRenderingHandler<>(DefinitionItem.class, this::render)
        ));
    }

    private void render(DefinitionList node, NodeRendererContext context, HtmlWriter html) {
        html.withAttr().tag("dl").indent();
        context.renderChildren(node);
        html.unIndent().tag("/dl");
    }

    private void render(DefinitionTerm node, NodeRendererContext context, HtmlWriter html) {
        html.withAttr().tag("dt").indent();
        context.renderChildren(node);
        html.unIndent().tag("/dt");
    }

    private void render(DefinitionItem node, NodeRendererContext context, HtmlWriter html) {
        html.withAttr().tag("dd").indent();
        context.renderChildren(node);
        html.unIndent().tag("/dd");
    }
}
