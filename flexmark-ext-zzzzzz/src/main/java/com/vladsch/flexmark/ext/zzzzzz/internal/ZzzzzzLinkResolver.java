package com.vladsch.flexmark.ext.zzzzzz.internal;

import com.vladsch.flexmark.html.LinkResolver;
import com.vladsch.flexmark.html.LinkResolverFactory;
import com.vladsch.flexmark.html.renderer.LinkResolverContext;
import com.vladsch.flexmark.html.renderer.ResolvedLink;
import com.vladsch.flexmark.util.ast.Node;

import java.util.Set;

public class ZzzzzzLinkResolver implements LinkResolver {
    public ZzzzzzLinkResolver(LinkResolverContext context) {
    }

    @Override
    public ResolvedLink resolveLink(Node node, LinkResolverContext context, ResolvedLink link) {
        return link;
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
        public LinkResolver create(LinkResolverContext context) {
            return new ZzzzzzLinkResolver(context);
        }
    }
}
