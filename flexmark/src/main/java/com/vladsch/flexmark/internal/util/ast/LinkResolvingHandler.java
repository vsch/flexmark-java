package com.vladsch.flexmark.internal.util.ast;

import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.ResolvedLink;
import com.vladsch.flexmark.node.Node;

public class LinkResolvingHandler<N extends Node> extends NodeAdaptingVisitHandler<N, LinkResolvingVisitor<N>> implements LinkResolvingVisitor<N> {
    public LinkResolvingHandler(Class<? extends N> aClass, LinkResolvingVisitor<N> adapter) {
        super(aClass, adapter);
    }

    @Override
    public ResolvedLink resolveLink(Node node, NodeRendererContext context, ResolvedLink link) {
        return myAdapter.resolveLink((N)node, context, link);
    }
}
