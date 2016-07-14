package com.vladsch.flexmark.ext.zzzzzz.internal;

import com.vladsch.flexmark.html.LinkResolver;
import com.vladsch.flexmark.html.LinkResolverFactory;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.ResolvedLink;

import java.util.Set;

public class ZzzzzzLinkResolver implements LinkResolver {
    public ZzzzzzLinkResolver(NodeRendererContext context) {
    }

    @Override
    public ResolvedLink resolveLink(NodeRendererContext context, ResolvedLink link) {
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
        public LinkResolver create(NodeRendererContext context) {
            return new ZzzzzzLinkResolver(context);
        }
    }
    
}
