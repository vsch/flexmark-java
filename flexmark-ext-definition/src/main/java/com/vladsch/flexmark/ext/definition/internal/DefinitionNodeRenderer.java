package com.vladsch.flexmark.ext.definition.internal;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ext.definition.DefinitionItem;
import com.vladsch.flexmark.ext.definition.DefinitionList;
import com.vladsch.flexmark.ext.definition.DefinitionTerm;
import com.vladsch.flexmark.html.CustomNodeRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.util.options.DataHolder;

import java.util.HashSet;
import java.util.Set;

public class DefinitionNodeRenderer implements NodeRenderer {
    private final DefinitionOptions options;

    public DefinitionNodeRenderer(DataHolder options) {
        this.options = new DefinitionOptions(options);
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        HashSet<NodeRenderingHandler<?>> set = new HashSet<NodeRenderingHandler<?>>();
        set.add(new NodeRenderingHandler<DefinitionList>(DefinitionList.class, new CustomNodeRenderer<DefinitionList>() {
            @Override
            public void render(DefinitionList node, NodeRendererContext context, HtmlWriter html) {
                DefinitionNodeRenderer.this.render(node, context, html);
            }
        }));
        set.add(new NodeRenderingHandler<DefinitionTerm>(DefinitionTerm.class, new CustomNodeRenderer<DefinitionTerm>() {
            @Override
            public void render(DefinitionTerm node, NodeRendererContext context, HtmlWriter html) {
                DefinitionNodeRenderer.this.render(node, context, html);
            }
        }));
        set.add(new NodeRenderingHandler<DefinitionItem>(DefinitionItem.class, new CustomNodeRenderer<DefinitionItem>() {
            @Override
            public void render(DefinitionItem node, NodeRendererContext context, HtmlWriter html) {
                DefinitionNodeRenderer.this.render(node, context, html);
            }
        }));

        return set;
    }

    private void render(DefinitionList node, NodeRendererContext context, HtmlWriter html) {
        html.withAttr().tag("dl").indent();
        context.renderChildren(node);
        html.unIndent().tag("/dl");
    }

    private void render(final DefinitionTerm node, final NodeRendererContext context, HtmlWriter html) {
        final Node childText = node.getFirstChild();
        if (childText != null) {
            html.srcPosWithEOL(childText.getChars()).withAttr().withCondIndent().tagLine("dt", new Runnable() {
                @Override
                public void run() {
                    context.renderChildren(node);
                }
            });
        }
    }

    private void render(final DefinitionItem node, final NodeRendererContext context, final HtmlWriter html) {
        html.srcPosWithEOL(node.getChars()).withAttr().withCondIndent().tagLine("dd", new Runnable() {
            @Override
            public void run() {
                context.renderChildren(node);
            }
        });
    }

    public static class Factory implements NodeRendererFactory {
        @Override
        public NodeRenderer create(final DataHolder options) {
            return new DefinitionNodeRenderer(options);
        }
    }
}
