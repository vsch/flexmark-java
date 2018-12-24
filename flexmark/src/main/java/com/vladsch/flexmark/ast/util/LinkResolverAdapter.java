package com.vladsch.flexmark.ast.util;

import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeAdaptedVisitor;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.ResolvedLink;

import java.util.Collection;

public class LinkResolverAdapter extends NodeAdaptedVisitor<LinkResolvingHandler<?>> implements LinkResolvingVisitor<Node> {
    public LinkResolverAdapter(LinkResolvingHandler<?>... handlers) {
        super(handlers);
    }

    public LinkResolverAdapter(LinkResolvingHandler<?>[]... handlers) {
        super(handlers);
    }

    public LinkResolverAdapter(Collection<LinkResolvingHandler<?>> handlers) {
        super(handlers);
    }

    @Override
    public ResolvedLink resolveLink(Node node, NodeRendererContext context, ResolvedLink link) {
        LinkResolvingHandler<?> handler = myCustomHandlersMap.get(node.getClass());
        if (handler != null) {
            return handler.resolveLink(node, context, link);
        }
        return link;
    }
}
