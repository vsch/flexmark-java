package com.vladsch.flexmark.ast.util;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ast.NodeAdaptingVisitHandler;
import com.vladsch.flexmark.html.renderer.AttributablePart;
import com.vladsch.flexmark.util.html.Attributes;

public class AttributeProvidingHandler<N extends Node> extends NodeAdaptingVisitHandler<N, AttributeProvidingVisitor<N>> implements AttributeProvidingVisitor<N> {
    public AttributeProvidingHandler(Class<? extends N> aClass, AttributeProvidingVisitor<N> adapter) {
        super(aClass, adapter);
    }

    @Override
    public void setAttributes(Node node, AttributablePart part, Attributes attributes) {
        myAdapter.setAttributes((N)node, part, attributes);
    }
}
