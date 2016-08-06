package com.vladsch.flexmark.ast.util;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ast.NodeAdaptedVisitor;
import com.vladsch.flexmark.html.renderer.AttributablePart;
import com.vladsch.flexmark.util.options.Attributes;

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

    //public void visit(Node ast) {
    //    A visitor = myCustomVisitorsMap.get(ast.getClass());
    //    if (visitor != null) {
    //        callFunctionHere();
    //    } else {
    //        visitChildren(ast);
    //    }
    //}
    //
    //public void visitNodeOnly(Node ast) {
    //    A visitor = myCustomVisitorsMap.get(ast.getClass());
    //    if (visitor != null) {
    //        callFunctionHere();
    //    }
    //}
}
