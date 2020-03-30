package com.vladsch.flexmark.ast.util;

import com.vladsch.flexmark.html.renderer.LinkResolverBasicContext;
import com.vladsch.flexmark.html.renderer.ResolvedLink;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.visitor.AstActionHandler;

import java.util.Collection;

@SuppressWarnings("rawtypes")
public class LinkResolverAdapter extends AstActionHandler<LinkResolverAdapter, Node, LinkResolvingHandler.LinkResolvingVisitor<Node>, LinkResolvingHandler<Node>> implements LinkResolvingHandler.LinkResolvingVisitor<Node> {
    protected static final LinkResolvingHandler[] EMPTY_HANDLERS = new LinkResolvingHandler[0];

    public LinkResolverAdapter(LinkResolvingHandler... handlers) {
        super(Node.AST_ADAPTER);
        super.addActionHandlers(handlers);
    }

    public LinkResolverAdapter(LinkResolvingHandler[]... handlers) {
        super(Node.AST_ADAPTER);
        //noinspection unchecked
        super.addActionHandlers(handlers);
    }

    public LinkResolverAdapter(Collection<LinkResolvingHandler> handlers) {
        super(Node.AST_ADAPTER);
        addHandlers(handlers);
    }

    // add handler variations
    public LinkResolverAdapter addHandlers(Collection<LinkResolvingHandler> handlers) {
        return super.addActionHandlers(handlers.toArray(EMPTY_HANDLERS));
    }

    public LinkResolverAdapter addHandlers(LinkResolvingHandler[] handlers) {
        return super.addActionHandlers(handlers);
    }

    public LinkResolverAdapter addHandlers(LinkResolvingHandler[]... handlers) {
        //noinspection unchecked
        return super.addActionHandlers(handlers);
    }

    public LinkResolverAdapter addHandler(LinkResolvingHandler handler) {
        //noinspection unchecked
        return super.addActionHandler(handler);
    }

    @Override
    public ResolvedLink resolveLink(Node node, LinkResolverBasicContext context, ResolvedLink link) {
        return processNodeOnly(node, link, (n, handler) -> handler.resolveLink(n, context, link));
    }
}
