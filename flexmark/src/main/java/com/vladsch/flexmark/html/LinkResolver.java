package com.vladsch.flexmark.html;

import com.vladsch.flexmark.html.renderer.LinkResolverContext;
import com.vladsch.flexmark.html.renderer.ResolvedLink;
import com.vladsch.flexmark.util.ast.Node;
import org.jetbrains.annotations.NotNull;

public interface LinkResolver {
    @NotNull ResolvedLink resolveLink(@NotNull Node node, @NotNull LinkResolverContext context, @NotNull ResolvedLink link);
}
