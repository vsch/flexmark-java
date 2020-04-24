package com.vladsch.flexmark.html;

import com.vladsch.flexmark.html.renderer.LinkResolverBasicContext;
import com.vladsch.flexmark.html.renderer.ResolvedContent;
import com.vladsch.flexmark.util.ast.Node;
import org.jetbrains.annotations.NotNull;

public interface UriContentResolver {
    @NotNull ResolvedContent resolveContent(@NotNull Node node, @NotNull LinkResolverBasicContext context, @NotNull ResolvedContent content);

    UriContentResolver NULL = (node, context, content) -> content;
}
