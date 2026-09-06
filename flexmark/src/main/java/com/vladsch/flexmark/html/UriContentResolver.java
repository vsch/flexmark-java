package com.vladsch.flexmark.html;

import com.vladsch.flexmark.html.renderer.LinkResolverBasicContext;
import com.vladsch.flexmark.html.renderer.ResolvedContent;
import com.vladsch.flexmark.util.ast.Node;

public interface UriContentResolver {
    ResolvedContent resolveContent(Node node, LinkResolverBasicContext context, ResolvedContent content);

    UriContentResolver NULL = (node, context, content) -> content;
}
