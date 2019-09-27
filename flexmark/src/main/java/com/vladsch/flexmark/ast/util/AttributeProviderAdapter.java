package com.vladsch.flexmark.ast.util;

import com.vladsch.flexmark.html.renderer.AttributablePart;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeAdaptedVisitor;
import com.vladsch.flexmark.util.html.Attributes;

import java.util.Collection;

public class AttributeProviderAdapter extends NodeAdaptedVisitor<AttributeProvidingHandler<?>> implements AttributeProvidingVisitor<Node> {
    public AttributeProviderAdapter(AttributeProvidingHandler<?>... visitors) {
        super(visitors);
    }

    public AttributeProviderAdapter(AttributeProvidingHandler<?>[]... visitors) {
        super(visitors);
    }

    public AttributeProviderAdapter(Collection<AttributeProvidingHandler<?>> visitors) {
        super(visitors);
    }

    @Override
    public void setAttributes(Node node, AttributablePart part, Attributes attributes) {
        AttributeProvidingHandler<?> handler = getHandler(node);
        if (handler != null) {
            handler.setAttributes(node, part, attributes);
        }
    }
}
