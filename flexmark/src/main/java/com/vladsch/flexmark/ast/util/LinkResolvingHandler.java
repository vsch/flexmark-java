package com.vladsch.flexmark.ast.util;

import com.vladsch.flexmark.html.renderer.LinkResolverBasicContext;
import com.vladsch.flexmark.html.renderer.ResolvedLink;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.visitor.AstAction;
import com.vladsch.flexmark.util.visitor.AstHandler;

public class LinkResolvingHandler<N extends Node> extends AstHandler<N, LinkResolvingHandler.LinkResolvingVisitor<N>> {
    public LinkResolvingHandler(Class<N> aClass, LinkResolvingVisitor<N> adapter) {
        super(aClass, adapter);
    }

    public ResolvedLink resolveLink(Node node, LinkResolverBasicContext context, ResolvedLink link) {
        //noinspection unchecked
        return getAdapter().resolveLink((N) node, context, link);
    }

    public static interface LinkResolvingVisitor<N extends Node> extends AstAction<N> {
        ResolvedLink resolveLink(N node, LinkResolverBasicContext context, ResolvedLink link);
    }
}
