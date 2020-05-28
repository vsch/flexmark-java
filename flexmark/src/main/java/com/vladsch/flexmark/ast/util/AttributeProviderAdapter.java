package com.vladsch.flexmark.ast.util;

import com.vladsch.flexmark.html.renderer.AttributablePart;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.html.Attributes;
import com.vladsch.flexmark.util.html.MutableAttributes;
import com.vladsch.flexmark.util.visitor.AstActionHandler;

import java.util.Collection;

@SuppressWarnings("rawtypes")
public class AttributeProviderAdapter extends AstActionHandler<AttributeProviderAdapter, Node, AttributeProvidingHandler.AttributeProvidingVisitor<Node>, AttributeProvidingHandler<Node>> implements AttributeProvidingHandler.AttributeProvidingVisitor<Node> {
    protected static final AttributeProvidingHandler[] EMPTY_HANDLERS = new AttributeProvidingHandler[0];

    public AttributeProviderAdapter(AttributeProvidingHandler... handlers) {
        super(Node.AST_ADAPTER);
        super.addActionHandlers(handlers);
    }

    public AttributeProviderAdapter(AttributeProvidingHandler[]... handlers) {
        super(Node.AST_ADAPTER);
        //noinspection unchecked
        super.addActionHandlers(handlers);
    }

    public AttributeProviderAdapter(Collection<AttributeProvidingHandler> handlers) {
        super(Node.AST_ADAPTER);
        addHandlers(handlers);
    }

    public AttributeProviderAdapter addHandlers(Collection<AttributeProvidingHandler> handlers) {
        return addHandlers(handlers.toArray(EMPTY_HANDLERS));
    }

    // needed for backward compatibility with extension handler arrays typed as VisitHandler<?>[]
    public AttributeProviderAdapter addHandlers(AttributeProvidingHandler... handlers) {
        return super.addActionHandlers(handlers);
    }

    public AttributeProviderAdapter addHandlers(AttributeProvidingHandler[]... handlers) {
        //noinspection unchecked
        return super.addActionHandlers(handlers);
    }

    public AttributeProviderAdapter addHandler(AttributeProvidingHandler handler) {
        //noinspection unchecked
        return super.addActionHandler(handler);
    }

    @Override
    public void setAttributes(Node node, AttributablePart part, MutableAttributes attributes) {
        processNode(node, false, (n, handler) -> handler.setAttributes(n, part, attributes));
    }
}
