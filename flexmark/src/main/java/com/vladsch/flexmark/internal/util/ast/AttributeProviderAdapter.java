package com.vladsch.flexmark.internal.util.ast;

import com.vladsch.flexmark.html.renderer.AttributablePart;
import com.vladsch.flexmark.internal.util.options.Attributes;
import com.vladsch.flexmark.node.Node;

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
        AttributeProvidingHandler<?> handler = myCustomHandlersMap.get(node.getClass());
        if (handler != null) {
            handler.setAttributes(node, part, attributes);
        }
    }

    //public void visit(Node node) {
    //    A visitor = myCustomVisitorsMap.get(node.getClass());
    //    if (visitor != null) {
    //        callFunctionHere();
    //    } else {
    //        visitChildren(node);
    //    }
    //}
    //
    //public void visitNodeOnly(Node node) {
    //    A visitor = myCustomVisitorsMap.get(node.getClass());
    //    if (visitor != null) {
    //        callFunctionHere();
    //    }
    //}
}
