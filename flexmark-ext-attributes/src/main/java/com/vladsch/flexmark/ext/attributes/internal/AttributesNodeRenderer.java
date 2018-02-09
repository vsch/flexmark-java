package com.vladsch.flexmark.ext.attributes.internal;

import com.vladsch.flexmark.ast.TextBase;
import com.vladsch.flexmark.ext.attributes.AttributesNode;
import com.vladsch.flexmark.html.CustomNodeRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.*;
import com.vladsch.flexmark.util.html.Attributes;
import com.vladsch.flexmark.util.options.DataHolder;

import java.util.HashSet;
import java.util.Set;

public class AttributesNodeRenderer implements NodeRenderer {

    private final AttributesOptions myOptions;

    public AttributesNodeRenderer(DataHolder options) {
        myOptions = new AttributesOptions(options);
    }

    // only registered if assignTextAttributes is enabled
    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        HashSet<NodeRenderingHandler<?>> set = new HashSet<NodeRenderingHandler<?>>();
        set.add(new NodeRenderingHandler<AttributesNode>(AttributesNode.class, new CustomNodeRenderer<AttributesNode>() {
            @Override
            public void render(AttributesNode node, NodeRendererContext context, HtmlWriter html) {
                int tmp = 0;
            }
        }));
        set.add(new NodeRenderingHandler<TextBase>(TextBase.class, new CustomNodeRenderer<TextBase>() {
            @Override
            public void render(TextBase node, NodeRendererContext context, HtmlWriter html) {
                if (myOptions.assignTextAttributes) {
                    final Attributes nodeAttributes = context.extendRenderingNodeAttributes(AttributablePart.NODE, null);
                    if (!nodeAttributes.isEmpty()) {
                        // has attributes then we wrap it in a span
                        html.setAttributes(nodeAttributes).withAttr().tag("span");
                        context.delegateRender();
                        html.closeTag("span");
                        return;
                    }
                }
                context.delegateRender();
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
