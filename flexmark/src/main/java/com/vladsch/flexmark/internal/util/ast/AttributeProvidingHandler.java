package com.vladsch.flexmark.internal.util.ast;

import com.vladsch.flexmark.html.renderer.AttributablePart;
import com.vladsch.flexmark.internal.util.options.Attributes;
import com.vladsch.flexmark.node.Node;

public class AttributeProvidingHandler<N extends Node> extends NodeAdaptingVisitHandler<N, AttributeProvidingVisitor<N>> implements AttributeProvidingVisitor<N> {
    public AttributeProvidingHandler(Class<? extends N> aClass, AttributeProvidingVisitor<N> adapter) {
        super(aClass, adapter);
    }

    @Override
    public void setAttributes(Node node, AttributablePart part, Attributes attributes) {
        myAdapter.setAttributes((N)node, part, attributes);
    }
}
