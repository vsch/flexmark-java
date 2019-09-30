package com.vladsch.flexmark.ext.zzzzzz.internal;

import com.vladsch.flexmark.ast.Image;
import com.vladsch.flexmark.ast.ImageRef;
import com.vladsch.flexmark.ast.Link;
import com.vladsch.flexmark.ast.LinkRef;
import com.vladsch.flexmark.ast.util.LinkResolverAdapter;
import com.vladsch.flexmark.ast.util.LinkResolvingHandler;
import com.vladsch.flexmark.html.LinkResolver;
import com.vladsch.flexmark.html.LinkResolverFactory;
import com.vladsch.flexmark.html.renderer.LinkResolverContext;
import com.vladsch.flexmark.html.renderer.ResolvedLink;
import com.vladsch.flexmark.util.ast.Node;

import java.util.Set;

public class ZzzzzzLinkResolver implements LinkResolver {
    private final LinkResolverAdapter nodeAdapter;

    public ZzzzzzLinkResolver(LinkResolverContext context) {
        nodeAdapter = new LinkResolverAdapter(
                new LinkResolvingHandler<>(Image.class, this::resolveLink),
                new LinkResolvingHandler<>(ImageRef.class, this::resolveLink),
                new LinkResolvingHandler<>(LinkRef.class, this::resolveLink),
                new LinkResolvingHandler<>(Link.class, this::resolveLink)
        );
    }

    @Override
    public ResolvedLink resolveLink(Node node, LinkResolverContext context, ResolvedLink link) {
        return nodeAdapter.resolveLink(node, context, link);
    }

    public static class Factory implements LinkResolverFactory {
        @Override
        public Set<Class<? extends LinkResolverFactory>> getAfterDependents() {
            return null;
        }

        @Override
        public Set<Class<? extends LinkResolverFactory>> getBeforeDependents() {
            return null;
        }

        @Override
        public boolean affectsGlobalScope() {
            return false;
        }

        @Override
        public LinkResolver apply(LinkResolverContext context) {
            return new ZzzzzzLinkResolver(context);
        }
    }
}
