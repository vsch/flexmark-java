package com.vladsch.flexmark.ext.attributes.internal;

import com.vladsch.flexmark.ext.attributes.AttributeNode;
import com.vladsch.flexmark.ext.attributes.AttributesNode;
import com.vladsch.flexmark.html.CustomNodeRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.util.options.DataHolder;

import java.util.HashSet;
import java.util.Set;

public class AttributesNodeRenderer implements NodeRenderer {

    public AttributesNodeRenderer(DataHolder options) {
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        HashSet<NodeRenderingHandler<?>> set = new HashSet<NodeRenderingHandler<?>>();
        set.add(new NodeRenderingHandler<AttributesNode>(AttributesNode.class, new CustomNodeRenderer<AttributesNode>() {
            @Override
            public void render(AttributesNode node, NodeRendererContext context, HtmlWriter html) {
                int tmp = 0;
            }
        }));
        set.add(new NodeRenderingHandler<AttributeNode>(AttributeNode.class, new CustomNodeRenderer<AttributeNode>() {
            @Override
            public void render(AttributeNode node, NodeRendererContext context, HtmlWriter html) {
                int tmp = 0;
            }
        }));
        return set;
    }

    public static class Factory implements NodeRendererFactory {
        @Override
        public NodeRenderer create(final DataHolder options) {
            return new AttributesNodeRenderer(options);
        }
    }
}
