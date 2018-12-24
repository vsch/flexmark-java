package com.vladsch.flexmark.ast.util;

import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeAdaptingVisitHandler;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.ResolvedLink;

public class LinkResolvingHandler<N extends Node> extends NodeAdaptingVisitHandler<N, LinkResolvingVisitor<N>> implements LinkResolvingVisitor<N> {
    public LinkResolvingHandler(Class<? extends N> aClass, LinkResolvingVisitor<N> adapter) {
        super(aClass, adapter);
    }

    @Override
    public ResolvedLink resolveLink(Node node, NodeRendererContext context, ResolvedLink link) {
        //noinspection unchecked
        return myAdapter.resolveLink((N)node, context, link);
    }
}
