package com.vladsch.flexmark.ext.attributes.internal;

import com.vladsch.flexmark.ast.TextBase;
import com.vladsch.flexmark.ext.attributes.AttributesNode;
import com.vladsch.flexmark.html.renderer.AttributablePart;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.html.Attributes;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class AttributesNodeRenderer implements NodeRenderer {

    final private AttributesOptions myOptions;

    public AttributesNodeRenderer(DataHolder options) {
        myOptions = new AttributesOptions(options);
    }

    // only registered if assignTextAttributes is enabled
    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        HashSet<NodeRenderingHandler<?>> set = new HashSet<>();
        set.add(new NodeRenderingHandler<>(AttributesNode.class, (node, context, html) -> {

        }));
        set.add(new NodeRenderingHandler<>(TextBase.class, (node, context, html) -> {
            if (myOptions.assignTextAttributes) {
                Attributes nodeAttributes = context.extendRenderingNodeAttributes(AttributablePart.NODE, null);
                if (!nodeAttributes.isEmpty()) {
                    // has attributes then we wrap it in a span
                    html.setAttributes(nodeAttributes).withAttr().tag("span");
                    context.delegateRender();
                    html.closeTag("span");
                    return;
                }
            }
            context.delegateRender();
        }));
        return set;
    }

    public static class Factory implements NodeRendererFactory {
        @NotNull
        @Override
        public NodeRenderer apply(@NotNull DataHolder options) {
            return new AttributesNodeRenderer(options);
        }
    }
}
