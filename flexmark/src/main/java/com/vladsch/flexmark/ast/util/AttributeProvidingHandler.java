package com.vladsch.flexmark.ast.util;

import com.vladsch.flexmark.html.renderer.AttributablePart;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.html.MutableAttributes;
import com.vladsch.flexmark.util.visitor.AstAction;
import com.vladsch.flexmark.util.visitor.AstHandler;

public class AttributeProvidingHandler<N extends Node> extends AstHandler<N, AttributeProvidingHandler.AttributeProvidingVisitor<N>> {
    public AttributeProvidingHandler(Class<N> aClass, AttributeProvidingVisitor<N> adapter) {
        super(aClass, adapter);
    }

    public void setAttributes(Node node, AttributablePart part, MutableAttributes attributes) {
        //noinspection unchecked
        getAdapter().setAttributes((N) node, part, attributes);
    }

    public static interface AttributeProvidingVisitor<N extends Node> extends AstAction<N> {
        void setAttributes(N node, AttributablePart part, MutableAttributes attributes);
    }
}
