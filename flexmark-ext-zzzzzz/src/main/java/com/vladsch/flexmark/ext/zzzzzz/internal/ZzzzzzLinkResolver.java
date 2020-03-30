package com.vladsch.flexmark.ext.zzzzzz.internal;

import com.vladsch.flexmark.ast.Image;
import com.vladsch.flexmark.ast.ImageRef;
import com.vladsch.flexmark.ast.Link;
import com.vladsch.flexmark.ast.LinkRef;
import com.vladsch.flexmark.ast.util.LinkResolverAdapter;
import com.vladsch.flexmark.ast.util.LinkResolvingHandler;
import com.vladsch.flexmark.html.LinkResolver;
import com.vladsch.flexmark.html.LinkResolverFactory;
import com.vladsch.flexmark.html.renderer.LinkResolverBasicContext;
import com.vladsch.flexmark.html.renderer.ResolvedLink;
import com.vladsch.flexmark.util.ast.Node;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class ZzzzzzLinkResolver implements LinkResolver {
    final private LinkResolverAdapter nodeAdapter;

    public ZzzzzzLinkResolver(LinkResolverBasicContext context) {
        nodeAdapter = new LinkResolverAdapter(
                new LinkResolvingHandler<>(Image.class, this::resolveLink),
                new LinkResolvingHandler<>(ImageRef.class, this::resolveLink),
                new LinkResolvingHandler<>(LinkRef.class, this::resolveLink),
                new LinkResolvingHandler<>(Link.class, this::resolveLink)
        );
    }

    @NotNull
    @Override
    public ResolvedLink resolveLink(@NotNull Node node, @NotNull LinkResolverBasicContext context, @NotNull ResolvedLink link) {
        return nodeAdapter.resolveLink(node, context, link);
    }

    public static class Factory implements LinkResolverFactory {
        @Nullable
        @Override
        public Set<Class<?>> getAfterDependents() {
            return null;
        }

        @Nullable
        @Override
        public Set<Class<?>> getBeforeDependents() {
            return null;
        }

        @Override
        public boolean affectsGlobalScope() {
            return false;
        }

        @NotNull
        @Override
        public LinkResolver apply(@NotNull LinkResolverBasicContext context) {
            return new ZzzzzzLinkResolver(context);
        }
    }
}
