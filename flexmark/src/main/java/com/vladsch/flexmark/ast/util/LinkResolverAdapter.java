package com.vladsch.flexmark.ast.util;

import com.vladsch.flexmark.html.renderer.LinkResolverContext;
import com.vladsch.flexmark.html.renderer.ResolvedLink;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.visitor.AstNodeHandler;

import java.util.Collection;

@SuppressWarnings("rawtypes")
public class LinkResolverAdapter extends AstNodeHandler<LinkResolverAdapter, Node, LinkResolvingHandler.LinkResolvingVisitor<Node>, LinkResolvingHandler<Node>> implements LinkResolvingHandler.LinkResolvingVisitor<Node> {
    protected static final LinkResolvingHandler<Node>[] EMPTY_HANDLERS = new LinkResolvingHandler[0];

    public LinkResolverAdapter(LinkResolvingHandler... handlers) {
        super(Node.AST_ADAPTER);
        addHandlers(handlers);
    }

    public LinkResolverAdapter(LinkResolvingHandler[]... handlers) {
        super(Node.AST_ADAPTER);
        addHandlers(handlers);
    }

    public LinkResolverAdapter(Collection<LinkResolvingHandler> handlers) {
        super(Node.AST_ADAPTER);
        addHandlers(handlers);
    }

    public LinkResolverAdapter addHandlers(Collection<LinkResolvingHandler> handlers) {
        return addHandlers(handlers.toArray(EMPTY_HANDLERS));
    }

    // needed for backward compatibility with extension handler arrays typed as VisitHandler<?>[]
    public LinkResolverAdapter addHandlers(LinkResolvingHandler[] handlers) {
        return super.addHandlers(handlers);
    }

    @Override
    public ResolvedLink resolveLink(Node node, LinkResolverContext context, ResolvedLink link) {
        return processNodeOnly(node, link, (n, handler) -> handler.resolveLink(n, context, link));
    }
}
